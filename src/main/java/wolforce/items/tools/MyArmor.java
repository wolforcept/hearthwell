package wolforce.items.tools;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import wolforce.Main;

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
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return "hwell:textures/armor/soulsteel_" + (slot == EntityEquipmentSlot.LEGS ? 2 : 1) + ".png";
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem().equals(this) || repair.getItem().equals(Main.soulsteel_ingot);
	}
}
