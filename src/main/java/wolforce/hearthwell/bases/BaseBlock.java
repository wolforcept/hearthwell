package wolforce.hearthwell.bases;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.ArrayList;
import java.util.List;

public class BaseBlock extends Block implements BlockItemProperties {

	public BaseBlock(Properties properties) {
		super(properties);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(this));
		return drops;
	}

}
