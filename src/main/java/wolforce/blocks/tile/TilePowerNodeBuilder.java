package wolforce.blocks.tile;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.Util;
import wolforce.recipes.RecipePowerNode;

public class TilePowerNodeBuilder extends TileEntity {

	protected static final int[] maxPerSlot = { 1, 2, 6 };

	public ItemStackHandler inventory = new ItemStackHandler(3) {

		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {

			ItemStack slotStack = getStackInSlot(slot).copy();
			ItemStack returnStack = stack.copy();
			int slotCount = Util.isValid(slotStack) ? slotStack.getCount() : 0;

			if (Util.isValid(returnStack) && (!Util.isValid(slotStack) || Util.equalExceptAmount(stack, slotStack))) {
				slotStack.setCount(maxPerSlot[slot] - slotCount);
				returnStack.setCount(returnStack.getCount() - (maxPerSlot[slot] - slotCount));
			}

			if (!simulate) {
				setStackInSlot(slot, slotStack);
				markDirty();
			}

			return returnStack;
		};

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			markDirty();
			return super.extractItem(slot, amount, simulate);
		}

	};

	public void activated(IBlockState state, EntityPlayer player, EnumHand hand) {

		ItemStack held = player.getHeldItem(hand);
		ItemStack ret = null;

		if (RecipePowerNode.isNucleous(held))
			ret = inventory.insertItem(0, held, false);

		else if (RecipePowerNode.isRelay(held))
			ret = inventory.insertItem(1, held, false);

		else if (RecipePowerNode.isScreen(held))
			ret = inventory.insertItem(2, held, false);

		if (Util.isValid(ret))
			player.setHeldItem(hand, ret);

	}

	public void broken(IBlockState state) {
		for (int i = 0; i < inventory.getSlots(); i++)
			Util.spawnItem(world, pos, inventory.getStackInSlot(i));
	}

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

	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
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
