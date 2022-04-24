package wolforce.hearthwell.integration.jei;

import java.util.List;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.MapData;
import wolforce.hearthwell.data.recipes.RecipeHandItem;
import wolforce.hearthwell.integration.jei.meta.JeiCat;

public class JeiCatHandItem extends JeiCat<RecipeHandItem> {

	public JeiCatHandItem() {
		super(RecipeHandItem.class, HearthWell.MODID, "Flare Item Recipes", "handitem", 130, 34, HearthWell.myst_dust);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeHandItem recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 9, 9).addIngredients(IngredientFlare.INGREDIENT_TYPE, IngredientFlare.get(recipe.flareType));
		builder.addSlot(RecipeIngredientRole.INPUT, 57, 9).addItemStacks(recipe.getInputStack());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 105, 9).addItemStacks(recipe.getOutputStacksFlat());
	}

	@Override
	public List<RecipeHandItem> getAllRecipes() {
		return MapData.DATA.recipes_handitem;
	}

	@Override
	public boolean isToAddIconAsCatalyst() {
		return false;
	}

}
