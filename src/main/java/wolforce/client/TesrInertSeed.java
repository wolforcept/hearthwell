package wolforce.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import wolforce.Main;
import wolforce.blocks.tile.TileInertSeed;

public class TesrInertSeed extends TileEntitySpecialRenderer<TileInertSeed> {

	@Override
	public void render(TileInertSeed te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		RenderItem render = Minecraft.getMinecraft().getRenderItem();
		ItemStack stack = new ItemStack(Main.inert_seed);

		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();

		GlStateManager.translate(x + .5f, y + te.growth / 2, z + .5f);
		GlStateManager.scale(te.growth, te.growth, te.growth);

		render.renderItem(stack, TransformType.NONE);

		GlStateManager.popAttrib();
		GlStateManager.popMatrix();

	}

}
