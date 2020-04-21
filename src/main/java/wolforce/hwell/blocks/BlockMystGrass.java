package wolforce.hwell.blocks;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.hwell.Main;
import wolforce.hwell.base.MyBlock;

public class BlockMystGrass extends MyBlock {

	public BlockMystGrass() {
		super(Material.GRASS, true);
		setSoundType(SoundType.PLANT);
		setHardness(.6f);
		setLightLevel(.1f);
		setHarvestLevel("shovel", -1);
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		if (Math.random() < .5) {

			if (!isNearOtherGrass(worldIn, pos.up()) || Math.random() < .05)
				if (worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR)
					worldIn.setBlockState(pos.up(), Main.myst_bush.getDefaultState());
				else if (worldIn.getBlockState(pos.up()).getBlock() == Main.myst_bush)
					worldIn.setBlockState(pos.add(0, 1, 0), Main.myst_bush_big.getDefaultState());
		} else if (Math.random() < .2) {
			BlockPos pos2 = pos.add(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
			if (worldIn.getBlockState(pos2).getBlock() == Blocks.GRASS)
				worldIn.setBlockState(pos2, getDefaultState());
		}
	}

	private boolean isNearOtherGrass(World worldIn, BlockPos pos) {
		for (EnumFacing f : EnumFacing.HORIZONTALS) {
			if (worldIn.getBlockState(pos.offset(f)).getBlock() instanceof BlockMystBush)
				return true;
		}
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(Blocks.DIRT);
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return true;
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(this);
	}

}
