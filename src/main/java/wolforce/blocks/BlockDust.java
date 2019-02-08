package wolforce.blocks;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;

public class BlockDust extends BlockFalling {

	public BlockDust(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.GROUND);
		setHardness(.5f);
		setHarvestLevel("shovel", -1);
	}
}
