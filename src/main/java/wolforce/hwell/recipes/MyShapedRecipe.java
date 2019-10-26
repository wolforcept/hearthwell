package wolforce.hwell.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class MyShapedRecipe extends ShapedRecipes {

	public MyShapedRecipe(String group, int width, int height, NonNullList<Ingredient> ingredients, ItemStack result) {
		super(group, width, height, ingredients, result);
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		for (int i = 0; i <= inv.getWidth() - this.recipeWidth; ++i) {
			for (int j = 0; j <= inv.getHeight() - this.recipeHeight; ++j) {
				if (isMatch(inv, i, j, true)) {
					return true;
				}
				if (isMatch(inv, i, j, false)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Checks if the region of a crafting inventory is match for the recipe.
	 */
	private boolean isMatch(InventoryCrafting invIn, int dx, int dy, boolean b) {
		for (int i = 0; i < invIn.getWidth(); ++i) {
			for (int j = 0; j < invIn.getHeight(); ++j) {
				int x = i - dx;
				int y = j - dy;
				Ingredient ingredient = Ingredient.EMPTY;

				if (x >= 0 && y >= 0 && x < this.recipeWidth && y < this.recipeHeight) {
					if (b) {
						ingredient = this.recipeItems.get(this.recipeWidth - x - 1 + y * this.recipeWidth);
					} else {
						ingredient = this.recipeItems.get(x + y * this.recipeWidth);
					}
				}

				if (!equal(ingredient, (invIn.getStackInRowAndColumn(i, j)))) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean equal(Ingredient ingredient, ItemStack itemStack) {
		return ingredient.apply(itemStack);
	}

}