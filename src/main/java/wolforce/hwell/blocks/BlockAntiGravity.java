package wolforce.hwell.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.hwell.base.BlockMachineBase;
import wolforce.hwell.base.HasTE;
import wolforce.hwell.blocks.tile.TileAntiGravity;

public class BlockAntiGravity extends BlockMachineBase implements HasTE {

	public BlockAntiGravity() {
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAntiGravity();
	}
}
