package wolforce.hwell.base;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MyBlock extends Block {

	public float hardness, resistance;

	public MyBlock(Material mat) {
		this(mat, false);
	}

	public MyBlock(Material mat, boolean tickRandomly) {
		super(mat);
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

	public MyBlock setSound(SoundType sound) {
		setSoundType(sound);
		return this;
	}

	public MyBlock setLight(float light) {
		setLightLevel(light);
		return this;
	}
}
