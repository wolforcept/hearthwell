package wolforce.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.blocks.base.BlockEnergyConsumer;
import wolforce.blocks.base.BlockMachineBase;
import wolforce.blocks.tile.TileNourisher;

public class BlockNourisher extends BlockMachineBase implements ITileEntityProvider, BlockEnergyConsumer {

	public BlockNourisher(String name) {
		super(name);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileNourisher();
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Makes crops around it grow faster.", "Consumes " + getEnergyConsumption() + " per operation." };
	}

	@Override
	public int getEnergyConsumption() {
		return HwellConfig.nourisherConsumption;
	}
}
