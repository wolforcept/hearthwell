package wolforce.blocks;

import java.util.HashSet;
import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.Main;
import wolforce.Util;
import wolforce.base.BlockWithDescription;
import wolforce.base.HasTE;
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
	public static final PropertyBool ISBLACK = PropertyBool.create("isblack");

	public BlockTray(String name) {
		super(Material.WOOD);
		Util.setReg(this, name);
		setHardness(2);
		setHarvestLevel("axe", -1);
		setDefaultState(this.blockState.getBaseState().withProperty(ISBLACK, false).withProperty(FACING, EnumFacing.UP));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side,
			float hitX, float hitY, float hitZ) {

		if (player.isSneaking())
			return false;

		if (side != world.getBlockState(pos).getValue(FACING)) {
			changeIsBlack(world, pos);
			return true;
		}

		TileTray tile = (TileTray) world.getTileEntity(pos);

		int dx = hitX < .333 ? 0 : hitX < .666 ? 1 : 2;
		int dy = hitY < .333 ? 0 : hitY < .666 ? 1 : 2;
		int dz = hitZ < .333 ? 0 : hitZ < .666 ? 1 : 2;

		int index = dx + 3 * dz;
		if (side == EnumFacing.NORTH)
			index = dx + 3 * dy;
		if (side == EnumFacing.SOUTH)
			index = dx + 3 * dy;
		if (side == EnumFacing.WEST)
			index = dz + 3 * dy;
		if (side == EnumFacing.EAST)
			index = dz + 3 * dy;

		if (index < 0 || index > 8)
			return false;

		ItemStack stack = tile.inventory.getStackInSlot(index);

		// IF THERE IS SOMETHING INSIDE
		if (Util.isValid(stack)) {
			if (!world.isRemote) {
				Util.spawnItem(world, pos, tile.inventory.extractItem(index, 64, false));
				tile.markDirty();
			}
			return true;
		}

		// IF EMPTY LETS TRY INSERT
		ItemStack stackToInsert = player.getHeldItem(hand).copy();
		stackToInsert.setCount(1);
		player.getHeldItem(hand).shrink(1);

		if (!Util.isValid(stackToInsert)) {
			changeIsBlack(world, pos);
			return true;
		}

		if (!world.isRemote) {
			tile.inventory.setStackInSlot(index, stackToInsert);
			tile.markDirty();
		}

		return true;
	}

	private void changeIsBlack(World world, BlockPos pos) {
		ItemStackHandler oldInv = ((TileTray) world.getTileEntity(pos)).inventory;
		EnumFacing facing = world.getBlockState(pos).getValue(FACING);
		boolean isblack = world.getBlockState(pos).getValue(ISBLACK);
		IBlockState newstate = world.getBlockState(pos).withProperty(ISBLACK, !isblack).withProperty(FACING, facing);
		world.setBlockState(pos, newstate);
		((TileTray) world.getTileEntity(pos)).inventory = oldInv;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			TileTray tile = (TileTray) worldIn.getTileEntity(pos);
			for (int i = 0; i < tile.inventory.getSlots(); i++) {
				Util.spawnItem(worldIn, pos, tile.inventory.extractItem(i, 64, false));
			}
		}
		super.breakBlock(worldIn, pos, state);
	}

	public static boolean getFilter(World world, BlockPos pos, HashSet<Item> filters) {
		boolean isBlackList = true;
		TESTALL: for (EnumFacing face : EnumFacing.VALUES) {
			BlockPos pos2 = pos.offset(face);
			if (world.getBlockState(pos2).getBlock() instanceof BlockTray) {
				if (!isBlackList(world, pos2)) {
					isBlackList = false;
					break TESTALL;
				}
			}
		}
		for (EnumFacing face : EnumFacing.VALUES) {
			BlockPos pos2 = pos.offset(face);
			if (world.getBlockState(pos2).getBlock() instanceof BlockTray) {
				if (isBlackList(world, pos2) != isBlackList)
					continue;
				ItemStackHandler inv = ((TileTray) world.getTileEntity(pos2)).inventory;
				for (int i = 0; i < 9; i++) {
					ItemStack slot = inv.getStackInSlot(i);
					if (Util.isValid(slot)) {
						filters.add(slot.getItem());
					}
				}
			}
		}
		return isBlackList;
	}

	private static boolean isBlackList(World world, BlockPos pos) {
		return world.getBlockState(pos).getValue(ISBLACK);
	}

	public static boolean isItemAble(HashSet<Item> filter, Item item, boolean isBlackList) {
		return filter.isEmpty() || (isBlackList != filter.contains(item));
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
		return new BlockStateContainer(this, FACING, ISBLACK);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.values()[meta & 7]).withProperty(ISBLACK, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = ((EnumFacing) state.getValue(FACING)).ordinal();
		if (state.getValue(ISBLACK))
			meta |= 8;
		return meta;
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
		return new String[] { "Small Item Tray that holds up to 9 Items.",
				"Also serves as a white list filter to gravity and setter blocks.",
				"Will change to a black filter when right clicked with an empty hand." };
	}

}
