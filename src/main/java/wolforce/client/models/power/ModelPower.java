package wolforce.client.models.power;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPower extends ModelBase {

	ModelRenderer mid;
	ModelRenderer s1;
	ModelRenderer s2;
	ModelRenderer s3;

	public ModelPower() {
		textureWidth = 64;
		textureHeight = 64;

		mid = new ModelRenderer(this, 0, 0);
		mid.addBox(-1F, 0F, -1F, 2, 26, 2);
		mid.setRotationPoint(0F, -4F, 0F);
		mid.setTextureSize(64, 64);
		mid.mirror = true;
		setRotation(mid, 0F, 0.2617994F, 0F);
		s1 = new ModelRenderer(this, 0, 0);
		s1.addBox(-4F, 0F, -2F, 8, 16, 4);
		s1.setRotationPoint(0F, 3F, 0F);
		s1.setTextureSize(64, 64);
		s1.mirror = true;
		setRotation(s1, 0F, 0F, 0F);
		s2 = new ModelRenderer(this, 0, 0);
		s2.addBox(-2F, 0F, -4F, 4, 16, 8);
		s2.setRotationPoint(0F, -1F, 0F);
		s2.setTextureSize(64, 64);
		s2.mirror = true;
		setRotation(s2, 0F, 0.5235988F, 0F);
		s3 = new ModelRenderer(this, 0, 0);
		s3.addBox(-2F, 0F, -4F, 4, 16, 8);
		s3.setRotationPoint(0F, 1F, 0F);
		s3.setTextureSize(64, 64);
		s3.mirror = true;
		setRotation(s3, 0F, -0.5235988F, 0F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}