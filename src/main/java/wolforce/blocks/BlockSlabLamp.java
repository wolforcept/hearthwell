package wolforce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.MyBlock;

public class BlockSlabLamp extends MyBlock {

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);

	protected static final AxisAlignedBB AABB_UP = new AxisAlignedBB(.0, .0, .0, 1, .5, 1);
	protected static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(0, .5, 0, 1, 1, 1);
	protected static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0, 0, 0, 1, 1, .5);
	protected static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0, 0, .5, 1, 1, 1);
	protected static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0, 0, 0, .5, 1, 1);
	protected static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(.5, 0, 0, 1, 1, 1);

	public BlockSlabLamp(String name) {
		super(name, Material.ROCK);
		setHardness(1);
		setResistance(1);
		setLightLevel(0.9375F);
		setHarvestLevel("pickaxe", -1);
		setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.isSneaking() ? facing.getOpposite() : facing);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch ((EnumFacing) state.getValue(FACING)) {
		case DOWN:
			return AABB_DOWN;
		case EAST:
			return AABB_EAST;
		case NORTH:
			return AABB_NORTH;
		case SOUTH:
			return AABB_SOUTH;
		case UP:
			return AABB_UP;
		case WEST:
			return AABB_WEST;
		}
		return AABB_DOWN;
	}

	public boolean isTopSolid(IBlockState state) {
		return state.getValue(FACING) == EnumFacing.SOUTH;
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return state.getValue(FACING) == face ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
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
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return getBoundingBox(blockState, worldIn, pos);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	//

	//

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).ordinal();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

}
