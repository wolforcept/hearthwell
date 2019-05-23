package wolforce.items.tools;

import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.BlockPickerHolder;

public class ItemDustPicker extends ItemTool {

	public final Item shard;

	public ItemDustPicker(String name, Item shard) {
		super(0, 0, ToolMaterial.IRON, new HashSet<Block>());
		this.shard = shard;
		Util.setReg(this, name);
		setMaxDamage(128);
	}

	@Override
	public boolean isRepairable() {
		return true;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return repair.getItem() == Main.heavy_ingot || repair.getItem() == Main.soulsteel_ingot;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		int id = Enchantment.getEnchantmentID(enchantment);
		return id == 32 || id == 34 || id == 35;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX,
			float hitY, float hitZ) {
		if (worldIn.getBlockState(pos).getBlock() instanceof BlockPickerHolder && player.isSneaking() && hand == EnumHand.MAIN_HAND)
			worldIn.getBlockState(pos).getBlock().onBlockActivated(worldIn, pos, worldIn.getBlockState(pos), player, hand, facing,
					hitX, hitY, hitZ);
		return EnumActionResult.SUCCESS;

		// return EnumActionResult.PASS;
	}
}
