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
import wolforce.hwell.recipes.RecipeMutationPaste;
import wolforce.mechanics.api.integration.JeiCat;

public class JeiCatMutating extends JeiCat {

	public JeiCatMutating() {
		super(Hwell.MODID, "Mutator Recipes", "mutating", 90, 90, Main.mutator);
		addCatalyst(Main.mutation_paste);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		float max = ingredients.getInputs(VanillaTypes.ITEM).size();
		float d = 35;
		float a = (float) (Math.PI * 2 / (max - 1));
		float initA = (float) (-Math.PI / 2);

		stacks.init(0, true, 37, 37);
		// stacks.set(0, new ItemStack(Main.mutation_paste));
		for (int i = 1; i < max; i++)
			stacks.init(i, true, (int) (37f + d * Math.cos(a * i + initA)), (int) (37f + d * Math.sin(a * i + initA)));
		stacks.set(ingredients);
	}

	@Override
	public LinkedList<IRecipeWrapper> getAllRecipes() {
		LinkedList<IRecipeWrapper> recipeWrappers = new LinkedList<>();
		for (final RecipeMutationPaste recipe : RecipeMutationPaste.recipes) {
			IRecipeWrapper recipeWrapper = new IRecipeWrapper() {

				@Override
				public void getIngredients(IIngredients ingredients) {
					// ingredients.setInputs(VanillaTypes.ITEM, Util.listOfOne(new
					// ItemStack(Main.mutator)));
					List<ItemStack> ins = new LinkedList<>();
					ins.add(new ItemStack(Main.mutation_paste));
					for (ItemStack item : recipe.items)
						ins.add(item);
					ingredients.setInputs(VanillaTypes.ITEM, ins);
					ingredients.setOutputs(VanillaTypes.ITEM, recipe.items);
				}
			};
			recipeWrappers.add(recipeWrapper);
		}
		return recipeWrappers;
	}

}
