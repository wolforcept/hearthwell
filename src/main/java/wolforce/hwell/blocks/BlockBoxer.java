package wolforce.hwell.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.hwell.HwellConfig;
import wolforce.hwell.base.BlockEnergyConsumer;
import wolforce.hwell.base.HasTE;
import wolforce.hwell.blocks.tile.TileBoxer;

public class BlockBoxer extends Block implements HasTE, BlockEnergyConsumer {

	public BlockBoxer() {
		super(Material.ROCK);
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
		return HwellConfig.machines.grinderConsumption;
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Consumes " + getEnergyConsumption() + " energy per operation." };
	}
}
