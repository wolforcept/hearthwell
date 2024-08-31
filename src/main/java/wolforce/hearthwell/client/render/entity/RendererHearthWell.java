package wolforce.hearthwell.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.client.model.ModelHearthWell;
import wolforce.hearthwell.entities.EntityHearthWell;

public class RendererHearthWell extends EntityRenderer<EntityHearthWell> implements EntityRendererProvider<EntityHearthWell> {

	public static final ResourceLocation MODEL_TEXTURE = new ResourceLocation(HearthWell.MODID, "textures/entity/hearthwell.png");
	private ModelHearthWell<EntityHearthWell> model;

	public RendererHearthWell(EntityRendererProvider.Context context) {
		super(context);
		model = new ModelHearthWell<>(ModelHearthWell.createBodyLayer().bakeRoot());
	}

	@Override
	public ResourceLocation getTextureLocation(EntityHearthWell entity) {
		return MODEL_TEXTURE;
	}

	@Override
	public EntityRenderer<EntityHearthWell> create(Context context) {
		return new RendererHearthWell(context);
	}

	public void render(EntityHearthWell entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
			int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
		matrixStackIn.pushPose();
		matrixStackIn.translate(0, .2, 0);
		VertexConsumer ivertexbuilder = net.minecraft.client.renderer.entity.ItemRenderer.getFoilBufferDirect(bufferIn,
				this.model.renderType(this.getTextureLocation(entityIn)), false, false);
		this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		matrixStackIn.popPose();
	}

}
