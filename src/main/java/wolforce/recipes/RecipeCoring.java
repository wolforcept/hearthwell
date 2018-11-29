package wolforce.recipes;

import static net.minecraft.init.Blocks.AIR;
import static net.minecraft.init.Blocks.BONE_BLOCK;
import static net.minecraft.init.Blocks.BROWN_MUSHROOM_BLOCK;
import static net.minecraft.init.Blocks.COAL_BLOCK;
import static net.minecraft.init.Blocks.COBBLESTONE;
import static net.minecraft.init.Blocks.DIAMOND_BLOCK;
import static net.minecraft.init.Blocks.DIRT;
import static net.minecraft.init.Blocks.EMERALD_BLOCK;
import static net.minecraft.init.Blocks.END_STONE;
import static net.minecraft.init.Blocks.FLOWING_WATER;
import static net.minecraft.init.Blocks.GLASS;
import static net.minecraft.init.Blocks.GLASS_PANE;
import static net.minecraft.init.Blocks.GLOWSTONE;
import static net.minecraft.init.Blocks.GOLD_BLOCK;
import static net.minecraft.init.Blocks.HARDENED_CLAY;
import static net.minecraft.init.Blocks.ICE;
import static net.minecraft.init.Blocks.IRON_BLOCK;
import static net.minecraft.init.Blocks.LAPIS_BLOCK;
import static net.minecraft.init.Blocks.LOG;
import static net.minecraft.init.Blocks.LOG2;
import static net.minecraft.init.Blocks.MAGMA;
import static net.minecraft.init.Blocks.MELON_BLOCK;
import static net.minecraft.init.Blocks.MYCELIUM;
import static net.minecraft.init.Blocks.NETHERRACK;
import static net.minecraft.init.Blocks.PACKED_ICE;
import static net.minecraft.init.Blocks.PRISMARINE;
import static net.minecraft.init.Blocks.PUMPKIN;
import static net.minecraft.init.Blocks.PURPUR_BLOCK;
import static net.minecraft.init.Blocks.QUARTZ_BLOCK;
import static net.minecraft.init.Blocks.REDSTONE_BLOCK;
import static net.minecraft.init.Blocks.RED_MUSHROOM_BLOCK;
import static net.minecraft.init.Blocks.SANDSTONE;
import static net.minecraft.init.Blocks.SEA_LANTERN;
import static net.minecraft.init.Blocks.SNOW;
import static net.minecraft.init.Blocks.SOUL_SAND;
import static net.minecraft.init.Blocks.STAINED_GLASS;
import static net.minecraft.init.Blocks.STAINED_GLASS_PANE;
import static net.minecraft.init.Blocks.STAINED_HARDENED_CLAY;
import static net.minecraft.init.Blocks.STONE;
import static net.minecraft.init.Blocks.TNT;
import static net.minecraft.init.Blocks.WATER;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.item.Item;
import wolforce.Main;
import wolforce.blocks.BlockCore;

public class RecipeCoring {

	private static final HashMap<Block, HashMap<Item, RecipeCoring>> recipeLists = new HashMap<>();
	private static Block stone, heat, green, senti;

	public static void initRecipes() {

		stone = Main.core_stone;
		heat = Main.core_heat;
		green = Main.core_green;
		senti = Main.core_sentient;

		recipeLists.put(stone, new HashMap());
		recipeLists.put(heat, new HashMap());
		recipeLists.put(green, new HashMap());
		recipeLists.put(senti, new HashMap());

		putRecipe(stone, Main.shard_c, COAL_BLOCK, LOG, LOG2);
		putRecipe(stone, Main.shard_fe, IRON_BLOCK, COBBLESTONE, STONE, SANDSTONE);
		putRecipe(stone, Main.shard_au, GOLD_BLOCK, IRON_BLOCK);
		putRecipe(stone, Main.shard_h, SOUL_SAND, QUARTZ_BLOCK);
		putRecipe(stone, Main.shard_o, new Iri(Main.compressed_clay), new Iri(WATER), new Iri(FLOWING_WATER, 15));
		putRecipe(stone, Main.shard_ca, BONE_BLOCK, GLASS, GLASS_PANE, STAINED_GLASS, STAINED_GLASS_PANE);
		putRecipe(stone, Main.shard_p, REDSTONE_BLOCK, NETHERRACK, MAGMA);
		// putRecipe(stone, Main.shard_n, AIR); // TODO

		putRecipe(heat, Main.shard_c, TNT, HARDENED_CLAY, STAINED_HARDENED_CLAY);
		// putRecipe(heat, Main.shard_fe, AIR); // TODO
		putRecipe(heat, Main.shard_au, GLOWSTONE, GOLD_BLOCK);
		putRecipe(heat, Main.shard_h, NETHERRACK, COAL_BLOCK);
		putRecipe(heat, Main.shard_o, SEA_LANTERN, SNOW, ICE, PACKED_ICE);
		putRecipe(heat, Main.shard_ca, QUARTZ_BLOCK, SNOW);
		putRecipe(heat, Main.shard_p, MAGMA, NETHERRACK);
		putRecipe(heat, Main.shard_n, EMERALD_BLOCK, DIAMOND_BLOCK);

		putRecipe(green, Main.shard_c, MYCELIUM, RED_MUSHROOM_BLOCK, BROWN_MUSHROOM_BLOCK);
		putRecipe(green, Main.shard_fe, MELON_BLOCK, TNT);
		putRecipe(green, Main.shard_au, PUMPKIN, MELON_BLOCK);
		// putRecipe(green, Main.shard_h, AIR); // TODO
		putRecipe(green, Main.shard_o, LAPIS_BLOCK, PRISMARINE);
		putRecipe(green, Main.shard_ca, AIR);
		// SPECIAL CASE
		// putRecipe(green, Main.shard_p, Main.compressed_clay, WATER, ICE, PACKED_ICE,
		// SNOW, DIRT);
		putRecipe(green, Main.shard_n, PRISMARINE, QUARTZ_BLOCK);

		Iri podzol = new Iri(DIRT,
				DIRT.getMetaFromState(DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL)));
		putRecipe(senti, Main.shard_c, podzol, MYCELIUM);
		// putRecipe(senti, Main.shard_fe, AIR); // TODO
		putRecipe(senti, Main.shard_au, END_STONE, GLOWSTONE);
		putRecipe(senti, Main.shard_h, PURPUR_BLOCK, END_STONE);
		putRecipe(senti, Main.shard_o, DIAMOND_BLOCK, REDSTONE_BLOCK);
		// putRecipe(senti, Main.shard_ca, AIR); // TODO
		putRecipe(senti, Main.shard_p, AIR/* Main.leather_block */, BROWN_MUSHROOM_BLOCK, RED_MUSHROOM_BLOCK);
		putRecipe(senti, Main.shard_n, AIR/* Blocks.endblock */, END_STONE);
	}

	private static void putRecipe(Block coreBlock, Item shard, Iri result, Iri... consumes) {
		recipeLists.get(coreBlock).put(shard, new RecipeCoring(result, consumes));
	}

	private static void putRecipe(Block coreBlock, Item shard, Iri result, Block... bconsumes) {
		Iri[] consumes = new Iri[bconsumes.length];
		for (int i = 0; i < consumes.length; i++) {
			consumes[i] = new Iri(bconsumes[i]);
		}
		putRecipe(coreBlock, shard, result, consumes);
	}

	private static void putRecipe(Block coreBlock, Item shard, Block result, Block... consumes) {
		putRecipe(coreBlock, shard, new Iri(result), consumes);
	}

	//

	public static RecipeCoring getResult(Block coreBlock, Item shard) {
		if (coreBlock != stone && coreBlock != heat && coreBlock != green && coreBlock != senti)
			throw new RuntimeException(coreBlock.getUnlocalizedName() + " is not a valid core");
		// SPECIAL CASE
		if (coreBlock == green && shard == Main.shard_p)
			return new RecipeCoring(new Iri(Math.random() < .5 ? BROWN_MUSHROOM_BLOCK : RED_MUSHROOM_BLOCK), new Iri(MELON_BLOCK));
		return recipeLists.get(coreBlock).get(shard);
	}

	//

	public final Iri result;
	public final Iri[] consumes;

	private RecipeCoring(Iri result, Iri... consumes) {
		this.result = result;
		this.consumes = consumes;
	}

	private Iri i(Block block) {
		return new Iri(block);
	}

	private Iri i(Block block, int meta) {
		return new Iri(block, meta);
	}

	// FOR JEI INTEGRATION
	public static Set<Entry<Item, RecipeCoring>> getRecipeList(BlockCore coreBlock) {
		return recipeLists.get(coreBlock).entrySet();
	}
}
