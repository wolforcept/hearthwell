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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.Main;
import wolforce.RecipeGrinder;
import wolforce.Util;
import wolforce.Util.BlockWithMeta;
import wolforce.blocks.BlockHeatFurnace;
import wolforce.blocks.BlockPrecisionGrinder;
import wolforce.blocks.BlockPrecisionGrinderEmpty;
import wolforce.items.ItemGrindingWheel;

public class TileSeparator extends TileEntity implements ITickable {

	static final int MAX_COOLDOWN = 100;

	static final String[][][] multiblock = new String[][][] { //
			{ //
					{ "HB", "SL", "HB" }, //
					{ "PB", "00", "PB" }, //
					{ "HB", "PB", "HB" }, //
			}, { //
					{ null, "PB", null }, //
					{ "PB", "AR", "AR" }, //
					{ null, "PB", null } //

			}, { //
					{ null, "PB", null }, //
					{ "PB", "SL", "AR" }, //
					{ null, "PB", null } //

			} };

	//

	private ItemStackHandler inventory = new ItemStackHandler(1);
	int cooldown = 0;

	@Override
	public void update() {

		if (world.isRemote)
			return;

		if (cooldown > 0) {
			cooldown -= 5;
			return;
		}

		IBlockState block = world.getBlockState(pos);
		if (!(block.getBlock() instanceof BlockPrecisionGrinder))
			return;
		EnumFacing facing = block.getValue(BlockHeatFurnace.FACING);

		List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, //
				new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1.5, pos.getZ() + 1));
		if (entities.size() > 0) {
			HashMap<String, BlockWithMeta> table = new HashMap<>();
			table.put("PB", new BlockWithMeta(Main.protection_block));
			table.put("HB", new BlockWithMeta(Main.heavy_block));
			table.put("SL", new BlockWithMeta(Main.slab_lamp, EnumFacing.DOWN.ordinal()));
			table.put("AR", new BlockWithMeta(Blocks.AIR));

			if (Util.isMultiblockBuilt(world, pos, facing, multiblock, table)) {
				ItemGrindingWheel gwheel = ((BlockPrecisionGrinder) block.getBlock()).grindingWheel;
				EntityItem entityItem = entities.get(0);
				ItemStack result = RecipeGrinder.getResult(gwheel, entityItem.getItem());
				if (result != null && result != ItemStack.EMPTY) {
					output(result, facing);
					entityItem.getItem().shrink(1);
					cooldown = MAX_COOLDOWN;
					double prob = Math.pow(entityItem.getItem().getCount(), 4) / 33333300.0;
					if (entityItem.getItem().getCount() > 20 && Math.random() < prob)
						popGrindingWheel(entityItem, gwheel, facing);
				}
			}
		}
	}

	private void output(ItemStack result, EnumFacing facing) {
		BlockPos newpos = pos.offset(facing);
		EntityItem newentity = new EntityItem(world, newpos.getX() + .5, newpos.getY(), newpos.getZ() + .5,
				new ItemStack(result.getItem(), result.getCount(), result.getMetadata()));
		BlockPos velpos = new BlockPos(0, 0, 0).offset(facing);
		newentity.setVelocity(velpos.getX() / 2.0, velpos.getY() / 2.0, velpos.getZ() / 2.0); // TODO set velocity depending on facing
		world.spawnEntity(newentity);
	}

	private void popGrindingWheel(EntityItem entityItem, ItemGrindingWheel gwheel, EnumFacing facing) {
		Util.spawnItem(world, pos.up(), new ItemStack(gwheel));
		world.setBlockState(pos, Main.precision_grinder_empty.getDefaultState().withProperty(BlockPrecisionGrinderEmpty.FACING, facing));
	}

	//

	//

	//

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("cooldown", cooldown);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		cooldown = compound.getInteger("cooldown");
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

	public void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
		markDirty();
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
