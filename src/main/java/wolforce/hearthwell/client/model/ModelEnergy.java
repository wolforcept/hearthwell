package wolforce.hearthwell.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import wolforce.hearthwell.entities.EntityFlare;

public class ModelEnergy<T extends Entity> extends EntityModel<EntityFlare> {

	private final ModelPart bb_main;

	public ModelEnergy(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		// PartDefinition bb_main =
		partdefinition.addOrReplaceChild("bb_main", //
				CubeListBuilder.create().texOffs(0, 0) //
						.addBox(-2.0F, -9.0F, 0.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0) //
						.addBox(-1.0F, -4.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), //
				PartPose.offset(0.0F, 24.0F, 0.0F) //
		);

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(EntityFlare entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		bb_main.render(poseStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}

// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

//public class ModelEnergy extends EntityModel<EntityFlare> {
//	private final ModelPart bb_main;
//	private final ModelPart cube_r1;
//	private final ModelPart cube_r2;
//
//	public ModelEnergy() {
//		texWidth = 16;
//		texHeight = 16;
//
//		bb_main = new ModelPart(this);
//		bb_main.setPos(0.0F, 24.0F, 0.0F);
//		bb_main.texOffs(0, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, 0.0F, false);
//
//		cube_r1 = new ModelPart(this);
//		cube_r1.setPos(8.0F, 0.0F, 8.0F);
//		bb_main.addChild(cube_r1);
//		setRotationAngle(cube_r1, -1.5708F, -1.5708F, 0.0F);
//		cube_r1.texOffs(0, 0).addBox(-16.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, 0.0F, false);
//
//		cube_r2 = new ModelPart(this);
//		cube_r2.setPos(0.0F, 8.0F, 8.0F);
//		bb_main.addChild(cube_r2);
//		setRotationAngle(cube_r2, 0.0F, -1.5708F, 0.0F);
//		cube_r2.texOffs(0, 0).addBox(-16.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, 0.0F, false);
//	}
//
//	
//}