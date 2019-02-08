package wolforce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.Util;

public class BlockHeat extends Block {

	public BlockHeat(String name) {
		super(Material.CLAY);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(2);
		setResistance(.5f);
		setHarvestLevel("pickaxe", -1);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.getHeldItem(hand).getItem().equals(Items.FLINT_AND_STEEL)) {
			worldIn.createExplosion(playerIn, pos.getX(), pos.getY(), pos.getZ(), 1, true);
			playerIn.getHeldItem(hand).damageItem(1, playerIn);
			return true;
		}
		return false;
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		if (!worldIn.isRemote)
			Util.spawnItem(worldIn, pos, new ItemStack(Main.core_heat));
	}

	@Override
	public boolean canDropFromExplosion(Explosion explosionIn) {
		return false;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 0;
	}

}
