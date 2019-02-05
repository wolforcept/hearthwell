package wolforce.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
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
import wolforce.blocks.tile.TilePickerHolder;
import wolforce.blocks.tile.TileSeparator;
import wolforce.items.tools.ItemDustPicker;

public class BlockPickerHolder extends Block implements ITileEntityProvider {

	private final static double F = 1.0 / 16.0;
	protected static final AxisAlignedBB aabb = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 16 * F, 16 * F, 16 * F);

	public BlockPickerHolder(String name) {
		super(Material.WOOD);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand handnomatter,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote || handnomatter == EnumHand.OFF_HAND)
			return true;
		TilePickerHolder tile = (TilePickerHolder) world.getTileEntity(pos);
		ItemStack stackHeld = player.getHeldItemMainhand();

		if (stackHeld.equals(ItemStack.EMPTY) && !tile.isEmpty() && !player.isSneaking()) {
			// has nothing in hand, takes the first picker
			player.setHeldItem(EnumHand.MAIN_HAND, tile.takeNext(0));
			tile.markDirty();
			return true;
		}

		// has something that is not a picker
		if (!Util.isValid(stackHeld) || !(stackHeld.getItem() instanceof ItemDustPicker))
			return false;

		if (player.isSneaking()) {
			// sneaking = JUST INSERT
			if (tile.canInsert(stackHeld)) {
				tile.insert(stackHeld);
				player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
				tile.markDirty();
				return true;
			}
		} else {
			// not sneaking = SWAP WITH THE NEXT
			if (tile.canInsert(stackHeld) && !tile.isEmpty()) {
				player.setHeldItem(EnumHand.MAIN_HAND, tile.swap(stackHeld));
				tile.markDirty();
				return true;
			}
		}

		return false;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		for (int i = 0; i < TilePickerHolder.nSlots; i++) {
			ItemStack stack = ((TilePickerHolder) world.getTileEntity(pos)).inventory.extractItem(i, 1, false);
			Util.spawnItem(world, pos, stack);
		}
		super.breakBlock(world, pos, state);
	}

	//

	//

	//

	//

	//

	// BLOCK VISUALS

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

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return aabb;
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return aabb;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	//

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePickerHolder();
	}

}
