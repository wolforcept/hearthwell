package wolforce.integration;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IAdvancedGuiHandler;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IIngredientType;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import wolforce.Main;

@JEIPlugin
public class WithJei implements IModPlugin {

	// @Override
	// public void registerCategories(IRecipeCategoryRegistration reg) {
	// IGuiHelper guiHelper = reg.getJeiHelpers().getGuiHelper();
	//
	// HwellCatergory coring = new HwellCatergory("coring", "Coring", Main.MODID,
	// res(guiHelper, "items/stone_core"));
	// reg.addRecipeCategories(coring);
	// }

	@Override
	public void register(IModRegistry reg) {

		reg.addIngredientInfo(new ItemStack(Main.core_stone), ItemStack.class, "Allows the creation of:", //
				"Coal Block from Wood Logs", //
				"Iron Block from Stone / Cobblestone and Sandstone", //
				"Gold Block from Iron Blocks", //
				" from ", //
				"Clay Block	Water", //
				"Bone Block	Snow", //
				"Redstone Block Netherrack / Magma Block");
		
//		reg.addIngredientInfo(new ItemStack(Main.core_heat), ItemStack.class, "Allows the creation of:", //
//				"Gunpowder Block from ", //
//				"Iron Block	from Stone, Cobblestone and Sandstone", //
//				"Glowstone from Gold Block", //
//				"Netherrack from Coal Blocks / Charcoal Blocks", //
//				"Sea Lantern from Snow / Ice / Packed Ice", //
//				"Bone Block Snow", //
//				"Redstone Block Netherrack / Magma Block");
	}

	//

	//

	//

	// private IDrawable res(IGuiHelper gh, String location) {
	// return gh.createDrawable(new ResourceLocation(Main.MODID, location), 16, 16,
	// 64, 64);
	// }
	//
	// private static class HwellWrapper implements IRecipeWrapper {
	// @Override
	// public void getIngredients(IIngredients ingredients) {
	//
	// }
	// }
	//
	// private static class HwellCatergory implements
	// IRecipeCategory<IRecipeWrapper> {
	//
	// String uid, title, modName;
	// IDrawable back;
	//
	// public HwellCatergory(String uid, String title, String modName, IDrawable
	// back) {
	// this.uid = uid;
	// this.title = title;
	// this.modName = modName;
	// this.back = back;
	// }
	//
	// @Override
	// public String getUid() {
	// return uid;
	// }
	//
	// @Override
	// public String getTitle() {
	// return title;
	// }
	//
	// @Override
	// public String getModName() {
	// return modName;
	// }
	//
	// @Override
	// public IDrawable getBackground() {
	// return back;
	// }
	//
	// @Override
	// public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper
	// recipeWrapper, IIngredients ingredients) {
	// IGuiItemStackGroup items = recipeLayout.getItemStacks();
	// // items.ini
	// }
	//
	// }

}
