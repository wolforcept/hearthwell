package wolforce.client.models.power;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import wolforce.client.models.MyRender;
import wolforce.entities.EntityPower;

public class RenderPower extends MyRender<EntityPower> {

	protected static final double pi100 = 100.0 * Math.PI;

	public RenderPower(RenderManager renderManager, ResourceLocation resloc) {
		super(renderManager, makeModel(), resloc);
	}

	private static ModelBase makeModel() {
		return new ModelPower() {
			@Override
			public void render(Entity _entity, float f, float f1, float f2, float f3, float f4, float scale) {
				EntityPower entity = (EntityPower) _entity;
				// GlStateManager.scale(1.0F, 1.0F, 1.0F);
				super.render(entity, f, f1, f2, f3, f4, scale);
				setRotationAngles(f, f1, f2, f3, f4, scale, entity);

				double time = (int) (entity.getEntityWorld().getWorldTime());

				// GlStateManager.enableAlpha();
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				// GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				// GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE,
				// GlStateManager.DestFactor.ONE);
				// GlStateManager.blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA,
				// GlStateManager.DestFactor.CONSTANT_ALPHA);

				float dy = normalizeTime(time, 10, .1);
				GlStateManager.pushMatrix();
				GlStateManager.translate(0, dy, 0);
				mid.render(scale);
				GlStateManager.popMatrix();

				dy = normalizeTime(time, 31, .1);
				GlStateManager.pushMatrix();
				GlStateManager.translate(0, dy, 0);
				s1.render(scale);
				GlStateManager.popMatrix();

				dy = normalizeTime(time, 43, .1);
				GlStateManager.pushMatrix();
				GlStateManager.translate(0, dy, 0);
				s2.render(scale);
				GlStateManager.popMatrix();

				dy = normalizeTime(time, 82, .1);
				GlStateManager.pushMatrix();
				GlStateManager.translate(0, dy, 0);
				s3.render(scale);
				GlStateManager.popMatrix();

				// GlStateManager.disableAlpha();
				GlStateManager.disableBlend();

			}

			private float normalizeTime(double time, double phase, double multiplier) {
				return (float) (Math.cos(((time + phase) % 100) / 100 * 2 * Math.PI) * multiplier);
				// return (float) (Math.cos(((time) % 100.0 / pi100 + phase)) );
			}
		};
	}

	// @Override
	// public void setup(EntityPower entity, double x, double y, double z, float
	// entityYaw, float partialTicks) {
	//
	// }

}
