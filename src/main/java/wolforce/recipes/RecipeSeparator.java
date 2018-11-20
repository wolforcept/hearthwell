package wolforce.recipes;

import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import wolforce.blocks.BlockPrecisionGrinder;
import wolforce.items.ItemGrindingWheel;

public class RecipeSeparator {

	private static HashMap<Iri, RecipeSeparator> recipes;

	public static void initRecipes() {
		recipes = new HashMap<>();
		put(new Iri(Items.MAGMA_CREAM), new RecipeSeparator(new ItemStack(Items.SLIME_BALL, 1), new ItemStack(Items.BLAZE_POWDER, 1)));
		put(new Iri(Blocks.MAGMA), new RecipeSeparator(new ItemStack(Items.SLIME_BALL, 4), new ItemStack(Items.BLAZE_POWDER, 4)));
	}

	private static void put(Iri stack, RecipeSeparator recipeGrinder) {
		recipes.put(stack, recipeGrinder);
	}

	//

	//

	public static ItemStack[] getResult(ItemStack itemStack) {
		RecipeSeparator result = recipes.get(new Iri(itemStack.getItem(), itemStack.getMetadata()));
		if (result != null)
			return new ItemStack[] { result.left, result.right };
		return null;
	}

	private ItemStack left, right;

	public RecipeSeparator(ItemStack left, ItemStack right) {
		this.left = left;
		this.right = right;
	}

}
