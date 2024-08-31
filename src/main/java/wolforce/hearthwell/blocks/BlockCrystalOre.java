package wolforce.hearthwell.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.bases.BaseBlock;
import wolforce.hearthwell.bases.BlockHasRenderLayer;

import java.util.ArrayList;
import java.util.List;

public class BlockCrystalOre extends BaseBlock implements BlockHasRenderLayer.Translucent {

	public final boolean isBlack;

	public BlockCrystalOre(Properties properties, boolean isBlack) {
		super(properties);
		this.isBlack = isBlack;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		List<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(HearthWell.crystal.get(), Math.random() < 0.25 ? 2 : 1));
		if (isBlack)
			drops.add(new ItemStack(HearthWell.petrified_wood_chunk.get(), 2 + (int) (Math.random() * 3)));
		else
			drops.add(new ItemStack(Blocks.COBBLESTONE, 1));
		return drops;
	}

}
