package wolforce.hearthwell.util;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import wolforce.hearthwell.net.ClientProxy;

public class UtilClient {

	public static ItemRenderer renderItem = ClientProxy.MC.getItemRenderer();
	public static TextureManager textureManager = ClientProxy.MC.textureManager;
	public static LocalPlayer player = Minecraft.getInstance().player;

	public static void renderItem(PoseStack matrix, ItemStack stack, int x, int y, int z) {
		renderItem(matrix, stack, x, y, z, false);
	}

	public static void renderItem(PoseStack matrix, ItemStack stack, int x, int y, int z, boolean isSilhouette) {

		if (!Util.isValid(stack))
			return;

		BakedModel model = renderItem.getModel(stack, null, player, 0);
		PoseStack posestack = RenderSystem.getModelViewStack();
		posestack.pushPose();
		posestack.translate((double) x, (double) y, (double) (100.0F + z));
		posestack.translate(8.0D, 8.0D, 0.0D);
		posestack.scale(1.0F, -1.0F, 1.0F);
		posestack.scale(16.0F, 16.0F, 16.0F);
		RenderSystem.applyModelViewMatrix();
		PoseStack posestack1 = new PoseStack();
		MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
		Lighting.setupForFlatItems();

		if (isSilhouette)
			renderItemBlack(stack, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880,
					OverlayTexture.NO_OVERLAY, model);
		else
			renderItem.render(stack, ItemTransforms.TransformType.GUI, false, posestack1, multibuffersource$buffersource, 15728880,
					OverlayTexture.NO_OVERLAY, model);

		multibuffersource$buffersource.endBatch();

		posestack.popPose();
		RenderSystem.applyModelViewMatrix();

	}

	public static void renderItemBlack(ItemStack stack, ItemTransforms.TransformType type, boolean _flag, PoseStack matrix,
			MultiBufferSource source, int x, int y, BakedModel p_115151_) {
		if (!stack.isEmpty()) {
			matrix.pushPose();
			boolean flag = type == ItemTransforms.TransformType.GUI || type == ItemTransforms.TransformType.GROUND
					|| type == ItemTransforms.TransformType.FIXED;
			if (flag) {
				if (stack.is(Items.TRIDENT)) {
					p_115151_ = renderItem.getItemModelShaper().getModelManager()
							.getModel(new ModelResourceLocation("minecraft:trident#inventory"));
				} else if (stack.is(Items.SPYGLASS)) {
					p_115151_ = renderItem.getItemModelShaper().getModelManager()
							.getModel(new ModelResourceLocation("minecraft:spyglass#inventory"));
				}
			}

			p_115151_ = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(matrix, p_115151_, type, _flag);
			matrix.translate(-0.5D, -0.5D, -0.5D);
			if (!p_115151_.isCustomRenderer() && (!stack.is(Items.TRIDENT) || flag)) {
				boolean flag1;
				if (type != ItemTransforms.TransformType.GUI && !type.firstPerson() && stack.getItem() instanceof BlockItem) {
					Block block = ((BlockItem) stack.getItem()).getBlock();
					flag1 = !(block instanceof HalfTransparentBlock) && !(block instanceof StainedGlassPaneBlock);
				} else {
					flag1 = true;
				}
				if (p_115151_.isLayered()) {
					net.minecraftforge.client.ForgeHooksClient.drawItemLayered(renderItem, p_115151_, stack, matrix, source, x, y, flag1);
				} else {
					RenderType rendertype = ItemBlockRenderTypes.getRenderType(stack, flag1);
					VertexConsumer vertexconsumer;
					if (stack.is(Items.COMPASS) && stack.hasFoil()) {
						matrix.pushPose();
						PoseStack.Pose posestack$pose = matrix.last();
						if (type == ItemTransforms.TransformType.GUI) {
							posestack$pose.pose().multiply(0.5F);
						} else if (type.firstPerson()) {
							posestack$pose.pose().multiply(0.75F);
						}

						if (flag1) {
							vertexconsumer = ItemRenderer.getCompassFoilBufferDirect(source, rendertype, posestack$pose);
						} else {
							vertexconsumer = ItemRenderer.getCompassFoilBuffer(source, rendertype, posestack$pose);
						}

						matrix.popPose();
					} else if (flag1) {
						vertexconsumer = ItemRenderer.getFoilBufferDirect(source, rendertype, true, stack.hasFoil());
					} else {
						vertexconsumer = ItemRenderer.getFoilBuffer(source, rendertype, true, stack.hasFoil());
					}

					renderItem.renderModelLists(p_115151_, stack, x, y, matrix, new BlackVertexConsumer(vertexconsumer));
				}
			} else {
				net.minecraftforge.client.RenderProperties.get(stack).getItemStackRenderer().renderByItem(stack, type, matrix, source, x,
						y);
			}

			matrix.popPose();
		}
	}

	static class BlackVertexConsumer implements VertexConsumer {

		private VertexConsumer vc;

		public BlackVertexConsumer(VertexConsumer vc) {
			this.vc = vc;
		}

		@Override
		public VertexConsumer vertex(double p_85945_, double p_85946_, double p_85947_) {
			vc.vertex(p_85945_, p_85946_, p_85947_);
			return this;
		}

		@Override
		public VertexConsumer color(int p_85973_, int p_85974_, int p_85975_, int p_85976_) {
//			System.out.println(p_85973_ + " " + p_85974_ + " " + p_85975_ + " " + p_85976_);
			vc.color(0, 0, 0, p_85976_);
			return this;
		}

		@Override
		public VertexConsumer uv(float p_85948_, float p_85949_) {
			vc.uv(p_85948_, p_85949_);
			return this;
		}

		@Override
		public VertexConsumer overlayCoords(int p_85971_, int p_85972_) {
			vc.overlayCoords(p_85971_, p_85972_);
			return this;
		}

		@Override
		public VertexConsumer uv2(int p_86010_, int p_86011_) {
			vc.uv2(p_86010_, p_86011_);
			return this;
		}

		@Override
		public VertexConsumer normal(float p_86005_, float p_86006_, float p_86007_) {
			vc.normal(p_86005_, p_86006_, p_86007_);
			return this;
		}

		@Override
		public void endVertex() {
			vc.endVertex();
		}

		@Override
		public void defaultColor(int p_166901_, int p_166902_, int p_166903_, int p_166904_) {
			vc.defaultColor(p_166901_, p_166902_, p_166903_, p_166904_);
		}

		@Override
		public void unsetDefaultColor() {
			vc.unsetDefaultColor();
		}
	}

	/** @param colorStr e.g. "#FFFFFF" */
	public static float[] hex2Rgb(String colorStr) {
		return new float[] { //
				Integer.valueOf(colorStr.substring(0, 2), 16) / 256f, //
				Integer.valueOf(colorStr.substring(2, 4), 16) / 256f, //
				Integer.valueOf(colorStr.substring(4, 6), 16) / 256f //
		};
	}
}
