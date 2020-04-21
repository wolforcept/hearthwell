package wolforce.hwell.recipes;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import wolforce.hwell.HwellConfig;
import wolforce.mechanics.Util;

public class RecipePuller {

	public static LinkedList<RecipePuller> recipes;
	static HashSet<ItemStack> filters;

	public static void initRecipes(JsonArray recipesJson) {
		// for (JsonElement e : recipesJson) {
		// totalProb += getProb(e.getAsJsonObject());
		// }
		recipes = new LinkedList<>();
		filters = new HashSet<>();
		for (JsonElement e : recipesJson)
			readRecipe(e.getAsJsonObject());

	}

	// private static float getProb(JsonObject o) {
	// return o.get("probability").getAsFloat();
	// }

	private static void readRecipe(JsonObject o) {

		ItemStack output = ShapedRecipes.deserializeItem(o.get("output").getAsJsonObject(), true);
		double prob = o.get("probability").getAsFloat();
		ItemStack filter = ShapedRecipes.deserializeItem(o.get("filter").getAsJsonObject(), true);
		filters.add(filter);
		recipes.add(new RecipePuller(output, prob, filter));
	}

	public static ItemStack getRandomPull(List<ItemStack> stacksInLiquid) {
		if (stacksInLiquid != null && !stacksInLiquid.isEmpty()
				&& Math.random() < HwellConfig.machines.pullerChanceToGetFilteredPull) {
			LinkedList<RecipePuller> preferredRecipes = new LinkedList<>();
			double total = 0;
			for (ItemStack stackInLiquid : stacksInLiquid) {
				for (RecipePuller recipe : recipes) {
					if (Util.equalExceptAmount(recipe.filter, stackInLiquid) && !preferredRecipes.contains(recipe)) {
						preferredRecipes.add(recipe);
						total += recipe.getProb();
					}
				}
			}
			if (!preferredRecipes.isEmpty() && total > 0) {
				double rand = Math.random();
				for (RecipePuller recipe : preferredRecipes) {
					rand -= recipe.getProb() / total;
					if (rand <= 0) {
						ItemStack randomStack = recipe.output.copy();
						randomStack.setCount(1 + (int) (Math.random() * 2));
						return randomStack;
					}
				}
				// ItemStack randomPreferedStack = preferredRecipes.get((int)
				// (preferredRecipes.size() * Math.random())).output.copy();
				// randomPreferedStack.setCount(1 + (int) (Math.random() * 2));
				// return randomPreferedStack;
			}
		}
		double rand = Math.random();
		for (RecipePuller recipe : recipes) {
			rand -= recipe.getProb();
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

	public static double totalProb = 0;

	public ItemStack output;
	private double probability;
	public ItemStack filter;

	public RecipePuller(ItemStack output, double probability, ItemStack filter) {
		this.output = output;
		this.probability = probability;
		totalProb += probability;
		this.filter = filter;
	}

	public double getProb() {
		return probability / totalProb;
	}

	public double getProb2() {
		return getProb() * HwellConfig.machines.pullerChanceToGetFilteredPull + getProb();
	}
}
