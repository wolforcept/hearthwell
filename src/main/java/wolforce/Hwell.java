package wolforce;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Hwell.MODID, name = Hwell.NAME, version = Hwell.VERSION)
@Mod.EventBusSubscriber(modid = Hwell.MODID)
public class Hwell {

	@Instance(Hwell.MODID)
	public static Hwell instance;

	public static long nextAvailableNoEnergySound = 0;

	static {
		FluidRegistry.enableUniversalBucket();
	}

	public static final String MODID = "hwell";
	public static final String NAME = "Hearth Well";
	public static final String VERSION = "0.5.2";
	public static final Logger logger = LogManager.getLogger(NAME);

	@SidedProxy(serverSide = "wolforce.ServerProxy", clientSide = "wolforce.client.ClientProxy")
	public static IProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Main.preInit(event);
		if (event.getSide() == Side.CLIENT)
			clientPreInit();
	}

	private void clientPreInit() {
		wolforce.registry.client.RegisterModels.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		Main.init(event);
	}

	public static interface IProxy {
		void particle(World world, BlockPos pos, BlockPos pos2, Vec3d dir);

		void playSoundNoEnergy(World world, BlockPos pos);

		Object ee(Object payload);
	}

}
