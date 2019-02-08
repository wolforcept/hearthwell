package wolforce.blocks;

import net.minecraft.block.material.Material;
import wolforce.blocks.simplevariants.MySlab;

public class BlockSlabLamp extends MySlab {

	public BlockSlabLamp(String name) {
		super(name, Material.ROCK);
		setHardness(1);
		setResistance(1);
		setLightLevel(0.9375F);
		setHarvestLevel("pickaxe", -1);
	}

}
