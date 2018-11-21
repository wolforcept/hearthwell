package wolforce.items.tools;

import net.minecraft.item.ItemAxe;

public class MyAxe extends ItemAxe {

	public MyAxe(String name, int maxUses, int harvestLevel) {
		super(ToolMaterial.DIAMOND);
		setRegistryName(name);
		setUnlocalizedName(name);
		setMaxDamage(maxUses);
		setHarvestLevel("axe", harvestLevel);
		setMaxStackSize(1);
	}
}
