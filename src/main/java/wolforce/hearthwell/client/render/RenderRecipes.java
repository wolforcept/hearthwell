package wolforce.hearthwell.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.data.RecipeHearthWell;
import wolforce.hearthwell.data.recipes.*;
import wolforce.hearthwell.integration.jei.IngredientFlare;
import wolforce.hearthwell.util.UtilClient;

import java.util.List;

public class RenderRecipes {

	private static final ResourceLocation texBurst = new ResourceLocation(HearthWell.MODID, "textures/gui/bursting.png");
	private static final ResourceLocation texHanditem = new ResourceLocation(HearthWell.MODID, "textures/gui/handitem.png");
	private static final ResourceLocation texFlare = new ResourceLocation(HearthWell.MODID, "textures/gui/flare2.png");
	private static final ResourceLocation texFlareIcon = new ResourceLocation(HearthWell.MODID, "textures/gui/flare_icon.png");
	private static final ResourceLocation texInfluence = new ResourceLocation(HearthWell.MODID, "textures/gui/influence.png");
	private static final ResourceLocation texTransformation = new ResourceLocation(HearthWell.MODID, "textures/gui/transformation.png");

	public static void render(RecipeHearthWell _recipe, GuiGraphics guiGraphics, int middleX, int _yy) {

		int secs = (int) (System.currentTimeMillis() / 1500);
		int x = middleX;
		int y = _yy;

		//
		// BURST SEEDS

		if (_recipe instanceof RecipeBurstSeed recipe) {

            int w = 64;
			int h = 64;
			x -= w / 2;

			RenderSystem.enableBlend();
			// x,y u,v width,height textureWidth,textureHeight
			guiGraphics.blit(texBurst, x, y, 0, 0, w, h, w, h);
			List<ItemStack> inputs = recipe.getInputStack();
			if (inputs != null && !inputs.isEmpty()) {
				int i = secs % inputs.size();
				UtilClient.renderItem(inputs.get(i), x + 13, y + 35, 500);
			}
		}

		//
		// HAND ITEM

		if (_recipe instanceof RecipeHandItem recipe) {

            int w = 130;
			int h = 34;
			x -= w / 2;
			// x,y u,v width,height textureWidth,textureHeight
//			GuiComponent.
			guiGraphics.blit(texHanditem, x, y, 0, 0, w, h, w, h);

			// render output
			List<ItemStack> outputs = recipe.getOutputStacksFlat();
			if (!outputs.isEmpty()) {
				int inputIndex = secs % outputs.size();
				UtilClient.renderItem(outputs.get(inputIndex), x + 105, y + 9, 500);
			}

			// render input
			List<ItemStack> inputs = recipe.getInputStack();
			if (!inputs.isEmpty()) {
				int inputIndex = secs % inputs.size();
				UtilClient.renderItem(inputs.get(inputIndex), x + 57, y + 9, 500);
			}

			RenderSystem.enableBlend();
			List<IngredientFlare> flares = IngredientFlare.get(recipe.flareType);
			if (flares != null && !flares.isEmpty()) {
				int flare_index = secs % flares.size();
				IngredientFlare flare = flares.get(flare_index);
				RenderSystem.setShaderColor(flare.rgb[0], flare.rgb[1], flare.rgb[2], 1);
				guiGraphics.blit(texFlareIcon, x + 9, y + 9, /* w,h */16, 16, /* u,v */0, 0, /* uw,vh */16, 16, /* w,h */16, 16);
			}
		}

		//
		// FLARE

		if (_recipe instanceof RecipeFlare recipe) {

            int w = 134;
			int h = 82;
			x -= w / 2;
			RenderSystem.enableBlend();
			guiGraphics.blit(texFlare, x, y, 0, 0, w, h, w, h);

			List<List<ItemStack>> inputLists = recipe.getInputStacks();
			int nInputLists = inputLists.size();
			double angle = Math.PI * 2 / nInputLists;
			for (int inputListIndex = 0; inputListIndex < nInputLists; inputListIndex++) {
				int dx = (int) (Math.cos(angle * inputListIndex) * 25);
				int dy = (int) (Math.sin(angle * inputListIndex) * 25);
				List<ItemStack> inputs = inputLists.get(inputListIndex);
				if (inputs != null && !inputs.isEmpty()) {
					int i = secs % inputs.size();
					UtilClient.renderItem(inputs.get(i), x + 41 - 9 + dx, y + 41 - 9 + dy, 500);
				}
			}

			RenderSystem.enableBlend();
			List<IngredientFlare> flares = IngredientFlare.get(recipe.recipeId);
			if (flares != null && !flares.isEmpty()) {
				int flare_index = secs % flares.size();
				IngredientFlare flare = flares.get(flare_index);
				RenderSystem.setShaderColor(flare.rgb[0], flare.rgb[1], flare.rgb[2], 1);
				guiGraphics.blit(texFlareIcon, x + 108, y + 32, /* w,h */16, 16, /* u,v */0, 0, /* uw,vh */16, 16, /* w,h */16, 16);
			}
		}

		//
		// TRANSFORMATION

		if (_recipe instanceof RecipeTransformation recipe) {

            int w = 130;
			int h = 34;
			x -= w / 2;
			RenderSystem.setShaderTexture(0, texTransformation);
			// x,y u,v width,height textureWidth,textureHeight
			guiGraphics.blit(texTransformation, x, y, 0, 0, w, h, w, h);

			// render output
			List<ItemStack> outputs = recipe.getOutputStacksFlat();
			if (!outputs.isEmpty()) {
				int inputIndex = secs % outputs.size();
				UtilClient.renderItem(outputs.get(inputIndex), x + 105, y + 9, 500);
			}

			// render input
			List<ItemStack> input = recipe.getInputStack();
			if (input != null && !input.isEmpty()) {
				int inputIndex = secs % input.size();
				UtilClient.renderItem(input.get(inputIndex), x + 57, y + 9, 500);
			}

			RenderSystem.enableBlend();
			List<IngredientFlare> flares = IngredientFlare.get(recipe.flareType);
			if (flares != null && !flares.isEmpty()) {
				int flare_index = secs % flares.size();
				IngredientFlare flare = flares.get(flare_index);
				RenderSystem.setShaderColor(flare.rgb[0], flare.rgb[1], flare.rgb[2], 1);
				guiGraphics.blit(texFlareIcon, x + 9, y + 9, /* w,h */16, 16, /* u,v */0, 0, /* uw,vh */16, 16, /* w,h */16, 16);
			}
		}

		//
		// INFLUENCE

		if (_recipe instanceof RecipeInfluence recipe) {

            int w = 82;
			int h = 78;
			x -= w / 2;
			// x,y u,v width,height textureWidth,textureHeight
			guiGraphics.blit(texInfluence, x, y, 0, 0, w, h, w, h);

			// render input
			List<ItemStack> input = recipe.getInputStack();
			if (input != null && !input.isEmpty()) {
				int inputIndex = secs % input.size();
				UtilClient.renderItem(input.get(inputIndex), x + 9, y + 53, 500);
			}

			// render output
			List<ItemStack> outputs = recipe.getOutputStacksFlat();
			if (!outputs.isEmpty()) {
				int inputIndex = secs % outputs.size();
				UtilClient.renderItem(outputs.get(inputIndex), x + 57, y + 53, 500);
			}
		}

		//

	}
}
