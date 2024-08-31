package wolforce.hearthwell.integration.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.MapData;
import wolforce.hearthwell.data.recipes.RecipeFlare;
import wolforce.hearthwell.integration.jei.meta.JeiCat;

import java.util.List;

public class JeiCatFlare extends JeiCat<RecipeFlare> {

	public JeiCatFlare() {
		super(RecipeFlare.class, HearthWell.MODID, "jei.hearthwell.recipe.flare.title", "entity_flare", 134, 82, HearthWell.flare_torch.get());
	}

	@Override
	public Component getTitle() {
		return Component.translatable("jei.hearthwell.recipe.flare.title");
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RecipeFlare recipe, IFocusGroup focuses) {

		List<List<ItemStack>> inputLists = recipe.getInputStacks();
		int nInputs = inputLists.size();
		double angle = Math.PI * 2 / nInputs;
		for (int i = 0; i < nInputs; i++) {
			int dx = (int) (Math.cos(angle * i) * 25);
			int dy = (int) (Math.sin(angle * i) * 25);
			builder.addSlot(RecipeIngredientRole.INPUT, 41 - 9 + dx, 41 - 9 + dy)//
					.addItemStacks(inputLists.get(i));
		}

		builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 32).addIngredients(IngredientFlare.INGREDIENT_TYPE, IngredientFlare.get(recipe.flareType));

	}

	@Override
	public List<RecipeFlare> getAllRecipes() {
		return MapData.DATA.recipes_flare;
	}

}
