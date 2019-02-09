package wolforce.blocks;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.MyBlock;

public class BlockMystLeaves extends MyBlock /* implements IShearable */ {

	public BlockMystLeaves(String name) {
		super(name, Material.LEAVES);
		// setTickRandomly(true);
		setSoundType(SoundType.PLANT);
	}

	// @Override
	// public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random
	// rand) {
	// if (!hasLogNear(worldIn, pos))
	// worldIn.setBlockToAir(pos);
	// }

	// private boolean hasLogNear(World world, BlockPos pos) {
	// for (int x = -4; x <= 4; x++) {
	// for (int y = -4; y <= 4; y++) {
	// for (int z = -4; z <= 4; z++) {
	// if (world.getBlockState(pos.add(x, y, z)).getBlock() == Main.myst_log)
	// return true;
	// }
	// }
	// }
	// return false;
	// }

	/*
	 * @Override public boolean isShearable(ItemStack item, IBlockAccess world,
	 * BlockPos pos) { return true; }
	 */

	/*
	 * @Override public int quantityDropped(IBlockState state, int fortune, Random
	 * random) { return 0;// random.nextInt(10) < 1 ? 1 : 0; }
	 */

	// @Override
	// public Item getItemDropped(IBlockState state, Random rand, int fortune) {
	// return Main.getRandomCrystal(rand);
	// }

	/*
	 * @Override public List<ItemStack> onSheared(ItemStack item, IBlockAccess
	 * world, BlockPos pos, int fortune) { return Arrays.asList(new ItemStack[] {
	 * new ItemStack(Main.myst_leaves, 1) }); }
	 */

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}

	@Override
	public boolean isTopSolid(IBlockState state) {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

}
