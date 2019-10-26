
package wolforce.hwell.items;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import wolforce.hwell.Main;
import wolforce.hwell.base.MyItem;

public class ItemCrystalBowl extends MyItem {

	public ItemCrystalBowl(String name, String... lore) {
		super(name, lore);
		setMaxStackSize(16);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

		if (raytraceresult == null) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);

		} else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);

		} else {
			BlockPos blockpos = raytraceresult.getBlockPos();

			if (!worldIn.isBlockModifiable(playerIn, blockpos)) {
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);

			} else {
				IBlockState iblockstate = worldIn.getBlockState(blockpos);
				Material material = iblockstate.getMaterial();

				if (material == Material.WATER && ((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
					playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1f, 1f);
					return new ActionResult<ItemStack>(EnumActionResult.SUCCESS,
							this.fillCrystalBowl(itemstack, playerIn, Main.crystal_bowl_water));
				} else {
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
				}

			}
		}
	}

	private ItemStack fillCrystalBowl(ItemStack crystal_bowl, EntityPlayer player, Item crystal_bowl_water) {
		crystal_bowl.shrink(1);

		if (crystal_bowl.isEmpty()) {
			return new ItemStack(crystal_bowl_water);
		} else {
			if (!player.inventory.addItemStackToInventory(new ItemStack(crystal_bowl_water))) {
				player.dropItem(new ItemStack(crystal_bowl_water), false);
			}
			return crystal_bowl;

		}
	}

}
