package wolforce.hwell.items;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import wolforce.hwell.Main;
import wolforce.hwell.base.MyItem;
import wolforce.hwell.recipes.RecipeRepairingPaste;
import wolforce.mechanics.Util;

public class ItemRepairingPaste extends MyItem {

	public ItemRepairingPaste(String name, String[] lore) {
		super(name, lore);
		setMaxStackSize(1);
		setMaxDamage(500);
	}

	@Override
	public void onUpdate(ItemStack paste, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(paste, worldIn, entityIn, itemSlot, isSelected);
		if (worldIn.isRemote)
			return;

		if (entityIn instanceof EntityPlayer
				&& ((EntityPlayer) entityIn).getHeldItemOffhand().getItem().equals(Main.repairing_paste)) {
			EntityPlayer player = (EntityPlayer) entityIn;
			if (player.getHeldItem(EnumHand.OFF_HAND).getItem().equals(Main.repairing_paste)) {
				ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
				if (Util.timeConstraint(player.getEntityWorld().getTotalWorldTime(), 2) && stack.isItemDamaged()
						&& RecipeRepairingPaste.isRepairable(stack)) {
					paste.damageItem(1, player);
					stack.damageItem(-1, player);
				}
			}
		}
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return false;
	}
}
