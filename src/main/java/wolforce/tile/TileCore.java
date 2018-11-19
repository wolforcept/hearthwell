package wolforce.tile;

import static wolforce.blocks.BlockCore.CoreType.*;
import static net.minecraft.init.Blocks.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.BlockCore;
import wolforce.blocks.BlockCore.CoreType;

public class TileCore extends TileEntity implements ITickable {

	private static final int MAX_CHARGE = 500;

	// ---------- CoreBlock, Corestate [Block outputBlock, Block ... consumedBlocks]
	private static HashMap<Block, HashMap<CoreType, Block[]>> recipes = new HashMap<>();

	public static void initRecipes() {
		{
			HashMap<CoreType, Block[]> stoneRecipes = new HashMap();
			stoneRecipes.put(core_c, new Block[] { COAL_BLOCK, LOG, LOG2 });
			stoneRecipes.put(core_fe, new Block[] { COBBLESTONE, STONE, SANDSTONE });
			stoneRecipes.put(core_au, new Block[] { IRON_BLOCK, IRON_ORE });
			stoneRecipes.put(core_h, new Block[] { AIR, AIR }); // TODO
			stoneRecipes.put(core_o, new Block[] { Main.compressed_clay, WATER });
			stoneRecipes.put(core_ca, new Block[] { BONE_BLOCK, GLASS, GLASS_PANE, STAINED_GLASS, STAINED_GLASS_PANE });
			stoneRecipes.put(core_p, new Block[] { REDSTONE_BLOCK, NETHERRACK, MAGMA });
			stoneRecipes.put(core_n, new Block[] { AIR, AIR }); // TODO
			recipes.put(Main.core_stone, stoneRecipes);
		}
		{
			HashMap<CoreType, Block[]> heatRecipes = new HashMap();
			heatRecipes.put(core_c, new Block[] { TNT, NETHERRACK });
			heatRecipes.put(core_fe, new Block[] { AIR, AIR }); // TODO
			heatRecipes.put(core_au, new Block[] { GLOWSTONE, GOLD_BLOCK, GOLD_ORE });
			heatRecipes.put(core_h, new Block[] { NETHERRACK, COAL_BLOCK });
			heatRecipes.put(core_o, new Block[] { SEA_LANTERN, SNOW, ICE, PACKED_ICE });
			heatRecipes.put(core_ca, new Block[] { QUARTZ_BLOCK, IRON_BLOCK });
			heatRecipes.put(core_p, new Block[] { MAGMA, NETHERRACK });
			heatRecipes.put(core_n, new Block[] { Main.compressed_clay, WATER, ICE, PACKED_ICE, SNOW, DIRT });
			recipes.put(Main.core_heat, heatRecipes);
		}
		{
			HashMap<CoreType, Block[]> greenRecipes = new HashMap();
			greenRecipes.put(core_c, new Block[] { LAPIS_BLOCK, STONE });
			greenRecipes.put(core_fe, new Block[] { GLASS, BONE_BLOCK });
			greenRecipes.put(core_au, new Block[] { COBBLESTONE, COAL_BLOCK });
			greenRecipes.put(core_h, new Block[] { IRON_BLOCK, GOLD_BLOCK });
			greenRecipes.put(core_o, new Block[] { WATER, Main.compressed_clay });
			greenRecipes.put(core_ca, new Block[] { Main.compressed_clay, WATER, ICE, PACKED_ICE, SNOW, DIRT });
			greenRecipes.put(core_p, new Block[] { Main.compressed_clay, WATER, ICE, PACKED_ICE, SNOW, DIRT });
			greenRecipes.put(core_n, new Block[] { Main.compressed_clay, WATER, ICE, PACKED_ICE, SNOW, DIRT });
			recipes.put(Main.core_green, greenRecipes);
		}
		{
			HashMap<CoreType, Block[]> sentiRecipes = new HashMap();
			sentiRecipes.put(core_c, new Block[] { LAPIS_BLOCK, STONE });
			sentiRecipes.put(core_fe, new Block[] { GLASS, BONE_BLOCK });
			sentiRecipes.put(core_au, new Block[] { COBBLESTONE, COAL_BLOCK });
			sentiRecipes.put(core_h, new Block[] { IRON_BLOCK, GOLD_BLOCK });
			sentiRecipes.put(core_o, new Block[] { WATER, Main.compressed_clay });
			sentiRecipes.put(core_ca, new Block[] { Main.compressed_clay, WATER, ICE, PACKED_ICE, SNOW, DIRT });
			sentiRecipes.put(core_p, new Block[] { Main.compressed_clay, WATER, ICE, PACKED_ICE, SNOW, DIRT });
			sentiRecipes.put(core_n, new Block[] { Main.compressed_clay, WATER, ICE, PACKED_ICE, SNOW, DIRT });
			recipes.put(Main.core_sentient, sentiRecipes);
		}
	}

	int charge;

	@Override
	public void update() {
		// IBlockState block = world.getBlockState(pos);
		// if (!(block.getBlock() instanceof BlockCore)) {
		// System.out.println("ERROR! core tile entity is not on a core block");
		// return;
		// }

		if (world.isRemote)
			return;

		Block coreBlock = (Block) world.getBlockState(pos).getBlock();
		CoreType coreType = (CoreType) world.getBlockState(pos).getValue(BlockCore.TYPE);

		if (coreType == CoreType.core_base)
			return;

		LinkedList<Block> consuming = getConsuming(coreBlock, coreType);

		if (consuming == null)
			return;

		// for (int[] xyz : touches) {
		// BlockPos pos1 = pos.add(xyz[0], xyz[1], xyz[2]);

		for (BlockPos pos1 : Util.getBlocksTouching(world, pos)) {
			Block b = world.getBlockState(pos1).getBlock();

			// check if there is a block to consume at pos1
			if (consuming.contains(b)) {
				// at this point the core will certainly charge
				// (charges faster with more blocks surrounding it)
				particlesandsounds(pos);
				particlesandsounds(pos1);
				if (charge % 10 == 0)
					System.out.println(charge);
				if (Math.random() < .015) {
					// remove and drop the block on that pos1
					ItemStack drop = getSilkTouchDrop(world.getBlockState(pos1));
					world.notifyBlockUpdate(pos1, b.getDefaultState(), Blocks.AIR.getDefaultState(), 1 | 2);
					world.setBlockToAir(pos1);
					Util.spawnItem(world, pos1, drop);
				}
				if (charge == MAX_CHARGE - 1) {
					Block result = getResult(coreBlock, coreType);
					if (result != null)
						world.setBlockState(pos, result.getDefaultState());
					return; // don't want to keep checking other touches
				} else {
					charge++;
					markDirty();
				}

			}
		}
	}

	private LinkedList<Block> getConsuming(Block coreBlock, CoreType coreType) {

		LinkedList<Block> consumes = new LinkedList<>();
		if (recipes.containsKey(coreBlock) && recipes.get(coreBlock).containsKey(coreType)) {
			Block[] blocks = recipes.get(coreBlock).get(coreType);
			for (int i = 1; i < blocks.length; i++) {
				consumes.add(blocks[i]);
			}
		}
		return consumes;

		// if (coreBlock == Main.coreStone)
		// switch (coreType) {
		// case iron:
		// return Main.crystalBlock;
		// case clay:
		// return Blocks.WATER;
		// case coal:
		// return Blocks.COBBLESTONE;
		// case diamond:
		// return Blocks.COAL_BLOCK;
		// default:
		// return null;
		// }
		// else if (coreBlock == Main.coreHeat)
		// switch (coreType) {
		// case base:
		// return Main.crystalBlock;
		// default:
		// return null;
		// }
		// else
		// return null;
	}

	private Block getResult(Block coreBlock, CoreType coreType) {
		return recipes.get(coreBlock).get(coreType)[0];
		//
		// switch (coreType) {
		// case iron:
		// return Blocks.IRON_BLOCK;
		// case clay:
		// return Main.compressedClay;
		// default:
		// throw new RuntimeException("shouldnt reach this code");
		// }
	}

	@SideOnly(Side.CLIENT)
	private void particlesandsounds(BlockPos pos) {
		// world.playSound(x, y, z, new SoundEv, category, volume, pitch,
		// distanceDelay);
		for (int i = 0; i < 10; i++) {
			world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, //
					pos.getX(), pos.getY(), pos.getZ(), Math.random() * .2 - .1, Math.random() * .2 - .1, Math.random() * .2 - .1);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("charge", charge);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		charge = compound.getInteger("charge");
	}

	// DO I REALLY NEED THIS

	// @Override
	// public SPacketUpdateTileEntity getUpdatePacket() {
	// NBTTagCompound compound = new NBTTagCompound();
	// // Write your data into the nbtTag
	// compound.setInteger("charge", charge);
	// return new SPacketUpdateTileEntity(getPos(), 1, compound);
	// }
	//
	// @Override
	// public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
	// NBTTagCompound compound = pkt.getNbtCompound();
	// // Handle your Data
	// charge = compound.getInteger("charge");
	// }

	protected ItemStack getSilkTouchDrop(IBlockState state) {
		Item item = Item.getItemFromBlock(state.getBlock());
		int i = 0;

		if (item.getHasSubtypes()) {
			i = state.getBlock().getMetaFromState(state);
		}

		return new ItemStack(item, 1, i);
	}

}
