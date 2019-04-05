package wolforce;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Hwell.MODID, name = Hwell.NAME, version = Hwell.VERSION)
@Mod.EventBusSubscriber(modid = Hwell.MODID)
public class Hwell {

	@Instance(Hwell.MODID)
	public static Hwell instance;

	static {
		FluidRegistry.enableUniversalBucket();
	}

	public static final String MODID = "hwell";
	public static final String NAME = "Hearth Well";
	public static final String VERSION = "0.3";
	public static final Logger logger = LogManager.getLogger(NAME);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Main.preInit(event);
		if (event.getSide() == Side.CLIENT)
			clientPreInit();
	}

	private void clientPreInit() {
		wolforce.registry.RegisterModels.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		Main.init(event);
	}
}
