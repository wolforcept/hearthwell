package wolforce.hearthwell.items;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import wolforce.hearthwell.ConfigServer;

public class ItemTokenOf extends Item {

	public static String nbtTokenString = "token_name";
	private int i;

	public ItemTokenOf(Properties props, int i) {
		super(props);
		this.i = i;
	}

	@Override
	public Component getName(ItemStack stack) {
		List<? extends String> names = ConfigServer.getTokenNames();
		String name = names.size() > i ? names.get(i) : null;
		return new TranslatableComponent("item.hearthwell.token_of", name != null && name.length() > 0 ? name : "??");
	}

}
