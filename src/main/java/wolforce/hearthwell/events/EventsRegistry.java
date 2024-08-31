package wolforce.hearthwell.events;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import wolforce.hearthwell.net.Net;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventsRegistry {

	@SubscribeEvent
	public static void commonSetup(FMLCommonSetupEvent event) {
//		DataSerializers.registerSerializer(EntityHearthWell.SERIALIZER_BYTE_ARRAY);
//		DataSerializers.registerSerializer(EntityHearthWell.SERIALIZER_SHORT_SET);
		Net.register();
	}

}