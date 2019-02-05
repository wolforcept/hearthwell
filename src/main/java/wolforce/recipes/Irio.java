package wolforce.recipes;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * ITEM RECIPE INPUT OUPUT
 */
public class Irio {
	public final Item item;
	public final int meta;

	public Irio(ItemStack itemstack) {
		item = itemstack.getItem();
		meta = itemstack.getMetadata();
	}

	public Irio(Item _item, int _meta) {
		item = _item;
		meta = _meta;
	}

	public Irio(Item _item) {
		this(_item, -1);
	}

	public Irio(Block _block) {
		this(Item.getItemFromBlock(_block));
	}

	public Irio(Block _block, int _meta) {
		this(Item.getItemFromBlock(_block), _meta);
	}

	public Irio(IBlockState state) {
		this(state.getBlock(), state.getBlock().getMetaFromState(state));
	}

	@Override
	public boolean equals(Object obj2) {
		if (obj2 == null || !(obj2 instanceof Irio))
			return false;

		Irio irio2 = (Irio) obj2;
		if (meta == -1 || irio2.meta == -1)
			return item.equals(irio2.item);

		return item.equals(irio2.item) && meta == irio2.meta;

		// return item == ((Irio) obj).item && (meta == -1 || ((Irio) obj).meta == -1 ||
		// meta == ((Irio) obj).meta);
	}

	@Override
	public int hashCode() {
		if (meta == -1)
			return item.hashCode();
		return (item.hashCode() + "" + meta).hashCode();
	}

	public ItemStack stack() {
		return new ItemStack(item, 1, meta);
	}

	/**
	 * ONLY FOR IRIOS OF BLOCKS
	 */
	public Block getBlock() {
		return Block.getBlockFromItem(item);
	}

	/**
	 * ONLY FOR IRIOS OF BLOCKS
	 */
	public IBlockState getState() {
		return Block.getBlockFromItem(item).getStateFromMeta(meta);
	}

}
