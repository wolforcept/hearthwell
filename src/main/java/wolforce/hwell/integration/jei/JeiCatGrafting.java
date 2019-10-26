package wolforce.hwell.integration.jei;

import java.util.LinkedList;

import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import wolforce.hwell.Hwell;
import wolforce.hwell.Main;
import wolforce.hwell.blocks.BlockCore;
import wolforce.mechanics.Util;
import wolforce.mechanics.api.integration.JeiCat;

public class JeiCatGrafting extends JeiCat {

	public JeiCatGrafting() {
		super(Hwell.MODID, "Grafting Recipes", "grafting", 100, 58, Main.grafting_tray);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(1, true, 15, 8); // in
		stacks.init(2, false, 67, 8); // out
		stacks.set(ingredients);
	}

	public LinkedList<IRecipeWrapper> getAllRecipes() {
		LinkedList<IRecipeWrapper> list = new LinkedList<>();
		for (BlockCore core : Main.cores.values()) {
			list.add(new IRecipeWrapper() {

				@Override
				public void getIngredients(IIngredients ingredients) {
					ingredients.setInput(VanillaTypes.ITEM, new ItemStack(core));
					ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(Main.custom_grafts.get(core), 8));
				}

				@Override
				public void drawInfo(Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
					Util.drawTextCentered(mc.fontRenderer, "Life Cost: " + Main.graft_costs.get(core), 0, 42, 100, 11,
							0xFF000000, false);
				}
			});
		}
		return list;
	}
}
