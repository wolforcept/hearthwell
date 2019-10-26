package wolforce.hwell.client.tesr;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import wolforce.hwell.blocks.tile.TileCharger;
import wolforce.mechanics.UtilClient;

public class TesrCharger extends TileEntitySpecialRenderer<TileCharger> {

	@Override
	public void render(TileCharger te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		// IItemHandler itemh =
		// te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
		// EnumFacing.UP);
		// itemh.

		ItemStack stack = te.inventory.getStackInSlot(0);

		if (stack != null && UtilClient.canRenderTESR(te)) {
			UtilClient.renderItem(0, 0, te.getWorld(), stack, x, y - .5, z, 0, 0, 0, 1.5, 1.5, 1.5);
		}
	}

	// @Override
	// public void render(TileCharger te, double x, double y, double z, float
	// partialTicks, int destroyStage, float alpha) {
	// IItemHandler itemh =
	// te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
	// EnumFacing.UP);
	// ItemStack stack = itemh.getStackInSlot(0);
	//
	// double i = UtilClient.getNrForDebugFromHand(te.getWorld(), x, y, z);
	// // double j = -.0625 * Util.getNrForDebugFromHand2(te.getWorld(), x, y, z);
	//
	// if (stack != null && UtilClient.canRenderTESR(te)) {
	// UtilClient.renderItem(i, 0, te.getWorld(), stack, x, y - .5, z);
	// }
	// }
}
