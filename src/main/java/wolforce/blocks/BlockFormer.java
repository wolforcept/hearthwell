package wolforce.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.base.BlockEnergyConsumer;
import wolforce.base.MyBlock;

public class BlockFormer extends MyBlock implements BlockEnergyConsumer {

	public BlockFormer(String name) {
		super(name, Material.ROCK, true);
		setResistance(2f);
		setHardness(2f);
		setHarvestLevel("pickaxe", -1);
		setSoundType(SoundType.STONE);
	}

	@Override
	public void randomTick(World world, BlockPos _pos, IBlockState state, Random random) {
		if (world.isRemote || world.isBlockPowered(_pos))
			return;

		if (!BlockEnergyConsumer.tryConsume(world, _pos, getEnergyConsumption()))
			return;

		BlockPos pos = getPos(world, _pos);
		if (pos == null)
			return;
		world.playSound(null, _pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1, 1);
		world.playSound(null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1, 1);
		world.setBlockState(pos, Blocks.STONE.getDefaultState());
	}

	private BlockPos getPos(World world, BlockPos pos) {
		ArrayList<BlockPos> blocks = new ArrayList<>();

		double d = 1;
		double miny = -d / 4;
		double temp = miny / d;
		int iterations = 0;
		while (iterations < Integer.MAX_VALUE / 2) {
			for (double x = -d; x < d; x++) {
				for (double y = -d; y < 0; y++) {
					for (double z = -d; z < d; z++) {

						double d2 = d * d;
						double y2 = y * temp;
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

		// SQUARE :

		// int direction = 1, maxd = 1, d = 0;
		// int x = 0, y = 0, z = 0;
		// boolean onxx = true;
		// LinkedList<BlockPos> now = new LinkedList<>();
		// LinkedList<BlockPos> later = new LinkedList<>();
		// LinkedList<BlockPos> later2 = new LinkedList<>();
		// while (!world.isAirBlock(pos.add(x, y, z)) || !now.isEmpty()) {
		//
		// while (!now.isEmpty()) {
		// BlockPos nowpos = now.pop();
		// if (world.isAirBlock(nowpos))
		// return nowpos;
		// }
		//
		// if (Math.hypot(x, z) > (maxd - 1) / 2.0) {
		// later2.add(pos.add(x, y, z));
		// }
		// if (onxx) {
		// d++;
		// x += direction;
		// if (d == maxd) {
		// onxx = false;
		// d = 0;
		// }
		// } else {
		// d++;
		// z += direction;
		// if (d == maxd) {
		// onxx = true;
		// maxd++;
		// d = 0;
		// direction *= -1;
		// now = later;
		// later = later2;
		// later2 = new LinkedList<>();
		// }
		// }
		// }
		// return pos.add(x, y, z);
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Will slowly create a flat-top, circular, stone shape around it.",
				"Consumes " + getEnergyConsumption() + " energy per operation." };
	}

	@Override
	public int getEnergyConsumption() {
		return HwellConfig.machines.formerConsumption;
	}

}
