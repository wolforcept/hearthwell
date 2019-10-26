package wolforce.hwell;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HwellTab extends CreativeTabs {

	public HwellTab() {
		super(-1, "Hearth Well");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Main.myst_dust);
	}

}
