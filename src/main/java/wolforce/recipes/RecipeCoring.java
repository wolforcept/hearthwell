package wolforce.recipes;

import static net.minecraft.init.Blocks.*;
import static wolforce.blocks.BlockCore.CoreType.core_au;
import static wolforce.blocks.BlockCore.CoreType.core_c;
import static wolforce.blocks.BlockCore.CoreType.core_ca;
import static wolforce.blocks.BlockCore.CoreType.core_fe;
import static wolforce.blocks.BlockCore.CoreType.core_h;
import static wolforce.blocks.BlockCore.CoreType.core_n;
import static wolforce.blocks.BlockCore.CoreType.core_o;
import static wolforce.blocks.BlockCore.CoreType.core_p;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import wolforce.Main;
import wolforce.blocks.BlockCore.CoreType;

public class RecipeCoring {

	private static final HashMap<Block, HashMap<CoreType, RecipeCoring>> recipeLists = new HashMap<>();
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

		putRecipe(stone, core_c, COAL_BLOCK, LOG, LOG2);
		putRecipe(stone, core_fe, IRON_BLOCK, COBBLESTONE, STONE, SANDSTONE);
		putRecipe(stone, core_au, GOLD_BLOCK, IRON_BLOCK);
		putRecipe(stone, core_h, SOUL_SAND, QUARTZ_BLOCK); // TODO
		putRecipe(stone, core_o, new Iri(Main.compressed_clay), new Iri(WATER), new Iri(FLOWING_WATER, 15));
		putRecipe(stone, core_ca, BONE_BLOCK, GLASS, GLASS_PANE, STAINED_GLASS, STAINED_GLASS_PANE);
		putRecipe(stone, core_p, REDSTONE_BLOCK, NETHERRACK, MAGMA);
		putRecipe(stone, core_n, AIR); // TODO

		putRecipe(heat, core_c, TNT, HARDENED_CLAY, STAINED_HARDENED_CLAY);
		putRecipe(heat, core_fe, AIR); // TODO
		putRecipe(heat, core_au, GLOWSTONE, GOLD_BLOCK);
		putRecipe(heat, core_h, NETHERRACK, COAL_BLOCK);
		putRecipe(heat, core_o, SEA_LANTERN, SNOW, ICE, PACKED_ICE);
		putRecipe(heat, core_ca, QUARTZ_BLOCK, SNOW);
		putRecipe(heat, core_p, MAGMA, NETHERRACK);
		putRecipe(heat, core_n, EMERALD_BLOCK, DIAMOND_BLOCK);

		putRecipe(green, core_c, MYCELIUM, RED_MUSHROOM, BROWN_MUSHROOM);
		putRecipe(green, core_fe, MELON_BLOCK, TNT);
		putRecipe(green, core_au, PUMPKIN, MELON_BLOCK);
		putRecipe(green, core_h, AIR); // TODO
		putRecipe(green, core_o, LAPIS_BLOCK, PRISMARINE);
		putRecipe(green, core_ca, AIR);
		// SPECIAL CASE
		putRecipe(green, core_p, Main.compressed_clay, WATER, ICE, PACKED_ICE, SNOW, DIRT);
		putRecipe(green, core_n, PRISMARINE, QUARTZ_BLOCK);

		Iri podzol = new Iri(DIRT,
				DIRT.getMetaFromState(DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL)));
		putRecipe(senti, core_c, podzol, MYCELIUM);
		putRecipe(senti, core_fe, AIR); // TODO
		putRecipe(senti, core_au, END_STONE, GLOWSTONE);
		putRecipe(senti, core_h, PURPUR_BLOCK, END_STONE);
		putRecipe(senti, core_o, DIAMOND_BLOCK, REDSTONE_BLOCK);
		putRecipe(senti, core_ca, AIR); // TODO
		putRecipe(senti, core_p, AIR/*Main.leather_block*/, BROWN_MUSHROOM_BLOCK, RED_MUSHROOM_BLOCK);
		putRecipe(senti, core_n, AIR/*Blocks.endblock*/, END_STONE);
	}

	private static void putRecipe(Block coreBlock, CoreType coreType, Iri result, Iri... consumes) {
		recipeLists.get(coreBlock).put(coreType, new RecipeCoring(result, consumes));
	}

	private static void putRecipe(Block coreBlock, CoreType coreType, Iri result, Block... bconsumes) {
		Iri[] consumes = new Iri[bconsumes.length];
		for (int i = 0; i < consumes.length; i++) {
			consumes[i] = new Iri(bconsumes[i]);
		}
		putRecipe(coreBlock, coreType, result, consumes);
	}

	private static void putRecipe(Block coreBlock, CoreType coreType, Block result, Block... consumes) {
		putRecipe(coreBlock, coreType, new Iri(result), consumes);
	}

	//

	public static RecipeCoring getResult(Block coreBlock, CoreType coreType) {
		if (coreBlock != stone && coreBlock != heat && coreBlock != green && coreBlock != senti)
			throw new RuntimeException(coreBlock.getUnlocalizedName() + " is not a valid core");
		// SPECIAL CASE
		if (coreBlock == green && coreType == CoreType.core_p)
			return new RecipeCoring(new Iri(Math.random() < .5 ? BROWN_MUSHROOM_BLOCK : RED_MUSHROOM_BLOCK), new Iri(MELON_BLOCK));
		return recipeLists.get(coreBlock).get(coreType);
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
}
