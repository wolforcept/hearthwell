package integration.jei;

import java.util.LinkedList;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
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

	// private IIngredientRegistry ingReg;

	private LinkedList<JeiCat> jeiCats;

	@Override
	public void registerCategories(IRecipeCategoryRegistration reg) {

		final IJeiHelpers helpers = reg.getJeiHelpers();
		JeiCat.helpers = helpers;

		jeiCats = new LinkedList<JeiCat>();

		// jeiCats.add(new JeiCatCoring(Main.core_stone));
		// jeiCats.add(new JeiCatCoring(Main.core_anima));
		// jeiCats.add(new JeiCatCoring(Main.core_heat));
		// jeiCats.add(new JeiCatCoring(Main.core_green));
		// jeiCats.add(new JeiCatCoring(Main.core_sentient));

		for (BlockCore core : Main.cores.values())
			jeiCats.add(new JeiCatCoring(core));

		jeiCats.add(new JeiCatBoxing());
		jeiCats.add(new JeiCatCharging());
		jeiCats.add(new JeiCatCrushing());
		jeiCats.add(new JeiCatFreezing());
		jeiCats.add(new JeiCatGrinding());
		jeiCats.add(new JeiCatMutating());
		jeiCats.add(new JeiCatPortal());
		jeiCats.add(new JeiCatPulling());
		jeiCats.add(new JeiCatPullingFiltered());
		jeiCats.add(new JeiCatSeparating());
		jeiCats.add(new JeiCatTubing());
		jeiCats.add(new JeiCatGrafting());

		for (JeiCat cat : jeiCats) {
			reg.addRecipeCategories(cat);
		}

		// JeiCat coreStone;
		// JeiCat coreHeat;
		// JeiCat coreGreen;
		// JeiCat coreSentient;
		//
		// JeiCat boxing;
		// JeiCat crushing;
		// JeiCat freezing;
		// JeiCat grinding;
		// JeiCat mutating;
		// JeiCat separating;
		// JeiCat tubing;

		// reg.addRecipeCategories(new JeiCatCoring(Main.core_stone));
		// reg.addRecipeCategories(new JeiCatCoring(Main.core_anima));
		// reg.addRecipeCategories(new JeiCatCoring(Main.core_heat));
		// reg.addRecipeCategories(new JeiCatCoring(Main.core_green));
		// reg.addRecipeCategories(new JeiCatCoring(Main.core_sentient));
		// for (Entry<String, BlockCore> entry : Main.custom_cores.entrySet()) {
		// BlockCore core = entry.getValue();
		// reg.addRecipeCategories(new JeiCatCoring(helpers, core));
		// }

	}

	@Override
	public void register(IModRegistry reg) {

		// ingReg = reg.getIngredientRegistry();
		// IJeiHelpers jeiHelpers = reg.getJeiHelpers();

		// reg.addRecipeCatalyst(new ItemStack(Main.core_stone),
		// JeiCatCoring.UID_CORING_STONE);
		// reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_stone),
		// JeiCatCoring.UID_CORING_STONE);
		// reg.addRecipeCatalyst(new ItemStack(Main.core_anima),
		// JeiCatCoring.UID_CORING_ANIMA);
		// reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_anima),
		// JeiCatCoring.UID_CORING_ANIMA);
		// reg.addRecipeCatalyst(new ItemStack(Main.core_heat),
		// JeiCatCoring.UID_CORING_HEAT);
		// reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_heat),
		// JeiCatCoring.UID_CORING_HEAT);
		// reg.addRecipeCatalyst(new ItemStack(Main.core_green),
		// JeiCatCoring.UID_CORING_GREEN);
		// reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_green),
		// JeiCatCoring.UID_CORING_GREEN);
		// reg.addRecipeCatalyst(new ItemStack(Main.core_sentient),
		// JeiCatCoring.UID_CORING_SENTIENT);
		// reg.addRecipes(JeiCatCoring.getAllRecipes(Main.core_sentient),
		// JeiCatCoring.UID_CORING_SENTIENT);
		// for (Entry<String, BlockCore> entry : Main.custom_cores.entrySet()) {
		// BlockCore core = entry.getValue();
		// reg.addRecipes(JeiCatCoring.getAllRecipes(core),
		// JeiCatCoring.makeCustomCoreUID(core));
		// }

		// PRECISION GRINDER
		// reg.addRecipeCatalyst(new ItemStack(Main.precision_grinder_empty),
		// JeiCatGrinding.UID_GRINDING);
		// reg.addRecipes(JeiCatGrinding.getAllRecipes(), JeiCatGrinding.UID_GRINDING);
		//
		// // SEPARATOR
		// reg.addRecipeCatalyst(new ItemStack(Main.separator),
		// JeiCatSeparating.UID_SEPARATOR);
		// reg.addRecipes(JeiCatSeparating.getAllRecipes(),
		// JeiCatSeparating.UID_SEPARATOR);
		//
		// // FREEZER
		// reg.addRecipeCatalyst(new ItemStack(Main.freezer),
		// JeiCatFreezing.UID_FREEZING);
		// reg.addRecipes(JeiCatFreezing.getAllRecipes(), JeiCatFreezing.UID_FREEZING);

		for (JeiCat cat : jeiCats) {
			for (ItemStack catalyst : cat.getCatalysts()) {
				reg.addRecipeCatalyst(catalyst, cat.getUid());
			}
			reg.addRecipes(cat.getAllRecipes(), cat.getUid());
		}

		//

		// INFOS

		reg.addIngredientInfo(new ItemStack(Main.crystal_nether), VanillaTypes.ITEM,
				"Obtained by throwing a crystal into the nether portal.");
		reg.addIngredientInfo(new ItemStack(Main.cores.get("core_anima")), VanillaTypes.ITEM,
				"Obtained by growing an Inert Seed.", "You must stay within 4 blocks of it.");
		reg.addIngredientInfo(new ItemStack(Main.inert_seed), VanillaTypes.ITEM,
				"If you stay within 4 blocks of it, it will slowly grow and become an Anima Core.");
		reg.addIngredientInfo(new ItemStack(Main.cores.get("core_heat")), VanillaTypes.ITEM,
				"Obtained by right clicking a Heat Block with a flint and steel.", "Just be careful...");
		reg.addIngredientInfo(new ItemStack(Main.empty_rod), VanillaTypes.ITEM, "Hold right click to use.");
		reg.addIngredientInfo(new ItemStack(Main.branch), VanillaTypes.ITEM, "Drop from Breaking Trees on Grit Vases.");

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("patchouli:book", "hwell:book_of_the_well");
		ItemStack book = new ItemStack(Item.getByNameOrId("patchouli:guide_book"), 1, 0, nbt);
		book.setTagInfo("patchouli:book", new NBTTagString("hwell:book_of_the_well"));
		reg.addIngredientInfo(book, VanillaTypes.ITEM,
				"To get this item you need two wooden pressure plates, one on each hand. "
						+ "Then say an HONEST HEARTFELT prayer to the gods containing, but not only, the words \"poor\", \"please\", \"send\", \"book\", \"gods\". "
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
