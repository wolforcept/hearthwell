package wolforce.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import wolforce.base.MyItem;

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

}
