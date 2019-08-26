package wolforce.blocks.tile;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.Util;
import wolforce.recipes.RecipeMutationPaste;

public class TileMutator extends TileEntity /* implements ITickable */ {

	//

	private int charge = 0;
	private boolean usePaste = false;

	public long nextAvailableUpdateMinTime = 0;

	public ItemStackHandler inventory = new ItemStackHandler(1) {

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			ItemStack ret = stack.copy();
			if (stack.getItem() == Main.mutation_paste) {
				if (canAddPaste()) {
					addPaste();
					ret.shrink(1);
				}
			} else {

				if (!Util.isValid(getStackInSlot(0)) && Util.isValid(RecipeMutationPaste.getNext(stack))) {

					int canInsertNr = Math.min( //
							stack.getCount(), //
							getCharge() / HwellConfig.machines.mutatorPasteConsumption //
					);
					ItemStack willInsert = stack.copy();
					willInsert.setCount(canInsertNr);
					super.setStackInSlot(0, willInsert);
					ret.shrink(canInsertNr);
					markDirty();
				}
			}
			return ret.copy();
		}

	};

	// @Override
	// public void update() {
	//
	// if (world.isRemote || world.isBlockPowered(pos))
	// return;
	//
	// if (!Util.isValid(inventory.getStackInSlot(0)))
	// return;
	//
	// IBlockState block = world.getBlockState(pos);
	// if (!(block.getBlock() instanceof BlockSeparator))
	// return;
	//
	// }

	public boolean canAddPaste() {
		return charge + HwellConfig.machines.mutatorPasteValue <= 1000;
	}

	public void addPaste() {
		charge += HwellConfig.machines.mutatorPasteValue;
		markDirty();
	}

	public void tryUsePaste() {
		if (!usePaste)
			return;
		charge -= HwellConfig.machines.mutatorPasteConsumption * inventory.getStackInSlot(0).getCount();
		usePaste = false;
		markDirty();
	}

	public int getCharge() {
		return charge;
	}

	public void tryMutate() {
		ItemStack next = RecipeMutationPaste.getNext(inventory.getStackInSlot(0));
		if (Util.isValid(next) && //
				charge >= HwellConfig.machines.mutatorPasteConsumption * inventory.getStackInSlot(0).getCount()) {
			int count = inventory.getStackInSlot(0).getCount();
			inventory.setStackInSlot(0, next);
			inventory.getStackInSlot(0).setCount(count);
			usePaste = true;
			markDirty();
		}
	}

	//

	//

	// HAS DATA TO SAVE

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("charge", charge);
		compound.setBoolean("usePaste", usePaste);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		charge = compound.getInteger("charge");
		usePaste = compound.getBoolean("usePaste");
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		// if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY &&
		// inventory.getStackInSlot(0).equals(ItemStack.EMPTY))
		// return false;
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory
				: super.getCapability(capability, facing);
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
