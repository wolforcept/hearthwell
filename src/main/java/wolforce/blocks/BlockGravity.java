package wolforce.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.base.BlockMachineBase;
import wolforce.base.HasTE;
import wolforce.blocks.tile.TileGravity;

public class BlockGravity extends BlockMachineBase implements HasTE {

	public BlockGravity(String name) {
		super(name);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileGravity();
	}
}
