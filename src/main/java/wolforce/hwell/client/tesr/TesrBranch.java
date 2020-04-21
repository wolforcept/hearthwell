package wolforce.hwell.client.tesr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import wolforce.hwell.Main;
import wolforce.hwell.blocks.tile.TileBranch;
import wolforce.hwell.items.ItemBranch;

public class TesrBranch extends TileEntitySpecialRenderer<TileBranch> {

	@Override
	public void render(TileBranch te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		RenderItem render = Minecraft.getMinecraft().getRenderItem();
		ItemStack stack = new ItemStack(Main.branch);
		stack.setTagCompound(new NBTTagCompound());
		ItemBranch.setNBT(stack.getTagCompound(), te.time);

		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();

		GlStateManager.translate(x + .5f, y + .5f, z + .5f);

		render.renderItem(stack, TransformType.NONE);

		GlStateManager.popAttrib();
		GlStateManager.popMatrix();

	}

}
