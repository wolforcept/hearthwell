package wolforce;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import wolforce.fluids.BlockLiquidSouls;
import wolforce.recipes.RecipeRepairingPaste;

@Mod.EventBusSubscriber
public class HwellEventSubscriber {

	@SubscribeEvent
	public static void registerColors(ColorHandlerEvent.Block event) {
		IBlockColor grassBlockColor = new IBlockColor() {
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				return worldIn != null && pos != null ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos)
						: ColorizerGrass.getGrassColor(0.5D, 1.0D);
			}
		};
		event.getBlockColors().registerBlockColorHandler(grassBlockColor, Main.fullgrass_block);
	}	

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
	public void onEvent(PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END && !event.player.world.isRemote) {
			if (event.player.getHeldItem(EnumHand.OFF_HAND).getItem().equals(Main.repairing_paste)) {
				ItemStack stack = event.player.getHeldItem(EnumHand.MAIN_HAND);
				ItemStack paste = event.player.getHeldItem(EnumHand.OFF_HAND);
				if (timeConstraint(event.player) && stack.isItemDamaged() && RecipeRepairingPaste.isRepairable(stack.getItem())) {
					paste.damageItem(1, event.player);
					stack.damageItem(-1, event.player);
				}
			}
		}
		if (event.phase == TickEvent.Phase.END && event.player.world.isRemote)
			motion(event.player);
	}

	private boolean timeConstraint(EntityPlayer player) {
		String str = (player.getEntityWorld().getTotalWorldTime() + "");
		return str.charAt(str.length() - 2) == '0';
	}

	@SubscribeEvent // (priority = EventPriority.NORMAL, receiveCanceled = true)
	public void onEvent(WorldTickEvent event) {
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

	private void motion(EntityPlayer player) {
		if (isInsideLiquidSouls(player)) {
			player.motionY = player.isSneaking() ? -.08 : .08;
			player.fallDistance = 0;
		}
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
		BlockPos pos1 = new BlockPos(//
				(int) player.posX - (player.posX < 0 ? 1 : 0), //
				(int) player.posY, //
				(int) player.posZ - (player.posZ < 0 ? 1 : 0) //
		);
		return player.world.getBlockState(pos1).getMaterial().equals(Main.material_liquid_souls)
				|| player.world.getBlockState(pos1.up()).getMaterial().equals(Main.material_liquid_souls);
	}
}
