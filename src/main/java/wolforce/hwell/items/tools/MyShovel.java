package wolforce.hwell.items.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import wolforce.mechanics.Util;

public class MyShovel extends ItemSpade {

	private ItemStack repairIngot;

	public MyShovel(String name, ToolMaterial mat, Item repairIngot) {
		super(mat);
		this.repairIngot = new ItemStack(repairIngot);
		Util.setReg(this, name);
		setMaxStackSize(1);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		if (Util.isValid(repairIngot)
				&& net.minecraftforge.oredict.OreDictionary.itemMatches(repairIngot, repair, false))
			return true;
		return super.getIsRepairable(toRepair, repair);
	}
}
