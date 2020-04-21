package wolforce.hwell.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import wolforce.hwell.base.MyBlock;

public class BlockGroundShovel extends MyBlock {

	public BlockGroundShovel(String name) {
		super(Material.GRASS);
		setSoundType(SoundType.GROUND);
		setHardness(.6f);
		setHarvestLevel("shovel", -1);
	}

}
