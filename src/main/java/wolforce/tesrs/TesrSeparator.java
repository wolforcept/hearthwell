package wolforce.tesrs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import wolforce.Util;
import wolforce.tile.TileSeparator;

public class TesrSeparator extends TileEntitySpecialRenderer<TileSeparator> {

	@Override
	public void render(TileSeparator te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		IItemHandler itemh = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		ItemStack stack = itemh.getStackInSlot(0);

		if (stack != null)
			Util.renderItem(te.getWorld(), stack, x, y, z);
	}
}
