package wolforce.blocks.tile;

import java.util.LinkedList;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.base.BlockEnergyConsumer;

public class TileNourisher extends TileEntity implements ITickable {

	private static final String COOLDOWN = "cooldown";
	private int cooldown = 0;
	final int range = HwellConfig.nourisherRange;

	@Override
	public void update() {
		if (world.isRemote)
			return;

		if (cooldown > 0) {
			cooldown--;
			return;
		}

		BlockPos pos = getRandomPos();

		if (pos == null)
			return;

		if (!BlockEnergyConsumer.tryConsume(world, pos, Main.nourisher.getEnergyConsumption()))
			return;

		for (int y = 0; y <= 4; y++) {
			BlockPos posy = pos.add(0, y, 0);
			if (Util.blockAt(world, posy) instanceof BlockCrops) {
				((BlockCrops) Util.blockAt(world, posy)).grow(world, posy, world.getBlockState(posy));
			}
		}
		cooldown = HwellConfig.nourisherCooldown;
	}

	private BlockPos getRandomPos() {
		LinkedList<BlockPos> list = new LinkedList<>();
		for (int dx = -4; dx <= 4; dx++) {
			for (int dz = -4; dz <= 4; dz++) {
				BlockPos ss = pos.add(dx, 0, dz);
				if (isGrowableCropInXZ(ss))
					list.add(ss);
			}
		}
		if (list.isEmpty())
			return null;
		return list.get((int) (Math.random() * list.size()));
	}

	private boolean isGrowableCropInXZ(BlockPos pos) {
		for (int y = 0; y <= 4; y++) {
			BlockPos posy = pos.add(0, y, 0);
			if (Util.blockAt(world, posy) instanceof IGrowable
					&& ((IGrowable) Util.blockAt(world, posy)).canGrow(world, posy, world.getBlockState(posy), world.isRemote))
				if(Util.blockAt(world, posy) instanceof BlockCrops || !HwellConfig.nourisherOnlyGrowCrops)
				return true;
		}
		return false;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger(COOLDOWN, cooldown);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		cooldown = compound.getInteger(COOLDOWN);
		super.readFromNBT(compound);
	}
}
