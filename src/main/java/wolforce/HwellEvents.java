package wolforce;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import wolforce.fluids.BlockLiquidSouls;
import wolforce.items.ItemLoot;
import wolforce.registry.RegisterRecipes;

@Mod.EventBusSubscriber
public class HwellEvents {

	// @SubscribeEvent
	// public static void makeCrystalPassPortals(EntityTravelToDimensionEvent event)
	// {
	//
	// if (!(event.getEntity() instanceof EntityItem))
	// return;
	//
	// if (event.getDimension() == DimensionType.NETHER.getId()) {
	// EntityItem entityItem = (EntityItem) event.getEntity();
	// if (entityItem.getItem().getItem() == Main.crystal) {
	// entityItem.setItem(new ItemStack(Main.crystal_nether,
	// entityItem.getItem().getCount()));
	// }
	// if (entityItem.getItem().getItem() ==
	// Item.getItemFromBlock(Main.crystal_block)) {
	// entityItem.setItem(new ItemStack(Main.crystal_nether_block,
	// entityItem.getItem().getCount()));
	// }
	// }
	//
	// if (event.getDimension() == DimensionType.OVERWORLD.getId()) {
	// EntityItem entityItem = (EntityItem) event.getEntity();
	// if (entityItem.getItem().getItem() == Main.crystal_nether) {
	// entityItem.setItem(new ItemStack(Main.crystal,
	// entityItem.getItem().getCount()));
	// }
	// if (entityItem.getItem().getItem() ==
	// Item.getItemFromBlock(Main.crystal_nether_block)) {
	// entityItem.setItem(new ItemStack(Main.crystal_block,
	// entityItem.getItem().getCount()));
	// }
	// }
	// }

	@SubscribeEvent // (priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(PlayerLoggedInEvent event) {
		if (!event.player.getEntityWorld().isRemote && RegisterRecipes.errored_recipes_file)
			event.player.sendMessage(new TextComponentString("HEARTH WELL WARNING - SERVER RECIPES FILE IS CORRUPTED!"));

		if (!event.player.getEntityWorld().isRemote && RegisterRecipes.old_version_recipes_file)
			event.player.sendMessage(
					new TextComponentString("HEARTH WELL WARNING - SERVER RECIPES FILE MAY NOT BE COMPATIBLE WITH THIS VERSION!"));
	}

	@SubscribeEvent // (priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(ItemExpireEvent event) {
		if (event.getEntityItem().getItem().getItem() instanceof ItemLoot) {
			ItemLoot.entityItemExpired(event);
		}
	}

	@SubscribeEvent
	public static void onEvent(PlayerTickEvent event) {
		// if (event.phase == TickEvent.Phase.END && !event.player.world.isRemote) {
		// if
		// (event.player.getHeldItem(EnumHand.OFF_HAND).getItem().equals(Main.repairing_paste))
		// {
		// ItemStack stack = event.player.getHeldItem(EnumHand.MAIN_HAND);
		// ItemStack paste = event.player.getHeldItem(EnumHand.OFF_HAND);
		// if (timeConstraint(event.player) && stack.isItemDamaged() &&
		// RecipeRepairingPaste.isRepairable(stack.getItem())) {
		// paste.damageItem(1, event.player);
		// stack.damageItem(-1, event.player);
		// }
		// }
		// }
		if (event.phase == TickEvent.Phase.END && event.player.world.isRemote)
			motion(event.player);
	}

	@SubscribeEvent // (priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(WorldTickEvent event) {
		if (event.phase == TickEvent.Phase.START)
			return;

		// List entityList = event.world.playerEntities;
		// Iterator<EntityPlayer> iterator = entityList.iterator();
		// while (iterator.hasNext())
		// motion(iterator.next());

		List<Entity> entityList = event.world.loadedEntityList;

		for (int i = 0; i < entityList.size(); i++) {
			Entity entity = entityList.get(i);
			if (entity instanceof EntityPlayer)
				motion((EntityPlayer) entity);
			else if (entity instanceof EntityItem)
				motion((EntityItem) entity);
		}
	}

	//

	//

	//

	private static void motion(EntityPlayer player) {
		if (isInsideLiquidSouls(player)) {
			player.motionY = player.isSneaking() ? -.08 : .08;
			player.fallDistance = 0;
		}
	}

	private static void motion(EntityItem item) {
		IBlockState bstate = item.world.getBlockState(item.getPosition());
		if (bstate.getBlock().equals(Main.liquid_souls_block) || bstate.getBlock().isAssociatedBlock(Main.liquid_souls_block)
				|| bstate.getBlock() instanceof BlockLiquidSouls) {
			BlockLiquidSouls ls = ((BlockLiquidSouls) bstate.getBlock());
			Vec3d flow = ls.getFlowVector(item.world, item.getPosition());
			item.motionX += flow.x * .1;
			item.motionY += flow.y * .1;
			item.motionZ += flow.z * .1;
			// TODO UPDATE CLIENTS
		}
	}

	private static boolean isInsideLiquidSouls(EntityPlayer player) {
		BlockPos pos1 = new BlockPos(//
				(int) player.posX - (player.posX < 0 ? 1 : 0), //
				(int) player.posY, //
				(int) player.posZ - (player.posZ < 0 ? 1 : 0) //
		);
		return player.world.getBlockState(pos1).getMaterial().equals(Main.material_liquid_souls)
				|| player.world.getBlockState(pos1.up()).getMaterial().equals(Main.material_liquid_souls);
	}
}
