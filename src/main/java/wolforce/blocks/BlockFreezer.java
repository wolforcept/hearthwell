package wolforce.blocks;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
import wolforce.Main;
import wolforce.MyBlock;
import wolforce.Util;
import wolforce.blocks.BlockCore.CoreType;
import wolforce.items.tools.ItemDustPicker;
import wolforce.tile.TileCore;

public class BlockFreezer extends MyBlock {

	private final static double F = 1.0 / 16.0;
	protected static final AxisAlignedBB aabb = new AxisAlignedBB(6 * F, 0, 6 * F, 10 * F, 9 * F, 10 * F);

	public BlockFreezer(String name) {
		super(name, Material.CLAY);
		setHardness(.1f);
		setResistance(.1f);
		setTickRandomly(true);
	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
		if (world.isRemote)
			return;
		if (!world.isDaytime()) {
			List<BlockPos> nearWater = getNearWater(world, pos);
			if (nearWater.size() == 0)
				return;
			Collections.shuffle(nearWater);
			world.setBlockState(nearWater.get(0), Math.random() < .5 ? Blocks.SNOW.getDefaultState() : Blocks.ICE.getDefaultState());
		}
	}

	private List<BlockPos> getNearWater(World world, BlockPos pos) {
		LinkedList<BlockPos> list = new LinkedList<>();
		for (int x = -5; x < 5; x++) {
			for (int y = -5; y < 5; y++) {
				for (int z = -5; z < 5; z++) {
					Block block = world.getBlockState(pos.add(x, y, z)).getBlock();
					if (block.equals(Blocks.FLOWING_WATER) || block.equals(Blocks.WATER))
						list.add(pos.add(x, y, z));
				}
			}
		}
		return list;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return aabb;
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return aabb;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

}
