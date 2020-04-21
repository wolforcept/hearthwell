package wolforce.hwell.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class MyStairs extends BlockStairs {

	@SuppressWarnings("deprecation")
	public MyStairs(Block fullblock) {
		super(fullblock.getDefaultState());

		String name = fullblock.getRegistryName().getResourcePath() + "_stairs";
		this.useNeighborBrightness = true;
		this.fullBlock = false;
		this.setLightOpacity(255);
		// this WILL break in the future
		this.setHardness(fullblock.getBlockHardness(fullblock.getDefaultState(), null, null));
		this.setResistance(fullblock.getExplosionResistance(null, null, null, null));
	}

}
