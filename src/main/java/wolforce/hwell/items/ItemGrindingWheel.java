package wolforce.hwell.items;

import net.minecraft.block.Block;
import wolforce.hwell.base.MyItem;

public class ItemGrindingWheel extends MyItem {

	public Block grinder;

	public ItemGrindingWheel(String... lore) {
		super(lore);
		setMaxStackSize(1);
	}

}
