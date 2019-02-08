package wolforce.blocks.tile;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import wolforce.HwellConfig;
import wolforce.Main;

public class TileGravity extends TileEntity implements ITickable {

	@Override
	public void update() {
		if (world.getBlockState(pos).getBlock() == Main.gravity_block || world.getBlockState(pos).getBlock() == Main.gravity_block_mini
				|| world.isBlockPowered(pos)) {
			int dist = world.getBlockState(pos).getBlock() == Main.gravity_block_mini ? //
					HwellConfig.gravityBlockDistanceMini : HwellConfig.gravityBlockDistance;
			List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class,
					new AxisAlignedBB(pos.add(-dist, -dist, -dist), pos.add(dist, dist, dist)));
			for (EntityItem entityItem : items) {
				entityItem.motionX += .01 * (pos.getX() + .5 - entityItem.posX);
				entityItem.motionY += .01 * (pos.getY() - entityItem.posY);
				entityItem.motionZ += .01 * (pos.getZ() + .5 - entityItem.posZ);
				entityItem.setNoDespawn();
				// double dir = Math.atan2(entityItem.posZ - pos.getZ(), entityItem.posX -
				// pos.getX());
				// entityItem.posX += Math.cos(dir);
				// entityItem.posY += Math.sin(dir);
			}
		}
	}

}
