package integration.jei;

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
import wolforce.Main;
import wolforce.Util;
import wolforce.recipes.RecipeGrinding;

public class JeiCatGrinding extends JeiCat {

	public JeiCatGrinding() {
		super("Precision Grinder Recipes", "grinding", 86, 86, Main.precision_grinder_empty);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 8, 34); // grinder
		stacks.init(1, true, 60, 34); // wheel
		stacks.init(2, true, 34, 8); // in
		stacks.init(3, false, 34, 60); // out
		stacks.set(ingredients);
	}

	@Override
	public LinkedList<IRecipeWrapper> getAllRecipes() {
		LinkedList<IRecipeWrapper> list = new LinkedList<>();
		for (RecipeGrinding recipe : RecipeGrinding.recipes) {
			list.add(new IRecipeWrapper() {
				@Override
				public void getIngredients(IIngredients ingredients) {
					List<List<ItemStack>> ins = new ArrayList<>(4);
					ins.add(Util.<ItemStack>listOfOne(new ItemStack(Main.precision_grinder_empty)));
					ins.add(recipe.getWheelList());
					ins.add(Arrays.asList(recipe.inputs));
					ingredients.setInputLists(VanillaTypes.ITEM, ins);

					ingredients.setOutputLists(VanillaTypes.ITEM, Util.<List<ItemStack>>listOfOne(//
							Arrays.asList(recipe.outputs)//
					));
				}
			});
		}
		return list;
	}

	// public static class JeiRecGrinding implements IRecipeWrapper {
	//
	// private List<ItemStack> grinder;
	// private List<ItemStack> grindingWheel;
	// private List<ItemStack> in;
	// private ItemStack out;
	//
	// private RecipeGrinding result;
	//
	// public JeiRecGrinding(Irio in, RecipeGrinding recipeGrinding) {
	// this.result = recipeGrinding;
	//
	// this.grinder = Util.listOfOneItemStack(Main.precision_grinder_empty);
	// this.grindingWheel = new LinkedList<ItemStack>();
	// for (Item wheel : recipeGrinding.levels)
	// grindingWheel.add(new ItemStack(wheel));
	// if (in.item.getHasSubtypes()) {
	// NonNullList<ItemStack> itemSubtypes = NonNullList.create();
	// in.item.getSubItems(CreativeTabs.SEARCH, itemSubtypes);
	// this.in = itemSubtypes;
	// }
	// this.in = Util.listOfOne(in.stack());
	// out = recipeGrinding.result;
	// }
	//
	// @Override
	// public void getIngredients(IIngredients ingredients) {
	// List<List<ItemStack>> ins = new LinkedList<>();
	// ins.add(grinder);
	// ins.add(grindingWheel);
	// ins.add(in);
	// ingredients.setInputLists(ItemStack.class, ins);
	// ingredients.setOutput(ItemStack.class, out);
	// }
	//
	// @Override
	// public String toString() {
	// return "[ " + in.get(0).getCount() + "x" + in.get(0).getUnlocalizedName() + "
	// -> " + out.getCount() + "x"
	// + out.getUnlocalizedName() + " ]";
	// }
	// }
	//

}
