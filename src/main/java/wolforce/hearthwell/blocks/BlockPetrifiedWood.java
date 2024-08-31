package wolforce.hearthwell.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.bases.BaseBlock;

import java.util.ArrayList;
import java.util.List;

public class BlockPetrifiedWood extends BaseBlock {

	public BlockPetrifiedWood(Properties properties) {
		super(properties);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(HearthWell.petrified_wood_chunk.get(), (int) (3 + Math.random() * 4)));
		return drops;
	}

	
	
}
