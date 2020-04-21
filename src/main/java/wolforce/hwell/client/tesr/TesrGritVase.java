package wolforce.hwell.client.tesr;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import wolforce.hwell.Main;
import wolforce.hwell.blocks.BlockGritVase;
import wolforce.hwell.blocks.tile.TileGritVase;

public class TesrGritVase extends TileEntitySpecialRenderer<TileGritVase> {

	private static final float BRANCH_ANGLE = 60;
	private static float SIZE = .5f;
	float temp = 0, temp2 = 0;

	@Override
	public void render(TileGritVase te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		if (te == null || te.getWorld().getBlockState(te.getPos()) == null || //
				!te.getWorld().getBlockState(te.getPos()).getPropertyKeys().contains(BlockGritVase.SIZE))
			return;

		int size = te.getWorld().getBlockState(te.getPos()).getValue(BlockGritVase.SIZE);
		if (size == 0)
			return;

		RenderItem render = Minecraft.getMinecraft().getRenderItem();
		ItemStack stack = new ItemStack(Main.branch);

		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();

		GlStateManager.translate(x + .5f, y + 1, z + .5f);
		GlStateManager.scale(SIZE, SIZE, SIZE);
		GlStateManager.scale(1, .66, 1);

		BlockPos pos = te.getPos();
		Random rand = new Random(pos.getX() + pos.getY() + pos.getZ());
		float scale = .5f + size / 10f;

		GlStateManager.scale(scale, 1, scale);
		renderBranch(render, stack, 2 + (int) (size * 1.5), rand, 0, scale, true);

		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
	}

	private void renderBranch(RenderItem render, ItemStack stack, int size, Random _rand, float curve, float scale, boolean isTrunk) {

		render.renderItem(stack, TransformType.NONE);

		if (size == 0)
			return;

		GlStateManager.pushMatrix();

		GlStateManager.scale(scale, 1, scale);
		rotateRandom(_rand, curve);
		float newCurve = curve;
		if (!isTrunk)
			newCurve *= 1.1;
		else
			newCurve += 1;
		renderBranch(render, stack, size - 1, _rand, newCurve, scale - .05f, isTrunk);

		Random rand = new Random(_rand.nextLong());
		if (size > 1 && (rand.nextInt(size) > 1 || (size - 1) % 2 == 0)) {

			GlStateManager.pushMatrix();
			GlStateManager.scale(scale - .1f, 1, scale - .1f);

			rotateRandom(_rand, BRANCH_ANGLE);

			renderBranch(render, stack, Math.min(size - 1, 3), rand, BRANCH_ANGLE, scale * .8f, false);
			GlStateManager.popMatrix();
		}

		GlStateManager.popMatrix();
	}

	private void rotateRandom(Random rand, float a) {
		GlStateManager.translate(0, .5, 0);
		GlStateManager.rotate(rand.nextInt(180), 0, 1, 0);
		if (rand.nextBoolean())
			GlStateManager.rotate(a, 0, 0, 1);
		else
			GlStateManager.rotate(a, 1, 0, 0);
		GlStateManager.translate(0, .5, 0);

	}

}
