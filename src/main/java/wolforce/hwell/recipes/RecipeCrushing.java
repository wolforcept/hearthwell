package wolforce.hwell.recipes;

import java.util.Iterator;
import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import wolforce.mechanics.Util;

public class RecipeCrushing {

	public static LinkedList<RecipeCrushing> recipes;

	public static void initRecipes(JsonArray recipesJson) {

		recipes = new LinkedList<>();

		for (JsonElement e : recipesJson)
			readRecipe(e.getAsJsonObject());
	}

	private static void readRecipe(JsonObject recipe) {

		JsonArray inputsArray = recipe.get("inputs").getAsJsonArray();
		JsonArray outputsArray = recipe.get("possible_outputs").getAsJsonArray();

		ItemStack[] inputs = new ItemStack[inputsArray.size()];
		ItemStack[] outputs = new ItemStack[outputsArray.size()];
		double[] probs = new double[outputsArray.size()];

		for (int i = 0; i < inputs.length; i++) {
			JsonObject o = inputsArray.get(i).getAsJsonObject();
			inputs[i] = ShapedRecipes.deserializeItem(o.getAsJsonObject(), false);
		}

		for (int i = 0; i < outputs.length; i++) {
			JsonObject o = outputsArray.get(i).getAsJsonObject();
			outputs[i] = ShapedRecipes.deserializeItem(o.get("out").getAsJsonObject(), true);
			probs[i] = o.has("probability") ? o.get("probability").getAsDouble() : 1.0;
		}
		
		recipes.add(new RecipeCrushing(inputs, outputs, probs));
	}

	public static void removeRecipe(ItemStack input) {
		for (Iterator<RecipeCrushing> iterator = recipes.iterator(); iterator.hasNext();) {
			RecipeCrushing recipe = (RecipeCrushing) iterator.next();
			for (ItemStack stack : recipe.inputs) {
				if (Util.equalExceptAmount(stack, input))
					iterator.remove();
			}
		}
	}

	private static RecipeCrushing getRecipeFor(ItemStack input) {

		for (RecipeCrushing recipe : recipes) {
			for (ItemStack stack : recipe.inputs) {
				if (Util.equalExceptAmount(input, stack)) {
					return recipe;
				}
			}
		}

		return null;
	}

	public static ItemStack getSingleResult(ItemStack input) {

		RecipeCrushing recipe = getRecipeFor(input);

		if (recipe == null)
			return null;

		double d = Math.random();
		int i = 0;
		while (true) {

			if (i >= recipe.outputs.length)
				return null;
			if (d < recipe.probabilities[i])
				return recipe.outputs[i].copy();
			d -= recipe.probabilities[i];
			i++;
		}
	}

	//

	public final ItemStack[] inputs;
	public final ItemStack[] outputs;
	public final double[] probabilities;

	public RecipeCrushing(ItemStack inputs, ItemStack[] outputs, double[] probabilities) {
		this(new ItemStack[] { inputs }, outputs, probabilities);
	}

	public RecipeCrushing(ItemStack[] inputs, ItemStack[] outputs, double[] probabilities) {
		this.inputs = inputs;
		this.outputs = outputs;
		this.probabilities = probabilities;
	}

	// private RecipeCrushing(Item item) {
	// this(new ItemStack(item, 1), 1.0);
	// }
	//
	// private RecipeCrushing(Item item, int n) {
	// this(new ItemStack(item, n), 1.0);
	// }
	//
	// private RecipeCrushing(Item item, int n, double prob) {
	// this(new ItemStack(item, n), prob);
	// }
	//
	// private RecipeCrushing(Block block) {
	// this(new ItemStack(block, 1), 1.0);
	// }

	// public static Set<Entry<ItemStack, RecipeCrushing[]>> getRecipeList() {
	// return recipes.entrySet();
	// }

	// public static void initRecipes() {
	// putRecipe(new Irio(Blocks.COBBLESTONE), new RecipeCrushing(Main.dust, 2));
	// putRecipe(new Irio(Blocks.STONE), new RecipeCrushing(Main.dust, 2));
	// putRecipe(new Irio(Blocks.SANDSTONE), new RecipeCrushing(Main.dust, 2));
	// putRecipe(new Irio(Main.heavy_nugget), new RecipeCrushing(Main.fuel_dust,
	// 2));
	//
	// putRecipe(new Irio(Main.myst_rod), new RecipeCrushing(Main.myst_dust, 2));
	//
	// putRecipe(new Irio(Blocks.GRAVEL), //
	// new RecipeCrushing(Items.FLINT, 1, .6), //
	// new RecipeCrushing(Items.FLINT, 2, .3), //
	// new RecipeCrushing(Items.FLINT, 0, .1)//
	// );
	// putRecipe(new Irio(Blocks.CLAY), new RecipeCrushing(Items.CLAY_BALL, 4));
	// putRecipe(new Irio(Blocks.SOUL_SAND), new RecipeCrushing(Main.soul_dust, 4));
	// putRecipe(new Irio(Blocks.TALLGRASS, 1), new
	// RecipeCrushing(Items.WHEAT_SEEDS, 1, .75));
	//
	// putRecipe(new Irio(Main.citrinic_sand), //
	// new RecipeCrushing(Main.salt, 2, .5), //
	// new RecipeCrushing(Main.salt, 1, .25), //
	// new RecipeCrushing(Main.salt, 3, .25)//
	// );
	//
	// putRecipe(new Irio(Main.crystal), //
	// new RecipeCrushing(Main.shard_ca, 1, .125), //
	// new RecipeCrushing(Main.shard_c, 1, .125), //
	// new RecipeCrushing(Main.shard_h, 1, .125), //
	// new RecipeCrushing(Main.shard_o, 1, .125), //
	// new RecipeCrushing(Main.shard_fe, 1, .125), //
	// new RecipeCrushing(Main.shard_n, 1, .125), //
	// new RecipeCrushing(Main.shard_p, 1, .125), //
	// new RecipeCrushing(Main.shard_au, 1, .125) //
	// );
	// putRecipe(new Irio(Main.crystal_nether), //
	// new RecipeCrushing(Main.shard_ca, 1, .125), //
	// new RecipeCrushing(Main.shard_c, 1, .125), //
	// new RecipeCrushing(Main.shard_h, 1, .125), //
	// new RecipeCrushing(Main.shard_o, 1, .125), //
	// new RecipeCrushing(Main.shard_fe, 1, .125), //
	// new RecipeCrushing(Main.shard_p, 1, .125), //
	// new RecipeCrushing(Main.shard_n, 1, .125), //
	// new RecipeCrushing(Main.shard_au, 1, .125) //
	// );
	//
	// putRecipe(new Irio(Blocks.LADDER), //
	// new RecipeCrushing(Items.STICK, 2)//
	// );
	// }

}
