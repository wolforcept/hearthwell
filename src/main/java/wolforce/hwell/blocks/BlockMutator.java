package wolforce.hwell.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
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
import net.minecraftforge.items.ItemStackHandler;
import wolforce.hwell.HwellConfig;
import wolforce.hwell.base.BlockEnergyConsumer;
import wolforce.hwell.base.HasTE;
import wolforce.hwell.blocks.tile.TileMutator;
import wolforce.mechanics.Util;

public class BlockMutator extends Block implements BlockEnergyConsumer, HasTE {

	private final static double F = 1.0 / 16.0;
	protected static final AxisAlignedBB aabb = new AxisAlignedBB(F, 0, F, 1f - F, 1f - F, 1f - F);

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);

	public BlockMutator() {
		super(Material.ROCK);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {

			TileMutator tile = (TileMutator) world.getTileEntity(pos);
			ItemStackHandler itemHandler = tile.inventory;
			ItemStack held = player.getHeldItem(hand);
			if (!player.isSneaking()) {

				// if ((side == state.getValue(FACING) && hitY < .33)) {
				//
				// clicked(world, pos, tile);
				//
				// } else
				if (side != state.getValue(FACING).getOpposite() && side != EnumFacing.DOWN) {

					if (!Util.isValid(itemHandler.getStackInSlot(0))) {
						// TA VAZIO
						if (!held.isEmpty())
							player.setHeldItem(hand, tile.inventory.insertItem(0, held, false));

					} else {
						tile.tryUsePaste();
						ItemStack itemToTake = itemHandler.extractItem(0, 64, false);
						// NAO TA VAZIO
						if (held.isEmpty()) { // NAO TEM NADA NA MAO
							// VAMOS TIRAR E POR NA MAO
							player.setHeldItem(hand, itemToTake);
						} else {
							// VAMOS TIRAR E DEITAR PO CHAO
							Util.spawnItem(world, pos, itemToTake);
						}
					}
				}
			}
			tile.markDirty();
		}
		return true;

	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote) {
			if (worldIn.isBlockIndirectlyGettingPowered(fromPos) > 0)
				// worldIn.isSidePowered(pos, Util.sideOf(pos, fromPos)))
				clicked(worldIn, pos, (TileMutator) worldIn.getTileEntity(pos));
		}
	}

	private void clicked(World world, BlockPos pos, TileMutator tile) {
		if (System.currentTimeMillis() > tile.nextAvailableUpdateMinTime //
				&& Util.isValid(tile.inventory.getStackInSlot(0)) //
		) {
			boolean gotPower = BlockEnergyConsumer.tryConsume(world, pos, HwellConfig.machines.mutatorPowerConsumption);
			if (gotPower)
				tile.tryMutate();
			tile.nextAvailableUpdateMinTime = System.currentTimeMillis();
		}
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileMutator tile = (TileMutator) world.getTileEntity(pos);
		ItemStack item = tile.inventory.extractItem(0, 64, false);
		if (!item.isEmpty())
			Util.spawnItem(world, pos, item);
		super.breakBlock(world, pos, state);
	}

	//

	//

	// BLOCK VISUALS

	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
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
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isTopSolid(IBlockState state) {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return side != EnumFacing.UP && side != world.getBlockState(pos).getValue(FACING);
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
		EnumFacing facing = world.getBlockState(pos).getValue(FACING);
		if (face == EnumFacing.UP || face == facing)
			return BlockFaceShape.UNDEFINED;
		return BlockFaceShape.SOLID;
	}

	@Override
	public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return true;
	}

	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return aabb;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return aabb;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return aabb;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	// @Override
	// public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
	// return layer == BlockRenderLayer.TRANSLUCENT;
	// }
	//

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileMutator();
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
		return new String[] { "Mutates items into other similar items.",
				"Consumes " + getEnergyConsumption() + " energy per operation." };
	}

	@Override
	public int getEnergyConsumption() {
		return HwellConfig.machines.mutatorPowerConsumption;
	}

}
