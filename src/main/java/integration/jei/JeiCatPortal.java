package integration.jei;

import java.util.LinkedList;

import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import wolforce.recipes.RecipeNetherPortal;

public class JeiCatPortal extends JeiCat {

	public JeiCatPortal() {
		super("Nether Portal Recipes", "portal", 92, 34, Blocks.OBSIDIAN);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(1, true, 8, 8); // in
		stacks.init(2, false, 66, 8); // out
		stacks.set(ingredients);
	}

	public LinkedList<IRecipeWrapper> getAllRecipes() {
		LinkedList<IRecipeWrapper> list = new LinkedList<>();
		for (RecipeNetherPortal box : RecipeNetherPortal.recipes) {
			list.add(new IRecipeWrapper() {

				@Override
				public void getIngredients(IIngredients ingredients) {
					ingredients.setInput(VanillaTypes.ITEM, box.input);
					ingredients.setOutput(VanillaTypes.ITEM, box.output);
				}
			});
		}
		return list;
	}
}
