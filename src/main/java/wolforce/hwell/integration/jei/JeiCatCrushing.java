package wolforce.hwell.integration.jei;

import java.util.ArrayList;
import java.util.Arrays;
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
import wolforce.hwell.recipes.RecipeCrushing;
import wolforce.mechanics.Util;
import wolforce.mechanics.api.integration.JeiCat;

public class JeiCatCrushing extends JeiCat {

	public JeiCatCrushing() {
		super(Hwell.MODID, "Crushing Recipes", "crushing", 60, 86, Main.crushing_block);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 8, 34); // the crushing block
		stacks.init(1, true, 34, 8); // in
		stacks.init(2, false, 34, 60); // out
		stacks.set(ingredients);
	}

	public LinkedList<IRecipeWrapper> getAllRecipes() {
		LinkedList<IRecipeWrapper> list = new LinkedList<IRecipeWrapper>();
		for (RecipeCrushing recipe : RecipeCrushing.recipes) {
			list.add(new IRecipeWrapper() {

				@Override
				public void getIngredients(IIngredients ingredients) {

					List<List<ItemStack>> ins = new ArrayList<>(2);
					ins.add(Util.<ItemStack>listOfOne(new ItemStack(Main.crushing_block)));
					ins.add(Arrays.asList(recipe.inputs));
					ingredients.setInputLists(VanillaTypes.ITEM, ins);

					LinkedList<ItemStack> list = new LinkedList<>(Arrays.asList(recipe.outputs));
					double sum = 0;
					for (double d : recipe.probabilities)
						sum += d;
					if (sum < 1)
						list.add(new ItemStack(Main.empty));
					ingredients.setOutputLists(VanillaTypes.ITEM, Util.<List<ItemStack>>listOfOne(list));
				}
			});
		}
		return list;
	}

	// public static class JeiRecCrushing implements IRecipeWrapper {
	//
	// private ItemStack crushingBlock;
	// private ItemStack in;
	// private List<ItemStack> out;
	// private RecipeCrushing[] results;
	//
	// public JeiRecCrushing(Irio in, RecipeCrushing[] results) {
	// this.results = results;
	// this.crushingBlock = new ItemStack(Main.crushing_block);
	// this.in = in.stack();
	// this.out = new LinkedList<>();
	// for (int i = 0; i < results.length; i++) {
	// this.out.add(results[i].stack);
	// }
	// }
	//
	// @Override
	// public void getIngredients(IIngredients ingredients) {
	// LinkedList<ItemStack> ins = new LinkedList<>();
	// ins.add(crushingBlock);
	// ins.add(in);
	// ingredients.setInputs(ItemStack.class, ins);
	// ingredients.setOutput(ItemStack.class, out);
	// // List<List<ItemStack>> outs = new LinkedList<>();
	// // for (ItemStack stack : out) {
	// // List<ItemStack> listofone = new LinkedList<>();
	// // listofone.add(stack);
	// // outs.add(listofone);
	// // }
	// // ingredients.setOutputLists(ItemStack.class, outs);
	// }
	//
	// @Override
	// public String toString() {
	// return "[ " + in.getUnlocalizedName() + " -> " + out.size() + " ]";
	// }
	// }

}
