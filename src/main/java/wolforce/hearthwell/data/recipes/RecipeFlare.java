package wolforce.hearthwell.data.recipes;

import net.minecraft.world.item.ItemStack;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.RecipeHearthWell;
import wolforce.hearthwell.util.Util;

import java.util.LinkedList;
import java.util.List;

public class RecipeFlare extends RecipeHearthWell {

	private static final long serialVersionUID = HearthWell.VERSION.hashCode();

//	private final String[] inputs;
	public final String color_string;
	public final String flare_name;
	public final int uses;

	public transient int color;
	public transient String flareType;
//	private transient Object[] realInputs;

	public RecipeFlare(String recipeId, String flareName, String colorString, String input) {
		this(recipeId, flareName, colorString, 1, input);
	}

	public RecipeFlare(String recipeId, String flareName, String colorString, int uses, String input) {
		super(recipeId, input, "");
		this.flareType = recipeId;
		this.flare_name = flareName;
		this.color_string = colorString;
		this.uses = uses;
//		this.inputs = inputs;
		color = Integer.parseInt(color_string, 16);
	}

	public boolean matchesAllInputs(List<ItemStack> _available) {

		List<List<ItemStack>> _inputs = getInputStacks();

		List<List<ItemStack>> ingredients = new LinkedList<>();
		for (List<ItemStack> list : _inputs) {
			ingredients.add(new LinkedList<ItemStack>(list));
		}

		List<ItemStack> available = new LinkedList<>();
		for (ItemStack availableStack : _available) {
			available.add(availableStack.copy());
		}

		for (List<ItemStack> ingred : ingredients) {
			if (!findSuitableAndRemove(available, ingred))
				return false;
		}
		return true;
	}

	private boolean findSuitableAndRemove(List<ItemStack> available, List<ItemStack> ingredient) {

		for (ItemStack ingred : ingredient) {

			ItemStack searchNeededStack = Util.stackListFind_moreOrEqualNr(ingred, available);
			if (searchNeededStack != null) {
				searchNeededStack.shrink(ingred.getCount());
				return true;
			}
		}
		return false;

//		for (Iterator<ItemStack> it = available.iterator(); it.hasNext();) {
//			ItemStack availableStack = (ItemStack) it.next();
//			ItemStack searchNeededStack = Util.stackListFind_moreOrEqualNr(availableStack, ingredient);
//			if (searchNeededStack != null) {
//				availableStack.shrink(searchNeededStack.getCount());
//				return true;
//			}
//		}
//		return false;
	}

//	@Override
//	protected void initRecipe() {
////		realInputs = new Object[inputs.length];
//		for (int i = 0; i < inputs.length; i++) {
//			List<Item> tagItems = UtilTags.getTagItems(inputs[i]);
//			if (tagItems != null && !tagItems.isEmpty())
//				realInputs[i] = tagItems;
//			else
//				realInputs[i] = Util.tryGetItem(inputs[i]);
//		}
//	}

//	public boolean matches(List<ItemStack> list) {
//		List<ItemStack> clone = list.stream().map(s -> s.copy()).collect(toList());
//		return tryRemoveInputsFrom(clone);
////		int maxMatches = 0;
////		while (tryRemoveInputsFrom(clone))
////			maxMatches++;
////		int nrOfMystDust = 0;
////		for (ItemStack itemStack : clone) {
////			if (itemStack.getItem() == HearthWell.myst_dust)
////				nrOfMystDust += itemStack.getCount();
////		}
////		return maxMatches;
//	}

//	@SuppressWarnings("unchecked")
//	private boolean tryRemoveInputsFrom(List<ItemStack> list) {
//		if (list.size() < realInputs.length)
//			return false;
//		for (Object obj : realInputs) {
//			if (obj instanceof List) {
//				if (!tryRemoveAnyInputFrom((List<Item>) obj, list))
//					return false;
//			} else if (!tryRemoveInputFrom((Item) obj, list))
//				return false;
//		}
//		return true;
//	}
//
//	private boolean tryRemoveAnyInputFrom(List<Item> inputItems, List<ItemStack> list) {
//		for (ItemStack itemStack : list) {
//			if (inputItems.contains(itemStack.getItem())) {
//				itemStack.shrink(1);
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private boolean tryRemoveInputFrom(Item item, List<ItemStack> list) {
//		for (ItemStack itemStack : list) {
//			if (itemStack.getItem() == item) {
//				itemStack.shrink(1);
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public List<List<Item>> getAllInputs() {
//		List<List<Item>> inputLists = new ArrayList<>();
//
//		for (String input : inputs) {
//			List<Item> items = UtilTags.getTagItems(input);
//			if (items != null)
//				inputLists.add(items);
//			else {
//				Item item = Util.tryGetItem(input);
//				if (item != null)
//					inputLists.add(Util.listOf(item));
//			}
//		}
//		return inputLists;
//	}

}
