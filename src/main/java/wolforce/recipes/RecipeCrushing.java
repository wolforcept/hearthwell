package wolforce.recipes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import wolforce.Main;

public class RecipeCrushing {

	private static final HashMap<Irio, RecipeCrushing[]> recipes = new HashMap<>();

	public static void initRecipes() {
		putRecipe(new Irio(Blocks.COBBLESTONE), new RecipeCrushing(Main.dust, 2));
		putRecipe(new Irio(Blocks.STONE), new RecipeCrushing(Main.dust, 2));
		putRecipe(new Irio(Blocks.SANDSTONE), new RecipeCrushing(Main.dust, 2));
		putRecipe(new Irio(Main.heavy_nugget), new RecipeCrushing(Main.fuel_dust, 2));

		putRecipe(new Irio(Main.myst_rod), new RecipeCrushing(Main.myst_dust, 2));

		putRecipe(new Irio(Blocks.GRAVEL), //
				new RecipeCrushing(Items.FLINT, 1, .6), //
				new RecipeCrushing(Items.FLINT, 2, .3), //
				new RecipeCrushing(Items.FLINT, 0, .1)//
		);
		putRecipe(new Irio(Blocks.CLAY), new RecipeCrushing(Items.CLAY_BALL, 4));
		putRecipe(new Irio(Blocks.SOUL_SAND), new RecipeCrushing(Main.soul_dust, 4));
		putRecipe(new Irio(Blocks.TALLGRASS, 1), new RecipeCrushing(Items.WHEAT_SEEDS, 1, .75));

		putRecipe(new Irio(Main.citrinic_sand), //
				new RecipeCrushing(Main.salt, 2, .5), //
				new RecipeCrushing(Main.salt, 1, .25), //
				new RecipeCrushing(Main.salt, 3, .25)//
		);

		putRecipe(new Irio(Main.crystal), //
				new RecipeCrushing(Main.shard_ca, 1, .125), //
				new RecipeCrushing(Main.shard_c, 1, .125), //
				new RecipeCrushing(Main.shard_h, 1, .125), //
				new RecipeCrushing(Main.shard_o, 1, .125), //
				new RecipeCrushing(Main.shard_fe, 1, .125), //
				new RecipeCrushing(Main.shard_n, 1, .125), //
				new RecipeCrushing(Main.shard_p, 1, .125), //
				new RecipeCrushing(Main.shard_au, 1, .125) //
		);
		putRecipe(new Irio(Main.crystal_nether), //
				new RecipeCrushing(Main.shard_ca, 1, .125), //
				new RecipeCrushing(Main.shard_c, 1, .125), //
				new RecipeCrushing(Main.shard_h, 1, .125), //
				new RecipeCrushing(Main.shard_o, 1, .125), //
				new RecipeCrushing(Main.shard_fe, 1, .125), //
				new RecipeCrushing(Main.shard_p, 1, .125), //
				new RecipeCrushing(Main.shard_n, 1, .125), //
				new RecipeCrushing(Main.shard_au, 1, .125) //
		);

		putRecipe(new Irio(Blocks.LADDER), //
				new RecipeCrushing(Items.STICK, 2)//
		);
	}

	private static void putRecipe(Irio irio, RecipeCrushing... list) {
		recipes.put(irio, list);
	}

	public static Iterable<ItemStack> getResult(ItemStack itemstack) {
		// RecipeCrushing[] possible = recipes.get(new Irio(itemstack.getItem(),
		// itemstack.getMetadata()));

		RecipeCrushing[] possible = getRecipeOf(itemstack);

		if (possible != null) {
			List list = new LinkedList<ItemStack>();
			for (int i = 0; i < itemstack.getCount(); i++)
				for (ItemStack singleresult : getSingleResult(possible))
					list.add(singleresult);
			return list;
		}
		return null;
	}

	private static RecipeCrushing[] getRecipeOf(ItemStack itemstack) {
		for (Entry<Irio, RecipeCrushing[]> recipe : recipes.entrySet()) {
			if (recipe.getKey().equals(new Irio(itemstack)))
				return recipe.getValue();
		}
		return null;
	}

	private static ItemStack[] getSingleResult(RecipeCrushing[] possible) {
		double d = Math.random();
		int i = 0;
		while (true) {

			if (i >= possible.length)
				return new ItemStack[] {};
			if (d < possible[i].probability)
				return new ItemStack[] { possible[i].stack.copy() };
			d -= possible[i].probability;
			i++;
		}
	}

	//

	public final ItemStack stack;
	public final double probability;

	private RecipeCrushing(ItemStack stack, double probability) {
		this.stack = stack;
		this.probability = probability;
	}

	private RecipeCrushing(Item item) {
		this(new ItemStack(item, 1), 1.0);
	}

	private RecipeCrushing(Item item, int n) {
		this(new ItemStack(item, n), 1.0);
	}

	private RecipeCrushing(Item item, int n, double prob) {
		this(new ItemStack(item, n), prob);
	}

	private RecipeCrushing(Block block) {
		this(new ItemStack(block, 1), 1.0);
	}

	public static Set<Entry<Irio, RecipeCrushing[]>> getRecipeList() {
		return recipes.entrySet();
	}
}
