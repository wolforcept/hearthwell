package wolforce.hearthwell.client.events;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import wolforce.hearthwell.bases.BlockHasRenderLayer;
import wolforce.hearthwell.client.render.entity.RendererFlare;
import wolforce.hearthwell.client.render.entity.RendererHearthWell;
import wolforce.hearthwell.client.render.te.TerBurstSeed;
import wolforce.hearthwell.particles.ParticleEnergy;
import wolforce.hearthwell.registries.Blocks;
import wolforce.hearthwell.registries.Entities;
import wolforce.hearthwell.registries.Particles;
import wolforce.hearthwell.registries.TileEntities;

import java.util.stream.Collectors;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EventsMod {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {

		for (Block block : Blocks.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toSet())) {

			if (block instanceof BlockHasRenderLayer)
				registerRenderLayer(block);

		}
	}

	@SubscribeEvent
	public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(TileEntities.burst_seed.get(), TerBurstSeed::new);
	}

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(Entities.entity_hearthwell.get(), RendererHearthWell::new);
		event.registerEntityRenderer(Entities.entity_flare.get(), RendererFlare::new);
	}

	private static void registerRenderLayer(Block block) {
		if (block instanceof BlockHasRenderLayer.Cutout)
			ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout()::equals);
		if (block instanceof BlockHasRenderLayer.CutoutMipped)
			ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutoutMipped()::equals);
		if (block instanceof BlockHasRenderLayer.Translucent)
			ItemBlockRenderTypes.setRenderLayer(block, RenderType.translucent()::equals);
	}

	@SubscribeEvent
	public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(Particles.ENERGY.get(), ParticleEnergy.Factory::new);
	}

}
