package wolforce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import wolforce.blocks.simplevariants.MySlab;
import wolforce.blocks.simplevariants.MyStairs;
import wolforce.recipes.Irio;

public class Util {

	public static void setReg(Block block, String name) {
		block.setUnlocalizedName(Hwell.MODID + "." + name);
		block.setRegistryName(Util.res(name));
	}

	public static void setReg(Item block, String name) {
		block.setUnlocalizedName(Hwell.MODID + "." + name);
		block.setRegistryName(Util.res(name));
	}

	// ITEMSTACKS
	public static boolean isValid(ItemStack stack) {
		return stack != null && stack.getCount() > 0 && !stack.isEmpty() && !stack.getItem().equals(Items.AIR);
	}

	public static boolean canBeStacked(ItemStack stack1, ItemStack stack2) {
		return ItemStack.areItemsEqual(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
	}

	public static boolean equalExceptAmount(ItemStack stack1, ItemStack stack2) {
		return stack1.getItem() == stack2.getItem() && stack1.getMetadata() == stack2.getMetadata();
	}

	// SPAWN ITEMS

	public static void spawnItem(World world, BlockPos pos, ItemStack stack, EnumFacing facing) {
		spawnItem(world, new Vec3d(pos.getX() + .5, pos.getY(), pos.getZ() + .5), stack, facing);
	}

	public static void spawnItem(World world, Vec3d pos, ItemStack stack, EnumFacing facing) {
		spawnItem(world, pos, stack, 0, facing);
	}

	public static void spawnItem(World world, Vec3d pos, ItemStack stack, int pickupDelay, EnumFacing facing) {
		Vec3d v = facingToVector(facing);
		spawnItem(world, pos, stack, pickupDelay, v.x / 2.0, v.y / 2.0, v.z / 2.0);
	}

	public static void spawnItem(World world, BlockPos pos, ItemStack stack, double... speeds) {
		spawnItem(world, new Vec3d(pos.getX() + .5, pos.getY(), pos.getZ() + .5), stack, speeds);
	}

	public static void spawnItem(World world, Vec3d pos, ItemStack stack, double... speeds) {
		spawnItem(world, pos, stack, 0, speeds);
	}

	/**
	 * default pickupDelay = 10 player throw pickupDelay = 40
	 */
	public static void spawnItem(World world, Vec3d pos, ItemStack stack, int pickupDelay, double... speeds) {
		if (!Util.isValid(stack))
			return;
		EntityItem entityitem = new EntityItem(world, pos.x, pos.y, pos.z, stack);
		if (speeds.length == 0) {
			entityitem.motionX = Math.random() * .4 - .2;
			entityitem.motionY = Math.random() * .2;
			entityitem.motionZ = Math.random() * .4 - .2;
		} else {
			entityitem.motionX = speeds[0];
			entityitem.motionY = speeds[1];
			entityitem.motionZ = speeds[2];
		}
		entityitem.setPickupDelay(pickupDelay);
		world.spawnEntity(entityitem);
	}

	public static Vec3d facingToVector(EnumFacing facing) {
		return new Vec3d(//
				facing.getAxisDirection().getOffset() * (facing.getAxis().equals(Axis.X) ? 1 : 0), //
				facing.getAxisDirection().getOffset() * (facing.getAxis().equals(Axis.Y) ? 1 : 0), //
				facing.getAxisDirection().getOffset() * (facing.getAxis().equals(Axis.Z) ? 1 : 0));
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

	public static LinkedList<Block> makeVariants(MyBlock... blocks) {
		LinkedList<Block> variants = new LinkedList<>();
		for (MyBlock block : blocks) {

			MySlab slab = new MySlab(block);
			variants.add(slab);

			MyStairs stairs = new MyStairs(block);
			variants.add(stairs);

			if (block != Main.myst_planks) {

				MyBlock brick = new MyBlock(block.getRegistryName().getResourcePath() + "_bricks",
						block.getDefaultState().getMaterial());
				brick.setHardness(block.hardness);
				brick.setResistance(block.resistance);
				variants.add(brick);

				MySlab brickslab = new MySlab(brick);
				brickslab.setHardness(block.hardness);
				brickslab.setResistance(block.resistance);
				variants.add(brickslab);

				MyStairs brickstairs = new MyStairs(brick);
				brickstairs.setHardness(block.hardness);
				brickstairs.setResistance(block.resistance);
				variants.add(brickstairs);
			}
		}
		return variants;
	}

	public static ResourceLocation res(String string) {
		return new ResourceLocation(Hwell.MODID, string);
	}

	//

	public static BlockPos[] getBlocksTouching(World world, BlockPos pos) {
		BlockPos[] ret = new BlockPos[EnumFacing.VALUES.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = pos.offset(EnumFacing.VALUES[i]);
		}
		return ret;
	}

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

		if (!isCorrect && HwellConfig.general.isAutomaticMultiblocks) {
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
		return (requiredMeta == -1 || (inverse ? meta != requiredMeta : meta == requiredMeta));
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

	public static <T> List<T> listOfOne(T item) {
		LinkedList<T> list = new LinkedList<>();
		list.add(item);
		return list;
	}

	public static List<ItemStack> listOfOneItemStack(Item item) {
		return listOfOne(new ItemStack(item));
	}

	public static List<ItemStack> listOfOneItemStack(Block block) {
		return listOfOne(new ItemStack(block));
	}

	public static Irio toIrio(IBlockState blockState) {
		return new Irio(blockState.getBlock(), blockState.getBlock().getMetaFromState(blockState));
	}

	public static boolean isVanillaFluid(Block in) {
		return in == Blocks.WATER || in == Blocks.FLOWING_WATER || in == Blocks.LAVA || in == Blocks.FLOWING_LAVA;
	}

	public static FluidStack vanillaFluidBlockToFluidStack(Block in) {
		if (in == Blocks.WATER || in == Blocks.FLOWING_WATER)
			return new FluidStack(FluidRegistry.WATER, 1000);
		if (in == Blocks.LAVA || in == Blocks.FLOWING_LAVA)
			return new FluidStack(FluidRegistry.LAVA, 1000);
		return null;
	}

	// public static void setIngredients(IIngredients ingredients, Object[] ins,
	// Object[] outs) {
	// LinkedList<ItemStack> inList = new LinkedList<>();
	// for (Object object : ins) {
	//
	// }
	// LinkedList<ItemStack> outList = new LinkedList<>();
	//
	// if (ins instanceof Block[]) {
	// for (Block block : ((Block[]) ins))
	// inList.add(new ItemStack(block));
	// }
	// if (ins instanceof Item[]) {
	// for (Item item : ((Item[]) ins))
	// inList.add(new ItemStack(item));
	// }
	//
	// if (outs instanceof Block[]) {
	// for (Block block : ((Block[]) outs))
	// outList.add(new ItemStack(block));
	// }
	// if (outs instanceof Item[]) {
	// for (Item item : ((Item[]) outs))
	// outList.add(new ItemStack(item));
	// }
	// // ToStringer<ItemStack> outStringer = new ToStringer<ItemStack>() {
	// // @Override
	// // public String toString(ItemStack t) {
	// // // TODO Auto-generated method stub
	// // return null;
	// // }
	// // };
	// // ToStringer<ItemStack> toStringer = new ToStringer<ItemStack>() {
	// //
	// // @Override
	// // public String toString(ItemStack t) {
	// // return t.getCount() + "x" + t.getItem().getRegistryName() +
	// ((t.getMetadata()
	// // == 0) ? "" : t.getMetadata());
	// // }
	// // };
	// // System.out.println("New Separator Recipe: [" + toStringed(inList,
	// toStringer)
	// // + "] -> [" + toStringed(outList) + "]");
	//
	// ingredients.setInputs(ItemStack.class, inList);
	// ingredients.setOutputs(ItemStack.class, outList);
	// }

	private static <T> String toStringed(Collection<T> inList, ToStringer<T>... toStringer) {
		String s = "";
		for (T t : inList) {
			if (toStringer.length > 0)
				s += toStringer[0].toString(t) + ", ";
			else
				s += t.toString() + ", ";
		}
		return s.substring(0, s.length() - 3);
	}

	private interface ToStringer<T> {
		String toString(T t);
	}

	public static List<ItemStack> toItemStackList(Block[] blocks) {
		List<ItemStack> list = new LinkedList<>();
		for (Block block : blocks)
			list.add(new ItemStack(block));
		return list;
	}

	public static Item getRegisteredItem(String name) {
		Item item = Item.REGISTRY.getObject(new ResourceLocation(name));
		if (item == null) {
			throw new IllegalStateException("Invalid Item requested: " + name);
		} else {
			return item;
		}
	}

	public static Block getRegisteredBlock(String name) {
		Block block = Block.REGISTRY.getObject(new ResourceLocation(name));
		if (block == null) {
			throw new IllegalStateException("Invalid Block requested: " + name);
		} else {
			return block;
		}
	}

	private void makeTree(World world, BlockPos pos) {
		IBlockState wood = Blocks.LOG.getDefaultState();
		IBlockState leaf = Blocks.LEAVES.getDefaultState();
		world.setBlockState(pos.up(0), wood);
		world.setBlockState(pos.up(1), wood);
		world.setBlockState(pos.up(2), wood);
		world.setBlockState(pos.up(3), wood);
		world.setBlockState(pos.up(4), wood);

		// layer 2
		for (int[] d : new int[][] { //
				{ -2, -2 }, { -2, -1 }, { -2, 0 }, { -2, 1 }, { -2, 2 }, //
				{ -1, -2 }, { -1, -1 }, { -1, 0 }, { -1, 1 }, { -1, 2 }, //
				{ 0, -2 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 }, //
				{ 1, -2 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 1, 2 }, //
				{ 2, -2 }, { 2, -1 }, { 2, 0 }, { 2, 1 }, { 2, 2 }, //
		}) {
			BlockPos dpos = pos.add(d[0], 2, d[1]);
			if (world.isAirBlock(dpos))
				world.setBlockState(dpos, leaf);
		}

		// layer 3
		for (int[] d : new int[][] { //
				{ -2, -1 }, { -2, 0 }, { -2, 1 }, //
				{ -1, -2 }, { -1, -1 }, { -1, 0 }, { -1, 1 }, { -1, 2 }, //
				{ 0, -2 }, { 0, -1 }, { 0, 1 }, { 0, 2 }, //
				{ 1, -2 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 1, 2 }, //
				{ 2, -1 }, { 2, 0 }, { 2, 1 }, //
		}) {
			BlockPos dpos = pos.add(d[0], 3, d[1]);
			if (world.isAirBlock(dpos))
				world.setBlockState(dpos, leaf);
		}

		// layer 4
		for (int[] d : new int[][] { //
				{ -1, -1 }, { -1, 0 }, { -1, 1 }, //
				{ 0, -1 }, /*       */ { 0, 1 }, //
				{ 1, -1 }, { 1, 0 }, { 1, 1 }, //
		}) {
			BlockPos dpos = pos.add(d[0], 4, d[1]);
			if (world.isAirBlock(dpos))
				world.setBlockState(dpos, leaf);
		}

		// layer 5
		for (int[] d : new int[][] { //
				{ -1, 0 }, //
				{ 0, -1 }, { 0, 0 }, { 0, 1 }, //
				{ 1, 0 }, //
		}) {
			BlockPos dpos = pos.add(d[0], 5, d[1]);
			if (world.isAirBlock(dpos))
				world.setBlockState(dpos, leaf);
		}
	}

	public static Block blockAt(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock();
	}

	public static JsonElement readJson(String resource, boolean... internal) throws IOException {
		Gson gson = new Gson();

		InputStream in = (internal.length > 0 && internal[0]) ? //
				Hwell.class.getResourceAsStream(resource) : //
				new FileInputStream(new File(resource));
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		JsonElement je = gson.fromJson(reader, JsonElement.class);
		return je;
		// }
	}

	public static boolean clientIsDaytime(World world) {
		return world.getWorldTime() < 12550 || world.getWorldTime() > 23300;
	}

	public static Irio readJsonIrio(JsonObject o) {
		if (!o.has("data")) {
			o.addProperty("data", 0);
			ItemStack input = ShapedRecipes.deserializeItem(o, true);
			return new Irio(input.getItem());
		}
		ItemStack input = ShapedRecipes.deserializeItem(o, true);
		return new Irio(input.getItem(), input.getMetadata());
	}

	public static boolean hasEnchantment(ItemStack stack, Enchantment ench) {
		if (!stack.isItemEnchanted())
			return false;
		NBTTagList enchs = stack.getEnchantmentTagList();
		for (NBTBase nbtBase : enchs)
			if (((NBTTagCompound) nbtBase).getShort("id") == ench.getEnchantmentID(ench))
				return true;
		return false;
	}
}
