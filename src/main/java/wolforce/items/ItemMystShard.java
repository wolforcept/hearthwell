package wolforce.items;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
import wolforce.blocks.BlockCore;
import wolforce.blocks.BlockCore.CoreType;
import wolforce.tile.TileDismantler;

public class ItemMystShard extends MyItem {

	private static final double SMALLNESS = 3;

	public ItemMystShard(String name, String... lore) {
		super(name, lore);
	}

//	@Override
//	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
//			float hitX, float hitY, float hitZ) {
//
//		ItemStack heldItem = player.getHeldItem(hand);
//		if (!world.isRemote || !Util.isValid(heldItem))
//			return EnumActionResult.PASS;
//
//		if (aux(world, player, pos, Main.core_stone, Main.shard_c, BlockCore.CoreType.core_base)) {
//			setCore(world, pos, BlockCore.CoreType.core_fe);
//		} else
//			return EnumActionResult.PASS;
//		return EnumActionResult.SUCCESS;
//	}
//
//	private boolean aux(World world, EntityPlayer player, BlockPos pos, Block core, Item shard,
//			BlockCore.CoreType pretype) {
//		return player.getActiveItemStack().getItem() == shard && world.getBlockState(pos).getBlock() == core
//				&& world.getBlockState(pos).getProperties().get(BlockCore.TYPE).equals(pretype);
//	}
//
//	private void setCore(World world, BlockPos pos, CoreType coretype) {
//		world.setBlockState(pos, Main.core_stone.getDefaultState().withProperty(BlockCore.TYPE, coretype));
//	}

}
