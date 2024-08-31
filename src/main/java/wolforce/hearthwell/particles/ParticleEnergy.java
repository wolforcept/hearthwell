package wolforce.hearthwell.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wolforce.hearthwell.client.render.particle.RenderTypeEnergy;

@OnlyIn(Dist.CLIENT)
public class ParticleEnergy extends TextureSheetParticle {

	public static final Minecraft mc = Minecraft.getInstance();
	public static final String REG_ID = "particle_energy";

	private static final ParticleRenderType RENDER_TYPE = new RenderTypeEnergy();
	private static final ParticleRenderType RENDER_TYPE_DARK = new RenderTypeEnergy().dark();

	private int color;

	public ParticleEnergy(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet, int color) {
		super(world, x, y, z);
		this.color = color;
		this.gravity = 0.0F;
		this.hasPhysics = false;
		this.xd = xSpeed;
		this.yd = ySpeed;
		this.zd = zSpeed;

		if (color == 0) {
			float f = (float) (.2 + Math.random() * .3);
			setColor(//
					f + .2f, //
					f, //
					(float) (.5 + Math.random() * .5)//
			);
			this.setAlpha(0);
		} else {
			int c = Math.abs(color);
			int b = (c) & 0xFF;
			int g = (c >> 8) & 0xFF;
			int r = (c >> 16) & 0xFF;
//			int a = (c >> 24) & 0xFF;
			this.setColor(r / 255f, g / 255f, b / 255f);
			if (color < 0) {
				this.setAlpha(.02f);
			}
		}
//		setColor(1, 1, 1);

		lifetime = 25 + (int) (Math.random() * 50);
		quadSize = (float) (0.01 + Math.random() * 0.1);
		setSprite(spriteSet.get(1, 2));

//		if (color < 0) {
//			this.setAlphaF(0.01f);
//		}
//		setAlphaF(color < 0 ? 0.1f : 1f);

	}

	@Override
	public ParticleRenderType getRenderType() {
		return color < 0 ? RENDER_TYPE_DARK : RENDER_TYPE;
	}

	@Override
	public float getQuadSize(float scaleFactor) {
		double scale = (lifetime - age) * .015;
		if (color < 0)
			scale *= 3;
		return .5f * (float) Math.max(scale, 0);
	}

	@Override
	public void tick() {
		if (color == 0) {
			double alpha = -(age - 100) * (age + 10) * .00011;
			setAlpha((float) Math.max(0, Math.min(1, alpha)));
		}
		super.tick();
	}

	//
	//

//	public void setColorByType(byte type) {
//
//		if (Math.abs(type) == 1) {
//			float f = (float) (.2 + Math.random() * .3);
//			setColor(//
//					f + .2f, //
//					f, //
//					(float) (.5 + Math.random() * .5)//
//			);
//			return;
//		}
//
//		if (type > 1) {
////			EnergyType energyType = MapData.DATA.getEnergyType(type);
////			setColor(energyType.getR(), energyType.getG(), energyType.getB());
//			setColor(1, 0, 0);
//			return;
//		}
//
//		setColor(1, 1, 1);
//
//	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<ParticleEnergyData> {
		private final SpriteSet spriteSet;

		public Factory(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(ParticleEnergyData data, ClientLevel world, double x, double y, double z, double vx, double vy, double vz) {
			return new ParticleEnergy(world, x, y, z, vx, vy, vz, spriteSet, data.color);
		}
	}

}
