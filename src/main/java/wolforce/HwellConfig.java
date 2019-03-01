package wolforce;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Hwell.MODID, category = "everything")
// @Config.LangKey("hwell.config.title")
public class HwellConfig {

	@Config.Comment({ "Are the recipes config able? (default: false)" })
	public static boolean customRecipesEnabled = false;

	@Config.Comment({ "Where the base recipes file is located (default: config/hwell_recipes.json)" })
	public static String recipeFileLocation = "config/hwell_recipes.json";

	// @Config.Comment({ "If this is true, when failing to read the recipes file,
	// the file will be (default: config/hwell_recipes.json)" })
	// public static boolean alwaysWriteRecipes = true;

	@Config.Comment({ "Build multiblocks automatically when they are needed. "
			+ "This should always be false except for debugging (default: false)" })
	public static boolean isAutomaticMultiblocks = false;

	@Config.Comment({ "Allow Entities to travel to the nether (Players, mobs and animals, items, etc) (default: false)" })
	public static boolean allowEntitiesToTravelToTheNether = false;

	//

	@Config.Comment({ "Max range that Power Providers (Power Crystals) will provide power to machines. (default: 10)" })
	public static int powerMaxRange = 10;

	@Config.Comment({ "Do Power Crystals drop when they become empty? (default: true)" })
	public static boolean powerCrystalDropsWhenEmpty = true;

	// ----------------------------------------

	// NOURISHER
	@Config.Comment({ "Charger Cooldown in ticks (default: 100)" })
	public static int nourisherCooldown = 100;

	@Config.Comment({ "Nourisher Power Consumption (default: 5)" })
	public static int nourisherConsumption = 5;

	@Config.Comment({ "Nourisher Range (default: 4)" })
	public static int nourisherRange = 4;

	@Config.Comment({ "Does the Nourisher only work on normal crops or on all Growables (sapplings and other)? (default: true)" })
	public static boolean nourisherOnlyGrowCrops = true;

	// CHARGER
	@Config.Comment({ "Charger Cooldown in ticks (default: 20)" })
	public static int chargerCooldown = 20;

	// SETTER
	@Config.Comment({ "Setter Base Range (default: 4)" })
	public static int setterBaseRange = 4;

	@Config.Comment({ "Setter Power Consumption (default: 15)" })
	public static int setterConsumption = 15;

	// PULLER
	@Config.Comment({ "Puller Power Consumption per Operation (default: 100)" })
	public static int pullerConsumption = 50;
	@Config.Comment({ "Puller Delay Between pulls (default: 3000)" })
	public static int pullerDelay = 3000;
	@Config.Comment({ "Puller chance to get a filtered item (default: 0.5)" })
	public static double pullerChanceToGetFilteredPull = 0.5;

	// GRINDER
	@Config.Comment({ "Grinder Power Consumption per Operation (default: 100)" })
	public static int grinderConsumption = 12;

	// SEPARATOR
	@Config.Comment({ "Separator Power Consumption per Operation (default: 100)" })
	public static int separatorConsumption = 25;

	// FREEZER
	@Config.Comment({ "Freezer Power Consumption per Operation  (default: 30)" })
	public static int freezerConsumption = 5;

	@Config.Comment({ "Range of freezer? (default: 5)" })
	@Config.RangeInt(min = 1, max = 16)
	public static int freezerRange = 5;

	@Config.Comment({ "Does freezer only work in night time? (default: true)" })
	public static boolean freezerIsRequiredToBeNight = true;

	// LOOT KIT PRODUCER
	@Config.Comment({ "Number of ticks that a loot kit need to hatch. 20 ticks = 1 second. (default: 600)" })
	@Config.RangeInt(min = 40, max = 16000)
	public static int producerTimeToHatch = 600;

	@Config.Comment({ "Charger Cooldown in ticks (default: 100)" })
	public static int producerConsumption = 25;

	// // HEAT FURNACE
	// @Config.Comment({ "Heat Furnace Power Consumption per Operation (default:
	// 100)" })
	// public static int energyConsumptionFurnace = 100;

	// GRAVITY
	@Config.Comment({ "Range of Gravity Block? (default: 5)" })
	@Config.RangeInt(min = 1, max = 16)
	public static int gravityBlockRange = 5;

	@Config.Comment({ "Range of Mini Gravity Block? (default: 1)" })
	@Config.RangeInt(min = 1, max = 16)
	public static int gravityBlockRangeMini = 1;

	// BOXER
	@Config.Comment({ "Number of boxes a boxer spawns (default: 32)" })
	@Config.RangeInt(min = 1, max = 64)
	public static int boxerNrOfBoxesSpawned = 32;

	// ----------------------------------------
	
	// MYST FERTILIZER
	@Config.Comment({ "Is myst fertilizer required to see sky? (default: true)" })
	public static boolean mystSaplingRequireSky = true;
	
	// TUBES
	@Config.Comment({ "Is tube required to see sky? (default: true)" })
	public static boolean tubeIsRequiredToSeeSky = true;

	@Config.Comment({ "Does tube only work in day time? (default: true)" })
	public static boolean tubeIsRequiredToBeDay = true;

	// LIGHT COLLECTOR
	@Config.Comment({ "Is light collector required to see sky? (default: true)" })
	public static boolean lightCollectorIsRequiredToSeeSky = true;

	@Config.Comment({ "Does Light Collector only work in day time? (default: true)" })
	public static boolean lightCollectorIsRequiredToBeDay = true;

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
