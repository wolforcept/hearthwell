package hwell;

import java.util.LinkedList;

import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import mezz.jei.plugins.vanilla.ingredients.fluid.FluidStackHelper;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import wolforce.items.ItemGrindingWheel;
import wolforce.recipes.Irio;
import wolforce.recipes.RecipeCharger;
import wolforce.recipes.RecipeCrushing;
import wolforce.recipes.RecipeFreezer;
import wolforce.recipes.RecipeGrinding;
import wolforce.recipes.RecipeNetherPortal;
import wolforce.recipes.RecipePowerCrystal;
import wolforce.recipes.RecipePowerCrystal.ItemAndVals;
import wolforce.recipes.RecipePuller;
import wolforce.recipes.RecipeRepairingPaste;
import wolforce.recipes.RecipeSeedOfLife;
import wolforce.recipes.RecipeSeparator;
import wolforce.recipes.RecipeTube;

@ZenClass("mods.hwell")
@ZenRegister
public class CT {

	// private static LinkedList<ItemStack> getItems(IItemStack ing) {
	// for (IItemStack iis : ing.getItems()) {
	// IItemStack stack = iis.getDefinition().makeStack(iis.getMetadata());
	// }
	// return null;
	// }

	private static FluidStack is(ILiquidStack iLiquidStack) {
		return CraftTweakerMC.getLiquidStack(iLiquidStack);
	}

	private static ItemStack is(IItemStack iItemStack) {
		return CraftTweakerMC.getItemStack(iItemStack);
	}

	private static ItemStack[] is(IItemStack[] _outputs) {
		ItemStack[] outputs = new ItemStack[_outputs.length];
		for (int i = 0; i < outputs.length; i++) {
			outputs[i] = is(_outputs[i]);
		}
		return outputs;
	}

	@ZenDoc("Add a recipe for the Charger.")
	@ZenMethod
	public static void addChargerRecipe(IItemStack input, int power) {
		RecipeCharger.recipes.add(new RecipeCharger(is(input), ItemStack.EMPTY, power));
	}

	@ZenDoc("Add a recipe for the Charger that outputs a byproduct from the top.")
	@ZenMethod
	public static void addChargerRecipe(IItemStack input, IItemStack output, int power) {
		RecipeCharger.recipes.add(new RecipeCharger(is(input), is(output), power));
	}

	@ZenDoc("Add a recipe for the Crushing Block. The number of probabilities must be the same as the number of outputs. If the sum of the probabilities isn't 1, there is a chance for an output of nothing.")
	@ZenMethod
	public static void addCrushingBlockRecipe(IItemStack input, IItemStack[] outputs, double[] probs) {
		RecipeCrushing[] recipes = new RecipeCrushing[outputs.length];
		for (int i = 0; i < recipes.length; i++) {
			recipes[i] = new RecipeCrushing(is(outputs[i]), probs[i]);
		}
		RecipeCrushing.recipes.put(new Irio(is(input)), recipes);
	}

	@ZenDoc("Add a recipe for the Freezer. If there is more than one output, a random output will be selected.")
	@ZenMethod
	public static void addFreezerRecipe(ILiquidStack input, ItemStack[] outputs) {
		RecipeFreezer.recipes.add(new RecipeFreezer(is(input), outputs));
	}

	@ZenDoc("Add a recipe for the Grinder. The grinding wheel names are: \"iron\", \"diamond\" and \"crystal\".")
	@ZenMethod
	public static void addGrinderRecipe(IItemStack input, IItemStack output, String[] gearNames) {
		ItemGrindingWheel[] gears = new ItemGrindingWheel[gearNames.length];
		for (int i = 0; i < gears.length; i++) {
			ItemGrindingWheel wheel = RecipeGrinding.getWheelOf(gearNames[i]);
			if (wheel == null)
				throw new RuntimeException(gearNames[i] + " is not an id of a valid grinding wheel");
			gears[i] = wheel;
		}
		RecipeGrinding.recipes.put(new Irio(is(input)), new RecipeGrinding(is(output), gears));
	}

	@ZenDoc("Add a recipe for the Puller. The probability will be dependent on the sum of all probabilities. A filter must be provided.")
	@ZenMethod
	public static void addPullerRecipe(IItemStack output, double prob, IItemStack filter) {
		RecipePuller.recipes.add(new RecipePuller(is(output), prob, is(filter)));
	}

	@ZenDoc("Add a new item that has durability that the Repairing Paste can repair.")
	@ZenMethod
	public static void addRepairingPasteRecipe(IItemStack input) {
		RecipeRepairingPaste.items.add(is(input).getItem());
	}

	@ZenDoc("Add a new block for the Seed of Life to be right clicked on.")
	@ZenMethod
	public static void addSeedOfLifeRecipe(IItemStack input) {
		RecipeSeedOfLife.blocks.add(new Irio(is(input)));
	}

	@ZenDoc("Add a recipe for the Tube with a liquid output.")
	@ZenMethod
	public static void addTubeRecipe(IItemStack input, ILiquidStack output) {
		RecipeTube.put(is(input), is(output));
	}

	@ZenDoc("Add a recipe for the Tube with a solid block output.")
	@ZenMethod
	public static void addTubeRecipe(IItemStack input, IItemStack output) {
		RecipeTube.put(is(input), is(output));
	}

	@ZenDoc("Add a recipe for the Separator that outputs only two items.")
	@ZenMethod
	public static void addSeparatorSeparator(IItemStack input, IItemStack leftOutput, IItemStack rightOutput) {
		RecipeSeparator.recipes.put(new Irio(is(input)), new RecipeSeparator(is(leftOutput), is(rightOutput)));
	}

	@ZenDoc("Add a recipe for the Separator that outputs all three items.")
	@ZenMethod
	public static void addSeparatorSeparator(IItemStack input, IItemStack leftOutput, IItemStack rightOutput, IItemStack backOutput) {
		RecipeSeparator.recipes.put(new Irio(is(input)), new RecipeSeparator(is(leftOutput), is(rightOutput), is(backOutput)));
	}

	@ZenDoc("Add a new item to be used as a Nucleous in the Power Crystal Recipe.")
	@ZenMethod
	public static void addPowerCrystalNucleous(IItemStack item, String name, int power, int range, float purity) {
		RecipePowerCrystal.nucleousRecipes.add(new ItemAndVals(is(item), name, power, range, purity));
	}

	@ZenDoc("Add a new item to be used as a Relay in the Power Crystal Recipe.")
	@ZenMethod
	public static void addPowerCrystalRelay(IItemStack item, String name, int power, int range, float purity) {
		RecipePowerCrystal.relayRecipes.add(new ItemAndVals(is(item), name, power, range, purity));
	}

	@ZenDoc("Add a new item to be used as a Screen in the Power Crystal Recipe.")
	@ZenMethod
	public static void addPowerCrystalScreen(IItemStack item, String name, int power, int range, float purity) {
		RecipePowerCrystal.screenRecipes.add(new ItemAndVals(is(item), name, power, range, purity));
	}

	@ZenDoc("Add a new transformation recipe through the nether portal.")
	@ZenMethod
	public static void addNetherPortalRecipe(IItemStack input, IItemStack output) {
		RecipeNetherPortal.recipes.add(new RecipeNetherPortal(is(input), is(output)));
	}
}
