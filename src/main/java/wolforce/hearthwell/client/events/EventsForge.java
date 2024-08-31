package wolforce.hearthwell.client.events;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Quaternionf;

import java.util.Iterator;
import java.util.LinkedList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EventsForge {

	// public static final ItemStack identifierStack = new ItemStack(HearthWell.myst_dust);

	@SubscribeEvent
	public static void renderTooltip(final RenderTooltipEvent.Pre event) {
//		if (event.getItemStack() == identifierStack) {
//
//		}
	}

	private static class LetterRender {
		BlockPos pos;
		int n;
		int nTransparency;

		private LetterRender(BlockPos pos, int n, int nTransparency) {
			this.pos = pos;
			this.n = n;
			this.nTransparency = nTransparency;
		}
	}

	static LinkedList<LetterRender> letterRenders = new LinkedList<>();

	public static void setRenderLetter(BlockPos pos, int n) {
		letterRenders.add(new LetterRender(pos, n, 200));
	}

	@SubscribeEvent
	public static void renderOverlay(RenderLevelStageEvent event) {

		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) return;

		for (Iterator<LetterRender> iterator = letterRenders.iterator(); iterator.hasNext();) {
			LetterRender letter = iterator.next();

			letter.nTransparency--;
			if (letter.nTransparency < 0) {
				iterator.remove();
				continue;
			}

			Minecraft mc = Minecraft.getInstance();
			Font font = mc.font;
			LocalPlayer player = mc.player;
			EntityRenderDispatcher entityRenderDispatcher = mc.getEntityRenderDispatcher();
			BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();

			String string = "" + (char) letter.n;
			Quaternionf cam = entityRenderDispatcher.cameraOrientation();
			Vec3 relPos = new Vec3(letter.pos.getX(), letter.pos.getY(), letter.pos.getZ())
					.subtract(player.getPosition(event.getPartialTick()));

			float f = letter.nTransparency / 200f;
			int ff = (int) ((.1 + f * .9) * 0xFF);
			float f1 = (1 - f);

			int color = f > .00001 ? (ff << 24) | (0xFF << 16) | (0xFF << 8) | 0xFF : 0x11FFFFFF;

			PoseStack matrix = event.getPoseStack();
			matrix.pushPose();

			matrix.translate(0, -2, 0);
			matrix.translate(relPos.x + .5, relPos.y + 1, relPos.z + .5);
			matrix.translate(0, f1 * f1, 0);
			matrix.mulPose(cam);
			matrix.translate(.1, .05, 0);
			matrix.scale(-0.025F, -0.025F, 0.025F);
			font.drawInBatch(string, 0, 0, color, false, matrix.last().pose(), buffer, Font.DisplayMode.NORMAL, 1, 15728880);

			matrix.popPose();

		}
	}

}
