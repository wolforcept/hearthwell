package wolforce.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import wolforce.Main;

public class BlockFluid extends BlockFluidClassic {

	String name;
	public final Fluid fluid;

	public BlockFluid(Fluid fluid, Material material) {
		super(fluid, material);
		String fluidBlockName = "fluid_" + fluid.getName();
		this.name = fluidBlockName;
		setUnlocalizedName(Main.MODID + "." + fluidBlockName);
		setRegistryName(new ResourceLocation(Main.MODID, fluidBlockName));
		this.fluid = fluid;
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock().getMaterial(world.getBlockState(pos)).isLiquid())
			return false;
		return super.canDisplace(world, pos);
	}

	@Override
	public boolean displaceIfPossible(World world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock().getMaterial(world.getBlockState(pos)).isLiquid())
			return false;
		return super.displaceIfPossible(world, pos);
	}

	public String getName() {
		return name;
	}
}