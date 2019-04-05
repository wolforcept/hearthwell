package wolforce.recipes;

import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class RecipeTube {

	public static LinkedList<RecipeTube> recipes;

	public static void initRecipes(JsonArray recipesJson) {
		recipes = new LinkedList<>();
		for (JsonElement e : recipesJson) {
			recipes.add(readRecipe(e.getAsJsonObject()));
		}
	}

	private static RecipeTube readRecipe(JsonObject o) {
		ItemStack input = ShapedRecipes.deserializeItem(o.get("input").getAsJsonObject(), true);
		if (o.has("output_fluid")) {
			FluidStack output = new FluidStack(FluidRegistry.getFluid(o.get("output_fluid").getAsString()), 1000);
			return new RecipeTube(input, null, output);
		}
		ItemStack output = ShapedRecipes.deserializeItem(o.get("output").getAsJsonObject(), true);
		return new RecipeTube(input, output, null);
	}

	// public static void initRecipes() {
	// put(new ItemStack(Main.raw_asul_block), new ItemStack(Main.asul_block));
	// put(new ItemStack(Blocks.CACTUS), new FluidStack(FluidRegistry.WATER, 1000));
	// put(new ItemStack(Blocks.LEAVES), new FluidStack(FluidRegistry.WATER, 1000));
	// put(new ItemStack(Blocks.LEAVES2), new FluidStack(FluidRegistry.WATER,
	// 1000));
	// put(new ItemStack(Main.myst_leaves), new FluidStack(FluidRegistry.WATER,
	// 1000));
	// put(new ItemStack(Blocks.SNOW), new FluidStack(FluidRegistry.WATER, 1000));
	// put(new ItemStack(Blocks.CLAY), new FluidStack(FluidRegistry.WATER, 1000));
	// put(new ItemStack(Blocks.ICE), new FluidStack(FluidRegistry.WATER, 1000));
	// put(new ItemStack(Blocks.PACKED_ICE), new FluidStack(FluidRegistry.WATER,
	// 1000));
	//
	// put(new ItemStack(Blocks.STONE), new FluidStack(FluidRegistry.LAVA, 1000));
	// put(new ItemStack(Blocks.COBBLESTONE), new FluidStack(FluidRegistry.LAVA,
	// 1000));
	// put(new ItemStack(Blocks.SANDSTONE), new FluidStack(FluidRegistry.LAVA,
	// 1000));
	//
	// put(new ItemStack(Main.myst_dust_block), new FluidStack(Main.liquid_souls,
	// 1000));
	// }

	public static void put(ItemStack in, ItemStack out) {
		recipes.add(new RecipeTube(in, out, null));
	}

	public static void put(ItemStack in, FluidStack out) {
		recipes.add(new RecipeTube(in, null, out));
	}

	/**
	 * @return FluidStack or ItemStack or null
	 */
	public static Object getResult(IBlockState blockstate) {
		ItemStack input = new ItemStack(blockstate.getBlock(), 1, blockstate.getBlock().getMetaFromState(blockstate));
		if (input.getItem() == null)
			return null;
		for (RecipeTube recipeTube : recipes)
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

	//

	public final ItemStack in;
	public final ItemStack out;
	public final FluidStack outFluid;

	public RecipeTube(ItemStack in, ItemStack out, FluidStack outf) {
		this.in = in;
		this.out = out;
		this.outFluid = outf;
	}

	@Override
	public String toString() {
		return "[ " + in.getUnlocalizedName() + " -> " + out.getUnlocalizedName() + " ]";
	}

}
