package wolforce.client;

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
import wolforce.Main;
import wolforce.Util;
import wolforce.UtilClient;
import wolforce.blocks.BlockSeparator;
import wolforce.blocks.tile.TileCharger;
import wolforce.blocks.tile.TileTray;

public class TesrTray extends TileEntitySpecialRenderer<TileTray> {

	@Override
	public void render(TileTray te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		UtilClient.simpleRenderItem(te.getWorld(), new ItemStack(Main.antigravity_block), te.getPos().getX(), te.getPos().getY(),
				te.getPos().getZ(), true);
		for (int i = 0; i < 9; i++) {

			int dx2d = i % 3;
			int dy2d = i / 3;

			double dx = dx2d * .333;
			double dy = 0;
			double dz = dy2d * .333;

			ItemStack stack = te.inventory.getStackInSlot(i);
			if (Util.isValid(stack) && UtilClient.canRenderTESR(te)) {
				UtilClient.renderItem(0, 0, te.getWorld(), stack, x + dx, y + dy, z + dz);
			}
		}

		ItemStack stack = te.inventory.getStackInSlot(0);

		if (Util.isValid(stack) && UtilClient.canRenderTESR(te)) {
			EnumFacing facing = te.getWorld().getBlockState(te.getPos()).getValue(BlockSeparator.FACING);
			double d = .25;
			double dx = facing == EnumFacing.WEST ? d : (facing == EnumFacing.EAST ? -d : 0);
			double dz = facing == EnumFacing.NORTH ? d : (facing == EnumFacing.SOUTH ? -d : 0);
			double ryy = facing == NORTH ? 180 : facing == SOUTH ? 0 : facing == WEST ? 90 : 270;
			double sy = Block.getBlockFromItem(stack.getItem()).equals(Blocks.AIR) ? 1 : .125;
			UtilClient.renderItem(0, 0, te.getWorld(), stack, x + dx, y + .125, z + dz, 90, 0, ryy, 1, 1, sy);
		}
	}
}
