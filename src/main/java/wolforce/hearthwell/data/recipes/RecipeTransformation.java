package wolforce.hearthwell.data.recipes;

import net.minecraft.world.level.block.Block;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.RecipeHearthWell;

import java.util.List;

public class RecipeTransformation extends RecipeHearthWell {

	private static final long serialVersionUID = HearthWell.VERSION.hashCode();

//	private final String[] inputs;
//	private final String output;
	public final String flareType;
//	private transient Item realOutput;
//	private transient int count;

	public RecipeTransformation(String recipeId, String flareType, String input, String output) {
		super(recipeId, input, output);
		this.flareType = flareType;
//		this.inputs = inputs;
//		this.output = output;
	}

	public boolean matches(Block block) {
		for (List<Block> blocks : getInputBlocks()) {
			for (Block block2 : blocks) {
				if (block == block2)
					return true;
			}
		}
		return false;
	}

//	@Override
//	protected void initRecipe() {
//		if (output.contains("*")) {
//			String[] parts = output.split("\\*");
//			realOutput = Util.tryGetItem(parts[0]);
//			count = Integer.parseInt(parts[1]);
//		} else {
//			realOutput = Util.tryGetItem(output);
//			count = 1;
//		}
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
//			List<Item> tag = Util.getTagItems(input);
//			if (tag != null && !tag.isEmpty())
//				inputItems.addAll(tag);
//			else {
//				Item item = Util.tryGetItem(input);
//				if (item != null)
//					inputItems.add(item);
//			}
//		}
//		return inputItems;
//	}
//
////	public Block getOuputBlock() {
////		return Block.getBlockFromItem(realOutput);
////	}
//
//	public ItemStack getOuputStack() {
//		return new ItemStack(realOutput, count);
//	}

}
