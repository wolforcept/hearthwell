package wolforce.hwell.recipes;

import net.minecraft.block.Block;
import wolforce.hwell.Main;
import wolforce.hwell.blocks.BlockBox;

public class RecipeBoxer  {

	public static Block getResult(Block blockIn) {
		if (blockIn == null)
			return null;
		for (BlockBox box : Main.boxes)
			if (box.block.equals(blockIn))
				return box;
		return null;
	}

}
