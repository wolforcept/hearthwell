package wolforce.items;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import scala.collection.mutable.HashSet;

public class ItemHeavyShears extends ItemShears {

	public ItemHeavyShears(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(1);
        setMaxDamage(120);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		int id = Enchantment.getEnchantmentID(enchantment);
		return id == 32 || id == 34 || id == 35;
	}
}
