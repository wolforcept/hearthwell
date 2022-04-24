package wolforce.hearthwell.bases;

import net.minecraft.world.item.Item;
import wolforce.hearthwell.HearthWell;

public interface BlockItemProperties {

	public default Item.Properties getItemProperties() {
		return new Item.Properties().tab(HearthWell.group);
	}

}
