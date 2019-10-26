package wolforce.hwell.integration.jei;

import java.util.LinkedList;

import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import wolforce.hwell.Hwell;
import wolforce.hwell.Main;
import wolforce.hwell.blocks.BlockBox;
import wolforce.mechanics.api.integration.JeiCat;

public class JeiCatBoxing extends JeiCat {

	public JeiCatBoxing() {
		super(Hwell.MODID, "Boxing Recipes", "boxing", 86, 34, Main.boxer);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(1, true, 8, 8); // in
		stacks.init(2, false, 60, 8); // out
		stacks.set(ingredients);
	}

	public LinkedList<IRecipeWrapper> getAllRecipes() {
		LinkedList<IRecipeWrapper> list = new LinkedList<>();
		for (BlockBox box : Main.boxes) {
			list.add(new IRecipeWrapper() {

				@Override
				public void getIngredients(IIngredients ingredients) {
					ingredients.setInput(VanillaTypes.ITEM, new ItemStack(box.block));
					ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(box));
				}
			});
		}
		return list;
	}
}
