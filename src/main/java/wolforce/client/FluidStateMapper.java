package wolforce.client;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import wolforce.Hwell;

public class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {
	public final ModelResourceLocation location;

	public FluidStateMapper(Fluid fluid) {
		this.location = new ModelResourceLocation(Hwell.MODID + ":fluid_block", fluid.getName());
	}

	@Nonnull
	@Override
	protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
		return location;
	}

	@Nonnull
	@Override
	public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
		return location;
	}

}
