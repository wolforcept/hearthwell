package wolforce.hearthwell.integration.jei;

import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.integration.jei.meta.IJeiIntegration;
import wolforce.hearthwell.items.ItemTokenOf;
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
		reg.register(IngredientFlare.INGREDIENT_TYPE, IngredientFlare.getAll(), new IngredientFlare.Helper(),
				new IngredientFlare.Renderer());
	}

	@Override
	protected void registerOther(IRecipeRegistration reg) {
		reg.addIngredientInfo(Util.listOf(new ItemStack(HearthWell.prayer_letter)), VanillaTypes.ITEM_STACK,
				new TranslatableComponent("description.hearthwell.prayer_letter"));
		reg.addIngredientInfo(Util.listOf(new ItemStack(HearthWell.flare_torch)), VanillaTypes.ITEM_STACK,
				new TranslatableComponent("description.hearthwell.flare_torch"));
		reg.addIngredientInfo(Util.listOf(new ItemStack(HearthWell.myst_dust)), VanillaTypes.ITEM_STACK,
				new TranslatableComponent("description.hearthwell.myst_dust"));
		reg.addIngredientInfo(Util.listOf(new ItemStack(HearthWell.myst_bush)), VanillaTypes.ITEM_STACK,
				new TranslatableComponent("description.hearthwell.myst_bush"));
		reg.addIngredientInfo(Util.listOf(new ItemStack(HearthWell.myst_bush_small)), VanillaTypes.ITEM_STACK,
				new TranslatableComponent("description.hearthwell.myst_bush_small"));
		reg.addIngredientInfo(Util.listOf(new ItemStack(HearthWell.crystal)), VanillaTypes.ITEM_STACK,
				new TranslatableComponent("description.hearthwell.crystal"));
		reg.addIngredientInfo(Util.listOf(new ItemStack(HearthWell.token_base)), VanillaTypes.ITEM_STACK,
				new TranslatableComponent("description.hearthwell.token_base"));
		for (ItemTokenOf tokenItem : HearthWell.getTokenItems()) {
			reg.addIngredientInfo(Util.listOf(new ItemStack(tokenItem)), VanillaTypes.ITEM_STACK,
					new TranslatableComponent("description.hearthwell.token_base"));
		}
	}

}
