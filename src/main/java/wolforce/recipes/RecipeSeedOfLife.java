package wolforce.recipes;

import java.util.Iterator;
import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import wolforce.Util;

public class RecipeSeedOfLife {

	public static LinkedList<ItemStack> blocks;

	public static void initRecipes(JsonArray recipesJson) {
		blocks = new LinkedList<>();
		for (JsonElement e : recipesJson) {
			ItemStack stack = ShapedRecipes.deserializeItem(e.getAsJsonObject(), false);
			blocks.add(stack);
		}
	}

	public static void removeRecipe(ItemStack input) {
		for (Iterator<ItemStack> iterator = blocks.iterator(); iterator.hasNext();) {
			ItemStack block = (ItemStack) iterator.next();
			if (Util.equalExceptAmount(block, input))
				iterator.remove();
		}
	}

	private static boolean getResult(ItemStack stack1) {
		for (ItemStack stack2 : blocks) {
			if (Util.equalExceptAmount(stack1, stack2))
				return true;
		}
		return false;
	}

	public static boolean getResult(Block block, int metaFromState) {
		return getResult(new ItemStack(block, 1, metaFromState));
	}

}
