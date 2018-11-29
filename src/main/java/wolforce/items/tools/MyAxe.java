package wolforce.items.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import wolforce.Util;

public class MyAxe extends ItemAxe {

	private ItemStack repairIngot;

	public MyAxe(String name, int maxUses, int harvestLevel, float attackDamage, float attackSpeed, Item repairIngot) {
		super(ToolMaterial.DIAMOND, attackDamage, attackSpeed);
		this.repairIngot = new ItemStack(repairIngot);
		setRegistryName(name);
		setUnlocalizedName(name);
		setMaxDamage(maxUses);
		setHarvestLevel("axe", harvestLevel);
		setMaxStackSize(1);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		if (Util.isValid(repairIngot) && net.minecraftforge.oredict.OreDictionary.itemMatches(repairIngot, repair, false))
			return true;
		return super.getIsRepairable(toRepair, repair);
	}
}
