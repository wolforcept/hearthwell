package wolforce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.Util;
import wolforce.base.BlockEnergyConsumer;
import wolforce.base.HasTE;
import wolforce.blocks.tile.TilePuller;

public class BlockPuller extends Block implements HasTE, BlockEnergyConsumer {

	public BlockPuller(String name) {
		super(Material.ROCK);
		Util.setReg(this, name);
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
		return HwellConfig.machines.pullerConsumption;
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Consumes " + getEnergyConsumption() + " energy per operation." };
	}
}
