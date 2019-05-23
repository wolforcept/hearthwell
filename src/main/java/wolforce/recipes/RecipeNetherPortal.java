package wolforce.recipes;

import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import wolforce.Util;

public class RecipeNetherPortal {

	public static LinkedList<RecipeNetherPortal> recipes;

	public static void initRecipes(JsonArray recipesJson) {
		recipes = new LinkedList<>();
		for (JsonElement e : recipesJson) {
			JsonObject o = e.getAsJsonObject();
			ItemStack input = ShapedRecipes.deserializeItem(o.get("input").getAsJsonObject(), true);
			ItemStack output = ShapedRecipes.deserializeItem(o.get("output").getAsJsonObject(), true);
			recipes.add(new RecipeNetherPortal(input, output));
		}
	}

	public static ItemStack getOutput(ItemStack in) {
		for (RecipeNetherPortal recipe : recipes) {
			if (Util.equalExceptAmount(recipe.input, in)) {
				return recipe.output;
			}
		}
		return null;
	}

	public final ItemStack input;
	private final ItemStack output;

	public RecipeNetherPortal(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}
}
