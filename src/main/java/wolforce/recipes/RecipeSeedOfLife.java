package wolforce.recipes;

import java.util.HashSet;
import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import wolforce.Util;

public class RecipeSeedOfLife {

	static LinkedList<Irio> blocks;

	public static void initRecipes(JsonArray recipesJson) {
		blocks = new LinkedList<>();
		for (JsonElement e : recipesJson) {
			Irio irio = Util.readJsonIrio(e.getAsJsonObject());
			blocks.add(irio);
		}
	}

	public static boolean getResult(Block block, int metaFromState) {
		for (Irio irio : blocks) {
			if (irio.equals(new Irio(block, metaFromState)))
				return true;
		}
		return false;
	}

}
