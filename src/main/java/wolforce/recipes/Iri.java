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
		meta = -1;
	}

	public Iri(Block _block) {
		item = Item.getItemFromBlock(_block);
		meta = -1;
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
		if (obj == null || !(obj instanceof Iri))
			return false;
		return item == ((Iri) obj).item && (meta == -1 || ((Iri) obj).meta == -1 || meta == ((Iri) obj).meta);
	}

	@Override
	public int hashCode() {
		if (meta == -1)
			return item.hashCode();
		return (item.hashCode() + "" + meta).hashCode();
	}

	public Block getBlock() {
		return Block.getBlockFromItem(item);
	}

}
