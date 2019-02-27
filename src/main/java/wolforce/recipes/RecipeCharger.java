package wolforce.recipes;

import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import wolforce.Main;
import wolforce.Util;

public class RecipeCharger {

	private static LinkedList<RecipeCharger> recipes;

	public static void initRecipes(JsonArray recipesJson) {
		recipes = new LinkedList<>();
		for (JsonElement e : recipesJson) {
			RecipeCharger rec = readRecipe(e.getAsJsonObject());
			if (rec != null) {
				recipes.add(rec);
				System.out.println(rec);
			}
		}
	}

	private static RecipeCharger readRecipe(JsonObject o) {
		if (o.has("special_liquid_souls_bucket_recipe")) {
			if (o.get("special_liquid_souls_bucket_recipe").getAsBoolean()) {
				return new RecipeCharger(//
						FluidUtil.getFilledBucket(new FluidStack(Main.liquid_souls, 1000)), //
						new ItemStack(Items.BUCKET), //
						o.get("power").getAsInt());
			}
			return null;
		}
		ItemStack input = ShapedRecipes.deserializeItem(o.getAsJsonObject("input"), false);
		ItemStack output = o.has("output") ? ShapedRecipes.deserializeItem(o.getAsJsonObject("output"), false) : null;
		int power = o.get("power").getAsInt();
		return new RecipeCharger(input, output, power);
	}

	public static int getResult(ItemStack stack) {
		for (RecipeCharger recipeCharger : recipes) {
			if (Util.equalExceptAmount(stack, recipeCharger.input)) {
				return recipeCharger.power;
			}
		}
		return 0;
	}

	public static ItemStack getSpit(ItemStack stack) {
		for (RecipeCharger recipeCharger : recipes) {
			if (Util.equalExceptAmount(stack, recipeCharger.input)) {
				return recipeCharger.output != null ? recipeCharger.output.copy() : null;
			}
		}
		return null;
	}

	//

	//

	//

	final ItemStack input;
	final ItemStack output;
	final int power;

	public RecipeCharger(ItemStack input, ItemStack output, int power) {
		this.input = input;
		this.output = output;
		this.power = power;
	}

	@Override
	public String toString() {
		return "(" + power + ")" + input + " -> " + output;
	}

}
