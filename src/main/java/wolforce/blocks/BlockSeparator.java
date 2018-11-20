package wolforce.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.Main;
import wolforce.Util;
import wolforce.tile.TileSeparator;

public class BlockSeparator extends Block implements ITileEntityProvider {

	private final static double F = 1.0 / 16.0;
	protected static final AxisAlignedBB aabb = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 4.0 * F, 1.0D);

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);

	public BlockSeparator(String name) {
		super(Main.material_soulsteel);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX,
			float hitY, float hitZ) {
		if (!world.isRemote) {
			TileSeparator tile = (TileSeparator) world.getTileEntity(pos);
			IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
			ItemStack held = player.getHeldItem(hand);
			if (!player.isSneaking()) {
				if (itemHandler.getStackInSlot(0).equals(ItemStack.EMPTY)) {
					// TA VAZIO
					if (!held.isEmpty()) {
						// VAMOS POR
						itemHandler.insertItem(0, new ItemStack(held.getItem(), 1, held.getMetadata()), false);
						held.shrink(1);
						tile.markDirty();
					}
				} else {
					ItemStack itemToTake = itemHandler.extractItem(0, 64, false);
					tile.markDirty();
					// NAO TA VAZIO
					if (held.isEmpty()) { // NAO TEM NADA NA MAO
						// VAMOS TIRAR
						player.setHeldItem(hand, itemToTake);

					} else if (held.getItem().equals(itemToTake.getItem()) && held.getMetadata() == itemToTake.getMetadata()
							&& held.getCount() < held.getItem().getItemStackLimit(held)) {
						held.grow(1);
					} else {
						Util.spawnItem(world, pos, itemToTake);
					}
				}
			}
			tile.markDirty();
			// } else {
			// ItemStack stack = itemHandler.getStackInSlot(0);
			// if (!stack.isEmpty()) {
			// String localized = TutorialMod.proxy.localize(stack.getUnlocalizedName() +
			// ".name");
			// player.addChatMessage(new TextComponentString(stack.getCount() + "x " +
			// localized));
			// } else {
			// player.addChatMessage(new TextComponentString("Empty"));
			// }
			// }
		}
		return true;

	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileSeparator tile = (TileSeparator) world.getTileEntity(pos);
		IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		ItemStack item = itemHandler.getStackInSlot(0);
		if (!item.isEmpty())
			Util.spawnItem(world, pos, item);
		super.breakBlock(world, pos, state);
	}

	//

	//

	//
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
			EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
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

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return this == Main.precision_grinder_crystal || this == Main.precision_grinder_crystal_nether ? //
				layer == BlockRenderLayer.TRANSLUCENT : super.canRenderInLayer(state, layer);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	//

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileSeparator();
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
