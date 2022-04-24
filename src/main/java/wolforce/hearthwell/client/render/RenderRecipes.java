package wolforce.hearthwell.client.render;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.RecipeHearthWell;
import wolforce.hearthwell.data.recipes.RecipeBurstSeed;
import wolforce.hearthwell.data.recipes.RecipeFlare;
import wolforce.hearthwell.data.recipes.RecipeHandItem;
import wolforce.hearthwell.data.recipes.RecipeInfluence;
import wolforce.hearthwell.data.recipes.RecipeTransformation;
import wolforce.hearthwell.integration.jei.IngredientFlare;
import wolforce.hearthwell.util.UtilClient;

public class RenderRecipes {

	private static final ResourceLocation texBurst = new ResourceLocation(HearthWell.MODID, "textures/gui/bursting.png");
	private static final ResourceLocation texHanditem = new ResourceLocation(HearthWell.MODID, "textures/gui/handitem.png");
	private static final ResourceLocation texFlare = new ResourceLocation(HearthWell.MODID, "textures/gui/flare2.png");
	private static final ResourceLocation texFlareIcon = new ResourceLocation(HearthWell.MODID, "textures/gui/flare_icon.png");
	private static final ResourceLocation texInfluence = new ResourceLocation(HearthWell.MODID, "textures/gui/influence.png");
	private static final ResourceLocation texTransformation = new ResourceLocation(HearthWell.MODID, "textures/gui/transformation.png");

	public static void render(RecipeHearthWell _recipe, PoseStack matrix, int middleX, int _yy) {

		int secs = (int) (System.currentTimeMillis() / 1500);
		int x = middleX;
		int y = _yy;

		//
		// BURST SEEDS

		if (_recipe instanceof RecipeBurstSeed) {
			RecipeBurstSeed recipe = (RecipeBurstSeed) _recipe;

			int w = 64;
			int h = 64;
			x -= w / 2;

			RenderSystem.enableBlend();
			RenderSystem.setShaderTexture(0, texBurst);
			// x,y u,v width,height textureWidth,textureHeight
			GuiComponent.blit(matrix, x, y, 0, 0, w, h, w, h);
			List<ItemStack> inputs = recipe.getInputStack();
			if (inputs != null && !inputs.isEmpty()) {
				int i = secs % inputs.size();
				UtilClient.renderItem(matrix, inputs.get(i), x + 13, y + 35, 500);
			}
		}

		//
		// HAND ITEM

		if (_recipe instanceof RecipeHandItem) {
			RecipeHandItem recipe = (RecipeHandItem) _recipe;

			int w = 130;
			int h = 34;
			x -= w / 2;
			RenderSystem.setShaderTexture(0, texHanditem);
			// x,y u,v width,height textureWidth,textureHeight
//			GuiComponent.
			GuiComponent.blit(matrix, x, y, 0, 0, w, h, w, h);

			// render output
			List<ItemStack> outputs = recipe.getOutputStacksFlat();
			if (!outputs.isEmpty()) {
				int inputIndex = secs % outputs.size();
				UtilClient.renderItem(matrix, outputs.get(inputIndex), x + 105, y + 9, 500);
			}

			// render input
			List<ItemStack> inputs = recipe.getInputStack();
			if (!inputs.isEmpty()) {
				int inputIndex = secs % inputs.size();
				UtilClient.renderItem(matrix, inputs.get(inputIndex), x + 57, y + 9, 500);
			}

			RenderSystem.enableBlend();
			RenderSystem.setShaderTexture(0, texFlareIcon);
			List<IngredientFlare> flares = IngredientFlare.get(recipe.flareType);
			if (flares != null && !flares.isEmpty()) {
				int flare_index = secs % flares.size();
				IngredientFlare flare = flares.get(flare_index);
				RenderSystem.setShaderColor(flare.rgb[0], flare.rgb[1], flare.rgb[2], 1);
				GuiComponent.blit(matrix, x + 9, y + 9, /* w,h */16, 16, /* u,v */0, 0, /* uw,vh */16, 16, /* w,h */16, 16);
			}
		}

		//
		// FLARE

		if (_recipe instanceof RecipeFlare) {
			RecipeFlare recipe = (RecipeFlare) _recipe;

			int w = 134;
			int h = 82;
			x -= w / 2;
			RenderSystem.enableBlend();
			RenderSystem.setShaderTexture(0, texFlare);
			GuiComponent.blit(matrix, x, y, 0, 0, w, h, w, h);

			List<List<ItemStack>> inputLists = recipe.getInputStacks();
			int nInputLists = inputLists.size();
			double angle = Math.PI * 2 / nInputLists;
			for (int inputListIndex = 0; inputListIndex < nInputLists; inputListIndex++) {
				int dx = (int) (Math.cos(angle * inputListIndex) * 25);
				int dy = (int) (Math.sin(angle * inputListIndex) * 25);
				List<ItemStack> inputs = inputLists.get(inputListIndex);
				if (inputs != null && !inputs.isEmpty()) {
					int i = secs % inputs.size();
					UtilClient.renderItem(matrix, inputs.get(i), x + 41 - 9 + dx, y + 41 - 9 + dy, 500);
				}
			}

			RenderSystem.enableBlend();
			RenderSystem.setShaderTexture(0, texFlareIcon);
			List<IngredientFlare> flares = IngredientFlare.get(recipe.recipeId);
			if (flares != null && !flares.isEmpty()) {
				int flare_index = secs % flares.size();
				IngredientFlare flare = flares.get(flare_index);
				RenderSystem.setShaderColor(flare.rgb[0], flare.rgb[1], flare.rgb[2], 1);
				GuiComponent.blit(matrix, x + 108, y + 32, /* w,h */16, 16, /* u,v */0, 0, /* uw,vh */16, 16, /* w,h */16, 16);
			}
		}

		//
		// TRANSFORMATION

		if (_recipe instanceof RecipeTransformation) {
			RecipeTransformation recipe = (RecipeTransformation) _recipe;

			int w = 130;
			int h = 34;
			x -= w / 2;
			RenderSystem.setShaderTexture(0, texTransformation);
			// x,y u,v width,height textureWidth,textureHeight
			GuiComponent.blit(matrix, x, y, 0, 0, w, h, w, h);

			// render output
			List<ItemStack> outputs = recipe.getOutputStacksFlat();
			if (!outputs.isEmpty()) {
				int inputIndex = secs % outputs.size();
				UtilClient.renderItem(matrix, outputs.get(inputIndex), x + 105, y + 9, 500);
			}

			// render input
			List<ItemStack> input = recipe.getInputStack();
			if (input != null && !input.isEmpty()) {
				int inputIndex = secs % input.size();
				UtilClient.renderItem(matrix, input.get(inputIndex), x + 57, y + 9, 500);
			}

			RenderSystem.enableBlend();
			RenderSystem.setShaderTexture(0, texFlareIcon);
			List<IngredientFlare> flares = IngredientFlare.get(recipe.flareType);
			if (flares != null && !flares.isEmpty()) {
				int flare_index = secs % flares.size();
				IngredientFlare flare = flares.get(flare_index);
				RenderSystem.setShaderColor(flare.rgb[0], flare.rgb[1], flare.rgb[2], 1);
				GuiComponent.blit(matrix, x + 9, y + 9, /* w,h */16, 16, /* u,v */0, 0, /* uw,vh */16, 16, /* w,h */16, 16);
			}
		}

		//
		// INFLUENCE

		if (_recipe instanceof RecipeInfluence) {
			RecipeInfluence recipe = (RecipeInfluence) _recipe;

			int w = 82;
			int h = 78;
			x -= w / 2;
			RenderSystem.setShaderTexture(0, texInfluence);
			// x,y u,v width,height textureWidth,textureHeight
			GuiComponent.blit(matrix, x, y, 0, 0, w, h, w, h);

			// render input
			List<ItemStack> input = recipe.getInputStack();
			if (input != null && !input.isEmpty()) {
				int inputIndex = secs % input.size();
				UtilClient.renderItem(matrix, input.get(inputIndex), x + 9, y + 53, 500);
			}

			// render output
			List<ItemStack> outputs = recipe.getOutputStacksFlat();
			if (!outputs.isEmpty()) {
				int inputIndex = secs % outputs.size();
				UtilClient.renderItem(matrix, outputs.get(inputIndex), x + 57, y + 53, 500);
			}
		}

		//

	}
}
