package integration.jei;

import java.util.Collection;
import java.util.LinkedList;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import wolforce.Hwell;
import wolforce.Main;
import wolforce.Util;
import wolforce.recipes.RecipeFreezer;

public class JeiCatTubing implements IRecipeCategory {

	public static final String UID_TUBING = Hwell.MODID + ".tubing";

	static final ResourceLocation TEX = Util.res("textures/gui/tubing.png");
	static IDrawableStatic back;
	static private IDrawable icon;

	public JeiCatTubing(IJeiHelpers helpers) {

		final IGuiHelper gui = helpers.getGuiHelper();

		back = gui.drawableBuilder(TEX, 0, 0, 70, 90).setTextureSize(70, 90).build();
		icon = gui.createDrawableIngredient(new ItemStack(Main.furnace_tube));
	}

	@Override
	public String getUid() {
		return UID_TUBING;
	}

	@Override
	public String getTitle() {
		return "Tube Recipes";
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public String getModName() {
		return Hwell.MODID;
	}

	@Override
	public IDrawable getBackground() {
		return back;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipe, IIngredients ingredients) {

		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.set(ingredients);
		//
		// // the tube block
		// stacks.init(0, true, 8, 32);
		// stacks.set(0, new ItemStack(Main.furnace_tube));
		//
		// // in
		// stacks.init(1, true, 8, 64);
		// stacks.set(1, new ItemStack(recipe.in));
		//
		// // out
		// stacks.init(2, false, 44, 64);
		// stacks.set(2, new ItemStack(recipe.out));
		// // stacks.set(ingredients);
		//
		// FluidStack fluid = Util.vanillaFluidBlockToFluidStack(recipe.out);
		// if (fluid != null) {
		// recipeLayout.getFluidStacks().init(0, true, 45, 65);
		// recipeLayout.getFluidStacks().set(0, fluid);
		// }

	}

	public static Collection<?> getAllRecipes() {
		LinkedList<IRecipeWrapper> recipeWrappers = new LinkedList<>();
		for (RecipeFreezer recipe : RecipeFreezer.recipes) {
			IRecipeWrapper recipeWrapper = new IRecipeWrapper() {

				@Override
				public void getIngredients(IIngredients ingredients) {
					// TODO ingredients.setInputLists(Block.class, Main.furnace_tube);
					// Util.setIngredients(ingredients, new Block[] { , in }, new Block[] { out });
				}
			};
		}
		return recipeWrappers;
	}

	//

	//

	//

	//

	//

	// public static class JeiRecTubing implements IRecipeWrapper {
	//
	// private ItemStack tube;
	// private ItemStack in;
	// private ItemStack out;
	//
	// public JeiRecTubing(Block in, Block out) {
	// this.tube = new ItemStack(Main.furnace_tube);
	// this.in = new ItemStack(in);
	// this.out = new ItemStack(out);
	// }
	//
	// @Override
	// public void getIngredients(IIngredients ingredients) {
	// List<List<ItemStack>> ins = new LinkedList<>();
	// ins.add(Util.listOfOneItemStack(tube));
	// ins.add(Util.listOfOneItemStack(in));
	// ingredients.setInputLists(ItemStack.class, ins);
	// ingredients.setOutput(ItemStack.class, Util.listOfOneItemStack(out));
	// }
	//
	// @Override
	// public String toString() {
	// return "[ " + in.getUnlocalizedName() + " -> " + out + " ]";
	// }
	// }

}
