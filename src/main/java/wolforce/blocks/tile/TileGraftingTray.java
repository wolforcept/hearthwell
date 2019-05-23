package wolforce.blocks.tile;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.Hwell;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.BlockCore;
import wolforce.blocks.BlockGraftingTray;
import wolforce.items.ItemBranch;

public class TileGraftingTray extends TileEntity implements ITickable {

	public ItemStackHandler inventory = new ItemStackHandler(1);
	private int charge = 0;

	@Override
	public void update() {

		if (!Util.isValid(inventory.getStackInSlot(0)) || !BlockCore.isCore(inventory.getStackInSlot(0)))
			return;

		IBlockState state = world.getBlockState(pos);
		if (state == null || state.getBlock() != Main.grafting_tray || !state.getValue(BlockGraftingTray.FILLED))
			return;

		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				BlockPos pos2 = pos.add(x, 0, z);
				TileEntity tile = world.getTileEntity(pos2);
				if (tile != null && tile instanceof TileBranch) {
					TileBranch btile = ((TileBranch) tile);
					long life = ItemBranch.getLife(btile.time);
					if (life > 0) {
						if (!world.isRemote) {
							btile.decreaseTime(1000);
							charge++;
						} else {
							Vec3d dir = new Vec3d(pos2).subtract(new Vec3d(pos)).normalize();
							particles(pos2, dir);
						}
					}
				}
			}
		}

		if (!world.isRemote)
			if (charge >= Main.graft_costs.get(BlockCore.getCore(inventory.getStackInSlot(0)))) {
				inventory.setStackInSlot(0,
						new ItemStack(BlockCore.getGraft(Block.getBlockFromItem(inventory.getStackInSlot(0).getItem())), 8));
				charge = 0;
				BlockGraftingTray.changeFilled(world, pos, false);
				markDirty();
			}
	}

	public ItemStack getStack() {
		return inventory.getStackInSlot(0);
	}

	public ItemStack pop() {
		return inventory.extractItem(0, 64, false);
	}

	public void setCoreStack(ItemStack stack) {
		inventory.insertItem(0, new ItemStack(stack.getItem(), 1), false);
	}

	//

	//

	private void particles(BlockPos pos2, Vec3d dir) {
		// world.playSound(x, y, z, new SoundEv, category, volume, pitch,
		// distanceDelay);

		// ((WorldServer) world).spawnParticle(EnumParticleTypes.BARRIER, //
		// particleType,
		// false, // long distance
		// pos.getX() + Math.random(), pos.getY() + 1, pos.getZ() + Math.random(), //
		// xCoord, yCoord, zCoord,
		// 1, // numberOfParticles,
		// 0, -.2, 0, // xOffset, yOffset, zOffset,
		// .01 * Math.random() // particleSpeed,
		// );

		Hwell.proxy.particle(world, pos, pos2, dir);
	}

	//

	//

	// HAS DATA TO SAVE

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setInteger("charge", charge);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		charge = compound.getInteger("charge");
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
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
