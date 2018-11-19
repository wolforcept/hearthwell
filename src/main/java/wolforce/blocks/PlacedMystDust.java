package wolforce.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockSand;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.MyBlock;

public class PlacedMystDust extends Block {

	protected static final double F = 1.0 / 16.0;
	protected static final AxisAlignedBB aabb = new AxisAlignedBB(5 * F, 0, 5 * F, 11 * F, 2 * F, 11 * F);

	public PlacedMystDust(String name) {
		super(Material.SAND);
		setRegistryName(name);
		setUnlocalizedName(name);
		setHardness(3);
		setResistance(1);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		Item picker = harvesters.get().getHeldItemMainhand().getItem();
		int nr = 3 + fortune;
		boolean done = true;
		if (picker == Main.myst_dust_picker_fe)
			drops.add(new ItemStack(Main.shard_fe, nr));
		else if (picker == Main.myst_dust_picker_ca)
			drops.add(new ItemStack(Main.shard_ca, nr));
		else if (picker == Main.myst_dust_picker_c)
			drops.add(new ItemStack(Main.shard_c, nr));
		else if (picker == Main.myst_dust_picker_o)
			drops.add(new ItemStack(Main.shard_o, nr));
		else if (picker == Main.myst_dust_picker_au)
			drops.add(new ItemStack(Main.shard_au, nr));
		else if (picker == Main.myst_dust_picker_h)
			drops.add(new ItemStack(Main.shard_h, nr));
		else
			done = false;
		if (done)
			harvesters.get().getHeldItemMainhand().damageItem(1, harvesters.get());
	}

	//

	//

	//

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean canDropFromExplosion(Explosion explosionIn) {
		return false;
	}

	//

	//

	//

	// VISUAL

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return aabb;
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for
	 * render
	 */
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public boolean hasCustomBreakingProgress(IBlockState state) {
		return true;
	}

	
}
