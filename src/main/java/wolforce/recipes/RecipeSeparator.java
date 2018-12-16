package wolforce.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import wolforce.blocks.BlockPrecisionGrinder;
import wolforce.items.ItemGrindingWheel;

public class RecipeSeparator {

	private static HashMap<Irio, RecipeSeparator> recipes;

	public static void initRecipes() {
		recipes = new HashMap<>();
		put(new Irio(Items.MAGMA_CREAM), new RecipeSeparator(//
				new ItemStack(Items.SLIME_BALL, 1), new ItemStack(Items.BLAZE_POWDER, 1)));
		put(new Irio(Blocks.MAGMA), new RecipeSeparator(//
				new ItemStack(Items.SLIME_BALL, 4), new ItemStack(Items.BLAZE_POWDER, 4)));
		put(new Irio(Blocks.TNT), new RecipeSeparator(//
				new ItemStack(Items.GUNPOWDER, 5), new ItemStack(Blocks.SAND, 4)));
		put(new Irio(Items.PAINTING), new RecipeSeparator(//
				new ItemStack(Items.STRING, 4), new ItemStack(Items.STICK, 8)));
		put(new Irio(Items.ENDER_EYE), new RecipeSeparator(//
				new ItemStack(Items.ENDER_PEARL, 1), new ItemStack(Items.BLAZE_POWDER, 1)));
		put(new Irio(Blocks.ENDER_CHEST), new RecipeSeparator(//
				new ItemStack(Items.ENDER_EYE, 1), new ItemStack(Blocks.OBSIDIAN, 8)));
		put(new Irio(Blocks.DISPENSER), new RecipeSeparator(//
				new ItemStack(Items.BOW, 1), new ItemStack(Items.REDSTONE, 1)));
		put(new Irio(Blocks.DROPPER), new RecipeSeparator(//
				new ItemStack(Blocks.COBBLESTONE, 7), new ItemStack(Items.REDSTONE, 1)));
		put(new Irio(Blocks.ENCHANTING_TABLE), new RecipeSeparator(//
				new ItemStack(Blocks.OBSIDIAN, 4), new ItemStack(Items.DIAMOND, 2)));
		put(new Irio(Blocks.BREWING_STAND), new RecipeSeparator(//
				new ItemStack(Blocks.COBBLESTONE, 3), new ItemStack(Items.BLAZE_ROD, 1)));
		put(new Irio(Items.PUMPKIN_PIE), new RecipeSeparator(//
				new ItemStack(Blocks.PUMPKIN, 1), new ItemStack(Items.EGG, 1), new ItemStack(Items.SUGAR, 1)));
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
