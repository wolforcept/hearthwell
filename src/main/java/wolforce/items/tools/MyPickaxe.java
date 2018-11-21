package wolforce.items.tools;

import net.minecraft.item.ItemPickaxe;

public class MyPickaxe extends ItemPickaxe {

	public MyPickaxe(String name, int maxUses, int harvestLevel) {
		super(ToolMaterial.DIAMOND);
		setRegistryName(name);
		setUnlocalizedName(name);
		setMaxDamage(maxUses);
		setHarvestLevel("pickaxe", harvestLevel);
		setMaxStackSize(1);
	}
}
