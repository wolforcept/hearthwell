package wolforce.client.tesr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import wolforce.blocks.tile.TileGraftingTray;

public class TesrGraftingTray extends TileEntitySpecialRenderer<TileGraftingTray> {

	@Override
	public void render(TileGraftingTray te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		ItemStack core = te.inventory.getStackInSlot(0);

		RenderItem render = Minecraft.getMinecraft().getRenderItem();

		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();

		GlStateManager.translate(x + .5f, y + .25, z + .5f);
		GlStateManager.scale(.5, .5, .5);

		render.renderItem(core, TransformType.NONE);

		GlStateManager.popAttrib();
		GlStateManager.popMatrix();

	}

}
