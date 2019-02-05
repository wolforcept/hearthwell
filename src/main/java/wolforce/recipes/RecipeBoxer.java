package wolforce.recipes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.BlockBox;
import wolforce.items.ItemGrindingWheel;

public class RecipeBoxer  {

	public static Block getResult(Block blockIn) {
		if (blockIn == null)
			return null;
		for (BlockBox box : Main.boxes)
			if (box.block.equals(blockIn))
				return box;
		return null;
	}

}
