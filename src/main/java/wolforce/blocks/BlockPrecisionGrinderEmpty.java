package wolforce.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.items.ItemGrindingWheel;
import wolforce.tile.TilePrecisionGrinder;

public class BlockPrecisionGrinderEmpty extends Block {

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);

	public BlockPrecisionGrinderEmpty(String name) {
		super(Material.IRON);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		Item heldItem = player.getHeldItem(hand).getItem();
		if (heldItem instanceof ItemGrindingWheel) {
			player.getHeldItem(hand).shrink(1);
			EnumFacing prevfacing = world.getBlockState(pos).getValue(FACING);
			world.setBlockState(pos, ((ItemGrindingWheel) heldItem).grinder.getDefaultState()
					.withProperty(BlockPrecisionGrinder.FACING, prevfacing));
			return true;
		}
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

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BlockPrecisionGrinder.aabb;
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return BlockPrecisionGrinder.aabb;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
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

}
