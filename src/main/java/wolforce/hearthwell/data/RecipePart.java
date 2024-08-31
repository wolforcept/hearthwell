package wolforce.hearthwell.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class RecipePart {

	int nr = 1;
	List<Item> items;
	List<Block> blocks;
	CompoundTag nbt;

	public RecipePart(String _string) {

		items = new LinkedList<>();
		blocks = new LinkedList<>();

		try {
			String[] parts = _string.split("\\*");
			String _stringSplit = parts[parts.length > 1 ? 1 : 0];

			if (parts.length > 1)
				try {
					nr = Integer.parseInt(parts[0]);
				} catch (NumberFormatException e) {
					nr = 1;
				}

			String[] _split = _stringSplit.split("\\|");

			for (String string : _split) {
				if (string.startsWith("#")) { // IS TAG

					ResourceLocation resource = new ResourceLocation(string.substring(1));

					try {
						@NotNull
						TagKey<Item> itemTag = ForgeRegistries.ITEMS.tags().createTagKey(resource);
						if (ForgeRegistries.ITEMS.tags().isKnownTagName(itemTag))
							items.addAll(ForgeRegistries.ITEMS.tags().getTag(itemTag).stream().toList());
					} catch (Exception e) {
						System.err.println("Failed to resolve item tag <" + resource + ">");
					}

					try {
						@NotNull
						TagKey<Block> blockTag = ForgeRegistries.BLOCKS.tags().createTagKey(resource);
						if (ForgeRegistries.BLOCKS.tags().isKnownTagName(blockTag))
							blocks.addAll(ForgeRegistries.BLOCKS.tags().getTag(blockTag).stream().toList());
					} catch (Exception e) {
						System.err.println("Failed to resolve block tag <" + resource + ">");
					}

				} else { // IS NOT TAG

					ResourceLocation resource = new ResourceLocation(string);

					if (ForgeRegistries.ITEMS.containsKey(resource))
						items.add(ForgeRegistries.ITEMS.getValue(resource));

					if (ForgeRegistries.BLOCKS.containsKey(resource))
						blocks.add(ForgeRegistries.BLOCKS.getValue(resource));
				}
			}
		} catch (Exception e) {
			System.err.println("Failed to load recipe part <" + _string + ">");
		}

		if (items.isEmpty() && !blocks.isEmpty()) {
			items = blocks.stream().map(x -> x.asItem()).toList();
		}
	}

	public ItemStack randomStack() {
		List<Item> items = getItems();
		if (items.isEmpty())
			return ItemStack.EMPTY;
		return new ItemStack(items.get((int) (Math.random() * items.size())), nr, nbt);
	}

	public Block randomBlock() {
		List<Block> blocks = getBlocks();
		if (blocks.isEmpty())
			return null;
		return blocks.get((int) (Math.random() * blocks.size()));
	}

	// get lists

	public List<Block> getBlocks() {
		return blocks.stream().toList();
	}

	public List<ItemStack> stacks() {
		List<Item> items = getItems();
		if (items != null)
			return items.stream().map(i -> new ItemStack(i, nr, nbt)).toList();
		return new LinkedList<ItemStack>();
	}

	public List<Item> getItems() {
		if (!items.isEmpty())
			return items.stream().toList();
		if (!blocks.isEmpty())
			return blocks.stream().map(b -> b.asItem()).toList();
		return new LinkedList<Item>();
	}

	@Override
	public String toString() {
		return "RecipePart: " + this.items + "," + this.blocks;
	}

}
