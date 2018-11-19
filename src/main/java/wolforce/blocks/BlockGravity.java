package wolforce.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.MyBlock;
import wolforce.tile.TileGravity;

public class BlockGravity extends MyBlock implements ITileEntityProvider {

	private boolean isToRegister;

	public BlockGravity(String name, boolean isToRegister) {
		super(name, Material.ROCK);
		this.isToRegister = isToRegister;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileGravity();
	}
}
