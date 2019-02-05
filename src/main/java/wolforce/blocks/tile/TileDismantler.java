package wolforce.blocks.tile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.Main;
import wolforce.MyBlock;
import wolforce.blocks.BlockDismantler;

public class TileDismantler extends TileEntity implements ITickable {

	private static final int maxCharge = 1000;

	private int charge;

	private ItemStackHandler inv;

	public TileDismantler() {
		inv = new ItemStackHandler(1);
	}

	public ItemStack getItemStack() {
		return inv.getStackInSlot(0);
	}

	// lifecycle

	// @Override public void validate() {super.validate();}

	// @Override public void onLoad() { super.onLoad(); }

	@Override
	public void update() {
		// if (BlockDismantler.isValid(inv.getStackInSlot(0)))
		// System.out.println("AAA");;
		// if (BlockDismantler.isValid(inv.getStackInSlot(0))) {
		// charge++;
		// if (charge == maxCharge) {
		// complete();
		// inv.setStackInSlot(0, ItemStack.EMPTY);
		// }
		// }
	}

	private void complete() {
		world.spawnEntity(
				new EntityItem(world, getPos().getX(), getPos().getY(), getPos().getZ(), new ItemStack(Main.dust)));
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		charge = nbt.getInteger("charge");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("value", charge);
		return nbt;
	}

	public void setItemStack(ItemStack stack) {
		inv.setStackInSlot(0, stack);
		markDirty();
	}

	// handling block updates
	// @Override
	// public SPacketUpdateTileEntity getUpdatePacket() {
	// return super.getUpdatePacket();
	// }

	// @Override
	// public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
	// super.onDataPacket(net, pkt);
	// }

	// handling chunk loads

	// @Override
	// public NBTTagCompound getUpdateTag() {
	// // TODO Auto-generated method stub
	// return super.getUpdateTag();
	// }

	// @Override
	// public void handleUpdateTag(NBTTagCompound tag) {
	// // TODO Auto-generated method stub
	// super.handleUpdateTag(tag);
	// }

	// other? "used to store TE data on the world"

	// @Override
	// public NBTTagCompound getTileData() {
	// return super.getTileData();
	// }
}
