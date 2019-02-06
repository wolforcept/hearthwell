package wolforce.recipes;

import static net.minecraft.init.Blocks.*;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.init.Blocks;
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
		putRecipe(stone, Main.shard_o, Main.compressed_clay, Blocks.AIR); // new Irio(WATER), new Irio(FLOWING_WATER, 15));
		putRecipe(stone, Main.shard_ca, BONE_BLOCK, GLASS, GLASS_PANE, STAINED_GLASS, STAINED_GLASS_PANE);
		putRecipe(stone, Main.shard_p, REDSTONE_BLOCK, NETHERRACK, MAGMA);
		putRecipe(stone, Main.shard_n, Main.mutation_paste_block, Blocks.CLAY);

		putRecipe(heat, Main.shard_c, TNT, HARDENED_CLAY, STAINED_HARDENED_CLAY);
		// putRecipe(heat, Main.shard_fe, AIR); // TODO
		putRecipe(heat, Main.shard_au, GLOWSTONE, GOLD_BLOCK);
		putRecipe(heat, Main.shard_h, NETHERRACK, COAL_BLOCK);
		putRecipe(heat, Main.shard_o, SEA_LANTERN, SNOW, ICE, PACKED_ICE);
		putRecipe(heat, Main.shard_ca, QUARTZ_BLOCK, SNOW);
		putRecipe(heat, Main.shard_p, MAGMA, NETHERRACK);
//		putRecipe(heat, Main.shard_n, EMERALD_BLOCK, DIAMOND_BLOCK);

		putRecipe(green, Main.shard_c, MYCELIUM, RED_MUSHROOM_BLOCK, BROWN_MUSHROOM_BLOCK);
		putRecipe(green, Main.shard_fe, MELON_BLOCK, Main.fertilizer_block);
		putRecipe(green, Main.shard_au, PUMPKIN, Main.fertilizer_block);
		// putRecipe(green, Main.shard_h, );
		putRecipe(green, Main.shard_o, LAPIS_BLOCK, PRISMARINE);
		putRecipe(green, Main.shard_ca, Main.compressed_wool, AIR);
		// SPECIAL CASE
		// putRecipe(green, Main.shard_p, Main.compressed_clay, WATER, ICE, PACKED_ICE,
		// SNOW, DIRT);
		putRecipe(green, Main.shard_n, PRISMARINE, QUARTZ_BLOCK);

		// Irio podzol = new Irio(DIRT,
		// DIRT.getMetaFromState(DIRT.getDefaultState().withProperty(BlockDirt.VARIANT,
		// BlockDirt.DirtType.PODZOL)));
		// System.out.println(DIRT.getMetaFromState(DIRT.getDefaultState().withProperty(BlockDirt.VARIANT,
		// BlockDirt.DirtType.PODZOL)));
		putRecipe(senti, Main.shard_c, new Irio(DIRT, 2), MYCELIUM);
		putRecipe(senti, Main.shard_fe, Main.metaldiamond_block, DIAMOND_BLOCK); // TODO
		putRecipe(senti, Main.shard_au, END_STONE, GLOWSTONE);
		putRecipe(senti, Main.shard_h, PURPUR_BLOCK, END_STONE);
		putRecipe(senti, Main.shard_o, DIAMOND_BLOCK, REDSTONE_BLOCK);
		// putRecipe(senti, Main.shard_ca, );
		// putRecipe(senti, Main.shard_p, AIR/* Main.leather_block */,
		// BROWN_MUSHROOM_BLOCK, RED_MUSHROOM_BLOCK);
		putRecipe(senti, Main.shard_n, EMERALD_BLOCK, DIAMOND_BLOCK);
	}

	private static void putRecipe(Block coreBlock, Item shard, Irio result, Irio... consumes) {
		recipeLists.get(coreBlock).put(shard, new RecipeCoring(result, consumes));
	}

	private static void putRecipe(Block coreBlock, Item shard, Irio result, Block... bconsumes) {
		Irio[] consumes = new Irio[bconsumes.length];
		for (int i = 0; i < consumes.length; i++) {
			consumes[i] = new Irio(bconsumes[i]);
		}
		putRecipe(coreBlock, shard, result, consumes);
	}

	private static void putRecipe(Block coreBlock, Item shard, Block result, Block... consumes) {
		putRecipe(coreBlock, shard, new Irio(result), consumes);
	}

	//

	public static RecipeCoring getResult(Block coreBlock, Item shard) {
		if (coreBlock != stone && coreBlock != heat && coreBlock != green && coreBlock != senti)
			throw new RuntimeException(coreBlock.getUnlocalizedName() + " is not a valid core");
		// SPECIAL CASE
		if (coreBlock == green && shard == Main.shard_p)
			return new RecipeCoring(new Irio(Math.random() < .5 ? BROWN_MUSHROOM_BLOCK : RED_MUSHROOM_BLOCK),
					new Irio(Main.fertilizer_block));
		return recipeLists.get(coreBlock).get(shard);
	}

	//

	public final Irio result;
	public final Irio[] consumes;

	private RecipeCoring(Irio _result, Irio... _consumes) {
		if (_result == null)
			throw new RuntimeException("RESULT OF CORING IS NULL");
		result = _result;
		consumes = new Irio[_consumes.length + 1];
		for (int i = 0; i < _consumes.length; i++) {
			consumes[i] = _consumes[i];
		}
		consumes[_consumes.length] = result;
	}

	private Irio i(Block block) {
		return new Irio(block);
	}

	private Irio i(Block block, int meta) {
		return new Irio(block, meta);
	}

	// FOR JEI INTEGRATION
	public static Set<Entry<Item, RecipeCoring>> getRecipeList(BlockCore coreBlock) {
		return recipeLists.get(coreBlock).entrySet();
	}
}
