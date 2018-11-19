package wolforce.items;

import net.minecraft.block.Block;
import wolforce.MyItem;

public class ItemGrindingWheel extends MyItem {

	public Block grinder;

	public ItemGrindingWheel(String name, String... lore) {
		super(name, lore);
		setMaxStackSize(1);
	}

}
