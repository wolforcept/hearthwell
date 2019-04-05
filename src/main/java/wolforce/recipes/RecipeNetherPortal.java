package wolforce.recipes;

import java.util.HashSet;
import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
			ItemStack output = ShapedRecipes.deserializeItem(o.get("output1").getAsJsonObject(), true);
			recipes.add(new RecipeNetherPortal(input, output));
		}
	}

	public static ItemStack getOutput(ItemStack item) {
		return null;
	}

	private final ItemStack input;
	private final ItemStack output;

	public RecipeNetherPortal(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}
}
