package wolforce.hearthwell.net;

import net.minecraft.client.Minecraft;
import wolforce.hearthwell.entities.EntityHearthWell;

public class ClientProxy {

	public final static Minecraft MC = Minecraft.getInstance();

	public static void openHearthWellScreen(EntityHearthWell entity) {
		MC.setScreen(new wolforce.hearthwell.client.screen.ScreenHearthWellMap(entity));
	}

}
