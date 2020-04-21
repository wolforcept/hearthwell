package wolforce.hwell.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.hwell.HwellConfig;
import wolforce.hwell.base.BlockEnergyConsumer;
import wolforce.hwell.base.BlockMachineBase;
import wolforce.hwell.base.HasTE;
import wolforce.hwell.blocks.tile.TileNourisher;

public class BlockNourisher extends BlockMachineBase implements HasTE, BlockEnergyConsumer {

	public BlockNourisher() {
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileNourisher();
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Makes crops around it grow faster.",
				"Consumes " + getEnergyConsumption() + " per operation." };
	}

	@Override
	public int getEnergyConsumption() {
		return HwellConfig.machines.nourisherConsumption;
	}
}
