package wolforce.hearthwell.data.recipes;

import net.minecraft.world.item.ItemStack;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.RecipeHearthWell;
import wolforce.hearthwell.util.Util;

import java.util.List;

public class RecipeBurstSeed extends RecipeHearthWell {

	private static final long serialVersionUID = HearthWell.VERSION.hashCode();

	public RecipeBurstSeed(String recipeId, String input) {
		super(recipeId, input, input);
//		this.input = input;
	}

//	@Override
//	protected void initRecipe() {
//		realInput = Util.tryGetItem(input);
//	}

	public boolean matches(ItemStack stack) {
		for (List<ItemStack> stacks : getInputStacks()) {
			for (ItemStack stack2 : stacks) {
				if (Util.equalExceptAmount(stack, stack2))
					return true;
			}
		}
		return false;
	}

//
//	public List<Item> getAllInputs() {
//		List<Item> inputItems = new ArrayList<>();
//		List<Item> tagItems = Util.getTagItems(input);
//		if (tagItems != null && !tagItems.isEmpty())
//			inputItems.addAll(tagItems);
//		else {
//			Item item = Util.tryGetItem(input);
//			if (item != null)
//				inputItems.add(item);
//		}
//		return inputItems;
//	}

}
