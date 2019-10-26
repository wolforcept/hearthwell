package wolforce.hwell.blocks;

import net.minecraft.block.material.Material;
import wolforce.hwell.base.MySlab;

public class BlockSlabLamp extends MySlab {

	public BlockSlabLamp() {
		super(Material.ROCK);
		setHardness(1);
		setResistance(1);
		setLightLevel(0.9375F);
		setHarvestLevel("pickaxe", -1);
	}

}
