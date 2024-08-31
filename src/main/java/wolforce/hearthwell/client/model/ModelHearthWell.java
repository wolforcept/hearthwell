package wolforce.hearthwell.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class ModelHearthWell<T extends Entity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = //
			new ModelLayerLocation(new ResourceLocation("modid", "modelhearthwell"), "main");

	private ModelPart cube0, cube1, cube2, cube3;

	public ModelHearthWell(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		ModelPart group = root.getChild("group");
		this.cube0 = group.getChild("cube0");
		this.cube1 = group.getChild("cube1");
		this.cube2 = group.getChild("cube1");
		this.cube3 = group.getChild("cube3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition group = partdefinition.addOrReplaceChild("group",
				CubeListBuilder.create().addBox(0, 0, 0, 0, 0, 0, new CubeDeformation(0)), PartPose.offset(0, 24, 0));

		group.addOrReplaceChild("cube0", CubeListBuilder.create().texOffs(9, 51)//
				.addBox(-1, -7, -1, 2, 14, 2, new CubeDeformation(0)), PartPose.offsetAndRotation(0, 0, 0, 0, 0, 0));

		group.addOrReplaceChild("cube1", CubeListBuilder.create().texOffs(7, 20)//
				.addBox(-3, -5, -1.5F, 6, 10, 3, new CubeDeformation(0)), PartPose.offsetAndRotation(0, 0, 0, -3.1416F, 1.0472F, 3.1416F));

		group.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(18, 37)//
				.addBox(-3, -5, -1.5F, 6, 10, 3, new CubeDeformation(0)), PartPose.offsetAndRotation(0, 0, 0, 0, 1.0472F, 0));

		group.addOrReplaceChild("cube3", CubeListBuilder.create().texOffs(27, 43)//
				.addBox(-3, -5, -1.5F, 6, 10, 3, new CubeDeformation(0)), PartPose.offsetAndRotation(0, 0, 0, 0, 0, 0));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

//	public static RenderType getHearthWellRenderType(ResourceLocation locationIn) {
//		RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
//				.setShaderState(MyRenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_SHADER)
//				.setTextureState(new RenderStateShard.TextureStateShard(locationIn, false, false))
//				.setTransparencyState(MyRenderStateShard.TRANSLUCENT_TRANSPARENCY).setCullState(MyRenderStateShard.NO_CULL)
//				.setLightmapState(MyRenderStateShard.LIGHTMAP).setOverlayState(MyRenderStateShard.OVERLAY).createCompositeState(true);
//		return RenderType.create("entity_translucent", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true,
//				rendertype$compositestate);
////		return RenderType.create("entity_cutout_no_cull", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.TRIANGLES, 256, true,
////				RenderType.CompositeState.entityTranslucent(locationIn));
//	}

	private float normalizeTime(double time, double phase, double multiplier) {
		return .6f + (float) (Math.cos(((time + phase) % 100) / 100 * 2 * Math.PI) * multiplier);
	}

	@Override
	public void setupAnim(T p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {

	}

	@Override
	public void renderToBuffer(PoseStack pose, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		double time = (double) System.currentTimeMillis() / 50.0;
		double scale = .1;

		pose.pushPose();

		float dy = normalizeTime(time, 10, scale);
		pose.pushPose();
		pose.translate(0, dy, 0);
		cube0.render(pose, buffer, packedLight, packedOverlay);
		pose.popPose();

		dy = normalizeTime(time, 82, scale);
		pose.pushPose();
		pose.translate(0, dy, 0);
		cube1.render(pose, buffer, packedLight, packedOverlay);
		pose.popPose();

		dy = normalizeTime(time, 43, scale);
		pose.pushPose();
		pose.translate(0, dy, 0);
		cube2.render(pose, buffer, packedLight, packedOverlay);
		pose.popPose();

		dy = normalizeTime(time, 31, scale);
		pose.pushPose();
		pose.translate(0, dy, 0);
		cube3.render(pose, buffer, packedLight, packedOverlay);
		pose.popPose();

		pose.popPose();

		// cube0.render(pose, buffer, packedLight, packedOverlay);

	}

}
