package wolforce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.base.BlockWithDescription;
import wolforce.blocks.base.HasTE;
import wolforce.blocks.tile.TileCharger;
import wolforce.blocks.tile.TileTray;

public class BlockTray extends Block implements HasTE, BlockWithDescription {

	private static final double F = 1.0 / 16.0;

	protected static final AxisAlignedBB AABB_UP = new AxisAlignedBB(0, 0, 0, 1, 3 * F, 1);
	protected static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(0, 13 * F, 0, 1, 1, 1);
	protected static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0, 0, 0, 1, 1, 3 * F);
	protected static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0, 0, 13 * F, 1, 1, 1);
	protected static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0, 0, 0, 3 * F, 1, 1);
	protected static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(13 * F, 0, 0, 1, 1, 1);

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);

	public BlockTray(String name) {
		super(Material.WOOD);
		Util.setReg(this, name);
		setHardness(2);
		setHarvestLevel("axe", -1);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		TileTray tile = (TileTray) world.getTileEntity(pos);
		ItemStack stack = tile.inventory.getStackInSlot(0);

		// IF THERE IS SOMETHING INSIDE
		if (Util.isValid(stack)) {
			if (!world.isRemote) {
				Util.spawnItem(world, pos.down(), tile.inventory.extractItem(0, 64, false));
				tile.markDirty();
			}
			return true;
		}

		// IF EMPTY LETS TRY INSERT
		ItemStack held = player.getHeldItem(hand);
		if (!Util.isValid(held))
			return false;

		if (!world.isRemote) {
			tile.inventory.setStackInSlot(0, held);
			tile.markDirty();
		}
		player.setHeldItem(hand, ItemStack.EMPTY);
		return true;

	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (worldIn.isRemote) {
			TileTray tile = (TileTray) worldIn.getTileEntity(pos);
			for (int i = 0; i < tile.inventory.getSlots(); i++) {
				Util.spawnItem(worldIn, pos, tile.inventory.extractItem(i, 64, false));
			}
		}
		super.breakBlock(worldIn, pos, state);
	}

	//

	//

	// VISUALS

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
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

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	//

	//

	//

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		if (placer.rotationPitch < -45)
			return this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
		if (placer.rotationPitch > 45)
			return this.getDefaultState().withProperty(FACING, EnumFacing.UP);
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
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

	//

	//

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileTray();
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Small Item Tray that holds up to 9 Items.", "Also serves as a filter to some blocks." };
	}

}
