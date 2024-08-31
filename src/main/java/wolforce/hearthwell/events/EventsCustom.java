package wolforce.hearthwell.events;

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import wolforce.hearthwell.ConfigServer;
import wolforce.hearthwell.TokenNames;
import wolforce.hearthwell.data.MapData;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventsCustom {

	@SubscribeEvent
	public static void commonSetup(ServerAboutToStartEvent event) {
		long seed = event.getServer().getWorldData().worldGenOptions().seed();
		ConfigServer.setTokenNames(TokenNames.createNames(seed));
		MapData.loadData();
	}

//	@SubscribeEvent
//	public static void onChat(ServerChatEvent e) {
//		String message = e.getMessage().toLowerCase();
//		if (message.contains("hearth well")) {
////		if (message.contains("gods") && message.contains("please") && message.contains("hearth well")) {
//			ServerPlayerEntity player = e.getPlayer();
//			ServerWorld world = (ServerWorld) player.world;
//
//			int x = (int) player.getPosX();
//			int y = (int) player.getPosY();
//			int z = (int) player.getPosZ();
//			createHearthWell(world, x - 1, y, z);
//		}
//	}

//	private static void createHearthWell(ServerWorld world, int x, int y, int z) {
//		BlockPos pos = new BlockPos(x, y, z);

//		LightningBoltEntity lb = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, world);
//		lb.setEffectOnly(true);
//		lb.setPosition(x - .5, y + .5, z - .5);
//		world.addEntity(lb);
//
//		EntityHearthWell hwEntity = new EntityHearthWell(world);
//		hwEntity.setPosition(x + .5, y + 2, z + .5);
//		world.addEntity(hwEntity);

//		if (!world.isRemote) {
//
//			for (int[] d : EntityHearthWell.POSITIONS_HOLE) {
//				world.destroyBlock(pos.add(d[0], -1, d[1]), true, hwEntity);
//			}
//
//			for (int[] d : EntityHearthWell.POSITIONS_RING) {
//				world.destroyBlock(pos.add(d[0], -1, d[1]), true, hwEntity);
//				world.setBlockState(pos.add(d[0], -1, d[1]), Blocks.BEDROCK.getDefaultState());
//			}
//		}
//	}

}
