package wolforce.hwell.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.hwell.base.HasTE;
import wolforce.hwell.blocks.tile.TileShardLiquifier;

public class BlockShardLiquifier extends Block implements HasTE {

	public BlockShardLiquifier() {
		super(Material.CLAY);
		setHardness(2);
		setResistance(.5f);
		setHarvestLevel("pickaxe", -1);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileShardLiquifier();
	}

}
