package wolforce.hearthwell.events;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.net.Net;
import wolforce.hearthwell.particles.ParticleEnergy;
import wolforce.hearthwell.particles.ParticleEnergyData;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventsRegistry {

	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
//		DataSerializers.registerSerializer(EntityHearthWell.SERIALIZER_BYTE_ARRAY);
//		DataSerializers.registerSerializer(EntityHearthWell.SERIALIZER_SHORT_SET);
		Net.register();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		HearthWell.setupBlocks();
		IForgeRegistry<Block> registry = event.getRegistry();
		for (Block block : HearthWell.blocks.values()) {
			registry.register(block);
		}
	}

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		HearthWell.setupItems();
		IForgeRegistry<Item> registry = event.getRegistry();
		for (Item item : HearthWell.items.values()) {
			registry.register(item);
		}
	}

	@SubscribeEvent
	public static void registerParticles(final RegistryEvent.Register<ParticleType<?>> event) {
		ParticleEnergyData.TYPE.setRegistryName(new ResourceLocation(HearthWell.MODID, ParticleEnergy.REG_ID));
		event.getRegistry().register(ParticleEnergyData.TYPE);
	}

}