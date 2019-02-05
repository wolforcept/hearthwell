package integration.jei;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import integration.jei.JeiCatGrinding.JeiRecGrinding;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;
import wolforce.Main;
import wolforce.Util;
import wolforce.recipes.Irio;
import wolforce.recipes.RecipeGrinding;

public class JeiCatGrinding<T extends JeiRecGrinding> implements IRecipeCategory<JeiRecGrinding> {

	public static final String UID_GRINDING = Main.MODID + ".grinding";

	static final ResourceLocation TEX = Util.res("textures/gui/grinding.png");
	static IDrawableStatic back;
	static private IDrawable icon;

	public JeiCatGrinding(IJeiHelpers helpers) {

		final IGuiHelper gui = helpers.getGuiHelper();

		back = gui.drawableBuilder(TEX, 0, 0, 86, 86).setTextureSize(86, 86).build();
		icon = gui.createDrawableIngredient(new ItemStack(Main.precision_grinder_empty));
	}

	@Override
	public String getUid() {
		return UID_GRINDING;
	}

	@Override
	public String getTitle() {
		return "Grinding Recipes";
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public String getModName() {
		return Main.MODID;
	}

	@Override
	public IDrawable getBackground() {
		return back;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, JeiRecGrinding recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 8, 34); // grinder
		stacks.init(1, true, 60, 34); // wheel
		stacks.init(2, true, 34, 8); // in
		stacks.init(3, false, 34, 60); // out
		stacks.set(ingredients);
	}

	//

	//

	//

	//

	//

	public static List<JeiRecGrinding> getAllRecipes() {
		List<JeiRecGrinding> list = new LinkedList<JeiRecGrinding>();
		for (Entry<Irio, RecipeGrinding> entry : RecipeGrinding.getRecipeList()) {
			JeiRecGrinding recipe = new JeiRecGrinding(entry.getKey(), entry.getValue());
			System.out.println("added jei crushing recipe: " + recipe);
			list.add(recipe);
		}
		return list;
	}

	public static class JeiRecGrinding implements IRecipeWrapper {

		private List<ItemStack> grinder;
		private List<ItemStack> grindingWheel;
		private List<ItemStack> in;
		private ItemStack out;

		private RecipeGrinding result;

		public JeiRecGrinding(Irio in, RecipeGrinding recipeGrinding) {
			this.result = recipeGrinding;

			this.grinder = Util.listOfOneItemStack(Main.precision_grinder_empty);
			this.grindingWheel = new LinkedList<ItemStack>();
			for (Item wheel : recipeGrinding.levels)
				grindingWheel.add(new ItemStack(wheel));
			this.in = Util.listOfOne(in.stack());
			out = recipeGrinding.result;
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			List<List<ItemStack>> ins = new LinkedList<>();
			ins.add(grinder);
			ins.add(grindingWheel);
			ins.add(in);
			ingredients.setInputLists(ItemStack.class, ins);
			ingredients.setOutput(ItemStack.class, out);
		}

		@Override
		public String toString() {
			return "[ " + in.get(0).getCount() + "x" + in.get(0).getUnlocalizedName() + " -> " + out.getCount() + "x"
					+ out.getUnlocalizedName() + " ]";
		}
	}

}
