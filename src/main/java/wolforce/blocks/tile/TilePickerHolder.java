package wolforce.blocks.tile;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.Main;

public class TilePickerHolder extends TileEntity {

	public static final int nSlots = 8;
	public ItemStackHandler inventory = new ItemStackHandler(nSlots);

	public ItemStack swap(ItemStack stack) {
		insert(stack);
		int next = stackToInt(stack) + 1;
		if (next >= nSlots)
			next = 0;
		return takeNext(next);
	}

	public ItemStack takeNext(int startI) {
		for (int i = 0; i <= nSlots; i++) {
			int takeI = startI + i;
			if (takeI >= nSlots)
				takeI -= nSlots;
			if (!inventory.getStackInSlot(takeI).isEmpty())
				return inventory.extractItem(takeI, 1, false);
		}
		return ItemStack.EMPTY;
	}

	public boolean canInsert(ItemStack picker) {
		return inventory.getStackInSlot(stackToInt(picker)).isEmpty();
	}

	public void insert(ItemStack picker) {
		inventory.setStackInSlot(stackToInt(picker), picker);
	}

	public boolean isEmpty() {
		for (int i = 0; i < nSlots; i++) {
			if (!inventory.getStackInSlot(i).isEmpty())
				return false;
		}
		return true;
	}

	private int stackToInt(ItemStack stack) {
		Item picker = stack.getItem();
		if (picker.equals(Main.myst_dust_picker_c))
			return 0;
		if (picker.equals(Main.myst_dust_picker_au))
			return 1;
		if (picker.equals(Main.myst_dust_picker_ca))
			return 2;
		if (picker.equals(Main.myst_dust_picker_h))
			return 3;
		if (picker.equals(Main.myst_dust_picker_fe))
			return 4;
		if (picker.equals(Main.myst_dust_picker_n))
			return 5;
		if (picker.equals(Main.myst_dust_picker_o))
			return 6;
		if (picker.equals(Main.myst_dust_picker_p))
			return 7;
		throw new RuntimeException("not a valid dustpicker: " + picker);
	}

	private ItemStack intToStack(int index) {
		Item shard = Main.shards[index];
		if (shard == Main.shard_c)
			return new ItemStack(Main.myst_dust_picker_c);
		if (shard == Main.shard_au)
			return new ItemStack(Main.myst_dust_picker_au);
		if (shard == Main.shard_ca)
			return new ItemStack(Main.myst_dust_picker_ca);
		if (shard == Main.shard_h)
			return new ItemStack(Main.myst_dust_picker_h);
		if (shard == Main.shard_fe)
			return new ItemStack(Main.myst_dust_picker_fe);
		if (shard == Main.shard_n)
			return new ItemStack(Main.myst_dust_picker_n);
		if (shard == Main.shard_o)
			return new ItemStack(Main.myst_dust_picker_o);
		if (shard == Main.shard_p)
			return new ItemStack(Main.myst_dust_picker_p);
		throw new RuntimeException("invalid picker index: " + index);
	}

	//

	//

	//

	// HAS DATA TO SAVE

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
	}

	// @Override
	// public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing
	// facing) {
	// return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
	// super.hasCapability(capability, facing);
	// }
	//
	// @Nullable
	// @Override
	// public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing
	// facing) {
	// return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)
	// inventory : super.getCapability(capability, facing);
	// }

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
		world.scheduleBlockUpdate(pos, Main.picker_holder, 0, 0);
		super.markDirty();
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, -1, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		handleUpdateTag(pkt.getNbtCompound());
	}

}
