package wolforce.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import wolforce.Util;
import wolforce.UtilClient;
import wolforce.blocks.tile.TilePickerHolder;
import wolforce.blocks.tile.TileStatue;

public class TesrStatue extends TileEntitySpecialRenderer<TileStatue> {

	Entity entity = null;

	@Override
	public void render(TileStatue te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		float debug1 = .01f * UtilClient.getNrForDebugFromHand(te.getWorld(), x, y, z);
		float debug2 = .01f * UtilClient.getNrForDebugFromHand2(te.getWorld(), x, y, z);
		if (UtilClient.canRenderTESR(te)) {
			BlockPos pos = te.getPos();
			if (entity == null || entity.isDead)
				entity = new EntityBat(te.getWorld());
			Minecraft.getMinecraft().getRenderManager().renderEntity(entity, //
					pos.getX() + .5f, pos.getY() + 1.5f, pos.getZ() + .5f, debug1, debug2, false);
		}
	}
	
}
