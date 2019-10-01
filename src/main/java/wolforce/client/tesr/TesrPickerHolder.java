package wolforce.client.tesr;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import wolforce.Util;
import wolforce.UtilClient;
import wolforce.blocks.tile.TilePickerHolder;

public class TesrPickerHolder extends TileEntitySpecialRenderer<TilePickerHolder> {

	@Override
	public void render(TilePickerHolder te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {
		// IItemHandler itemh =
		// te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
		// EnumFacing.NORTH);

		// double debug1 = .01 * Util.getNrForDebugFromHand(te.getWorld(), x, y, z);
		// double debug2 = 45.0 / 2 * Util.getNrForDebugFromHand2(te.getWorld(), x, y,
		// z);

		if (UtilClient.canRenderTESR(te)) {
			for (int i = 0; i < TilePickerHolder.nSlots; i++) {

				ItemStack stack = te.inventory.getStackInSlot(i);
				if (!Util.isValid(stack))
					continue;

				double a = Math.PI * 2 / TilePickerHolder.nSlots;
				double dx = Math.cos(i * a) * (.38);
				double dz = Math.sin(i * a) * (.38);
				UtilClient.renderItem(0, 0, te.getWorld(), stack, x + dx, y + .87, z + dz, 0,
						-Math.toDegrees(a) * i - 90, -45);
			}
		}
	}
}
