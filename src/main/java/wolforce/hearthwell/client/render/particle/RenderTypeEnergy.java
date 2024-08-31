package wolforce.hearthwell.client.render.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.GL11;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.net.ClientProxy;

public class RenderTypeEnergy implements ParticleRenderType {

	private boolean dark;

	public RenderTypeEnergy() {
	}

	public RenderTypeEnergy dark() {
		dark = true;
		return this;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {

//		RenderSystem.depthMask(false);
		RenderSystem.disableDepthTest();
		RenderSystem.enableBlend();

		if (dark)
			RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		else
			RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

//		RenderSystem.alphaFunc(GL11.GL_ALWAYS, 0);
//		RenderSystem.disableLighting();

//		textureManager.bindForSetup(TextureAtlas.LOCATION_PARTICLES);
		RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
		textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).setBlurMipmap(true, false);
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void end(Tesselator tessellator) {
		tessellator.end();
		ClientProxy.MC.textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES).restoreLastBlurMipmap();
//		RenderSystem.defaultAlphaFunc();
		RenderSystem.disableBlend();
		RenderSystem.enableDepthTest();
//		RenderSystem.depthMask(true);
//		RenderSystem.enableLighting();
	}

	@Override
	public String toString() {
		return HearthWell.MODID + ":energy_particle_render_type";
	}

}
