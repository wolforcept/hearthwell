package wolforce.recipes;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.Util;
import wolforce.items.ItemPowerCrystal;

public class RecipePowerCrystal implements IRecipe {

	public static class ItemAndVals {
		public ItemStack stack;
		public int power;
		public int range;
		public float purity;
		public String name;

		// public ItemAndVals(Item item, int meta, String name, int power, int range,
		// double purity) {
		// this(new Irio(item, meta), name, power, range, purity);
		// }

		public ItemAndVals(Block block, String name, int power, int range, float purity) {
			this(new ItemStack(block), name, power, range, purity);
		}

		public ItemAndVals(Item item, String name, int power, int range, float purity) {
			this(new ItemStack(item), name, power, range, purity);
		}

		public ItemAndVals(ItemStack item, String name, int power, int range, float purity) {
			this.stack = item;
			this.name = name;
			this.power = power;
			this.range = range;
			this.purity = purity;
		}
	}

	private ResourceLocation name;
	private static ItemAndVals[] nucleousList;
	private static ItemAndVals[] relayList;
	private static ItemAndVals[] screenList;
	private static boolean innited = false;

	public static boolean isInnited() {
		return innited;
	}

	public static void init() {
		nucleousList = new ItemAndVals[] { //
				new ItemAndVals(Main.myst_rod, "Mysterious", 250, 2, .90f), //
				new ItemAndVals(Items.BLAZE_ROD, "Blaze", 1000, 3, .98f), //
		};

		relayList = new ItemAndVals[] { //
				new ItemAndVals(Main.azurite, "Azurite", 500, 1, .90f), //
				new ItemAndVals(Main.moonstone, "Moonstone", 100, 2, .98f), //
		};

		screenList = new ItemAndVals[] { //
				new ItemAndVals(Main.crystal, "Crystal", 0, -2, .90f), //
				new ItemAndVals(Main.crystal_nether, "Nether", 0, -1, .98f), //
		};

		innited = true;
	}

	@Override
	public IRecipe setRegistryName(ResourceLocation name) {
		this.name = name;
		return this;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return name;
	}

	@Override
	public Class<IRecipe> getRegistryType() {
		return IRecipe.class;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return isScreen(inv.getStackInSlot(0)) && isRelay(inv.getStackInSlot(1)) && isScreen(inv.getStackInSlot(2)) && //
				isScreen(inv.getStackInSlot(3)) && isNucleous(inv.getStackInSlot(4)) && isScreen(inv.getStackInSlot(5)) && //
				isScreen(inv.getStackInSlot(6)) && isRelay(inv.getStackInSlot(7)) && isScreen(inv.getStackInSlot(8)) && //
				areSame(inv, 1, 7) && areSame(inv, 0, 2, 3, 5, 6, 8);
		// return false;
	}

	private boolean areSame(InventoryCrafting inv, int... ints) {
		Item item = inv.getStackInSlot(ints[0]).getItem();
		for (int i : ints) {
			if (item != inv.getStackInSlot(i).getItem())
				return false;
		}
		return true;
	}

	private boolean isNucleous(ItemStack stack) {
		for (ItemAndVals itemAndVals : nucleousList) {
			if (Util.equalExceptAmount(itemAndVals.stack, stack))
				return true;
		}
		return false;
	}

	private boolean isRelay(ItemStack stack) {
		for (ItemAndVals itemAndVals : relayList) {
			if (Util.equalExceptAmount(itemAndVals.stack, stack))
				return true;
		}
		return false;
	}

	private boolean isScreen(ItemStack stack) {
		for (ItemAndVals itemAndVals : screenList) {
			if (Util.equalExceptAmount(itemAndVals.stack, stack))
				return true;
		}
		return false;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width >= 3 && height >= 3;
	}

	@Override
	public ItemStack getRecipeOutput() {
		ItemStack newstack = new ItemStack(Main.power_crystal);

		if (!newstack.hasTagCompound())
			newstack.setTagCompound(new NBTTagCompound());

		NBTTagCompound nbt = new NBTTagCompound();
		ItemPowerCrystal.setNBT(nbt, 0, 0, 0);
		nbt.setString("comment", "test");
		newstack.getTagCompound().setTag("hwell", nbt);
		return newstack;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack newstack = new ItemStack(Main.power_crystal);

		if (!newstack.hasTagCompound())
			newstack.setTagCompound(new NBTTagCompound());

		NBTTagCompound nbt = new NBTTagCompound();
		ItemPowerCrystal.setNBT(nbt, getNucIndex(inv), getRelayIndex(inv), getScreenIndex(inv));
		newstack.getTagCompound().setTag("hwell", nbt);
		return newstack;
	}

	private int getNucIndex(InventoryCrafting inv) {
		ItemStack stack = inv.getStackInSlot(4);
		int i = 0;
		for (ItemAndVals itemAndVals : nucleousList) {
			if (ItemStack.areItemStacksEqual(itemAndVals.stack, stack))
				return i;
			i++;
		}
		return 0;
	}

	private int getRelayIndex(InventoryCrafting inv) {
		ItemStack stack = inv.getStackInSlot(1);
		int i = 0;
		for (ItemAndVals itemAndVals : relayList) {
			if (ItemStack.areItemStacksEqual(itemAndVals.stack, stack))
				return i;
			i++;
		}
		return 0;
	}

	private int getScreenIndex(InventoryCrafting inv) {
		ItemStack stack = inv.getStackInSlot(0);
		int i = 0;
		for (ItemAndVals itemAndVals : screenList) {
			if (ItemStack.areItemStacksEqual(itemAndVals.stack, stack))
				return i;
			i++;
		}
		return 0;
	}

	public static ItemAndVals getNucleous(int nucleousIndex) {
		int i = 0;
		for (ItemAndVals itemAndVals : nucleousList) {
			if (i == nucleousIndex)
				return itemAndVals;
			i++;
		}
		return null;
	}

	public static ItemAndVals getRelay(int relayIndex) {
		int i = 0;
		for (ItemAndVals itemAndVals : relayList) {
			if (i == relayIndex)
				return itemAndVals;
			i++;
		}
		return null;
	}

	public static ItemAndVals getScreen(int screenIndex) {
		int i = 0;
		for (ItemAndVals itemAndVals : screenList) {
			if (i == screenIndex)
				return itemAndVals;
			i++;
		}
		return null;
	}

	public static int calcPower(int nucleousIndex, int relayIndex, int screenIndex) {
		ItemAndVals nucleous = getNucleous(nucleousIndex);
		ItemAndVals relay = getRelay(relayIndex);
		ItemAndVals screen = getScreen(screenIndex);
		return nucleous.power + relay.power + screen.power;
	}

	public static int calcRange(int nucleousIndex, int relayIndex, int screenIndex) {
		ItemAndVals nucleous = getNucleous(nucleousIndex);
		ItemAndVals relay = getRelay(relayIndex);
		ItemAndVals screen = getScreen(screenIndex);
		return nucleous.range + relay.range + screen.range;
	}

	public static float calcPurity(int nucleousIndex, int relayIndex, int screenIndex) {
		ItemAndVals nucleous = getNucleous(nucleousIndex);
		ItemAndVals relay = getRelay(relayIndex);
		ItemAndVals screen = getScreen(screenIndex);
		return nucleous.purity * relay.purity * screen.purity;
	}

}
