package wolforce.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.MyItem;
import wolforce.blocks.BlockLightCollector;

public class ItemLockedLight extends MyItem {

	public ItemLockedLight(String name, String... lore) {
		super(name, lore);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX,
			float hitY, float hitZ) {
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() == Main.light_collector) {
			if (state.getValue(BlockLightCollector.CHARGE) != 3) {
				world.setBlockState(pos, Main.light_collector.getDefaultState().withProperty(BlockLightCollector.CHARGE, 3));
				player.getHeldItem(hand).shrink(1);
			}
		}
		return EnumActionResult.PASS;
	}

}
