package wolforce.hwell.recipes;

import java.util.Iterator;
import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import wolforce.mechanics.Util;

public class RecipeSeparator {

	public static LinkedList<RecipeSeparator> recipes;

	public static void initRecipes(JsonArray recipesJson) {
		recipes = new LinkedList<>();
		for (JsonElement e : recipesJson) {
			readAndAddRecipe(e.getAsJsonObject());
		}
	}

	private static void readAndAddRecipe(JsonObject o) {
		ItemStack input = ShapedRecipes.deserializeItem(o.get("input").getAsJsonObject(), true);
		ItemStack output1 = ShapedRecipes.deserializeItem(o.get("output1").getAsJsonObject(), true);
		ItemStack output2 = ShapedRecipes.deserializeItem(o.get("output2").getAsJsonObject(), true);
		if (!o.has("output3")) {
			recipes.add(new RecipeSeparator(input, output1, output2));
		} else {
			ItemStack output3 = ShapedRecipes.deserializeItem(o.get("output3").getAsJsonObject(), true);
			recipes.add(new RecipeSeparator(input, output1, output2, output3));
		}
	}

	public static void removeRecipe(ItemStack input) {
		for (Iterator<RecipeSeparator> iterator = recipes.iterator(); iterator.hasNext();) {
			RecipeSeparator recipe = (RecipeSeparator) iterator.next();
			if (Util.equalExceptAmount(recipe.input, input))
				iterator.remove();
		}
	}

	public static ItemStack[] getResult(ItemStack itemStack) {

		for (RecipeSeparator recipe : recipes) {
			if (Util.equalExceptAmount(recipe.input, itemStack)) {
				return new ItemStack[] { recipe.left.copy(), recipe.right.copy(), recipe.back.copy() };
			}
		}
		return null;

	}

	public final ItemStack input, left, right, back;

	public RecipeSeparator(ItemStack input, ItemStack left, ItemStack right, ItemStack back) {
		this.input = input;
		this.left = left;
		this.right = right;
		this.back = back;
	}

	public RecipeSeparator(ItemStack input, ItemStack left, ItemStack right) {
		this(input, left, right, ItemStack.EMPTY);
	}

}
