package wolforce.recipes;

import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import wolforce.Main;
import wolforce.items.ItemGrindingWheel;

public class RecipeGrinder {

	private static HashMap<Iri, RecipeGrinder> recipes;

	public static void initRecipes() {
		ItemGrindingWheel flint = Main.grinding_wheel_flint;
		ItemGrindingWheel iron = Main.grinding_wheel_iron;
		ItemGrindingWheel diamond = Main.grinding_wheel_diamond;
		ItemGrindingWheel crystal = Main.grinding_wheel_crystal;
		ItemGrindingWheel nether = Main.grinding_wheel_crystal_nether;

		recipes = new HashMap<>();

		// FLINT AND UP
		put(new Iri(Blocks.STONE), new RecipeGrinder(new ItemStack(Blocks.GRAVEL, 1), flint, iron, diamond));
		put(new Iri(Blocks.COBBLESTONE), new RecipeGrinder(new ItemStack(Blocks.GRAVEL, 1), flint, iron, diamond));
		put(new Iri(Blocks.GRAVEL), new RecipeGrinder(new ItemStack(Blocks.SAND, 1), flint, iron, diamond));
		put(new Iri(Blocks.LOG, 0), new RecipeGrinder(new ItemStack(Blocks.PLANKS, 6, 0), flint, iron, diamond));
		put(new Iri(Blocks.LOG, 1), new RecipeGrinder(new ItemStack(Blocks.PLANKS, 6, 1), flint, iron, diamond));
		put(new Iri(Blocks.LOG, 2), new RecipeGrinder(new ItemStack(Blocks.PLANKS, 6, 2), flint, iron, diamond));
		put(new Iri(Blocks.LOG, 3), new RecipeGrinder(new ItemStack(Blocks.PLANKS, 6, 3), flint, iron, diamond));
		put(new Iri(Blocks.LOG2, 0), new RecipeGrinder(new ItemStack(Blocks.PLANKS, 6, 4), flint, iron, diamond));
		put(new Iri(Blocks.LOG2, 1), new RecipeGrinder(new ItemStack(Blocks.PLANKS, 6, 5), flint, iron, diamond));
		put(new Iri(Main.myst_log), new RecipeGrinder(new ItemStack(Main.myst_planks, 6), flint, iron, diamond));

		// IRON AND UP
		put(new Iri(Blocks.BONE_BLOCK), new RecipeGrinder(new ItemStack(Items.BONE, 1), iron, diamond));
		put(new Iri(Blocks.MELON_BLOCK), new RecipeGrinder(new ItemStack(Items.MELON, 9), iron, diamond));

		// DIAMOND ONLY
		// iron stuff
		put(new Iri(Items.IRON_INGOT), new RecipeGrinder(new ItemStack(Items.IRON_NUGGET, 9), diamond));
		put(new Iri(Items.BUCKET), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 3), diamond));
		put(new Iri(Items.COMPASS), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 4), diamond));
		put(new Iri(Items.MINECART), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 5), diamond));
		put(new Iri(Items.SHIELD), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 1), diamond));
		put(new Iri(Items.IRON_DOOR), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new Iri(Items.IRON_HORSE_ARMOR), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 5), diamond));
		put(new Iri(Items.SHEARS), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new Iri(Items.IRON_PICKAXE), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 3), diamond));
		put(new Iri(Items.IRON_SHOVEL), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 1), diamond));
		put(new Iri(Items.IRON_SWORD), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new Iri(Items.IRON_AXE), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 3), diamond));
		put(new Iri(Items.IRON_HOE), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new Iri(Items.IRON_BOOTS), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 4), diamond));
		put(new Iri(Items.IRON_CHESTPLATE), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 8), diamond));
		put(new Iri(Items.IRON_HELMET), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 5), diamond));
		put(new Iri(Items.IRON_LEGGINGS), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 7), diamond));
		put(new Iri(Blocks.ACTIVATOR_RAIL), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 1), diamond));
		put(new Iri(Blocks.DETECTOR_RAIL), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 1), diamond));
		put(new Iri(Blocks.RAIL), new RecipeGrinder(new ItemStack(Items.IRON_NUGGET, 3), diamond));
		put(new Iri(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new Iri(Blocks.HOPPER), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 5), diamond));
		put(new Iri(Blocks.IRON_BARS), new RecipeGrinder(new ItemStack(Items.IRON_NUGGET, 3), diamond));
		put(new Iri(Blocks.IRON_ORE), new RecipeGrinder(new ItemStack(Items.IRON_NUGGET, 12), diamond));
		put(new Iri(Blocks.IRON_BLOCK), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 9), diamond));
		put(new Iri(Blocks.IRON_TRAPDOOR), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 4), diamond));
		put(new Iri(Blocks.ANVIL), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 31), diamond));
		// gold stuff
		put(new Iri(Items.GOLD_INGOT), new RecipeGrinder(new ItemStack(Items.GOLD_NUGGET, 9), diamond));
		put(new Iri(Items.CLOCK), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 4), diamond));
		put(new Iri(Items.GOLDEN_HORSE_ARMOR), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 5), diamond));
		put(new Iri(Items.GOLDEN_PICKAXE), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 3), diamond));
		put(new Iri(Items.GOLDEN_SHOVEL), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 1), diamond));
		put(new Iri(Items.GOLDEN_SWORD), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 2), diamond));
		put(new Iri(Items.GOLDEN_AXE), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 3), diamond));
		put(new Iri(Items.GOLDEN_HOE), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 2), diamond));
		put(new Iri(Items.GOLDEN_BOOTS), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 4), diamond));
		put(new Iri(Items.GOLDEN_CHESTPLATE), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 8), diamond));
		put(new Iri(Items.GOLDEN_HELMET), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 5), diamond));
		put(new Iri(Items.GOLDEN_LEGGINGS), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 7), diamond));
		put(new Iri(Blocks.GOLDEN_RAIL), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 1), diamond));
		put(new Iri(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 2), diamond));
		put(new Iri(Blocks.GOLD_ORE), new RecipeGrinder(new ItemStack(Items.GOLD_NUGGET, 12), diamond));
		put(new Iri(Blocks.GOLD_BLOCK), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 9), diamond));
		// diamond stuff

	}

	private static void put(Iri stack, RecipeGrinder recipeGrinder) {
		recipes.put(stack, recipeGrinder);
	}

	//

	//

	public static ItemStack getResult(ItemGrindingWheel gwheel, ItemStack itemStack) {
		RecipeGrinder result = recipes.get(new Iri(itemStack.getItem()));
		if (result != null && Arrays.asList(result.levels).contains(gwheel))
			return result.result;
		result = recipes.get(new Iri(itemStack.getItem(), itemStack.getMetadata()));
		if (result != null && Arrays.asList(result.levels).contains(gwheel))
			return result.result;
		return null;
	}

	private ItemStack result;
	private ItemGrindingWheel[] levels;

	public RecipeGrinder(ItemStack result, ItemGrindingWheel... levels) {
		this.levels = levels;
		this.result = result;
	}
}
