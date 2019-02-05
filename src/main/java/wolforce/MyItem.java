package wolforce;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MyItem extends Item {

	private String[] lore;

	public MyItem(String name, String... lore) {
		setUnlocalizedName(name);
		setRegistryName(name);
		// setMaxStackSize(64);
		this.lore = lore;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.addAll(Arrays.asList(lore));
	}
}
