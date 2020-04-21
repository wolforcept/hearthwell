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
import wolforce.hwell.recipes.RecipeCharger;
import wolforce.mechanics.Util;
import wolforce.mechanics.api.integration.JeiCat;

public class JeiCatCharging extends JeiCat {

	public JeiCatCharging() {
		super(Hwell.MODID, "Charger Recipes", "charging", 64, 86, Main.charger);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 23, 60); // in
		stacks.init(1, false, 23, 8); // out
		stacks.set(ingredients);

	}

	public LinkedList<IRecipeWrapper> getAllRecipes() {
		LinkedList<IRecipeWrapper> list = new LinkedList<IRecipeWrapper>();
		for (RecipeCharger recipe : RecipeCharger.recipes) {
			list.add(new IRecipeWrapper() {
				@Override
				public void getIngredients(IIngredients ingredients) {
					ingredients.setInput(VanillaTypes.ITEM, recipe.input);
					ingredients.setOutput(VanillaTypes.ITEM, recipe.output);
				}

				@Override
				public void drawInfo(Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
					Util.drawTextCentered(mc.fontRenderer, recipe.power + "\u03C1", 8, 40, 48, 11, 0xFF000000, false);
				}
			});
		}
		return list;
	}

}
