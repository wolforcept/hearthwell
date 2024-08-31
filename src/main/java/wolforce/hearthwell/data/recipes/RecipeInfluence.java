package wolforce.hearthwell.data.recipes;

import net.minecraft.world.level.block.Block;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.RecipeHearthWell;

import java.util.List;

public class RecipeInfluence extends RecipeHearthWell {

	private static final long serialVersionUID = HearthWell.VERSION.hashCode();

//	private final String[] inputs;
//	private final String output;
//	private transient Block realOutput;

	public RecipeInfluence(String name, String input, String output) {
		super(name, input, output);
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
//		realOutput = Util.tryGetBlock(output);
//	}

//	public boolean matchesInput(Block block) {
//		for (String input : inputs) {
//			if (UtilTags.tagHasBlock(input, block))
//				return true;
//			if (block == Util.tryGetBlock(input))
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
//	@Nullable
//	public Block getOuput() {
//		return realOutput;
//	}

}
