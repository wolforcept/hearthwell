package wolforce.hwell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import wolforce.mechanics.api.MakeMod;

@Mod(modid = Hwell.MODID, name = Hwell.NAME, version = Hwell.VERSION)
// @Mod.EventBusSubscriber(modid = Hwell.MODID)
public class Hwell {

	public static MakeMod mod = new MakeMod() {

	};

	@Instance(Hwell.MODID)
	public static Hwell instance;

	public static long nextAvailableNoEnergySound = 0;

	public static final String MODID = "hwell";
	public static final String NAME = "Hearth Well";
	public static final String VERSION = "0.5.3";
	public static final Logger logger = LogManager.getLogger(NAME);

	@SidedProxy(serverSide = "wolforce.hwell.ServerProxy", clientSide = "wolforce.hwell.client.ClientProxy")
	public static IProxy proxy;

	public static interface IProxy {
		void particle(World world, BlockPos pos, BlockPos pos2, Vec3d dir);

		void playSoundNoEnergy(World world, BlockPos pos);

		Object ee(Object payload);
	}

}
