package wolforce;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HwellTab extends CreativeTabs {

	public HwellTab() {
		super(-1, "GodKeepers");
		// TODO Auto-generated constructor stub
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Main.myst_dust);
	}

}
