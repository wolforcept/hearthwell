package wolforce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.blocks.base.BlockEnergyConsumer;
import wolforce.blocks.tile.TilePuller;

public class BlockPuller extends Block implements ITileEntityProvider, BlockEnergyConsumer {

	public BlockPuller(String name) {
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
	}

	//

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePuller();
	}

	@Override
	public int getEnergyConsumption() {
		return HwellConfig.energyConsumptionPuller;
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Consumes " + getEnergyConsumption() + " energy per operation." };
	}
}
