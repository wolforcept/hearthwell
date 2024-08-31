package wolforce.hearthwell.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleEnergyData implements ParticleOptions {

	int color;

	public ParticleEnergyData(int color) {
		this.color = color;
	}

	public ParticleEnergyData(int r, int g, int b) {
		this.color = (r << 24) + (g << 16) + (b << 8);
	}

	public ParticleEnergyData(int r, int g, int b, int a) {
		this.color = (r << 24) + (g << 16) + (b << 8) + a;
	}

	@Override
	public ParticleType<?> getType() {
		return TYPE;
	}

	@Override
	public void writeToNetwork(FriendlyByteBuf buffer) {
		buffer.writeInt(color);
	}

	@Override
	public String writeToString() {
		return ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()) + " " + color;
	}

	@SuppressWarnings("deprecation")
	public static final Deserializer<ParticleEnergyData> DESERIALIZER = new Deserializer<>() {

        @Override
        public ParticleEnergyData fromCommand(ParticleType<ParticleEnergyData> type, StringReader reader) throws CommandSyntaxException {
			reader.readString();
            reader.expect(' ');
            return new ParticleEnergyData(reader.readInt());
        }

        @Override
        public ParticleEnergyData fromNetwork(ParticleType<ParticleEnergyData> type, FriendlyByteBuf buf) {
            return new ParticleEnergyData(buf.readInt());
        }
    };

	public static final ParticleType<ParticleEnergyData> TYPE = new ParticleType<>(false, DESERIALIZER) {
        public Codec<ParticleEnergyData> codec() {
            return CODEC;
        }
    };

	public static final Codec<ParticleEnergyData> CODEC = RecordCodecBuilder
			.create(instance -> instance.group(Codec.INT.fieldOf("color").forGetter(d -> d.color))//
					.apply(instance, ParticleEnergyData::new));
}
