package wolforce.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.Util;
import wolforce.items.ItemDustPicker;
import wolforce.tile.TileCore;

public class BlockPickingTable extends Block {

	private final static double F = 1.0 / 16.0;
	protected static final AxisAlignedBB aabb = new AxisAlignedBB(F, 0, F, 1.0 - F, 11.0 * F, 1.0 - F);
	public static final PropertyInteger FILLING = PropertyInteger.create("filling", 0, 3);

	public BlockPickingTable(String name) {
		super(Material.WOOD);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(2);
		setHarvestLevel("axe", -1);
		setDefaultState(getDefaultState().withProperty(FILLING, 0));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand enumhand, EnumFacing facing,
			float hitX, float hitY, float hitZ) {

		ItemStack hand = playerIn.getHeldItem(enumhand);

		if (state.getValue(FILLING) == 0) {
			if (hand.getItem() == Main.myst_dust) {
				world.setBlockState(pos, getDefaultState().withProperty(FILLING, 3));
				if (!playerIn.isCreative())
					playerIn.getHeldItem(enumhand).shrink(1);
				return true;
			}
		} else if (hand.getItem() instanceof ItemDustPicker) {
			hand.damageItem(1, playerIn);
			if (!world.isRemote)
				Util.spawnItem(world, pos, new ItemStack(((ItemDustPicker) hand.getItem()).shard, 1));
			if (state.getValue(FILLING) == 3)
				world.setBlockState(pos, getDefaultState().withProperty(FILLING, 2));
			if (state.getValue(FILLING) == 2)
				world.setBlockState(pos, getDefaultState().withProperty(FILLING, 1));
			if (state.getValue(FILLING) == 1)
				world.setBlockState(pos, getDefaultState().withProperty(FILLING, 0));
			return true;
		}
		return false;
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
}
