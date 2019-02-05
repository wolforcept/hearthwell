package wolforce.recipes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import wolforce.Main;
import wolforce.items.ItemShard;

import static net.minecraft.init.Blocks.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class RecipePuller {

	static RecipePuller[] recipes;

	public static void initRecipes() {
		recipes = new RecipePuller[] { //

				new RecipePuller(COAL_ORE, 0.3, Main.shard_c), //
				new RecipePuller(IRON_ORE, 0.25, Main.shard_fe), //
				// sum .55

				new RecipePuller(GOLD_ORE, 0.08, Main.shard_au), //
				new RecipePuller(REDSTONE_ORE, 0.08, Main.shard_p), //
				new RecipePuller(Main.quartz_ore, 0.08, Main.shard_ca), //
				new RecipePuller(Main.glowstone_ore, 0.08, Main.shard_au), //
				new RecipePuller(LAPIS_ORE, 0.08, Main.shard_o), //
				// .55 + .4 = .95

				new RecipePuller(DIAMOND_ORE, 0.025, Main.shard_o), //
				new RecipePuller(EMERALD_ORE, 0.025, Main.shard_n), //
				// .95 + .5 = 1
		};
	}

	public static ItemStack getRandomPull(List<ItemShard> shardsInLiquid) {
		if (shardsInLiquid != null && !shardsInLiquid.isEmpty() && Math.random() < .5) {
			LinkedList<RecipePuller> preferredRecipes = new LinkedList<>();
			for (ItemShard shardInLiquid : shardsInLiquid) {
				for (RecipePuller recipe : recipes) {
					if (recipe.shard.equals(shardInLiquid) && !preferredRecipes.contains(recipe)) {
						preferredRecipes.add(recipe);
					}
				}
			}
			if (!preferredRecipes.isEmpty())
				return new ItemStack(//
						preferredRecipes.get(//
								(int) (preferredRecipes.size() * Math.random())//
						).block, //
						1 + (int) (Math.random() * 2));
		}
		double rand = Math.random();
		for (int i = 0; i < recipes.length; i++) {
			rand -= recipes[i].prob;
			if (rand <= 0)
				return new ItemStack(recipes[i].block, 1 + (int) (Math.random() * 2));
		}
		return ItemStack.EMPTY;
	}

	//

	//

	//

	private Block block;
	private double prob;
	private ItemShard shard;

	public RecipePuller(Block block, double prob, ItemShard shard) {
		this.block = block;
		this.prob = prob;
		this.shard = shard;
	}
}
