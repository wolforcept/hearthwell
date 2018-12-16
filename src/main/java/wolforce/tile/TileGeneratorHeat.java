package wolforce.tile;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.BlockCore;
import wolforce.blocks.BlockGeneratorHeat;
import wolforce.recipes.Irio;
import wolforce.recipes.RecipeCoring;

public class TileGeneratorHeat extends TileEntity implements ITickable {

	@Override
	public void update() {

		if (world.isRemote || world.getBlockState(pos.down()).getMaterial() != Material.LAVA || Math.random() > .1)
			return;

		int next = world.getBlockState(pos).getValue(BlockGeneratorHeat.TEMP) + 1;
		if (next != 10)
			world.setBlockState(pos, Main.generator_heat.getDefaultState().withProperty(BlockGeneratorHeat.TEMP, next));
	}

}
