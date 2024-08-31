package wolforce.hearthwell.integration.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.MapData;
import wolforce.hearthwell.data.recipes.RecipeBurstSeed;
import wolforce.hearthwell.integration.jei.meta.JeiCat;

import java.util.List;

public class JeiCatBursting extends JeiCat<RecipeBurstSeed> {

	public JeiCatBursting() {
		super(RecipeBurstSeed.class, HearthWell.MODID, "jei.hearthwell.recipe.burst.title", "bursting", 64, 64, HearthWell.burst_seed.get());
	}

	@Override
	public Component getTitle() {
		return Component.translatable("jei.hearthwell.recipe.burst.title");
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeBurstSeed recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 13, 35).addItemStacks(recipe.getInputStack());
	}

	@Override
	public List<RecipeBurstSeed> getAllRecipes() {
		return MapData.DATA.recipes_burst;
	}

}
