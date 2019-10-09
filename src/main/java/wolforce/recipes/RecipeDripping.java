package wolforce.recipes;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import wolforce.Main;
import wolforce.Util;

public class RecipeDripping {

	public static LinkedList<RecipeDripping> recipes;

	public static void initRecipes() {
		recipes = new LinkedList<>();
		addRecipeDripping(Blocks.WATER, Main.liquid_souls_block);
		addRecipeDripping(Blocks.SAND, Blocks.SOUL_SAND);
		addRecipeDripping(new ItemStack(Blocks.SAND, 1, 1), new ItemStack(Blocks.SOUL_SAND, 1));
	}

	private static void addRecipeDripping(Block input, Block output) {
		addRecipeDripping(new ItemStack(input), new ItemStack(output));
	}

	private static void addRecipeDripping(ItemStack input, ItemStack output) {
		recipes.add(new RecipeDripping(input, output));
	}

	public static Block getResult(Block block) {
		for (RecipeDripping recipe : recipes) {
			if (Util.equalExceptAmount(recipe.input, new ItemStack(block))) {
				return Block.getBlockFromItem(recipe.output.getItem());
			}
		}
		return null;
	}

	public final ItemStack input;
	public final ItemStack output;

	public RecipeDripping(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}

}
