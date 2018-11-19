package wolforce.blocks.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolforce.Main;
import wolforce.blocks.BlockDismantler;
import wolforce.tile.TileDismantler;

@SideOnly(Side.CLIENT)
public class RenderDismantler/* extends TileEntitySpecialRenderer<TileDismantler> */ {

	// @Override
	// public void render(TileDismantler tile, double x, double y, double z, float
	// par5, int par6, float f) {
	//
	// ItemStack stack = tile.getItemStack();
	// if (BlockDismantler.isValid(stack)) {
	// System.out.println("RenderDismantler.render()");
	// GlStateManager.pushMatrix();
	// GlStateManager.translate((float) x + 0.5F, (float) y + 1F, (float) z + 0.5F);
	//
	// double boop = Minecraft.getSystemTime() / 800D;
	// GlStateManager.translate(0D, Math.sin(boop % (2 * Math.PI)) * 0.065, 0D);
	// GlStateManager.rotate((float) (((boop * 40D) % 360)), 0, 1, 0);
	//
	// float scale = stack.getItem() instanceof ItemBlock ? 0.85F : 0.65F;
	// GlStateManager.scale(scale, scale, scale);
	// try {
	// renderItemInWorld(stack);
	// } catch (Exception e) {
	// Main.logger.error("render item fail " + stack.getItem().getRegistryName() +
	// "!", e);
	// }
	//
	// GlStateManager.popMatrix();
	// }
	// }
	//
	// @SideOnly(Side.CLIENT)
	// public static void renderItemInWorld(ItemStack stack) {
	// GlStateManager.pushMatrix();
	// GlStateManager.disableLighting();
	// GlStateManager.pushAttrib();
	// RenderHelper.enableStandardItemLighting();
	// Minecraft.getMinecraft().getRenderItem().renderItem(stack,
	// TransformType.FIXED);
	// RenderHelper.disableStandardItemLighting();
	// GlStateManager.popAttrib();
	// GlStateManager.enableLighting();
	// GlStateManager.popMatrix();
	// }
}