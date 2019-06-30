package wolforce.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.base.BlockMachineBase;
import wolforce.base.BlockWithDescription;
import wolforce.base.HasTE;
import wolforce.blocks.tile.TileGravity;

public class BlockGravity extends BlockMachineBase implements HasTE, BlockWithDescription {

	public BlockGravity(String name) {
		super(name);
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
