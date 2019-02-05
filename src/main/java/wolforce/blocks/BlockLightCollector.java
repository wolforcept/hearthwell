package wolforce.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.HWellConfig;
import wolforce.Main;
import wolforce.MyBlock;
import wolforce.Util;
import wolforce.blocks.BlockCore.CoreType;
import wolforce.blocks.tile.TileCore;
import wolforce.items.tools.ItemDustPicker;

public class BlockLightCollector extends MyBlock {

	public static final PropertyInteger CHARGE = PropertyInteger.create("charge", 0, 3);

	public BlockLightCollector(String name) {
		super(name, Material.CLAY);
		setHardness(.5f);
		setResistance(.5f);
		setDefaultState(getDefaultState().withProperty(CHARGE, 0));
		setTickRandomly(true);
	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
		if (/**/(world.isDaytime() || !HWellConfig.isLightCollectorRequiredToBeDay) && //
				(world.canBlockSeeSky(pos.up()) || !HWellConfig.isLightCollectorRequiredToSeeSky)) {
			int curr = state.getValue(CHARGE);
			if (curr < 3)
				world.setBlockState(pos, getDefaultState().withProperty(CHARGE, curr + 1));
		}
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		super.getDrops(drops, world, pos, state, fortune);
		switch (state.getValue(CHARGE)) {
		case 1:
			drops.add(new ItemStack(Items.GLOWSTONE_DUST));
			break;
		case 2:
			drops.add(new ItemStack(Items.GLOWSTONE_DUST, 2));
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
}
