package wolforce.client.tesr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import wolforce.Main;
import wolforce.UtilClient;
import wolforce.blocks.tile.TileMutator;

public class TesrMutator extends TileEntitySpecialRenderer<TileMutator> {

	@Override
	public void render(TileMutator te, double x, double y, double z, float partialTicks, int destroyStage,
			float alpha) {

		GlStateManager.pushMatrix();
		// RenderItem render = Minecraft.getMinecraft().getRenderItem();
		ItemStack stack = te.inventory.getStackInSlot(0);
		ItemStack muta = new ItemStack(Main.mutation_paste_block);

		float rot = Minecraft.getMinecraft().world.getWorldTime() / 12f * (float) Math.PI;

		// float debug1 = UtilClient.getNrForDebugFromHand() / 10f;
		// float debug2 = UtilClient.getNrForDebugFromHand2() / 10f;

		// double[][] poss = { { 0, .5, 0 } };
		// UtilClient.renderItem(0, 0, te.getWorld(), stack, //
		// x, y + .5, z, //
		// 0, rot, 0, //
		// 1, 1, 1);

		if (te.getWorld().getBlockState(te.getPos()).getBlock() == Main.mutator) {
			// Random rand = new Random(te.getPos().toLong());
			// boolean xdir =
			// te.getWorld().getBlockState(te.getPos()).getValue(BlockMutator.FACING).getAxis()
			// == Axis.X;
			// for (int i = 1; i < stack.getCount(); i++) {
			// double dx = !xdir ? (-.5 + rand.nextDouble()) : 0;
			// double dy = -.05 + rand.nextDouble() * .1;
			// double dz = xdir ? (-.5 + rand.nextDouble()) : 0;
			UtilClient.renderItemMult(stack.getCount(), 0, 0, te.getWorld(), stack, //
					x, y + .5, z, //
					0, rot, 0, //
					1, 1, 1);
			// }
		}

		if (te.getCharge() > 0) {
			float minH = .1f, maxH = .3f;
			float mutaH = minH + (float) ((maxH - minH) * te.getCharge() / 1000f);

			UtilClient.renderItem(0, 0, te.getWorld(), muta, //
					x, y + mutaH, z, //
					0, 0, 0, //
					3.4, .01, 3.4);
		}
		GlStateManager.popMatrix();

	}

}
