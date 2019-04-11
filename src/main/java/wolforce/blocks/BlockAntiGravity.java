package wolforce.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.base.BlockMachineBase;
import wolforce.base.HasTE;
import wolforce.blocks.tile.TileAntiGravity;

public class BlockAntiGravity extends BlockMachineBase implements HasTE {

	public BlockAntiGravity(String name) {
		super(name);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAntiGravity();
	}
}
