package wolforce.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import wolforce.base.MyBlock;

public class BlockGroundShovel extends MyBlock {

	public BlockGroundShovel(String name) {
		super(name, Material.GRASS);
		setSoundType(SoundType.GROUND);
		setHardness(.6f);
		setHarvestLevel("shovel", -1);
	}

}
