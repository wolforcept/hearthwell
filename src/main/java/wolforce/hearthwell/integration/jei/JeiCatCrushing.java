package wolforce.hearthwell.integration.jei;

import java.util.List;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.world.item.ItemStack;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.MapData;
import wolforce.hearthwell.data.recipes.RecipeCrushing;
import wolforce.hearthwell.integration.jei.meta.JeiCat;

public class JeiCatCrushing extends JeiCat<RecipeCrushing> {

	public JeiCatCrushing() {
		super(RecipeCrushing.class, HearthWell.MODID, "Crushing Recipes", "crushing", 120, 50, HearthWell.crushing_block);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeCrushing recipe, IFocusGroup focuses) {
		builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addItemStack(new ItemStack(HearthWell.crushing_block));
		builder.addSlot(RecipeIngredientRole.INPUT, 17, 17).addItemStacks(recipe.getInputStack());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 87, 17).addItemStacks(recipe.getOutputStacksFlat());
	}

	@Override
	public List<RecipeCrushing> getAllRecipes() {
		return MapData.DATA.recipes_crushing;
	}

}
