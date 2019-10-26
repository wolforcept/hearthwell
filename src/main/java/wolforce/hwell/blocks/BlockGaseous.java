package wolforce.hwell.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolforce.hwell.Main;
import wolforce.hwell.base.MyFalling;
import wolforce.hwell.base.MyGlass;

public class BlockGaseous {

	private static boolean ignoreSimilarity = false;

	public static class Sand extends MyFalling {

		public Sand() {
			super();
		}

		@Override
		public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
			return null;
		}

		@Override
		public boolean isFullBlock(IBlockState state) {
			return false;
		}

		@Override
		public boolean isFullCube(IBlockState state) {
			return false;
		}

		@Override
		public boolean isOpaqueCube(IBlockState state) {
			return false;
		}

		@SuppressWarnings("deprecation")
		@SideOnly(Side.CLIENT)
		public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
				EnumFacing side) {
			IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
			Block block = iblockstate.getBlock();

			if (this == Main.crystal_block) {
				if (blockState != iblockstate)
					return true;
				if (block == this)
					return false;
			}

			return !ignoreSimilarity && block == this ? false
					: super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}
	}

	public static class Glass extends MyGlass {

		public Glass() {
			super();
		}

		@Override
		public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
			return null;
		}
	}
}
