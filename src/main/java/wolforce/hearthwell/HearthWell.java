package wolforce.hearthwell;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import wolforce.hearthwell.bases.BaseFallingBlock;
import wolforce.hearthwell.bases.BlockItemProperties;
import wolforce.hearthwell.blocks.BlockBurstSeed;
import wolforce.hearthwell.blocks.BlockCore;
import wolforce.hearthwell.blocks.BlockCrushingBlock;
import wolforce.hearthwell.blocks.BlockCrystalOre;
import wolforce.hearthwell.blocks.BlockFertileSoil;
import wolforce.hearthwell.blocks.BlockMystBush;
import wolforce.hearthwell.blocks.BlockMystGrass;
import wolforce.hearthwell.blocks.BlockPetrifiedWood;
import wolforce.hearthwell.items.ItemPetrifiedWoodChunk;
import wolforce.hearthwell.items.ItemPrayerLetter;
import wolforce.hearthwell.items.ItemTokenBase;
import wolforce.hearthwell.items.ItemTokenOf;
import wolforce.hearthwell.registries.Entities;
import wolforce.hearthwell.registries.TileEntities;

@Mod(HearthWell.MODID)
public class HearthWell {

	public static final String MODID = "hearthwell";
	public static final String VERSION = "0.1";

	public HearthWell() {
		ConfigServer.init();
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigServer.CONFIG_SPEC, MODID + "_server.toml");
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
//		modBus.addListener(this::setupCompleteClient);
//		modBus.addListener(this::setupCompleteServer);
		MinecraftForge.EVENT_BUS.register(this);
		TileEntities.REGISTRY.register(modBus);
		Entities.REGISTRY.register(modBus);
	}

//	private void setupCompleteServer(final FMLDedicatedServerSetupEvent event) {
//		MapData.loadData();
//	}
//
//	private void setupCompleteClient(final FMLClientSetupEvent event) {
//		MapData.loadData();
//	}

	public static HashMap<String, Block> blocks = new HashMap<>();
	public static HashMap<String, Item> items = new HashMap<>();

	public static CreativeModeTab group = new CreativeModeTab(0, "Hearth Well") {

		@Override
		public ItemStack makeIcon() {
			return new ItemStack(myst_dust);
		}
	};

	@Target(ElementType.FIELD)
	@interface Mineable {
		String value();
	}

	@Mineable("shovel")
	public static Block crystal_ore, crystal_ore_black, petrified_wood;
	@Mineable("shovel")
	public static Block myst_grass, fertile_soil, myst_dust_block, inert_dust_block;
	public static Block myst_bush, myst_bush_small, burst_seed;
	public static Block crushing_block;

	public static void setupBlocks() {
		Block.Properties organic = Block.Properties.of(Material.GRASS, MaterialColor.COLOR_PURPLE)//
				.strength(1).sound(SoundType.GRASS);
//		Block.Properties plants = Block.Properties.create(Material.PLANTS, MaterialColor.PURPLE);
		Block.Properties sand = Block.Properties.of(Material.SAND, MaterialColor.COLOR_PURPLE)//
				.strength(.7f).sound(SoundType.SAND);
		Block.Properties rock = Block.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY)//
				.strength(1);
		Block.Properties rockNoToolNeeded = Block.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY)//
				.strength(.8f);
//		Block.Properties torch = AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance()
//				.setLightLevel((state) -> 14).sound(SoundType.WOOD);
		Block.Properties plantNoDrops = Properties.of(Material.PLANT, MaterialColor.COLOR_PURPLE).noCollission()
				.instabreak().sound(SoundType.GRASS).noDrops();
		Block.Properties miscellaneous = Properties.of(Material.DECORATION, MaterialColor.NONE).strength(.3f)
				.sound(SoundType.BONE_BLOCK);

		myst_grass = addBlock("myst_grass", new BlockMystGrass(organic));
		petrified_wood = addBlock("petrified_wood", new BlockPetrifiedWood(rockNoToolNeeded));
		myst_dust_block = addBlock("myst_dust_block", new BaseFallingBlock(sand));
		inert_dust_block = addBlock("inert_dust_block", new BaseFallingBlock(sand));
		myst_bush = addBlock("myst_bush", new BlockMystBush(true, plantNoDrops));
		myst_bush_small = addBlock("myst_bush_small", new BlockMystBush(false, plantNoDrops));
		crystal_ore = addBlock("crystal_ore", new BlockCrystalOre(rock, false));
		crystal_ore_black = addBlock("crystal_ore_black", new BlockCrystalOre(rock, true));
		crushing_block = addBlock("crushing_block", new BlockCrushingBlock(rockNoToolNeeded));

		addBlock("core_anima", new BlockCore(rock));
		addBlock("core_crystal", new BlockCore(rock));
		addBlock("core_heat", new BlockCore(rock));
		addBlock("core_rock", new BlockCore(rock));
		addBlock("core_rotten", new BlockCore(rock));
		addBlock("core_soft", new BlockCore(rock));
		addBlock("core_verdant", new BlockCore(rock));

//		myst_light = addBlock("myst_light", new BlockMystLight(torch));

		// HAVE TILE REGISTRY
		burst_seed = addBlock("burst_seed", new BlockBurstSeed(miscellaneous));
		fertile_soil = addBlock("fertile_soil", new BlockFertileSoil(organic));
	}

	public static Item myst_dust, petrified_wood_chunk, crystal, flare_torch, prayer_letter, token_base;
	private static ArrayList<ItemTokenOf> tokenItems;

	public static void setupItems() {

		Item.Properties props = new Item.Properties().tab(group);
		Item.Properties propsNoStack = new Item.Properties().tab(group).stacksTo(1);
		myst_dust = addItem("myst_dust", props);
		addItem("inert_dust", props);
		petrified_wood_chunk = addItem("petrified_wood_chunk", new ItemPetrifiedWoodChunk(props));
		crystal = addItem("crystal", props);
		addItem("mystic_ingot", props);
		addItem("inert_seed", props);

		addItem("crystal_black", props);
		addItem("crystal_blue", props);
		addItem("crystal_brown", props);
		addItem("crystal_cyan", props);
		addItem("crystal_green", props);
		addItem("crystal_orange", props);
		addItem("crystal_pink", props);
		addItem("crystal_purple", props);
		addItem("crystal_red", props);
		addItem("crystal_white", props);
		addItem("crystal_yellow", props);

		token_base = addItem("token_base", new ItemTokenBase(propsNoStack));
		tokenItems = new ArrayList<ItemTokenOf>(12);
		for (int i = 0; i < 12; i++)
			tokenItems.add(i, addItem("token_" + i, new ItemTokenOf(propsNoStack, i)));

		flare_torch = addItem("flare_torch", props);

		prayer_letter = addItem("prayer_letter", new ItemPrayerLetter(propsNoStack));
	}

	//
	//
	//

	private static Item addItem(String string, Item.Properties props) {
		return addItem(string, new Item(props));
	}

	private static <T extends Item> T addItem(String string, T item) {
		item.setRegistryName(new ResourceLocation(MODID, string));
		items.put(string, item);
		return item;
	}

	private static Block addBlock(String regId, Block block) {
		block.setRegistryName(new ResourceLocation(MODID, regId));
		blocks.put(regId, block);
		if (block instanceof BlockItemProperties)
			addItem(regId, new BlockItem(block, ((BlockItemProperties) block).getItemProperties()));
		return block;
	}

	public static Item getTokenItem(int i) {
		return tokenItems.get(i);
	}

	public static List<ItemTokenOf> getTokenItems() {
		return tokenItems.stream().toList();
	}
}
