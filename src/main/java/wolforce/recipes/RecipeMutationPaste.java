package wolforce.recipes;

import java.util.ArrayList;
import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import wolforce.Main;
import wolforce.Util;

public class RecipeMutationPaste {

	public static LinkedList<RecipeMutationPaste> recipes;

	public static void initRecipes() {
		recipes = new LinkedList<>();

		addRecipe(s(Main.hamburger, 0), s(Items.MUTTON, 0), //
				s(Items.BEEF, 0), s(Items.PORKCHOP, 0), //
				s(Items.CHICKEN, 0), s(Items.RABBIT, 0));

		addRecipe(s(Items.FISH, 0), s(Items.FISH, 1), //
				s(Items.FISH, 2), s(Items.FISH, 3));

		addRecipe(s(Blocks.YELLOW_FLOWER, 0), s(Blocks.RED_FLOWER, 0), //
				s(Blocks.RED_FLOWER, 1), s(Blocks.RED_FLOWER, 2), s(Blocks.RED_FLOWER, 3), s(Blocks.RED_FLOWER, 4), //
				s(Blocks.RED_FLOWER, 5), s(Blocks.RED_FLOWER, 6), //
				s(Blocks.RED_FLOWER, 7), s(Blocks.RED_FLOWER, 8));

		addRecipe(s(Blocks.SAPLING, 0), s(Blocks.SAPLING, 1), //
				s(Blocks.SAPLING, 2), s(Blocks.SAPLING, 3), //
				s(Blocks.SAPLING, 4), s(Blocks.SAPLING, 5));

		addRecipe(s(Items.BEETROOT_SEEDS, 0), s(Items.WHEAT_SEEDS, 0), //
				s(Items.PUMPKIN_SEEDS, 0), s(Items.MELON_SEEDS, 0));
	}

	public static void addRecipe(ItemStack... items) {
		recipes.add(new RecipeMutationPaste(items));
	}

	private static ItemStack s(Item item, int meta) {
		return new ItemStack(item, 1, meta);
	}

	private static ItemStack s(Block block, int meta) {
		return new ItemStack(block, 1, meta);
	}

	public static ItemStack getNext(ItemStack stackInSlot) {
		for (RecipeMutationPaste recipeMutationPaste : recipes) {
			if (recipeMutationPaste.contains(stackInSlot))
				return recipeMutationPaste.getNextOf(stackInSlot);
		}
		return null;
	}

	//

	//

	public final ArrayList<ItemStack> items;

	public RecipeMutationPaste(ItemStack[] items) {
		this.items = new ArrayList<>();
		for (ItemStack itemStack : items) {
			this.items.add(itemStack);
		}
	}

	@Override
	public String toString() {
		String str = "[";
		for (ItemStack item : items) {
			str += item.toString() + ", ";
		}
		return str.substring(0, str.length() - 2) + "]";
	}

	private boolean contains(ItemStack stackInSlot) {
		for (ItemStack stack : items)
			if (Util.equalExceptAmount(stack, stackInSlot))
				return true;
		return false;
	}

	private ItemStack getNextOf(ItemStack stackInSlot) {
		for (int i = 0; i < items.size(); i++) {
			if (Util.equalExceptAmount(items.get(i), stackInSlot))
				if (i == items.size() - 1)
					return items.get(0).copy();
				else
					return items.get(i + 1).copy();
		}
		System.err.println("SHOULD NEVER HAPPEN");
		return null;
	}

}
