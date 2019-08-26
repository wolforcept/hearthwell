package wolforce;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ServerProxy implements Hwell.IProxy {

	@Override
	public void particle(World world, BlockPos pos, BlockPos pos2, Vec3d dir) {

	}

	@Override
	public void playSoundNoEnergy(World world, BlockPos pos) {
		world.playSound(pos.getX(), pos.getY(), pos.getZ(),
				SoundEvent.REGISTRY.getObject(Util.resMC("block.fire.extinguish")), SoundCategory.BLOCKS, 0.3f, 0.6f,
				true);
	}

	@Override
	public Object ee(Object payload) {
		return null;
	}

}
