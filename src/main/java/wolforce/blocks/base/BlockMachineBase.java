package wolforce.blocks.base;

import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import wolforce.MyBlock;

public class BlockMachineBase extends MyBlock {

	public BlockMachineBase(String name) {
		super(name, Material.ROCK);
		setHardness(1f);
		setResistance(1f);
		setHarvest("pickaxe", -1);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}
}
