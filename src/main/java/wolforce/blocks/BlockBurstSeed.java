package wolforce.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.MyBlock;
import wolforce.UnjoinableEntityItem;
import wolforce.Util;

public class BlockBurstSeed extends MyBlock {

	private static final double F = 1 / 16f;
	private static final AxisAlignedBB colbox = new AxisAlignedBB(4 * F, 0, 4 * F, 12 * F, 8 * F, 12 * F);
	public ItemStack stack;
	private SoundType sound;

	public BlockBurstSeed(String name, Material mat, Block block, String tool, SoundType sound) {
		// this(name, mat, new ItemStack(block), tool, sound);
		// }
		// public BlockBurstSeed(String name, Material mat, ItemStack item, String tool,
		// SoundType sound) {
		super(name, mat, true);
		this.stack = new ItemStack(block);
		this.sound = sound;
		setResistance(2f);
		setHardness(2f);
		setHarvestLevel(tool, -1);
		setSoundType(sound);
	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
		if (world.isRemote)
			return;

		world.playSound(null, pos, SoundEvents.ENTITY_FIREWORK_BLAST, SoundCategory.BLOCKS, 1, 1);
		world.playSound(null, pos, sound.getBreakSound(), SoundCategory.BLOCKS, 10, 1);
		// world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, pos.getX(),
		// pos.getY(), pos.getZ(), 1.0D, 0.0D, 0.0D);
		// for (int i = 0; i < 35; i++)
		// world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, //
		// pos.getX(), pos.getY(), pos.getZ(), //
		// Math.random() - .5, Math.random() - .5, Math.random() - .5);

		world.setBlockToAir(pos);
		int n = (int) (Math.random() * 32) + 32;
		if (stack.getItem().equals(new ItemStack(Main.crystal_block).getItem()))
			n = (int) (Math.random() * 10) + 10;
		for (int i = 0; i < n; i++) {
			Entity newEntity = new UnjoinableEntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(stack.getItem()), 40);
			newEntity.addVelocity(Math.random() * 2 - 1, Math.random(), Math.random() * 2 - 1);
			world.spawnEntity(newEntity);
		}
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		if (stack.getItem().equals(new ItemStack(Main.crystal_block).getItem()))
			return layer == BlockRenderLayer.TRANSLUCENT;
		return super.canRenderInLayer(state, layer);
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return colbox;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return colbox;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

}
