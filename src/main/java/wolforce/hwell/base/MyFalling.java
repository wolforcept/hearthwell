package wolforce.hwell.base;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;

public class MyFalling extends BlockFalling {

	public MyFalling() {
		setSoundType(SoundType.SAND);
		setHardness(.5f);
		setHarvestLevel("shovel", -1);
	}
}
