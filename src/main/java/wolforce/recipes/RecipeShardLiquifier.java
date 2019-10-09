package wolforce.recipes;

import java.util.LinkedList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class RecipeShardLiquifier {

	public static LinkedList<RecipeShardLiquifier> recipes;

	public static void initRecipes() {
		recipes = new LinkedList<>();
	}

	public static void put(ItemStack in, ItemStack out) {
		recipes.add(new RecipeShardLiquifier(in, out, null));
	}

	public static void put(ItemStack in, FluidStack out) {
		recipes.add(new RecipeShardLiquifier(in, null, out));
	}

	/**
	 * @return FluidStack or ItemStack or null
	 */
	public static Object getResult(IBlockState blockstate) {
		ItemStack input = new ItemStack(blockstate.getBlock(), 1, blockstate.getBlock().getMetaFromState(blockstate));
		if (input.getItem() == null)
			return null;
		for (RecipeShardLiquifier recipeTube : recipes)
			if (recipeTube.in.getItem().equals(input.getItem())) {
				if (recipeTube.outFluid != null)
					return recipeTube.outFluid.copy();
				if (recipeTube.out != null)
					return recipeTube.out.copy();
			}
		return null;
	}

	//

	//

	//

	public final ItemStack in;
	public final ItemStack out;
	public final FluidStack outFluid;

	public RecipeShardLiquifier(ItemStack in, ItemStack out, FluidStack outf) {
		this.in = in;
		this.out = out;
		this.outFluid = outf;
	}

}
