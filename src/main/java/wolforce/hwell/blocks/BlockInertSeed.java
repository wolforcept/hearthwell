package wolforce.hwell.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.hwell.base.HasTE;
import wolforce.hwell.base.MyBlock;
import wolforce.hwell.blocks.tile.TileInertSeed;

public class BlockInertSeed extends MyBlock implements HasTE {

	public BlockInertSeed() {
		super(Material.CLAY);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileInertSeed();
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
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isTopSolid(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileInertSeed) {
			double g = ((int) (((TileInertSeed) te).growth * 10)) / 10.0;
			return new AxisAlignedBB(.5 - g / 2, 0, .5 - g / 2, .5 + g / 2, g, .5 + g / 2);
		}
		return new AxisAlignedBB(0, 0, 0, 1, 1, 1);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return getBox(source, pos);
	}

	private AxisAlignedBB getBox(IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileInertSeed) {
			double g = ((TileInertSeed) te).growth;
			return new AxisAlignedBB(.5 - g / 2, 0, .5 - g / 2, .5 + g / 2, g, .5 + g / 2);
		}
		return new AxisAlignedBB(0, 0, 0, 1, 1, 1);
	}

}
