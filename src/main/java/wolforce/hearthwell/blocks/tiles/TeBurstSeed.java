package wolforce.hearthwell.blocks.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import wolforce.hearthwell.bases.BlockEntityParent;
import wolforce.hearthwell.registries.TileEntities;
import wolforce.hearthwell.util.Util;

import java.util.Random;

public class TeBurstSeed extends BlockEntityParent {

	private ItemStackHandler inv;
	private float chaos = 0;

	public TeBurstSeed(BlockPos worldPosition, BlockState blockState) {
		super(TileEntities.burst_seed.get(), worldPosition, blockState);
		this.inv = new ItemStackHandler(1);
	}

	public void tick(Level level, BlockPos pos, BlockState blockState) {

		if (chaos > 0) {
			chaos *= .99F;
			chaos -= .00005F;// + Math.max(0, 1 - chaos);
		}
		if (chaos < .001)
			chaos = 0;

		if (level.isClientSide)
			return;

		if (getItem().getCount() >= 4 && Math.random() < getChaos() / 25) {
			burst(pos);
		}
	}

	private void burst(BlockPos pos) {
		if (level.isClientSide)
			return;
		Random rand = new Random();
		ItemStack stack = getItem().copy();
		double speed = 1.0 + (double) stack.getCount() / 10;
//		int count = stack.getCount() * stack.getCount() / 4;
		int _count = stack.getCount();
		double _count2 = 0.04 * _count * _count + 1.5 * _count - 2;
		int count = (int) Math.max(2 * _count, _count2);
		count = (int) (count + Math.random() * count);
		stack.setCount(1);
		double x = getBlockPos().getX() + 0.5;
		double y = getBlockPos().getY() + 0.5;
		double z = getBlockPos().getZ() + 0.5;
		for (int i = 0; i < count; i++) {
			ItemEntity entity = new ItemEntity(level, x, y, z, stack.copy()) {
				@Override
				public void tick() {
					if (tickCount > 10)
						super.tick();
					else {
						this.hasImpulse = true;
						this.xo = this.getX();
						this.yo = this.getY();
						this.zo = this.getZ();
						this.move(MoverType.SELF, this.getDeltaMovement());
						setDeltaMovement(getDeltaMovement().scale(0.9));
					}
				}
			};
			entity.setDefaultPickUpDelay();
			entity.setDeltaMovement(rand.nextGaussian() * speed, rand.nextGaussian() * speed, rand.nextGaussian() * speed);
			level.addFreshEntity(entity);
		}
		level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
		for (int i = 0; i < 10; i++)
			this.level.playSound((Player) null, pos, SoundEvents.FIREWORK_ROCKET_LARGE_BLAST, SoundSource.BLOCKS, 100000.0F, 20 + 5 * i);
	}

	public ItemStack getItem() {
		return inv.getStackInSlot(0);
	}

	public float getChaos() {
		if (getItem().getCount() >= 4)
			return chaos;
		return 0;
	}

	public ItemStack tryAddItem(Player player, ItemStack stack) {

		ItemStack item = getItem();

		if (!Util.isValid(item) || (Util.equalExceptAmount(item, stack) && item.getCount() < 64)) {

			ItemStack copy = stack.copy();
			copy.setCount(1);

			ItemStack ret = stack.copy();
			if (!player.isCreative())
				ret.shrink(1);

			setChanged();
			inv.insertItem(0, copy, false);

			int count = getItem().getCount();
			if (count > 4)
				chaos = (chaos * 2 + .005f * count);
			return ret;
		}

		if (!Util.isValid(stack)) {
			chaos = (chaos * 2 + .005f);
		}

		return stack;
	}

//	@Override
//	public CompoundTag saveAdditional(CompoundTag compound) {
//		CompoundTag tag = super.save(compound);
//		tag.put("inventory", this.inv.serializeNBT());
//		return tag;
//	}
//
//	@Override
//	public void load(BlockState state, CompoundTag tag) {
//		super.load(state, tag);
//		this.inv.deserializeNBT(tag.getCompound("inventory"));
//	}

//	@Override
//	public ClientboundBlockEntityDataPacket getUpdatePacket() {
//		CompoundTag tag = new CompoundTag();
//		tag.put("inventory", this.inv.serializeNBT());
//		return new ClientboundBlockEntityDataPacket(getBlockPos(), -1, tag);
//	}

//	@Override
//	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
//		CompoundTag tag = pkt.getTag();
//		this.inv.deserializeNBT(tag.getCompound("inventory"));
//		this.chaos = nbt.getFloat("chaos");
//	}

	@Override
	public void writePacketNBT(CompoundTag nbt) {
		nbt.put("inventory", this.inv.serializeNBT());
		nbt.putFloat("chaos", this.chaos);
	}

	@Override
	public void readPacketNBT(CompoundTag nbt) {
		this.inv.deserializeNBT(nbt.getCompound("inventory"));
		this.chaos = nbt.getFloat("chaos");
	}

}
