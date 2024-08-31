package wolforce.hearthwell.data.recipes;

import net.minecraft.world.item.ItemStack;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.RecipeHearthWell;
import wolforce.hearthwell.util.Util;

import java.util.List;

public class RecipeHandItem extends RecipeHearthWell {

	private static final long serialVersionUID = HearthWell.VERSION.hashCode();

//	private final String[] inputs;
//	public final String output;
	public final String flareType;
//	private transient Item realOutput;

	public RecipeHandItem(String name, String flareType, String input, String output) {
		super(name, input, output);
		this.flareType = flareType;
//		this.input = input;
//		this.output = output;
	}

	public boolean matches(ItemStack stack) {
		for (List<ItemStack> stacks : getInputStacks()) {
			for (ItemStack stack2 : stacks) {
				if (Util.equalExceptAmount(stack, stack2))
					return true;
			}
		}
		return false;
	}

//	@Override
//	protected void initRecipe() {
//		realOutput = Util.tryGetItem(output);
//	}

//	@SuppressWarnings("deprecation")
//	public boolean matchesInput(Block block) {
//		return matchesAnyInput(Item.byBlock(block));
//	}
//
//	public boolean matchesAnyInput(Item item) {
//		for (String input : inputs) {
//			if (UtilTags.tagHasItem(input, item))
//				return true;
//			if (item == Util.tryGetItem(input))
//				return true;
//		}
//		return false;
//	}
//
//	public List<Item> getAllInputs() {
//		List<Item> inputItems = new ArrayList<>();
//		for (String input : inputs) {
//			List<Item> tagItems = Util.getTagItems(input);
//			if (tagItems != null && !tagItems.isEmpty())
//				inputItems.addAll(tagItems);
//			else {
//				Item item = Util.tryGetItem(input);
//				if (item != null)
//					inputItems.add(item);
//			}
//		}
//		return inputItems;
//	}
//
//	public Item getOuput() {
//		return realOutput;
//	}

}
