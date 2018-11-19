package wolforce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSand;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockDust extends BlockFalling {

	public BlockDust(String name) {
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.GROUND);
		setHardness(.5f);
		setHarvestLevel("shovel", -1);
	}

}
