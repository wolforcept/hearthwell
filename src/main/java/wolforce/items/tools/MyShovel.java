package wolforce.items.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import wolforce.Util;

public class MyShovel extends ItemSpade {

	private ItemStack repairIngot;

	public MyShovel(String name, int maxUses, int harvestLevel, Item repairIngot) {
		super(ToolMaterial.DIAMOND);
		this.repairIngot = new ItemStack(repairIngot);
		setRegistryName(name);
		setUnlocalizedName(name);
		setMaxDamage(maxUses);
		setHarvestLevel("shovel", harvestLevel);
		setMaxStackSize(1);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		if (Util.isValid(repairIngot) && net.minecraftforge.oredict.OreDictionary.itemMatches(repairIngot, repair, false))
			return true;
		return super.getIsRepairable(toRepair, repair);
	}
}
