package wolforce.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import wolforce.Main;
import wolforce.Util;
import wolforce.items.ItemGrindingWheel;

public class RecipeGrinding {

	public static HashMap<Irio, RecipeGrinding> recipes;

	public static void initRecipes(JsonArray recipesJson) {
		recipes = new HashMap<>();
		for (JsonElement e : recipesJson) {
			recipes.put(Util.readJsonIrio(e.getAsJsonObject().get("input").getAsJsonObject()), readRecipe(e.getAsJsonObject()));
		}
	}

	private static RecipeGrinding readRecipe(JsonObject o) {
		JsonArray wheelsArray = o.get("wheels").getAsJsonArray();
		ItemGrindingWheel[] wheels = new ItemGrindingWheel[wheelsArray.size()];
		for (int i = 0; i < wheels.length; i++) {
			wheels[i] = getWheelOf(wheelsArray.get(i).getAsString());
		}
		ItemStack output = ShapedRecipes.deserializeItem(o.get("output").getAsJsonObject(), true);
		return new RecipeGrinding(output, wheels);
	}

	public static ItemGrindingWheel getWheelOf(String wheelString) {
		switch (wheelString) {
		case "iron":
			return Main.grinding_wheel_iron;
		case "diamond":
			return Main.grinding_wheel_diamond;
		case "crystal":
			return Main.grinding_wheel_crystal;
		}
		return null;
	}

	// public static void initRecipes() {
	// // ItemGrindingWheel flint = Main.grinding_wheel_flint;
	// ItemGrindingWheel iron = Main.grinding_wheel_iron;
	// ItemGrindingWheel diamond = Main.grinding_wheel_diamond;
	// ItemGrindingWheel crystal = Main.grinding_wheel_crystal;
	// // ItemGrindingWheel nether = Main.grinding_wheel_crystal_nether;
	//
	// // recipes = new HashMap<>();
	//
	// // IRON AND UP
	// put(new Irio(Blocks.STONE), new RecipeGrinding(new
	// ItemStack(Blocks.COBBLESTONE, 1), iron, diamond));
	// put(new Irio(Blocks.COBBLESTONE), new RecipeGrinding(new
	// ItemStack(Blocks.GRAVEL, 1), iron, diamond));
	// put(new Irio(Blocks.GRAVEL), new RecipeGrinding(new ItemStack(Blocks.SAND,
	// 1), iron, diamond));
	// put(new Irio(Blocks.LOG, 0), new RecipeGrinding(new ItemStack(Blocks.PLANKS,
	// 6, 0), iron, diamond));
	// put(new Irio(Blocks.LOG, 1), new RecipeGrinding(new ItemStack(Blocks.PLANKS,
	// 6, 1), iron, diamond));
	// put(new Irio(Blocks.LOG, 2), new RecipeGrinding(new ItemStack(Blocks.PLANKS,
	// 6, 2), iron, diamond));
	// put(new Irio(Blocks.LOG, 3), new RecipeGrinding(new ItemStack(Blocks.PLANKS,
	// 6, 3), iron, diamond));
	// put(new Irio(Blocks.LOG2, 0), new RecipeGrinding(new ItemStack(Blocks.PLANKS,
	// 6, 4), iron, diamond));
	// put(new Irio(Blocks.LOG2, 1), new RecipeGrinding(new ItemStack(Blocks.PLANKS,
	// 6, 5), iron, diamond));
	// put(new Irio(Main.myst_log), new RecipeGrinding(new
	// ItemStack(Main.myst_planks, 6), iron, diamond));
	// put(new Irio(Blocks.BONE_BLOCK), new RecipeGrinding(new ItemStack(Items.BONE,
	// 1), iron, diamond));
	// put(new Irio(Blocks.MELON_BLOCK), new RecipeGrinding(new
	// ItemStack(Items.MELON, 9), iron, diamond));
	// put(new Irio(Items.LEATHER_HELMET), new RecipeGrinding(new
	// ItemStack(Items.LEATHER, 5), iron, diamond));
	// put(new Irio(Items.LEATHER_CHESTPLATE), new RecipeGrinding(new
	// ItemStack(Items.LEATHER, 8), iron, diamond));
	// put(new Irio(Items.LEATHER_LEGGINGS), new RecipeGrinding(new
	// ItemStack(Items.LEATHER, 7), iron, diamond));
	// put(new Irio(Items.LEATHER_BOOTS), new RecipeGrinding(new
	// ItemStack(Items.LEATHER, 4), iron, diamond));
	//
	// // DIAMOND ONLY
	// put(new Irio(Main.glowstone_ore), new RecipeGrinding(new
	// ItemStack(Items.GLOWSTONE_DUST, 2), diamond));
	// put(new Irio(Main.quartz_ore), new RecipeGrinding(new ItemStack(Items.QUARTZ,
	// 2), diamond));
	// // iron stuff
	// put(new Irio(Items.IRON_INGOT), new RecipeGrinding(new
	// ItemStack(Items.IRON_NUGGET, 9), diamond));
	// put(new Irio(Items.BUCKET), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 3), diamond));
	// put(new Irio(Items.MINECART), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 5), diamond));
	// put(new Irio(Items.SHIELD), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 1), diamond));
	// put(new Irio(Items.IRON_DOOR), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 2), diamond));
	// put(new Irio(Items.IRON_HORSE_ARMOR), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 5), diamond));
	// put(new Irio(Items.SHEARS), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 2), diamond));
	// put(new Irio(Items.IRON_PICKAXE), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 3), diamond));
	// put(new Irio(Items.IRON_SHOVEL), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 1), diamond));
	// put(new Irio(Items.IRON_SWORD), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 2), diamond));
	// put(new Irio(Items.IRON_AXE), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 3), diamond));
	// put(new Irio(Items.IRON_HOE), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 2), diamond));
	// put(new Irio(Items.IRON_BOOTS), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 4), diamond));
	// put(new Irio(Items.IRON_CHESTPLATE), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 8), diamond));
	// put(new Irio(Items.IRON_HELMET), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 5), diamond));
	// put(new Irio(Items.IRON_LEGGINGS), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 7), diamond));
	// put(new Irio(Blocks.ACTIVATOR_RAIL), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 1), diamond));
	// put(new Irio(Blocks.DETECTOR_RAIL), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 1), diamond));
	// put(new Irio(Blocks.RAIL), new RecipeGrinding(new
	// ItemStack(Items.IRON_NUGGET, 3), diamond));
	// put(new Irio(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 2), diamond));
	// put(new Irio(Blocks.HOPPER), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 5), diamond));
	// put(new Irio(Blocks.IRON_BARS), new RecipeGrinding(new
	// ItemStack(Items.IRON_NUGGET, 3), diamond));
	// put(new Irio(Blocks.IRON_ORE), new RecipeGrinding(new
	// ItemStack(Items.IRON_NUGGET, 12), diamond));
	// put(new Irio(Blocks.IRON_BLOCK), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 9), diamond));
	// put(new Irio(Blocks.IRON_TRAPDOOR), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 4), diamond));
	// put(new Irio(Blocks.ANVIL), new RecipeGrinding(new
	// ItemStack(Items.IRON_INGOT, 31), diamond));
	// // gold stuff
	// put(new Irio(Items.GOLD_INGOT), new RecipeGrinding(new
	// ItemStack(Items.GOLD_NUGGET, 9), diamond));
	// put(new Irio(Items.GOLDEN_HORSE_ARMOR), new RecipeGrinding(new
	// ItemStack(Items.GOLD_INGOT, 5), diamond));
	// put(new Irio(Items.GOLDEN_PICKAXE), new RecipeGrinding(new
	// ItemStack(Items.GOLD_INGOT, 3), diamond));
	// put(new Irio(Items.GOLDEN_SHOVEL), new RecipeGrinding(new
	// ItemStack(Items.GOLD_INGOT, 1), diamond));
	// put(new Irio(Items.GOLDEN_SWORD), new RecipeGrinding(new
	// ItemStack(Items.GOLD_INGOT, 2), diamond));
	// put(new Irio(Items.GOLDEN_AXE), new RecipeGrinding(new
	// ItemStack(Items.GOLD_INGOT, 3), diamond));
	// put(new Irio(Items.GOLDEN_HOE), new RecipeGrinding(new
	// ItemStack(Items.GOLD_INGOT, 2), diamond));
	// put(new Irio(Items.GOLDEN_BOOTS), new RecipeGrinding(new
	// ItemStack(Items.GOLD_INGOT, 4), diamond));
	// put(new Irio(Items.GOLDEN_CHESTPLATE), new RecipeGrinding(new
	// ItemStack(Items.GOLD_INGOT, 8), diamond));
	// put(new Irio(Items.GOLDEN_HELMET), new RecipeGrinding(new
	// ItemStack(Items.GOLD_INGOT, 5), diamond));
	// put(new Irio(Items.GOLDEN_LEGGINGS), new RecipeGrinding(new
	// ItemStack(Items.GOLD_INGOT, 7), diamond));
	// put(new Irio(Blocks.GOLDEN_RAIL), new RecipeGrinding(new
	// ItemStack(Items.GOLD_INGOT, 1), diamond));
	// put(new Irio(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), new RecipeGrinding(new
	// ItemStack(Items.GOLD_INGOT, 2), diamond));
	// put(new Irio(Blocks.GOLD_ORE), new RecipeGrinding(new
	// ItemStack(Items.GOLD_NUGGET, 12), diamond));
	// put(new Irio(Blocks.GOLD_BLOCK), new RecipeGrinding(new
	// ItemStack(Items.GOLD_INGOT, 9), diamond));
	// // diamond stuff
	//
	// // CRYSTAL
	// put(new Irio(Blocks.GLOWSTONE), new RecipeGrinding(new
	// ItemStack(Items.GLOWSTONE_DUST, 4), crystal));
	// put(new Irio(Blocks.QUARTZ_BLOCK), new RecipeGrinding(new
	// ItemStack(Items.QUARTZ, 4), crystal));
	// put(new Irio(Blocks.BRICK_BLOCK), new RecipeGrinding(new
	// ItemStack(Items.BRICK, 4), crystal));
	// put(new Irio(Blocks.PRISMARINE, 0), new RecipeGrinding(new
	// ItemStack(Items.PRISMARINE_SHARD, 4), crystal));
	// put(new Irio(Blocks.PRISMARINE, 1), new RecipeGrinding(new
	// ItemStack(Items.PRISMARINE_SHARD, 9), crystal));
	// put(new Irio(Blocks.PRISMARINE, 2), new RecipeGrinding(new
	// ItemStack(Items.PRISMARINE_SHARD, 8), crystal));
	// put(new Irio(Blocks.WOOL), new RecipeGrinding(new ItemStack(Items.STRING, 4),
	// crystal));
	// put(new Irio(Items.WHEAT), new RecipeGrinding(new
	// ItemStack(Main.wheat_flour), crystal));
	//
	// }

	private static void put(Irio stack, RecipeGrinding recipeGrinder) {
		recipes.put(stack, recipeGrinder);
	}

	//

	//

	//

	//

	//

	public static ItemStack getResult(ItemGrindingWheel gwheel, ItemStack itemStack) {
		RecipeGrinding result = recipes.get(new Irio(itemStack.getItem()));
		if (result != null && Arrays.asList(result.levels).contains(gwheel))
			return result.result.copy();
		result = recipes.get(new Irio(itemStack.getItem(), itemStack.getMetadata()));
		if (result != null && Arrays.asList(result.levels).contains(gwheel))
			return result.result.copy();
		return null;
	}

	public final ItemStack result;
	public final ItemGrindingWheel[] levels;

	public RecipeGrinding(ItemStack result, ItemGrindingWheel... levels) {
		this.levels = levels;
		this.result = result;
	}

	public static Set<Entry<Irio, RecipeGrinding>> getRecipeList() {
		return recipes.entrySet();
	}
}
