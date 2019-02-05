package integration.jei;

import java.util.Collection;
import java.util.Collections;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import scala.actors.threadpool.Arrays;
import wolforce.Main;
import wolforce.Hwell;
import wolforce.Util;
import wolforce.recipes.RecipeFreezer;

public class JeiCatFreezing<T extends RecipeFreezer> implements IRecipeCategory<RecipeFreezer> {

	public static final String UID_FREEZING = Hwell.MODID + ".freezing";

	static final ResourceLocation TEX = Util.res("textures/gui/freezing.png");
	static IDrawableStatic back;
	static private IDrawable icon;

	public JeiCatFreezing(IJeiHelpers helpers) {

		final IGuiHelper gui = helpers.getGuiHelper();

		back = gui.drawableBuilder(TEX, 0, 0, 98, 56).setTextureSize(98, 56).build();
		icon = gui.createDrawableIngredient(new ItemStack(Main.freezer));
	}

	@Override
	public String getUid() {
		return UID_FREEZING;
	}

	@Override
	public String getTitle() {
		return "Freezer Recipes";
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
	public void setRecipe(IRecipeLayout recipeLayout, RecipeFreezer recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();

		// the freezer block
		stacks.init(0, true, 40, 30);
		stacks.set(0, new ItemStack(Main.freezer));

		// in
		FluidStack fluid = Util.vanillaFluidToFluidStack(recipe.blockIn);
		if (fluid != null) {
			recipeLayout.getFluidStacks().init(0, true, 9, 31);
			recipeLayout.getFluidStacks().set(0, fluid);
		}

		// out
		stacks.init(1, false, 72, 30);
		stacks.set(1, Util.toItemStackList(recipe.blocksOut));
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
