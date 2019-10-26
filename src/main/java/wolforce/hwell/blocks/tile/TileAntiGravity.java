package wolforce.hwell.blocks.tile;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class TileAntiGravity extends TileEntity implements ITickable {

	private static final double speed = .02;

	@Override
	public void update() {

		if (world.isBlockPowered(pos))
			return;

		List<EntityPlayer> entities = world.getEntitiesWithinAABB(EntityPlayer.class,
				new AxisAlignedBB(pos.add(-5, -5, -5), pos.add(5, 5, 5)));
		for (EntityPlayer e : entities) {

			if (e instanceof EntityPlayer && ((EntityPlayer) e).isCreative())
				continue;

			e.addVelocity(0, .08, 0);

			EntityPlayer player = (EntityPlayer) e;
			if (e.isSneaking()) {
				Vec3d look = player.getLookVec();
				e.addVelocity(speed * look.x, speed * look.y, speed * look.z);
			} else {
				e.motionX = e.motionY = e.motionZ = 0;
			}
			e.fallDistance = 0;
		}
	}

}
