package wolforce.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import mezz.jei.recipes.RecipeRegistry;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import wolforce.Util;

public class RecipeSeparator {

	private static HashMap<Irio, RecipeSeparator> recipes;

	public static void initRecipes() {
		recipes = new HashMap<>();
		// RecipeSeparator a = new Gson().fromJson("", RecipeSeparator.class);

		try {
			readRecipes("recipes_separator");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readRecipes(String filename) throws IOException {
		JsonArray recipes = Util.readJson("hwell:" + filename + ".json", true).getAsJsonArray();
		for (JsonElement e : recipes) {
			readRecipe(e.getAsJsonObject());
		}
		JsonArray recipes2 = Util.readJson("customrecipes.json", false).getAsJsonArray();
		for (JsonElement e : recipes2) {
			readRecipe(e.getAsJsonObject());
		}
	}

	private static void readRecipe(JsonObject o) {
		ItemStack input = ShapedRecipes.deserializeItem(o.get("input").getAsJsonObject(), true);
		ItemStack output1 = ShapedRecipes.deserializeItem(o.get("output1").getAsJsonObject(), true);
		ItemStack output2 = ShapedRecipes.deserializeItem(o.get("output2").getAsJsonObject(), true);
		if (!o.has("output3")) {
			put(new Irio(input), new RecipeSeparator(output1, output2));
		} else {
			ItemStack output3 = ShapedRecipes.deserializeItem(o.get("output3").getAsJsonObject(), true);
			put(new Irio(input), new RecipeSeparator(output1, output2, output3));
		}
	}

	private static void put(Irio stack, RecipeSeparator recipeGrinder) {
		recipes.put(stack, recipeGrinder);
	}

	public static Set<Entry<Irio, RecipeSeparator>> getRecipes() {
		return recipes.entrySet();
	}

	//

	//

	public static ItemStack[] getResult(ItemStack itemStack) {
		RecipeSeparator result = recipes.get(new Irio(itemStack.getItem()));
		if (result != null)
			return new ItemStack[] { result.left, result.right, result.back };
		result = recipes.get(new Irio(itemStack.getItem(), itemStack.getMetadata()));
		if (result != null)
			return new ItemStack[] { result.left, result.right, result.back };
		return null;
	}

	public final ItemStack left, right, back;

	public RecipeSeparator(ItemStack left, ItemStack right, ItemStack back) {
		this.left = left;
		this.right = right;
		this.back = back;
	}

	public RecipeSeparator(ItemStack left, ItemStack right) {
		this(left, right, ItemStack.EMPTY);
	}

}
