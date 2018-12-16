package wolforce.integration.jei;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

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
import wolforce.integration.jei.JeiCatSeparating.JeiRecSeparating;
import wolforce.recipes.Irio;
import wolforce.recipes.RecipeGrinding;
import wolforce.recipes.RecipeSeparator;

public class JeiCatSeparating<T extends JeiRecSeparating> implements IRecipeCategory<JeiRecSeparating> {

	public static final String UID_SEPARATOR = Main.MODID + ".separating";

	static final ResourceLocation TEX = Util.res("textures/gui/separating.png");
	static IDrawableStatic back;
	static private IDrawable icon;

	public JeiCatSeparating(IJeiHelpers helpers) {

		final IGuiHelper gui = helpers.getGuiHelper();

		back = gui.drawableBuilder(TEX, 0, 0, 106, 108).setTextureSize(106, 108).build();
		icon = gui.createDrawableIngredient(new ItemStack(Main.separator));
	}

	@Override
	public String getUid() {
		return UID_SEPARATOR;
	}

	@Override
	public String getTitle() {
		return "Separator Recipes";
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
	public void setRecipe(IRecipeLayout recipeLayout, JeiRecSeparating recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 44, 45); // separator
		stacks.init(1, true, 44, 8); // in
		stacks.init(2, false, 8, 45); // out1
		stacks.init(3, false, 80, 45); // out2
		stacks.init(4, false, 44, 82); // out3
		stacks.set(ingredients);
	}

	//

	//

	//

	//

	//

	public static List<JeiRecSeparating> getAllRecipes() {
		List<JeiRecSeparating> list = new LinkedList<JeiRecSeparating>();
		for (Entry<Irio, RecipeSeparator> entry : RecipeSeparator.getRecipes()) {
			JeiRecSeparating recipe = new JeiRecSeparating(entry.getKey(), entry.getValue());
			System.out.println("added jei separator recipe: " + recipe);
			list.add(recipe);
		}
		return list;
	}

	public static class JeiRecSeparating implements IRecipeWrapper {

		private ItemStack separator;
		private ItemStack in;
		private ItemStack out1, out2, out3;

		public JeiRecSeparating(Irio in, RecipeSeparator recipe) {

			this.separator = new ItemStack(Main.separator);
			this.in = in.stack();
			this.out1 = recipe.left;
			this.out2 = recipe.right;
			this.out3 = recipe.back;
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			List<List<ItemStack>> ins = new LinkedList<>();
			ins.add(Util.listOfOneItemStack(separator));
			ins.add(Util.listOfOneItemStack(in));
			ingredients.setInputLists(ItemStack.class, ins);
			List<List<ItemStack>> outs = new LinkedList<>();
			outs.add(Util.listOfOneItemStack(out1));
			outs.add(Util.listOfOneItemStack(out2));
			outs.add(Util.listOfOneItemStack(out3));
			ingredients.setOutputLists(ItemStack.class, outs);
		}

		@Override
		public String toString() {
			return "[ " + in.getUnlocalizedName() + " -> " + //
					out1.getCount() + "x" + out1.getUnlocalizedName() + "," + //
					out2.getCount() + "x" + out2.getUnlocalizedName() + "," + //
					out3.getCount() + "x" + out3.getUnlocalizedName() + " ]";
		}
	}

}
