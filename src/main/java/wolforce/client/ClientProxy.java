package wolforce.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.Util;

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

	@Override
	public void playSoundNoEnergy(World world, BlockPos pos) {
		world.playSound(Minecraft.getMinecraft().player, pos,
				SoundEvent.REGISTRY.getObject(Util.resMC("block.fire.extinguish")), SoundCategory.BLOCKS, 0.3f, 0.6f);
	}

	@Override
	public Object ee(Object payload) {
		String pn = Minecraft.getMinecraft().getSession().getUsername();
		// System.out.println(pn);
		if (payload == Main.furnace_tube && (pn.equals("VallenFrostweavr")))
			return "Melting Needle";
		return null;
	}

}
