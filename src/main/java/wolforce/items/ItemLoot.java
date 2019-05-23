package wolforce.items;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
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
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import wolforce.HwellConfig;
import wolforce.Util;
import wolforce.base.MyItem;

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

	private static ResourceLocation[] easyLootTables;
	private static ResourceLocation[] lootTables;
	private final int lootTableIndex;

	public ItemLoot(String name, int lootTableIndex, String... lore) {
		super(name, lore);
		this.lootTableIndex = lootTableIndex;
		setMaxStackSize(1);
	}

	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return HwellConfig.machines.producerTimeToHatch;
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

	public static void entityItemExpired(ItemExpireEvent event) {
		EntityItem entity = event.getEntityItem();
		if (!entity.world.isRemote) {
			entity.world.playSound(null, entity.getPosition(), SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 1, 1);
			for (ItemStack itemstack : ((ItemLoot) entity.getItem().getItem()).getLoot(entity.world, entity)) {
				Util.spawnItem(entity.world, entity.getPositionVector(), itemstack, Math.random() * .2 - .1, Math.random() * .1,
						Math.random() * .2 - .1);
			}
		}
	}

	private List<ItemStack> getLoot(World world, Entity location) {
		if (world.isRemote)
			return null;
		Random rand = new Random();
		List<ItemStack> lootList;

		LootContext.Builder lootcontext = location instanceof EntityPlayer ? //
				(new LootContext.Builder((WorldServer) world)).withPlayer((EntityPlayer) location) : //
				(new LootContext.Builder((WorldServer) world)).withPlayer(FakePlayerFactory.getMinecraft((WorldServer) world));

		if (lootTableIndex < 0) {
			if (rand.nextDouble() < .75) {
				// NORMAL LOOT 75% of the time
				ResourceLocation lootTable = easyLootTables[(int) (Math.random() * easyLootTables.length)];
				LootTable loottable = world.getLootTableManager().getLootTableFromLocation(lootTable);
				lootList = loottable.generateLootForPools(rand, lootcontext.build());
				return lootList;
			} else {
				// BETTER LOOT 25% of the time
				ResourceLocation lootTable = lootTables[(int) (Math.random() * lootTables.length)];
				LootTable loottable = world.getLootTableManager().getLootTableFromLocation(lootTable);
				lootList = loottable.generateLootForPools(rand, lootcontext.build());
				return lootList;
			}
		} else {
			ResourceLocation lootTable = lootTables[lootTableIndex];
			LootTable loottable = world.getLootTableManager().getLootTableFromLocation(lootTable);
			lootList = loottable.generateLootForPools(rand, lootcontext.build());
			lootList.addAll(loottable.generateLootForPools(rand, lootcontext.build()));
			lootList.addAll(loottable.generateLootForPools(rand, lootcontext.build()));
			lootList.addAll(loottable.generateLootForPools(rand, lootcontext.build()));
			return lootList;
		}
	}

	public static void setLootTables() {
		lootTables = new ResourceLocation[] { //
				LootTableList.ENTITIES_BLAZE, //
				LootTableList.ENTITIES_CREEPER, //
				LootTableList.ENTITIES_ENDERMAN, //
				LootTableList.ENTITIES_GHAST, //
				LootTableList.ENTITIES_SHULKER, //
				LootTableList.ENTITIES_SKELETON, //
				LootTableList.ENTITIES_SLIME, //
				LootTableList.ENTITIES_SPIDER, //
				LootTableList.ENTITIES_WITCH, //
				LootTableList.ENTITIES_WITHER_SKELETON, //
				LootTableList.ENTITIES_ZOMBIE, //
				LootTableList.ENTITIES_GUARDIAN //
		};

		easyLootTables = new ResourceLocation[] { //
				LootTableList.ENTITIES_ZOMBIE, LootTableList.ENTITIES_ZOMBIE, //
				LootTableList.ENTITIES_ZOMBIE, LootTableList.ENTITIES_ZOMBIE, //
				LootTableList.ENTITIES_SKELETON, LootTableList.ENTITIES_SKELETON, //
				LootTableList.ENTITIES_SKELETON, LootTableList.ENTITIES_SKELETON, //
				LootTableList.ENTITIES_CREEPER, LootTableList.ENTITIES_CREEPER, //
				LootTableList.ENTITIES_CREEPER, LootTableList.ENTITIES_CREEPER, //
				LootTableList.ENTITIES_SPIDER, LootTableList.ENTITIES_SPIDER, //
				LootTableList.ENTITIES_SPIDER, LootTableList.ENTITIES_SPIDER, //
				LootTableList.ENTITIES_ENDERMAN, //
		};
	}
}
