package wolforce.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSand;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.blocks.base.BlockWithDescription;

public class BlockStoneDust extends BlockDust implements BlockWithDescription {

	public BlockStoneDust(String name) {
		super(name);
		setTickRandomly(true);
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		boolean nearWater = false;
		for (EnumFacing dir : EnumFacing.VALUES) {
			if (worldIn.getBlockState(pos.offset(dir)).getMaterial().equals(Material.WATER))
				nearWater = true;
		}
		if (nearWater && worldIn.rand.nextDouble() < .1)
			worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());

	}

	@Override
	public String[] getDescription() {
		return new String[] { "If placed near water, will eventually become dirt." };
	}

}
