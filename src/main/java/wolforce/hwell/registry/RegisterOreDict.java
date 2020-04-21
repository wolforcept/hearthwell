package wolforce.hwell.registry;

import static wolforce.hwell.Main.asul_block;
import static wolforce.hwell.Main.asul_ingot;
import static wolforce.hwell.Main.asul_nugget;
import static wolforce.hwell.Main.azurite;
import static wolforce.hwell.Main.citrinic_sand;
import static wolforce.hwell.Main.citrinic_stone;
import static wolforce.hwell.Main.crystal;
import static wolforce.hwell.Main.crystal_block;
import static wolforce.hwell.Main.crystal_nether;
import static wolforce.hwell.Main.crystal_nether_block;
import static wolforce.hwell.Main.glowstone_ore;
import static wolforce.hwell.Main.heavy_block;
import static wolforce.hwell.Main.heavy_ingot;
import static wolforce.hwell.Main.heavy_nugget;
import static wolforce.hwell.Main.metaldiamond;
import static wolforce.hwell.Main.metaldiamond_block;
import static wolforce.hwell.Main.moonstone;
import static wolforce.hwell.Main.myst_log;
import static wolforce.hwell.Main.myst_planks;
import static wolforce.hwell.Main.mystic_iron_block;
import static wolforce.hwell.Main.mystic_iron_ingot;
import static wolforce.hwell.Main.onyx;
import static wolforce.hwell.Main.quartz_ore;
import static wolforce.hwell.Main.salt;
import static wolforce.hwell.Main.smooth_azurite;
import static wolforce.hwell.Main.smooth_onyx;
import static wolforce.hwell.Main.soulsteel_block;
import static wolforce.hwell.Main.soulsteel_ingot;

import net.minecraftforge.oredict.OreDictionary;

public class RegisterOreDict {

	public static void registerBlocks() {
		OreDictionary.registerOre("logWood", myst_log);
		OreDictionary.registerOre("plankWood", myst_planks);
		OreDictionary.registerOre("oreGlowstone", glowstone_ore);
		OreDictionary.registerOre("oreQuartz", quartz_ore);

		OreDictionary.registerOre("blockHeavy", heavy_block);

		OreDictionary.registerOre("blockAsul", asul_block);

		OreDictionary.registerOre("blockAsul", asul_block);

		OreDictionary.registerOre("blockMysticIron", mystic_iron_block);

		OreDictionary.registerOre("blockSoulsteel", soulsteel_block);

		OreDictionary.registerOre("blockMetaldiamond", metaldiamond_block);

		//

		OreDictionary.registerOre("blockHearthWellCrystal", crystal_block);
		OreDictionary.registerOre("blockHearthWellNetherCrystal", crystal_nether_block);

		//

		OreDictionary.registerOre("stoneAzurite", azurite);
		OreDictionary.registerOre("stoneAzuriteSmooth", smooth_azurite);

		OreDictionary.registerOre("stoneOnyx", onyx);
		OreDictionary.registerOre("stoneOnyxSmooth", smooth_onyx);

		OreDictionary.registerOre("stoneMoonstone", moonstone);

		OreDictionary.registerOre("stoneBrackish", citrinic_stone);
		OreDictionary.registerOre("stoneWhiteBrackish", citrinic_sand);
	}

	public static void registerItems() {
		OreDictionary.registerOre("dustSalt", salt);

		OreDictionary.registerOre("ingotHeavy", heavy_ingot);
		OreDictionary.registerOre("nuggetHeavy", heavy_nugget);

		OreDictionary.registerOre("ingotAsul", asul_ingot);
		OreDictionary.registerOre("nuggetAsul", asul_nugget);

		OreDictionary.registerOre("ingotMysticIron", mystic_iron_ingot);
		OreDictionary.registerOre("ingotSoulsteel", soulsteel_ingot);
		OreDictionary.registerOre("ingotMetaldiamond", metaldiamond);

		//

		OreDictionary.registerOre("gemHearthWellCrystal", crystal);
		OreDictionary.registerOre("gemHearthWellNetherCrystal", crystal_nether);
	}
}
