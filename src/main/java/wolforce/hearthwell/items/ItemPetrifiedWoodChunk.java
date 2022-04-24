package wolforce.hearthwell.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class ItemPetrifiedWoodChunk extends Item {

	public ItemPetrifiedWoodChunk(Properties properties) {
		super(properties);
	}

	@Override
	public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
		return 200;
	}
}
