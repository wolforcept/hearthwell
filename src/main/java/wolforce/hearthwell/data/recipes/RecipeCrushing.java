package wolforce.hearthwell.data.recipes;

import net.minecraft.world.item.ItemStack;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.RecipeHearthWell;
import wolforce.hearthwell.util.Util;

import java.util.List;

public class RecipeCrushing extends RecipeHearthWell {

	private static final long serialVersionUID = HearthWell.VERSION.hashCode();

//	private final String input;
//	private final String[] outputs;

	public RecipeCrushing(String name, String input, String output) {
		super(name, input, output);
//		this.input = input;
//		this.outputs = outputs;
	}
//
//	@Override
//	protected void initRecipe() {
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
//	public List<Item> getAllOutputs() {
//		List<Item> outputItems = new ArrayList<>();
//		for (String output : outputs) {
//			List<Item> tag = Util.getTagItems(output);
//			if (tag != null && !tag.isEmpty())
//				outputItems.addAll(tag);
//			else {
//				Item item = Util.tryGetItem(output);
//				if (item != null)
//					outputItems.add(item);
//			}
//		}
//		return outputItems;
//	}

}
