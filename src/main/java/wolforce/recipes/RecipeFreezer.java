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

import java.util.HashMap;
import java.util.LinkedList;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import wolforce.Main;
import wolforce.Util;

public class RecipeFreezer implements IRecipeWrapper {

	public static final LinkedList<RecipeFreezer> recipes = new LinkedList<>();

	public static void initRecipes() {
		put(Blocks.WATER, 0, new Block[] { Blocks.SNOW, Blocks.ICE });
		put(Blocks.FLOWING_WATER, 0, new Block[] { Blocks.SNOW, Blocks.ICE });
		put(Blocks.LAVA, 0, new Block[] { Blocks.OBSIDIAN });
		put(Blocks.FLOWING_LAVA, 0, new Block[] { Blocks.OBSIDIAN });
	}

	private static void put(Block block, int meta, Block[] blocks) {
		recipes.add(new RecipeFreezer(block, meta, blocks));
	}

	///////////////////////////////////////////////////////////////////////

	public static Block getResult(IBlockState stateIn) {
		if (stateIn == null || !hasResult(stateIn))
			return null;

		for (RecipeFreezer r : recipes) {
			if (r.blockIn == stateIn.getBlock()) {
				if (r.metaIn == -1 || stateIn.getBlock().getMetaFromState(stateIn) == r.metaIn)
					return r.blocksOut[(int) (Math.random() * r.blocksOut.length)];
			}
		}

		return null;
	}

	public static boolean hasResult(IBlockState stateIn) {
		for (RecipeFreezer r : recipes) {
			if (r.blockIn == stateIn.getBlock()) {
				if (r.metaIn == -1 || stateIn.getBlock().getMetaFromState(stateIn) == r.metaIn)
					return true;
			}
		}
		return false;
	}

	///////////////////////////////////////////////////////////////////////

	public Block blockIn;
	public int metaIn;
	public Block[] blocksOut;

	public RecipeFreezer(Block block, int meta, Block[] blocks) {
		this.blockIn = block;
		this.metaIn = meta;
		this.blocksOut = blocks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		Util.setIngredients(ingredients, new Block[] { Main.freezer, blockIn }, blocksOut);
	}

	// @Override
	// public String toString() {
	// return "[ " + in.getUnlocalizedName() + " -> " + out + " ]";
	// }
}
