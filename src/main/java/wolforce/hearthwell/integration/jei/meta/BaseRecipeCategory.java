package wolforce.hearthwell.integration.jei.meta;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.category.IRecipeCategory;

public abstract class BaseRecipeCategory<T> implements IRecipeCategory<T> {

	public IDrawable background;
	public String unlocalizedName;

	public BaseRecipeCategory(IDrawable background, String unlocalizedName) {
		this.background = background;
		this.unlocalizedName = unlocalizedName;
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

}