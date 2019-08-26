package wolforce.blocks.tile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.Util;
import wolforce.Util.BlockWithMeta;
import wolforce.base.BlockEnergyConsumer;
import wolforce.blocks.BlockLightCollector;
import wolforce.blocks.BlockTube;
import wolforce.recipes.RecipePuller;

public class TilePuller extends TileEntity implements ITickable {

	String[][][] multiblock = new String[][][] { //
			{ //
					{ "PB", "PB", "PB" }, //
					{ "PB", "00", "PB" }, //
					{ "PB", "PB", "PB" }, //
			}, { //
					{ "HB", "HB", "HB" }, //
					{ "HB", "L0", "HB" }, //
					{ "HB", "HB", "HB" } //

			}, { //
					{ "TU", null, "TU" }, //
					{ null, null, null }, //
					{ "TU", null, "TU" } //

			}, { //
					{ "LC", null, "LC" }, //
					{ null, null, null }, //
					{ "LC", null, "LC" } //

			} };

	String[][][] multiblock2 = new String[][][] { //
			{ //
					{ null, "PB", null }, //
					{ "PB", "00", "PB" }, //
					{ null, "PB", null }, //
			} };

	static final int MAX_COOLDOWN = HwellConfig.machines.pullerDelay;
	int cooldown = MAX_COOLDOWN;

	@Override
	public void update() {

		if (world.isRemote || isPowered())
			return;

		if (cooldown > 0) {
			cooldown -= 5;
			return;
		}

		HashMap<String, BlockWithMeta> table = new HashMap<>();
		table.put("PB", new BlockWithMeta(Main.heavy_protection_block));
		table.put("HB", new BlockWithMeta(Main.heat_block));
		table.put("L0", new BlockWithMeta(Main.liquid_souls_block));
		table.put("TU", new BlockWithMeta(Main.furnace_tube, Main.furnace_tube.getMetaFromState(//
				Main.furnace_tube.getDefaultState().withProperty(BlockTube.AXIS, EnumFacing.Axis.Y))));
		table.put("LC", new BlockWithMeta(Main.light_collector, Main.light_collector.getMetaFromState(//
				Main.light_collector.getDefaultState().withProperty(BlockLightCollector.CHARGE, 3))));

		// get shard in liquid
		List<EntityItem> entityList = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.up()));
		List<ItemStack> filtersInLiquid = new LinkedList<>();
		if (!entityList.isEmpty()) {
			for (EntityItem entityItem : entityList) {
				if (RecipePuller.isFilter(entityItem.getItem()))
					filtersInLiquid.add(entityItem.getItem());
			}
		}

		if (Util.isMultiblockBuilt(world, pos, EnumFacing.EAST, multiblock, table)) {
			// ItemShard shard = ((ItemShard) );
			ItemStack result = RecipePuller.getRandomPull(filtersInLiquid);
			if (Util.isValid(result)) {

				// ENERGY CONSUMPTION
				if (!BlockEnergyConsumer.tryConsume(world, pos, Main.puller.getEnergyConsumption())) {
					cooldown = MAX_COOLDOWN - 50 * getNrExtraLayers(table);
					cooldown /= 2;
					return;
				}
				cooldown = MAX_COOLDOWN - 50 * getNrExtraLayers(table);
				Util.spawnItem(world, pos, result, 0, .4, 0);
			}
		}
	}

	private boolean isPowered() {
		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				if (world.isBlockPowered(new BlockPos(pos.getX() + dx, pos.getY() + 1, pos.getZ() + dz)))
					return true;
			}
		}
		return world.isBlockPowered(pos);
	}

	private int getNrExtraLayers(HashMap<String, BlockWithMeta> table) {
		BlockPos posn = pos.down();
		int n = 0;
		while (Util.blockAt(world, posn).equals(Main.furnace_tube) && //
				Util.isMultiblockBuilt(world, posn, EnumFacing.EAST, multiblock2, table)) {
			n++;
			posn = posn.down();
		}
		return n;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("cooldown", cooldown);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		cooldown = compound.getInteger("cooldown");
	}
}
