package wolforce.items;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.util.FakePlayerFactory;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.MyItem;
import wolforce.Util;

public class ItemLoot extends MyItem {

	// loot_blaze
	// loot_creeper
	// loot_enderman
	// loot_ghast
	// loot_shulker
	// loot_skeleton
	// loot_slime
	// loot_witch
	// loot_wither
	// loot_zombie

	private ResourceLocation lootTable = null;
	private Item[] basic_loot;

	public ItemLoot(String name, String... lore) {
		super(name, lore);
		setMaxStackSize(1);
		basic_loot = new Item[] {
				// from most probable to least
				Items.ROTTEN_FLESH, Items.BONE, Items.GUNPOWDER, Items.STRING, Items.ENDER_PEARL }; //
		// Items.POTATO, Items.POISONOUS_POTATO, //
		// Items.MUTTON, Items.BEEF, Items.LEATHER, //
		// Items.RABBIT, Items.RABBIT_HIDE, //
		// Items.EGG, Items.FISH, //
		// Items.BOWL, Items.WOODEN_HOE, Items.WOODEN_AXE, //
		// Items.DRAGON_BREATH, Items.RABBIT_FOOT, Items.CARROT };
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		if (location instanceof EntityItem) {
			final EntityItem dc = (EntityItem) location;
			final List<ItemStack> lootList = getLoot(world, location);
			return new EntityItem(world, dc.posX, dc.posY, dc.posZ, dc.getItem()) {
				{
					lifespan = HwellConfig.lootTimeToHatch;
					setPickupDelay(40);
					motionX = dc.motionX;
					motionY = dc.motionY;
					motionZ = dc.motionZ;
				}

				@Override
				public void onRemovedFromWorld() {
					if (!world.isRemote && getAge() > lifespan) {
						world.playSound(null, getPosition(), SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 1, 1);
						for (ItemStack itemstack : lootList) {
							Util.spawnItem(world, getPositionVector(), itemstack, Math.random() * .2 - .1, Math.random() * .1,
									Math.random() * .2 - .1);
						}
					}
				}
			};
		}
		return null;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (worldIn.isRemote) {
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, ItemStack.EMPTY);
		}
		for (ItemStack itemstack : getLoot(worldIn, playerIn)) {
			playerIn.dropItem(itemstack, false);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, ItemStack.EMPTY);
	}

	private List<ItemStack> getLoot(World world, Entity location) {
		if (world.isRemote)
			return null;
		Random rand = new Random();
		List<ItemStack> lootList;
		if (lootTable == null) {
			lootList = new LinkedList<>();
			int n = (int) (Math.random() * 2);
			for (int i = 0; i < n; i++) {
				int index = (int) (new Random().nextGaussian() * basic_loot.length / 2);
				if (index < 0 || index >= basic_loot.length)
					index = (int) (Math.random() * basic_loot.length);
				lootList.add(new ItemStack(basic_loot[index], (int) (Math.random() * 2)));
			}
		} else {
			LootTable loottable = world.getLootTableManager().getLootTableFromLocation(lootTable);
			LootContext.Builder lootcontext$builder = location instanceof EntityPlayer ? //
					(new LootContext.Builder((WorldServer) world)).withPlayer((EntityPlayer) location) : //
					(new LootContext.Builder((WorldServer) world)).withPlayer(FakePlayerFactory.getMinecraft((WorldServer) world));

			lootList = loottable.generateLootForPools(rand, lootcontext$builder.build());
			lootList.addAll(loottable.generateLootForPools(rand, lootcontext$builder.build()));
			lootList.addAll(loottable.generateLootForPools(rand, lootcontext$builder.build()));
			lootList.addAll(loottable.generateLootForPools(rand, lootcontext$builder.build()));
		}
		return lootList;
	}

	public static void setLootTables() {
		Main.loot_blaze.lootTable = LootTableList.ENTITIES_BLAZE;
		Main.loot_creeper.lootTable = LootTableList.ENTITIES_CREEPER;
		Main.loot_enderman.lootTable = LootTableList.ENTITIES_ENDERMAN;
		Main.loot_ghast.lootTable = LootTableList.ENTITIES_GHAST;
		Main.loot_shulker.lootTable = LootTableList.ENTITIES_SHULKER;
		Main.loot_skeleton.lootTable = LootTableList.ENTITIES_SKELETON;
		Main.loot_slime.lootTable = LootTableList.ENTITIES_SLIME;
		Main.loot_spider.lootTable = LootTableList.ENTITIES_SPIDER;
		Main.loot_witch.lootTable = LootTableList.ENTITIES_WITCH;
		Main.loot_wither.lootTable = LootTableList.ENTITIES_WITHER_SKELETON;
		Main.loot_zombie.lootTable = LootTableList.ENTITIES_ZOMBIE;
	}
}
