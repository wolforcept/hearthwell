package wolforce.hearthwell.integration.jei;

import java.util.List;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.renderer.Rect2i;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.MapData;
import wolforce.hearthwell.data.recipes.RecipeInfluence;
import wolforce.hearthwell.integration.jei.meta.JeiCat;

public class JeiCatInfluence extends JeiCat<RecipeInfluence> {

	public JeiCatInfluence() {
		super(RecipeInfluence.class, HearthWell.MODID, "Influence Recipes", "influence", 82, 78, HearthWell.myst_dust);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeInfluence recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 9, 53).addItemStacks(recipe.getInputStacksFlat());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 57, 53).addItemStacks(recipe.getOutputStacksFlat());
	}

	@Override
	public List<RecipeInfluence> getAllRecipes() {
		return MapData.DATA.recipes_influence;
	}

	@Override
	public boolean isToAddIconAsCatalyst() {
		return false;
	}

	@Override
	public Rect2i getClickArea() {
		return new Rect2i(68, 111, 46, 35);
	}

}
