package wolforce.tile;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.Main;
import wolforce.Util;
import wolforce.Util.BlockWithMeta;
import wolforce.blocks.BlockLightCollector;
import wolforce.blocks.BlockSeparator;
import wolforce.recipes.RecipeGrinding;
import wolforce.recipes.RecipeSeparator;

public class TileSeparator extends TileEntity implements ITickable {

	static final int MAX_CHARGE = 100;

	static final String[][][] multiblock = new String[][][] { //
			{ //
					{ null, null, "L1", null, null }, //
					{ null, "HB", "L1", "HB", null }, //
					{ "L1", "L1", "L0", "L1", "L1" }, //
					{ null, "HB", "PB", "HB", null }, //
			}, //
			{ //
					{ null, null, null, null, null }, //
					{ null, "PB", "PB", "PB", null }, //
					{ null, "HB", "PB", "HB", null }, //
					{ null, "AR", "00", "AR", null }, //
			}, //
			{ //
					{ null, null, null, null, null }, //
					{ null, null, null, null, null }, //
					{ null, "FT", "AR", "FT", null }, //
					{ null, null, null, null, null }, //
			}, //
			{ //
					{ null, null, null, null, null }, //
					{ null, null, null, null, null }, //
					{ null, "LC", "AR", "LC", null }, //
					{ null, null, null, null, null }, //
			}, //

	};

	//

	private ItemStackHandler inventory = new ItemStackHandler(1);
	int charge = 0;

	@Override
	public void update() {

		if (world.isRemote)
			return;

		IBlockState block = world.getBlockState(pos);
		if (!(block.getBlock() instanceof BlockSeparator))
			return;
		// BlockSeparator separator = (BlockSeparator)block.getBlock();
		EnumFacing facing = block.getValue(BlockSeparator.FACING);

		ItemStack[] result = RecipeSeparator.getResult(inventory.getStackInSlot(0));
		if (!inventory.getStackInSlot(0).equals(ItemStack.EMPTY) && result != null) {
			HashMap<String, BlockWithMeta> table = new HashMap<>();
			table.put("PB", new BlockWithMeta(Main.heavy_protection_block));
			table.put("HB", new BlockWithMeta(Main.heat_block));
			table.put("FT", new BlockWithMeta(Main.furnace_tube));
			int metaSource = Main.liquid_souls_block.getMetaFromState(Main.liquid_souls_block.getBlockState().getBaseState());
			table.put("L0", new BlockWithMeta(Main.liquid_souls_block, metaSource));
			table.put("L1", new BlockWithMeta(Main.liquid_souls_block, metaSource, true));
			table.put("LC", new BlockWithMeta(Main.light_collector, Main.light_collector.getMetaFromState(//
					Main.light_collector.getDefaultState().withProperty(BlockLightCollector.CHARGE, 3))));
			table.put("AR", new BlockWithMeta(Blocks.AIR));

			if (Util.isMultiblockBuilt(world, pos, facing, multiblock, table)) {
				if (chargeup())
					done(facing);
			}
		}
	}

	private void done(EnumFacing facing) {
		ItemStack[] result = RecipeSeparator.getResult(inventory.extractItem(0, 64, false));
		markDirty();

		BlockPos leftPos = pos.offset(facing.rotateYCCW()).offset(facing).offset(EnumFacing.DOWN);
		Util.spawnItem(world, leftPos, result[0], 0, 0, 0);

		BlockPos rightPos = pos.offset(facing.rotateY()).offset(facing).offset(EnumFacing.DOWN);
		Util.spawnItem(world, rightPos, result[1], 0, 0, 0);

		if (!result[2].equals(ItemStack.EMPTY)) {
			BlockPos backPos = pos.offset(facing).offset(facing).offset(EnumFacing.DOWN);
			Util.spawnItem(world, backPos, result[2], 0, 0, 0);
		}
	}

	private boolean chargeup() {
		charge++;
		if (charge >= MAX_CHARGE) {
			charge = 0;
			return true;
		}
		return false;
	}
	
	//

	//

	// HAS DATA TO SAVE

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("charge", charge);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		charge = compound.getInteger("charge");
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory : super.getCapability(capability, facing);
	}

	//

	//

	// UPDATING VIA NET

	private IBlockState getState() {
		return world.getBlockState(pos);
	}

	@Override
	public void markDirty() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
		super.markDirty();
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
}
