package wolforce.hearthwell.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import wolforce.hearthwell.bases.BaseBlock;
import wolforce.hearthwell.blocks.tiles.TeFertileSoil;

public class BlockFertileSoil extends BaseBlock implements EntityBlock {

	public BlockFertileSoil(Properties props) {
		super(props);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TeFertileSoil(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level _level, BlockState _blockState, BlockEntityType<T> type) {
		return (Level level, BlockPos pos, BlockState blockState, T te) -> {
			if (te instanceof TeFertileSoil)
				((TeFertileSoil) te).tick(level, pos, blockState);
		};
	}
}
