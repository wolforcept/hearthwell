package wolforce;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Util {

	String[][] stone = { //
			{ "Coal Block", "Wood Logs" }, //
			{ "Iron Block", "Stone / Cobblestone / Sandstone" }, //
			{ "Gold Block", "Iron Blocks" }, //
			{ "", "" }, //
			{ "Clay Block", "Water" }, //
			{ "Bone Block", "Snow Blocks" }, //
			{ "Redstone Block", "Netherrack / Magma Blocks" }, //
			{ "", "" } //
	}, heat = { //
			{ "Gunpowder Block", "?" }, //
			{ "", "" }, //
			{ "Glowstone", "Gold Blocks" }, //
			{ "Netherrack", "Coal Blocks / Charcoal Blocks" }, //
			{ "Sea Lantern", "Snow / Ice / Packed Ice" }, //
			{ "Quartz Block", "Iron Blocks" }, //
			{ "Magma Block", "Netherrack" }, //
			{ "Prismarine", "Sea Lantern / Quartz / Dark Prismarine" } //
	}, green = { //
			{ "Podzol", "Fullgrass Blocks" }, //
			{ "Melon", "Water" }, //
			{ "Pumpkin", "Melon" }, //
			{ "Wheat Seeds Crate	", "" }, //
			{ "Packed Ice", "Ice" }, //
			{ "Feather Block", "Wool Blocks" }, //
			{ "Mushroom Block", "?" }, //
			{ "Emerald Block", "Diamond Block" } //
	}, sentient = { //
			{ "Podzol", "Grass Blocks" }, //
			{ "", "" }, //
			{ "End Stone", "Glowstone / Glowstone Lamp" }, //
			{ "Purpur Block", "End Stone" }, //
			{ "Diamond Block", "Redstone Blocks" }, //
			{ "Bone Block", "Snow Blocks" }, //
			{ "Leather Block", "Terracota" }, //
			{ "Ender Pearl ", "End Stone Blocks" } //
	};

	public static boolean isValid(ItemStack stack) {
		return stack != null && !stack.isEmpty();
	}

	public static boolean canBeStacked(ItemStack stack1, ItemStack stack2) {
		return ItemStack.areItemsEqual(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
	}

	public static void spawnItem(World world, BlockPos pos, ItemStack item) {
		EntityItem entityitem = new EntityItem(world, pos.getX() + .5, pos.getY(), pos.getZ() + .5, item);
		entityitem.motionX = Math.random() * .4 - .2;
		entityitem.motionY = Math.random() * .2;
		entityitem.motionZ = Math.random() * .4 - .2;
		world.spawnEntity(entityitem);
	}

	public static boolean isMultiblockBuilt(World world, BlockPos realPos, EnumFacing facing, String[][][] multiblock,
			HashMap<String, BlockWithMeta> table) {
		BlockPos center = getMyPosition(facing, multiblock);

		if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
			for (int y = 0; y < multiblock.length; y++) {
				for (int x = 0; x < multiblock[y].length; x++) {
					for (int z = 0; z < multiblock[y][x].length; z++) {
						if (multiblock[y][x][z] == null || multiblock[y][x][z] == "00")
							continue;
						if (!checkPos(world, realPos, center, facing, multiblock[y][x][z], x, y, z, table))
							return false;
					}
				}
			}
		} else if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
			for (int y = 0; y < multiblock.length; y++) {
				for (int z = 0; z < multiblock[y].length; z++) {
					for (int x = 0; x < multiblock[y][z].length; x++) {
						if (multiblock[y][z][x] == null || multiblock[y][z][x] == "00")
							continue;
						if (!checkPos(world, realPos, center, facing, multiblock[y][z][x], x, y, z, table))
							return false;
					}
				}
			}
		}
		return true;

		// if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
		// for (int y = 0; y < multiblock.length; y++) {
		// for (int z = 0; z < multiblock[y].length; z++) {
		// for (int x = 0; x < multiblock[y][z].length; x++) {
		// if (multiblock[y][z][x] == "00")
		// return new BlockPos(x, y, z);
		// }
		// }
		// }
		// }
		// return false;
	}

	private static boolean checkPos(World world, BlockPos realPos, BlockPos centre, EnumFacing facing, String mbs, int x, int y, int z,
			HashMap<String, BlockWithMeta> table) {

		BlockPos thispos = centre.subtract(new BlockPos(x, y, z));
		if (facing == EnumFacing.EAST)
			thispos = new BlockPos(-thispos.getX(), thispos.getY(), thispos.getZ());
		if (facing == EnumFacing.WEST)
			thispos = new BlockPos(thispos.getX(), thispos.getY(), -thispos.getZ());
		if (facing == EnumFacing.SOUTH)
			thispos = new BlockPos(-thispos.getX(), thispos.getY(), -thispos.getZ());

		BlockWithMeta tableEntry = table.get(mbs);
		IBlockState state = world.getBlockState(realPos.subtract(thispos));

		// ----------------------------------
		// if (tableEntry.meta != -1)
		// world.setBlockState(realPos.subtract(thispos),
		// tableEntry.block.getStateFromMeta(tableEntry.meta), 2);
		// else
		// world.setBlockState(realPos.subtract(thispos),
		// tableEntry.block.getDefaultState(), 2);
		// ----------------------------------

		return tableEntry.block == state.getBlock() && //
				(tableEntry.meta == -1 || tableEntry.meta == tableEntry.block.getMetaFromState(state));
	}

	private static BlockPos getMyPosition(EnumFacing facing, String[][][] multiblock) {
		if (facing == EnumFacing.EAST || facing == EnumFacing.WEST)
			for (int y = 0; y < multiblock.length; y++) {
				for (int x = 0; x < multiblock[y].length; x++) {
					for (int z = 0; z < multiblock[y][x].length; z++) {
						if (multiblock[y][x][z] == "00")
							return new BlockPos(x, y, z);
					}
				}
			}
		if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH)
			for (int y = 0; y < multiblock.length; y++) {
				for (int z = 0; z < multiblock[y].length; z++) {
					for (int x = 0; x < multiblock[y][z].length; x++) {
						if (multiblock[y][z][x] == "00")
							return new BlockPos(x, y, z);
					}
				}
			}
		return null;
	}

	// @SideOnly(Side.CLIENT)
	// private void particlesandsounds(int x, int y, int z) {
	// // world.playSound(x, y, z, new SoundEv, category, volume, pitch,
	// // distanceDelay);
	// for (int i = 0; i < 10; i++) {
	// world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, //
	// x, y, z, Math.random() * .2 - .1, Math.random() * .2 - .1, Math.random() * .2
	// - .1);
	// }
	// }

	public static class BlockWithMeta {

		Block block;
		int meta;

		public BlockWithMeta(Block block) {
			this.block = block;
			this.meta = -1;
		}

		public BlockWithMeta(Block block, int meta) {
			this.block = block;
			this.meta = meta;
		}

	}

	public static ResourceLocation res(String string) {
		return new ResourceLocation(Main.MODID, string);
	}

	private static int[][] touches = new int[][] { { -1, 0, 0 }, { 1, 0, 0 }, { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 } };

	public static BlockPos[] getBlocksTouching(World world, BlockPos pos) {
		return new BlockPos[] { pos.add(-1, 0, 0), pos.add(1, 0, 0), //
				pos.add(0, -1, 0), pos.add(0, 0, 1), //
				pos.add(0, 0, -1), pos.add(0, 0, 1) };
	}

	public static void renderItem(World world, ItemStack item, double... ins) {

		boolean flag1 = ins.length >= 3;
		boolean flag2 = ins.length >= 6;
		boolean flag3 = ins.length >= 9;
		double x = flag1 ? ins[0] : 0;
		double y = flag1 ? ins[1] : 0;
		double z = flag1 ? ins[2] : 0;
		float rx = flag2 ? (float) ins[3] : 0;
		float ry = flag2 ? (float) ins[4] : 0;
		float rz = flag2 ? (float) ins[5] : 0;
		double sx = flag3 ? ins[6] : 1;
		double sy = flag3 ? ins[7] : 1;
		double sz = flag3 ? ins[8] : 1;

		GlStateManager.pushMatrix();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.translate(x, y, z);

		GlStateManager.pushMatrix();
		GlStateManager.scale(0.5F, 0.5F, 0.5F);
		GlStateManager.translate(1.5F, 0.3F, 0.5F); // Y
		GlStateManager.rotate(0F, 0F, 1F, 0F);
		GlStateManager.translate(-0.5F, 0F, 0.5F); // X & Z
		GlStateManager.rotate(0F, 0F, 1F, 0F);
		GlStateManager.translate(0D, 0D, 0F);

		EntityItem customItem = new EntityItem(world, z, y, z + 1, item);
		customItem.hoverStart = 0.0F;

		GlStateManager.scale(1.8F * sx, 1.8F * sy, 1.8F * sz);
		GlStateManager.translate(-0.02f, 0.98f, -1.32f);
		GlStateManager.rotate(90.0f + rx, 90.0F + ry, 1.0F + rz, 0.0F);

		Minecraft.getMinecraft().getRenderManager().renderEntity(customItem, 0, 1, 0, 0, 0, false);

		GlStateManager.popMatrix();
		GlStateManager.disableAlpha();
		GlStateManager.pushMatrix();
		// GlStateManager.translate(0.5F, 1.8F, 0.5F);
		GlStateManager.rotate(0.0F, 1F, 0F, 1F);
		GlStateManager.popMatrix();
		GlStateManager.enableAlpha();
		GlStateManager.popMatrix();
	}
}
