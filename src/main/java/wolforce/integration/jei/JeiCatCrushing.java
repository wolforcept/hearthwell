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
import wolforce.blocks.BlockCore;
import wolforce.integration.jei.JeiCatCrushing.JeiRecCrushing;
import wolforce.recipes.Iri;
import wolforce.recipes.RecipeCoring;
import wolforce.recipes.RecipeCrushing;

public class JeiCatCrushing<T extends JeiRecCrushing> implements IRecipeCategory<JeiRecCrushing> {

	public static final String UID_CRUSHING = Main.MODID + ".crushing";

	static final ResourceLocation TEX = Util.res("textures/gui/crushing.png");
	static IDrawableStatic back;
	static private IDrawable icon;

	public JeiCatCrushing(IJeiHelpers helpers) {

		final IGuiHelper gui = helpers.getGuiHelper();

		back = gui.drawableBuilder(TEX, 0, 0, 60, 86).setTextureSize(60, 86).build();
		icon = gui.createDrawableIngredient(new ItemStack(Main.crushing_block));
	}

	@Override
	public String getUid() {
		return UID_CRUSHING;
	}

	@Override
	public String getTitle() {
		return "Crushing Recipes";
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
	public void setRecipe(IRecipeLayout recipeLayout, JeiRecCrushing recipe, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, true, 8, 34); // the crushing block
		stacks.init(1, true, 34, 8); // in
		stacks.init(2, false, 34, 60); // out
		stacks.set(ingredients);
	}

	//

	//

	//

	//

	//

	public static List<JeiRecCrushing> getAllRecipes() {
		List<JeiRecCrushing> list = new LinkedList<JeiRecCrushing>();
		for (Entry<Item, RecipeCrushing[]> entry : RecipeCrushing.getRecipeList()) {
			JeiRecCrushing recipe = new JeiRecCrushing(entry.getKey(), entry.getValue());
			System.out.println("added jei crushing recipe: " + recipe);
			list.add(recipe);
		}
		return list;
	}

	public static class JeiRecCrushing implements IRecipeWrapper {

		private ItemStack crushingBlock;
		private ItemStack in;
		private List<ItemStack> out;
		private boolean isSingleResult;
		private RecipeCrushing[] results;

		public JeiRecCrushing(Item in, RecipeCrushing[] results) {
			this.results = results;
			this.crushingBlock = new ItemStack(Main.crushing_block);
			this.in = new ItemStack(in);
			this.out = new LinkedList<>();
			for (int i = 0; i < results.length; i++) {
				this.out.add(results[i].item);
			}
		}

		@Override
		public void getIngredients(IIngredients ingredients) {
			LinkedList<ItemStack> ins = new LinkedList<>();
			ins.add(crushingBlock);
			ins.add(in);
			ingredients.setInputs(ItemStack.class, ins);
			if (isSingleResult) {
				ingredients.setOutput(ItemStack.class, out);
			} else {
				List<List<ItemStack>> outs = new LinkedList<>();
				for (ItemStack stack : out) {
					List<ItemStack> listofone = new LinkedList<>();
					listofone.add(stack);
					outs.add(listofone);
				}
				ingredients.setOutputLists(ItemStack.class, outs);
			}
		}

		@Override
		public String toString() {
			return "[ " + in.getUnlocalizedName() + " -> " + out.size() + " ]";
		}
	}

}
