package wolforce;

import java.util.HashSet;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import wolforce.fluids.BlockLiquidSouls;
import wolforce.items.ItemLoot;
import wolforce.recipes.RecipeNetherPortal;
import wolforce.registry.RegisterRecipes;

@Mod.EventBusSubscriber
public class HwellEvents {

	private static HashSet<String> prayers = new HashSet<>();

	@SubscribeEvent
	public static void checkChatForBook(ServerChatEvent event) {
		if (lastChatIsValid(event.getMessage())) {
			final String username = event.getUsername();
			prayers.add(username);
			new Thread() {
				public void run() {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					prayers.remove(username);
				};
			}.start();
		}
	}

	// please gods send a book to your poor servant
	@SubscribeEvent
	public static void requestBook(RightClickItem event) {

		if (event.getHand() != EnumHand.MAIN_HAND)
			return;

		EntityPlayer player = event.getEntityPlayer();
		if (!player.getEntityWorld().isRemote && //
				HwellConfig.general.book_ritual_enabled && //
				player.rotationPitch <= -80 && //
				player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.WOODEN_PRESSURE_PLATE) && //
				player.getHeldItemOffhand().getItem() == Item.getItemFromBlock(Blocks.WOODEN_PRESSURE_PLATE) && //
				player.getHeldItemMainhand().getCount() == 1 && //
				player.getHeldItemOffhand().getCount() == 1 && //
				Item.getByNameOrId("patchouli:guide_book") != null && //
				prayers.contains(player.getName())//
		) {

			player.getHeldItemMainhand().shrink(1);
			player.getHeldItemOffhand().shrink(1);

			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("patchouli:book", "hwell:book_of_the_well");
			ItemStack book = new ItemStack(Item.getByNameOrId("patchouli:guide_book"), 1, 0, nbt);
			book.setTagInfo("patchouli:book", new NBTTagString("hwell:book_of_the_well"));
			System.out.println(player.getHeldItemMainhand());
			player.setHeldItem(EnumHand.MAIN_HAND, book);

			prayers.remove(player.getName());
			World world = player.getEntityWorld();
			if (!world.isRemote) {
				world.addWeatherEffect(new EntityLightningBolt(world, player.posX, player.posY, player.posZ, true));
			}
		}
	}

	private static boolean lastChatIsValid(String string) {
		if (string == null)
			return false;
		String s = string.toLowerCase();
		return s.length() > 30 && s.contains("poor") && s.contains("please") && s.contains("send") && s.contains("book")
				&& s.contains("gods");
		// gods please send a book to your poor servant
	}

	@SubscribeEvent
	public static void travelToDim(EntityTravelToDimensionEvent event) {

		if ((event.getEntity() instanceof EntityItem)) {
			EntityItem entityItem = (EntityItem) event.getEntity();
			ItemStack stackThrown = RecipeNetherPortal.getOutput(entityItem.getItem());
			if (Util.isValid(stackThrown)) {
				event.setCanceled(true);
				ItemStack newItemStack = stackThrown.copy();
				newItemStack.setCount(entityItem.getItem().getCount() * newItemStack.getCount());
				entityItem.setItem(newItemStack);
				// spawnInPlaceOf(newItemStack, entityItem);
			}
		}

		if (!HwellConfig.general.allowEntitiesToTravelToTheNether
				&& event.getDimension() == DimensionType.NETHER.getId())
			event.setCanceled(true);
	}

	// private static void spawnInPlaceOf(ItemStack newItemStack, EntityItem
	// entityItem) {
	// EntityItem newEntity = new EntityItem(entityItem.world, entityItem.posX,
	// entityItem.posY, entityItem.posZ, newItemStack);
	// newEntity.motionX = entityItem.motionX;
	// newEntity.motionY = entityItem.motionY;
	// newEntity.motionZ = entityItem.motionZ;
	// entityItem.setDead();
	// entityItem.world.spawnEntity(newEntity);
	// }

	@SubscribeEvent // (priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(PlayerLoggedInEvent event) {
		if (!event.player.getEntityWorld().isRemote && RegisterRecipes.errored_recipes_file)
			event.player
					.sendMessage(new TextComponentString("HEARTH WELL WARNING - SERVER RECIPES FILE IS CORRUPTED!"));

		if (!event.player.getEntityWorld().isRemote && RegisterRecipes.old_version_recipes_file)
			event.player.sendMessage(new TextComponentString(
					"HEARTH WELL WARNING - SERVER RECIPES FILE MAY NOT BE COMPATIBLE WITH THIS VERSION! (Should be "
							+ RegisterRecipes.recipenr + " )"));
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
		if (bstate.getBlock().equals(Main.liquid_souls_block)
				|| bstate.getBlock().isAssociatedBlock(Main.liquid_souls_block)
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
