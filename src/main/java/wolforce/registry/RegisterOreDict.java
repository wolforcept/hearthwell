package wolforce.registry;

import static wolforce.Main.asul_block;
import static wolforce.Main.asul_ingot;
import static wolforce.Main.asul_nugget;
import static wolforce.Main.azurite;
import static wolforce.Main.citrinic_sand;
import static wolforce.Main.citrinic_stone;
import static wolforce.Main.crystal;
import static wolforce.Main.crystal_block;
import static wolforce.Main.crystal_nether;
import static wolforce.Main.crystal_nether_block;
import static wolforce.Main.glowstone_ore;
import static wolforce.Main.heavy_block;
import static wolforce.Main.heavy_ingot;
import static wolforce.Main.heavy_nugget;
import static wolforce.Main.metaldiamond;
import static wolforce.Main.metaldiamond_block;
import static wolforce.Main.moonstone;
import static wolforce.Main.myst_log;
import static wolforce.Main.myst_planks;
import static wolforce.Main.mystic_iron_block;
import static wolforce.Main.mystic_iron_ingot;
import static wolforce.Main.onyx;
import static wolforce.Main.quartz_ore;
import static wolforce.Main.salt;
import static wolforce.Main.smooth_azurite;
import static wolforce.Main.smooth_onyx;
import static wolforce.Main.soulsteel_block;
import static wolforce.Main.soulsteel_ingot;

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
