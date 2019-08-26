package integration.jei;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import wolforce.Main;
import wolforce.Util;
import wolforce.recipes.RecipeSeparator;

public class JeiCatSeparating extends JeiCat {

	public JeiCatSeparating() {
		super("Separator Recipes", "separating", 106, 108, Main.separator);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 44, 45); // separator
		stacks.init(1, true, 44, 8); // in
		stacks.init(2, false, 8, 45); // out1
		stacks.init(3, false, 80, 45); // out2
		stacks.init(4, false, 44, 82); // out3
		stacks.set(ingredients);
	}

	@Override
	public LinkedList<IRecipeWrapper> getAllRecipes() {
		LinkedList<IRecipeWrapper> list = new LinkedList<IRecipeWrapper>();
		for (RecipeSeparator recipe : RecipeSeparator.recipes) {
			list.add(new IRecipeWrapper() {

				@Override
				public void getIngredients(IIngredients ingredients) {

					List<List<ItemStack>> ins = new ArrayList<>(3);
					ins.add(Util.listOfOneItemStack(Main.separator));
					ins.add(Util.listOfOne(recipe.input));
					ingredients.setInputLists(VanillaTypes.ITEM, ins);

					List<List<ItemStack>> outs = new ArrayList<>(3);
					outs.add(Util.listOfOne(recipe.left));
					outs.add(Util.listOfOne(recipe.right));
					outs.add(Util.listOfOne(recipe.back));
					ingredients.setOutputLists(VanillaTypes.ITEM, outs);

				}
			});
		}
		return list;
	}

}
