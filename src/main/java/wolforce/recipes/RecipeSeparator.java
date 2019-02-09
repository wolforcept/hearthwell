package wolforce.recipes;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;

//@ZenClass("mods.hwell.separator")
//@ZenRegister
public class RecipeSeparator {

	private static HashMap<Irio, RecipeSeparator> recipes;

	public static void initRecipes(JsonArray recipesJson) {
		recipes = new HashMap<>();
		for (JsonElement e : recipesJson) {
			readAndAddRecipe(e.getAsJsonObject());
		}
	}

	private static void readAndAddRecipe(JsonObject o) {
		ItemStack input = ShapedRecipes.deserializeItem(o.get("input").getAsJsonObject(), true);
		ItemStack output1 = ShapedRecipes.deserializeItem(o.get("output1").getAsJsonObject(), true);
		ItemStack output2 = ShapedRecipes.deserializeItem(o.get("output2").getAsJsonObject(), true);
		if (!o.has("output3")) {
			addRecipe(new Irio(input), new RecipeSeparator(output1, output2));
		} else {
			ItemStack output3 = ShapedRecipes.deserializeItem(o.get("output3").getAsJsonObject(), true);
			addRecipe(new Irio(input), new RecipeSeparator(output1, output2, output3));
		}
	}

	// @ZenMethod
	public static void addRecipe(ItemStack input, ItemStack output1, ItemStack output2, ItemStack output3) {
		addRecipe(new Irio(input), new RecipeSeparator(output1, output2, output3));
	}

	// @ZenMethod
	public static void addRecipe(ItemStack input, ItemStack output1, ItemStack output2) {
		addRecipe(new Irio(input), new RecipeSeparator(output1, output2));
	}

	private static void addRecipe(Irio stack, RecipeSeparator recipeGrinder) {
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
