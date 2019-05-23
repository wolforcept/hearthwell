package integration.jei;

import java.util.Map.Entry;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.BlockCore;
import wolforce.registry.RegisterRecipes;

@JEIPlugin
public class JeiIntegration implements IModPlugin {

	private IIngredientRegistry ingReg;

	@Override
	public void registerCategories(IRecipeCategoryRegistration reg) {

		final IJeiHelpers helpers = reg.getJeiHelpers();

		reg.addRecipeCategories(new JeiCatCoring(helpers, Main.core_stone));
		reg.addRecipeCategories(new JeiCatCoring(helpers, Main.core_anima));
		reg.addRecipeCategories(new JeiCatCoring(helpers, Main.core_heat));
		reg.addRecipeCategories(new JeiCatCoring(helpers, Main.core_green));
		reg.addRecipeCategories(new JeiCatCoring(helpers, Main.core_sentient));
		for (Entry<String, BlockCore> entry : Main.custom_cores.entrySet()) {
			BlockCore core = entry.getValue();
			reg.addRecipeCategories(new JeiCatCoring(helpers, core));
		}

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
		reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_anima), JeiCatCoring.UID_CORING_ANIMA);
		reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_heat), JeiCatCoring.UID_CORING_HEAT);
		reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_green), JeiCatCoring.UID_CORING_GREEN);
		reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_sentient), JeiCatCoring.UID_CORING_SENTIENT);
		for (Entry<String, BlockCore> entry : Main.custom_cores.entrySet()) {
			BlockCore core = entry.getValue();
			reg.addRecipes(JeiCatCoring.getAllRecipes(core), JeiCatCoring.makeCustomCoreUID(core));
		}

		reg.addRecipes(JeiCatCrushing.getAllRecipes(), JeiCatCrushing.UID_CRUSHING);
		reg.addRecipes(JeiCatGrinding.getAllRecipes(), JeiCatGrinding.UID_GRINDING);
		reg.addRecipes(JeiCatSeparating.getAllRecipes(), JeiCatSeparating.UID_SEPARATOR);
		reg.addRecipes(JeiCatTubing.getAllRecipes(), JeiCatTubing.UID_TUBING);
		reg.addRecipes(JeiCatFreezing.getAllRecipes(), JeiCatFreezing.UID_FREEZING);

		reg.addIngredientInfo(new ItemStack(Main.crystal_nether), VanillaTypes.ITEM,
				"Obtained by throwing a crystal into the nether portal.");
		reg.addIngredientInfo(new ItemStack(Main.core_heat), VanillaTypes.ITEM,
				"Obtained by right clicking a Heat Block with a flint and steel.", "Just be careful...");
		reg.addIngredientInfo(new ItemStack(Main.empty_rod), VanillaTypes.ITEM, "Hold right click to use.");

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("patchouli:book", "hwell:book_of_the_well");
		ItemStack book = new ItemStack(Item.getByNameOrId("patchouli:guide_book"), 1, 0, nbt);
		book.setTagInfo("patchouli:book", new NBTTagString("hwell:book_of_the_well"));
		reg.addIngredientInfo(book, VanillaTypes.ITEM, "To get this item you need two wooden pressure plates, one on each hand. "
				+ "Then say a heartfelt prayer to the gods containing the words \"poor\", \"please\", \"send\", \"book\", \"gods\". "
				+ "Quickly after that, while looking directly up, smash the pressure plates together with right click!");
		// System.out.println("AAAAAAAAAAAAAA" + RegisterRecipes.recipePowerCrystal);
		reg.addRecipes(Util.listOfOne(RegisterRecipes.recipePowerCrystal), VanillaRecipeCategoryUid.CRAFTING);
		// reg.addRecipeCatalyst(Main.core_stone, JeiCatCoring.UID_CORING_STONE);

		// reg.addRecipes(recipes, "");
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
