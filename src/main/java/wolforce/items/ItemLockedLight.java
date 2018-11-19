package wolforce.items;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.MyItem;
import wolforce.Util;
import wolforce.tile.TileDismantler;

public class ItemLockedLight extends MyItem {

	public ItemLockedLight(String name, String... lore) {
		super(name, lore);
	}

	// @Override
	// public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos
	// pos, EnumHand hand, EnumFacing facing, float hitX, float hitY,
	// float hitZ) {
	//
	// return EnumActionResult.PASS;
	// }

}
