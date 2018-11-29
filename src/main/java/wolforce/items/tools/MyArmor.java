package wolforce.items.tools;

import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
import wolforce.Util;

public class MyArmor extends ItemArmor {
	
	private ItemStack repairIngot;

	public MyArmor(String name, EntityEquipmentSlot slot, ArmorMaterial mat, Item repairIngot) {
		super(mat, 3, slot);
		this.repairIngot = new ItemStack(repairIngot);
		setRegistryName(name);
		setUnlocalizedName(name);
		setMaxStackSize(1);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return "hwell:textures/armor/soulsteel_" + (slot == EntityEquipmentSlot.LEGS ? 2 : 1) + ".png";
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		if (Util.isValid(repairIngot) && net.minecraftforge.oredict.OreDictionary.itemMatches(repairIngot, repair, false))
			return true;
		return super.getIsRepairable(toRepair, repair);
	}

}
