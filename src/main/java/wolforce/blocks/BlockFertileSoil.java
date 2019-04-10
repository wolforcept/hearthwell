package wolforce.blocks;

import java.util.LinkedList;
import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import wolforce.MyBlock;
import wolforce.blocks.base.BlockWithDescription;
import wolforce.blocks.base.HasTE;
import wolforce.blocks.tile.TileFertileSoil;

public class BlockFertileSoil extends MyBlock implements HasTE, BlockWithDescription {

	private static final int[][] blocksnear = { //
			{ -1, -1, -1 }, { -1, -1, 00 }, { -1, -1, 01 }, //
			{ 00, -1, -1 }, /*-----------*/ { 00, -1, 01 }, //
			{ 01, -1, -1 }, { 01, -1, 00 }, { 01, -1, 01 }, //

			{ -1, 00, -1 }, { -1, 00, 00 }, { -1, 00, 01 }, //
			{ 00, 00, -1 }, /*-----------*/ { 00, 00, 01 }, //
			{ 01, 00, -1 }, { 01, 00, 00 }, { 01, 00, 01 }, //

			{ -1, 01, -1 }, { -1, 01, 00 }, { -1, 01, 01 }, //
			{ 00, 01, -1 }, /*-----------*/ { 00, 01, 01 }, //
			{ 01, 01, -1 }, { 01, 01, 00 }, { 01, 01, 01 }, //
	};

	public BlockFertileSoil(String name) {
		super(name, Material.GRASS, true);
		setSoundType(SoundType.GROUND);
		setHardness(.2f);
		setHarvestLevel("shovel", -1);
	}

	@Override
	public void randomTick(World world, BlockPos _pos, IBlockState state, Random random) {
		if (world.isRemote)
			return;
		LinkedList<BlockPos> nearDirtBlocks = new LinkedList<>();
		for (int[] xyz : blocksnear) {
			BlockPos pos = new BlockPos(xyz[0], xyz[1], xyz[2]);
			if (world.getBlockState(pos).getBlock() == Blocks.DIRT) {
				nearDirtBlocks.add(pos);
			}
		}
		if (nearDirtBlocks.isEmpty())
			return;
		BlockPos dirtpos = nearDirtBlocks.get((int) (nearDirtBlocks.size() * Math.random()));
		world.setBlockState(dirtpos, Blocks.GRASS.getDefaultState());
	}

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
		return plantable instanceof BlockSapling || plantable instanceof BlockBush;
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Increases the speed at which saplings grow over it.", "May turn dirt around it into grass." };
	}
	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileFertileSoil();
	}
}
