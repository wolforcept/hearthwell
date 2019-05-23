package wolforce.registry;

import static wolforce.Main.asul_machine_case;
import static wolforce.Main.azurite;
import static wolforce.Main.citrinic_sand;
import static wolforce.Main.citrinic_stone;
import static wolforce.Main.compressed_clay;
import static wolforce.Main.crystal_block;
import static wolforce.Main.dust_block;
import static wolforce.Main.gaseous_glass;
import static wolforce.Main.gaseous_sand;
import static wolforce.Main.glowstone_ore;
import static wolforce.Main.hamburger;
import static wolforce.Main.hamburger_cooked;
import static wolforce.Main.heavy_ingot;
import static wolforce.Main.heavy_mesh;
import static wolforce.Main.leaf_mesh;
import static wolforce.Main.liquid_souls;
import static wolforce.Main.metaldiamond_block;
import static wolforce.Main.mystic_iron_ingot;
import static wolforce.Main.onyx;
import static wolforce.Main.producer;
import static wolforce.Main.quartz_ore;
import static wolforce.Main.raw_mystic_iron;
import static wolforce.Main.raw_repairing_paste;
import static wolforce.Main.raw_soulsteel;
import static wolforce.Main.repairing_paste;
import static wolforce.Main.scorch_glass;
import static wolforce.Main.scorch_grit;
import static wolforce.Main.smooth_azurite;
import static wolforce.Main.smooth_onyx;
import static wolforce.Main.soulsteel_ingot;
import static wolforce.Main.wheat_flour;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wolforce.Hwell;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.Util;
import wolforce.base.MyBlock;
import wolforce.blocks.BlockCore;
import wolforce.items.ItemLoot;
import wolforce.items.ItemMystDust;
import wolforce.recipes.RecipeCharger;
import wolforce.recipes.RecipeCoring;
import wolforce.recipes.RecipeCrushing;
import wolforce.recipes.RecipeFreezer;
import wolforce.recipes.RecipeGrinding;
import wolforce.recipes.RecipeNetherPortal;
import wolforce.recipes.RecipePowerCrystal;
import wolforce.recipes.RecipePuller;
import wolforce.recipes.RecipeRepairingPaste;
import wolforce.recipes.RecipeSeedOfLife;
import wolforce.recipes.RecipeSeparator;
import wolforce.recipes.RecipeTube;

@Mod.EventBusSubscriber(modid = Hwell.MODID)
public class RegisterRecipes {

	private static final String INNER_RECIPES_FILE = "recipes.json";
	public static boolean old_version_recipes_file = false;
	public static boolean errored_recipes_file = false;
	public static IRecipe recipePowerCrystal;
	public static String recipenr = "unknown";

	// MUST REGISTER CORE BLOCKS BEFORE EVERYTHING ELSE (MARTELADA)

	// PRE INIT
	public static void createNewCores() {
		Main.custom_cores = new HashMap<String, BlockCore>();
		Main.custom_grafts = new HashMap<>();
		Main.graft_costs = new HashMap<>();
		Main.graft_costs.put(Main.core_stone, 100);
		Main.graft_costs.put(Main.core_anima, 750);
		Main.graft_costs.put(Main.core_heat, 300);
		Main.graft_costs.put(Main.core_green, 500);
		Main.graft_costs.put(Main.core_sentient, 1000);

		if (!HwellConfig.meta.customRecipesEnabled)
			return;

		try {
			File recipesFile = new File(HwellConfig.meta.recipeFileLocation);
			if (!recipesFile.exists())
				return;
			JsonObject recipeJson = Util.readJson(HwellConfig.meta.recipeFileLocation).getAsJsonObject();

			if (!recipeJson.has("coring_recipes"))
				return;

			for (Entry<String, JsonElement> entry : recipeJson.getAsJsonObject("coring_recipes").entrySet()) {
				String nameid = entry.getKey();
				if (RecipeCoring.getNormalCoreBlock(nameid) != null)
					continue;
				JsonObject recipes = entry.getValue().getAsJsonObject();
				final String localizedName = recipes.get("name").getAsString();
				final String colorString1 = recipes.get("base_color").getAsString();
				final String colorString2 = recipes.get("border_color").getAsString();
				final int graftCost = recipes.get("graft_cost").getAsInt();
				BlockCore newCore = new BlockCore(nameid, false, colorString1, colorString2) {
					@Override
					public String getLocalizedName() {
						return localizedName;
					}
				};
				System.out.println("created new core with registry name <" + nameid + "> and localized name <" + localizedName + ">.");
				Main.custom_cores.put(nameid, newCore);

				String temp = "" + localizedName;
				if (localizedName.endsWith(" Core"))
					temp = localizedName.substring(0, localizedName.indexOf(" Core"));
				if (localizedName.endsWith(" core"))
					temp = localizedName.substring(0, localizedName.indexOf(" core"));
				temp += " Graft";
				final String graftLoc = "" + temp;
				Block newGraft = new MyBlock("graft_" + nameid, Material.ROCK) {
					@Override
					public String getLocalizedName() {
						return graftLoc;
					}
				}.setHarvest("pickaxe", -1).setResistance(2).setHardness(2);
				Main.custom_grafts.put(newCore, newGraft);
				Main.graft_costs.put(newCore, graftCost);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//

	//

	//

	//

	//

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {

		// fix for burst seeds of hearthwell materials
		Main.burst_seed_crystal.stack = new ItemStack(Main.crystal_block);

		File recipesFile = new File(HwellConfig.meta.recipeFileLocation);

		// if file does not exist, write default file
		if (!recipesFile.exists() && HwellConfig.meta.customRecipesEnabled)
			try {
				writeRecipesFile(HwellConfig.meta.recipeFileLocation);
			} catch (IOException e) {
				throw new RuntimeException("Could not initialise the Recipes File!! Game will crash.");
			}
		if (!HwellConfig.meta.customRecipesEnabled && recipesFile.exists())
			recipesFile.delete();
		JsonObject recipes = null, defaultRecipes = null;
		try {
			if (HwellConfig.meta.customRecipesEnabled)
				recipes = Util.readJson(HwellConfig.meta.recipeFileLocation).getAsJsonObject();
		} catch (Exception e) {
			System.err.println("Error while reading the Recipes File! Defaulting all recipes.");
			errored_recipes_file = true;
		}
		try {
			defaultRecipes = Util.readJson("/assets/hwell/" + INNER_RECIPES_FILE, true).getAsJsonObject();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (errored_recipes_file || recipes == null || !HwellConfig.meta.customRecipesEnabled)
			recipes = defaultRecipes;

		if (!recipes.has("version") || !recipes.get("version").getAsString().equals(defaultRecipes.get("version").getAsString())) {
			old_version_recipes_file = true;
		}

		recipenr = defaultRecipes.get("version").getAsString();

		String recipeName = "power_crystal_recipes";
		RecipePowerCrystal.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonObject());

		recipeName = "charger_recipes";
		RecipeCharger.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "nether_portal_recipes";
		RecipeNetherPortal.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "separator_recipes";
		RecipeSeparator.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "freezer_recipes";
		RecipeFreezer.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "puller_recipes";
		RecipePuller.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "crushing_recipes";
		RecipeCrushing.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "tube_recipes";
		RecipeTube.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "grinding_recipes";
		RecipeGrinding.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "repairing_paste_recipes";
		RecipeRepairingPaste.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "coring_recipes";
		RecipeCoring.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonObject());

		recipeName = "seed_of_life_recipes";
		RecipeSeedOfLife.initRecipes((recipes.has(recipeName) ? recipes : defaultRecipes).get(recipeName).getAsJsonArray());

		recipeName = "myst_dust_recipes";
		// ItemMystDust.initRecipes((recipes.has(recipeName) ? recipes :
		// defaultRecipes).get(recipeName).getAsJsonArray());
		ItemMystDust.initRecipes();

		Main.initShards();
		ItemLoot.setLootTables();

		GameRegistry.addSmelting(glowstone_ore, new ItemStack(Items.GLOWSTONE_DUST), 0.5f);
		GameRegistry.addSmelting(quartz_ore, new ItemStack(Items.QUARTZ), 0.5f);
		GameRegistry.addSmelting(heavy_mesh, new ItemStack(heavy_ingot, 2), 0.5f);
		GameRegistry.addSmelting(dust_block, new ItemStack(Blocks.GLASS, 2), 0f);
		GameRegistry.addSmelting(leaf_mesh, new ItemStack(crystal_block, 1), .5f);
		GameRegistry.addSmelting(compressed_clay, new ItemStack(Blocks.HARDENED_CLAY, 9), 0f);
		GameRegistry.addSmelting(scorch_grit, new ItemStack(scorch_glass), 0f);
		GameRegistry.addSmelting(gaseous_sand, new ItemStack(gaseous_glass), 0f);
		GameRegistry.addSmelting(azurite, new ItemStack(smooth_azurite), 0f);
		GameRegistry.addSmelting(citrinic_stone, new ItemStack(citrinic_sand), 0f);
		GameRegistry.addSmelting(onyx, new ItemStack(smooth_onyx), 0f);
		GameRegistry.addSmelting(raw_mystic_iron, new ItemStack(mystic_iron_ingot), 1f);
		GameRegistry.addSmelting(raw_soulsteel, new ItemStack(soulsteel_ingot), 1f);
		GameRegistry.addSmelting(raw_repairing_paste, new ItemStack(repairing_paste), 1f);
		GameRegistry.addSmelting(wheat_flour, new ItemStack(Items.BREAD), 1f);
		GameRegistry.addSmelting(hamburger, new ItemStack(hamburger_cooked), 1f);

		GameRegistry.addShapedRecipe(Util.res("producer." + producer.getRegistryName().getResourcePath()), Util.res("hwell.producer"),
				new ItemStack(Main.producer), //
				"ABA", "MCM", "MYM", //
				'B', FluidUtil.getFilledBucket(new FluidStack(liquid_souls, Fluid.BUCKET_VOLUME)), //
				'A', smooth_azurite, //
				'M', metaldiamond_block, //
				'C', asul_machine_case, //
				'Y', crystal_block);

		recipePowerCrystal = new RecipePowerCrystal().setRegistryName(Util.res("hwell:recipe_power_crystal"));
		event.getRegistry().register(recipePowerCrystal);
		// event.getRegistry().register(new Recipe);
	}

	private static void writeRecipesFile(String destination) throws IOException {
		// ResourceLocation loc = new ResourceLocation("hwell:" + INNER_RECIPES_FILE);
		// String str = "assets/" + loc.toString().replace(":", "/");
		InputStream inStream = Hwell.class.getResourceAsStream("/assets/hwell/" + INNER_RECIPES_FILE);
		BufferedReader in = new BufferedReader(new InputStreamReader(inStream));

		FileWriter outstream = new FileWriter(destination, false);
		BufferedWriter out = new BufferedWriter(outstream);

		String aLine = null;
		while ((aLine = in.readLine()) != null) {
			out.write(aLine);
			out.newLine();
		}
		in.close();
		out.close();
	}

	public static interface HwellRecipe {

	}
}
