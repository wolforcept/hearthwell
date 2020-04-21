package wolforce.hwell.items;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import wolforce.mechanics.Util;

public class ItemHeavyShears extends ItemShears {

	public ItemHeavyShears(String name) {
		Util.setReg(this, name);
		setMaxStackSize(1);
		setMaxDamage(120);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		int id = Enchantment.getEnchantmentID(enchantment);
		return id == 32 || id == 34 || id == 35;
	}
}
