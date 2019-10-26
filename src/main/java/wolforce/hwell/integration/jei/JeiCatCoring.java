package wolforce.hwell.integration.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import wolforce.hwell.Hwell;
import wolforce.hwell.Main;
import wolforce.hwell.blocks.BlockCore;
import wolforce.hwell.items.ItemShard;
import wolforce.hwell.recipes.RecipeCoring;
import wolforce.mechanics.Util;
import wolforce.mechanics.api.integration.JeiCat;

public class JeiCatCoring extends JeiCat {

	// public static String makeCustomCoreUID(BlockCore core) {
	// return Hwell.MODID + ".coring." + core.getRegistryName().getResourcePath();
	// }

	private BlockCore core;

	public JeiCatCoring(BlockCore core) {
		super(Hwell.MODID, core.getLocalizedName() + " Recipes", "coring", 62, 90, core);
		this.core = core;
	}

	@Override
	public String getUid() {
		return core.getLocalizedName() + "_" + super.getUid();
	}

	// @Override
	// public String getUid() {
	// if (core == Main.core_stone)
	// return UID_CORING_STONE;
	// if (core == Main.core_anima)
	// return UID_CORING_ANIMA;
	// if (core == Main.core_heat)
	// return UID_CORING_HEAT;
	// if (core == Main.core_green)
	// return UID_CORING_GREEN;
	// if (core == Main.core_sentient)
	// return UID_CORING_SENTIENT;
	// for (Entry<String, BlockCore> entry : Main.custom_cores.entrySet()) {
	// if (entry.getValue().equals(core))
	// return makeCustomCoreUID(core);
	// }
	// return "";
	// }

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(0, false, 22, 64); // output
		stacks.init(1, true, 8, 8); // core
		stacks.init(2, true, 36, 8); // shard
		stacks.init(3, true, 8, 34); // consumes
		stacks.set(ingredients);
	}

	//

	//

	//

	//

	//

	@Override
	public LinkedList<IRecipeWrapper> getAllRecipes() {
		LinkedList<IRecipeWrapper> list = new LinkedList<IRecipeWrapper>();
		for (Entry<ItemShard, RecipeCoring> entry : RecipeCoring.recipes.get(core).entrySet()) {
			final ItemShard shard = entry.getKey();
			final ItemStack[] consumes = entry.getValue().consumes;
			final ItemStack[] possibleOutputs = entry.getValue().possibleOutputs;
			list.add(new IRecipeWrapper() {

				@Override
				public void getIngredients(IIngredients ingredients) {

					List<List<ItemStack>> ins = new ArrayList<>(3);
					ins.add(Util.<ItemStack>listOfOne(new ItemStack(core)));
					ins.add(Util.<ItemStack>listOfOne(new ItemStack(shard)));
					LinkedList<ItemStack> consumesList = new LinkedList<>(Arrays.asList(consumes));
					boolean hasAir = false;
					for (ItemStack itemStack : consumes) {
						if (!Util.isValid(itemStack))
							hasAir = true;
					}
					if (hasAir)
						consumesList.add(new ItemStack(Main.empty));
					ins.add(consumesList);

					ingredients.setInputLists(VanillaTypes.ITEM, ins);
					ingredients.setOutputLists(VanillaTypes.ITEM, Util.<List<ItemStack>>listOfOne(//
							Arrays.asList(possibleOutputs)//
					));
				}
			});
		}
		// // SPECIAL CASE
		// if (core == Main.core_green)
		// list.add(new JeiRecCoring(core, Main.shard_p, new Irio[] { new
		// Irio(Main.fertilizer_block) },
		// new Irio[] { new Irio(BROWN_MUSHROOM_BLOCK), new Irio(RED_MUSHROOM_BLOCK)
		// }));
		return list;
	}

}
