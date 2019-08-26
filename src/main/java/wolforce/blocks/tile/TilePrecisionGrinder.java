package wolforce.blocks.tile;

import java.util.HashMap;
import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import wolforce.Main;
import wolforce.Util;
import wolforce.Util.BlockWithMeta;
import wolforce.base.BlockEnergyConsumer;
import wolforce.blocks.BlockPrecisionGrinder;
import wolforce.blocks.BlockPrecisionGrinderEmpty;
import wolforce.items.ItemGrindingWheel;
import wolforce.recipes.RecipeGrinding;

public class TilePrecisionGrinder extends TileEntity implements ITickable {

	String[][][] multiblock = new String[][][] { //
			{ //
					{ "HB", "SL", "HB" }, //
					{ "PB", "00", "PB" }, //
					{ "HB", "PB", "HB" }, //
			}, { //
					{ null, "PB", null }, //
					{ "PB", "AR", "AR" }, //
					{ null, "PB", null } //

			}, { //
					{ null, "PB", null }, //
					{ "PB", "SL", "AR" }, //
					{ null, "PB", null } //

			} };

	int cooldown = 0;
	static final int MAX_COOLDOWN = 100;

	@Override
	public void update() {

		if (world.isRemote)
			return;

		if (cooldown > 0) {
			cooldown -= 5;
			return;
		}

		IBlockState block = world.getBlockState(pos);
		if (!(block.getBlock() instanceof BlockPrecisionGrinder))
			return;
		BlockPrecisionGrinder grinder = (BlockPrecisionGrinder) block.getBlock();

		EnumFacing facing = block.getValue(BlockPrecisionGrinder.FACING);

		List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, //
				new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1.5,
						pos.getZ() + 1));
		if (entities.size() > 0) {
			HashMap<String, BlockWithMeta> table = new HashMap<>();
			table.put("PB", new BlockWithMeta(Main.protection_block));
			table.put("HB", new BlockWithMeta(Main.heavy_block));
			table.put("SL", new BlockWithMeta(Main.slab_lamp, EnumFacing.DOWN.ordinal()));
			table.put("AR", new BlockWithMeta(Blocks.AIR));

			if (Util.isMultiblockBuilt(world, pos, facing, multiblock, table)) {
				ItemGrindingWheel gwheel = ((BlockPrecisionGrinder) block.getBlock()).grindingWheel;
				EntityItem entityItem = entities.get(0);
				ItemStack[] outputs = RecipeGrinding.getResult(gwheel, entityItem.getItem());

				if (outputs == null || !BlockEnergyConsumer.tryConsume(world, pos, grinder.getEnergyConsumption())) {
					cooldown = MAX_COOLDOWN;
					return;
				}

				spawnOutputs(outputs, facing);
				entityItem.getItem().shrink(1);
				cooldown = MAX_COOLDOWN;
				double prob = Math.pow(entityItem.getItem().getCount(), 4) / 33333300.0;
				if (entityItem.getItem().getCount() > 20 && Math.random() < prob)
					popGrindingWheel(entityItem, gwheel, facing);
			}
		}
	}

	private void spawnOutputs(ItemStack[] outputs, EnumFacing facing) {
		BlockPos newpos = pos.offset(facing);
		// EntityItem newentity = new EntityItem(world, newpos.getX() + .5,
		// newpos.getY(), newpos.getZ() + .5,
		// new ItemStack(result.getItem(), result.getCount(), result.getMetadata()));
		for (ItemStack stack : outputs)
			Util.spawnItem(world, newpos, stack.copy(), facing);
	}

	private void popGrindingWheel(EntityItem entityItem, ItemGrindingWheel gwheel, EnumFacing facing) {
		Util.spawnItem(world, pos.up(), new ItemStack(gwheel));
		world.setBlockState(pos,
				Main.precision_grinder_empty.getDefaultState().withProperty(BlockPrecisionGrinderEmpty.FACING, facing));
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
