package wolforce;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.blocks.simplevariants.MySlab;
import wolforce.blocks.simplevariants.MyStairs;

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

	public static void spawnItem(World world, BlockPos pos, ItemStack stack, double... speeds) {
		if (!Util.isValid(stack))
			return;
		EntityItem entityitem = new EntityItem(world, pos.getX() + .5, pos.getY(), pos.getZ() + .5, stack);
		if (speeds.length == 0) {
			entityitem.motionX = Math.random() * .4 - .2;
			entityitem.motionY = Math.random() * .2;
			entityitem.motionZ = Math.random() * .4 - .2;
		} else {
			entityitem.motionX = speeds[0];
			entityitem.motionY = speeds[1];
			entityitem.motionZ = speeds[2];
		}
		world.spawnEntity(entityitem);
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

	static LinkedList<Block> makeVariants(Block... blocks) {
		LinkedList<Block> variants = new LinkedList<>();
		for (Block block : blocks) {

			MySlab slab = new MySlab(block);
			variants.add(slab);

			MyStairs stairs = new MyStairs(block);
			variants.add(stairs);

			if (block != Main.myst_planks) {
				MyBlock brick = new MyBlock(block.getRegistryName().getResourcePath() + "_bricks",
						block.getDefaultState().getMaterial());
				variants.add(brick);

				MySlab brickslab = new MySlab(brick);
				variants.add(brickslab);

				MyStairs brickstairs = new MyStairs(brick);
				variants.add(brickstairs);
			}
		}
		return variants;
	}

	public static ResourceLocation res(String string) {
		return new ResourceLocation(Main.MODID, string);
	}

	//

	private static int[][] touches = new int[][] { { -1, 0, 0 }, { 1, 0, 0 }, { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 } };

	public static BlockPos[] getBlocksTouching(World world, BlockPos pos) {
		return new BlockPos[] { pos.add(-1, 0, 0), pos.add(1, 0, 0), //
				pos.add(0, -1, 0), pos.add(0, 0, 1), //
				pos.add(0, 0, -1), pos.add(0, 0, 1) };
	}

	//

	//

	//

	//

	// MULTIBLOCK CHECKERS

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

		boolean isCorrect = tableEntry.block == state.getBlock()
				&& hasCorrectMeta(tableEntry.block, tableEntry.meta, tableEntry.block.getMetaFromState(state), tableEntry.inverse);

		if (!isCorrect) {
			// ----------------------------------
			if (tableEntry.meta != -1)
				world.setBlockState(realPos.subtract(thispos), tableEntry.block
						.getStateFromMeta(tableEntry.inverse ? (tableEntry.meta != 0 ? 0 : tableEntry.meta) : tableEntry.meta), 2);
			else
				world.setBlockState(realPos.subtract(thispos), tableEntry.block.getDefaultState(), 2);
			return true;
			// ----------------------------------
		}
		return isCorrect;
	}

	private static boolean hasCorrectMeta(Block block, int requiredMeta, int meta, boolean inverse) {
		return (meta == -1 || (inverse ? meta != requiredMeta : meta == requiredMeta));
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

	public static class BlockWithMeta {

		Block block;
		int meta;
		boolean inverse = false;

		public BlockWithMeta(Block block) {
			this.block = block;
			this.meta = -1;
		}

		public BlockWithMeta(Block block, int meta, boolean... inverse) {
			this.block = block;
			this.meta = meta;
			this.inverse = inverse.length > 0 && inverse[0];
		}

	}

	//

	//

	//

	//

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

	public static List<ItemStack> listOfOneItemStack(ItemStack item) {
		LinkedList<ItemStack> list = new LinkedList<>();
		list.add(item);
		return list;
	}

	public static List<ItemStack> listOfOneItemStack(Item item) {
		return listOfOneItemStack(new ItemStack(item));
	}

	public static List<ItemStack> listOfOneItemStack(Block block) {
		return listOfOneItemStack(new ItemStack(block));
	}
}
