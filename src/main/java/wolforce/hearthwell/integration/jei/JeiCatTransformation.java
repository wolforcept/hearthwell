package wolforce.hearthwell.integration.jei;

import java.util.List;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.MapData;
import wolforce.hearthwell.data.recipes.RecipeTransformation;
import wolforce.hearthwell.integration.jei.meta.JeiCat;

public class JeiCatTransformation extends JeiCat<RecipeTransformation> {

	public JeiCatTransformation() {
		super(RecipeTransformation.class, HearthWell.MODID, "Transformation Recipes", "transformation", 130, 34, HearthWell.myst_dust);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeTransformation recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 9, 9).addIngredients(IngredientFlare.INGREDIENT_TYPE, IngredientFlare.get(recipe.flareType));
		builder.addSlot(RecipeIngredientRole.INPUT, 57, 9).addItemStacks(recipe.getInputStack());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 105, 9).addItemStacks(recipe.getOutputStacksFlat());
	}

	@Override
	public List<RecipeTransformation> getAllRecipes() {
		return MapData.DATA.recipes_transformation;
	}

	@Override
	public boolean isToAddIconAsCatalyst() {
		return false;
	}

}
