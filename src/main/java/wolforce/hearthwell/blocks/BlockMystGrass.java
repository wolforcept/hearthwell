package wolforce.hearthwell.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.bases.BaseBlock;

import java.util.ArrayList;
import java.util.List;

public class BlockMystGrass extends BaseBlock {

	private static final Direction[] horizontals = { Direction.EAST, Direction.NORTH, Direction.WEST, Direction.SOUTH };

	public BlockMystGrass(Properties properties) {
		super(properties);
	}

	@Override
	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (world instanceof ServerLevel && !canBeGrass((ServerLevel) world, pos))
			world.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(Blocks.DIRT));
		return drops;
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return true;
	}

	private boolean canBeGrass(ServerLevel world, BlockPos pos) {
		BlockState up = world.getBlockState(pos.above());
		return world.isEmptyBlock(pos.above()) || up.useShapeForLightOcclusion() /* || !up.isOpaqueCube(world, pos) */;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {

		if (!canBeGrass(world, pos))
			world.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());

		trySpread(world, pos, random);

		if (isNearBushes(world, pos.above()) && random.nextFloat() < .5)
			return;
		if (world.isEmptyBlock(pos.above())) {
			world.setBlockAndUpdate(pos.above(), HearthWell.myst_bush_small.get().defaultBlockState());
		} else if (world.getBlockState(pos.above()).getBlock() == HearthWell.myst_bush_small.get()) {
			world.setBlockAndUpdate(pos.above(), HearthWell.myst_bush.get().defaultBlockState());
		}
	}

	int[][] nearPositions = { //
			{ -1, -1, -1 }, { -1, -1, 0 }, { -1, -1, 1 }, //
			{ 0, -1, -1 }, { 0, -1, 0 }, { 0, -1, 1 }, //
			{ 1, -1, -1 }, { 1, -1, 0 }, { 1, -1, 1 },

			{ -1, 0, -1 }, { -1, 0, 0 }, { -1, 0, 1 }, //
			{ 0, 0, -1 }, { 0, 0, 1 }, //
			{ 1, 0, -1 }, { 1, 0, 0 }, { 1, 0, 1 },

			{ -1, 1, -1 }, { -1, 1, 0 }, { -1, 1, 1 }, //
			{ 0, 1, -1 }, { 0, 1, 0 }, { 0, 1, 1 }, //
			{ 1, 1, -1 }, { 1, 1, 0 }, { 1, 1, 1 } //
	};

	private void trySpread(ServerLevel world, BlockPos _pos, RandomSource random) {
		int[] nearPos = nearPositions[random.nextInt(nearPositions.length)];
		BlockPos pos = _pos.offset(nearPos[0], nearPos[1], nearPos[2]);
		if (world.getBlockState(pos).getBlock() == Blocks.DIRT || world.getBlockState(pos).getBlock() == Blocks.GRASS_BLOCK)
			if (canBeGrass(world, pos))
				world.setBlockAndUpdate(pos, HearthWell.myst_grass.get().defaultBlockState());
	}

	private boolean isNearBushes(ServerLevel world, BlockPos pos) {
		for (Direction direction : horizontals) {
			Block block = world.getBlockState(pos.relative(direction)).getBlock();
			if (block == HearthWell.myst_bush.get() || block == HearthWell.myst_bush_small.get())
				return true;
		}
		return false;
	}

}
