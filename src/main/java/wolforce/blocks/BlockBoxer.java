package wolforce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.HWellConfig;
import wolforce.blocks.base.BlockEnergyConsumer;
import wolforce.blocks.tile.TileBoxer;

public class BlockBoxer extends Block implements ITileEntityProvider, BlockEnergyConsumer {

	public BlockBoxer(String name) {
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
	}

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileBoxer();
	}

	@Override
	public int getEnergyConsumption() {
		return HWellConfig.energyConsumptionGrinder;
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Consumes " + getEnergyConsumption() + " energy per operation." };
	}
}
