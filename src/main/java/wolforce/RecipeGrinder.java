package wolforce;

import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import wolforce.blocks.BlockPrecisionGrinder;
import wolforce.items.ItemGrindingWheel;

public class RecipeGrinder {

	private static HashMap<MyItem, RecipeGrinder> recipes;

	public static void init() {
		ItemGrindingWheel flint = Main.grinding_wheel_flint;
		ItemGrindingWheel iron = Main.grinding_wheel_iron;
		ItemGrindingWheel diamond = Main.grinding_wheel_diamond;
		ItemGrindingWheel crystal = Main.grinding_wheel_crystal;
		ItemGrindingWheel nether = Main.grinding_wheel_crystal_nether;

		recipes = new HashMap<>();
		put(new MyItem(Blocks.STONE), new RecipeGrinder(new ItemStack(Blocks.GRAVEL, 1), flint, iron, diamond));
		put(new MyItem(Blocks.COBBLESTONE), new RecipeGrinder(new ItemStack(Blocks.GRAVEL, 1), flint, iron, diamond));
		put(new MyItem(Blocks.GRAVEL), new RecipeGrinder(new ItemStack(Blocks.SAND, 1), flint, iron, diamond));
		put(new MyItem(Blocks.LOG, 0), new RecipeGrinder(new ItemStack(Blocks.PLANKS, 6, 0), flint, iron, diamond));
		put(new MyItem(Blocks.LOG, 1), new RecipeGrinder(new ItemStack(Blocks.PLANKS, 6, 1), flint, iron, diamond));
		put(new MyItem(Blocks.LOG, 2), new RecipeGrinder(new ItemStack(Blocks.PLANKS, 6, 2), flint, iron, diamond));
		put(new MyItem(Blocks.LOG, 3), new RecipeGrinder(new ItemStack(Blocks.PLANKS, 6, 3), flint, iron, diamond));
		put(new MyItem(Blocks.LOG2, 0), new RecipeGrinder(new ItemStack(Blocks.PLANKS, 6, 4), flint, iron, diamond));
		put(new MyItem(Blocks.LOG2, 1), new RecipeGrinder(new ItemStack(Blocks.PLANKS, 6, 5), flint, iron, diamond));
		put(new MyItem(Main.myst_log), new RecipeGrinder(new ItemStack(Main.myst_planks, 6), flint, iron, diamond));

		// IRON AND DIAMOND ONLY
		put(new MyItem(Blocks.BONE_BLOCK), new RecipeGrinder(new ItemStack(Items.BONE, 2), iron, diamond));

		// DIAMOND ONLY
		// iron stuff
		put(new MyItem(Items.IRON_INGOT), new RecipeGrinder(new ItemStack(Items.IRON_NUGGET, 9), diamond));
		put(new MyItem(Items.BUCKET), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 3), diamond));
		put(new MyItem(Items.COMPASS), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 4), diamond));
		put(new MyItem(Items.MINECART), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 5), diamond));
		put(new MyItem(Items.SHIELD), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 1), diamond));
		put(new MyItem(Items.IRON_DOOR), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new MyItem(Items.IRON_HORSE_ARMOR), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 5), diamond));
		put(new MyItem(Items.SHEARS), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new MyItem(Items.IRON_PICKAXE), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 3), diamond));
		put(new MyItem(Items.IRON_SHOVEL), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 1), diamond));
		put(new MyItem(Items.IRON_SWORD), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new MyItem(Items.IRON_AXE), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 3), diamond));
		put(new MyItem(Items.IRON_HOE), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new MyItem(Items.IRON_BOOTS), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 4), diamond));
		put(new MyItem(Items.IRON_CHESTPLATE), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 8), diamond));
		put(new MyItem(Items.IRON_HELMET), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 5), diamond));
		put(new MyItem(Items.IRON_LEGGINGS), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 7), diamond));
		put(new MyItem(Blocks.ACTIVATOR_RAIL), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 1), diamond));
		put(new MyItem(Blocks.DETECTOR_RAIL), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 1), diamond));
		put(new MyItem(Blocks.RAIL), new RecipeGrinder(new ItemStack(Items.IRON_NUGGET, 3), diamond));
		put(new MyItem(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 2), diamond));
		put(new MyItem(Blocks.HOPPER), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 5), diamond));
		put(new MyItem(Blocks.IRON_BARS), new RecipeGrinder(new ItemStack(Items.IRON_NUGGET, 3), diamond));
		put(new MyItem(Blocks.IRON_ORE), new RecipeGrinder(new ItemStack(Items.IRON_NUGGET, 12), diamond));
		put(new MyItem(Blocks.IRON_BLOCK), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 9), diamond));
		put(new MyItem(Blocks.IRON_TRAPDOOR), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 4), diamond));
		put(new MyItem(Blocks.ANVIL), new RecipeGrinder(new ItemStack(Items.IRON_INGOT, 31), diamond));
		// gold stuff
		put(new MyItem(Items.GOLD_INGOT), new RecipeGrinder(new ItemStack(Items.GOLD_NUGGET, 9), diamond));
		put(new MyItem(Items.CLOCK), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 4), diamond));
		put(new MyItem(Items.GOLDEN_HORSE_ARMOR), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 5), diamond));
		put(new MyItem(Items.GOLDEN_PICKAXE), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 3), diamond));
		put(new MyItem(Items.GOLDEN_SHOVEL), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 1), diamond));
		put(new MyItem(Items.GOLDEN_SWORD), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 2), diamond));
		put(new MyItem(Items.GOLDEN_AXE), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 3), diamond));
		put(new MyItem(Items.GOLDEN_HOE), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 2), diamond));
		put(new MyItem(Items.GOLDEN_BOOTS), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 4), diamond));
		put(new MyItem(Items.GOLDEN_CHESTPLATE), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 8), diamond));
		put(new MyItem(Items.GOLDEN_HELMET), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 5), diamond));
		put(new MyItem(Items.GOLDEN_LEGGINGS), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 7), diamond));
		put(new MyItem(Blocks.GOLDEN_RAIL), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 1), diamond));
		put(new MyItem(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 2), diamond));
		put(new MyItem(Blocks.GOLD_ORE), new RecipeGrinder(new ItemStack(Items.GOLD_NUGGET, 12), diamond));
		put(new MyItem(Blocks.GOLD_BLOCK), new RecipeGrinder(new ItemStack(Items.GOLD_INGOT, 9), diamond));
		// diamond stuff

	}

	private static void put(MyItem stack, RecipeGrinder recipeGrinder) {
		recipes.put(stack, recipeGrinder);
	}

	//

	//

	public static ItemStack getResult(ItemGrindingWheel gwheel, ItemStack itemStack) {
		RecipeGrinder result = recipes.get(new MyItem(itemStack.getItem(), itemStack.getMetadata()));
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

	private static class MyItem {
		Item item;
		int meta;

		public MyItem(Item _item) {
			item = _item;
			meta = 0;
		}

		public MyItem(Block _block) {
			item = Item.getItemFromBlock(_block);
			meta = 0;
		}

		public MyItem(Item _item, int _meta) {
			item = _item;
			meta = _meta;
		}

		public MyItem(Block _block, int _meta) {
			item = Item.getItemFromBlock(_block);
			meta = _meta;
		}

		@Override
		public boolean equals(Object obj) {
			return item == ((MyItem) obj).item && meta == ((MyItem) obj).meta;
		}

		@Override
		public int hashCode() {
			return (item.hashCode() + "" + meta).hashCode();
		}
	}

}
