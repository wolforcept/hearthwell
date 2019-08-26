package integration.jei;

import java.util.Arrays;
import java.util.LinkedList;

import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import wolforce.Main;
import wolforce.Util;
import wolforce.recipes.RecipeFreezer;

public class JeiCatFreezing extends JeiCat {

	public JeiCatFreezing() {
		super("Freezer Recipes", "freezing", 98, 56, Main.freezer);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 40, 30);
		stacks.init(1, false, 72, 30);
		stacks.set(ingredients);
		recipeLayout.getFluidStacks().init(0, true, 9, 31);
		recipeLayout.getFluidStacks().set(ingredients);
	}

	public LinkedList<IRecipeWrapper> getAllRecipes() {
		LinkedList<IRecipeWrapper> recipeWrappers = new LinkedList<>();
		for (final RecipeFreezer recipe : RecipeFreezer.recipes) {
			IRecipeWrapper recipeWrapper = new IRecipeWrapper() {

				@Override
				public void getIngredients(IIngredients ingredients) {
					ingredients.setInputs(VanillaTypes.FLUID, Util.listOfOne(recipe.fluidIn));
					ingredients.setInputs(VanillaTypes.ITEM, Util.listOfOne(new ItemStack(Main.freezer)));
					ingredients.setOutputLists(VanillaTypes.ITEM, Util.listOfOne(Arrays.asList(recipe.blocksOut)));
				}
			};
			recipeWrappers.add(recipeWrapper);
		}
		return recipeWrappers;
	}

	// public static List<RecipeTube> getAllRecipes() {
	// List<JeiRecTube> list = new LinkedList<>();
	// for (RecipeFreezer r : RecipeFreezer.getRecipes()) {
	// JeiRecTube recipe = new JeiRecTube(r.blockIn, r.blocksOut);
	// System.out.println("added jei tube recipe: " + recipe); list.add(recipe); }
	// return list; }

	// public static class JeiRecTube implements IRecipeWrapper {
	// private ItemStack freezer;
	// private Block in;
	// private List<ItemStack> out;
	// public JeiRecTube(Block in, Block[] outs) {
	// this.freezer = new ItemStack(Main.freezer);
	// this.in = in; out = new LinkedList<ItemStack>();
	// for (Block b : outs)
	// out.add(new ItemStack(b)); }
	// @Override public void getIngredients(IIngredients ingredients) { }
	// @Override public String toString() {
	// return "[ " + in.getUnlocalizedName() + " -> " + out + " ]"; }}

}
