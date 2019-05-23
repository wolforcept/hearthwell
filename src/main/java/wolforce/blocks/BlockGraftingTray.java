package wolforce.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.Main;
import wolforce.Util;
import wolforce.base.BlockWithDescription;
import wolforce.base.HasTE;
import wolforce.base.MyBlock;
import wolforce.blocks.tile.TileGraftingTray;

public class BlockGraftingTray extends MyBlock implements HasTE, BlockWithDescription {

	private static final double F = 1.0 / 16.0;

	protected static final AxisAlignedBB colbox = new AxisAlignedBB(0, 0, 0, 1, 3 * F, 1);

	public static final PropertyBool FILLED = PropertyBool.create("filled");

	public BlockGraftingTray(String name) {
		super(name, Material.ROCK);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
		setDefaultState(this.blockState.getBaseState().withProperty(FILLED, false));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {

		ItemStack handstack = playerIn.getHeldItem(hand);

		// DEAL WITH BUCKET

		if (handstack.getItem() == Items.BUCKET || handstack.getItem() == ForgeModContainer.getInstance().universalBucket) {
			ItemStack bucket_filled = FluidUtil.getFilledBucket(new FluidStack(Main.liquid_souls, Fluid.BUCKET_VOLUME));
			ItemStack bucket_empty = new ItemStack(Items.BUCKET);
			if (!state.getValue(FILLED) && Util.equalNBT(handstack, bucket_filled)) {
				if (!world.isRemote)
					changeFilled(world, pos, true);
				playerIn.setHeldItem(hand, bucket_empty);
				return true;
			}

			if (state.getValue(FILLED) && Util.equalNBT(handstack, bucket_empty)) {
				if (!world.isRemote)
					changeFilled(world, pos, false);
				playerIn.setHeldItem(hand, bucket_filled);
				return true;
			}
			return false;
		}

		// DEAL WITH CORES

		TileGraftingTray tile = (TileGraftingTray) world.getTileEntity(pos);

		if (!Util.isValid(tile.getStack()) && BlockCore.isCore(handstack)) {
			if (!world.isRemote) {
				tile.setCoreStack(handstack);
				tile.markDirty();
			}
			handstack.shrink(1);
			return true;
		}

		if (!world.isRemote) {
			ItemStack pop = tile.pop();
			Util.spawnItem(world, pos, pop);
			tile.markDirty();
		}
		return true;
	}

	public static void changeFilled(World world, BlockPos pos, boolean filled) {
		ItemStackHandler oldInv = ((TileGraftingTray) world.getTileEntity(pos)).inventory;
		world.setBlockState(pos, Main.grafting_tray.getDefaultState().withProperty(FILLED, filled));
		((TileGraftingTray) world.getTileEntity(pos)).inventory = oldInv;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		super.getDrops(drops, world, pos, state, fortune);
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileGraftingTray)
			drops.add(((TileGraftingTray) te).inventory.getStackInSlot(0));
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

	//

	//

	// BLOCK STATES

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FILLED);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FILLED, meta != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FILLED) ? 1 : 0;
	}

	//

	//

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileGraftingTray();
	}

	@Override
	public String[] getDescription() {
		return new String[] {
				"Allows the production of Core Grafts. Fill with water and a core, then place Living Branches around it." };
	}

}
