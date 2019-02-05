package wolforce.blocks.tile;

import java.util.LinkedList;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import scala.util.Random;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.base.BlockEnergyConsumer;

public class TileNourisher extends TileEntity implements ITickable {

	final int range = 4;

	@Override
	public void update() {
		if (world.isRemote || world.getTotalWorldTime() % 200 == 0)
			return;

		BlockPos pos = getRandomPos();

		if (pos == null)
			return;
		
		if (!BlockEnergyConsumer.tryConsume(world, pos, Main.nourisher.getEnergyConsumption()))
			return;

		for (int y = 0; y <= 4; y++) {
			BlockPos posy = pos.add(0, y, 0);
			if (Util.blockAt(world, posy) instanceof BlockCrops)
				((BlockCrops) Util.blockAt(world, posy)).grow(world, posy, world.getBlockState(posy));
		}
	}

	private BlockPos getRandomPos() {
		LinkedList<BlockPos> list = new LinkedList<>();
		for (int dx = -4; dx <= 4; dx++) {
			for (int dz = -4; dz <= 4; dz++) {
				BlockPos ss = pos.add(dx, 0, dz);
				if (isCropInXZ(ss))
					list.add(ss);
			}
		}
		if (list.isEmpty())
			return null;
		return list.get((int) (Math.random() * list.size()));
	}

	private boolean isCropInXZ(BlockPos pos) {
		for (int y = 0; y <= 4; y++) {
			BlockPos posy = pos.add(0, y, 0);
			if (Util.blockAt(world, posy) instanceof BlockCrops)
				return true;
		}
		return false;
	}

}
