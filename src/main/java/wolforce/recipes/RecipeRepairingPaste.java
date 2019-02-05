package wolforce.recipes;

import net.minecraft.item.Item;

import static net.minecraft.init.Items.*;
import static wolforce.Main.*;

import java.util.Arrays;
import java.util.HashSet;

public class RecipeRepairingPaste {

	static HashSet<Item> items = new HashSet<Item>();

	public static void initRecipes() {
		for (Item item : new Item[] { //
				WOODEN_PICKAXE, WOODEN_AXE, WOODEN_HOE, WOODEN_SHOVEL, WOODEN_SHOVEL, //
				IRON_PICKAXE, IRON_AXE, IRON_HOE, IRON_SHOVEL, IRON_SHOVEL, //
				DIAMOND_PICKAXE, DIAMOND_AXE, DIAMOND_HOE, DIAMOND_SHOVEL, DIAMOND_SHOVEL, //
				GOLDEN_PICKAXE, GOLDEN_AXE, GOLDEN_HOE, GOLDEN_SHOVEL, GOLDEN_SHOVEL, //
				LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS, //
				IRON_HELMET, IRON_CHESTPLATE, IRON_LEGGINGS, IRON_BOOTS, //
				DIAMOND_HELMET, DIAMOND_CHESTPLATE, DIAMOND_LEGGINGS, DIAMOND_BOOTS, //
				GOLDEN_HELMET, GOLDEN_CHESTPLATE, GOLDEN_LEGGINGS, GOLDEN_BOOTS, //
				SHEARS, BOW, //
				repairing_paste, //
				obsidian_displacer, empowered_displacer, //
				soulsteel_pickaxe, soulsteel_axe, soulsteel_shovel, soulsteel_sword, soulsteel_dagger, //
				soulsteel_helmet, soulsteel_chest, soulsteel_legs, soulsteel_boots, //
				myst_dust_picker_au, myst_dust_picker_c, myst_dust_picker_ca, myst_dust_picker_fe, //
				myst_dust_picker_h, myst_dust_picker_o, myst_dust_picker_n, myst_dust_picker_p, //
				mystic_iron_pickaxe, mystic_iron_axe, mystic_iron_shovel, mystic_iron_sword, mystic_iron_dagger, //
				mystic_iron_helmet, mystic_iron_chest, mystic_iron_legs, mystic_iron_boots, //
		}) {
			items.add(item);
		}
	}

	public static boolean isRepairable(Item item) {
		return items.contains(item);
	}
}
