package wolforce.blocks.tile;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.Util;
import wolforce.Util.BlockWithMeta;
import wolforce.blocks.BlockCharger;
import wolforce.items.ItemPowerCrystal;
import wolforce.recipes.RecipeCharger;

public class TileCharger extends TileEntity implements ITickable {

	static final String[][][] multiblock = new String[][][] { //
			{ //
					{ "CY", "ON", "ON", "ON", "CY" }, //
					{ "ON", "MO", "MO", "MO", "ON" }, //
					{ "ON", "MO", "MO", "MO", "ON" }, //
					{ "ON", "MO", "MO", "MO", "ON" }, //
					{ "CY", "ON", "ON", "ON", "CY" }, //
			}, //
			{ //
					{ null, null, null, null, null }, //
					{ null, "AR", "AR", "AR", null }, //
					{ null, "AR", "AR", "AR", null }, //
					{ null, "AR", "AR", "AR", null }, //
					{ null, null, null, null, null }, //
			}, //
			{ //
					{ null, null, null, null, null }, //
					{ null, null, "GV", null, null }, //
					{ null, "GV", "00", "GV", null }, //
					{ null, null, "GV", null, null }, //
					{ null, null, null, null, null }, //
			}, //
	};

	//

	private int cooldown = 0;
	public ItemStackHandler inventory = new ItemStackHandler(1);

	@Override
	public void update() {

		if (world.isRemote)
			return;

		if (cooldown > 0) {
			cooldown--;
			return;
		}

		if (!Util.isValid(inventory.getStackInSlot(0)))
			return;

		IBlockState block = world.getBlockState(pos);
		if (!(block.getBlock() instanceof BlockCharger))
			return;

		ItemStack powerCrystal = inventory.getStackInSlot(0);

		// NBTTagCompound nbt = powerCrystal.getTagCompound() == null ? new
		// NBTTagCompound() : powerCrystal.getTagCompound();
		// if (!nbt.hasKey("hwell"))
		// nbt.setTag("hwell", new NBTTagCompound());
		// NBTTagCompound hwellnbt = nbt.getCompoundTag("hwell");
		// ItemPowerCrystal.setHwellNBT(hwellnbt, 0, 0, 0);
		//
		// ItemPowerCrystal.getMaxPower(nbt);
		// ItemPowerCrystal.getPower(nbt);

		if (powerCrystal != null) {
			HashMap<String, BlockWithMeta> table = new HashMap<>();
			table.put("ON", new BlockWithMeta(Main.smooth_onyx));
			table.put("MO", new BlockWithMeta(Main.moonstone));
			table.put("CY", new BlockWithMeta(Main.crystal_block));
			table.put("GV", new BlockWithMeta(Main.gravity_block_mini));
			table.put("AR", new BlockWithMeta(Blocks.AIR));

			List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.down()));

			if (!entities.isEmpty() && Util.isMultiblockBuilt(world, pos, EnumFacing.NORTH, multiblock, table)) {
				for (EntityItem entityItem : entities) {
					int powerGained = tryConsume(powerCrystal, entityItem.getItem());
					if (powerGained > 0) {
						cooldown = HwellConfig.chargerCooldown;
						return;
					}
				}
			}
		}
	}

	private int tryConsume(ItemStack powerCrystal, ItemStack stack) {
		int powerGained = RecipeCharger.getResult(stack);
		ItemStack result = RecipeCharger.getSpit(stack);

		if (powerGained > 0) {

			((WorldServer) world).spawnParticle(EnumParticleTypes.FLAME, /* particleType */ false, // long distance
					pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, // xCoord, yCoord, zCoord,
					10, /* numberOfParticles */ 0, 0, 0, // xOffset, yOffset, zOffset,
					.01 * Math.random() // particleSpeed,
			);

			world.spawnParticle(EnumParticleTypes.FLAME, pos.getX(), pos.getY(), pos.getZ() * .2, //
					Math.random() * .2, Math.random() * .2, Math.random());
			world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1, 1);

			if (Util.isValid(result))
				Util.spawnItem(world, pos.up(), result, 0, .5, 0);

			stack.grow(-1);

			if (powerCrystal.getTagCompound() != null && powerCrystal.getTagCompound().hasKey("hwell")) {
				NBTTagCompound hwellnbt = powerCrystal.getTagCompound().getCompoundTag("hwell");
				int power = ItemPowerCrystal.getPower(hwellnbt);
				int maxPower = ItemPowerCrystal.getMaxPower(hwellnbt);
				int finalPower = Math.min(power + powerGained, maxPower);
				ItemPowerCrystal.setPower(hwellnbt, finalPower);
			}
		}
		return powerGained;
	}

	//

	//

	// HAS DATA TO SAVE

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
		// if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY &&
		// inventory.getStackInSlot(0).equals(ItemStack.EMPTY))
		// return false;
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return (facing == EnumFacing.UP && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) ? (T) inventory
				: super.getCapability(capability, facing);
	}

	//

	//

	// UPDATING VIA NET

	@Override
	public void markDirty() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
		super.markDirty();
	}

	private IBlockState getState() {
		return world.getBlockState(pos);
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
