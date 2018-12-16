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
import wolforce.items.ItemGrindingWheel;

public class RecipeTube implements IRecipeWrapper {

	public final static LinkedList<RecipeTube> recipes = new LinkedList<>();

	public static void initRecipes() {
		put(Blocks.CACTUS, Blocks.WATER);
		put(Blocks.LEAVES, Blocks.WATER);
		put(Blocks.LEAVES2, Blocks.WATER);
		put(Main.myst_leaves, Blocks.WATER);
		put(Blocks.SNOW, Blocks.WATER);
		put(Blocks.CLAY, Blocks.WATER);
		put(Blocks.ICE, Blocks.WATER);
		put(Blocks.PACKED_ICE, Blocks.WATER);

		put(Blocks.STONE, Blocks.LAVA);
		put(Blocks.COBBLESTONE, Blocks.LAVA);
		put(Blocks.SANDSTONE, Blocks.LAVA);

		put(Main.myst_dust_block, Main.liquid_souls_block);
	}

	private static void put(Block in, Block out) {
		recipes.add(new RecipeTube(in, out));
	}

	public static Block getResult(Block block) {
		if (block == null)
			return null;
		for (RecipeTube recipeTube : recipes)
			if (recipeTube.in.equals(block))
				return recipeTube.out;
		return null;
	}
	//

	//

	//

	//

	public final Block in;
	public final Block out;

	public RecipeTube(Block in, Block out) {
		this.in = in;
		this.out = out;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		Util.setIngredients(ingredients, new Block[] { Main.furnace_tube, in }, new Block[] { out });
	}

	@Override
	public String toString() {
		return "[ " + in.getUnlocalizedName() + " -> " + out.getUnlocalizedName() + " ]";
	}

}
