package wolforce.blocks.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import wolforce.Main;
import wolforce.blocks.BlockPickingTable;

public class TilePickingTable extends TileEntity {

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		IBlockState block = world.getBlockState(pos);
		if (block.getValue(BlockPickingTable.FILLING) == 0 //
				&& capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		IBlockState state = world.getBlockState(pos);
		if (state.getProperties().get(BlockPickingTable.FILLING).equals(new Integer(0)) //
				&& capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) new IItemHandler() {

				@Override
				public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
					ItemStack newstack = stack.copy();
					if (stack.getItem() == Main.myst_dust) {
						newstack.shrink(1);
					}
					if (!simulate) {
						IBlockState block = world.getBlockState(pos);
						IBlockState newstate = BlockPickingTable.increase(block);
						world.setBlockState(pos, newstate);
					}
					return newstack;
				}

				@Override
				public ItemStack getStackInSlot(int slot) {
					// IBlockState block = world.getBlockState(pos);
					if (state.getValue(BlockPickingTable.FILLING) == 0)
						return ItemStack.EMPTY;
					return new ItemStack(Main.myst_dust);
				}

				@Override
				public int getSlots() {
					return 1;
				}

				@Override
				public int getSlotLimit(int slot) {
					return 1;
				}

				@Override
				public ItemStack extractItem(int slot, int amount, boolean simulate) {
					return ItemStack.EMPTY;
				}
			};
		}
		return super.getCapability(capability, facing);
	}
}
