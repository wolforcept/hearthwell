package wolforce.items.tools;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemDustPicker extends ItemTool {

	public final Item shard;

	public ItemDustPicker(String name, Item shard) {
		super(0, 0, ToolMaterial.IRON, new HashSet<Block>());
		this.shard = shard;
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxDamage(64);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		int id = Enchantment.getEnchantmentID(enchantment);
		return id == 32 || id == 34 || id == 35;
	}
}
