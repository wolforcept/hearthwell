package wolforce.hearthwell.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import wolforce.hearthwell.bases.BaseBlock;
import wolforce.hearthwell.blocks.tiles.TeBurstSeed;
import wolforce.hearthwell.data.MapData;
import wolforce.hearthwell.data.recipes.RecipeBurstSeed;
import wolforce.hearthwell.util.Util;

public class BlockBurstSeed extends BaseBlock implements EntityBlock {

	protected static final VoxelShape SHAPE = Block.box(6.5, 6.5, 6.5, 9.5, 9.5, 9.5);

	public BlockBurstSeed(Properties props) {
		super(props);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		if (isBurstRecipe(player.getItemInHand(hand)) || !Util.isValid(player.getItemInHand(InteractionHand.MAIN_HAND))) {
			if (!world.isClientSide) {
				TeBurstSeed te = (TeBurstSeed) world.getBlockEntity(pos);
				player.setItemInHand(hand, te.tryAddItem(player, player.getItemInHand(hand)));
				world.sendBlockUpdated(pos, state, state, 3);
			}
			return InteractionResult.CONSUME;
		}
		return InteractionResult.PASS;
	}

	private boolean isBurstRecipe(ItemStack stack) {
		for (RecipeBurstSeed recipe : MapData.DATA.recipes_burst) {
			if (recipe.matches(stack))
				return true;
		}
		return false;
	}

	//
	//
	//

	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level _level, BlockState _blockState, BlockEntityType<T> type) {
		return (Level level, BlockPos pos, BlockState blockState, T te) -> {
			if (te instanceof TeBurstSeed)
				((TeBurstSeed) te).tick(level, pos, blockState);
		};
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TeBurstSeed(pos, state);
	}
}
