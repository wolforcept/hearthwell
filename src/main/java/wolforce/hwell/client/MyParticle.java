package wolforce.hwell.client;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MyParticle extends Particle {

	float oSize;
	Vec3d tar;

	protected MyParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46284_8_, double p_i46284_10_,
			double p_i46284_12_) {
		this(worldIn, xCoordIn, yCoordIn, zCoordIn, p_i46284_8_, p_i46284_10_, p_i46284_12_, 1.0F);
	}

	protected MyParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46285_8_, double p_i46285_10_,
			double p_i46285_12_, float p_i46285_14_) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
		this.setParticleTextureIndex(128 + (7 - this.particleAge * 8 / this.particleMaxAge));
		this.motionX *= 0.10000000149011612D;
		this.motionY *= 0.10000000149011612D;
		this.motionZ *= 0.10000000149011612D;
		this.motionX += p_i46285_8_ * 0.4D;
		this.motionY += p_i46285_10_ * 0.4D;
		this.motionZ += p_i46285_12_ * 0.4D;
		float f = (float) (Math.random() * 0.30000001192092896D + 0.6000000238418579D);
		this.particleRed = f;
		this.particleGreen = f;
		this.particleBlue = f;
		this.particleScale *= 0.75F;
		this.particleScale *= p_i46285_14_;
		this.oSize = this.particleScale;
		this.particleMaxAge = (int) (6.0D / (Math.random() * 0.8D + 0.6D));
		this.particleMaxAge = (int) ((float) this.particleMaxAge * p_i46285_14_);
		this.setParticleTextureIndex(65);
		this.canCollide = false;
		this.onUpdate();
	}

	/**
	 * Renders the particle
	 */
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ,
			float rotationYZ, float rotationXY, float rotationXZ) {
		float f = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge * 32.0F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		this.particleScale = this.oSize * f;
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}

	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge) {
			this.setExpired();
		}

		this.move(this.motionX, this.motionY, this.motionZ);
		this.particleGreen = (float) ((double) this.particleGreen * 0.96D);
		this.particleBlue = (float) ((double) this.particleBlue * 0.9D);
		this.motionX *= 0.699999988079071D;
		this.motionY *= 0.699999988079071D;
		this.motionZ *= 0.699999988079071D;
		// this.motionY -= 0.019999999552965164D;

		if (tar != null)
			if (new Vec3d(posX, posY, posZ).distanceTo(tar) < new Vec3d(motionX, motionY, motionZ).lengthVector() * 2)
				setExpired();
	}

	public static class MyParticleFactory implements IParticleFactory {

		@Override
		public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
				double xSpeedIn, double ySpeedIn, double zSpeedIn, int... args) {
			MyParticle particle = new MyParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
			particle.setRBGColorF(particle.getRedColorF() * 0.3F, particle.getGreenColorF() * 0.8F, particle.getBlueColorF());
			particle.nextTextureIndexX();
			particle.tar = new Vec3d(args[0] / 1000f, args[1] / 1000f, args[2] / 1000f);
			// particle.setParticleTextureIndex(particleTextureIndex);
			return particle;
		}

	}

	public static MyParticle makeParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn, double tarx, double tary, double tarz) {
		MyParticle particle = new MyParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		particle.setRBGColorF(particle.getRedColorF() * 0.3F, particle.getGreenColorF() * 0.8F, particle.getBlueColorF());
		particle.nextTextureIndexX();
		particle.tar = new Vec3d(tarx, tary, tarz);
		// particle.setParticleTextureIndex(particleTextureIndex);
		return particle;
	}
}
