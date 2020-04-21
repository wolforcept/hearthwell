package wolforce.hwell.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.hwell.base.BlockWithDescription;
import wolforce.hwell.base.HasTE;
import wolforce.hwell.blocks.tile.TileHeatFurnace;

public class BlockHeatFurnace extends Block implements HasTE, BlockWithDescription {

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);

	public BlockHeatFurnace(String name) {
		super(Material.IRON);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	// @Override
	// public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
	// if (!worldIn.isRemote) {
	// IBlockState iblockstate = worldIn.getBlockState(pos.north());
	// IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
	// IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
	// IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
	// EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
	//
	// if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() &&
	// !iblockstate1.isFullBlock()) {
	// enumfacing = EnumFacing.SOUTH;
	// } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() &&
	// !iblockstate.isFullBlock()) {
	// enumfacing = EnumFacing.NORTH;
	// } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() &&
	// !iblockstate3.isFullBlock()) {
	// enumfacing = EnumFacing.EAST;
	// } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() &&
	// !iblockstate2.isFullBlock()) {
	// enumfacing = EnumFacing.WEST;
	// }
	//
	// worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
	// }
	// }

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileHeatFurnace();
	}

	// BLOCK STATES

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).ordinal();
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Smelts items instantly and at no cost.", "Requires a multiblock Structure." };
	}
}
