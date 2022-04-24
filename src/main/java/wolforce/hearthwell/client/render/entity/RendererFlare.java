package wolforce.hearthwell.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import wolforce.hearthwell.entities.EntityFlare;

public class RendererFlare extends EntityRenderer<EntityFlare> implements EntityRendererProvider<EntityFlare> {

	public RendererFlare(Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityFlare entity) {
		return null;
	}

	@Override
	public EntityRenderer<EntityFlare> create(Context context) {
		return new RendererFlare(context);
	}

}
