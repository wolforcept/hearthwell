
package wolforce.hwell.registry;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import wolforce.hwell.Hwell;
import wolforce.hwell.entities.EntityPower;
import wolforce.mechanics.Util;

@Mod.EventBusSubscriber(modid = Hwell.MODID)
public class RegisterEntities {

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {

		EntityEntry entry = EntityEntryBuilder.create() //
				.entity(EntityPower.class) //
				.id(Util.res("entity_power"), 150) //
				.name("entity_power") //
				.tracker(128, 1, true) //
				// .egg(Integer.parseInt("DD4422", 16), Integer.parseInt("FF7020", 16)) //
				// .spawn(typeIn, prob, min, max, biomes) //
				.build();

		event.getRegistry().register(entry);

	}

	// public static void preInit() {
	// EntityRegistry.registerModEntity(Util.res("entity_power"), EntityPower.class,
	// "entity_power", 50, Hwell.instance, 1/* range */,
	// 1, true, Integer.parseInt("DD4422", 16), Integer.parseInt("FF7020", 16));
	// }

}
