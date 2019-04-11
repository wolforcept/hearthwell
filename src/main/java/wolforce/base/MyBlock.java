package wolforce.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import wolforce.Util;

public class MyBlock extends Block {

	public float hardness, resistance;

	public MyBlock(String name, Material mat) {
		this(name, mat, false);
	}

	public MyBlock(String name, Material mat, boolean tickRandomly) {
		super(mat);
		Util.setReg(this, name);
		setTickRandomly(tickRandomly);
	}

	/**
	 * level Harvest level: Wood: 0 Stone: 1 Iron: 2 Diamond: 3 Gold: 0
	 */
	public MyBlock setHarvest(String toolClass, int level) {
		setHarvestLevel(toolClass, level);
		return this;
	}

	@Override
	public MyBlock setHardness(float hardness) {
		super.setHardness(hardness);
		this.hardness = hardness;
		return this;
	}

	@Override
	public MyBlock setResistance(float resistance) {
		super.setResistance(resistance);
		this.resistance = resistance;
		return this;
	}
}
