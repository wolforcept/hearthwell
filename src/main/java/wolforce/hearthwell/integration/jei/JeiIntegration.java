package wolforce.hearthwell.integration.jei;

import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.integration.jei.meta.IJeiIntegration;
import wolforce.hearthwell.util.Util;

@JeiPlugin
public class JeiIntegration extends IJeiIntegration {

	@Override
	public ResourceLocation getPluginUid() {
		return Util.res(HearthWell.MODID, "jei_plugin");
	}

	@Override
	public void registerCategories() {

		add(new JeiCatTransformation());
		add(new JeiCatBursting());
		add(new JeiCatInfluence());
		add(new JeiCatFlare());
		add(new JeiCatHandItem());
		add(new JeiCatCrushing());
	}

	@Override
	public void registerIngredients(IModIngredientRegistration reg) {
		reg.register(IngredientFlare.INGREDIENT_TYPE, IngredientFlare.getAll(), new IngredientFlare.Helper(), new IngredientFlare.Renderer());
	}

	@Override
	protected void registerOther(IRecipeRegistration reg) {
		reg.addIngredientInfo(Util.listOf(new ItemStack(HearthWell.prayer_letter)), VanillaTypes.ITEM_STACK,
				new TextComponent("A prayer to the Gods, requesting help in a time of need. When thrown, this item will summon a Hearth Well."));
		reg.addIngredientInfo(Util.listOf(new ItemStack(HearthWell.myst_dust)), VanillaTypes.ITEM_STACK,
				new TextComponent("Obtained by breaking the Mysterious Bushes that grow on top of Mysterious Grass."));
		reg.addIngredientInfo(Util.listOf(new ItemStack(HearthWell.myst_bush)), VanillaTypes.ITEM_STACK,
				new TextComponent("These grow on top of Mysterious Grass. When broken, they drop Mysterious Dust"));
		reg.addIngredientInfo(Util.listOf(new ItemStack(HearthWell.myst_bush_small)), VanillaTypes.ITEM_STACK,
				new TextComponent("These grow on top of Mysterious Grass. When broken, they may drop Mysterious Dust"));
		reg.addIngredientInfo(Util.listOf(new ItemStack(HearthWell.crystal)), VanillaTypes.ITEM_STACK,
				new TextComponent("Obtained by breaking the Crystal Ore that may form near the HearthWell."));
	}

}
