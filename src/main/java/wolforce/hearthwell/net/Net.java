package wolforce.hearthwell.net;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.entities.EntityHearthWell;

public class Net {

	private static SimpleChannel INSTANCE;

	public static void register() {

		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(HearthWell.MODID, "network"), //
				() -> HearthWell.MODID, //
				HearthWell.MODID::equals, //
				HearthWell.MODID::equals //
		);

		int id = 0;
		INSTANCE.registerMessage(id++, MessageToggle.class, MessageToggle::encode, MessageToggle::decode, MessageToggle::onMessage);
//		INSTANCE.registerMessage(id++, MessageEnergy.class, MessageEnergy::encode, MessageEnergy::decode, MessageEnergy::onMessage);
	}

	public static void sendToggleMessage(EntityHearthWell entity, byte x, byte y) {
		INSTANCE.sendToServer(new MessageToggle(entity, x, y));
	}

//	public static void sendEnergizeMessage(EntityHearthWell entity, byte x, byte y, byte energy) {
//		INSTANCE.sendToServer(new MessageEnergy(entity, x, y, energy));
//	}

}
