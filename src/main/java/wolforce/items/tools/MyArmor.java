package wolforce.items.tools;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class MyArmor extends ItemArmor {
	private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };

	public MyArmor(String name, EntityEquipmentSlot slot, int maxUses) {
		super(ArmorMaterial.DIAMOND, 3, slot);
		setRegistryName(name);
		setUnlocalizedName(name);
		setMaxDamage(MAX_DAMAGE_ARRAY[slot.getIndex()] * maxUses);
		setMaxStackSize(1);
	}

	@Override
	public boolean hasOverlay(ItemStack stack) {
		return false;
	}

	@Override
	public ArmorMaterial getArmorMaterial() {
		return ArmorMaterial.DIAMOND;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		// TODO Auto-generated method stub
		return super.getIsRepairable(toRepair, repair);
	}
	
	
}
