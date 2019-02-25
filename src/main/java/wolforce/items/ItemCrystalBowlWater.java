package wolforce.items;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.Util;

public class ItemCrystalBowlWater extends ItemBucketMilk {

	private String[] lore;

	public ItemCrystalBowlWater(String name, String... lore) {
		Util.setReg(this, name);
		this.lore = lore;
		this.setMaxStackSize(16);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.addAll(Arrays.asList(lore));
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {

		if (!world.isRemote)
			entityLiving.addPotionEffect(new PotionEffect(MobEffects.HASTE, 3600/* 3 minutes */, 0));
		if (entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).capabilities.isCreativeMode) {
			stack.shrink(1);
			if (!stack.isEmpty())
				if (!((EntityPlayer) entityLiving).inventory.addItemStackToInventory(new ItemStack(Main.crystal_bowl))) {
					((EntityPlayer) entityLiving).dropItem(new ItemStack(Main.crystal_bowl), false);
				}
		}
		return stack.isEmpty() ? new ItemStack(Main.crystal_bowl) : stack;
	}

}
