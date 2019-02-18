package wolforce.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.Util;
import wolforce.blocks.tile.TilePickerHolder;
import wolforce.blocks.tile.TileStatue;
import wolforce.items.tools.ItemDustPicker;

public class BlockStatue extends Block implements ITileEntityProvider {

	private final static double F = 1.0 / 16.0;
	private static final AxisAlignedBB colbox = new AxisAlignedBB(6 * F, 0, 6 * F, 10 * F, 12 * F, 10 * F);

	public BlockStatue(String name) {
		super(Material.WOOD);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
	}

	//

	// BLOCK VISUALS

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return colbox;
	}
	//
	// @Override
	// public boolean isFullBlock(IBlockState state) {
	// return false;
	// }
	//
	// @Override
	// public boolean isFullCube(IBlockState state) {
	// return false;
	// }
	//
	// @Override
	// public boolean isBlockNormalCube(IBlockState state) {
	// return false;
	// }
	//
	// public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source,
	// BlockPos pos) {
	// return colbox;
	// }
	//
	// @Override
	// public boolean isOpaqueCube(IBlockState state) {
	// return false;
	// }

	//

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileStatue();
	}

}
