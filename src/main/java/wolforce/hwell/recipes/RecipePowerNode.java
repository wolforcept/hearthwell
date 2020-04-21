
package wolforce.hwell.recipes;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.hwell.HwellConfig;
import wolforce.hwell.Main;
import wolforce.hwell.entities.EntityPower;
import wolforce.mechanics.Util;

public class RecipePowerNode {

	private static HashMap<String, PowerNodeModifier> modifiers;
	private static HashMap<String, RecipePowerNode> recipesNucleous, recipesRelay, recipesScreen;

	public static void initRecipes() {

		modifiers = new HashMap<>();
		modifiers.put("dripping", DRIPPING);

		recipesNucleous = new HashMap<>();
		recipesRelay = new HashMap<>();
		recipesScreen = new HashMap<>();

		addNucleous(i(Items.BLAZE_ROD), 1000, 1, .5, c(1, 0, 0));

		addRelay(i(Main.smooth_azurite), 1000, 1, .5, c(1, 0, 0));

		addScreen(i(Main.crystal), 1000, 1, .5, c(1, 0, 0));
		addScreen(i(Main.gaseous_glass), 1000, 1, .5, c(1, 0, 0), "dripping");
	}

	//

	//

	//

	public static void addNucleous(ItemStack item, //
			int power, double range, double purity, int color, String... modifier) {

		add(recipesNucleous, new RecipePowerNode(item, power, (float) range, (float) purity, //
				color, modifier.length == 0 ? null : modifiers.get(modifier[0].toLowerCase())));
	}

	public static void addRelay(ItemStack item, //
			int power, double range, double purity, int color, String... modifier) {

		add(recipesNucleous, new RecipePowerNode(item, power, (float) range, (float) purity, //
				color, modifier.length == 0 ? null : modifiers.get(modifier[0].toLowerCase())));
	}

	public static void addScreen(ItemStack item, //
			int power, double range, double purity, int color, String... modifier) {

		add(recipesNucleous, new RecipePowerNode(item, power, (float) range, (float) purity, //
				color, modifier.length == 0 ? null : modifiers.get(modifier[0].toLowerCase())));
	}

	public static void removeNucleous(ItemStack input) {
		removeFrom(input, recipesNucleous);
	}

	public static void removeRelay(ItemStack input) {
		removeFrom(input, recipesRelay);
	}

	public static void removeScreen(ItemStack input) {
		removeFrom(input, recipesScreen);
	}

	public static RecipePowerNode getNucleous(ItemStack item) {
		return getFrom(item, recipesNucleous);
	}

	public static RecipePowerNode getRelay(ItemStack item) {
		return getFrom(item, recipesRelay);
	}

	public static RecipePowerNode getScreen(ItemStack item) {
		return getFrom(item, recipesScreen);
	}

	public static boolean isNucleous(ItemStack item) {
		return getFrom(item, recipesNucleous) != null;
	}

	public static boolean isRelay(ItemStack item) {
		return getFrom(item, recipesRelay) != null;
	}

	public static boolean isScreen(ItemStack item) {
		return getFrom(item, recipesScreen) != null;
	}

	//

	//

	// GENERIC METHODS

	private static void add(HashMap<String, RecipePowerNode> recipes, RecipePowerNode recipe) {
		recipes.put(recipe.item.getItem().getRegistryName().toString(), recipe);
	}

	private static void removeFrom(ItemStack input, HashMap<String, RecipePowerNode> recipes) {
		recipes.remove(input.getItem().getRegistryName().toString());
	}

	private static RecipePowerNode getFrom(ItemStack item, HashMap<String, RecipePowerNode> recipes) {
		for (RecipePowerNode recipe : recipes.values()) {
			if (Util.equalExceptAmount(item, recipe.item))
				return recipe;
		}
		return null;
	}

	//

	//

	// RECIPE CLASS IMPLEMENTATION

	public final ItemStack item;
	public final int power;
	public final float range, purity;
	public final int color;
	public final PowerNodeModifier modifier;

	public RecipePowerNode(ItemStack item, //
			int power, float range, float purity, int color, PowerNodeModifier modifier) {
		this.item = item;
		this.power = power;
		this.range = range;
		this.purity = purity;
		this.color = color;
		this.modifier = modifier;
	}

	// MODIFIERS

	public static abstract class PowerNodeModifier {
		public abstract void onUpdate(EntityPower entity, World world);
	}

	public static final PowerNodeModifier DRIPPING = new PowerNodeModifier() {
		public void onUpdate(EntityPower entity, World world) {
			if (!world.isRemote)
				return;

			int h = (int) entity.posY;
			while (h >= 0) {
				Block result = RecipeDripping
						.getResult(world.getBlockState(new BlockPos(entity.posX, h, entity.posZ)).getBlock());
				if (result != null) {
					if (Math.random() < HwellConfig.power.drippingChance)
						return;
				}
				h--;
			}
		}

	};

	// OTHER

	private static ItemStack i(Object item) {
		if (item instanceof Item)
			return new ItemStack((Item) item);
		if (item instanceof Block)
			return new ItemStack((Block) item);
		return ItemStack.EMPTY;
	}

	private static int c(int red, int green, int blue) {
		return c(red, green, blue, 255);
	}

	private static int c(int red, int green, int blue, int alpha) {
		return (red << 24) | (green << 16) | (blue << 8) | alpha;
	}
}
