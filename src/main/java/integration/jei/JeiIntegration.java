package integration.jei;

import java.util.LinkedList;
import java.util.List;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import wolforce.Main;
import wolforce.Util;
import wolforce.recipes.RecipeFreezer;
import wolforce.recipes.RecipeTube;

@JEIPlugin
public class JeiIntegration implements IModPlugin {

	private static IIngredientRegistry ingreg;

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		IModPlugin.super.onRuntimeAvailable(jeiRuntime);

		List<ItemStack> templist1 = new LinkedList<>();
		List<ItemStack> templist2 = new LinkedList<>();
		templist1.add(new ItemStack(Main.empowered_displacer));
		ingreg.removeIngredientsAtRuntime(ItemStack.class, templist1);
		ItemStack item = new ItemStack(Main.empowered_displacer);
		item.addEnchantment(Enchantments.SILK_TOUCH, 1);
		templist2.add(item);
		ingreg.addIngredientsAtRuntime(ItemStack.class, templist2);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration reg) {

		final IJeiHelpers helpers = reg.getJeiHelpers();

		reg.addRecipeCategories(new JeiCatCoring(helpers, Main.core_stone));
		reg.addRecipeCategories(new JeiCatCoring(helpers, Main.core_heat));
		reg.addRecipeCategories(new JeiCatCoring(helpers, Main.core_green));
		reg.addRecipeCategories(new JeiCatCoring(helpers, Main.core_sentient));

		reg.addRecipeCategories(new JeiCatCrushing<>(helpers));

		reg.addRecipeCategories(new JeiCatGrinding<>(helpers));

		reg.addRecipeCategories(new JeiCatSeparating<>(helpers));

		reg.addRecipeCategories(new JeiCatTubing(helpers));

		reg.addRecipeCategories(new JeiCatFreezing(helpers));

		// HwellCatergory coring = new HwellCatergory("coring", "Coring", Main.MODID,
		// res(guiHelper, "items/stone_core"));
		// reg.addRecipeCategories(coring);
	}

	@Override
	public void register(IModRegistry reg) {

		ingreg = reg.getIngredientRegistry();
		IJeiHelpers jeiHelpers = reg.getJeiHelpers();

		// crafting helper +
		// IRecipeTransferRegistry recipeTransfer = reg.getRecipeTransferRegistry();

		reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_stone), JeiCatCoring.UID_CORING_STONE);
		reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_heat), JeiCatCoring.UID_CORING_HEAT);
		reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_green), JeiCatCoring.UID_CORING_GREEN);
		reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_sentient), JeiCatCoring.UID_CORING_SENTIENT);

		reg.addRecipes(JeiCatCrushing.getAllRecipes(), JeiCatCrushing.UID_CRUSHING);

		reg.addRecipes(JeiCatGrinding.getAllRecipes(), JeiCatGrinding.UID_GRINDING);

		reg.addRecipes(JeiCatSeparating.getAllRecipes(), JeiCatSeparating.UID_SEPARATOR);

		reg.addRecipes(JeiCatTubing.getRecipes(), JeiCatTubing.UID_TUBING);

		reg.addRecipes(JeiCatFreezing.getRecipes(), JeiCatFreezing.UID_FREEZING);

		// reg.addRecipeClickArea(guiContainerClass, xPos, yPos, width, height,
		// recipeCategoryUids);

		reg.addIngredientInfo(new ItemStack(Blocks.SNOW), ItemStack.class, "Obtained by placing a freezer near water.");

		reg.addIngredientInfo(new ItemStack(Blocks.ICE), ItemStack.class, "Obtained by placing a freezer near water.");

		reg.addIngredientInfo(new ItemStack(Main.crystal_nether), ItemStack.class,
				"Obtained by throwing a crystal into the nether portal.");

		reg.addIngredientInfo(new ItemStack(Main.core_heat), ItemStack.class,
				"Obtained by right clicking a HeatBlock with a flint and steel.", "Just be careful...");
	}

	// public static String translateToLocal(String key) {
	// if (I18n.canTranslate(key))
	// return I18n.translateToLocal(key);
	// return I18n.translateToFallback(key);
	// }

	// public static String translateToLocalFormated(String key, Object... format) {
	// String s = translateToLocal(key);
	// try {
	// return String.format(s, format);
	// } catch (IllegalFormatException e) {
	// return "Format error: " + s;
	// }
	// }
}
