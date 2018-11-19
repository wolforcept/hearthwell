package wolforce.fluids;

import java.awt.Color;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class FluidLiquidSouls extends Fluid {

	public FluidLiquidSouls(String fluidName, ResourceLocation still, ResourceLocation flowing, Color color) {
		super(fluidName, still, flowing, color);
	}

}