package wolforce.tile;

import static wolforce.blocks.BlockCore.CoreType.*;
import static net.minecraft.init.Blocks.*;

import java.util.Arrays;
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
import wolforce.recipes.Iri;
import wolforce.recipes.RecipeCoring;

public class TileCore extends TileEntity implements ITickable {

	private static final int MAX_CHARGE = 500;

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

		RecipeCoring result = RecipeCoring.getResult(coreBlock, coreType);

		if (result == null)
			return;

		// for (int[] xyz : touches) {
		// BlockPos pos1 = pos.add(xyz[0], xyz[1], xyz[2]);

		for (BlockPos pos1 : Util.getBlocksTouching(world, pos)) {
			IBlockState state = world.getBlockState(pos1);

			// check if there is a block to consume at pos1
			if (Arrays.asList(result.consumes).contains(new Iri(state))) {
				// at this point the core will certainly charge
				// (charges faster with more blocks surrounding it)
				particlesandsounds(pos);
				particlesandsounds(pos1);
				if (charge % 10 == 0)
					System.out.println(charge);
				if (Math.random() < .015) {
					// remove and drop the block on that pos1
					ItemStack drop = getSilkTouchDrop(world.getBlockState(pos1));
					world.notifyBlockUpdate(pos1, state, Blocks.AIR.getDefaultState(), 1 | 2);
					world.setBlockToAir(pos1);
					Util.spawnItem(world, pos1, drop);
				}
				if (charge == MAX_CHARGE - 1) {
					world.setBlockState(pos, result.result.getBlock().getDefaultState(), 2 | 4); // im quite sure its a block
					return; // don't want to keep checking other touches
				} else {
					charge++;
					markDirty();
				}

			}
		}
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

	protected ItemStack getSilkTouchDrop(IBlockState state) {
		Item item = Item.getItemFromBlock(state.getBlock());
		int i = 0;

		if (item.getHasSubtypes()) {
			i = state.getBlock().getMetaFromState(state);
		}

		return new ItemStack(item, 1, i);
	}

}
