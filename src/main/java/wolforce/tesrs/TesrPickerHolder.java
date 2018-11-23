package wolforce.tesrs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import wolforce.Util;
import wolforce.blocks.BlockSeparator;
import wolforce.tile.TilePickerHolder;
import wolforce.tile.TileSeparator;
import static net.minecraft.util.EnumFacing.*;

import net.minecraft.block.Block;

public class TesrPickerHolder extends TileEntitySpecialRenderer<TilePickerHolder> {

	@Override
	public void render(TilePickerHolder te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		IItemHandler itemh = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

//		double debug1 = .01 * Util.getNrForDebugFromHand(te.getWorld(), x, y, z);
//		double debug2 = 45.0 / 2 * Util.getNrForDebugFromHand2(te.getWorld(), x, y, z);

		if (Util.canRenderTESR(te)) {
			for (int i = 0; i < te.nSlots; i++) {

				ItemStack stack = itemh.getStackInSlot(i);
				if (!Util.isValid(stack))
					continue;

				double a = Math.PI * 2 / te.nSlots;
				double dx = Math.cos(i * a) * (.38);
				double dz = Math.sin(i * a) * (.38);
				Util.renderItem(0, 0, te.getWorld(), stack, x + dx, y + .87, z + dz, 0, -Math.toDegrees(a) * i - 90, -45);
			}
		}
	}
}
