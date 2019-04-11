package wolforce.base;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import wolforce.Util;

public class MyFalling extends BlockFalling {

	public MyFalling(String name) {
		Util.setReg(this, name);
		setSoundType(SoundType.SAND);
		setHardness(.5f);
		setHarvestLevel("shovel", -1);
	}
}
