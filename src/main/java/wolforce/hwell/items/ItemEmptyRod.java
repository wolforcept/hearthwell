
package wolforce.hwell.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import wolforce.hwell.Main;
import wolforce.hwell.base.MyItem;

public class ItemEmptyRod extends MyItem {

	public enum RodType {
		EMPTY(null) {
			@Override
			public ItemStack onItemUseFinish(EntityPlayer player, ItemStack stack, ItemStack hand2, Item nextItem) {
				if (hand2.getItem().equals(BLAZE.consumes))
					return BLAZE.onItemUseFinish(player, stack, hand2, Main.rod_blaze_1);
				if (hand2.getItem().equals(Main.myst_dust)) {
					hand2.shrink(1);
					return MYST.onItemUseFinish(player, stack, hand2, Main.myst_rod);
				}
				return MYST.onItemUseFinish(player, stack, hand2, Main.rod_myst_1);
			}
		},
		MYST(null) {
			@Override
			public ItemStack onItemUseFinish(EntityPlayer player, ItemStack stack, ItemStack hand2, Item nextItem) {
				player.playSound(SoundEvents.ENTITY_WITCH_DRINK, 1f, 1f);
				player.attackEntityFrom(DamageSource.MAGIC, 1);
				return new ItemStack(nextItem);
			}
		},
		BLAZE(Items.BLAZE_POWDER) {
			@Override
			public ItemStack onItemUseFinish(EntityPlayer player, ItemStack stack, ItemStack hand2, Item nextItem) {
				if (hand2.getItem().equals(BLAZE.consumes)) {
					hand2.shrink(1);
					player.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1f, 1f);
					return new ItemStack(nextItem);
				}
				return stack;
			}
		};

		private Item consumes;

		private RodType(Item consumes) {
			this.consumes = consumes;
		}

		public abstract ItemStack onItemUseFinish(EntityPlayer player, ItemStack stack, ItemStack hand2, Item nextItem);
	}

	private final Item nextItem;
	private RodType type;

	public ItemEmptyRod(String name, RodType type, Item nextItem, String... lore) {
		super(name, lore);
		this.nextItem = nextItem;
		this.type = type;
		setMaxStackSize(1);
	}

	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 20;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (hand.equals(EnumHand.OFF_HAND))
			return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(hand));

		ItemStack hand1 = player.getHeldItemMainhand();
		// ItemStack hand2 = player.getHeldItemOffhand();
		if (hand1.getItem() instanceof ItemEmptyRod) {
			player.setActiveHand(EnumHand.MAIN_HAND);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, hand1);
		}
		return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(hand));
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {

		if (!(entityLiving instanceof EntityPlayer))
			return stack;

		EntityPlayer player = (EntityPlayer) entityLiving;

		ItemStack hand1 = player.getHeldItemMainhand();
		ItemStack hand2 = player.getHeldItemOffhand();

		if (hand1.getItem() instanceof ItemEmptyRod) {
			return type.onItemUseFinish(player, stack, hand2, nextItem);
		}
		return stack;

	}

}
