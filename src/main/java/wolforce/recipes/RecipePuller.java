package wolforce.recipes;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import wolforce.HwellConfig;

public class RecipePuller {

	static LinkedList<RecipePuller> recipes;
	static HashSet<ItemStack> filters;

	public static void initRecipes(JsonArray recipesJson) {
		float totalProb = 0;
		for (JsonElement e : recipesJson) {
			totalProb += getProb(e.getAsJsonObject());
		}
		recipes = new LinkedList<>();
		filters = new HashSet<>();
		for (JsonElement e : recipesJson) {
			recipes.add(readRecipe(e.getAsJsonObject(), totalProb));
		}
	}

	private static float getProb(JsonObject o) {
		return o.get("probability").getAsFloat();
	}

	private static RecipePuller readRecipe(JsonObject o, float totalProb) {

		ItemStack output = ShapedRecipes.deserializeItem(o.get("output").getAsJsonObject(), true);
		double prob = o.get("probability").getAsFloat() / totalProb;
		ItemStack filter = ShapedRecipes.deserializeItem(o.get("filter").getAsJsonObject(), true);
		filters.add(filter);
		return new RecipePuller(output, prob, filter);
	}

	public static ItemStack getRandomPull(List<ItemStack> stacksInLiquid) {
		if (stacksInLiquid != null && !stacksInLiquid.isEmpty() && Math.random() < HwellConfig.pullerChanceToGetFilteredPull) {
			LinkedList<RecipePuller> preferredRecipes = new LinkedList<>();
			for (ItemStack stackInLiquid : stacksInLiquid) {
				for (RecipePuller recipe : recipes) {
					if (recipe.filter.getItem().equals(stackInLiquid.getItem()) && !preferredRecipes.contains(recipe)) {
						preferredRecipes.add(recipe);
					}
				}
			}
			if (!preferredRecipes.isEmpty()) {
				ItemStack randomPreferedStack = preferredRecipes.get((int) (preferredRecipes.size() * Math.random())).output.copy();
				randomPreferedStack.setCount(1 + (int) (Math.random() * 2));
				return randomPreferedStack;
			}
		}
		double rand = Math.random();
		for (RecipePuller recipe : recipes) {
			rand -= recipe.prob;
			if (rand <= 0) {
				ItemStack randomStack = recipe.output.copy();
				randomStack.setCount(1 + (int) (Math.random() * 2));
				return randomStack;
			}
		}
		return ItemStack.EMPTY;
	}

	public static boolean isFilter(ItemStack possible) {
		for (ItemStack filter : filters) {
			if (possible.getItem().equals(filter.getItem()) && possible.getMetadata() == filter.getMetadata())
				return true;
		}
		return false;
	}

	//

	//

	//

	private ItemStack output;
	private double prob;
	private ItemStack filter;

	public RecipePuller(ItemStack output, double prob, ItemStack filter) {
		this.output = output;
		this.prob = prob;
		this.filter = filter;
	}

}
