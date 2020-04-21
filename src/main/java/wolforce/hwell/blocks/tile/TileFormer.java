package wolforce.hwell.blocks.tile;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.hwell.Main;
import wolforce.hwell.base.BlockEnergyConsumer;
import wolforce.mechanics.Util;

public class TileFormer extends TileEntity implements ITickable {

	@Override
	public void update() {
		if (world.isRemote || world.isBlockPowered(getPos()))
			return;

		if (!Util.timeConstraint(world.getTotalWorldTime(), 2) || !Util.timeConstraint(world.getTotalWorldTime(), 3))
			return;

		if (!BlockEnergyConsumer.tryConsume(world, getPos(), Main.former.getEnergyConsumption()))
			return;

		BlockPos pos = getPos(world, getPos());
		if (pos == null)
			return;
		world.playSound(null, getPos(), SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1, 1);
		world.playSound(null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1, 1);
		world.setBlockState(pos, Blocks.STONE.getDefaultState());
	}

	private BlockPos getPos(World world, BlockPos pos) {
		ArrayList<BlockPos> blocks = new ArrayList<>();

		double d = 1;
		// double miny = -d / 4;
		// double temp = miny / d;
		int iterations = 0;
		while (iterations < Integer.MAX_VALUE / 2) {
			for (double x = -d; x < d; x++) {
				for (double y = -d; y < 0; y++) {
					for (double z = -d; z < d; z++) {

						double d2 = d * d;
						// double y2 = y * temp;
						double r = x * x + z * z + (y * y) / .2;

						// r *= (y - miny) / 5;

						if (r < d2 && world.isAirBlock(pos.add(x, y, z)))
							blocks.add(pos.add(x, y, z));
					}
				}
			}
			if (!blocks.isEmpty())
				return blocks.get((int) (Math.random() * blocks.size()));
			d += .5;
			iterations++;
		}
		return null;
	}
}
