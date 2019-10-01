package wolforce.recipes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import wolforce.Main;
import wolforce.Util;
import wolforce.base.MyBlock;
import wolforce.blocks.BlockCore;
import wolforce.items.ItemShard;

public class RecipeCoring {

	public static final HashMap<BlockCore, HashMap<ItemShard, RecipeCoring>> recipes = new HashMap<>();

	public static void addCore(BlockCore core) {
		recipes.put(core, new HashMap<>());
	}

	public static void addCore(String coreRegistryName, String localizedName, int graftCost) {
		addCore(coreRegistryName, localizedName, graftCost, 0, 0, false);
	}

	public static void addCore(String coreRegistryName, String localizedName, int graftCost, int color1, int color2,
			boolean isCustom) {

		BlockCore newCore = new BlockCore(coreRegistryName, false, color1, color2, isCustom) {
			@Override
			public String getLocalizedName() {
				return localizedName;
			}
		};
		Main.cores.put(coreRegistryName, newCore);

		String temp = "" + localizedName;
		if (localizedName.endsWith(" Core"))
			temp = localizedName.substring(0, localizedName.indexOf(" Core"));
		if (localizedName.endsWith(" core"))
			temp = localizedName.substring(0, localizedName.indexOf(" core"));
		temp += " Graft";
		final String graftLoc = "" + temp;
		Block newGraft = new MyBlock(
				"graft_" + (coreRegistryName.startsWith("core_") ? coreRegistryName.substring(5) : coreRegistryName),
				Material.ROCK) {
			@Override
			public String getLocalizedName() {
				return graftLoc;
			}
		}.setHarvest("pickaxe", -1).setResistance(2).setHardness(2);

		Main.custom_grafts.put(newCore, newGraft);
		Main.graft_costs.put(newCore, graftCost);

		recipes.put(newCore, new HashMap<>());
	}

	public static void addCoreRecipe(String coreRegName, String shardString, ItemStack[] result, ItemStack[] consumes) {

		BlockCore core = Main.cores.get(coreRegName);
		ItemShard shard = ItemShard.getFromString(shardString);
		addCoreRecipe(core, shard, result, consumes);
	}

	public static void addCoreRecipe(BlockCore core, ItemShard shard, ItemStack[] result, ItemStack[] consumes) {

		if (!recipes.containsKey(core)) {
			throw new RuntimeException(core + " does not exist or has not been created yet.");
		}

		HashMap<ItemShard, RecipeCoring> coreRecipes = recipes.get(core);
		if (coreRecipes.containsKey(shard))
			coreRecipes.remove(shard);
		// throw new RuntimeException(shard + " shard already exists in core " + core);

		RecipeCoring recipe = new RecipeCoring(result, consumes);
		coreRecipes.put(shard, recipe);
	}

	public static RecipeCoring getResult(BlockCore coreBlock, ItemShard shard) {
		return recipes.get(coreBlock).get(shard);
	}

	//

	public final ItemStack[] possibleOutputs;
	public final ItemStack[] consumes;

	private RecipeCoring(ItemStack[] _result, ItemStack[] _consumes) {
		if (_result == null)
			throw new RuntimeException("RESULT OF CORING IS NULL");
		possibleOutputs = _result;
		if (_consumes.length == 1 && _consumes[0].getItem() == Items.AIR) {
			consumes = new ItemStack[] { new ItemStack(Blocks.AIR) };
		} else {
			consumes = new ItemStack[_consumes.length + possibleOutputs.length];
			for (int i = 0; i < _consumes.length; i++) {
				consumes[i] = _consumes[i];
			}
			for (int i = 0; i < possibleOutputs.length; i++) {
				consumes[_consumes.length - i] = possibleOutputs[i];
			}
		}
	}

	public Block getRandomResult() {
		return Block.getBlockFromItem(possibleOutputs[(int) (Math.random() * possibleOutputs.length)].getItem());
	}

	// FOR JEI INTEGRATION
	public static Set<Entry<ItemShard, RecipeCoring>> getRecipeList(BlockCore coreBlock) {
		return recipes.get(coreBlock).entrySet();
	}

	//

	//

	//

	//

	//

	// private static Block stone, heat, green, senti;

	public static void initRecipes(JsonObject recipesJson) {

		// initCoreRecipes(Main.cores.get("core_stone"),
		// recipesJson.get("core_stone").getAsJsonObject());
		// initCoreRecipes(Main.cores.get("core_anima"),
		// recipesJson.get("core_anima").getAsJsonObject());
		// initCoreRecipes(Main.cores.get("core_heat"),
		// recipesJson.get("core_heat").getAsJsonObject());
		// initCoreRecipes(Main.cores.get("core_green"),
		// recipesJson.get("core_green").getAsJsonObject());
		// initCoreRecipes(Main.cores.get("core_sentient"),
		// recipesJson.get("core_sentient").getAsJsonObject());

		for (Entry<String, JsonElement> entry : recipesJson.entrySet()) {
			String nameid = entry.getKey();
			// if (getNormalCoreBlock(nameid) != null)
			// continue;
			BlockCore core = Main.cores.get(nameid);
			initCoreRecipes(core, recipesJson.getAsJsonObject(nameid));
		}
	}

	// public static BlockCore getNormalCoreBlock(String nameid) {
	// switch (nameid) {
	// case "core_stone":
	// return Main.core_stone;
	// case "core_anima":
	// return Main.core_anima;
	// case "core_heat":
	// return Main.core_heat;
	// case "core_green":
	// return Main.core_green;
	// case "core_sentient":
	// return Main.core_sentient;
	// }
	// return null;
	// }

	private static void initCoreRecipes(BlockCore core, JsonObject recipesJson) {

		addCore(core);

		// HashMap<ItemShard, RecipeCoring> map = new HashMap<>();

		String[] shardsS = { "shard_c", "shard_fe", "shard_au", "shard_h", "shard_o", "shard_ca", "shard_p",
				"shard_n" };

		// ItemShard[] shardsI = { Main.shard_c, Main.shard_fe, Main.shard_au,
		// Main.shard_h, Main.shard_o, Main.shard_ca,
		// Main.shard_p, Main.shard_n };

		for (int i = 0; i < shardsS.length; i++) {
			if (recipesJson.has(shardsS[i])) {
				JsonObject shard = recipesJson.get(shardsS[i]).getAsJsonObject();
				ItemStack[] outs = readArrayOfStacks(shard.get("outputs").getAsJsonArray());
				ItemStack[] ins = readArrayOfStacks(shard.get("inputs").getAsJsonArray());
				addCoreRecipe(core, ItemShard.getFromString(shardsS[i]), outs, ins);
			}
		}

	}

	private static ItemStack[] readArrayOfStacks(JsonArray array) {
		LinkedList<ItemStack> list = new LinkedList<>();
		for (JsonElement elem : array) {
			// TODO ItemStack item = CraftingHelper.getIngredient(obj);
			// ShapelessOreRecipe
			ItemStack item = ShapedRecipes.deserializeItem(elem.getAsJsonObject(), true);
			if (item == null)
				System.err.println("Problem reading core recipes at " + array);
			else
				list.add(item);
		}
		return list.toArray(new ItemStack[] {});
	}

	public boolean canConsume(IBlockState state) {
		for (ItemStack itemStack : consumes) {
			if (Util.equalExceptAmount(itemStack, new ItemStack(state.getBlock())))
				return true;
		}
		return false;
	}

	// private static Irio[] readBlocks(JsonArray array) {
	// Irio[] blocks = new Irio[array.size()];
	// for (int i = 0; i < blocks.length; i++) {
	// blocks[i] = Util.readJsonIrio(array.get(i).getAsJsonObject());
	// }
	// return blocks;
	// }

	// // public static void initRecipes() {
	// //
	// // putRecipe(stone, Main.shard_c, COAL_BLOCK, LOG, LOG2);
	// // putRecipe(stone, Main.shard_fe, IRON_BLOCK, COBBLESTONE, STONE,
	// SANDSTONE);
	// // putRecipe(stone, Main.shard_au, GOLD_BLOCK, IRON_BLOCK);
	// // putRecipe(stone, Main.shard_h, SOUL_SAND, QUARTZ_BLOCK);
	// // putRecipe(stone, Main.shard_o, Main.compressed_clay, Blocks.AIR); // new
	// // Irio(WATER), new Irio(FLOWING_WATER, 15));
	// // putRecipe(stone, Main.shard_ca, BONE_BLOCK, GLASS, GLASS_PANE,
	// STAINED_GLASS,
	// // STAINED_GLASS_PANE);
	// // putRecipe(stone, Main.shard_p, REDSTONE_BLOCK, NETHERRACK, MAGMA);
	// // putRecipe(stone, Main.shard_n, Main.mutation_paste_block, Blocks.CLAY);
	// //
	// // putRecipe(heat, Main.shard_c, TNT, HARDENED_CLAY, STAINED_HARDENED_CLAY);
	// // // putRecipe(heat, Main.shard_fe, AIR); // TODO
	// // putRecipe(heat, Main.shard_au, GLOWSTONE, GOLD_BLOCK);
	// // putRecipe(heat, Main.shard_h, NETHERRACK, COAL_BLOCK);
	// // putRecipe(heat, Main.shard_o, SEA_LANTERN, SNOW, ICE, PACKED_ICE);
	// // putRecipe(heat, Main.shard_ca, QUARTZ_BLOCK, SNOW);
	// // putRecipe(heat, Main.shard_p, MAGMA, NETHERRACK);
	// // // putRecipe(heat, Main.shard_n, EMERALD_BLOCK, DIAMOND_BLOCK);
	// //
	// // putRecipe(green, Main.shard_c, MYCELIUM, RED_MUSHROOM_BLOCK,
	// // BROWN_MUSHROOM_BLOCK);
	// // putRecipe(green, Main.shard_fe, MELON_BLOCK, Main.fertilizer_block);
	// // putRecipe(green, Main.shard_au, PUMPKIN, Main.fertilizer_block);
	// // // putRecipe(green, Main.shard_h, );
	// // putRecipe(green, Main.shard_o, LAPIS_BLOCK, PRISMARINE);
	// // putRecipe(green, Main.shard_ca, Main.compressed_wool, AIR);
	// // // SPECIAL CASE
	// // // putRecipe(green, Main.shard_p, Main.compressed_clay, WATER, ICE,
	// // PACKED_ICE,
	// // // SNOW, DIRT);
	// // putRecipe(green, Main.shard_n, PRISMARINE, QUARTZ_BLOCK);
	// //
	// // Irio podzol = new Irio(Blocks.DIRT,
	// // //
	// //
	// Blocks.DIRT.getMetaFromState(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT,
	// // BlockDirt.DirtType.PODZOL)));
	// // //
	// //
	// System.out.println(DIRT.getMetaFromState(DIRT.getDefaultState().withProperty(BlockDirt.VARIANT,
	// // // BlockDirt.DirtType.PODZOL)));
	// // putRecipe(senti, Main.shard_c, new Irio(DIRT, 2), MYCELIUM);
	// // putRecipe(senti, Main.shard_fe, Main.metaldiamond_block, DIAMOND_BLOCK);
	// // putRecipe(senti, Main.shard_au, END_STONE, GLOWSTONE);
	// // putRecipe(senti, Main.shard_h, PURPUR_BLOCK, END_STONE);
	// // putRecipe(senti, Main.shard_o, DIAMOND_BLOCK, REDSTONE_BLOCK);
	// // // putRecipe(senti, Main.shard_ca, );
	// // // putRecipe(senti, Main.shard_p, AIR/* Main.leather_block */,
	// // // BROWN_MUSHROOM_BLOCK, RED_MUSHROOM_BLOCK);
	// // putRecipe(senti, Main.shard_n, EMERALD_BLOCK, DIAMOND_BLOCK);
	// // }
	//
	// // private static void putRecipe(Block coreBlock, Item shard, ItemStack
	// result,
	// // Irio... consumes) {
	// // recipeLists.get(coreBlock).put(shard, new RecipeCoring(result, consumes));
	// // }
	//
	// // private static void putRecipe(Block coreBlock, Item shard, Irio result,
	// // Block... bconsumes) {
	// // Irio[] consumes = new Irio[bconsumes.length];
	// // for (int i = 0; i < consumes.length; i++) {
	// // consumes[i] = new Irio(bconsumes[i]);
	// // }
	// // putRecipe(coreBlock, shard, result, consumes);
	// // }
	//
	// // private static void putRecipe(Block coreBlock, Item shard, Block result,
	// // Block... consumes) {
	// // putRecipe(coreBlock, shard, new Irio(result), consumes);
	// // }
	//
	// //

}
