package wolforce.hearthwell.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wolforce.hearthwell.bases.BaseBlock;
import wolforce.hearthwell.bases.BlockHasRenderLayer;

public class BlockCore extends BaseBlock implements BlockHasRenderLayer.Translucent {

	public BlockCore(Properties properties) {
		super(properties);
//		Arrays.stream(drops).map(BlockCore::getRandomDrop).collect(Collectors.toList());
	}

	private static final double F = 1.0 / 16.0;
	private static final VoxelShape shape = Shapes.box(2 * F, 0, 2 * F, (16 - 2) * F, (16 - 4) * F, (16 - 2) * F);

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return shape;
	}

	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

//	@Override
//	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
//		return world.getBlockState(pos.below()).canOcclude();
//	}


	public static ItemStack getRandomDrop(ItemStack stack) {
		int n = (int) ((double) stack.getCount() / 2 + Math.random() * stack.getCount() / 2);
		return new ItemStack(stack.getItem(), n, stack.getTag());
	}

}
