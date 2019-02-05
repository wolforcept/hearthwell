package wolforce.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.HWellConfig;
import wolforce.recipes.RecipeTube;

public class BlockTube extends MyLog {

	public BlockTube(String name) {
		super(name);
		setTickRandomly(true);
		setHardness(2);
		setResistance(2);
		setHarvestLevel("pickaxe", -1);
	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
		if (state.getValue(BlockLog.LOG_AXIS) != BlockLog.EnumAxis.Y)
			return;
		Block result = RecipeTube.getResult(world.getBlockState(pos.down()).getBlock());
		if (result != null)
			tryMake(world, pos, result.getDefaultState());
	}

	private void tryMake(World world, BlockPos pos, IBlockState state) {
		int nTubes = getNrOfTubesOnTop(world, pos);
		if (/**/(world.canBlockSeeSky(pos.up(nTubes)) || !HWellConfig.isTubeRequiredToSeeSky) && //
				(world.isDaytime() || !HWellConfig.isTubeRequiredToBeDay) && //
				Math.random() < nTubes * .1 //
		)
			world.setBlockState(pos.down(), state, 1 | 2);
	}

	private int getNrOfTubesOnTop(World world, BlockPos pos) {
		int n = 0;
		BlockPos currentPos = pos.up();
		while (world.getBlockState(currentPos).getBlock() == Main.furnace_tube
				&& world.getBlockState(currentPos).getValue(BlockLog.LOG_AXIS) == BlockLog.EnumAxis.Y && n < 10) {
			n++;
			currentPos = currentPos.up();
		}
		return n;
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
		Block block = iblockstate.getBlock();

		if (this == Main.crystal_block) {
			if (blockState != iblockstate)
				return true;
			if (block == this)
				return false;
		}

		return block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}
