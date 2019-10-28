package wolforce.hwell;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import wolforce.hwell.base.CustomNamedBlock;
import wolforce.hwell.base.MyBlock;
import wolforce.hwell.base.MySlab;
import wolforce.hwell.base.MyStairs;

public class HwellUtil {

	public static LinkedList<Block> makeVariants(MyBlock... blocks) {
		LinkedList<Block> variants = new LinkedList<>();
		for (MyBlock block : blocks) {

			MySlab slab = new MySlab(block);
			variants.add(slab);

			MyStairs stairs = new MyStairs(block);
			variants.add(stairs);

			if (block != Main.myst_planks) {

				MyBlock brick = new MyBrick(block.getRegistryName().getResourcePath() + "_bricks",
						block.getDefaultState().getMaterial());
				brick.setHardness(block.hardness);
				brick.setResistance(block.resistance);
				variants.add(brick);

				MySlab brickslab = new MySlab(brick);
				brickslab.setHardness(block.hardness);
				brickslab.setResistance(block.resistance);
				variants.add(brickslab);

				MyStairs brickstairs = new MyStairs(brick);
				brickstairs.setHardness(block.hardness);
				brickstairs.setResistance(block.resistance);
				variants.add(brickstairs);
			}
		}
		return variants;
	}

	private static class MyBrick extends MyBlock implements CustomNamedBlock {

		private String name;

		public MyBrick(String name, Material mat) {
			super(mat);
			this.name = name;

		}

		@Override
		public String getName() {
			return name;
		}
	}
}
