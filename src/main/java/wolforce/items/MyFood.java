package wolforce.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import wolforce.Util;

public class MyFood extends ItemFood {

	private int hunger;

	public MyFood(String name, int amount, float saturation) {
		this(name, amount, saturation, 0);
	}

	public MyFood(String name, int amount, float saturation, int hungerInSeconds) {
		super(amount, saturation, false);
		Util.setReg(this, name);
		this.hunger = hungerInSeconds;
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if (hunger > 0 && !worldIn.isRemote) {
			player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, hunger * 20));
		}
	}
}
