package wolforce.hwell.client.itemmeshes;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import wolforce.hwell.items.ItemPowerCrystal;
import wolforce.mechanics.Util;

public class ItemMeshPowerCrystal implements ItemMeshDefinition {

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack) {

		NBTTagCompound nbt = stack.serializeNBT();
		String prop1 = "power_nucleous=" + (int) nbt.getFloat(ItemPowerCrystal.nucleous);
		String prop2 = "power_relay=" + (int) nbt.getFloat(ItemPowerCrystal.relay);
		String prop3 = "power_screen=" + (int) nbt.getFloat(ItemPowerCrystal.screen);

		ModelResourceLocation model = new ModelResourceLocation(Util.res("power_crystal"), prop1 + "," + prop2 + "," + prop3);
		return model;
	}
}
