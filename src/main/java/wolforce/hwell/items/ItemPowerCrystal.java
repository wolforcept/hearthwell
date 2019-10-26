package wolforce.hwell.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import wolforce.hwell.Main;
import wolforce.hwell.base.MyItem;
import wolforce.hwell.entities.EntityPower;
import wolforce.hwell.recipes.RecipePowerCrystalOld;

public class ItemPowerCrystal extends MyItem {

	// NBT DATA IDS
	public static final String nucleous = "power_nucleous";
	public static final String relay = "power_relay";
	public static final String screen = "power_screen";
	public static final String power = "power_power";
	public static final String max_power = "power_max_power";
	public static final String range = "power_range";
	public static final String purity = "power_purity";

	public ItemPowerCrystal(String name) {
		super(name);
		setMaxStackSize(1);
		// addPropertyOverride(Util.res(nucleous), new IItemPropertyGetter() {
		//
		// @Override
		// public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn)
		// {
		// return stack.serializeNBT().getFloat(nucleous);
		// }
		// });
		// addPropertyOverride(Util.res(relay), new IItemPropertyGetter() {
		//
		// @Override
		// public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn)
		// {
		// return stack.serializeNBT().getFloat(relay);
		// }
		// });
		// addPropertyOverride(Util.res(screen), new IItemPropertyGetter() {
		//
		// @Override
		// public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn)
		// {
		// return stack.serializeNBT().getFloat(screen);
		// }
		// });
	}

	// @Override
	// public boolean hasEffect(ItemStack stack) {
	// return true;
	// }

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (RecipePowerCrystalOld.isInnited() && stack.hasTagCompound() && stack.getTagCompound().hasKey("hwell")) {
			NBTTagCompound nbt = stack.getSubCompound("hwell");

			int nuc = getNucleous(nbt);
			int rel = getRelay(nbt);
			int scr = getScreen(nbt);

			tooltip.add("Nucleous: " + RecipePowerCrystalOld.getNucleous(nuc).name);
			tooltip.add("Relay: " + RecipePowerCrystalOld.getRelay(rel).name);
			tooltip.add("Screen: " + RecipePowerCrystalOld.getScreen(scr).name);

			int pow = getPower(nbt);
			int max = getMaxPower(nbt);
			int rng = getRange(nbt);
			float pur = getPurity(nbt);

			tooltip.add("Power: " + pow + "/" + max);
			tooltip.add("Range: " + rng);
			if (pur == 1)
				tooltip.add("Purity: 100%");
			else
				tooltip.add("Purity: " + (pur + "").substring(2, Math.min(4, (pur + "").length())) + "%");

			// tooltip.add(stack.getTagCompound() + "");
		}
	}

	// public static byte[] float2ByteArray(float value) {
	// return ByteBuffer.allocate(4).putFloat(value).array();
	// }

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer p, EnumHand handIn) {

		// FIRST CHECK IF LOOKING AT A CHARGER
		if (!p.isSneaking()) {
			RayTraceResult raytraceresult = this.rayTrace(worldIn, p, false);
			if (raytraceresult != null && raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
				// System.out.println(worldIn.getBlockState(raytraceresult.getBlockPos()).getBlock());
				if (worldIn.getBlockState(raytraceresult.getBlockPos()).getBlock() == Main.charger)
					return new ActionResult<ItemStack>(EnumActionResult.PASS, p.getHeldItem(handIn));
			}
		}

		if (worldIn.isRemote)
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, ItemStack.EMPTY);

		Vec3d v = p.getLookVec();

		EntityPower e = new EntityPower(worldIn, //
				p.posX + v.x, p.posY + .5, p.posZ + v.z);
		// EntityPower e = new EntityPower(worldIn, //
		// p.posX + v.x, p.posY + .5, p.posZ + v.z, //
		// 0, .5, 0);
		e.setLateEntityNBT(p.getHeldItem(handIn).getTagCompound());
		p.getEntityWorld().spawnEntity(e);

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, ItemStack.EMPTY);
	}

	// @Override
	// public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound
	// _nbt) {
	// if (RecipePowerCrystal.isInnited())
	// setStackNBT(stack);
	// return null;
	// }

	// public static ItemStack setStackNBT(ItemStack stack) {
	// return setStackNBT(stack, 0, 0, 0);
	// }

	// public static ItemStack setStackNBT(ItemStack stack, int nuc, int rel, int
	// scr) {
	// NBTTagCompound nbt = new NBTTagCompound();
	// setNBT(nbt, nuc, rel, scr);
	// stack.setTagCompound(nbt);
	// return stack;
	// }

	public static void setHwellNBT(NBTTagCompound nbt, int nuc, int rel, int scr) {
		int max = RecipePowerCrystalOld.calcMaxPower(nuc, rel, scr);
		int pow = RecipePowerCrystalOld.calcPower(max);
		int rng = RecipePowerCrystalOld.calcRange(nuc, rel, scr);
		float pur = RecipePowerCrystalOld.calcPurity(nuc, rel, scr);
		setNBT(nbt, nuc, rel, scr, pow, max, rng, pur);
	}

	public static void setNBT(NBTTagCompound nbt, int nuc, int rel, int scr, int pow, int max, int rng, float pur) {
		nbt.setByte(nucleous, (byte) nuc);
		nbt.setByte(relay, (byte) rel);
		nbt.setByte(screen, (byte) scr);
		nbt.setInteger(power, pow);
		nbt.setInteger(max_power, max);
		nbt.setInteger(range, rng);
		nbt.setFloat(purity, pur);
	}

	public static int getNucleous(NBTTagCompound nbt) {
		return (int) nbt.getByte(nucleous);
	}

	public static int getRelay(NBTTagCompound nbt) {
		return (int) nbt.getByte(relay);
	}

	public static int getScreen(NBTTagCompound nbt) {
		return (int) nbt.getByte(screen);
	}

	public static int getPower(NBTTagCompound nbt) {
		return nbt.getInteger(power);
	}

	public static int getMaxPower(NBTTagCompound nbt) {
		return nbt.getInteger(max_power);
	}

	public static int getRange(NBTTagCompound nbt) {
		return nbt.getInteger(range);
	}

	public static float getPurity(NBTTagCompound nbt) {
		return nbt.getFloat(purity);
	}

	public static void setPower(NBTTagCompound nbt, int newPower) {
		nbt.setInteger(power, newPower);
	}

}
