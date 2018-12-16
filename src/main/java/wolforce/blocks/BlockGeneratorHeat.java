package wolforce.blocks;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.MyBlock;
import wolforce.blocks.base.BlockEnergyProvider;
import wolforce.tile.TileGeneratorHeat;

public class BlockGeneratorHeat extends MyBlock implements BlockEnergyProvider, ITileEntityProvider {

	public static final int E = 15; // energy per temp value
	public static final PropertyInteger TEMP = PropertyInteger.create("temp", 0, 9);

	public BlockGeneratorHeat(String name) {
		super(name, Material.ROCK);
		setHardness(1);
		setResistance(5);
		setDefaultState(getDefaultState().withProperty(TEMP, 0));
	}

	@Override
	public boolean hasEnergy(World world, BlockPos pos, int energy) {
		return world.getBlockState(pos).getValue(TEMP) * E >= energy;
	}

	@Override
	public void consume(World world, BlockPos pos, int energyDecrease) {
		int energy = world.getBlockState(pos).getValue(TEMP);
		int newEnergy = energy - energyDecrease / E - 1;
		world.setBlockState(pos, getDefaultState().withProperty(TEMP, newEnergy));
	}
	// BLOCK STATES

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TEMP);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TEMP, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TEMP);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileGeneratorHeat();
	}
}
