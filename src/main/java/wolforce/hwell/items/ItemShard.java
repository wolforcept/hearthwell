
package wolforce.hwell.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import wolforce.hwell.Main;
import wolforce.hwell.base.MyItem;

public class ItemShard extends MyItem {

	public ItemShard(String name) {
		super(name);
	}

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		Entity e = super.createEntity(world, location, itemstack);
		if (e instanceof EntityItem)
			((EntityItem) e).setNoDespawn();
		return e;
	}

	public static ItemShard getFromString(String shard) {
		switch (shard) {
		case "shard_au":
			return Main.shard_au;
		case "shard_c":
			return Main.shard_c;
		case "shard_ca":
			return Main.shard_ca;
		case "shard_fe":
			return Main.shard_fe;
		case "shard_h":
			return Main.shard_h;
		case "shard_n":
			return Main.shard_n;
		case "shard_o":
			return Main.shard_o;
		case "shard_p":
			return Main.shard_p;
		}
		return null;
	}

}
