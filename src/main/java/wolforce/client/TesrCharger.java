package wolforce.client;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import wolforce.UtilClient;
import wolforce.blocks.tile.TileCharger;

public class TesrCharger extends TileEntitySpecialRenderer<TileCharger> {

	@Override
	public void render(TileCharger te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		IItemHandler itemh = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
		ItemStack stack = itemh.getStackInSlot(0);

		double i = UtilClient.getNrForDebugFromHand(te.getWorld(), x, y, z);
		// double j = -.0625 * Util.getNrForDebugFromHand2(te.getWorld(), x, y, z);

		if (stack != null && UtilClient.canRenderTESR(te)) {
			UtilClient.renderItem(i, 0, te.getWorld(), stack, x, y - .5, z);
		}
	}
}
