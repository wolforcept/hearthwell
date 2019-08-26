package wolforce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolforce.Util;

public class BlockGaseousFrame extends Block {

	static final AxisAlignedBB BB = new AxisAlignedBB(0, 0, 0, 1, 1, 1);

	public static final PropertyBool PASSABLE = PropertyBool.create("passable");

	public BlockGaseousFrame(String name) {
		super(Material.GLASS);
		Util.setReg(this, name);
		setDefaultState(blockState.getBaseState().withProperty(PASSABLE, false));
		setHarvestLevel("pickaxe", -1);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote)
			transform(worldIn, pos, !state.getValue(PASSABLE));
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote) {
			if (worldIn.isBlockIndirectlyGettingPowered(fromPos) > 0)
				transform(worldIn, pos, !state.getValue(PASSABLE));
		}
	}

	private void transform(World world, BlockPos pos, boolean newValue) {
		world.setBlockState(pos, getDefaultState().withProperty(PASSABLE, newValue));
		for (EnumFacing facing : EnumFacing.values()) {
			IBlockState state2 = world.getBlockState(pos.offset(facing));
			if (state2.getBlock() instanceof BlockGaseousFrame && (state2.getValue(PASSABLE) != newValue))
				transform(world, pos.offset(facing), newValue);
		}
	}

	private boolean isPassable(IBlockState state) {
		return state.getBlock() instanceof BlockGaseousFrame && state.getPropertyKeys().contains(PASSABLE)
				&& state.getValue(PASSABLE);
	}

	//

	//

	@Override
	public boolean causesSuffocation(IBlockState state) {
		return !isPassable(state);
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
		return !isPassable(state);
	}

	@Override
	public boolean isPassable(IBlockAccess world, BlockPos pos) {
		return isPassable(world.getBlockState(pos));
	}

	@Override
	public boolean isTopSolid(IBlockState state) {
		return !isPassable(state);
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return !isPassable(state);
	}

	// VISUALS
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isTranslucent(IBlockState state) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
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
		return block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return isPassable(state) ? null : BB;
	}

	// BLOCK STATES

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PASSABLE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(PASSABLE, meta != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(PASSABLE) ? 1 : 0;
	}
}
