package wolforce.hearthwell.registries;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.entities.EntityFlare;
import wolforce.hearthwell.entities.EntityHearthWell;

public class Entities {

	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HearthWell.MODID);

	public static final RegistryObject<EntityType<EntityFlare>> entity_flare = REGISTRY.register(EntityFlare.REG_ID, () -> //
	EntityType.Builder.<EntityFlare>of(EntityFlare::new, MobCategory.MISC).fireImmune().sized(.1f, .1f).clientTrackingRange(8)
			.build(EntityFlare.REG_ID) //
	);

	public static final RegistryObject<EntityType<EntityHearthWell>> entity_hearthwell = REGISTRY.register(EntityHearthWell.REG_ID, () -> //
	EntityType.Builder.<EntityHearthWell>of(EntityHearthWell::new, MobCategory.MISC).fireImmune().sized(.4f, 1.5f).clientTrackingRange(8)
			.build(EntityHearthWell.REG_ID) //
	);

}
