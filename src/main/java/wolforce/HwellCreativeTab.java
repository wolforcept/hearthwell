package wolforce;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class HwellCreativeTab extends CreativeTabs {

	public HwellCreativeTab() {
		super("hwell");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Main.myst_dust);
	}
}