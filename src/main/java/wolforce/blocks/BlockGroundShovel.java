package wolforce.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.MyBlock;

public class BlockGroundShovel extends MyBlock {

	public BlockGroundShovel(String name) {
		super(name, Material.GRASS);
		setSoundType(SoundType.GROUND);
		setHardness(.6f);
		setHarvestLevel("shovel", -1);
	}

}
