package wolforce.hearthwell.bases;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.ArrayList;
import java.util.List;

public class BaseFallingBlock extends FallingBlock implements BlockItemProperties {

	public BaseFallingBlock(Properties properties) {
		super(properties);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(this));
		return drops;
	}

}
