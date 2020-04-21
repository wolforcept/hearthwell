package wolforce.hwell.integration.jei;

import java.util.LinkedList;

import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import wolforce.hwell.Hwell;
import wolforce.hwell.Main;
import wolforce.hwell.recipes.RecipePuller;
import wolforce.mechanics.Util;
import wolforce.mechanics.api.integration.JeiCat;

public class JeiCatPullingFiltered extends JeiCat {

	public JeiCatPullingFiltered() {
		super(Hwell.MODID, "Puller with Filter Recipes", "pulling_filtered", 80, 80, Main.puller);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 53, 35); // in
		stacks.init(1, false, 31, 8); // out
		stacks.set(ingredients);

	}

	public LinkedList<IRecipeWrapper> getAllRecipes() {
		LinkedList<IRecipeWrapper> list = new LinkedList<IRecipeWrapper>();
		for (RecipePuller recipe : RecipePuller.recipes) {
			list.add(new IRecipeWrapper() {
				@Override
				public void getIngredients(IIngredients ingredients) {
					ingredients.setInput(VanillaTypes.ITEM, recipe.filter);
					ingredients.setOutput(VanillaTypes.ITEM, recipe.output);
				}

				@Override
				public void drawInfo(Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
					Util.drawTextCentered(mc.fontRenderer, "Chance:", 0, 55, 80, 10, 0xFF000000, false);
					Util.drawTextCentered(mc.fontRenderer, (Math.round(recipe.getProb2() * 100d * 100d) / 100d) + "%", //
							0, 65, 80, 10, 0xFF000000, false);
				}
			});
		}
		return list;
	}

}
