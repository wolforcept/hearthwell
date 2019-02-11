package wolforce.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BlockEnergyConsumer extends BlockWithDescription {

	public static final int MAX_ENERGY_DISTANCE = 10;

	int getEnergyConsumption();

	static boolean tryConsume(World world, BlockPos pos, int energy) {

		BlockPos pos2 = null;

		{// FIND ENERGY
			double distsq = 0;
			for (int x = -MAX_ENERGY_DISTANCE; x <= MAX_ENERGY_DISTANCE; x++) {
				for (int y = -MAX_ENERGY_DISTANCE; y <= MAX_ENERGY_DISTANCE; y++) {
					for (int z = -MAX_ENERGY_DISTANCE; z <= MAX_ENERGY_DISTANCE; z++) {
						BlockPos posTemp = pos.add(x, y, z);
						Block blockat = world.getBlockState(posTemp).getBlock();
						if (blockat instanceof BlockEnergyProvider && //
								((BlockEnergyProvider) blockat).hasEnergy(world, posTemp, energy)) {
							double distsqTemp = posTemp.distanceSq(pos);
							if (pos2 == null || distsqTemp < distsq) {
								pos2 = posTemp;
								distsq = distsqTemp;
							}
						}
					}
				}
			}
		}

		if (pos2 == null) {
			return false;
		}

		BlockEnergyProvider provider = (BlockEnergyProvider) world.getBlockState(pos2).getBlock();

		int loss = (int) Math.max(0, .01 * pos2.distanceSq(pos) - 1);

		if (provider.hasEnergy(world, pos2, energy + loss)) {
			return provider.tryConsume(world, pos2, energy + loss);
		}
		return false;
	}

}
