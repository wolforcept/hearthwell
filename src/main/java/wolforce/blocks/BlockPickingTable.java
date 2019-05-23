package wolforce.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.Util;
import wolforce.base.HasTE;
import wolforce.blocks.tile.TilePickingTable;
import wolforce.items.tools.ItemDustPicker;

public class BlockPickingTable extends Block implements HasTE {

	private final static double F = 1.0 / 16.0;
	protected static final AxisAlignedBB aabb = new AxisAlignedBB(F, 0, F, 1.0 - F, 11.0 * F, 1.0 - F);
	public static final PropertyInteger FILLING = PropertyInteger.create("filling", 0, 3);

	public BlockPickingTable(String name) {
		super(Material.WOOD);
		Util.setReg(this, name);
		setHardness(2);
		setHarvestLevel("axe", -1);
		setDefaultState(getDefaultState().withProperty(FILLING, 0));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand enumhand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {

		ItemStack hand = playerIn.getHeldItem(enumhand);

		if (state.getValue(FILLING) == 0) {
			if (hand.getItem() == Main.myst_dust) {
				world.setBlockState(pos, getDefaultState().withProperty(FILLING, 3));
				if (!playerIn.isCreative())
					playerIn.getHeldItem(enumhand).shrink(1);
				return true;
			}
		} else if (hand.getItem() instanceof ItemDustPicker) {
			if (state.getValue(FILLING) == 0) {
				return false;
			}

			hand.damageItem(1, playerIn);
			if (!world.isRemote && Math.random() < HwellConfig.other.pickingTableChance)
				Util.spawnItem(world, pos, new ItemStack(((ItemDustPicker) hand.getItem()).shard, 1));
			IBlockState newstate = reduce(state);
			world.setBlockState(pos, newstate);
			return true;
		}
		return false;
	}

	public static IBlockState reduce(IBlockState state) {
		if (state.getValue(FILLING) == 3)
			return state.withProperty(FILLING, 2);
		if (state.getValue(FILLING) == 2)
			return state.withProperty(FILLING, 1);
		if (state.getValue(FILLING) == 1)
			return state.withProperty(FILLING, 0);
		return state;
	}

	public static IBlockState increase(IBlockState state) {
		if (state.getValue(FILLING) == 0)
			return state.withProperty(FILLING, 3);
		return state;
	}

	// @Override
	// public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
	// return layer == BlockRenderLayer.TRANSLUCENT;
	// }

	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return aabb;
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return aabb;
	}

	// BLOCK STATES

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FILLING);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FILLING, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FILLING);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePickingTable();
	}
}
