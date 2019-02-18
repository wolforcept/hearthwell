package integration.jei;

import java.util.Map.Entry;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;
import wolforce.Hwell;
import wolforce.Main;
import wolforce.blocks.BlockCore;

@JEIPlugin
public class JeiIntegration implements IModPlugin {

	private IIngredientRegistry ingReg;

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

	}

	@Override
	public void register(IModRegistry reg) {

		ingReg = reg.getIngredientRegistry();
		IJeiHelpers jeiHelpers = reg.getJeiHelpers();

		reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_stone), JeiCatCoring.UID_CORING_STONE);
		reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_heat), JeiCatCoring.UID_CORING_HEAT);
		reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_green), JeiCatCoring.UID_CORING_GREEN);
		reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_sentient), JeiCatCoring.UID_CORING_SENTIENT);

		for (Entry<String, BlockCore> entry : Main.custom_cores.entrySet()) {
			BlockCore core = entry.getValue();
			reg.addRecipes(JeiCatCoring.getAllRecipes(core), Hwell.MODID + ".coring." + core.getRegistryName().getResourcePath());
		}
		reg.addRecipes(JeiCatCrushing.getAllRecipes(), JeiCatCrushing.UID_CRUSHING);
		reg.addRecipes(JeiCatGrinding.getAllRecipes(), JeiCatGrinding.UID_GRINDING);
		reg.addRecipes(JeiCatSeparating.getAllRecipes(), JeiCatSeparating.UID_SEPARATOR);
		reg.addRecipes(JeiCatTubing.getAllRecipes(), JeiCatTubing.UID_TUBING);
		reg.addRecipes(JeiCatFreezing.getAllRecipes(), JeiCatFreezing.UID_FREEZING);

		reg.addIngredientInfo(new ItemStack(Main.crystal_nether), VanillaTypes.ITEM,
				"Obtained by throwing a crystal into the nether portal.");
		reg.addIngredientInfo(new ItemStack(Main.core_heat), VanillaTypes.ITEM,
				"Obtained by right clicking a HeatBlock with a flint and steel.", "Just be careful...");

	}

	// TODO Add enchantment to JEI Obsidian displacer
	// @Override
	// public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	// IModPlugin.super.onRuntimeAvailable(jeiRuntime);
	//
	// List<ItemStack> removeThese = new LinkedList<>();
	// removeThese.add(new ItemStack(Main.empowered_displacer));
	// ingReg.removeIngredientsAtRuntime(VanillaTypes.ITEM, removeThese);
	//
	// List<ItemStack> addThese = new LinkedList<>();
	// ItemStack item = new ItemStack(Main.empowered_displacer);
	// item.addEnchantment(Enchantments.SILK_TOUCH, 1);
	// addThese.add(item);
	// ingReg.addIngredientsAtRuntime(VanillaTypes.ITEM, addThese);
	// }

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
