package wolforce.blocks.tile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.BlockCore;
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
		BlockCore.CoreType coreType = (BlockCore.CoreType) world.getBlockState(pos).getValue(BlockCore.SHARD);

		if (!(coreBlock instanceof BlockCore) || coreType == BlockCore.CoreType.core_base)
			return;

		RecipeCoring result = RecipeCoring.getResult((BlockCore) coreBlock, coreType.getShard());

		if (result == null)
			return;

		// for (int[] xyz : touches) {
		// BlockPos pos1 = pos.add(xyz[0], xyz[1], xyz[2]);

		for (BlockPos pos1 : Util.getBlocksTouching(world, pos)) {
			IBlockState state = world.getBlockState(pos1);

			// check if there is a block to consume at pos1
			if (result.canConsume(state)) {
				// at this point the core will certainly charge
				// (charges faster with more blocks surrounding it)
				particles(pos1);
				// if (charge % 10 == 0)
				// System.out.println(charge);
				if (Math.random() < .03) {
					if (isToDrop(pos1)) {
						// remove and drop the block on that pos1
						ItemStack drop = getSilkTouchDrop(world.getBlockState(pos1));
						world.notifyBlockUpdate(pos1, state, Blocks.AIR.getDefaultState(), 1 | 2);
						world.setBlockToAir(pos1);
						Util.spawnItem(world, pos1, drop);
					}
				}
				if (charge >= (int) ((MAX_CHARGE - 1) * getStabReduction(pos1))) {
					// System.out.println(result.result.getMetadata());
					IBlockState newBlock = result.possibleOutputs.length == 1 ? //
							Block.getBlockFromItem(result.possibleOutputs[0].getItem()).getDefaultState() //
							: result.getRandomResult().getDefaultState();
					changeGrafts(world, pos, BlockCore.getGraft(coreBlock), newBlock);
					world.setBlockState(pos, newBlock, 2 | 4); // im quite sure its a block
					return; // don't want to keep checking other touches
				} else {
					charge++;
					markDirty();
				}

			}
		}
	}

	private void changeGrafts(World world, BlockPos pos, Block core, IBlockState newBlock) {
		for (EnumFacing facing : EnumFacing.VALUES) {
			BlockPos pos2 = pos.offset(facing);
			if (world.getBlockState(pos2).getBlock() == core) {
				world.setBlockState(pos2, newBlock);
				changeGrafts(world, pos2, core, newBlock);
			}
		}
	}

	private boolean isToDrop(BlockPos pos1) {
		Block under = world.getBlockState(pos1.down()).getBlock();
		if (under.equals(Main.stabiliser_heavy))
			return false;
		if (under.equals(Main.stabiliser))
			return Math.random() < .25;
		if (under.equals(Main.stabiliser_light))
			return Math.random() < .5;
		return true;
	}

	private float getStabReduction(BlockPos pos1) {
		Block under = world.getBlockState(pos1.down()).getBlock();
		if (under.equals(Main.stabiliser_light))
			return .75f;
		if (under.equals(Main.stabiliser))
			return .625f;
		if (under.equals(Main.stabiliser_heavy))
			return .5f;
		return 1;
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
