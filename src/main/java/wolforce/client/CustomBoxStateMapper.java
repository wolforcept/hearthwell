package wolforce.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;

public class CustomBoxStateMapper extends StateMapperBase {

	private ResourceLocation res;
	private boolean hasAxis;

	public CustomBoxStateMapper(ResourceLocation res, boolean hasAxis) {
		this.res = res;
		this.hasAxis = hasAxis;
	}

	@Override
	protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
		if (hasAxis)
			return new ModelResourceLocation(res, getPropertyString(state.getProperties()));
		return new ModelResourceLocation(res, "normal");
	}

}