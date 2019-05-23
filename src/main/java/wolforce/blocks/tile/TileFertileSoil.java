package wolforce.blocks.tile;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileFertileSoil extends TileEntity implements ITickable {

	@Override
	public void update() {
		if (world.isRemote || Math.random() > .01)
			return;
		Block block = world.getBlockState(pos.up()).getBlock();
		if (block instanceof IGrowable) {
			((IGrowable) block).grow(world, world.rand, pos.up(), world.getBlockState(pos.up()));
		}
	}

}
