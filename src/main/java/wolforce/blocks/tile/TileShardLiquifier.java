package wolforce.blocks.tile;

import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import wolforce.Main;
import wolforce.Util.BlockWithMeta;
import wolforce.recipes.RecipeShardLiquifier;

public class TileShardLiquifier extends TileEntity implements ITickable {

	static final String[][][] multiblock = new String[][][] { //
			{ //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, "HP", "HP", "HP", null, null }, //
					{ null, null, "HP", "00", "HP", null, null }, //
					{ null, null, "HP", "HP", "HP", null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
			}, //
			{ //
					{ null, null, null, null, null, null, null }, //
					{ null, "HB", "HP", "HP", "HP", "HB", null }, //
					{ null, "HP", null, null, null, "HP", null }, //
					{ null, "HP", null, null, null, "HP", null }, //
					{ null, "HP", null, null, null, "HP", null }, //
					{ null, "HB", "HP", "HP", "HP", "HB", null }, //
					{ null, null, null, null, null, null, null }, //
			}, //
			{ //
					{ "HB", "HP", "HP", "HP", "HP", "HP", "HB" }, //
					{ "HP", "L0", "L0", "L0", "L0", "L0", "HP" }, //
					{ "HP", "L0", "L0", "L0", "L0", "L0", "HP" }, //
					{ "HP", "L0", "L0", "L0", "L0", "L0", "HP" }, //
					{ "HP", "L0", "L0", "L0", "L0", "L0", "HP" }, //
					{ "HP", "L0", "L0", "L0", "L0", "L0", "HP" }, //
					{ "HB", "HP", "HP", "HP", "HP", "HP", "HB" }, //
			}, //
			{ //
					{ "FT", null, null, null, null, null, "FT" }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ "FT", null, null, null, null, null, "FT" }, //
			}, //
			{ //
					{ "FT", null, null, null, null, null, "FT" }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ "FT", null, null, null, null, null, "FT" }, //
			}, //
			{ //
					{ "LC", null, null, null, null, null, "LC" }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ null, null, null, null, null, null, null }, //
					{ "LC", null, null, null, null, null, "LC" }, //
			}, //
	};

	@Override
	public void update() {

		if (world.isRemote || world.isBlockPowered(pos))
			return;

		tryTransform(pos.up());

	}

	private void tryTransform(BlockPos _pos) {
		if (RecipeShardLiquifier.getResult(world.getBlockState(_pos)) != null) {
			HashMap<String, BlockWithMeta> table = new HashMap<>();
			table.put("ON", new BlockWithMeta(Main.smooth_onyx));
			table.put("MO", new BlockWithMeta(Main.moonstone));
			table.put("CY", new BlockWithMeta(Main.crystal_block));
			table.put("GV", new BlockWithMeta(Main.gravity_block_mini));
			table.put("AR", new BlockWithMeta(Blocks.AIR));

			List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.down()));

			// if (!entities.isEmpty() && Util.isMultiblockBuilt(world, pos,
			// EnumFacing.NORTH, multiblock, table)) {
			// for (EntityItem entityItem : entities) {
			// int powerGained = tryConsume(powerCrystal, entityItem.getItem());
			// if (powerGained > 0) {
			// cooldown = HwellConfig.machines.chargerCooldown;
			// return;
			// }
			// }
			// }
		}
	}
}
