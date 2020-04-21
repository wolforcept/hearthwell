package wolforce.hwell.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.hwell.HwellConfig;
import wolforce.hwell.base.BlockEnergyConsumer;
import wolforce.hwell.base.BlockMachineBase;
import wolforce.hwell.base.HasTE;
import wolforce.hwell.blocks.tile.TilePrecisionGrinder;

public class BlockSpawnerBase extends BlockMachineBase implements HasTE, BlockEnergyConsumer {

	public BlockSpawnerBase() {
		super();
	}

	//

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePrecisionGrinder();
	}

	@Override
	public int getEnergyConsumption() {
		return HwellConfig.machines.grinderConsumption;
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Consumes " + getEnergyConsumption() + " energy per operation." };
	}
}
