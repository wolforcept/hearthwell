package wolforce.hearthwell;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ConfigServer {
	public static ForgeConfigSpec CONFIG_SPEC;
	public static ConfigServer CONFIG;

	public final ConfigValue<List<? extends String>> tokenNames;

	public static void init() {
		Pair<ConfigServer, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigServer::new);
		CONFIG_SPEC = specPair.getRight();
		CONFIG = specPair.getLeft();
	}

	ConfigServer(ForgeConfigSpec.Builder builder) {
		tokenNames = builder.comment("The tokens names").defineList("tokenNames", TokenNames.createNames(0), x -> true);
	}

	public static List<? extends String> getTokenNames() {
		return CONFIG.tokenNames.get();
	}

	public static void setTokenNames(List<String> names) {
		CONFIG.tokenNames.set(names);
	}

}