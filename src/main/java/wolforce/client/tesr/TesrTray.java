package wolforce.client.tesr;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import wolforce.Util;
import wolforce.UtilClient;
import wolforce.blocks.BlockTray;
import wolforce.blocks.tile.TileTray;

public class TesrTray extends TileEntitySpecialRenderer<TileTray> {

	@Override
	public void render(TileTray te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		for (int i = 0; i < 9; i++) {
			ItemStack stack = te.inventory.getStackInSlot(i);

			if (Util.isValid(stack) && UtilClient.canRenderTESR(te)) {

				boolean isBlock = Block.getBlockFromItem(stack.getItem()) != Blocks.AIR;

				double margin = (isBlock ? 2.1 : 1.0) / 16.0;

				double scaleX = isBlock ? 1 : .5;
				double scaleY = isBlock ? 1 : .5;
				double scaleZ = isBlock ? 1 : .5;

				int dx2d = i % 3;
				int dy2d = i / 3;

				double dx = 0;
				double dy = 0;
				double dz = 0;

				double rotation1 = 0;
				double rotation2 = 0;
				double rotation3 = 0;

				EnumFacing side = te.getWorld().getBlockState(te.getPos()).getValue(BlockTray.FACING);

				if (side == EnumFacing.UP || side == EnumFacing.DOWN) {
					dx = (dx2d - 1) * .333;
					dz = (dy2d - 1) * .333;
					rotation1 = 90;
					// if (isBlock)
					// scaleZ = .1;
					if (side == EnumFacing.UP)
						dy = margin;
					if (side == EnumFacing.DOWN)
						dy = 1 - margin;
				}

				// double dddd = ((double) UtilClient.getNrForDebugFromHand(te.getWorld(),
				// te.getPos())) / 100.0;
				if (side == EnumFacing.NORTH || side == EnumFacing.SOUTH) {
					dx = (dx2d - 1) * .333;
					dy = .17 + (dy2d) * .333;
					// if (isBlock)
					// scaleZ = .1;
				}
				if (side == EnumFacing.NORTH)
					dz = +.5 - margin;
				if (side == EnumFacing.SOUTH)
					dz = -.5 + margin;

				if (side == EnumFacing.WEST || side == EnumFacing.EAST) {
					dz = (dx2d - 1) * .333;
					dy = .17 + dy2d * .333;
					// if (isBlock)
					// scaleX = .1;
					// else
					// if (!isBlock)
					rotation2 = 90;
				}
				if (side == EnumFacing.WEST)
					dx = .5 - margin;
				if (side == EnumFacing.EAST)
					dx = -.5 + margin;

				UtilClient.renderItem(0, 0, te.getWorld(), stack, //
						x + dx, y + dy, z + dz, //
						rotation1, rotation2, rotation3, //
						scaleX, scaleY, scaleZ);
			}
		}
	}
}
