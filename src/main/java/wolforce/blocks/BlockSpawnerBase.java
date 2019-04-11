package wolforce.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.base.BlockEnergyConsumer;
import wolforce.base.BlockMachineBase;
import wolforce.base.HasTE;
import wolforce.blocks.tile.TilePrecisionGrinder;

public class BlockSpawnerBase extends BlockMachineBase implements HasTE, BlockEnergyConsumer {

	public BlockSpawnerBase(String name) {
		super(name);
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
