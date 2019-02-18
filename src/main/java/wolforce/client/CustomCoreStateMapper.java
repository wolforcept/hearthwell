package wolforce.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;

public class CustomCoreStateMapper extends StateMapperBase {

	private ResourceLocation res;

	public CustomCoreStateMapper(ResourceLocation res) {
		this.res = res;
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return new ModelResourceLocation(res, getPropertyString(state.getProperties()));
	}

}