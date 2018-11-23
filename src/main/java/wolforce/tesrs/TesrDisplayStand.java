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
import wolforce.tile.TileSeparator;
import static net.minecraft.util.EnumFacing.*;

import net.minecraft.block.Block;

public class TesrDisplayStand extends TileEntitySpecialRenderer<TileSeparator> { // TODO

	@Override
	public void render(TileSeparator te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		IItemHandler itemh = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		ItemStack stack = itemh.getStackInSlot(0);
		if (stack != null && Util.canRenderTESR(te))
			Util.simpleRenderItem(te.getWorld(), stack, x, y, z, true);
	}
}
