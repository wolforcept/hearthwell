package wolforce.blocks;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.HWellConfig;
import wolforce.MyBlock;
import wolforce.Util;
import wolforce.blocks.base.BlockEnergyConsumer;
import wolforce.recipes.RecipeFreezer;

public class BlockFreezer extends MyBlock implements BlockEnergyConsumer {

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
		if (!world.isDaytime() || !HWellConfig.isFreezerRequiredToBeNight) {

			List<BlockPos> nearBlocks = getNearBlocks(world, pos);
			if (nearBlocks.size() == 0)
				return;

			// ENERGY CONSUMPTION
			if (!BlockEnergyConsumer.tryConsume(world, pos, getEnergyConsumption())) {
				return;
			}

			int randomIndex = (int) (Math.random() * nearBlocks.size());
			world.setBlockState(//
					nearBlocks.get(randomIndex), //
					RecipeFreezer.getResult(//
							world.getBlockState(nearBlocks.get(randomIndex))//
					).getDefaultState()//
			);

		}
	}

	private List<BlockPos> getNearBlocks(World world, BlockPos pos) {
		LinkedList<BlockPos> list = new LinkedList<>();
		final int n = HWellConfig.freezerRange;
		for (int x = -n; x <= n; x++) {
			for (int y = -n; y <= n; y++) {
				for (int z = -n; z <= n; z++) {
					BlockPos thispos = pos.add(x, y, z);
					IBlockState state = world.getBlockState(thispos);
					// if (!world.isRemote && RecipeFreezer.hasResult(state))//
					// !state.equals(Blocks.AIR.getDefaultState()))
					// System.out.println(state + " > " + RecipeFreezer.hasResult(state));
					if (RecipeFreezer.hasResult(state))
						list.add(thispos);
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

	@Override
	public String[] getDescription() {
		return new String[] { "Will freeze nearby water to ice or snow,", "and nearby lava to obsidian.",
				"Consumes " + getEnergyConsumption() + " Energy per Operation." };
	}

	@Override
	public int getEnergyConsumption() {
		return HWellConfig.energyConsumptionFreezer;
	}

}
