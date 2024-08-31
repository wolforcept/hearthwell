package wolforce.hearthwell.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.IPlantable;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.bases.BaseBlock;
import wolforce.hearthwell.bases.BlockHasRenderLayer;

import java.util.ArrayList;
import java.util.List;

public class BlockMystBush extends BaseBlock implements IForgeShearable, IPlantable, BlockHasRenderLayer.Cutout {

	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
	private boolean isBig;

	public BlockMystBush(boolean isBig, Properties props) {
		super(props);
		this.isBig = isBig;
	}

	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	public BlockBehaviour.OffsetType getOffsetType() {
		return BlockBehaviour.OffsetType.XZ;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		List<ItemStack> drops = new ArrayList<>();
		int amount = isBig ? 1 + (int) (Math.random() * 3) : (int) (Math.random() * 2);
		drops.add(new ItemStack(HearthWell.myst_dust.get(), amount));
		return drops;
	}

//	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
//		return state.isIn(HearthWell.myst_grass);
//	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
		return true;
	}

	@Override
	public boolean canBeReplaced(BlockState state, Fluid fluid) {
		return true;
	}

	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	/**
	 * Update the provided state given the provided neighbor facing and neighbor
	 * state, returning a new state. For example, fences make their connections to
	 * the passed in state if possible, and wet concrete powder immediately returns
	 * its solidified counterpart. Note that this method should ideally consider
	 * only the specific face passed in.
	 */
	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos,
			BlockPos facingPos) {
		return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		return worldIn.getBlockState(blockpos).getBlock() == HearthWell.myst_grass.get();
	}

	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@SuppressWarnings("deprecation")
	public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
		return type == PathComputationType.AIR && !this.hasCollision || super.isPathfindable(state, worldIn, pos, type);
	}

	@Override
	public BlockState getPlant(BlockGetter world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() != this)
			return defaultBlockState();
		return state;
	}
}
