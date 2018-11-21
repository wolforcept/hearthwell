package wolforce.items.tools;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSpade;

public class MyShovel extends ItemSpade {

	public  MyShovel(String name, int maxUses, int harvestLevel) {
		super(ToolMaterial.DIAMOND);
		setRegistryName(name);
		setUnlocalizedName(name);
		setMaxDamage(maxUses);
		setHarvestLevel("shovel", harvestLevel);
		setMaxStackSize(1);
	}
}
