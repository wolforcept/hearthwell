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

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import wolforce.Util;

public class RecipeFreezer {

	public static final LinkedList<RecipeFreezer> recipes = new LinkedList<>();

	public static void initRecipes() {
		put(FluidRegistry.WATER, 0, new Block[] { Blocks.SNOW, Blocks.ICE });
		put(FluidRegistry.LAVA, 0, new Block[] { Blocks.OBSIDIAN });
	}

	private static void put(Fluid fluid, int meta, Block[] blocks) {
		recipes.add(new RecipeFreezer(new FluidStack(fluid, Fluid.BUCKET_VOLUME), meta, blocks));
	}

	///////////////////////////////////////////////////////////////////////

	public static Block getResult(IBlockState stateIn) {
		if (stateIn == null || !hasResult(stateIn))
			return null;

		for (RecipeFreezer r : recipes) {
			if (r.fluidIn.getFluid().equals(Util.getFluidFromBlock(stateIn))) {
				if (r.metaIn == -1 || stateIn.getBlock().getMetaFromState(stateIn) == r.metaIn)
					return r.blocksOut[(int) (Math.random() * r.blocksOut.length)];
			}
		}

		return null;
	}

	public static boolean hasResult(IBlockState stateIn) {
		for (RecipeFreezer r : recipes) {
			if (r.fluidIn.getFluid().equals(Util.getFluidFromBlock(stateIn))) {
				if (r.metaIn == -1 || stateIn.getBlock().getMetaFromState(stateIn) == r.metaIn)
					return true;
			}
		}
		return false;
	}

	///////////////////////////////////////////////////////////////////////

	public FluidStack fluidIn;
	public int metaIn;
	public Block[] blocksOut;

	public RecipeFreezer(FluidStack fluid, int meta, Block[] blocks) {
		this.fluidIn = fluid;
		this.metaIn = meta;
		this.blocksOut = blocks;
	}

	// @Override
	// public String toString() {
	// return "[ " + in.getUnlocalizedName() + " -> " + out + " ]";
	// }
}
