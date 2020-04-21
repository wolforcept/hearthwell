package wolforce.hwell.integration.jei;

import java.util.LinkedList;
import java.util.List;

import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import wolforce.hwell.Hwell;
import wolforce.hwell.Main;
import wolforce.hwell.recipes.RecipeTube;
import wolforce.mechanics.Util;
import wolforce.mechanics.api.integration.JeiCat;

public class JeiCatTubing extends JeiCat {

	public JeiCatTubing() {
		super(Hwell.MODID, "Amplifying Tube Recipes", "tubing", 70, 90, Main.furnace_tube);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipe, IIngredients ingredients) {

		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		// // the tube block
		stacks.init(0, true, 8, 32);
		// // in
		stacks.init(1, true, 8, 64);
		// // out
		if (!ingredients.getOutputs(VanillaTypes.ITEM).isEmpty())
			stacks.init(2, false, 44, 64);
		stacks.set(ingredients);

		if (!ingredients.getOutputs(VanillaTypes.FLUID).isEmpty()) {
			recipeLayout.getFluidStacks().init(0, false, 45, 65);
			recipeLayout.getFluidStacks().set(ingredients);
		}
	}

	public LinkedList<IRecipeWrapper> getAllRecipes() {
		LinkedList<IRecipeWrapper> recipeWrappers = new LinkedList<>();
		for (final RecipeTube recipe : RecipeTube.recipes) {
			IRecipeWrapper recipeWrapper = new IRecipeWrapper() {

				@Override
				public void getIngredients(IIngredients ingredients) {

					List<ItemStack> inputs = Util.listOfOne(new ItemStack(Main.furnace_tube));
					inputs.add(recipe.in);
					ingredients.setInputs(VanillaTypes.ITEM, inputs);

					if (Util.isValid(recipe.out))
						ingredients.setOutput(VanillaTypes.ITEM, recipe.out);
					if (recipe.outFluid != null)
						ingredients.setOutput(VanillaTypes.FLUID, recipe.outFluid);

				}
			};
			recipeWrappers.add(recipeWrapper);
		}
		return recipeWrappers;
	}

}
