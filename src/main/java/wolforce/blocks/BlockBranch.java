package wolforce.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.Util;
import wolforce.base.HasTE;
import wolforce.base.MyBlock;
import wolforce.blocks.tile.TileBranch;
import wolforce.items.ItemBranch;

public class BlockBranch extends MyBlock implements HasCustomItem, HasTE {

	private static final float f = 1f / 16f;
	private static final AxisAlignedBB colbox = new AxisAlignedBB(f * 6, 0, f * 6, f * 10, 1, f * 10);

	public BlockBranch(String name) {
		super(name, Material.WOOD);
		setHarvest("axe", -1);
		setHardness(1);
		setResistance(2);
	}

	@Override
	public ItemBlock getCustomItem() {
		return Main.branch_item;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			ItemStack stack = new ItemStack(Main.branch_item);

			if (tile != null && tile instanceof TileBranch) {
				long time = ((TileBranch) tile).time;
				if (ItemBranch.getLife(time) > 0) {
					stack.setTagCompound(new NBTTagCompound());
					ItemBranch.setNBT(stack.getTagCompound(), time);
				}
			}
			Util.spawnItem(world, pos, stack);
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	//

	//

	// VISUALS

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
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
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return colbox;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return colbox;
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

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileBranch();
	}
}
