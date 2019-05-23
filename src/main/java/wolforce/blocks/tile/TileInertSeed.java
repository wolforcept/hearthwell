package wolforce.blocks.tile;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import wolforce.Main;

public class TileInertSeed extends TileEntity implements ITickable {

	public float growth = 0.1f;
	public BlockPos current = null;
	public float completeness = 0;

	@Override
	public void update() {

		int i = 0;
		for (EntityPlayer p : world.playerEntities) {
			double dx = pos.getX() - p.posX;
			double dy = pos.getY() - p.posY;
			double dz = pos.getZ() - p.posZ;
			if (dx * dx + dy * dy + dz * dz < 3 * 3)
				i++;
		}
		growth += i * 0.00025;
		if (i <= 0)
			growth -= 0.00005;

		if (growth >= 1)
			world.setBlockState(pos, Main.core_anima.getDefaultState());

		markDirty();
	}

	//

	//

	// HAS DATA TO SAVE

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setFloat("growth", growth);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		growth = compound.getFloat("growth");
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
