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
import wolforce.blocks.tile.TileDismantler;

public class ItemMystDust extends MyItem {

	private final static HashMap<Block, Block> recipes = new HashMap<>();
	private static final double SMALLNESS = 3;

	public static void initRecipes() {
		recipes.put(Blocks.GRASS, Main.myst_grass);
		recipes.put(Blocks.LAPIS_ORE, Main.asul_block);
	}

	public ItemMystDust(String name, String... lore) {
		super(name, lore);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
			float hitX, float hitY, float hitZ) {

		ItemStack heldItem = player.getHeldItem(hand);
		if (!world.isRemote) {
			Block block = world.getBlockState(pos).getBlock();
			if (block != null) {
				Block blockResult = recipes.get(block);
				if (blockResult != null) {
					if (Util.isValid(heldItem) && heldItem.getItem() == Main.myst_dust) {
						if (!player.capabilities.isCreativeMode)
							heldItem.shrink(1);
						world.setBlockState(pos, blockResult.getDefaultState());
						return EnumActionResult.SUCCESS;
					}
				}
			}
		}
		return EnumActionResult.PASS;
	}

}
