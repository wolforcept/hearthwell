package wolforce.items;

import java.util.HashMap;
import java.util.List;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;
import wolforce.Main;
import wolforce.MyItem;
import wolforce.Util;
import wolforce.blocks.tile.TileDismantler;

public class ItemCrystalBowlWater extends ItemBucketMilk {

	private String[] lore;

	public ItemCrystalBowlWater(String name, String... lore) {
		setUnlocalizedName(name);
		setRegistryName(name);
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
				if (!((EntityPlayer) entityLiving).inventory
						.addItemStackToInventory(new ItemStack(Main.crystal_bowl))) {
					((EntityPlayer) entityLiving).dropItem(new ItemStack(Main.crystal_bowl), false);
				}
		}
		return stack.isEmpty() ? new ItemStack(Main.crystal_bowl) : stack;
	}

}
