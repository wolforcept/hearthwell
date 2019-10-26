package wolforce.hwell.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.hwell.HwellConfig;
import wolforce.hwell.Main;
import wolforce.hwell.base.BlockMachineBase;
import wolforce.hwell.base.BlockWithDescription;
import wolforce.hwell.base.HasTE;
import wolforce.hwell.blocks.tile.TileGravity;

public class BlockGravity extends BlockMachineBase implements HasTE, BlockWithDescription {

	public BlockGravity() {
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileGravity();
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Maximum range: " + (this == Main.gravity_block ? HwellConfig.machines.gravityBlockRange
				: HwellConfig.machines.gravityBlockRangeMini) };
	}
}
