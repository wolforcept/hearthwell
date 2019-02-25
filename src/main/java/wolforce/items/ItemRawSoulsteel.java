package wolforce.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import wolforce.Util;

public class ItemRawSoulsteel extends ItemSimpleFoiled {

	private String[] description;

	public ItemRawSoulsteel(String name, String... description) {
		Util.setReg(this, name);
		this.description = description;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		for (String desc : description) {
			tooltip.add(desc);
		}
	}

}
