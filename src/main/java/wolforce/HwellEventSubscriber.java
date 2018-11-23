package wolforce;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import wolforce.fluids.BlockLiquidSouls;

@Mod.EventBusSubscriber
public class HwellEventSubscriber {

	@SubscribeEvent
	public static void makeCrystalPassPortals(EntityTravelToDimensionEvent event) {

		if (!(event.getEntity() instanceof EntityItem))
			return;

		if (event.getDimension() == DimensionType.NETHER.getId()) {
			EntityItem entityItem = (EntityItem) event.getEntity();
			if (entityItem.getItem().getItem() == Main.crystal) {
				entityItem.setItem(new ItemStack(Main.crystal_nether, entityItem.getItem().getCount()));
			}
			if (entityItem.getItem().getItem() == Item.getItemFromBlock(Main.crystal_block)) {
				entityItem.setItem(new ItemStack(Main.crystal_nether_block, entityItem.getItem().getCount()));
			}
		}

		if (event.getDimension() == DimensionType.OVERWORLD.getId()) {
			EntityItem entityItem = (EntityItem) event.getEntity();
			if (entityItem.getItem().getItem() == Main.crystal_nether) {
				entityItem.setItem(new ItemStack(Main.crystal, entityItem.getItem().getCount()));
			}
			if (entityItem.getItem().getItem() == Item.getItemFromBlock(Main.crystal_nether_block)) {
				entityItem.setItem(new ItemStack(Main.crystal_block, entityItem.getItem().getCount()));
			}
		}
	}

	@SubscribeEvent // (priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END && event.player.world.isRemote)
			motion(event.player);
	}
	
	@SubscribeEvent // (priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(WorldTickEvent event) {
		if (event.phase == TickEvent.Phase.START)
			return;

		// List entityList = event.world.playerEntities;
		// Iterator<EntityPlayer> iterator = entityList.iterator();
		// while (iterator.hasNext())
		// motion(iterator.next());

		List entityList = event.world.loadedEntityList;
		Iterator<Entity> iterator = entityList.iterator();

		while (iterator.hasNext()) {
			Entity entity = iterator.next();
			if (entity instanceof EntityPlayer)
				motion((EntityPlayer) entity);
			else if (entity instanceof EntityItem)
				motion((EntityItem) entity);
		}
	}

	
	//
	
	//
	
	//
	
	private void motion(EntityPlayer player) {
		if (isInsideLiquidSouls(player))
			player.motionY = .05;
	}

	private void motion(EntityItem item) {
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

	private boolean isInsideLiquidSouls(EntityPlayer player) {
		IBlockState state = player.world.getBlockState(new BlockPos(//
				(int) player.posX - (player.posX < 0 ? 1 : 0), //
				(int) player.posY, //
				(int) player.posZ - (player.posZ < 0 ? 1 : 0)));
		Block block = state.getBlock();
		return block.getMaterial(state).equals(Main.material_liquid_souls);
		// || block.equals(Main.liquid_souls_block) ||
		// block.isAssociatedBlock(Main.liquid_souls_block) || block instanceof
		// BlockLiquidSouls
	}
}
