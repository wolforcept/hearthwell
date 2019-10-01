package wolforce.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.blocks.tile.TileBranch;

public class ItemBranch extends ItemBlock {

	public static final String branch_time = "branch_time";
	// private String[] lore;

	public ItemBranch(Block block, String name, String... lore) {
		super(block);
		// this.lore = lore;
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side,
			float hitX, float hitY, float hitZ, IBlockState newState) {
		boolean ret = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
		if (ret) {
			TileBranch tile = (TileBranch) world.getTileEntity(pos);
			tile.time = ItemBranch.getTime(stack.getTagCompound());
		}
		return ret;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (hasLifeTag(stack)) {
			long life = getLife(stack);
			if (life > 0)
				tooltip.add("Liveliness remaining: " + (int) (life / 100));
			else
				tooltip.add("Lifeless");
		} else
			tooltip.add("Lifeless");
	}

	private static boolean hasLifeTag(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey(branch_time);
	}

	private static boolean hasLifeTag(NBTTagCompound nbt) {
		return nbt != null && nbt.hasKey(branch_time);
	}

	public static long getLife(ItemStack stack) {
		return getLife(stack.getTagCompound());
	}

	private static long getLife(NBTTagCompound nbt) {
		if (hasLifeTag(nbt))
			return getLife(nbt.getLong(branch_time));
		return -1;
	}

	public static long getLife(long time) {
		return time + 100000 - System.currentTimeMillis();
	}

	//

	//

	//

	//

	//

	//

	//

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		NBTTagCompound nbt = stack.getTagCompound();
		if (hasLifeTag(nbt) && getLife(nbt) <= 0)
			stack.setTagCompound(null);
	}

	// @Override
	// public boolean getShareTag() {
	// return true;
	// }

	@Override
	public boolean hasEffect(ItemStack stack) {
		return getLife(stack) > 0;
	}

	// @Override
	// public boolean updateItemStackNBT(NBTTagCompound nbt) {
	// if (hasLifeTag(nbt) && getLife(nbt) <= 0)
	// nbt.removeTag(branch_time);
	// super.updateItemStackNBT(nbt);
	// return true;
	// }

	public static ItemStack createNewStack(int n) {
		ItemStack stack = new ItemStack(Main.branch, n);
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		setNBT(stack.getTagCompound(), System.currentTimeMillis());
		return stack;
	}

	public static void setNBT(NBTTagCompound nbt, long time) {
		nbt.setLong(ItemBranch.branch_time, time);

		// if (hasLifeTag(nbt) && getLife(nbt) <= 0)
		// nbt.removeTag(branch_time);
	}

	public static long getTime(NBTTagCompound nbtTagCompound) {
		if (nbtTagCompound != null && nbtTagCompound.hasKey(branch_time))
			return nbtTagCompound.getLong(branch_time);
		return -1;
	}

}
