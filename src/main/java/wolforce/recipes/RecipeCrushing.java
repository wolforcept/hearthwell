package wolforce.recipes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import wolforce.Main;

public class RecipeCrushing {

	private static final HashMap<Item, RecipeCrushing[]> recipes = new HashMap<>();

	public static void initRecipes() {
		putRecipe(new ItemStack(Blocks.COBBLESTONE), new RecipeCrushing(Main.dust));
		putRecipe(new ItemStack(Blocks.STONE), new RecipeCrushing(Main.dust));
		putRecipe(new ItemStack(Blocks.SANDSTONE), new RecipeCrushing(Main.dust));

		putRecipe(new ItemStack(Main.heavy_ingot), //
				new RecipeCrushing(Main.dust), new RecipeCrushing(Main.myst_dust, 1, .2));

		putRecipe(new ItemStack(Blocks.GRAVEL), null, new RecipeCrushing(Items.FLINT, 1, .6), //
				new RecipeCrushing(Items.FLINT, 2, .3), new RecipeCrushing(Items.FLINT, 0, .1));
		putRecipe(new ItemStack(Blocks.CLAY), new RecipeCrushing(Items.CLAY_BALL, 4));
		putRecipe(new ItemStack(Blocks.SOUL_SAND), new RecipeCrushing(Main.soul_dust, 4));

		putRecipe(new ItemStack(Main.crystal_block), null, new RecipeCrushing(Main.crystal, 5, .50), //
				new RecipeCrushing(Main.crystal, 4, .20), new RecipeCrushing(Main.crystal, 6, .20), //
				new RecipeCrushing(Main.crystal, 3, .05), new RecipeCrushing(Main.crystal, 7, .05) //
		);
		putRecipe(new ItemStack(Main.crystal), null, // <- this null means that only one of the recipes will output
				new RecipeCrushing(Main.shard_ca, 1, .18), //
				new RecipeCrushing(Main.shard_c, 1, .18), //
				new RecipeCrushing(Main.shard_h, 1, .18), //
				new RecipeCrushing(Main.shard_o, 1, .12), //
				new RecipeCrushing(Main.shard_fe, 1, .12), //
				new RecipeCrushing(Main.shard_n, 1, .12), //
				new RecipeCrushing(Main.shard_p, 1, .05), //
				new RecipeCrushing(Main.shard_au, 1, .05) //
		);
		putRecipe(new ItemStack(Main.crystal_nether), null, //
				new RecipeCrushing(Main.shard_ca, 1, .14), //
				new RecipeCrushing(Main.shard_c, 1, .14), //
				new RecipeCrushing(Main.shard_h, 1, .14), //
				new RecipeCrushing(Main.shard_o, 1, .14), //
				new RecipeCrushing(Main.shard_fe, 1, .11), //
				new RecipeCrushing(Main.shard_p, 1, .11), //
				new RecipeCrushing(Main.shard_n, 1, .11), //
				new RecipeCrushing(Main.shard_au, 1, .11) //
		);
	}

	private static void putRecipe(ItemStack itemstack, RecipeCrushing... list) {
		recipes.put(itemstack.getItem(), list);
	}

	public static Iterable<ItemStack> getResult(ItemStack itemstack) {
		RecipeCrushing[] possible = recipes.get(itemstack.getItem());

		if (possible != null) {
			List list = new LinkedList<ItemStack>();
			for (int i = 0; i < itemstack.getCount(); i++)
				for (ItemStack singleresult : getSingleResult(possible))
					list.add(singleresult);
			return list;
		}
		return null;
	}

	private static ItemStack[] getSingleResult(RecipeCrushing[] possible) {
		if (possible[0] == null) { // check if only one output
			double d = Math.random();
			int i = 1;
			while (true) {
				if (d < possible[i].probability)
					return new ItemStack[] { possible[i].item.copy() };
				d -= possible[i].probability;
				i++;
			}
		} else {
			ArrayList<ItemStack> list = new ArrayList<>(possible.length);
			for (RecipeCrushing recipe : possible) {
				if (Math.random() < recipe.probability)
					list.add(recipe.item.copy());
			}
			return (ItemStack[]) list.toArray(new ItemStack[list.size()]);
		}
	}

	//

	public final ItemStack item;
	public final double probability;

	private RecipeCrushing(ItemStack item, double probability) {
		this.item = item;
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
}
