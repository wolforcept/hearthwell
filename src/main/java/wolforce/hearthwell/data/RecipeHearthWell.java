package wolforce.hearthwell.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.entities.EntityHearthWell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class RecipeHearthWell implements Serializable {

	private static final long serialVersionUID = HearthWell.VERSION.hashCode();

	public transient MapNode mapNode;
	public final String recipeId;

	private final String input;
	private final String output;

	private transient List<RecipePart> inputParts;
	private transient List<RecipePart> outputParts;

	protected RecipeHearthWell(String recipeId, String input, String output) {
		this.recipeId = recipeId;
		this.input = input;
		this.output = output;
	}

	public void init(MapNode mapNode) {
		this.mapNode = mapNode;

		inputParts = new ArrayList<RecipePart>();
		outputParts = new ArrayList<RecipePart>();

		for (String inputString : this.input.split(","))
			inputParts.add(new RecipePart(inputString));

		for (String string : this.output.split(","))
			outputParts.add(new RecipePart(string));

//		System.out.println("Recipe Inited: " + this.getClass().getSimpleName() + " " + recipeId);
//		System.out.println(input + " => " + getInputStacks());
//		System.out.println(output + " => " + getOutputStacksFlat());
//		System.out.println("--//--");
//		initRecipe();
	}

	public boolean isUnlocked(EntityHearthWell hw) {
		return hw.isUnlocked(mapNode);
	}

	public boolean isUnlocked(CompoundTag unlockedNodes) {
		return unlockedNodes.contains(mapNode.hash() + "");
	}

//	protected abstract void initRecipe();

	// Inputs

	public List<ItemStack> getInputStack() {
		return inputParts.get(0).stacks();
	}

	public List<List<ItemStack>> getInputStacks() {
		return inputParts.stream().map(x -> x.stacks()).toList();
	}

	public List<ItemStack> getInputStacksFlat() {
		List<ItemStack> list = new LinkedList<>();
		inputParts.stream().forEach(x -> list.addAll(x.stacks()));
		return list;
	}

	public List<List<Block>> getInputBlocks() {
		return inputParts.stream().map(x -> x.getBlocks()).toList();
	}

	// Outputs

//	public ItemStack getOutputStack() {
//		return outputParts.get(0).stack();
//	}
//
//	public Block getOuputBlock() {
//		return outputParts.get(0).getBlock();
//	}

	public Block getRandomOuputBlock() {
		List<Block> blocks = getOutputBlocks();
		return blocks.get((int) (Math.random() * blocks.size()));
	}

	public List<ItemStack> getOutputStacksFlat() {
		List<ItemStack> list = new LinkedList<>();
		outputParts.stream().forEach(x -> list.addAll(x.stacks()));
		return list;
	}

	public List<ItemStack> getOutputStacksRandomSecondLayer() {
		return outputParts.stream().map(x -> x.randomStack()).toList();
	}

	public ItemStack getOutputStacksFlatRandom() {
		List<ItemStack> list = new LinkedList<>();
		outputParts.stream().forEach(x -> list.addAll(x.stacks()));
		return list.get((int) (list.size() * Math.random()));
	}

	public List<List<ItemStack>> getOutputStackLists() {
		return outputParts.stream().map(x -> x.stacks()).toList();
	}

	private List<Block> getOutputBlocks() {
		return outputParts.stream().map(x -> x.randomBlock()).toList();
	}

}
