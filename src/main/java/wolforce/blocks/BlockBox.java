package wolforce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import wolforce.Main;

public class BlockBox extends BlockRotatedPillar {

	public final Block block;
	public final boolean hasAxis;
	
	public BlockBox(Block block, boolean hasAxis) {
		super(Material.CLAY);
		this.block = block;
		this.hasAxis = hasAxis;

		String name = "box_" + block.getRegistryName().getResourcePath();
		setUnlocalizedName(name);
		setRegistryName(name);

		setHardness(.25f);
		setResistance(2f);
	}

	@Override
	public String getLocalizedName() {
		return block.getLocalizedName() + " Box";
	}

	public String getDescription() {
		return "Boxes are decoration variants of a block and created in the Block Boxer machine.";
	}

	public boolean isCore(Block in) {
		return in == Main.core_stone || in == Main.core_heat || in == Main.core_green || in == Main.core_sentient;
	}

	//

	//

	// RECIPE RELATED

	@Override
	public String toString() {
		return "[ " + block.getUnlocalizedName() + " -> " + this.getUnlocalizedName() + " ]";
	}

}
