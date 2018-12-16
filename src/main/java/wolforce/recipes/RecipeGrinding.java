package wolforce.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import wolforce.Main;
import wolforce.items.ItemGrindingWheel;

public class RecipeGrinding {

	private static HashMap<Irio, RecipeGrinding> recipes;

	public static void initRecipes() {
		ItemGrindingWheel flint = Main.grinding_wheel_flint;
		ItemGrindingWheel iron = Main.grinding_wheel_iron;
		ItemGrindingWheel diamond = Main.grinding_wheel_diamond;
		ItemGrindingWheel crystal = Main.grinding_wheel_crystal;
		ItemGrindingWheel nether = Main.grinding_wheel_crystal_nether;

		recipes = new HashMap<>();

		// FLINT AND UP
		put(new Irio(Blocks.STONE), new RecipeGrinding(new ItemStack(Blocks.COBBLESTONE, 1), flint, iron, diamond));
		put(new Irio(Blocks.COBBLESTONE), new RecipeGrinding(new ItemStack(Blocks.GRAVEL, 1), flint, iron, diamond));
		put(new Irio(Blocks.GRAVEL), new RecipeGrinding(new ItemStack(Blocks.SAND, 1), flint, iron, diamond));
		put(new Irio(Blocks.LOG, 0), new RecipeGrinding(new ItemStack(Blocks.PLANKS, 6, 0), flint, iron, diamond));
		put(new Irio(Blocks.LOG, 1), new RecipeGrinding(new ItemStack(Blocks.PLANKS, 6, 1), flint, iron, diamond));
		put(new Irio(Blocks.LOG, 2), new RecipeGrinding(new ItemStack(Blocks.PLANKS, 6, 2), flint, iron, diamond));
		put(new Irio(Blocks.LOG, 3), new RecipeGrinding(new ItemStack(Blocks.PLANKS, 6, 3), flint, iron, diamond));
		put(new Irio(Blocks.LOG2, 0), new RecipeGrinding(new ItemStack(Blocks.PLANKS, 6, 4), flint, iron, diamond));
		put(new Irio(Blocks.LOG2, 1), new RecipeGrinding(new ItemStack(Blocks.PLANKS, 6, 5), flint, iron, diamond));
		put(new Irio(Main.myst_log), new RecipeGrinding(new ItemStack(Main.myst_planks, 6), flint, iron, diamond));

		// IRON AND UP
		put(new Irio(Blocks.BONE_BLOCK), new RecipeGrinding(new ItemStack(Items.BONE, 1), iron, diamond));
		put(new Irio(Blocks.MELON_BLOCK), new RecipeGrinding(new ItemStack(Items.MELON, 9), iron, diamond));

		// DIAMOND ONLY
		// iron stuff
		put(new Irio(Items.IRON_INGOT), new RecipeGrinding(new ItemStack(Items.IRON_NUGGET, 9), diamond));
		put(new Irio(Items.BUCKET), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 3), diamond));
		put(new Irio(Items.COMPASS), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 4), diamond));
		put(new Irio(Items.MINECART), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 5), diamond));
		put(new Irio(Items.SHIELD), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 1), diamond));
		put(new Irio(Items.IRON_DOOR), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new Irio(Items.IRON_HORSE_ARMOR), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 5), diamond));
		put(new Irio(Items.SHEARS), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new Irio(Items.IRON_PICKAXE), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 3), diamond));
		put(new Irio(Items.IRON_SHOVEL), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 1), diamond));
		put(new Irio(Items.IRON_SWORD), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new Irio(Items.IRON_AXE), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 3), diamond));
		put(new Irio(Items.IRON_HOE), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new Irio(Items.IRON_BOOTS), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 4), diamond));
		put(new Irio(Items.IRON_CHESTPLATE), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 8), diamond));
		put(new Irio(Items.IRON_HELMET), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 5), diamond));
		put(new Irio(Items.IRON_LEGGINGS), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 7), diamond));
		put(new Irio(Blocks.ACTIVATOR_RAIL), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 1), diamond));
		put(new Irio(Blocks.DETECTOR_RAIL), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 1), diamond));
		put(new Irio(Blocks.RAIL), new RecipeGrinding(new ItemStack(Items.IRON_NUGGET, 3), diamond));
		put(new Irio(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new Irio(Blocks.HOPPER), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 5), diamond));
		put(new Irio(Blocks.IRON_BARS), new RecipeGrinding(new ItemStack(Items.IRON_NUGGET, 3), diamond));
		put(new Irio(Blocks.IRON_ORE), new RecipeGrinding(new ItemStack(Items.IRON_NUGGET, 12), diamond));
		put(new Irio(Blocks.IRON_BLOCK), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 9), diamond));
		put(new Irio(Blocks.IRON_TRAPDOOR), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 4), diamond));
		put(new Irio(Blocks.ANVIL), new RecipeGrinding(new ItemStack(Items.IRON_INGOT, 31), diamond));
		// gold stuff
		put(new Irio(Items.GOLD_INGOT), new RecipeGrinding(new ItemStack(Items.GOLD_NUGGET, 9), diamond));
		put(new Irio(Items.CLOCK), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 4), diamond));
		put(new Irio(Items.GOLDEN_HORSE_ARMOR), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 5), diamond));
		put(new Irio(Items.GOLDEN_PICKAXE), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 3), diamond));
		put(new Irio(Items.GOLDEN_SHOVEL), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 1), diamond));
		put(new Irio(Items.GOLDEN_SWORD), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 2), diamond));
		put(new Irio(Items.GOLDEN_AXE), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 3), diamond));
		put(new Irio(Items.GOLDEN_HOE), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 2), diamond));
		put(new Irio(Items.GOLDEN_BOOTS), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 4), diamond));
		put(new Irio(Items.GOLDEN_CHESTPLATE), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 8), diamond));
		put(new Irio(Items.GOLDEN_HELMET), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 5), diamond));
		put(new Irio(Items.GOLDEN_LEGGINGS), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 7), diamond));
		put(new Irio(Blocks.GOLDEN_RAIL), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 1), diamond));
		put(new Irio(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 2), diamond));
		put(new Irio(Blocks.GOLD_ORE), new RecipeGrinding(new ItemStack(Items.GOLD_NUGGET, 12), diamond));
		put(new Irio(Blocks.GOLD_BLOCK), new RecipeGrinding(new ItemStack(Items.GOLD_INGOT, 9), diamond));
		// diamond stuff

	}

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
			return result.result;
		result = recipes.get(new Irio(itemStack.getItem(), itemStack.getMetadata()));
		if (result != null && Arrays.asList(result.levels).contains(gwheel))
			return result.result;
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
