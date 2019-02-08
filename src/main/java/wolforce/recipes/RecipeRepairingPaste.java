package wolforce.recipes;

import static net.minecraft.init.Items.BOW;
import static net.minecraft.init.Items.DIAMOND_AXE;
import static net.minecraft.init.Items.DIAMOND_BOOTS;
import static net.minecraft.init.Items.DIAMOND_CHESTPLATE;
import static net.minecraft.init.Items.DIAMOND_HELMET;
import static net.minecraft.init.Items.DIAMOND_HOE;
import static net.minecraft.init.Items.DIAMOND_LEGGINGS;
import static net.minecraft.init.Items.DIAMOND_PICKAXE;
import static net.minecraft.init.Items.DIAMOND_SHOVEL;
import static net.minecraft.init.Items.GOLDEN_AXE;
import static net.minecraft.init.Items.GOLDEN_BOOTS;
import static net.minecraft.init.Items.GOLDEN_CHESTPLATE;
import static net.minecraft.init.Items.GOLDEN_HELMET;
import static net.minecraft.init.Items.GOLDEN_HOE;
import static net.minecraft.init.Items.GOLDEN_LEGGINGS;
import static net.minecraft.init.Items.GOLDEN_PICKAXE;
import static net.minecraft.init.Items.GOLDEN_SHOVEL;
import static net.minecraft.init.Items.IRON_AXE;
import static net.minecraft.init.Items.IRON_BOOTS;
import static net.minecraft.init.Items.IRON_CHESTPLATE;
import static net.minecraft.init.Items.IRON_HELMET;
import static net.minecraft.init.Items.IRON_HOE;
import static net.minecraft.init.Items.IRON_LEGGINGS;
import static net.minecraft.init.Items.IRON_PICKAXE;
import static net.minecraft.init.Items.IRON_SHOVEL;
import static net.minecraft.init.Items.LEATHER_BOOTS;
import static net.minecraft.init.Items.LEATHER_CHESTPLATE;
import static net.minecraft.init.Items.LEATHER_HELMET;
import static net.minecraft.init.Items.LEATHER_LEGGINGS;
import static net.minecraft.init.Items.SHEARS;
import static net.minecraft.init.Items.WOODEN_AXE;
import static net.minecraft.init.Items.WOODEN_HOE;
import static net.minecraft.init.Items.WOODEN_PICKAXE;
import static net.minecraft.init.Items.WOODEN_SHOVEL;
import static wolforce.Main.empowered_displacer;
import static wolforce.Main.myst_dust_picker_au;
import static wolforce.Main.myst_dust_picker_c;
import static wolforce.Main.myst_dust_picker_ca;
import static wolforce.Main.myst_dust_picker_fe;
import static wolforce.Main.myst_dust_picker_h;
import static wolforce.Main.myst_dust_picker_n;
import static wolforce.Main.myst_dust_picker_o;
import static wolforce.Main.myst_dust_picker_p;
import static wolforce.Main.mystic_iron_axe;
import static wolforce.Main.mystic_iron_boots;
import static wolforce.Main.mystic_iron_chest;
import static wolforce.Main.mystic_iron_dagger;
import static wolforce.Main.mystic_iron_helmet;
import static wolforce.Main.mystic_iron_legs;
import static wolforce.Main.mystic_iron_pickaxe;
import static wolforce.Main.mystic_iron_shovel;
import static wolforce.Main.mystic_iron_sword;
import static wolforce.Main.obsidian_displacer;
import static wolforce.Main.repairing_paste;
import static wolforce.Main.soulsteel_axe;
import static wolforce.Main.soulsteel_boots;
import static wolforce.Main.soulsteel_chest;
import static wolforce.Main.soulsteel_dagger;
import static wolforce.Main.soulsteel_helmet;
import static wolforce.Main.soulsteel_legs;
import static wolforce.Main.soulsteel_pickaxe;
import static wolforce.Main.soulsteel_shovel;
import static wolforce.Main.soulsteel_sword;

import java.util.HashSet;

import net.minecraft.item.Item;

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
