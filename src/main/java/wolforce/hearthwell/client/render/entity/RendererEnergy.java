package wolforce.hearthwell.client.render.entity;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import wolforce.hearthwell.entities.EntityFlare;

public class RendererEnergy extends EntityRenderer<EntityFlare> implements EntityRendererProvider<EntityFlare> {

	public RendererEnergy(Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityFlare entity) {
		return null;
	}

	@Override
	public EntityRenderer<EntityFlare> create(Context context) {
		return new RendererEnergy(context);
	}

}
