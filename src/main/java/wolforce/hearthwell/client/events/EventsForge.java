package wolforce.hearthwell.client.events;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import wolforce.hearthwell.HearthWell;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EventsForge {

	public static final ItemStack identifierStack = new ItemStack(HearthWell.myst_dust);

	@SubscribeEvent
	public static void renderTooltip(final RenderTooltipEvent.Pre event) {
		if (event.getItemStack() == identifierStack) {

		}
	}

}
