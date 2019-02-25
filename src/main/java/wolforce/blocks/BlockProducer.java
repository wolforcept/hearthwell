package wolforce.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.MyBlock;
import wolforce.Util;
import wolforce.blocks.base.BlockEnergyConsumer;

public class BlockProducer extends MyBlock implements BlockEnergyConsumer {

	public BlockProducer(String name) {
		super(name, Material.ROCK, true);
		setHardness(2);
		setResistance(2);
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		if (BlockEnergyConsumer.tryConsume(worldIn, pos, getEnergyConsumption()))
			Util.spawnItem(worldIn, pos.up(), new ItemStack(Main.loot_base), 0, .3, 0);
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Produces Loot Kits." };
	}

	@Override
	public int getEnergyConsumption() {
		return HwellConfig.producerConsumption;
	}
}
