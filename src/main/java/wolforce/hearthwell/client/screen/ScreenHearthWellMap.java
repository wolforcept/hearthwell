package wolforce.hearthwell.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.opengl.GL11;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.client.render.RenderRecipes;
import wolforce.hearthwell.data.MapData;
import wolforce.hearthwell.data.MapNode;
import wolforce.hearthwell.data.RecipeHearthWell;
import wolforce.hearthwell.data.RecipePart;
import wolforce.hearthwell.data.recipes.RecipeFlare;
import wolforce.hearthwell.entities.EntityHearthWell;
import wolforce.hearthwell.net.ClientProxy;
import wolforce.hearthwell.net.Net;
import wolforce.hearthwell.util.Point;
import wolforce.hearthwell.util.Util;
import wolforce.hearthwell.util.UtilClient;
import wolforce.hearthwell.util.UtilClient.RGBA;

import java.util.*;
import java.util.stream.Collectors;

import static wolforce.hearthwell.data.MapData.DATA;

public class ScreenHearthWellMap extends Screen {

	public static boolean EDIT_MODE = false;

	public static final ResourceLocation GUI_TEXTURE = new ResourceLocation(HearthWell.MODID,
			"textures/gui/hearthwell.png");
	public static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(HearthWell.MODID,
			"textures/gui/hearthwell_background.png");
	public static final ResourceLocation BACKGROUND_TEXTURE_GRID = new ResourceLocation(HearthWell.MODID,
			"textures/gui/hearthwell_background2.png");

	private static final int S = 32; // GRID SIZE
	private static final int TEXTURE_W = 256, TEXTURE_H = 128;
	private static final int BACKGROUND_TEXTURE_W = 64, BACKGROUND_TEXTURE_H = 64;
//	private static final int BOTTOM_PANEL_W = 256, BOTTOM_PANEL_H = 42;
//	private static final int BAR_H = 10, BAR2_W = 241;
//	private static final int BAR_X = 7, BAR1_Y = 8, BAR2_Y = 24;
//	private static final int BAR_OVERLAY_V = 74;

	private EntityHearthWell hearthwell;
	private int dx, dy;
	private SelectedNode selectedNode = null;
//	private float zoom = 1;
	boolean dragging = false;
//	private LinkedList<Beam> beams, beamsToAdd;
//	private LinkedList<ParticleOnAScreen> particles;

	private Button buttonSave;
	TextBox[] fields;

	public ScreenHearthWellMap(EntityHearthWell hearthwell) {
		super(Component.translatable("gui.hearthwell.title"));
		this.hearthwell = hearthwell;

//		beams = new LinkedList<>();
//		beamsToAdd = new LinkedList<>();
//		particles = new LinkedList<>();
	}

	@Override
	protected void init() {
		super.init();
		dx = width / 2 - 16;
		dy = height / 2 - 16;

		fields = new TextBox[11];
		int xx = width / 2 - 64;
		int yy = height / 2 - 120;
		int ww = 128;
		int hh = 20;
		int lineH = 36;

		// id and name
		fields[0] = new TextBox(Component.translatable("gui.hearthwell.editmode.node_id"), font, xx - 128, yy, ww - 4, hh, stc(""));
		fields[1] = new TextBox(Component.translatable("gui.hearthwell.editmode.node_name"), font, xx, yy, ww + 128, hh, stc(""));
		yy += lineH;
		// x y time icon
		fields[2] = new TextBox(Component.translatable("gui.hearthwell.editmode.node_x"), font, xx - 128, yy, 60, hh, stc(""));
		fields[3] = new TextBox(Component.translatable("gui.hearthwell.editmode.node_y"), font, xx - 64, yy, 60, hh, stc(""));
		fields[4] = new TextBox(Component.translatable("gui.hearthwell.editmode.node_time"), font, xx, yy, 60, hh, stc(""));
		fields[5] = new TextBox(Component.translatable("gui.hearthwell.editmode.node_icon"), font, xx + 66, yy, ww + 62, hh, stc(""));
		yy += lineH;
		// description
		fields[6] = new TextBox(Component.translatable("gui.hearthwell.editmode.node_short_desc"), font, xx - 128, yy, ww + 256, hh, stc(""));
		// full description
		yy += lineH;
		fields[7] = new TextBox(Component.translatable("gui.hearthwell.editmode.node_full_desc"), font, xx - 128, yy, ww + 256, hh, stc(""));
		yy += lineH;
		fields[8] = new TextBox(Component.translatable("gui.hearthwell.editmode.node_parents"), font, xx - 128, yy, ww + 256, hh, stc(""));
		yy += lineH;
		fields[9] = new TextBox(Component.translatable("gui.hearthwell.editmode.node_recipes"), font, xx - 128, yy, ww + 256, hh, stc(""));
		yy += lineH;
		fields[10] = new TextBox(Component.translatable("gui.hearthwell.editmode.node_items"), font, xx - 128, yy, ww + 256, hh, stc(""));
		yy += 22;

        for (TextBox field : fields) {
            field.setVisible(false);
            field.setMaxLength(999);
            addWidget(field);
        }

		this.buttonSave = new Button.Builder(Component.translatable("gui.hearthwell.editmode.save"), (button) -> {
			//			this.buttonSave = new Button(width - 50, height - 50, 50, 50, stc("save"), (button) -> {
			if (selectedNode != null)
				selectedNode.save();
		}).bounds(xx + 32, yy, 64, 20).build();
		addWidget(buttonSave);
		buttonSave.visible = false;
	}

	@Override
	public void resize(Minecraft minecraft, int width, int height) {

		if (selectedNode != null) {

			SelectedNode prevSelNode = selectedNode;
			super.resize(minecraft, width, height);
			selectedNode = new SelectedNode(prevSelNode.nodeId, prevSelNode.node, EDIT_MODE);

		} else {

			super.resize(minecraft, width, height);

		}
	}

	//
	//
	// MOUSE EVENTS

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {

		if (selectedNode == null) {

			byte x = (byte) Math.floor((mouseX - dx) / S);
			byte y = (byte) Math.floor((mouseY - dy) / S);
			MapNode node = DATA.getNode(x, y);

			if (button == 1 && node != null && (hearthwell.isUnlocked(node) || EDIT_MODE)) {
				selectedNode = new SelectedNode(DATA.getNodeId(node), node, EDIT_MODE);
				return true;
			}

			dragging = true;

		} else {
			if (!EDIT_MODE)
				selectedNode = null;
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {

		if (selectedNode == null) {
			if (EDIT_MODE && button == 2) {

				byte x = (byte) Math.floor((mouseX - dx) / S);
				byte y = (byte) Math.floor((mouseY - dy) / S);
				MapNode node = DATA.getNode(x, y);
				if (node != null) {
					DATA.removeNode(x, y, true);
				} else {
					int i = 1;
					while (DATA.nodes.containsKey("new_node_" + i))
						i++;
					DATA.addNode("new_node_" + i, x, y, "New Node", 50, "minecraft:diamond", "add a short description",
							"add a long description. You can use \n to create a new line.", MapData.array(),
							MapData.array(), MapData.array(), true);
				}
				return true;
			}

			byte x = (byte) Math.floor((mouseX - dx) / S);
			byte y = (byte) Math.floor((mouseY - dy) / S);
			MapNode node = DATA.getNode(x, y);
			if (button == 0 && dragging) {
				dragging = false;
				if (node != null) {
					Net.sendToggleMessage(hearthwell, x, y);
					return true;
				}
			}
		}

		return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double _dragX, double _dragY) {

		if (selectedNode == null) {
//		boolean ret = super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
			int dragX = (int) (_dragX > 0 ? Math.ceil(_dragX) : Math.floor(_dragX));
			int dragY = (int) (_dragY > 0 ? Math.ceil(_dragY) : Math.floor(_dragY));
			dx = (int) (dx + (double) dragX);
			dy = (int) (dy + (double) dragY);
			if (!EDIT_MODE) {

//				int actualSizeX = Math.max(DATA.sizeX * S, width);
//				int actualSizeY = Math.max(DATA.sizeY * S, height);
//				dx = (int) Math.max(-actualSizeX - width, Math.min(actualSizeX, dx + Math.ceil(dragX)));
//				dy = (int) Math.max(-actualSizeY - height, Math.min(actualSizeY, dy + Math.ceil(dragY)));

				if ((Math.abs(DATA.minX) + DATA.maxX) * S < width) {
					if (dx < -S)
						dx = -S;
					if (dx > width)
						dx = width;
				} else {
					if (dx < -DATA.maxX * S)
						dx = -DATA.maxX * S;
					if (dx > width + DATA.minX * S - S)
						dx = width + DATA.minX * S - S;
				}

				if ((Math.abs(DATA.minY) + DATA.maxY) * S < height) {
					if (dy < -S)
						dy = -S;
					if (dy > height)
						dy = height;
				} else {
					if (dy < -DATA.maxY * S)
						dy = -DATA.maxY * S;
					if (dy > height + DATA.minY * S - S)
						dy = height + DATA.minY * S - S;
				}

			}
			dragging = false;
			return true;
		}

		return super.mouseDragged(mouseX, mouseY, button, _dragX, _dragY);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		if (selectedNode != null) {
			selectedNode.scroll(delta > 0 ? 1 : -1);
		}
		return super.mouseScrolled(mouseX, mouseY, delta);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {

		if (selectedNode == null) {
			if (ClientProxy.MC.options.keyInventory.getKey().getValue() == keyCode
					|| ClientProxy.MC.options.keyDown.getKey().getValue() == keyCode) {

				onClose();
			}
		}

		return super.keyReleased(keyCode, scanCode, modifiers);
	}

	//
	//
	// RENDER

	@SuppressWarnings("deprecation")
	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

//		RenderSystem.disableLighting();
//		RenderSystem.defaultAlphaFunc();
		RenderSystem.enableBlend();
		renderStarryBackground(guiGraphics, dx % 128 - 128, dy % 128 - 128, width + 128, height + 128);

		super.render(guiGraphics, mouseX, mouseY, partialTicks);

		if (selectedNode != null) {
			selectedNode.render(guiGraphics, mouseX, mouseY, partialTicks);
			return;
		}

		Collection<MapNode> allNodes = DATA.getAll();

		for (MapNode node : allNodes) {

			int x = dx + node.x * S;
			int y = dy + node.y * S;
			for (MapNode childNode : node.getParents(DATA)) {
				int x2 = dx + childNode.x * S;
				int y2 = dy + childNode.y * S;
				renderLine(x + S / 2, y + S / 2, x2 + S / 2, y2 + S / 2, false, hearthwell.isUnlockable(node));
			}
		}

		MapNode researchNode = DATA.getNode(hearthwell.getResearchNode());
		boolean isResearching = researchNode != null && (researchNode.x != 0 || researchNode.y != 0);

		if (isResearching)
			renderLineToParentsOf(researchNode);

		if (isResearching) {
			int x = dx + researchNode.x * S;
			int y = dy + researchNode.y * S;
			guiGraphics.blit(GUI_TEXTURE, x, y, /* w,h */S, S, /* u,v */64 + 32, 0, /* uw,vh */32, 32, /* w,h */TEXTURE_W, TEXTURE_H);
		}

		RenderSystem.disableBlend();

		for (MapNode node : allNodes) {

			int x = dx + node.x * S;
			int y = dy + node.y * S;
			if (x + S < 0 || y + S < 0)
				continue;
			if (node.x == 0 && node.y == 0) {
				guiGraphics.blit(GUI_TEXTURE, x, y, /* w,h */S, S, /* u,v */225, 0, /* uw,vh */32, 32, /* w,h */TEXTURE_W, TEXTURE_H);
			} else {
				if (hearthwell != null) {
					int v = hearthwell.isUnlocked(node) ? 0 : hearthwell.isUnlockable(node) ? 32 : 64;
					guiGraphics.blit(GUI_TEXTURE, x, y, /* w,h */S, S, /* u,v */v, 0, /* uw,vh */32, 32, /* w,h */TEXTURE_W, TEXTURE_H);
				}
			}
		}

		RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
		this.minecraft.textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);

		LinkedList<Tuple<Point, Integer>> flareRenders = new LinkedList<>();
		for (MapNode node : allNodes) {

			int x = dx + node.x * S;
			int y = dy + node.y * S;
			if (x + S < 0 || y + S < 0 || x > width || y > height)
				continue;

			RecipeFlare flare = DATA.recipes_flare.stream().filter(rf -> rf.recipeId.equals(node.icon_stack))
					.findFirst().orElse(null);
			if (flare != null) {
				int color = hearthwell.isUnlocked(node) || hearthwell.isUnlockable(node) ? flare.color : 0;
				flareRenders.add(new Tuple<>(new Point(x, y), color));
			} else {
				ItemStack item = Util.tryGetItemStack(node.icon_stack);
				if (hearthwell.isUnlocked(node) || hearthwell.isUnlockable(node))
					UtilClient.renderItem(item, x + 8, y + 8, 100);
				else
					UtilClient.renderItem(item, x + 8, y + 8, 100, (r, g, b, a) -> new RGBA(0, 0, 0, a));
			}
		}

//		this.minecraft.textureManager.getTexture(new ResourceLocation(HearthWell.MODID, "particles/particle_energy.png")).setFilter(false, false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableBlend();
		RenderSystem.setShaderTexture(0,
				new ResourceLocation(HearthWell.MODID, "textures/particle/particle_energy.png"));
		for (Tuple<Point, Integer> tuple : flareRenders) {

//			RenderSystem.colo

//			System.out.println(tuple.getB());
			UtilClient.colorBlit(guiGraphics.pose(), (int) tuple.getA().x + 4, (int) tuple.getA().y + 4, 0, 0, S - 8, S - 8,
					tuple.getB());
//			blit(guiGraphics, (int) tuple.getA().x + 4, (int) tuple.getA().y + 4, S - 8, S - 8, // X, Y, W, H
//					0, 0, S, S, // U, V, UW, VH
//					S, S // TOTAL W, TOTAL H
//			);
		}

		if (EDIT_MODE) {
			guiGraphics.drawString(font, Component.translatable("gui.hearthwell.editmode.1"), 3, height - 50, 0xFF0000);
			guiGraphics.drawString(font, Component.translatable("gui.hearthwell.editmode.2"), 3, height - 40, 0xFF0000);
			guiGraphics.drawString(font, Component.translatable("gui.hearthwell.editmode.3"), 3, height - 30, 0xFF0000);
			guiGraphics.drawString(font, Component.translatable("gui.hearthwell.editmode.4"), 3, height - 20, 0xFF0000);
			guiGraphics.drawString(font, Component.translatable("gui.hearthwell.editmode.5"), 3, height - 10, 0xFF0000);
		}

		RenderSystem.disableBlend();

		for (MapNode location : DATA.getAll()) {
			int x = dx + location.x * S;
			int y = dy + location.y * S;
			if (x + S < 0 || y + S < 0)
				continue;
			if (new Rect2i(x, y, S, S).contains(mouseX, mouseY)) {
				renderTooltip(guiGraphics, mouseX, mouseY, location);
				return;
			}
		}

	}

	private void renderLineToParentsOf(MapNode node) {
		if (node != null) {
			for (String parentId : node.parent_ids) {
				MapNode parent = DATA.getNode(parentId);
				if (parent != null) {
					int x = dx + node.x * S;
					int y = dy + node.y * S;
					int x2 = dx + parent.x * S;
					int y2 = dy + parent.y * S;
					renderLine(x + S / 2, y + S / 2, x2 + S / 2, y2 + S / 2, true, false);
				}
				renderLineToParentsOf(parent);
			}
		}
	}

	private void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY, MapNode node) {

		List<Component> texts = new ArrayList<>();

		if (hearthwell.isUnlocked(node)) {
			texts.add(Component.literal(node.name));
			for (String s : node.short_description.split("\n")) {
				texts.add(Component.literal(s));
			}
		} else {
			boolean renderItems = false;
			if (node.hash() == MapNode.hash(hearthwell.getResearchNode())) {
				texts.add(Component.translatable("gui.hearthwell.researching", (Math.round(hearthwell.getResearchPercent() * 1000) / 10.0)));
				renderItems = true;
			} else {
				if (hearthwell.isUnlockable(node)) {
					texts.add(Component.translatable("gui.hearthwell.locked"));
					renderItems = true;
				}
			}

			if (renderItems && !node.requiredItems.isEmpty()) {
				texts.add(Component.translatable("gui.hearthwell.required_item"));

				int tooltipTextWidth = 0;

				for (FormattedText textLine : texts) {
					int textLineWidth = font.width(textLine);
					if (textLineWidth > tooltipTextWidth)
						tooltipTextWidth = textLineWidth;
				}

				int tooltipX = mouseX + 12;
				if (tooltipX + tooltipTextWidth + 4 > width) {
					tooltipX = mouseX - 16 - tooltipTextWidth;
					if (tooltipX < 4) // if the tooltip doesn't fit on the screen
					{
						if (mouseX > width / 2)
							tooltipTextWidth = mouseX - 12 - 8;
						else
							tooltipTextWidth = width - 16 - mouseX;
					}
				}

				int dx = 3, dy = 11;
				StringBuilder widthInSpaces = new StringBuilder();
				for (RecipePart part : node.requiredItems) {
					List<ItemStack> stacks = part.stacks();
					if (stacks != null && !stacks.isEmpty()) {
						int itemIndex = (int) Math.max(0,
								Math.min(stacks.size() - 1, System.currentTimeMillis() / 1000 % stacks.size()));
						UtilClient.renderItem(stacks.get(itemIndex),
								mouseX + 12 + dx - (width - mouseX < tooltipTextWidth + 16 ? tooltipTextWidth + 28 : 0),
								Math.min(height - dy - 10, mouseY + dy), 500);
					}
					dx += 20;
					widthInSpaces.append("     ");
				}
				texts.add(Component.literal(widthInSpaces.toString()));
//				texts.add("");
			}
		}

        if (hearthwell.isUnlocked(node)) {
			MutableComponent learnMoreLine = Component.translatable("gui.hearthwell.learn_more");
			learnMoreLine.withStyle(Style.EMPTY.withColor(TextColor.parseColor("#555555")));
			texts.add(learnMoreLine);
		}

		guiGraphics.renderComponentTooltip(font, texts, mouseX, mouseY);
	}

	//
	//

	public void renderStarryBackground(GuiGraphics guiGraphics, int x, int y, int w, int h) {
		for (int xx = 0; xx < w + BACKGROUND_TEXTURE_W; xx += BACKGROUND_TEXTURE_W) {
			for (int yy = 0; yy < h + BACKGROUND_TEXTURE_H; yy += BACKGROUND_TEXTURE_H) {
				guiGraphics.blit(BACKGROUND_TEXTURE, x / 2 + xx, y / 2 + yy, BACKGROUND_TEXTURE_W, BACKGROUND_TEXTURE_H, // X, Y, W, H
						0, 0, BACKGROUND_TEXTURE_W, BACKGROUND_TEXTURE_H, // U, V, UW, VH
						BACKGROUND_TEXTURE_W, BACKGROUND_TEXTURE_H // TOTAL W, TOTAL H
				);
			}
		}

//		RenderSystem.depthMask(true);
//		Tesselator tessellator = Tesselator.getInstance();
//		BufferBuilder bufferbuilder = tessellator.getBuilder();
//		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//		bufferbuilder.vertex(x + 0, y + 0, 0).uv(0, h).endVertex();
//		bufferbuilder.vertex(x + 0, y + h, 0).uv(w, h).endVertex();
//		bufferbuilder.vertex(x + w, y + h, 0).uv(w, 0).endVertex();
//		bufferbuilder.vertex(x + w, y + 0, 0).uv(0, 0).endVertex();
//		tessellator.end();
	}

	public void renderLine(float x1, float y1, float x2, float y2, boolean moving, boolean impossible) {

		RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		RenderSystem.disableCull();
		RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);

		Random rand = moving ? new Random() : new Random(0);
		if (moving) {
			RenderSystem.lineWidth(4);
			float d = 3f, d2 = d / 2f;
			for (int i = 0; i < 10; i++) {
				float r = .5f + (float) Math.random() * .2f;
				float g = .3f;
				float b = .5f + (float) Math.random() * .5f;
				float a = .5f;
				double xx = x1 - d2 + d * rand.nextDouble();
				double yy = y1 - d2 + d * rand.nextDouble();
				double xx2 = x2 - d2 + d * rand.nextDouble();
				double yy2 = y2 - d2 + d * rand.nextDouble();
				Tesselator tessellator = Tesselator.getInstance();
				BufferBuilder bufferbuilder = tessellator.getBuilder();
				bufferbuilder.begin(VertexFormat.Mode.LINE_STRIP, DefaultVertexFormat.POSITION_COLOR_NORMAL);
				bufferbuilder.vertex(xx, yy, 0).color(r, g, b, a).normal(r(rand), r(rand), r(rand)).endVertex();
				bufferbuilder.vertex(xx2, yy2, 0).color(r, g, b, a).normal(r(rand), r(rand), r(rand)).endVertex();
				tessellator.end();
			}
		} else {
			RenderSystem.lineWidth(impossible ? 15 : 10);
			float d = impossible ? 5 : 1f, d2 = d / 2f;
			for (int i = 0; i < 10; i++) {
				float r = (impossible ? .5f : .2f) + (float) rand.nextDouble() * .2f;
				float g = 0;
				float b = (impossible ? .5f : .2f) + (float) rand.nextDouble() * .5f;
				float a = impossible ? .1f : .04f;
				double xx = x1 - d2 + d * rand.nextDouble();
				double yy = y1 - d2 + d * rand.nextDouble();
				double xx2 = x2 - d2 + d * rand.nextDouble();
				double yy2 = y2 - d2 + d * rand.nextDouble();
				Tesselator tessellator = Tesselator.getInstance();
				BufferBuilder bufferbuilder = tessellator.getBuilder();
				bufferbuilder.begin(VertexFormat.Mode.LINE_STRIP, DefaultVertexFormat.POSITION_COLOR_NORMAL);
				bufferbuilder.vertex(xx, yy, 0).color(r, g, b, a).normal(r(rand), r(rand), r(rand)).endVertex();
				bufferbuilder.vertex(xx2, yy2, 0).color(r, g, b, a).normal(r(rand), r(rand), r(rand)).endVertex();
				tessellator.end();
			}
		}
	}

	private float r(Random rand) {
		return (float) (-1 + rand.nextDouble() * 2);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	private class SelectedNode {

		ArrayList<String> lines = null;
		MapNode node;
		private String nodeId;
		List<RecipeHearthWell> recipes;
		int page = 0;

		public SelectedNode(String nodeId, MapNode node, boolean isEdit) {

			this.nodeId = nodeId;
			this.node = node;

			if (isEdit) {

				String[] arr = { //
						nodeId, node.name, //
						"" + node.x, "" + node.y, //
						"" + node.time, node.icon_stack, //
						node.short_description, node.full_description, //
						arrToStr(node.parent_ids), arrToStr(node.recipes_ids), arrToStr(node.required_items) //
				};

				for (int i = 0; i < fields.length; i++) {
					fields[i].setValue(arr[i]);
					fields[i].setVisible(isEdit);
					addWidget(fields[i]);
				}

				fields[0].setFocused(isEdit);

				buttonSave.visible = true;

			} else {

				lines = new ArrayList<String>();
				lines.add(node.name);
				lines.add("");
				lines.addAll(Arrays.asList(node.full_description.split("/n")));

				recipes = Arrays.stream(node.recipes_ids).map(id -> DATA.getRecipeById(id))
						.collect(Collectors.toList());
			}

		}

		public void scroll(int value) {
			if (recipes != null && !recipes.isEmpty())
				page = Math.min(recipes.size() - 1, Math.max(0, page - value));
		}

		void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

			if (lines != null) {
				int yy = height / 2 - 100;
				for (String line : lines) {
					yy += 10;
					int xx = width / 2 - font.width(line) / 2;
					guiGraphics.drawString(font, line, xx, yy, 0xFFFFFFFF);
				}

				if (recipes != null && !recipes.isEmpty()) {
					Component line1 = Component.translatable("gui.hearthwell.unlocked_recipes", page + 1, recipes.size());
					int xx = width / 2 - font.width(line1) / 2;
					guiGraphics.drawString(font, line1, xx, yy + 20, 0xFFFFFFFF);
					Component line2 = Component.translatable("gui.hearthwell.scroll");
					xx = width / 2 - font.width(line2) / 2;
					guiGraphics.drawString(font, line2, xx, yy + 32, 0x77FFFFFF);
					RecipeHearthWell recipe = recipes.get(page);
					if (recipe != null)
						RenderRecipes.render(recipe, guiGraphics, width / 2, yy + 60);
					else {
						Component line3 = Component.translatable("gui.hearthwell.recipe_notfound");
						xx = width / 2 - font.width(line3) / 2;
						guiGraphics.drawString(font, line3, xx, yy + 60, 0x77FFFFFF);
					}
				}
			} else {
				for (TextBox field : fields) {
					field.render(guiGraphics, mouseX, mouseY, partialTicks);
					guiGraphics.drawString(font, field.textboxTitle, field.getX(), field.getY() - 10, 0x88FFFFFF);
				}
				buttonSave.render(guiGraphics, mouseX, mouseY, partialTicks);

			}
		}

		public void save() {

			String id = fields[0].getValue();
			DATA.changeNodeId(node, id);
			node.name = fields[1].getValue();

			try {
				node.x = Byte.parseByte(fields[2].getValue());
			} catch (Exception ignored) {
			}
			try {
				node.y = Byte.parseByte(fields[3].getValue());
			} catch (Exception ignored) {
			}

			node.time = Integer.parseInt(fields[4].getValue());
			node.icon_stack = fields[5].getValue();

			node.short_description = fields[6].getValue();
			node.full_description = fields[7].getValue();

			node.parent_ids = strToArr(fields[8].getValue());
			node.recipes_ids = strToArr(fields[9].getValue());
			node.required_items = strToArr(fields[10].getValue());

			DATA.init();
			MapData.writeData(DATA);

            for (TextBox field : fields) field.setVisible(false);
			buttonSave.visible = false;
			selectedNode = null;
		}

	}

	private static Component stc(String s) {
		return Component.literal(s);
	}

	private String[] strToArr(String _str) {
		String str = _str.trim();
		if (str.isEmpty())
			return new String[] {};
		return str.split(",");
	}

	private static String arrToStr(String[] arr) {
		if (arr.length == 0)
			return "";
		if (arr.length == 1)
			return arr[0];
		StringBuilder s = new StringBuilder();
		for (String part : arr) {
			s.append(part).append(",");
		}
		return s.substring(0, s.length() - 1);
	}

	class TextBox extends EditBox {

		private Component textboxTitle;

		public TextBox(Component textboxTitle, Font font, int x, int y, int w, int h, Component text) {
			super(font, x, y, w, h, text);
			this.textboxTitle = textboxTitle;
		}

		@Override
		public void setFocused(boolean focused) {
			if (focused) {
				for (TextBox textBox : fields) {
					textBox.setFocused(false);
				}
			}
			super.setFocused(focused);
		}
	}
}