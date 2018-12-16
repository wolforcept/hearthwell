package wolforce.fluids;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import wolforce.Main;
import wolforce.Util;

public class BlockLiquidSouls extends BlockFluid {

	public BlockLiquidSouls(Fluid fluid, Material material) {
		super(fluid, material);

		setQuantaPerBlock(3);
	}

	// @Override
	// public void neighborChanged(IBlockState state, World world, BlockPos pos,
	// Block neighborBlock, BlockPos neighbourPos) {
	// super.neighborChanged(state, world, pos, neighborBlock, neighbourPos);
	// if (neighborBlock.equals(Blocks.FLOWING_WATER) ||
	// neighborBlock.equals(Blocks.WATER))
	// world.setBlockState(neighbourPos, Main.moonstone_block.getDefaultState());
	// if (neighborBlock.equals(Blocks.FLOWING_LAVA) ||
	// neighborBlock.equals(Blocks.LAVA))
	// world.setBlockState(neighbourPos, Main.onyx_block.getDefaultState());
	// }

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock().equals(Blocks.FLOWING_WATER)) {
			return true;
		}
		return super.canDisplace(world, pos);
	}

	@Override
	public Vec3d modifyAcceleration(World world, BlockPos pos, Entity entity, Vec3d vec) {
		if (densityDir > 0)
			return vec;
		Vec3d vec_flow = this.getFlowVector(world, pos);
		return vec.addVector(vec_flow.x * (quantaPerBlock * 4), vec_flow.y * (quantaPerBlock * 4), vec_flow.z * (quantaPerBlock * 4));
		// TODO update clients
	}

}
