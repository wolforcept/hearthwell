package wolforce.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import wolforce.Main;

public class RecipeCharger {

	public static int getResult(ItemStack stack) {
		if (stack.getItem() == Main.myst_dust)
			return 10;
		if (stack.getItem() == Items.LAVA_BUCKET)
			return 100;
		return 0;
	}

	public static ItemStack getSpit(ItemStack stack) {
		if (stack.getItem() == Items.LAVA_BUCKET)
			return new ItemStack(Items.BUCKET);
		if (stack.getItem() == Main.myst_dust)
			return new ItemStack(Main.dust);
		return null;
	}

}
