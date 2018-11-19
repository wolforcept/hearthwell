package wolforce;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class HwellEventSubscriber {

	@SubscribeEvent
	public static void makeCrystalPassPortals(EntityTravelToDimensionEvent event) {

		if (!(event.getEntity() instanceof EntityItem))
			return;

		if (event.getDimension() == DimensionType.NETHER.getId()) {
			EntityItem entityItem = (EntityItem) event.getEntity();
			if (entityItem.getItem().getItem() == Main.crystal) {
				entityItem.setItem(new ItemStack(Main.crystal_nether, entityItem.getItem().getCount()));
			}
			if (entityItem.getItem().getItem() == Item.getItemFromBlock(Main.crystal_block)) {
				entityItem.setItem(new ItemStack(Main.crystal_nether_block, entityItem.getItem().getCount()));
			}
		}

		if (event.getDimension() == DimensionType.OVERWORLD.getId()) {
			EntityItem entityItem = (EntityItem) event.getEntity();
			if (entityItem.getItem().getItem() == Main.crystal_nether) {
				entityItem.setItem(new ItemStack(Main.crystal, entityItem.getItem().getCount()));
			}
			if (entityItem.getItem().getItem() == Item.getItemFromBlock(Main.crystal_nether_block)) {
				entityItem.setItem(new ItemStack(Main.crystal_block, entityItem.getItem().getCount()));
			}
		}
	}

}
