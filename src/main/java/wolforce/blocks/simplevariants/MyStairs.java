package wolforce.blocks.simplevariants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class MyStairs extends BlockStairs {

	public MyStairs(Block fullblock) {
		super(fullblock.getDefaultState());
		setUnlocalizedName(fullblock.getRegistryName().getResourcePath() + "_stairs");
		setRegistryName(fullblock.getRegistryName().getResourcePath() + "_stairs");
		this.useNeighborBrightness = true;
		this.fullBlock = false;
		this.setLightOpacity(255);
		// this WILL break in the future
		this.setHardness(fullblock.getBlockHardness(fullblock.getDefaultState(), null, null));
		this.setResistance(fullblock.getExplosionResistance(null, null, null, null));
	}

}
