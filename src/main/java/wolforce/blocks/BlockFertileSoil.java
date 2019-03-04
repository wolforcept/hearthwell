package wolforce.blocks;

import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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

	public BlockFertileSoil(String name) {
		super(name, Material.GRASS, true);
		setSoundType(SoundType.GROUND);
		setHardness(.2f);
		setHarvestLevel("shovel", -1);
	}

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
		return plantable instanceof BlockSapling;
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Increases the speed at which saplings grow over it." };
	}
	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileFertileSoil();
	}
}
