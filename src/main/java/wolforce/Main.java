package wolforce;

import java.awt.Color;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import wolforce.blocks.BlockBurstSeed;
import wolforce.blocks.BlockCore;
import wolforce.blocks.BlockCrushing;
import wolforce.blocks.BlockDust;
import wolforce.blocks.BlockGravity;
import wolforce.blocks.BlockGroundShovel;
import wolforce.blocks.BlockHeat;
import wolforce.blocks.BlockHeatFurnace;
import wolforce.blocks.BlockLightCollector;
import wolforce.blocks.BlockMyGlass;
import wolforce.blocks.BlockMystBush;
import wolforce.blocks.BlockMystGrass;
import wolforce.blocks.BlockMystLeaves;
import wolforce.blocks.BlockPickingTable;
import wolforce.blocks.BlockPrecisionGrinder;
import wolforce.blocks.BlockPrecisionGrinderEmpty;
import wolforce.blocks.BlockSeparator;
import wolforce.blocks.BlockSlabLamp;
import wolforce.blocks.BlockTube;
import wolforce.blocks.MyLog;
import wolforce.fluids.BlockLiquidSouls;
import wolforce.items.ItemCrystalBowl;
import wolforce.items.ItemCrystalBowlWater;
import wolforce.items.ItemDustPicker;
import wolforce.items.ItemGrindingWheel;
import wolforce.items.ItemHeavyShears;
import wolforce.items.ItemLockedLight;
import wolforce.items.ItemMystDust;
import wolforce.items.ItemMystFertilizer;
import wolforce.items.ItemObsidianDisplacer;
import wolforce.tesrs.TesrSeparator;
import wolforce.tile.TileCore;
import wolforce.tile.TileGravity;
import wolforce.tile.TileSeparator;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
@Mod.EventBusSubscriber(modid = Main.MODID)
public class Main {

	static {
		FluidRegistry.enableUniversalBucket();
	}

	public static final String MODID = "hwell";
	public static final String NAME = "Hearth Well";
	public static final String VERSION = "1.0";

	public static final Logger logger = LogManager.getLogger(NAME);

	private static LinkedList<Item> items;

	private static LinkedList<Block> blocks;

	public static Material material_heavy, material_soulsteel;

	// tier 1
	public static Item dust;
	public static Item myst_dust, myst_fertilizer;
	public static Item heavy_mesh, heavy_ingot, heavy_nugget;
	public static Item heavy_shears, leaf_mesh;
	public static Item crystal, crystal_nether, crystal_bowl, crystal_bowl_water;
	public static Item shard_fe, shard_au, shard_o, shard_c, shard_h, shard_ca, shard_p, shard_n;
	public static Item obsidian_displacer;
	public static ItemGrindingWheel grinding_wheel_flint, grinding_wheel_iron, grinding_wheel_diamond, grinding_wheel_crystal,
			grinding_wheel_crystal_nether;
	// public static Item shard_water;

	public static Block heavy_block;
	public static Block crystal_block, crystal_nether_block;
	public static Block myst_grass;
	public static Block myst_bush, myst_bush_big;
	public static Block myst_dust_block;
	public static Block dust_block;
	public static Block dismantler, crushing_block, gravity_block, gravity_powered_block;
	public static Block myst_log, myst_planks, myst_leaves;
	public static Block light_collector;
	public static BlockCore core_stone, core_heat, core_green, core_sentient;
	public static Block picking_table;
	public static Block compressed_clay, moonstone_block, onyx_block, azurite_block, smooth_azurite_block, scorch_grit, scorch_glass, fullgrass_block,
			metal_diamond_block;

	// tier 2
	// public static Block coreGreen;
	// public static Block coreGreen2;

	// tier 3
	public static Item soul_dust;
	public static Item locked_light;
	public static Item myst_dust_picker_fe, myst_dust_picker_ca, //
			myst_dust_picker_c, myst_dust_picker_o, myst_dust_picker_au, //
			myst_dust_picker_h, myst_dust_picker_p, myst_dust_picker_n;

	public static Block slab_lamp;
	public static Block furnace_tube, heat_furnace;
	public static Block protection_block, heavy_protection_block;
	public static Block precision_grinder_flint, precision_grinder_iron, precision_grinder_diamond, precision_grinder_crystal,
			precision_grinder_crystal_nether, precision_grinder_empty;
	public static Block heat_block;

	public static Block burst_seed_stone, burst_seed_sand, burst_seed_dirt, burst_seed_snow, burst_seed_netherrack;

	public static CreativeTabs creativeTab;

	//

	// tier 4
	public static Block separator;

	public static Item asul_ingot;
	public static Item soulsteel_ingot;

	public static Block asul_block;
	public static Block soulsteel_block;
	public static Block weeping_block;

	static Item[] crystals;

	public static void initCrystals() {
		crystals = new Item[] { shard_ca, shard_c, shard_au, shard_fe, shard_o, shard_h };
	}

	public static Item getRandomCrystal(Random rand) {
		return crystals[rand.nextInt(crystals.length)];
	}

	private HashMap<String, Class> tiles;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		material_heavy = new Material(MapColor.SAND);
		material_soulsteel = new Material(MapColor.SAND);

		items = new LinkedList<>();
		blocks = new LinkedList<>();

		// TIER 1

		heavy_mesh = new MyItem("heavy_mesh");
		items.add(heavy_mesh);
		// TODO make new material?
		heavy_ingot = new MyItem("heavy_ingot");
		items.add(heavy_ingot);
		heavy_block = new MyBlock("heavy_block", Material.IRON).setResistance(2.5f).setHardness(1.6f);
		heavy_block.setHarvestLevel("pickaxe", -1);
		blocks.add(heavy_block);
		heavy_nugget = new MyItem("heavy_nugget") {
			@Override
			public int getItemBurnTime(ItemStack itemStack) {
				return 800;
			}
		};
		items.add(heavy_nugget);

		crushing_block = new BlockCrushing("crushing_block");
		blocks.add(crushing_block);
		core_stone = new BlockCore("core_stone", true);
		blocks.add(core_stone);
		heavy_shears = new ItemHeavyShears("heavy_shears");
		items.add(heavy_shears);
		gravity_block = new BlockGravity("gravity_block", true);
		blocks.add(gravity_block);
		gravity_powered_block = new BlockGravity("gravity_powered_block", false);
		blocks.add(gravity_powered_block);

		dust = new MyItem("dust");
		items.add(dust);
		myst_dust = new ItemMystDust("myst_dust", "Where does it come from?");
		items.add(myst_dust);
		myst_dust_block = new BlockDust("myst_dust_block");
		blocks.add(myst_dust_block);
		dust_block = new BlockDust("dust_block");
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
		crystal_block = new BlockMyGlass("crystal_block");
		blocks.add(crystal_block);

		crystal = new MyItem("crystal");
		items.add(crystal);

		crystal_bowl = new ItemCrystalBowl("crystal_bowl");
		items.add(crystal_bowl);
		crystal_bowl_water = new ItemCrystalBowlWater("crystal_bowl_water", "Drink it!");
		items.add(crystal_bowl_water);

		shard_c = new MyItem("shard_c");
		items.add(shard_c);
		shard_fe = new MyItem("shard_fe");
		items.add(shard_fe);
		shard_au = new MyItem("shard_au");
		items.add(shard_au);
		shard_o = new MyItem("shard_o");
		items.add(shard_o);
		shard_h = new MyItem("shard_h");
		items.add(shard_h);
		shard_ca = new MyItem("shard_ca");
		items.add(shard_ca);
		shard_p = new MyItem("shard_p");
		items.add(shard_p);
		shard_n = new MyItem("shard_n");
		items.add(shard_n);

		compressed_clay = new MyBlock("compressed_clay", Material.CLAY).setHardness(2).setResistance(2);
		blocks.add(compressed_clay);

		azurite_block = new MyBlock("azurite_block", Material.ROCK).setHardness(2).setResistance(2);
		blocks.add(azurite_block);
		smooth_azurite_block = new MyBlock("smooth_azurite_block", Material.ROCK).setHardness(2).setResistance(2);
		blocks.add(smooth_azurite_block);
		moonstone_block = new MyBlock("moonstone_block", Material.ROCK).setHardness(2).setResistance(2);
		blocks.add(moonstone_block);
		onyx_block = new MyBlock("onyx_block", Material.ROCK).setHardness(2).setResistance(2);
		blocks.add(onyx_block);
		scorch_grit = new BlockGroundShovel("scorch_grit");
		blocks.add(scorch_grit);
		fullgrass_block = new BlockGroundShovel("fullgrass_block");
		blocks.add(fullgrass_block);
		scorch_glass = new BlockMyGlass("scorch_glass");
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
				Material.ROCK, Blocks.SNOW, "shovel", SoundType.STONE);
		blocks.add(burst_seed_netherrack);

		obsidian_displacer = new ItemObsidianDisplacer("obsidian_displacer", "Pops obsidian right off", "at the expense of some hunger");
		items.add(obsidian_displacer);

		// TIER 2

		crystal_nether = new MyItem("crystal_nether");
		items.add(crystal_nether);
		crystal_nether_block = new BlockMyGlass("crystal_nether_block");
		blocks.add(crystal_nether_block);

		weeping_block = new MyBlock("weeping_block", Material.CLAY);
		blocks.add(weeping_block);

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

		grinding_wheel_flint = new ItemGrindingWheel("grinding_wheel_flint", "Allows the grind of:", //
				"", "");
		items.add(grinding_wheel_flint);
		grinding_wheel_iron = new ItemGrindingWheel("grinding_wheel_iron", "Allows the grind of:", //
				"", "");
		items.add(grinding_wheel_iron);
		grinding_wheel_diamond = new ItemGrindingWheel("grinding_wheel_diamond", "Allows the grind of:", //
				"", "");
		items.add(grinding_wheel_diamond);
		grinding_wheel_crystal = new ItemGrindingWheel("grinding_wheel_crystal", "Allows the grind of:", //
				"", "");
		items.add(grinding_wheel_crystal);
		grinding_wheel_crystal_nether = new ItemGrindingWheel("grinding_wheel_crystal_nether", "Allows the grind of:", //
				"", "");
		items.add(grinding_wheel_crystal_nether);

		precision_grinder_flint = new BlockPrecisionGrinder("precision_grinder_flint", grinding_wheel_flint);
		blocks.add(precision_grinder_flint);
		precision_grinder_iron = new BlockPrecisionGrinder("precision_grinder_iron", grinding_wheel_iron);
		blocks.add(precision_grinder_iron);
		precision_grinder_diamond = new BlockPrecisionGrinder("precision_grinder_diamond", grinding_wheel_diamond);
		blocks.add(precision_grinder_diamond);
		precision_grinder_crystal = new BlockPrecisionGrinder("precision_grinder_crystal", grinding_wheel_crystal);
		blocks.add(precision_grinder_crystal);
		precision_grinder_crystal_nether = new BlockPrecisionGrinder("precision_grinder_crystal_nether", grinding_wheel_crystal_nether);
		blocks.add(precision_grinder_crystal_nether);

		grinding_wheel_flint.grinder = precision_grinder_flint;
		grinding_wheel_iron.grinder = precision_grinder_iron;
		grinding_wheel_diamond.grinder = precision_grinder_diamond;
		grinding_wheel_crystal.grinder = precision_grinder_crystal;
		grinding_wheel_crystal_nether.grinder = precision_grinder_crystal_nether;

		light_collector = new BlockLightCollector("light_collector");
		blocks.add(light_collector);
		locked_light = new ItemLockedLight("locked_light");
		items.add(locked_light);

		core_green = new BlockCore("core_green", false);
		blocks.add(core_green);

		core_sentient = new BlockCore("core_sentient", false);
		blocks.add(core_sentient);

		//

		//

		//

		// TIER 4

		separator = new BlockSeparator("separator");
		blocks.add(separator);

		asul_ingot = new MyItem("asul_ingot");
		items.add(asul_ingot);

		soulsteel_ingot = new MyItem("soulsteel_ingot");
		items.add(soulsteel_ingot);

		asul_block = new MyBlock("asul_block", Material.CLAY).setHardness(.55f);
		asul_block.setHarvestLevel("pickaxe", -1);
		blocks.add(asul_block);

		soulsteel_block = new MyBlock("soulsteel_block", Material.CLAY).setHardness(.55f);
		blocks.add(soulsteel_block);

		// TIER 3

		soul_dust = new MyItem("soul_dust");
		items.add(soul_dust);

		heat_block = new BlockHeat("heat_block");
		blocks.add(heat_block);

		core_heat = new BlockCore("core_heat", false);
		blocks.add(core_heat);

		// coreGreen = new MyBlockTE("green_core", Material.CLAY);
		// blocks.add(coreGreen);
		// coreGreen2 = new MyBlockTE("sentient_green_core", Material.CLAY);
		// blocks.add(coreGreen2);
		// coreEnd = new MyBlockTE("end_core", Material.CLAY);
		// blocks.add(coreEnd);

		metal_diamond_block = new MyBlock("metal_diamond_block", Material.IRON).setHardness(3).setResistance(3);
		blocks.add(metal_diamond_block);

		tiles = new HashMap<>();
		tiles.put("tile_core", TileCore.class);
		tiles.put("tile_gravity_block", TileGravity.class);

		blocks.sort(new Comparator<Block>() {
			@Override
			public int compare(Block o1, Block o2) {
				return o1.getUnlocalizedName().compareToIgnoreCase(o2.getUnlocalizedName());
			}
		});

		items.sort(new Comparator<Item>() {
			@Override
			public int compare(Item o1, Item o2) {
				return o1.getUnlocalizedName().compareToIgnoreCase(o2.getUnlocalizedName());
			}
		});

		//

		// FLUIDS
		MaterialLiquid liquid_souls_mat = new MaterialLiquid(MapColor.CYAN) {
			@Override
			public boolean blocksMovement() {
				return true;
			}

		};
		liquid_souls = new Fluid("liquid_souls", Util.res("liquid_souls"), Util.res("liquid_souls_flowing"), Color.white);
		FluidRegistry.addBucketForFluid(liquid_souls);
		FluidRegistry.registerFluid(liquid_souls);

		liquid_souls_block = new BlockLiquidSouls(liquid_souls, liquid_souls_mat);
		blocks.add(liquid_souls_block);

		mapFluidState(liquid_souls_block, liquid_souls);

		//

		//

		creativeTab = new GodCreativeTab();
		for (Block block : blocks) {
			block.setCreativeTab(creativeTab);
		}
		for (Item item : items) {
			item.setCreativeTab(creativeTab);
		}
	}

	public static Fluid liquid_souls;
	public static Block liquid_souls_block;

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	@EventHandler
	public void init(FMLInitializationEvent event) {

		HashSet<Class> loaded = new HashSet<>();
		for (Block block : blocks)
			if (block instanceof ITileEntityProvider) {
				// && ((IEntity) block).isToRegister() //
				// && GameRegistry.findRegistry(((IEntity) block).getTileEntityClass()) == null
				// //
				Class clazz = ((ITileEntityProvider) block).createNewTileEntity(null, 0).getClass();
				if (!loaded.contains(clazz)) {
					GameRegistry.registerTileEntity(clazz, new ResourceLocation(MODID + ":tile_" + block.getUnlocalizedName()));
					loaded.add(clazz);
				}
			}

		GameRegistry.addSmelting(heavy_mesh, new ItemStack(heavy_ingot, 2), 0.5f);
		GameRegistry.addSmelting(dust_block, new ItemStack(Blocks.GLASS, 2), 0f);
		GameRegistry.addSmelting(leaf_mesh, new ItemStack(crystal_block, 1), .5f);
		GameRegistry.addSmelting(compressed_clay, new ItemStack(Blocks.HARDENED_CLAY, 9), 0f);
		GameRegistry.addSmelting(scorch_grit, new ItemStack(scorch_glass), 0f);

		OreDictionary.registerOre("logWood", myst_log);
		OreDictionary.registerOre("plankWood", myst_planks);

	}

	@SideOnly(Side.CLIENT)
	@EventHandler
	public void registerTesrs(FMLPostInitializationEvent event) {
		ClientRegistry.bindTileEntitySpecialRenderer(TileSeparator.class, new TesrSeparator());
	}

	private static void mapFluidState(Block block, Fluid fluid) {
		Item item = Item.getItemFromBlock(block);
		FluidStateMapper mapper = new FluidStateMapper(fluid);
		if (item != Items.AIR) {
			ModelLoader.registerItemVariants(item);
			ModelLoader.setCustomMeshDefinition(item, mapper);
		}
		ModelLoader.setCustomStateMapper(block, mapper);
	}

	static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {
		public final ModelResourceLocation location;

		public FluidStateMapper(Fluid fluid) {
			this.location = new ModelResourceLocation(MODID + ":fluid_block", fluid.getName());
		}

		@Nonnull
		@Override
		protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
			return location;
		}

		@Nonnull
		@Override
		public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
			return location;
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		RecipeCrushing.init();
		RecipeGrinder.init();
		ItemMystDust.initRecipes();
		TileCore.initRecipes();
		initCrystals();
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	// ----------------------
	// FOR ITEMS

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		for (Item item : items) {
			event.getRegistry().registerAll(item);
		}
	}

	@SubscribeEvent
	public static void registerRendersItem(ModelRegistryEvent event) {
		for (Item item : items) {
			registerRenderItem(item);
		}
	}

	private static void registerRenderItem(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	// ----------------------
	// FOR BLOCKS

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for (Block b : blocks)
			event.getRegistry().registerAll(b);
	}

	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
		for (Block block : blocks)
			event.getRegistry().registerAll(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	@SubscribeEvent
	public static void registerRendersBlock(ModelRegistryEvent event) {
		for (Block block : blocks) {
			Item item = Item.getItemFromBlock(block);
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}

	//

	//

	//

	public static class GodCreativeTab extends CreativeTabs {

		public GodCreativeTab() {
			super("hwell");
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Main.myst_dust);
		}
	}

}
