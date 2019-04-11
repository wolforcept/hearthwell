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
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.UtilClient;
import wolforce.base.MyLog;
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
	public void randomDisplayTick(IBlockState state, World world, BlockPos _pos, Random rand) {
		if (state.getValue(BlockLog.LOG_AXIS) != BlockLog.EnumAxis.Y || !UtilClient.clientIsDaytime(world))
			return;
		BlockPos bot = getBlockUnderTube(world, _pos);
		int nTubes = getNrOfTubesOnTop(world, bot);
		if (!isPossible(nTubes, world, bot))
			return;
		if (RecipeTube.getResult(world.getBlockState(bot)) != null) {
			world.spawnParticle(EnumParticleTypes.FLAME, _pos.getX() + Math.random(), _pos.getY() + (Math.random()),
					_pos.getZ() + Math.random(), 0, -.02 - Math.random() * .2, 0);
		}
	}

	@Override
	public void randomTick(World world, BlockPos _pos, IBlockState state, Random random) {
		if (state.getValue(BlockLog.LOG_AXIS) != BlockLog.EnumAxis.Y)
			return;

		BlockPos bot = getBlockUnderTube(world, _pos);

		Object result = RecipeTube.getResult(world.getBlockState(bot));
		if (result instanceof ItemStack) {
			Block resultBlock = Block.getBlockFromItem(((ItemStack) result).getItem());
			tryMake(world, bot, resultBlock.getDefaultState());
		}

		if (result instanceof FluidStack) {
			Block resultBlock = ((FluidStack) result).getFluid().getBlock();
			tryMake(world, bot, resultBlock.getDefaultState());
		}

	}

	private BlockPos getBlockUnderTube(World world, BlockPos _pos) {
		BlockPos pos = _pos;
		while (world.getBlockState(pos).getBlock() instanceof BlockTube) {
			pos = pos.down();
		}
		return pos;
	}

	private void tryMake(World world, BlockPos pos, IBlockState state) {
		int nTubes = getNrOfTubesOnTop(world, pos);
		if (/**/isPossible(nTubes, world, pos) && //
				Math.random() < (.2 + nTubes * .1) //
		)
			world.setBlockState(pos, state);
	}

	private boolean isPossible(int nTubes, World world, BlockPos pos) {
		return nTubes > 0 && nTubes <= 8 && //
				(world.canBlockSeeSky(pos.up(nTubes)) || !HwellConfig.other.tubeIsRequiredToSeeSky) && //
				(isDay(world) || !HwellConfig.other.tubeIsRequiredToBeDay);
	}

	private boolean isDay(World world) {
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
			return UtilClient.clientIsDaytime(world);
		// System.out.println(((WorldClient) world).getWorldTime());
		// if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
		// return Util.clientIsDaytime(world);
		return world.isDaytime();
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
