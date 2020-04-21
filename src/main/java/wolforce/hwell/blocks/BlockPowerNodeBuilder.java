package wolforce.hwell.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.hwell.base.BlockWithDescription;
import wolforce.hwell.base.HasTE;
import wolforce.hwell.blocks.tile.TilePowerNodeBuilder;

public class BlockPowerNodeBuilder extends Block implements HasTE, BlockWithDescription {

	private final static double F = 1.0 / 16.0;
	protected static final AxisAlignedBB aabb = new AxisAlignedBB(7 * F, 0.0D, 7 * F, 9 * F, 1.0D, 9 * F);

	public BlockPowerNodeBuilder() {
		super(Material.WOOD);
		setHardness(.5f);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile != null && tile instanceof TilePowerNodeBuilder)
				((TilePowerNodeBuilder) tile).activated(state, player, hand);
			tile.markDirty();
		}
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile != null && tile instanceof TilePowerNodeBuilder)
				((TilePowerNodeBuilder) tile).broken(state);
		}
		super.breakBlock(world, pos, state);
	}

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
		return new TilePowerNodeBuilder();
	}

	// BLOCK STATES

	@Override
	public String[] getDescription() {
		return new String[] { "Used to create Power Nodes." };
	}

}
