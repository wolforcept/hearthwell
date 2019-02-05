//package wolforce.recipes;
//
//import java.util.BitSet;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//import com.google.common.base.Predicate;
//import com.google.common.collect.Lists;
//
//import it.unimi.dsi.fastutil.ints.IntList;
//import net.minecraft.inventory.InventoryCrafting;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.crafting.IRecipe;
//import net.minecraft.item.crafting.Ingredient;
//import net.minecraft.item.crafting.ShapedRecipes;
//import net.minecraft.item.crafting.ShapelessRecipes;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.NonNullList;
//import net.minecraft.world.World;
//
//public class MyShapelessRecipe extends ShapelessRecipes {
//
//	public MyShapelessRecipe(String group, ItemStack output, NonNullList<Ingredient> ingredients) {
//		super(group, output, ingredients);
//	}
//
//	public boolean matches(InventoryCrafting inv, World worldIn) {
//		int ingredientCount = 0;
//		net.minecraft.client.util.RecipeItemHelper recipeItemHelper = new net.minecraft.client.util.RecipeItemHelper();
//		List<ItemStack> inputs = Lists.newArrayList();
//
//		for (int i = 0; i < inv.getHeight(); ++i) {
//			for (int j = 0; j < inv.getWidth(); ++j) {
//				ItemStack itemstack = inv.getStackInRowAndColumn(j, i);
//
//				if (!itemstack.isEmpty()) {
//					++ingredientCount;
//					inputs.add(itemstack);
//				}
//			}
//		}
//		if (ingredientCount != this.recipeItems.size())
//			return false;
//		return myFindMatches(inputs, this.recipeItems);
//	}
//
//	private boolean myFindMatches(List<ItemStack> inputs, NonNullList<Ingredient> recipeItems) {
//		Collections.sort(inputs, new Comparator<ItemStack>() {
//			@Override
//			public int compare(ItemStack o1, ItemStack o2) {
//				return 0;
//			}
//		});
//		Collections.sort(recipeItems, new Comparator<Ingredient>() {
//			@Override
//			public int compare(Ingredient o1, Ingredient o2) {
//				ItemStack[] stacks = o1.getMatchingStacks();
//				
//				[0].getItem().
//				return 0;
//			}
//		});
//		return false;
//	}
//
//	private boolean equal(Ingredient ingredient, ItemStack itemStack) {
//		return ingredient.apply(itemStack);
//	}
//
//}