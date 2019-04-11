package wolforce;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UtilClient {

	// RENDERING HELPERS

	public static void simpleRenderItem(World world, ItemStack item, double x, double y, double z, boolean rotating) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5F, y + 1.225F, z + 0.5F);

		if (rotating)
			GlStateManager.rotate((Minecraft.getSystemTime() / 720.0F) * (180.0F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
		GlStateManager.pushAttrib();

		Minecraft.getMinecraft().getRenderItem().renderItem(item, ItemCameraTransforms.TransformType.FIXED);

		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
	}

	static float i = 0;

	public static void renderItem(double debug1, double debug2, //
			World world, ItemStack item, double x, double y, double z, double... ins) {

		boolean flag1 = ins.length >= 3;
		boolean flag2 = ins.length >= 6;
		float rx = flag1 ? (float) ins[0] : 0;
		float ry = flag1 ? (float) ins[1] : 0;
		float rz = flag1 ? (float) ins[2] : 0;
		double sx = flag2 ? ins[3] : 1;
		double sy = flag2 ? ins[4] : 1;
		double sz = flag2 ? ins[5] : 1;

		GlStateManager.pushMatrix();
		GlStateManager.translate(x + .5f, y, z + .5f);

		GlStateManager.rotate(rx, 1, 0, 0);
		GlStateManager.rotate(ry, 0, 1, 0);
		GlStateManager.rotate(rz, 0, 0, 1);
		GlStateManager.scale(.5f * sx, .5f * sy, .5f * sz);
		GlStateManager.pushAttrib();
		Minecraft.getMinecraft().getRenderItem().renderItem(item, ItemCameraTransforms.TransformType.FIXED);
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();

	}

	public static int getNrForDebugFromHand(World world, BlockPos pos) {
		return getNrForDebugFromHand(world, pos.getX(), pos.getY(), pos.getZ());
	}

	public static int getNrForDebugFromHand(World world, double x, double y, double z) {

		EntityPlayer player = world.getClosestPlayer(x, y, z, 1000, false);
		if (player == null)
			return 0;

		ItemStack stack = player.getHeldItemMainhand();

		if (Util.isValid(stack))
			return stack.getCount();

		return 0;
	}

	public static int getNrForDebugFromHand2(World world, double x, double y, double z) {

		EntityPlayer player = world.getClosestPlayer(x, y, z, 1000, false);
		if (player == null)
			return 0;

		ItemStack stack = player.getHeldItemOffhand();

		if (Util.isValid(stack))
			return stack.getCount();

		return 0;
	}

	public static boolean canRenderTESR(TileEntity te) {
		if (te == null)
			return false;
		World world = te.getWorld();
		return world != null && world.isBlockLoaded(te.getPos()) && !world.getBlockState(te.getPos()).getBlock().equals(Blocks.AIR);
	}

	public static boolean clientIsDaytime(World world) {
		return world.getWorldTime() < 12550 || world.getWorldTime() > 23300;
	}
}
