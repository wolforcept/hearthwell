package wolforce.items.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import wolforce.Util;

public class MySword extends ItemSword {

	private float attackDamage;
	private ItemStack repairIngot;

	public MySword(String name, int maxUses, float attackDamage, Item repairIngot) {
		super(ToolMaterial.DIAMOND);
		this.repairIngot = new ItemStack(repairIngot);
		this.attackDamage = 3f + attackDamage;
		setRegistryName(name);
		setUnlocalizedName(name);
		setMaxDamage(maxUses);
		setMaxStackSize(1);
	}

	@Override
	public float getAttackDamage() {
		return attackDamage;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		if (Util.isValid(repairIngot) && net.minecraftforge.oredict.OreDictionary.itemMatches(repairIngot, repair, false))
			return true;
		return super.getIsRepairable(toRepair, repair);
	}
}
