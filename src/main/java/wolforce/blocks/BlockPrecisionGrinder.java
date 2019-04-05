package wolforce.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.base.BlockEnergyConsumer;
import wolforce.blocks.base.HasTE;
import wolforce.blocks.tile.TilePrecisionGrinder;
import wolforce.items.ItemGrindingWheel;

public class BlockPrecisionGrinder extends Block implements HasTE, BlockEnergyConsumer {

	private final static double F = 1.0 / 16.0;
	protected static final AxisAlignedBB aabb = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 12.0 * F, 1.0D);
	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);
	public ItemGrindingWheel grindingWheel;

	public BlockPrecisionGrinder(String name, ItemGrindingWheel grindingWheel) {
		super(Material.IRON);
		this.grindingWheel = grindingWheel;
		Util.setReg(this, name);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(new ItemStack(grindingWheel));
		drops.add(new ItemStack(Main.precision_grinder_empty));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {

		EnumFacing prevfacing = world.getBlockState(pos).getValue(FACING);

		Item held = player.getHeldItem(hand).getItem();
		IBlockState nextState = Main.precision_grinder_empty.getDefaultState();
		if (held instanceof ItemGrindingWheel) {
			if (held == Main.grinding_wheel_crystal)
				nextState = Main.precision_grinder_crystal.getDefaultState();
			if (held == Main.grinding_wheel_iron)
				nextState = Main.precision_grinder_iron.getDefaultState();
			if (held == Main.grinding_wheel_diamond)
				nextState = Main.precision_grinder_diamond.getDefaultState();
			player.setHeldItem(hand, ItemStack.EMPTY);
		}
		world.setBlockState(pos, nextState.withProperty(BlockPrecisionGrinder.FACING, prevfacing));
		if (!world.isRemote)
			Util.spawnItem(world, pos.offset(facing), new ItemStack(grindingWheel));
		return true;
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
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return aabb;
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return aabb;
	}

	// @Override
	// public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
	// return this == Main.precision_grinder_crystal || this ==
	// Main.precision_grinder_crystal_nether ? //
	// layer == BlockRenderLayer.TRANSLUCENT : super.canRenderInLayer(state, layer);
	// }

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	//

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePrecisionGrinder();
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
	public int getEnergyConsumption() {
		return HwellConfig.machines.grinderConsumption;
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Consumes " + getEnergyConsumption() + " energy per operation." };
	}
}
