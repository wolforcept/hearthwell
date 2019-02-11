package wolforce.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.blocks.simplevariants.MyLog;
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
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (state.getValue(BlockLog.LOG_AXIS) != BlockLog.EnumAxis.Y)
			return;
		int nTubes = getNrOfTubesOnTop(world, pos);
		if (!isPossible(nTubes, world, pos))
			return;
		if (RecipeTube.getResult(world.getBlockState(pos.down())) != null) {
			for (int i = 0; i < 3; i++) {
				world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + Math.random(), pos.getY() + (Math.random() * nTubes) + 1,
						pos.getZ() + Math.random(), 0, -.02 - Math.random() * .2, 0);
			}
		}
	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
		if (state.getValue(BlockLog.LOG_AXIS) != BlockLog.EnumAxis.Y)
			return;
		Object result = RecipeTube.getResult(world.getBlockState(pos.down()));
		if (result instanceof ItemStack) {
			Block resultBlock = Block.getBlockFromItem(((ItemStack) result).getItem());
			tryMake(world, pos, resultBlock.getDefaultState());
		}

		if (result instanceof FluidStack) {
			Block resultBlock =((FluidStack)result).getFluid().getBlock();
			tryMake(world, pos, resultBlock.getDefaultState());
		}

	}

	private void tryMake(World world, BlockPos pos, IBlockState state) {
		int nTubes = getNrOfTubesOnTop(world, pos);
		if (/**/isPossible(nTubes, world, pos) && //
				Math.random() < nTubes * .1 //
		)
			world.setBlockState(pos.down(), state, 1 | 2);
	}

	private boolean isPossible(int nTubes, World world, BlockPos pos) {
		return nTubes > 0 && nTubes < 10 && //
				(world.canBlockSeeSky(pos.up(nTubes)) || !HwellConfig.isTubeRequiredToSeeSky) && //
				(world.isDaytime() || !HwellConfig.isTubeRequiredToBeDay);
	}

	/**
	 * ON TOP
	 */
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
