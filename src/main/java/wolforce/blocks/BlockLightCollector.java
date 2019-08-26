package wolforce.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.base.BlockWithDescription;
import wolforce.base.MyBlock;

public class BlockLightCollector extends MyBlock implements BlockWithDescription {

	public static final PropertyInteger CHARGE = PropertyInteger.create("charge", 0, 3);

	public BlockLightCollector(String name) {
		super(name, Material.CLAY);
		setHardness(.5f);
		setResistance(.5f);
		setDefaultState(getDefaultState().withProperty(CHARGE, 0));
		setTickRandomly(true);
	}

	@Override
	public int getLightValue(IBlockState state) {
		return state.getValue(CHARGE) * 5;
	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
		if (/**/(world.isDaytime() || !HwellConfig.other.lightCollectorIsRequiredToBeDay) && //
				(world.canBlockSeeSky(pos.up()) || !HwellConfig.other.lightCollectorIsRequiredToSeeSky)) {
			int curr = state.getValue(CHARGE);
			if (curr < 3)
				world.setBlockState(pos, getDefaultState().withProperty(CHARGE, curr + 1));
		}
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World worldIn, BlockPos pos) {
		return state.getValue(CHARGE) * 5;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		super.getDrops(drops, world, pos, state, fortune);
		switch (state.getValue(CHARGE)) {
		case 2:
			if (Math.random() < .5)
				drops.add(new ItemStack(Items.GLOWSTONE_DUST, 1));
			break;
		case 3:
			drops.add(new ItemStack(Main.locked_light));
			break;
		default:
		}
	}

	// BLOCK STATES

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CHARGE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(CHARGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(CHARGE);
	}

	@Override
	public String[] getDescription() {
		return new String[] {"Right click this block with an Imprisoned Light to instantly fill it."};
	}
}
