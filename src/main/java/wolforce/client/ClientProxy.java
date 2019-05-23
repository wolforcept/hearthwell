package wolforce.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ClientProxy implements wolforce.Hwell.IProxy {

	@Override
	public void particle(World world, BlockPos pos, BlockPos pos2, Vec3d dir) {
		wolforce.client.MyParticle particle = wolforce.client.MyParticle.makeParticle(world, //
				pos2.getX() + .25 + Math.random() * .5, //
				pos2.getY() + .25 + Math.random() * .5, //
				pos2.getZ() + .25 + Math.random() * .5, //
				-dir.x, -dir.y, -dir.z, //
				pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5);
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}

}
