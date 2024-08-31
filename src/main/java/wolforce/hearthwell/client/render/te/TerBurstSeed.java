package wolforce.hearthwell.client.render.te;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import wolforce.hearthwell.blocks.tiles.TeBurstSeed;
import wolforce.hearthwell.util.Util;

import java.util.Random;

public class TerBurstSeed implements BlockEntityRenderer<TeBurstSeed> {

//	public TerBurstSeed(BlockEntityRenderDispatcher rendererDispatcherIn) {
//		super(rendererDispatcherIn);
//	}

	public TerBurstSeed(BlockEntityRendererProvider.Context context) {
		super();
	}

	@Override
	public void render(TeBurstSeed entityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn,
			int combinedOverlayIn) {

		Random random = new Random(0);
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

		ItemStack itemstack = entityIn.getItem();

		if (!Util.isValid(itemstack))
			return;

		BakedModel ibakedmodel = itemRenderer.getModel(itemstack, entityIn.getLevel(), null, combinedOverlayIn);
		float time = (float) (System.currentTimeMillis() % 15000 / 15000.0 * Math.PI * 2);
//		time = (float) Math.sin(time);

		double c = entityIn.getChaos();
		int _n = itemstack.getCount();
		int n = 4 + Math.min(24, _n);
		double d = 0.5 + (-0.008 * _n * _n + 1.524 * _n - 0.48) / 64.0;

//		int n = (int) Math.max(0, Math.min(16, Math.round(8.6263 * Math.log(_n) + 1.14516)));
//		int n = itemstack.getCount() * 2;
//		double d = 0.5 + n / 32.0;
		float scale = 0.3f;
		for (Axis axis : new Axis[] { Axis.XN, Axis.YN, Axis.ZN }) {
			float r = random.nextInt(300);
			for (int i = 0; i < n; i++) {
				matrixStackIn.pushPose();
				matrixStackIn.translate(.5, .5, .5);
				matrixStackIn.scale(scale, scale, scale);
				matrixStackIn.mulPose(axis.rotation((float) ((r + i) * Math.PI * 2 / n + time)));
				if (axis == Axis.XN)
					matrixStackIn.translate(0, 0, d);
				else
					matrixStackIn.translate(d, 0, 0);

				matrixStackIn.translate(//
						Math.max(-.1, Math.min(.1, (-.25 + .5 * Math.random()) * c)), //
						Math.max(-.1, Math.min(.1, (-.25 + .5 * Math.random()) * c)), //
						Math.max(-.1, Math.min(.1, (-.25 + .5 * Math.random()) * c)) //
				);

				matrixStackIn.translate(0, -.2, 0);

				itemRenderer.render(itemstack, ItemDisplayContext.GROUND, false, matrixStackIn, bufferIn, combinedLightIn, OverlayTexture.NO_OVERLAY,
						ibakedmodel);
				matrixStackIn.popPose();
			}
		}

		matrixStackIn.pushPose();
		matrixStackIn.translate(.5f, .5f, .5f);

		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Axis.XN.rotation((float) (.948 + time)));
		matrixStackIn.translate(0, -.175, 0);
		itemRenderer.render(itemstack, ItemDisplayContext.GROUND, false, matrixStackIn, bufferIn, combinedLightIn, OverlayTexture.NO_OVERLAY,
				ibakedmodel);
		matrixStackIn.popPose();

		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Axis.YN.rotation((float) (.345 + time)));
		matrixStackIn.translate(0, -.175, 0);
		itemRenderer.render(itemstack, ItemDisplayContext.GROUND, false, matrixStackIn, bufferIn, combinedLightIn, OverlayTexture.NO_OVERLAY,
				ibakedmodel);
		matrixStackIn.popPose();

		matrixStackIn.pushPose();
		matrixStackIn.mulPose(Axis.ZN.rotation((float) (time)));
		matrixStackIn.translate(0, -.175, 0);
		itemRenderer.render(itemstack, ItemDisplayContext.GROUND, false, matrixStackIn, bufferIn, combinedLightIn, OverlayTexture.NO_OVERLAY,
				ibakedmodel);
		matrixStackIn.popPose();

		matrixStackIn.popPose();

//		if (n > 1)
//			for (int i = 0; i < n / 3.0; i++) {
//				matrixStackIn.push();
//				random.setSeed(i);
//				matrixStackIn.translate(.5, .5, .5);
//				matrixStackIn.scale(scale, scale, scale);
//				matrixStackIn.rotate(Axis.ZN.rotation((float) (time + i * Math.PI * 2 / n)));
//				matrixStackIn.translate(d, 0, 0);
//
//				matrixStackIn.rotate(Axis.YP.rotation(random.nextInt(300) + time));
//				matrixStackIn.rotate(Axis.XP.rotation(random.nextInt(300) + time));
//				matrixStackIn.rotate(Axis.ZP.rotation(random.nextInt(300) + time));
//				matrixStackIn.translate(0, -.2, 0);
//
//				itemRenderer.renderItem(itemstack, ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, combinedLightIn,
//						OverlayTexture.NO_OVERLAY, ibakedmodel);
//				matrixStackIn.pop();
//			}
//		if (n > 2)
//			for (int i = 0; i < n / 3.0; i++) {
//				matrixStackIn.push();
//				random.setSeed(i);
//				matrixStackIn.translate(.5, .5, .5);
//				matrixStackIn.scale(scale, scale, scale);
//				matrixStackIn.rotate(Axis.XN.rotation((float) (random.nextInt(300) + time + i * Math.PI * 2 / n)));
//				matrixStackIn.translate(0, 0, d);
//
//				matrixStackIn.rotate(Axis.YP.rotation(random.nextInt(300) + time));
//				matrixStackIn.rotate(Axis.XP.rotation(random.nextInt(300) + time));
//				matrixStackIn.rotate(Axis.ZP.rotation(random.nextInt(300) + time));
//				matrixStackIn.translate(0, -.2, 0);
//
//				itemRenderer.renderItem(itemstack, ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, combinedLightIn,
//						OverlayTexture.NO_OVERLAY, ibakedmodel);
//				matrixStackIn.pop();
//			}
	}

}
