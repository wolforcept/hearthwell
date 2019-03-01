package wolforce.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.Util;
import wolforce.items.ItemPowerCrystal;

public class EntityPower extends Entity {

	private static final AxisAlignedBB colbox = new AxisAlignedBB(0.0, -1.0, 0.0, 1.0, 1.0, 1.0);

	private static final DataParameter<Integer> POWER = EntityDataManager.<Integer>createKey(EntityPower.class,
			DataSerializers.VARINT);
	private static final DataParameter<Integer> MAX_POWER = EntityDataManager.<Integer>createKey(EntityPower.class,
			DataSerializers.VARINT);

	int nucleous;
	int relay;
	int screen;
	int range;
	float purity;

	public EntityPower(World worldIn) {
		super(worldIn);
	}

	// public EntityPower(World world, double x, double y, double z, double vx,
	// double vy, double vz) {
	public EntityPower(World world, double x, double y, double z) {
		this(world);
		setPosition(x, y, z);
		// addVelocity(vx, vy, vz);
	}

	@Override
	protected void entityInit() {
		width = .5f;
		height = 1.5f;
	}

	public boolean hasEnergy(int _energy, float erange) {
		if (erange > range)
			return false;
		return getPower() >= calcEnergy(_energy, erange);
	}

	public boolean tryConsume(int _energy, float erange) {
		if (erange > range)
			return false;
		int energy = calcEnergy(_energy, erange);
		if (getPower() >= energy) {
			decreasePower(energy);
			return true;
		}
		return false;
	}

	private int calcEnergy(int _energy, float erange) {
		float lossTotal = _energy / purity;
		float lossPerRange = (lossTotal - _energy) / HwellConfig.powerMaxRange;
		// System.out.println(_energy + " with " + erange + " -> " + (_energy + (int)
		// (lossPerRange * erange)));
		return _energy + (int) (lossPerRange * erange);
	}

	// boolean isMovingUp = true;

	private String customName = "";

	@Override
	public void onEntityUpdate() {
		this.world.profiler.startSection("entityBaseTick");
		this.prevDistanceWalkedModified = this.distanceWalkedModified;
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.prevRotationPitch = this.rotationPitch;
		this.prevRotationYaw = this.rotationYaw;
		this.firstUpdate = false;
		this.world.profiler.endSection();

		if (world.isRemote)
			return;

		// if (isAirBorne) {
		// // System.out.println("EntityPower.onEntityUpdate(" + motionY + ")");
		// // move(MoverType.SELF, 0, vY, 0);
		// // vY *= .7;
		// move(MoverType.SELF, 0, motionY, 0);
		// motionY *= .7;
		// // setPosition(posX, posY + vY, posZ);
		// if (motionY < .01) {
		// motionY = 0;
		// isAirBorne = false;
		// }
		// }

		if (getPower() <= 0 && HwellConfig.powerCrystalDropsWhenEmpty && !isDead)
			pop();

		setCustomNameTag(customName + getPower());
	}

	public int getPower() {
		return getDataManager().get(POWER);
	}

	public int getMaxPower() {
		return getDataManager().get(MAX_POWER);
	}

	public void setPower(int power, int maxPower) {
		getDataManager().set(POWER, (Integer) power);
		getDataManager().set(MAX_POWER, (Integer) maxPower);
	}

	private void decreasePower(int power) {
		if (getPower() >= power)
			getDataManager().set(POWER, getPower() - power);
	}

	private void pop() {
		ItemStack newstack = new ItemStack(Main.power_crystal);

		if (!newstack.hasTagCompound())
			newstack.setTagCompound(new NBTTagCompound());

		NBTTagCompound nbt = new NBTTagCompound();
		ItemPowerCrystal.setNBT(nbt, nucleous, relay, screen, getPower(), getMaxPower(), range, purity);
		newstack.getTagCompound().setTag("hwell", nbt);

		Util.spawnItem(world, new Vec3d(posX, posY + 1, posZ), newstack, 10, .0, .2, .0);
		setDead();
		setPositionAndUpdate(posX, posY, posZ);
	}

	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		if (world.isRemote)
			return EnumActionResult.SUCCESS;
		pop();
		return EnumActionResult.SUCCESS;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox() {
		return colbox;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entityIn) {
		return colbox;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}

	// /**
	// * Checks if the entity is in range to render.
	// */
	// @SideOnly(Side.CLIENT)
	// public boolean isInRangeToRenderDist(double distance) {
	// double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
	//
	// if (Double.isNaN(d0)) {
	// d0 = 4.0D;
	// }
	//
	// d0 = d0 * 64.0D;
	// return distance < d0 * d0;
	// }

	int power, maxPower;

	public void setLateEntityNBT(NBTTagCompound __nbt) {

		// make sure root is not null
		NBTTagCompound _nbt = __nbt == null ? new NBTTagCompound() : __nbt;

		// make sure root.hwell is not null
		NBTTagCompound nbt;
		if (_nbt.hasKey("hwell")) {
			nbt = _nbt.getCompoundTag("hwell");
		} else {
			nbt = new NBTTagCompound();
			_nbt.setTag("hwell", nbt);
		}

		nucleous = ItemPowerCrystal.getNucleous(nbt);
		relay = ItemPowerCrystal.getRelay(nbt);
		screen = ItemPowerCrystal.getScreen(nbt);

		int power = ItemPowerCrystal.getPower(nbt);
		int maxPower = ItemPowerCrystal.getMaxPower(nbt);

		getDataManager().register(POWER, power);
		getDataManager().register(MAX_POWER, maxPower);

		range = ItemPowerCrystal.getRange(nbt);
		purity = ItemPowerCrystal.getPurity(nbt);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound _nbt) {

		setLateEntityNBT(_nbt);
		// NBTTagCompound nbt = initRootNBT(_nbt);
		//
		// nucleous = ItemPowerCrystal.getNucleous(nbt);
		// relay = ItemPowerCrystal.getRelay(nbt);
		// screen = ItemPowerCrystal.getScreen(nbt);
		// setPower(ItemPowerCrystal.getPower(nbt), ItemPowerCrystal.getMaxPower(nbt));
		// range = ItemPowerCrystal.getRange(nbt);
		// purity = ItemPowerCrystal.getPurity(nbt);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound rootnbt) {
		// compound.setInteger("power", getPower());
		// setNBT(nbt);
		if (!rootnbt.hasKey("hwell"))
			rootnbt.setTag("hwell", new NBTTagCompound());
		ItemPowerCrystal.setNBT(rootnbt.getCompoundTag("hwell"), nucleous, relay, screen, getPower(), getMaxPower(), range, purity);
	}

	@Override
	public boolean getAlwaysRenderNameTag() {
		return true;
	}
}
