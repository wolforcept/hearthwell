package wolforce.blocks.tile;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import wolforce.Hwell;
import wolforce.Util;
import wolforce.blocks.BlockGritVase;
import wolforce.blocks.BlockTray;

public class TileGritVase extends TileEntity implements ITickable {

	private BlockPos current = null;
	private Vec3d dir = null;
	private int completeness = 0;

	@Override
	public void update() {

		if (current != null && world.isAirBlock(current))
			reset();

		if (world.isRemote) {

			////////////////////// CLIENT //////////////////////

			if (current == null)
				return;

			if (dir == null)
				calcDir();

			// MyParticle particle = MyParticle.makeParticle(world, //
			// current.getX() + .25 + Math.random() * .5, //
			// current.getY() + .25 + Math.random() * .5, //
			// current.getZ() + .25 + Math.random() * .5, //
			// -dir.x, -dir.y, -dir.z, //
			// pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5);
			// Minecraft.getMinecraft().effectRenderer.addEffect(particle);

			Hwell.proxy.particle(world, current, current, dir);
			return;
		}

		////////////////////// SERVER //////////////////////

		if (current != null) {

			completeness++;
			if (completeness >= 25) {

				IBlockState iblockstate = world.getBlockState(current);
				// Block block = iblockstate.getBlock();

				// block.dropBlockAsItem(world, pos.down(), iblockstate, 0);
				@SuppressWarnings("deprecation")
				List<ItemStack> drops = iblockstate.getBlock().getDrops(world, pos, iblockstate, 0);
				for (ItemStack itemStack : drops) {
					Util.spawnItem(world, pos.down(), itemStack, 0, 0, 0);
				}
				world.setBlockState(current, Blocks.AIR.getDefaultState(), 3);

				reset();
			}
		} else if (Util.timeConstraint(world.getTotalWorldTime(), 1))
			search(world.getBlockState(pos).getValue(BlockGritVase.SIZE));

	}

	private void search(int size) {

		HashSet<Item> filter = new HashSet<Item>();
		boolean isBlackList = BlockTray.getFilter(world, pos, filter);

		for (int x = -size; x <= size; x++) {
			for (int y = 1; y <= size * 2; y++) {
				for (int z = -size; z <= size; z++) {
					BlockPos temp = pos.add(x, y, z);
					if (!world.isAirBlock(temp)) {
						if (BlockTray.isItemAble(filter, new ItemStack(world.getBlockState(temp).getBlock()).getItem(),
								isBlackList)) {

							current = temp;
							calcDir();
							markDirty();
							return;
						}
					}
				}
			}
		}
	}

	private void reset() {
		completeness = 0;
		dir = null;
		current = null;
		markDirty();
	}

	private void calcDir() {
		dir = new Vec3d(current).subtract(new Vec3d(pos)).normalize();
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return super.getRenderBoundingBox().grow(0, 3, 0);
	}

	//

	//

	// HAS DATA TO SAVE

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		if (current != null) {
			tag.setInteger("currx", current.getX());
			tag.setInteger("curry", current.getY());
			tag.setInteger("currz", current.getZ());
			tag.setInteger("compl", completeness);
		}
		return super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		if (tag.hasKey("currx")) {
			current = new BlockPos(tag.getInteger("currx"), tag.getInteger("curry"), tag.getInteger("currz"));
			completeness = tag.getInteger("compl");
		}
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
