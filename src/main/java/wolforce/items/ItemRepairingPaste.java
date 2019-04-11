package wolforce.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.base.MyItem;
import wolforce.recipes.RecipeRepairingPaste;

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
				if (timeConstraint(player) && stack.isItemDamaged() && RecipeRepairingPaste.isRepairable(stack.getItem())) {
					paste.damageItem(1, player);
					stack.damageItem(-1, player);
				}
			}
		}
	}

	private boolean timeConstraint(EntityPlayer player) {
		String str = (player.getEntityWorld().getTotalWorldTime() + "");
		return str.charAt(str.length() - 2) == '0';
	}
}
