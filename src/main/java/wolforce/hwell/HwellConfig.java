package wolforce.hwell;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//@Config(modid = Hwell.MODID)
@Mod.EventBusSubscriber(modid = Hwell.MODID)
@Config(modid = Hwell.MODID, category = "hwell_config")
public class HwellConfig {

	public static Meta meta = new Meta();
	public static General general = new General();
	public static Power power = new Power();
	public static Machines machines = new Machines();
	public static Other other = new Other();

	public static class Meta {

		// @Config.RequiresMcRestart
		// @Comment({ "Are the recipes config able? (default: false)" })
		// public boolean customRecipesEnabled = false;
		// @Config.RequiresMcRestart
		// @Comment({ "Where the base recipes file is located (default:
		// config/hwell_recipes.json)" })
		// public String recipeFileLocation = "config/hwell_recipes.json";

		@Config.RequiresMcRestart
		@Comment({ "Particles Id for Hearth Well start at this number (default: 291)" })
		public int particleIdStart = 291;

		// @Comment({ "If this is true, when failing to read the recipes file,
		// the file will be (default: config/hwell_recipes.json)" })
		// public boolean alwaysWriteRecipes = true;
	}

	public static class General {
		@Comment({ "Build multiblocks automatically when they are needed.",
				"This should always be false except for debugging (default: false)" })
		public boolean isAutomaticMultiblocks = false;

		@Comment({ "Allow Entities to travel to the nether (Players, mobs and animals, items, etc) (default: false)" })
		public boolean allowEntitiesToTravelToTheNether = false;

		@Comment({ "Allow player to perform the Book Ritual (default: true)" })
		public boolean book_ritual_enabled = true;
	}

	public static class Power {
		@Comment({ "Max range that Power Providers (Power Crystals) will provide power to machines. AFFECTS PERFORMANCE!! (default: 10)" })
		public int powerMaxRange = 10;

		@Comment({ "Do Power Crystals drop when they become empty? (default: false)" })
		public boolean powerCrystalDropsWhenEmpty = false;

		@Comment({ "Chance to turn a block from dripping per tick (default: 0.1%)" })
		public float drippingChance = .001f;
	}

	public static class Machines {
		// FORMER
		@Comment({ "Former Power Consumption (default: 1)" })
		public int formerConsumption = 1;

		// NOURISHER
		@Comment({ "Charger Cooldown in ticks (default: 100)" })
		public int nourisherCooldown = 100;

		@Comment({ "Nourisher Power Consumption (default: 5)" })
		public int nourisherConsumption = 5;

		@Comment({ "Nourisher Range (default: 4)" })
		public int nourisherRange = 4;

		@Comment({
				"Does the Nourisher only work on normal crops or on all Growables (sapplings and other)? (default: true)" })
		public boolean nourisherOnlyGrowCrops = true;

		// CHARGER
		@Comment({ "Charger Cooldown in ticks (default: 20)" })
		public int chargerCooldown = 20;

		// SETTER
		@Comment({ "Setter Base Range (default: 4)" })
		public int setterBaseRange = 4;

		@Comment({ "Setter Power Consumption (default: 15)" })
		public int setterConsumption = 15;

		// MUTATOR
		@Comment({ "Mutator Power Consumption (default: 4)" })
		public int mutatorPowerConsumption = 4;

		@Comment({ "Mutator Paste Value. (default: 1000)" })
		public int mutatorPasteValue = 1000;

		@Comment({ "Mutator Paste Consumption per item swap. MUST BE A DIVISOR OF THE PASTE VALUE (default: 200)" })
		public int mutatorPasteConsumption = 200;

		// PULLER
		@Comment({ "Puller Power Consumption per Operation (default: 100)" })
		public int pullerConsumption = 50;
		@Comment({ "Puller Delay Between pulls (default: 3000)" })
		public int pullerDelay = 3000;
		@Comment({ "Puller chance to get a filtered item (default: 0.5)" })
		public double pullerChanceToGetFilteredPull = 0.5;

		// GRINDER
		@Comment({ "Grinder Power Consumption per Operation (default: 100)" })
		public int grinderConsumption = 12;

		// SEPARATOR
		@Comment({ "Separator Power Consumption per Operation (default: 100)" })
		public int separatorConsumption = 25;

		// FREEZER
		@Comment({ "Freezer Power Consumption per Operation  (default: 30)" })
		public int freezerConsumption = 5;

		@Comment({ "Range of freezer? (default: 5)" })
		@Config.RangeInt(min = 1, max = 16)
		public int freezerRange = 2;

		@Comment({ "Does freezer only work in night time? (default: true)" })
		public boolean freezerIsRequiredToBeNight = true;

		// LOOT KIT PRODUCER
		@Comment({ "Number of ticks that a loot kit need to hatch. 20 ticks = 1 second. (default: 600)" })
		@Config.RangeInt(min = 40, max = 16000)
		public int producerTimeToHatch = 600;

		@Comment({ "Charger Cooldown in ticks (default: 100)" })
		public int producerConsumption = 25;

		// // HEAT FURNACE
		// @Comment({ "Heat Furnace Power Consumption per Operation (default:
		// 100)" })
		// public int energyConsumptionFurnace = 100;

		// GRAVITY
		@Comment({ "Range of Gravity Block? (default: 5)" })
		@Config.RangeInt(min = 1, max = 16)
		public int gravityBlockRange = 5;

		@Comment({ "Range of Mini Gravity Block? (default: 1)" })
		@Config.RangeInt(min = 1, max = 16)
		public int gravityBlockRangeMini = 1;

		// BOXER
		@Comment({ "Number of boxes a boxer spawns (default: 32)" })
		@Config.RangeInt(min = 1, max = 64)
		public int boxerNrOfBoxesSpawned = 32;
	}

	public static class Other {

		// MYST FERTILIZER
		@Comment({ "Is myst fertilizer required to see sky? (default: true)" })
		public boolean mystSaplingRequireSky = true;

		// TUBES
		@Comment({ "Is tube required to see sky? (default: true)" })
		public boolean tubeIsRequiredToSeeSky = true;

		@Comment({ "Does tube only work in day time? (default: true)" })
		public boolean tubeIsRequiredToBeDay = true;

		// LIGHT COLLECTOR
		@Comment({ "Is light collector required to see sky? (default: true)" })
		public boolean lightCollectorIsRequiredToSeeSky = true;

		@Comment({ "Does Light Collector only work in day time? (default: true)" })
		public boolean lightCollectorIsRequiredToBeDay = true;

		// PICKING TABLE
		@Comment({ "Chance that you will get a shard in a picking table. (default: .333)" })
		public double pickingTableChance = .333;

		// PICKING TABLE
		@Comment({
				"If heat blocks explode with flint and steel. Otherwise they will just transform peacefully. (default: true)" })
		public boolean heat_blocks_explode = true;

		// PICKING TABLE
		@Comment({
				"If heat blocks explode with flint and steel. Otherwise they will just transform peacefully. (default: true)" })
		@Config.RangeInt(min = 1, max = 64)
		public int inert_seed_distance = 4;

		// PICKING TABLE
		@Comment({ "Number of Graft Blocks a Grafting Tray creates from a single core. (default: 8)" })
		@Config.RangeInt(min = 1, max = 64)
		public int numberOfGrafts = 8;
	}

	@SubscribeEvent
	public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(Hwell.MODID)) {
			ConfigManager.sync(Hwell.MODID, Config.Type.INSTANCE);
		}
	}
}
