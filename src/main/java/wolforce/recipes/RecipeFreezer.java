//package wolforce.recipes;
//
//import java.util.LinkedList;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.material.Material;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.init.Blocks;
//
//public class RecipeFreezer {
//
//	private static LinkedList<RecipeFreezer> recipes;
//
//	public static void initRecipes() {
//		recipes = new LinkedList<>();
//		put(Material.WATER, 0, new Block[] { Blocks.SNOW, Blocks.ICE });
//		put(Material.LAVA, 0, new Block[] { Blocks.OBSIDIAN });
//		// put(Blocks.FLOWING_WATER, 0, new Block[] { Blocks.SNOW, Blocks.ICE });
//		// put(Blocks.LAVA, 0, new Block[] { Blocks.OBSIDIAN });
//		// put(Blocks.FLOWING_LAVA, 0, new Block[] { Blocks.OBSIDIAN });
//	}
//
//	private static void put(Material mat, int meta, Block[] blocks) {
//		recipes.add(new RecipeFreezer(mat, meta, blocks));
//	}
//
//	///////////////////////////////////////////////////////////////////////
//
//	public static Block getResult(IBlockState stateIn) {
//		if (stateIn == null || !hasResult(stateIn))
//			return null;
//
//		for (RecipeFreezer r : recipes) {
//			if (r.matIn == stateIn.getMaterial()) {
//				if (r.metaIn == -1 || stateIn.getBlock().getMetaFromState(stateIn) == r.metaIn)
//					return r.blocksOut[(int) (Math.random() * r.blocksOut.length)];
//			}
//		}
//
//		return null;
//	}
//
//	public static boolean hasResult(IBlockState stateIn) {
//		for (RecipeFreezer r : recipes) {
//			if (r.matIn == stateIn.getMaterial()) {
//				if (r.metaIn == -1 || stateIn.getBlock().getMetaFromState(stateIn) == r.metaIn)
//					return true;
//			}
//		}
//		return false;
//	}
//
//	///////////////////////////////////////////////////////////////////////
//
//	private Material matIn;
//	private int metaIn;
//	private Block[] blocksOut;
//
//	public RecipeFreezer(Material mat, int meta, Block[] blocks) {
//		this.matIn = mat;
//		this.metaIn = meta;
//		this.blocksOut = blocks;
//	}
//
//}

package wolforce.recipes;

import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class RecipeFreezer {

	public static LinkedList<RecipeFreezer> recipes;

	public static void initRecipes(JsonArray recipesJson) {
		recipes = new LinkedList<>();
		for (JsonElement e : recipesJson) {
			recipes.add(readRecipe(e.getAsJsonObject()));
		}
	}

	private static RecipeFreezer readRecipe(JsonObject o) {
		FluidStack input = new FluidStack(FluidRegistry.getFluid(o.get("input").getAsString()), 1000);
		JsonArray outputArray = o.get("outputs").getAsJsonArray();
		ItemStack[] outputs = new ItemStack[outputArray.size()];
		for (int i = 0; i < outputArray.size(); i++) {
			JsonObject output = outputArray.get(i).getAsJsonObject();
			outputs[i] = ShapedRecipes.deserializeItem(output, true);
		}
		return new RecipeFreezer(input, outputs);
	}

	///////////////////////////////////////////////////////////////////////

	public static Block getResult(IBlockState stateIn) {
		if (stateIn == null || !hasResult(stateIn))
			return null;

		for (RecipeFreezer r : recipes) {
			if (r.fluidIn.getFluid().equals(FluidRegistry.lookupFluidForBlock(stateIn.getBlock()))) {
				return Block.getBlockFromItem(r.blocksOut[(int) (Math.random() * r.blocksOut.length)].getItem());
			}
		}

		return null;
	}

	public static boolean hasResult(IBlockState stateIn) {
		for (RecipeFreezer r : recipes) {
			if (r.fluidIn.getFluid().equals(FluidRegistry.lookupFluidForBlock(stateIn.getBlock()))) {
				return true;
			}
		}
		return false;
	}

	///////////////////////////////////////////////////////////////////////

	public FluidStack fluidIn;
	public ItemStack[] blocksOut;

	public RecipeFreezer(FluidStack fluid, ItemStack[] blocks) {
		this.fluidIn = fluid;
		this.blocksOut = blocks;
	}

	// @Override
	// public String toString() {
	// return "[ " + in.getUnlocalizedName() + " -> " + out + " ]";
	// }
}
