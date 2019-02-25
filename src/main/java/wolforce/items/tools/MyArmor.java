package wolforce.items.tools;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import wolforce.Util;

public class MyArmor extends ItemArmor {

	private ItemStack repairIngot;
	private int enchantability;
	private String fileName;

	public MyArmor(String name, String fileName, EntityEquipmentSlot slot, ArmorMaterial mat, Item repairIngot, int enchantability) {
		super(mat, 3, slot);
		this.fileName = fileName;
		this.enchantability = enchantability;
		this.repairIngot = new ItemStack(repairIngot);
		Util.setReg(this, name);
		setMaxStackSize(1);
	}

	@Override
	public int getItemEnchantability() {
		return enchantability;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return "hwell:textures/armor/" + fileName + "_" + (slot == EntityEquipmentSlot.LEGS ? 2 : 1) + ".png";
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		if (Util.isValid(repairIngot) && net.minecraftforge.oredict.OreDictionary.itemMatches(repairIngot, repair, false))
			return true;
		return super.getIsRepairable(toRepair, repair);
	}

}
