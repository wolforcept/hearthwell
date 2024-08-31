package wolforce.hearthwell.bases;

import net.minecraft.world.item.Item;

public interface BlockItemProperties {

	public default Item.Properties getItemProperties() {
		return new Item.Properties();
	}

}
