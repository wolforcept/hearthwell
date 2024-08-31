package wolforce.hearthwell.blocks.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import wolforce.hearthwell.registries.TileEntities;

public class TeFertileSoil extends BlockEntity {

	public TeFertileSoil(BlockPos worldPosition, BlockState blockState) {
		super(TileEntities.fertile_soil.get(), worldPosition, blockState);
	}

	public void tick(Level level, BlockPos pos, BlockState blockState) {

		if (level.isClientSide || !(level instanceof ServerLevel))
			return;

		BlockPos up = worldPosition.above();

		if (level.isEmptyBlock(up) || Math.random() > 0.025)
			return;

		BlockState bs = level.getBlockState(up);
		Block block = bs.getBlock();

		if (block instanceof BonemealableBlock) {
			Block prevTopBlock = level.getBlockState(pos.above()).getBlock();
			((BonemealableBlock) block).performBonemeal((ServerLevel) level, level.random, up, bs);
			if (prevTopBlock != level.getBlockState(pos.above()).getBlock()) {
				SoundEvent sound = level.getBlockState(pos.above()).getSoundType().getPlaceSound();
				this.level.playSound(null, pos, sound, SoundSource.BLOCKS, 10000.0F, 1);
			}
		}
	}

}
