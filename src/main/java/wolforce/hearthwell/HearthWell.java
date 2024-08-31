package wolforce.hearthwell;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import wolforce.hearthwell.bases.BaseFallingBlock;
import wolforce.hearthwell.blocks.*;
import wolforce.hearthwell.items.ItemPetrifiedWoodChunk;
import wolforce.hearthwell.items.ItemPrayerLetter;
import wolforce.hearthwell.items.ItemTokenBase;
import wolforce.hearthwell.items.ItemTokenOf;
import wolforce.hearthwell.registries.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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
		Particles.register(modBus);

		setupItems();
		setupBlocks();
		Blocks.register(modBus);
		Items.register(modBus);
	}

//	private void setupCompleteServer(final FMLDedicatedServerSetupEvent event) {
//		MapData.loadData();
//	}
//
//	private void setupCompleteClient(final FMLClientSetupEvent event) {
//		MapData.loadData();
//	}

	public static RegistryObject<Block> crystal_ore, crystal_ore_black, petrified_wood;
	public static RegistryObject<Block> myst_grass, fertile_soil, myst_dust_block, inert_dust_block;
	public static RegistryObject<Block> myst_bush, myst_bush_small, burst_seed;
	public static RegistryObject<Block> crushing_block;

	public static void setupBlocks() {
		Block.Properties organic = Properties.of().mapColor(MapColor.GRASS)// // Material.STONE
				.strength(1).sound(SoundType.GRASS);
//		Block.Properties plants = Block.Properties.create(Material.PLANTS, MaterialColor.PURPLE);
		Block.Properties sand = Properties.of().mapColor(MapColor.SAND)// // Material.STONE
				.strength(.7f).sound(SoundType.SAND);
		Block.Properties rock = Properties.of().mapColor(MapColor.STONE)// // Material.STONE
				.strength(1);
		Block.Properties rockNoToolNeeded = Properties.of().mapColor(MapColor.STONE)// // Material.STONE
				.strength(.8f);
//		Block.Properties torch = AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance()
//				.setLightLevel((state) -> 14).sound(SoundType.WOOD);
		Block.Properties plantNoDrops = Properties.of()
				.noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT) // Material.PLANT
				.instabreak().sound(SoundType.GRASS).noLootTable();
		Block.Properties miscellaneous = Properties.of()
				.noCollission().forceSolidOff().pushReaction(PushReaction.DESTROY).mapColor(MapColor.NONE) // Material.DECORATION
				.strength(.3f).sound(SoundType.BONE_BLOCK);

		myst_grass = addBlock("myst_grass", () -> new BlockMystGrass(organic), organic);
		petrified_wood = addBlock("petrified_wood", () -> new BlockPetrifiedWood(rockNoToolNeeded), rockNoToolNeeded);
		myst_dust_block = addBlock("myst_dust_block", () -> new BaseFallingBlock(sand), sand);
		inert_dust_block = addBlock("inert_dust_block", () -> new BaseFallingBlock(sand), sand);
		myst_bush = addBlock("myst_bush", () -> new BlockMystBush(true, plantNoDrops), plantNoDrops);
		myst_bush_small = addBlock("myst_bush_small", () -> new BlockMystBush(false, plantNoDrops), plantNoDrops);
		crystal_ore = addBlock("crystal_ore", () -> new BlockCrystalOre(rock, false), rock);
		crystal_ore_black = addBlock("crystal_ore_black", () -> new BlockCrystalOre(rock, true), rock);
		crushing_block = addBlock("crushing_block", () -> new BlockCrushingBlock(rockNoToolNeeded), rockNoToolNeeded);

		addBlock("core_anima", () -> new BlockCore(rock), rock);
		addBlock("core_crystal", () -> new BlockCore(rock), rock);
		addBlock("core_heat", () -> new BlockCore(rock), rock);
		addBlock("core_rock", () -> new BlockCore(rock), rock);
		addBlock("core_rotten", () -> new BlockCore(rock), rock);
		addBlock("core_soft", () -> new BlockCore(rock), rock);
		addBlock("core_verdant", () -> new BlockCore(rock), rock);

//		myst_light = addBlock("myst_light", new BlockMystLight(torch));

		// HAVE TILE REGISTRY
		burst_seed = addBlock("burst_seed", () -> new BlockBurstSeed(miscellaneous), miscellaneous);
		fertile_soil = addBlock("fertile_soil", () -> new BlockFertileSoil(organic), organic);
	}

	public static RegistryObject<Item> myst_dust, petrified_wood_chunk, crystal, flare_torch, prayer_letter, token_base;
	private static ArrayList<RegistryObject<Item>> tokenItems;

	public static void setupItems() {

		Item.Properties props = new Item.Properties();
		Item.Properties propsNoStack = new Item.Properties().stacksTo(1);
		myst_dust = addItem("myst_dust", props);
		addItem("inert_dust", props);
		petrified_wood_chunk = Items.register("petrified_wood_chunk", () -> new ItemPetrifiedWoodChunk(props));
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

		token_base = Items.register("token_base", () -> new ItemTokenBase(propsNoStack));
		tokenItems = new ArrayList<>(12);
		for (int i = 0; i < 12; i++) {
			int finalI = i;
			tokenItems.add(i, Items.register("token_" + i, () -> new ItemTokenOf(propsNoStack, finalI)));
		}

		flare_torch = addItem("flare_torch", props);

		prayer_letter = Items.register("prayer_letter", () -> new ItemPrayerLetter(propsNoStack));
	}

	//
	//
	//

	private static RegistryObject<Item> addItem(String string, Item.Properties props) {
		return Items.register(string, () -> new Item(props));
	}

	private static RegistryObject<Block> addBlock(String regId, Supplier<Block> block, Block.Properties props) {
		var s = Blocks.register(regId, block);
		Items.register(regId, () -> new BlockItem(s.get(), new Item.Properties()));
		return s;
	}

	public static Item getTokenItem(int i) {
		return tokenItems.get(i).get();
	}

	public static List<ItemTokenOf> getTokenItems() {
		return tokenItems.stream().map(e -> (ItemTokenOf) e.get()).toList();
	}


}
