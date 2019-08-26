package wolforce.client.tesr;

import static net.minecraft.util.EnumFacing.NORTH;
import static net.minecraft.util.EnumFacing.SOUTH;
import static net.minecraft.util.EnumFacing.WEST;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import wolforce.Util;
import wolforce.UtilClient;
import wolforce.blocks.BlockSeparator;
import wolforce.blocks.tile.TileSeparator;

public class TesrSeparator extends TileEntitySpecialRenderer<TileSeparator> {

	@Override
	public void render(TileSeparator te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		IItemHandler itemh = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		ItemStack stack = itemh.getStackInSlot(0);

		double i = UtilClient.getNrForDebugFromHand(te.getWorld(), x, y, z);
		// double j = -.0625 * Util.getNrForDebugFromHand2(te.getWorld(), x, y, z);

		if (Util.isValid(stack) && UtilClient.canRenderTESR(te)) {
			EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockSeparator.FACING);
			double d = .25;
			double dx = facing == EnumFacing.WEST ? d : (facing == EnumFacing.EAST ? -d : 0);
			double dz = facing == EnumFacing.NORTH ? d : (facing == EnumFacing.SOUTH ? -d : 0);
			double ryy = facing == NORTH ? 180 : facing == SOUTH ? 0 : facing == WEST ? 90 : 270;
			double sy = Block.getBlockFromItem(stack.getItem()).equals(Blocks.AIR) ? 1 : .125;
			UtilClient.renderItem(i, 0, te.getWorld(), stack, x + dx, y + .125, z + dz, 90, 0, ryy, 1, 1, sy);
		}
	}
}
