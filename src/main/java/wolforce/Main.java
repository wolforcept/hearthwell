package wolforce;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import wolforce.blocks.BlockAntiGravity;
import wolforce.blocks.BlockBox;
import wolforce.blocks.BlockBoxer;
import wolforce.blocks.BlockBurstSeed;
import wolforce.blocks.BlockCore;
import wolforce.blocks.BlockCrushing;
import wolforce.blocks.BlockDust;
import wolforce.blocks.BlockFertileSoil;
import wolforce.blocks.BlockFreezer;
import wolforce.blocks.BlockGeneratorHeat;
import wolforce.blocks.BlockGravity;
import wolforce.blocks.BlockGravityMini;
import wolforce.blocks.BlockGroundShovel;
import wolforce.blocks.BlockHeat;
import wolforce.blocks.BlockHeatFurnace;
import wolforce.blocks.BlockLightCollector;
import wolforce.blocks.BlockMystBush;
import wolforce.blocks.BlockMystGrass;
import wolforce.blocks.BlockMystLeaves;
import wolforce.blocks.BlockNourisher;
import wolforce.blocks.BlockPickerHolder;
import wolforce.blocks.BlockPickingTable;
import wolforce.blocks.BlockPrecisionGrinder;
import wolforce.blocks.BlockPrecisionGrinderEmpty;
import wolforce.blocks.BlockProducer;
import wolforce.blocks.BlockPuller;
import wolforce.blocks.BlockSeparator;
import wolforce.blocks.BlockSlabLamp;
import wolforce.blocks.BlockStatue;
import wolforce.blocks.BlockStoneDust;
import wolforce.blocks.BlockTube;
import wolforce.blocks.simplevariants.MyGlass;
import wolforce.blocks.simplevariants.MyLog;
import wolforce.fluids.BlockLiquidSouls;
import wolforce.items.ItemCrystal;
import wolforce.items.ItemCrystalBowl;
import wolforce.items.ItemCrystalBowlWater;
import wolforce.items.ItemEmptyRod;
import wolforce.items.ItemGrindingWheel;
import wolforce.items.ItemHeavyShears;
import wolforce.items.ItemLockedLight;
import wolforce.items.ItemLoot;
import wolforce.items.ItemMystDust;
import wolforce.items.ItemMystFertilizer;
import wolforce.items.ItemRawSoulsteel;
import wolforce.items.ItemSeedOfLife;
import wolforce.items.ItemShard;
import wolforce.items.tools.ItemDisplacer;
import wolforce.items.tools.ItemDustPicker;
import wolforce.items.tools.MyArmor;
import wolforce.items.tools.MyAxe;
import wolforce.items.tools.MyDagger;
import wolforce.items.tools.MyPickaxe;
import wolforce.items.tools.MyShovel;
import wolforce.items.tools.MySword;
import wolforce.recipes.RecipeRepairingPaste;
import wolforce.registry.RegisterRecipes;

public class Main {

	//
	//
	//
	//
	// LISTS

	public static LinkedList<Item> items;
	public static LinkedList<Block> blocks;

	//
	//
	//
	//
	// ITEMS

	public static ToolMaterial material_tool_mystic_iron, material_tool_soulsteel;
	public static ArmorMaterial material_armor_mystic_iron, material_armor_soulsteel;
	public static MaterialLiquid material_liquid_souls;

	public static Item asul_ingot, asul_nugget;
	public static Item raw_mystic_iron, mystic_iron_ingot;
	public static Item mystic_iron_sword, mystic_iron_dagger, mystic_iron_pickaxe, mystic_iron_axe, mystic_iron_shovel,
			mystic_iron_hoe;
	public static Item mystic_iron_helmet, mystic_iron_chest, mystic_iron_legs, mystic_iron_boots;
	public static Item seed_of_life;
	public static Item wheat_flour, salt;
	public static Item dust;
	public static Item myst_dust, myst_fertilizer;
	public static Item heavy_mesh, heavy_ingot, heavy_nugget, fuel_dust, fuel_dust_tiny;
	public static Item heavy_shears, leaf_mesh;
	public static Item crystal, crystal_nether, crystal_bowl, crystal_bowl_water;
	public static Item obsidian_displacer, empowered_displacer;
	public static Item metaldiamond, crystal_catalyst, soul_dust, raw_soulsteel, soulsteel_ingot, mutation_paste, raw_repairing_paste,
			repairing_paste;
	public static Item soulsteel_sword, soulsteel_dagger, soulsteel_pickaxe, soulsteel_axe, soulsteel_shovel, soulsteel_hoe;
	public static Item soulsteel_helmet, soulsteel_chest, soulsteel_legs, soulsteel_boots;
	public static Item locked_light, empty_rod, myst_rod, rod_myst_1, rod_myst_2, rod_blaze_1, rod_blaze_2, rod_blaze_3;
	public static Item myst_dust_picker_fe, myst_dust_picker_ca, myst_dust_picker_c, myst_dust_picker_o, myst_dust_picker_au,
			myst_dust_picker_h, myst_dust_picker_p, myst_dust_picker_n;
	public static ItemShard shard_fe, shard_au, shard_o, shard_c, shard_h, shard_ca, shard_p, shard_n;
	public static ItemGrindingWheel grinding_wheel_iron, grinding_wheel_diamond, grinding_wheel_crystal;
	public static ItemLoot loot_base, loot_blaze, loot_creeper, loot_enderman, loot_ghast, loot_shulker, loot_skeleton, loot_slime,
			loot_spider, loot_witch, loot_wither, loot_zombie;

	//
	//
	//
	//
	// BLOCKS

	public static Block glowstone_ore, quartz_ore;
	public static Block fertile_soil;
	public static Block heavy_block;
	public static Block crystal_block, crystal_nether_block;
	public static Block myst_grass;
	public static Block myst_bush, myst_bush_big;
	public static Block myst_dust_block;
	public static Block dust_block;
	public static Block dismantler, crushing_block, gravity_block, gravity_block_mini, gravity_powered_block, antigravity_block,
			antigravity_powered_block;
	public static Block myst_log, myst_planks, myst_leaves;
	public static Block raw_asul_block, asul_block;
	public static Block asul_machine_case;
	public static Block light_collector;
	public static Block stabiliser;
	public static Block picking_table, picker_holder;
	public static Block white_block, moonstone, moonstone_bricks, citrinic_stone, citrinic_sand, onyx, smooth_onyx, azurite,
			smooth_azurite, scorch_grit, scorch_glass, fullgrass_block, metaldiamond_block;
	public static Block freezer, generator_heat;
	public static Block fertilizer_block;
	public static Block compressed_clay, compressed_wool, weeping_block, skin_block;
	public static Block mystic_iron_block, soulsteel_block;
	public static Block mutation_paste_block;
	public static BlockNourisher nourisher;
	public static BlockCore core_stone, core_heat, core_green, core_sentient;
	public static Block slab_lamp;
	public static Block furnace_tube, heat_furnace;
	public static Block protection_block, heavy_protection_block;
	public static Block precision_grinder_flint, precision_grinder_iron, precision_grinder_diamond, precision_grinder_crystal,
			precision_grinder_empty;
	public static Block heat_block;
	public static Block burst_seed_stone, burst_seed_sand, burst_seed_dirt, burst_seed_snow, burst_seed_netherrack, burst_seed_quartz,
			burst_seed_prismarine, burst_seed_crystal;
	public static Block totem_enderman, totem_zombie, totem_skeleton, totem_creeper;
	public static Block separator;
	public static BlockProducer producer;
	public static BlockPuller puller;

	//

	public static Block boxer;
	public static BlockBox[] boxes;

	//
	//
	//
	//
	// FLUIDS

	public static Fluid liquid_souls;
	public static Block liquid_souls_block;

	//
	//
	//
	//
	// TABS

	public static CreativeTabs creativeTab;

	//
	//
	//
	//
	// SHARDS

	public static Item[] shards;
	public static HashMap<String, BlockCore> custom_cores;

	public static void initShards() {
		shards = new Item[] { shard_ca, shard_c, shard_au, shard_fe, shard_o, shard_h };
	}

	public static Item getRandomCrystal(Random rand) {
		return shards[rand.nextInt(shards.length)];
	}

	//
	//
	//
	//
	//

	public static void preInit(FMLPreInitializationEvent event) {

		material_armor_mystic_iron = EnumHelper.addArmorMaterial("mysticIron", "mystic_iron", 10, new int[] { 2, 5, 6, 2 }, 25,
				SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2);
		material_armor_soulsteel = EnumHelper.addArmorMaterial("soulsteel", "soulsteel", 10, new int[] { 3, 6, 8, 3 }, 25,
				SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2);
		material_tool_mystic_iron = EnumHelper.addToolMaterial("mysticIron", 2, 425, 6, 2, 15);
		material_tool_soulsteel = EnumHelper.addToolMaterial("soulsteel", 3, 1920, 8, 3, 20);

		items = new LinkedList<>();
		blocks = new LinkedList<>();

		quartz_ore = new MyBlock("quartz_ore", Material.ROCK);
		blocks.add(quartz_ore);
		glowstone_ore = new MyBlock("glowstone_ore", Material.ROCK);
		blocks.add(glowstone_ore);
		// LootTableList.ENTITIES_BLAZE, //
		// LootTableList.ENTITIES_CREEPER, //
		// LootTableList.ENTITIES_ENDERMAN, //
		// LootTableList.ENTITIES_GHAST, //
		// LootTableList.ENTITIES_SHULKER, //
		// LootTableList.ENTITIES_SKELETON, //
		// LootTableList.ENTITIES_SLIME, //
		// LootTableList.ENTITIES_SPIDER, //
		// LootTableList.ENTITIES_WITCH, //
		// LootTableList.ENTITIES_WITHER_SKELETON, //
		// LootTableList.ENTITIES_ZOMBIE,//

		loot_base = new ItemLoot("loot_base", -1, "Produced in a Loot Kit Producer.");
		items.add(loot_base);
		loot_blaze = new ItemLoot("loot_blaze", 0);
		items.add(loot_blaze);
		loot_creeper = new ItemLoot("loot_creeper", 1);
		items.add(loot_creeper);
		loot_enderman = new ItemLoot("loot_enderman", 2);
		items.add(loot_enderman);
		loot_ghast = new ItemLoot("loot_ghast", 3);
		items.add(loot_ghast);
		loot_shulker = new ItemLoot("loot_shulker", 4);
		items.add(loot_shulker);
		loot_skeleton = new ItemLoot("loot_skeleton", 5);
		items.add(loot_skeleton);
		loot_slime = new ItemLoot("loot_slime", 6);
		items.add(loot_slime);
		loot_spider = new ItemLoot("loot_spider", 7);
		items.add(loot_spider);
		loot_witch = new ItemLoot("loot_witch", 8);
		items.add(loot_witch);
		loot_wither = new ItemLoot("loot_wither", 9);
		items.add(loot_wither);
		loot_zombie = new ItemLoot("loot_zombie", 10);
		items.add(loot_zombie);

		producer = new BlockProducer("producer");
		blocks.add(producer);
		puller = new BlockPuller("puller");
		blocks.add(puller);

		seed_of_life = new ItemSeedOfLife("seed_of_life", "Right-click on the ground to breed life!");
		items.add(seed_of_life);
		fertile_soil = new BlockFertileSoil("fertile_soil");
		blocks.add(fertile_soil);

		heavy_mesh = new MyItem("heavy_mesh");
		items.add(heavy_mesh);
		heavy_ingot = new MyItem("heavy_ingot");
		items.add(heavy_ingot);
		heavy_block = new MyBlock("heavy_block", Material.IRON).setResistance(2.5f).setHardness(1.6f);
		heavy_block.setHarvestLevel("pickaxe", -1);
		blocks.add(heavy_block);
		heavy_nugget = new MyItem("heavy_nugget", "Crush to make fuel dust.");
		items.add(heavy_nugget);
		fuel_dust = new MyItem("fuel_dust", "Smelts 4 items.") {
			@Override
			public int getItemBurnTime(ItemStack itemStack) {
				return 800;
			}
		};
		items.add(fuel_dust);
		fuel_dust_tiny = new MyItem("fuel_dust_tiny", "Smelts one item.") {
			@Override
			public int getItemBurnTime(ItemStack itemStack) {
				return 200;
			}
		};
		items.add(fuel_dust_tiny);

		asul_machine_case = new MyBlock("asul_machine_case", Material.ROCK).setHarvest("pickaxe", -1).setResistance(1f)
				.setHardness(1f);
		blocks.add(asul_machine_case);
		asul_ingot = new MyItem("asul_ingot");
		items.add(asul_ingot);
		asul_nugget = new MyItem("asul_nugget");
		items.add(asul_nugget);
		asul_block = new MyBlock("asul_block", Material.CLAY).setHardness(.55f).setHarvest("pickaxe", -1);
		blocks.add(asul_block);
		raw_asul_block = new MyBlock("raw_asul_block", Material.CLAY).setHardness(.55f).setHarvest("pickaxe", -1);
		blocks.add(raw_asul_block);

		crushing_block = new BlockCrushing("crushing_block");
		blocks.add(crushing_block);
		heavy_shears = new ItemHeavyShears("heavy_shears");
		items.add(heavy_shears);
		gravity_block = new BlockGravity("gravity_block", true);
		blocks.add(gravity_block);
		gravity_block_mini = new BlockGravityMini("gravity_block_mini", false);
		blocks.add(gravity_block_mini);
		gravity_powered_block = new BlockGravity("gravity_powered_block", false);
		blocks.add(gravity_powered_block);
		antigravity_block = new BlockAntiGravity("antigravity_block", true);
		blocks.add(antigravity_block);
		antigravity_powered_block = new BlockAntiGravity("antigravity_powered_block", false);
		blocks.add(antigravity_powered_block);

		core_stone = new BlockCore("core_stone", true);
		blocks.add(core_stone);
		core_heat = new BlockCore("core_heat", false);
		blocks.add(core_heat);
		core_green = new BlockCore("core_green", false);
		blocks.add(core_green);
		core_sentient = new BlockCore("core_sentient", false);
		blocks.add(core_sentient);

		dust = new MyItem("dust");
		items.add(dust);
		myst_dust = new ItemMystDust("myst_dust", "Where does it come from?");
		items.add(myst_dust);
		myst_dust_block = new BlockDust("myst_dust_block");
		blocks.add(myst_dust_block);
		dust_block = new BlockStoneDust("dust_block");
		blocks.add(dust_block);

		slab_lamp = new BlockSlabLamp("slab_lamp");
		blocks.add(slab_lamp);
		furnace_tube = new BlockTube("furnace_tube");
		blocks.add(furnace_tube);

		myst_grass = new BlockMystGrass("myst_grass");
		blocks.add(myst_grass);
		myst_bush = new BlockMystBush("myst_bush");
		blocks.add(myst_bush);
		myst_bush_big = new BlockMystBush("myst_bush_big");
		blocks.add(myst_bush_big);

		myst_fertilizer = new ItemMystFertilizer("myst_fertilizer", "When used on any sappling, a Mysterious Tree grows.");
		items.add(myst_fertilizer);

		myst_log = new MyLog("myst_log");
		blocks.add(myst_log);
		myst_planks = new MyBlock("myst_planks", Material.WOOD).setHardness(1.5f).setHarvest("axe", -1);
		blocks.add(myst_planks);
		myst_leaves = new BlockMystLeaves("myst_leaves");
		blocks.add(myst_leaves);

		leaf_mesh = new MyItem("leaf_mesh");
		items.add(leaf_mesh);
		crystal_block = new MyGlass("crystal_block");
		blocks.add(crystal_block);

		crystal = new ItemCrystal("crystal");
		items.add(crystal);

		crystal_bowl = new ItemCrystalBowl("crystal_bowl");
		items.add(crystal_bowl);
		crystal_bowl_water = new ItemCrystalBowlWater("crystal_bowl_water", "Drink it!");
		items.add(crystal_bowl_water);

		wheat_flour = new MyItem("wheat_flour");
		items.add(wheat_flour);
		salt = new MyItem("salt");
		items.add(salt);
		shard_c = new ItemShard("shard_c");
		items.add(shard_c);
		shard_fe = new ItemShard("shard_fe");
		items.add(shard_fe);
		shard_au = new ItemShard("shard_au");
		items.add(shard_au);
		shard_o = new ItemShard("shard_o");
		items.add(shard_o);
		shard_h = new ItemShard("shard_h");
		items.add(shard_h);
		shard_ca = new ItemShard("shard_ca");
		items.add(shard_ca);
		shard_p = new ItemShard("shard_p");
		items.add(shard_p);
		shard_n = new ItemShard("shard_n");
		items.add(shard_n);

		compressed_clay = new MyBlock("compressed_clay", Material.CLAY).setHardness(.5f).setResistance(2);
		blocks.add(compressed_clay);
		compressed_wool = new MyBlock("compressed_wool", Material.CLAY).setHardness(.5f).setResistance(2);
		blocks.add(compressed_wool);
		skin_block = new MyBlock("skin_block", Material.CARPET).setHardness(.5f).setResistance(2);
		blocks.add(skin_block);
		weeping_block = new MyBlock("weeping_block", Material.CLAY);
		blocks.add(weeping_block);
		fertilizer_block = new MyBlock("fertilizer_block", Material.GOURD);
		blocks.add(fertilizer_block);

		citrinic_stone = new MyBlock("citrinic_stone", Material.ROCK)//
				.setHarvest("pickaxe", 0).setHardness(1f).setResistance(10);
		blocks.add(citrinic_stone);
		citrinic_sand = new MyBlock("citrinic_sand", Material.SAND)//
				.setHarvest("pickaxe", 0).setHardness(1f).setResistance(10);
		blocks.add(citrinic_sand);
		azurite = new MyBlock("azurite", Material.ROCK)//
				.setHarvest("pickaxe", 0).setHardness(1f).setResistance(10);
		blocks.add(azurite);
		smooth_azurite = new MyBlock("smooth_azurite", Material.ROCK)//
				.setHarvest("pickaxe", 0).setHardness(1f).setResistance(10);
		blocks.add(smooth_azurite);

		moonstone = new MyBlock("moonstone", Material.ROCK)//
				.setHarvest("pickaxe", 0).setHardness(1f).setResistance(10);
		blocks.add(moonstone);
		onyx = new MyBlock("onyx", Material.ROCK)//
				.setHarvest("pickaxe", 0).setHardness(2).setResistance(10);
		blocks.add(onyx);
		smooth_onyx = new MyBlock("smooth_onyx", Material.ROCK)//
				.setHarvest("pickaxe", 0).setHardness(1f).setResistance(10);
		blocks.add(smooth_onyx);
		blocks.addAll(Util.makeVariants((MyBlock) citrinic_stone, (MyBlock) citrinic_sand, (MyBlock) azurite, (MyBlock) smooth_azurite,
				(MyBlock) moonstone, (MyBlock) onyx, (MyBlock) smooth_onyx, (MyBlock) myst_planks));

		white_block = new MyBlock("white_block", Material.ROCK)//
				.setHarvest("pickaxe", 0).setHardness(1f).setResistance(10);
		blocks.add(white_block);
		scorch_grit = new BlockGroundShovel("scorch_grit");
		blocks.add(scorch_grit);
		fullgrass_block = new BlockGroundShovel("fullgrass_block");
		blocks.add(fullgrass_block);
		scorch_glass = new MyGlass("scorch_glass");
		blocks.add(scorch_glass);

		burst_seed_stone = new BlockBurstSeed("burst_seed_stone", //
				Material.ROCK, Blocks.STONE, "pickaxe", SoundType.STONE);
		blocks.add(burst_seed_stone);
		burst_seed_sand = new BlockBurstSeed("burst_seed_sand", //
				Material.SAND, Blocks.SAND, "shovel", SoundType.SAND);
		blocks.add(burst_seed_sand);
		burst_seed_dirt = new BlockBurstSeed("burst_seed_dirt", //
				Material.GROUND, Blocks.DIRT, "shovel", SoundType.GROUND);
		blocks.add(burst_seed_dirt);
		burst_seed_snow = new BlockBurstSeed("burst_seed_snow", //
				Material.SNOW, Blocks.SNOW, "shovel", SoundType.SNOW);
		blocks.add(burst_seed_snow);
		burst_seed_netherrack = new BlockBurstSeed("burst_seed_netherrack", //
				Material.ROCK, Blocks.NETHERRACK, "shovel", SoundType.STONE);
		blocks.add(burst_seed_netherrack);
		burst_seed_quartz = new BlockBurstSeed("burst_seed_quartz", //
				Material.ROCK, Blocks.QUARTZ_BLOCK, "pickaxe", SoundType.STONE);
		blocks.add(burst_seed_quartz);
		burst_seed_prismarine = new BlockBurstSeed("burst_seed_prismarine", //
				Material.ROCK, Blocks.PRISMARINE, "pickaxe", SoundType.STONE);
		blocks.add(burst_seed_prismarine);
		burst_seed_crystal = new BlockBurstSeed("burst_seed_crystal", //
				Material.ROCK, new ItemStack(crystal), "pickaxe", SoundType.GLASS);
		blocks.add(burst_seed_crystal);

		obsidian_displacer = new ItemDisplacer("obsidian_displacer", false, "Pops obsidian and glass right off",
				"at the expense of some hunger");
		items.add(obsidian_displacer);
		empowered_displacer = new ItemDisplacer("empowered_displacer", true, "Pops almost everything right off!");
		items.add(empowered_displacer);
		freezer = new BlockFreezer("freezer");
		blocks.add(freezer);
		nourisher = new BlockNourisher("nourisher");
		blocks.add(nourisher);

		generator_heat = new BlockGeneratorHeat("generator_heat");
		blocks.add(generator_heat);

		// TIER 2

		crystal_nether = new MyItem("crystal_nether");
		items.add(crystal_nether);
		crystal_nether_block = new MyGlass("crystal_nether_block");
		blocks.add(crystal_nether_block);

		heat_block = new BlockHeat("heat_block");
		blocks.add(heat_block);

		picking_table = new BlockPickingTable("picking_table");
		blocks.add(picking_table);

		myst_dust_picker_fe = new ItemDustPicker("myst_dust_picker_fe", shard_fe);
		items.add(myst_dust_picker_fe);
		myst_dust_picker_ca = new ItemDustPicker("myst_dust_picker_ca", shard_ca);
		items.add(myst_dust_picker_ca);
		myst_dust_picker_c = new ItemDustPicker("myst_dust_picker_c", shard_c);
		items.add(myst_dust_picker_c);
		myst_dust_picker_o = new ItemDustPicker("myst_dust_picker_o", shard_o);
		items.add(myst_dust_picker_o);
		myst_dust_picker_au = new ItemDustPicker("myst_dust_picker_au", shard_au);
		items.add(myst_dust_picker_au);
		myst_dust_picker_h = new ItemDustPicker("myst_dust_picker_h", shard_h);
		items.add(myst_dust_picker_h);
		myst_dust_picker_p = new ItemDustPicker("myst_dust_picker_p", shard_p);
		items.add(myst_dust_picker_p);
		myst_dust_picker_n = new ItemDustPicker("myst_dust_picker_n", shard_n);
		items.add(myst_dust_picker_n);

		protection_block = new MyBlock("protection_block", Material.IRON).setHardness(1.5f).setResistance(2f);
		blocks.add(protection_block);
		heavy_protection_block = new MyBlock("heavy_protection_block", Material.IRON).setHardness(3f).setResistance(4f);
		blocks.add(heavy_protection_block);
		heat_furnace = new BlockHeatFurnace("heat_furnace");
		blocks.add(heat_furnace);

		precision_grinder_empty = new BlockPrecisionGrinderEmpty("precision_grinder_empty");
		blocks.add(precision_grinder_empty);

		// grinding_wheel_flint = new ItemGrindingWheel("grinding_wheel_flint",
		// "Allows
		// the grind of:", //
		// "", "");
		// items.add(grinding_wheel_flint);
		grinding_wheel_iron = new ItemGrindingWheel("grinding_wheel_iron");
		items.add(grinding_wheel_iron);
		grinding_wheel_diamond = new ItemGrindingWheel("grinding_wheel_diamond");
		items.add(grinding_wheel_diamond);
		grinding_wheel_crystal = new ItemGrindingWheel("grinding_wheel_crystal");
		items.add(grinding_wheel_crystal);
		// grinding_wheel_crystal_nether = new
		// ItemGrindingWheel("grinding_wheel_crystal_nether", "Allows the grind of:",
		//
		// "", "");
		// items.add(grinding_wheel_crystal_nether);

		// precision_grinder_flint = new
		// BlockPrecisionGrinder("precision_grinder_flint", grinding_wheel_flint);
		// blocks.add(precision_grinder_flint);
		precision_grinder_iron = new BlockPrecisionGrinder("precision_grinder_iron", grinding_wheel_iron);
		blocks.add(precision_grinder_iron);
		precision_grinder_diamond = new BlockPrecisionGrinder("precision_grinder_diamond", grinding_wheel_diamond);
		blocks.add(precision_grinder_diamond);
		precision_grinder_crystal = new BlockPrecisionGrinder("precision_grinder_crystal", grinding_wheel_crystal);
		blocks.add(precision_grinder_crystal);
		// precision_grinder_crystal_nether = new
		// BlockPrecisionGrinder("precision_grinder_crystal_nether",
		// grinding_wheel_crystal_nether);
		// blocks.add(precision_grinder_crystal_nether);

		// grinding_wheel_flint.grinder = precision_grinder_flint;
		grinding_wheel_iron.grinder = precision_grinder_iron;
		grinding_wheel_diamond.grinder = precision_grinder_diamond;
		grinding_wheel_crystal.grinder = precision_grinder_crystal;
		// grinding_wheel_crystal_nether.grinder = precision_grinder_crystal_nether;

		light_collector = new BlockLightCollector("light_collector");
		blocks.add(light_collector);
		locked_light = new ItemLockedLight("locked_light", "Drops from charged light collectors");
		items.add(locked_light);

		raw_mystic_iron = new MyItem("raw_mystic_iron");
		items.add(raw_mystic_iron);
		mystic_iron_ingot = new MyItem("mystic_iron_ingot");
		items.add(mystic_iron_ingot);
		mystic_iron_block = new MyBlock("mystic_iron_block", Material.CLAY).setHardness(.55f);
		blocks.add(mystic_iron_block);

		mystic_iron_sword = new MySword("mystic_iron_sword", material_tool_mystic_iron, mystic_iron_ingot, -2.4000000953674316);
		items.add(mystic_iron_sword);
		mystic_iron_dagger = new MyDagger("mystic_iron_dagger", material_tool_mystic_iron, mystic_iron_ingot,
				-2.4000000953674316 / 3d);
		items.add(mystic_iron_dagger);
		mystic_iron_axe = new MyAxe("mystic_iron_axe", material_tool_mystic_iron, mystic_iron_ingot, -3);
		items.add(mystic_iron_axe);
		mystic_iron_pickaxe = new MyPickaxe("mystic_iron_pickaxe", material_tool_mystic_iron, mystic_iron_ingot);
		items.add(mystic_iron_pickaxe);
		mystic_iron_shovel = new MyShovel("mystic_iron_shovel", material_tool_mystic_iron, mystic_iron_ingot);
		items.add(mystic_iron_shovel);
		// TODO mystic_iron_hoe;

		mystic_iron_helmet = new MyArmor("mystic_iron_helmet", "mystic_iron", EntityEquipmentSlot.HEAD, material_armor_mystic_iron,
				mystic_iron_ingot, 20);
		items.add(mystic_iron_helmet);
		mystic_iron_chest = new MyArmor("mystic_iron_chest", "mystic_iron", EntityEquipmentSlot.CHEST, material_armor_mystic_iron,
				mystic_iron_ingot, 20);
		items.add(mystic_iron_chest);
		mystic_iron_legs = new MyArmor("mystic_iron_legs", "mystic_iron", EntityEquipmentSlot.LEGS, material_armor_mystic_iron,
				mystic_iron_ingot, 20);
		items.add(mystic_iron_legs);
		mystic_iron_boots = new MyArmor("mystic_iron_boots", "mystic_iron", EntityEquipmentSlot.FEET, material_armor_mystic_iron,
				mystic_iron_ingot, 20);
		items.add(mystic_iron_boots);

		// TIER 3
		crystal_catalyst = new MyItem("crystal_catalyst");
		items.add(crystal_catalyst);
		metaldiamond = new MyItem("metaldiamond");
		items.add(metaldiamond);
		raw_soulsteel = new ItemRawSoulsteel("raw_soulsteel", "The trapped souls swirl inside.");
		items.add(raw_soulsteel);
		soul_dust = new MyItem("soul_dust");
		items.add(soul_dust);
		soulsteel_ingot = new MyItem("soulsteel_ingot", "A strong material with high enchantability.");
		items.add(soulsteel_ingot);
		mutation_paste = new MyItem("mutation_paste");
		items.add(mutation_paste);
		mutation_paste_block = new MyBlock("mutation_paste_block", Material.GRASS).setHardness(1f).setResistance(1f);
		blocks.add(mutation_paste_block);
		repairing_paste = new MyItem("repairing_paste", new String[] {
				"Place it on your left hand to slowly repair items on your right hand.", "Can repair up to 500 damage." }) {
			@Override
			public void onUpdate(ItemStack paste, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
				super.onUpdate(paste, worldIn, entityIn, itemSlot, isSelected);
				if (worldIn.isRemote)
					return;
				if (entityIn instanceof EntityPlayer
						&& ((EntityPlayer) entityIn).getHeldItemOffhand().getItem().equals(Main.repairing_paste)) {
					EntityPlayer player = (EntityPlayer) entityIn;
					if (player.getHeldItem(EnumHand.OFF_HAND).getItem().equals(Main.repairing_paste)) {
						ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
						if (timeConstraint(player) && stack.isItemDamaged() && RecipeRepairingPaste.isRepairable(stack.getItem())) {
							paste.damageItem(1, player);
							stack.damageItem(-1, player);
						}
					}
				}
			}

			private boolean timeConstraint(EntityPlayer player) {
				String str = (player.getEntityWorld().getTotalWorldTime() + "");
				return str.charAt(str.length() - 2) == '0';
			}
		}.setMaxStackSize(1).setMaxDamage(500);
		items.add(repairing_paste);
		raw_repairing_paste = new MyItem("raw_repairing_paste");
		items.add(raw_repairing_paste);
		soulsteel_block = new MyBlock("soulsteel_block", Material.CLAY).setHardness(.55f);
		blocks.add(soulsteel_block);

		soulsteel_sword = new MySword("soulsteel_sword", material_tool_soulsteel, soulsteel_ingot, -2.4000000953674316);
		items.add(soulsteel_sword);
		soulsteel_dagger = new MyDagger("soulsteel_dagger", material_tool_soulsteel, soulsteel_ingot, -2.4000000953674316 / 3d);
		items.add(soulsteel_dagger);
		soulsteel_axe = new MyAxe("soulsteel_axe", material_tool_soulsteel, soulsteel_ingot, -3);
		items.add(soulsteel_axe);
		soulsteel_pickaxe = new MyPickaxe("soulsteel_pickaxe", material_tool_soulsteel, soulsteel_ingot);
		items.add(soulsteel_pickaxe);
		soulsteel_shovel = new MyShovel("soulsteel_shovel", material_tool_soulsteel, soulsteel_ingot);
		items.add(soulsteel_shovel);
		// TODO soulsteel_hoe;

		soulsteel_helmet = new MyArmor("soulsteel_helmet", "soulsteel", EntityEquipmentSlot.HEAD, material_armor_soulsteel,
				soulsteel_ingot, 20);
		items.add(soulsteel_helmet);
		soulsteel_chest = new MyArmor("soulsteel_chest", "soulsteel", EntityEquipmentSlot.CHEST, material_armor_soulsteel,
				soulsteel_ingot, 20);
		items.add(soulsteel_chest);
		soulsteel_legs = new MyArmor("soulsteel_legs", "soulsteel", EntityEquipmentSlot.LEGS, material_armor_soulsteel,
				soulsteel_ingot, 20);
		items.add(soulsteel_legs);
		soulsteel_boots = new MyArmor("soulsteel_boots", "soulsteel", EntityEquipmentSlot.FEET, material_armor_soulsteel,
				soulsteel_ingot, 20);
		items.add(soulsteel_boots);

		stabiliser = new MyBlock("stabiliser", Material.ROCK).setHarvest("pickaxe", -1).setHardness(1).setResistance(1);
		blocks.add(stabiliser);

		separator = new BlockSeparator("separator");
		blocks.add(separator);

		picker_holder = new BlockPickerHolder("picker_holder");
		blocks.add(picker_holder);

		empty_rod = new ItemEmptyRod("empty_rod", ItemEmptyRod.RodType.EMPTY, null);
		items.add(empty_rod);

		myst_rod = new MyItem("myst_rod", "A rod filled with mysterious soul fluid.");
		items.add(myst_rod);
		rod_myst_2 = new ItemEmptyRod("rod_myst_2", ItemEmptyRod.RodType.MYST, Main.myst_rod);
		items.add(rod_myst_2);
		rod_myst_1 = new ItemEmptyRod("rod_myst_1", ItemEmptyRod.RodType.MYST, rod_myst_2);
		items.add(rod_myst_1);

		rod_blaze_3 = new ItemEmptyRod("rod_blaze_3", ItemEmptyRod.RodType.BLAZE, Items.BLAZE_ROD);
		items.add(rod_blaze_3);
		rod_blaze_2 = new ItemEmptyRod("rod_blaze_2", ItemEmptyRod.RodType.BLAZE, rod_blaze_3);
		items.add(rod_blaze_2);
		rod_blaze_1 = new ItemEmptyRod("rod_blaze_1", ItemEmptyRod.RodType.BLAZE, rod_blaze_2);
		items.add(rod_blaze_1);

		// totem_enderman = new BlockTotem("totem_enderman");
		// blocks.add(totem_enderman);
		// totem_zombie = new BlockStatue("totem_zombie");
		// blocks.add(totem_zombie);
		// totem_skeleton = new BlockTotem("totem_skeleton");
		// blocks.add(totem_skeleton);
		// totem_creeper = new BlockTotem("totem_creeper");
		// blocks.add(totem_creeper);
		// totem_ = new BlockTotem("totem_");
		// blocks.add(totem_);

		//

		//

		//

		metaldiamond_block = new MyBlock("metaldiamond_block", Material.IRON).setHardness(3).setResistance(3);
		blocks.add(metaldiamond_block);

		// tiles = new HashMap<>();
		// tiles.put("tile_core", TileCore.class);
		// tiles.put("tile_gravity_block", TileGravity.class);

		boxer = new BlockBoxer("boxer");
		blocks.add(boxer);
		Block[] blocksToBox = new Block[] {
				// WITH AXIS
				Blocks.BONE_BLOCK, Blocks.HAY_BLOCK, Main.furnace_tube,
				// NORMAL MINECRAFT
				Blocks.DIAMOND_BLOCK, Blocks.GOLD_BLOCK, Blocks.IRON_BLOCK, //
				Blocks.COAL_BLOCK, Blocks.EMERALD_BLOCK, Blocks.LAPIS_BLOCK, Blocks.QUARTZ_BLOCK, //
				Blocks.SLIME_BLOCK, Blocks.MELON_BLOCK, Blocks.NETHER_WART_BLOCK, //
				Blocks.NOTEBLOCK,
				// HWELL
				asul_block, soulsteel_block, mystic_iron_block, heavy_block, metaldiamond_block, //
				crystal_block, crystal_nether_block, //
				core_stone, core_heat, core_green, core_sentient, //
				crushing_block, boxer, producer, //
				compressed_clay, compressed_wool, mutation_paste_block, //
				protection_block, heavy_protection_block, //
				gravity_block, gravity_powered_block, antigravity_block, Main.antigravity_powered_block,//
		};
		boxes = new BlockBox[blocksToBox.length];
		for (int i = 0; i < blocksToBox.length; i++) {
			boxes[i] = new BlockBox(blocksToBox[i], i < 3);
			// DO NOT ADD BOXES TO BLOCKS TO REGISTER
			// THESE ARE DEALT SEPARATLY
		}

		RegisterRecipes.createNewCores();

		// blocks.sort(new Comparator<Block>() {
		// @Override
		// public int compare(Block o1, Block o2) {
		// return
		// o1.getUnlocalizedName().compareToIgnoreCase(o2.getUnlocalizedName());
		// }
		// });
		//
		// items.sort(new Comparator<Item>() {
		// @Override
		// public int compare(Item o1, Item o2) {
		// return
		// o1.getUnlocalizedName().compareToIgnoreCase(o2.getUnlocalizedName());
		// }
		// });

		//

		//

		// FLUIDS
		material_liquid_souls = new MaterialLiquid(MapColor.CYAN) {
			@Override
			public boolean blocksMovement() {
				return true;
			}

		};
		liquid_souls = new Fluid("liquid_souls", Util.res("liquid_souls"), Util.res("liquid_souls_flowing"), Color.white);
		FluidRegistry.addBucketForFluid(liquid_souls);
		// FluidRegistry.registerFluid(liquid_souls);

		liquid_souls_block = new BlockLiquidSouls(liquid_souls, material_liquid_souls);
		blocks.add(liquid_souls_block);

		//

		//

		creativeTab = new HwellCreativeTab();
		for (Block block : blocks) {
			block.setCreativeTab(creativeTab);
		}
		for (Item item : items) {
			item.setCreativeTab(creativeTab);
		}
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	public static void init(FMLInitializationEvent event) {

		HashSet<Class> loaded = new HashSet<>();
		for (Block block : blocks)
			if (block instanceof ITileEntityProvider) {
				// && ((IEntity) block).isToRegister() //
				// && GameRegistry.findRegistry(((IEntity) block).getTileEntityClass()) ==
				// null
				// //
				Class clazz = ((ITileEntityProvider) block).createNewTileEntity(null, 0).getClass();
				if (!loaded.contains(clazz)) {
					GameRegistry.registerTileEntity(clazz, new ResourceLocation(Hwell.MODID + ":tile_" + block.getUnlocalizedName()));
					loaded.add(clazz);
				}
			}

		OreDictionary.registerOre("logWood", myst_log);
		OreDictionary.registerOre("plankWood", myst_planks);
		OreDictionary.registerOre("oreGlowstone", glowstone_ore);
		OreDictionary.registerOre("oreQuartz", quartz_ore);

	}

}