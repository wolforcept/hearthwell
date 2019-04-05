package wolforce.items;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.MyItem;
import wolforce.Util;

public class ItemMystFertilizer extends MyItem {

	private static final double SMALLNESS = 3;

	public ItemMystFertilizer(String name, String... lore) {
		super(name, lore);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX,
			float hitY, float hitZ) {

		ItemStack heldItem = player.getHeldItem(hand);
		if (!world.isRemote) {
			Block block = world.getBlockState(pos).getBlock();
			if (block == Blocks.SAPLING) {
				if (Util.isValid(heldItem) && heldItem.getItem() == Main.myst_fertilizer) {
					if (!player.capabilities.isCreativeMode)
						heldItem.shrink(1);

					if (Math.random() < .3 && canMakeTree(world, pos)) {
						int treeHeight = (int) (4 + Math.random() * 4);
						for (int i = 0; i < treeHeight; i++) {
							world.setBlockState(pos.add(0, i, 0),
									i == treeHeight - 1 ? Main.myst_leaves.getDefaultState() : Main.myst_log.getDefaultState());
							if (i > 1)
								makeLeavesPlane(world, pos.add(0, i, 0), Math.abs((double) (treeHeight - 2) / 2.0 - (double) (i - 2)));
						}
					}
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.PASS;
	}

	private void makeLeavesPlane(World world, BlockPos c, double smallness) {
		IBlockState leaves = Main.myst_leaves.getDefaultState();
		double radius = SMALLNESS - smallness;
		System.out.println(radius);
		for (int x = -4; x < 4; x++) {
			for (int z = -4; z < 4; z++) {
				BlockPos pos = c.add(x, 0, z);
				if (world.isAirBlock(pos) && Math.hypot(x, z) < radius)
					world.setBlockState(pos, leaves);
			}
		}
	}

	private boolean canMakeTree(World world, BlockPos pos) {
		return world.canSeeSky(pos) || !HwellConfig.other.mystSaplingRequireSky;
	}

}
