package wolforce.recipes;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import wolforce.Main;

public class RecipeTube {

	public final static LinkedList<RecipeTube> recipes = new LinkedList<>();

	public static void initRecipes() {
		put(Main.raw_asul_block, Main.asul_block);
		put(Blocks.CACTUS, Blocks.WATER);
		put(Blocks.LEAVES, Blocks.WATER);
		put(Blocks.LEAVES2, Blocks.WATER);
		put(Main.myst_leaves, Blocks.WATER);
		put(Blocks.SNOW, Blocks.WATER);
		put(Blocks.CLAY, Blocks.WATER);
		put(Blocks.ICE, Blocks.WATER);
		put(Blocks.PACKED_ICE, Blocks.WATER);

		put(Blocks.STONE, Blocks.LAVA);
		put(Blocks.COBBLESTONE, Blocks.LAVA);
		put(Blocks.SANDSTONE, Blocks.LAVA);

		put(Main.myst_dust_block, Main.liquid_souls_block);
	}

	private static void put(Block in, Block out) {
		recipes.add(new RecipeTube(in, out));
	}

	public static Block getResult(Block block) {
		if (block == null)
			return null;
		for (RecipeTube recipeTube : recipes)
			if (recipeTube.in.equals(block))
				return recipeTube.out;
		return null;
	}
	//

	//

	//

	//

	public final Block in;
	public final Block out;

	public RecipeTube(Block in, Block out) {
		this.in = in;
		this.out = out;
	}

	@Override
	public String toString() {
		return "[ " + in.getUnlocalizedName() + " -> " + out.getUnlocalizedName() + " ]";
	}

}
