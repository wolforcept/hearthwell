package wolforce.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.blocks.base.BlockEnergyConsumer;
import wolforce.blocks.base.BlockMachineBase;
import wolforce.blocks.tile.TilePrecisionGrinder;

public class BlockSpawnerBase extends BlockMachineBase implements ITileEntityProvider, BlockEnergyConsumer {

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
		return HwellConfig.energyConsumptionGrinder;
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Consumes " + getEnergyConsumption() + " energy per operation." };
	}
}
