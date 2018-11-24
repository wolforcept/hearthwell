package wolforce;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class MyBlock extends Block {

	public MyBlock(String name, Material mat) {
		this(name, mat, false);
	}

	public MyBlock(String name, Material mat, boolean tickRandomly) {
		super(mat);
		setUnlocalizedName(name);
		setRegistryName(name);
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
		return this;
	}

}
