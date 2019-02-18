package wolforce;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Hwell.MODID, category = "everything")
// @Config.LangKey("hwell.config.title")
public class HwellConfig {

	@Config.Comment({ "Where the base recipes file is located (default: config/hwell_recipes.json)" })
	public static String recipeFileLocation = "config/hwell_recipes.json";

	// @Config.Comment({ "If this is true, when failing to read the recipes file,
	// the file will be (default: config/hwell_recipes.json)" })
	// public static boolean alwaysWriteRecipes = true;

	@Config.Comment({ "Build multiblocks automatically when they are needed. "
			+ "This should always be false except for debugging (default: false)" })
	public static boolean isAutomaticMultiblocks = false;

	// ----------------------------------------

	// PULLER
	@Config.Comment({ "Puller Energy Consumption per Operation (default: 100)" })
	public static int energyConsumptionPuller = 100;
	@Config.Comment({ "Puller Delay Between pulls (default: 3000)" })
	public static int pullerDelay = 3000;
	@Config.Comment({ "Puller chance to get a filtered item (default: 0.5)" })
	public static double pullerChanceToGetFilteredPull = 0.5;

	// GRINDER
	@Config.Comment({ "Grinder Energy Consumption per Operation (default: 100)" })
	public static int energyConsumptionGrinder = 100;

	// SEPARATOR
	@Config.Comment({ "Separator Energy Consumption per Operation (default: 100)" })
	public static int energyConsumptionSeparator = 100;

	// FREEZER
	@Config.Comment({ "Freezer Energy Consumption per Operation  (default: 30)" })
	public static int energyConsumptionFreezer = 30;

	@Config.Comment({ "Range of freezer? (default: 5)" })
	@Config.RangeInt(min = 1, max = 16)
	public static int freezerRange = 5;

	@Config.Comment({ "Does freezer only work in night time? (default: true)" })
	public static boolean isFreezerRequiredToBeNight = true;

	// LOOT
	@Config.Comment({ "Number of ticks that a loot kit need to hatch. 20 ticks = 1 second. (default: 600)" })
	@Config.RangeInt(min = 40, max = 16000)
	public static int lootTimeToHatch = 600;

	// // HEAT FURNACE
	// @Config.Comment({ "Heat Furnace Energy Consumption per Operation (default:
	// 100)" })
	// public static int energyConsumptionFurnace = 100;

	// GRAVITY
	@Config.Comment({ "Range of Gravity Block? (default: 5)" })
	@Config.RangeInt(min = 1, max = 16)
	public static int gravityBlockDistance = 5;

	@Config.Comment({ "Range of Mini Gravity Block? (default: 1)" })
	@Config.RangeInt(min = 1, max = 16)
	public static int gravityBlockDistanceMini = 1;
	// ----------------------------------------

	// TUBES
	@Config.Comment({ "Is tube required to see sky? (default: true)" })
	public static boolean isTubeRequiredToSeeSky = true;

	@Config.Comment({ "Does tube only work in day time? (default: true)" })
	public static boolean isTubeRequiredToBeDay = true;

	// LIGHT COLLECTOR
	@Config.Comment({ "Is light collector required to see sky? (default: true)" })
	public static boolean isLightCollectorRequiredToSeeSky = true;

	@Config.Comment({ "Does Light Collector only work in day time? (default: true)" })
	public static boolean isLightCollectorRequiredToBeDay = true;

	// @Config.Comment({ "General settings" })
	// public static General general = new General();
	//
	// @Config.Comment({ "Liquid settings" })
	// public static Liquid liquid = new Liquid();
	//
	// @Config.Comment({ "Crafting settings" })
	// public static Crafting crafting = new Crafting();
	//
	//
	// public static class General {
	// @Config.Comment("Water based liquids causing nausea when swam in [default:
	// true]")
	// public boolean liquidsCausesNausea = true;
	//
	// @Config.Comment("Makes liquid ore have a 1 in X chance of drying into an ore
	// block (higher = less chance) [default: 64]")
	// @Config.RangeInt(min = 6, max = 640)
	// public int oreChance = 64;
	// }
	//
	// public static class Liquid {
	// @Config.Comment("The time liquid dirt types take to solidify [default: 220]")
	// @Config.RangeInt(min = 6, max = 640)
	// public int dirtSolidifyTime = 220;
	// }
	//
	// public static class Crafting {
	// @Config.Comment("Enable crafting buckets with ice [default: true]")
	// public boolean enableCraftingWithIce = true;
	// }

	//
	// WHAT IS THIS
	//

	@Mod.EventBusSubscriber(modid = Hwell.MODID)
	private static class EventHandler {

		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Hwell.MODID)) {
				ConfigManager.sync(Hwell.MODID, Config.Type.INSTANCE);
			}
		}
	}
}

// THIS BECOMES:

// # Configuration file
//
// everything {
//
// ##########################################################################################################
// # general
// #--------------------------------------------------------------------------------------------------------#
// # General settings
// ##########################################################################################################
//
// general {
// # Water based liquids causing nausea when swam in [default: true]
// B:liquidsCausesNausea=true
//
// # Makes liquid ore have a 1 in X chance of drying into an ore block (higher =
// less chance) [default: 64]
// # Min: 6
// # Max: 640
// I:oreChance=64
// }
//
// ##########################################################################################################
// # liquid
// #--------------------------------------------------------------------------------------------------------#
// # Liquid settings
// ##########################################################################################################
//
// liquid {
// # The time liquid dirt types take to solidify [default: 220]
// # Min: 6
// # Max: 640
// I:dirtSolidifyTime=220
// }
//
// ##########################################################################################################
// # crafting
// #--------------------------------------------------------------------------------------------------------#
// # Crafting settings
// ##########################################################################################################
//
// crafting {
// # Enable crafting buckets with ice [default: true]
// B:enableCraftingWithIce=true
// }
//
// }
//
//
// "hearth well configuration settings" {
//
// general {
// B:liquidsCausesNausea=true
// I:oreChance=64
// }
//
// liquid {
// I:dirtSolidifyTime=220
// }
//
// crafting {
// B:enableCraftingWithIce=true
// }
//
// }
