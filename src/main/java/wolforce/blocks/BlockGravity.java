package wolforce.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.blocks.base.BlockMachineBase;
import wolforce.blocks.tile.TileGravity;

public class BlockGravity extends BlockMachineBase implements ITileEntityProvider {

	private boolean isTileToRegister;

	public BlockGravity(String name, boolean isTileToRegister) {
		super(name);
		this.isTileToRegister = isTileToRegister;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileGravity();
	}
}
