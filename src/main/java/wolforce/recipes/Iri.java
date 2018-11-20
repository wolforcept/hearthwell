package wolforce.recipes;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

/**
 * ITEM RECIPE INPUT
 */
public class Iri {
	Item item;
	int meta;

	/**
	 * ITEM RECIPE INPUT
	 */
	public Iri(Item _item) {
		item = _item;
		meta = 0;
	}

	public Iri(Block _block) {
		item = Item.getItemFromBlock(_block);
		meta = 0;
	}

	public Iri(Item _item, int _meta) {
		item = _item;
		meta = _meta;
	}

	public Iri(Block _block, int _meta) {
		item = Item.getItemFromBlock(_block);
		meta = _meta;
	}

	public Iri(IBlockState state) {
		this(state.getBlock(), state.getBlock().getMetaFromState(state));
	}

	@Override
	public boolean equals(Object obj) {
		return item == ((Iri) obj).item && meta == ((Iri) obj).meta;
	}

	@Override
	public int hashCode() {
		return (item.hashCode() + "" + meta).hashCode();
	}

	public Block getBlock() {
		return Block.getBlockFromItem(item);
	}

}
