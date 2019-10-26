package wolforce.hwell;

import net.minecraft.block.material.Material;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class UnjoinableEntityItem extends EntityItem {

	private int age, pickupDelay;

	public UnjoinableEntityItem(World worldIn, double x, double y, double z, ItemStack stack, int pickupDelay) {
		super(worldIn, x, y, z, stack);
		this.pickupDelay = pickupDelay;
		this.age = 0;
	}

	public void onUpdate() {
		if (getItem().getItem().onEntityItemUpdate(this))
			return;
		if (this.getItem().isEmpty()) {
			this.setDead();
		} else {

			if (this.pickupDelay > 0 && this.pickupDelay != 32767) {
				--this.pickupDelay;
			}

			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			double d0 = this.motionX;
			double d1 = this.motionY;
			double d2 = this.motionZ;

			if (!this.hasNoGravity()) {
				this.motionY -= 0.03999999910593033D;
			}

			if (this.world.isRemote) {
				this.noClip = false;
			} else {
				this.noClip = this.pushOutOfBlocks(this.posX,
						(this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D, this.posZ);
			}

			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
			boolean flag = (int) this.prevPosX != (int) this.posX || (int) this.prevPosY != (int) this.posY
					|| (int) this.prevPosZ != (int) this.posZ;

			if (flag || this.ticksExisted % 25 == 0) {
				if (this.world.getBlockState(new BlockPos(this)).getMaterial() == Material.LAVA) {
					this.motionY = 0.20000000298023224D;
					this.motionX = (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
					this.motionZ = (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
					this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
				}

			}

			float f = 0.98F;

			if (this.onGround) {
				BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1,
						MathHelper.floor(this.posZ));
				net.minecraft.block.state.IBlockState underState = this.world.getBlockState(underPos);
				f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.98F;
			}

			this.motionX *= (double) f;
			this.motionY *= 0.9800000190734863D;
			this.motionZ *= (double) f;

			if (this.onGround) {
				this.motionY *= -0.5D;
			}

			if (this.age != -32768) {
				++this.age;
			}

			this.handleWaterMovement();

			if (!this.world.isRemote) {
				double d3 = this.motionX - d0;
				double d4 = this.motionY - d1;
				double d5 = this.motionZ - d2;
				double d6 = d3 * d3 + d4 * d4 + d5 * d5;

				if (d6 > 0.01D) {
					this.isAirBorne = true;
				}
			}

			ItemStack item = this.getItem();

			if (!this.world.isRemote && this.age >= lifespan) {
				int hook = net.minecraftforge.event.ForgeEventFactory.onItemExpire(this, item);
				if (hook < 0)
					this.setDead();
				else
					this.lifespan += hook;
			}
			if (item.isEmpty()) {
				this.setDead();
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.removeTag("Age");
		compound.setShort("Age", (short) this.age);
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.age = compound.getShort("Age");
	}

}