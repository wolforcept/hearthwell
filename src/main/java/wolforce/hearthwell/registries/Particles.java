package wolforce.hearthwell.registries;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.particles.ParticleEnergyData;

import java.util.function.Supplier;

public class Particles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, HearthWell.MODID);
    public static final RegistryObject<ParticleType<ParticleEnergyData>> ENERGY = register("energy", () -> new ParticleType<>(false, ParticleEnergyData.DESERIALIZER) {
        public Codec<ParticleEnergyData> codec() {return ParticleEnergyData.CODEC;}
    });

    public static <T extends ParticleOptions> RegistryObject<ParticleType<T>> register(String id, Supplier<ParticleType<T>> particleType) {
        return PARTICLES.register(id, particleType);
    }

    public static void register(IEventBus bus) {
        Particles.PARTICLES.register(bus);
    }
}
