package wolforce.hearthwell.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import wolforce.hearthwell.entities.EntityHearthWell;
import wolforce.hearthwell.particles.ParticleEnergyData;

import java.util.Random;

public class ItemPrayerLetter extends Item {

	public ItemPrayerLetter(Properties properties) {
		super(properties);
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		boolean ret = super.onEntityItemUpdate(stack, entity);
		int time = entity.tickCount;
		if (time > 45) {
			double t = Math.max(0, (100.0 - time) / 100.0);
			entity.setDeltaMovement(0, 0.04 + 0.1 * t, 0);

			if (entity.level().isClientSide) {
				Random rand = new Random();
				float s = .05f;
				float d = time / 300f;
				if (time > 60)
					for (int i = 0; i < Math.max(0, time / 20); i++) {
						Vec3 entityPos = entity.position().add(0, 0.3, 0);
						Vec3 pos = entityPos.add(rand.nextGaussian() * d, rand.nextGaussian() * d, rand.nextGaussian() * d);
						Vec3 vel = entityPos.subtract(pos).normalize().multiply(s, s, s);
						entity.level().addParticle(new ParticleEnergyData(0), pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);
					}
			}

			if (time > 300) {
				entity.kill();

				BlockPos pos = entity.blockPosition();

				LightningBolt lb = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level());
				lb.setVisualOnly(true);
				lb.setPos(pos.getX() + .5, pos.getY(), pos.getZ() + .5);
				entity.level().addFreshEntity(lb);

				EntityHearthWell hwEntity = new EntityHearthWell(entity.level());
				hwEntity.setPos(pos.getX() + .5, pos.getY() + .333, pos.getZ() + .5);
				entity.level().addFreshEntity(hwEntity);

			}
		}
		return ret;
	}
}
