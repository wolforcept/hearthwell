package wolforce.blocks;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import wolforce.Util;

public class BlockDust extends BlockFalling {

	public BlockDust(String name) {
		Util.setReg(this, name);
		setSoundType(SoundType.GROUND);
		setHardness(.5f);
		setHarvestLevel("shovel", -1);
	}
}
