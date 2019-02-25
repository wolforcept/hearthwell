package wolforce.blocks.tile;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.BlockCore;
import wolforce.recipes.Irio;
import wolforce.recipes.RecipeCoring;

public class TileCore extends TileEntity implements ITickable {

	private static final int MAX_CHARGE = 500;

	int charge;

	@Override
	public void update() {
		IBlockState block = world.getBlockState(pos);
		if (!(block.getBlock() instanceof BlockCore)) {
			System.out.println("ERROR! core tile entity is not on a core block");
			return;
		}

		if (world.isRemote)
			return;

		Block coreBlock = (Block) world.getBlockState(pos).getBlock();
		BlockCore.CoreType coreType = (BlockCore.CoreType) world.getBlockState(pos).getValue(BlockCore.TYPE);

		if (coreType == BlockCore.CoreType.core_base)
			return;

		RecipeCoring result = RecipeCoring.getResult(coreBlock, coreType.getShard());

		if (result == null)
			return;

		// for (int[] xyz : touches) {
		// BlockPos pos1 = pos.add(xyz[0], xyz[1], xyz[2]);

		for (BlockPos pos1 : Util.getBlocksTouching(world, pos)) {
			IBlockState state = world.getBlockState(pos1);

			// check if there is a block to consume at pos1
			if (hasResult(result, state)) {
				// at this point the core will certainly charge
				// (charges faster with more blocks surrounding it)
				particles(pos1);
				// if (charge % 10 == 0)
				// System.out.println(charge);
				if (Math.random() < .015) {
					if (isToDrop(pos1)) {
						// remove and drop the block on that pos1
						ItemStack drop = getSilkTouchDrop(world.getBlockState(pos1));
						world.notifyBlockUpdate(pos1, state, Blocks.AIR.getDefaultState(), 1 | 2);
						world.setBlockToAir(pos1);
						Util.spawnItem(world, pos1, drop);
					}
				}
				if (charge == MAX_CHARGE - 1) {
					// System.out.println(result.result.getMetadata());
					world.setBlockState(pos, new Irio(result.result).getState(), 2 | 4); // im quite sure its a block
					return; // don't want to keep checking other touches
				} else {
					charge++;
					markDirty();
				}

			}
		}
	}

	private boolean isToDrop(BlockPos pos1) {
		Block under = world.getBlockState(pos1.down()).getBlock();
		if (under.equals(Main.stabiliser_heavy))
			return false;
		if (under.equals(Main.stabiliser))
			return Math.random() < .66;
		if (under.equals(Main.stabiliser_light))
			return Math.random() < .33;
		return false;
	}

	private boolean hasResult(RecipeCoring result, IBlockState state) {
		if (Arrays.asList(result.consumes).contains(new Irio(state.getBlock())))
			return true;
		if (Arrays.asList(result.consumes).contains(new Irio(state)))
			return true;
		return false;
	}

	private void particles(BlockPos pos) {
		// world.playSound(x, y, z, new SoundEv, category, volume, pitch,
		// distanceDelay);
		((WorldServer) world).spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, // particleType,
				false, // long distance
				pos.getX() + Math.random(), pos.getY() + 1, pos.getZ() + Math.random(), // xCoord, yCoord, zCoord,
				1, // numberOfParticles,
				0, -.2, 0, // xOffset, yOffset, zOffset,
				.01 * Math.random() // particleSpeed,
		);
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

	protected ItemStack getSilkTouchDrop(IBlockState state) {
		Item item = Item.getItemFromBlock(state.getBlock());
		int i = 0;

		if (item.getHasSubtypes()) {
			i = state.getBlock().damageDropped(state);
		}

		return new ItemStack(item, 1, i);
	}
}
