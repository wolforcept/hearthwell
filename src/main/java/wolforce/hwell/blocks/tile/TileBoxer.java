package wolforce.hwell.blocks.tile;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import wolforce.hwell.HwellConfig;
import wolforce.hwell.recipes.RecipeBoxer;
import wolforce.mechanics.Util;

public class TileBoxer extends TileEntity implements ITickable {

	@Override
	public void update() {

		if (world.isRemote || world.isBlockPowered(pos) || world.isAirBlock(pos.up()))
			return;

		Block block = Util.blockAt(world, pos.up());
		Block result = RecipeBoxer.getResult(block);
		if (result != null) {
			world.destroyBlock(pos.up(), false);
			Util.spawnItem(world, pos.up(), new ItemStack(result, HwellConfig.machines.boxerNrOfBoxesSpawned), 0.0, 0.2,
					0.0);
		}
	}
}
