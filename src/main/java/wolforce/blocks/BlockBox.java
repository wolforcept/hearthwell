package wolforce.blocks;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolforce.Main;
import wolforce.MyBlock;
import wolforce.Util;
import wolforce.blocks.base.BlockWithDescription;

public class BlockBox extends BlockRotatedPillar {

	public final Block block;
	private boolean hasAxis;

	public BlockBox(Block block, boolean hasAxis) {
		super(Material.CLAY);
		this.block = block;
		this.hasAxis = hasAxis;

		String name = block.getRegistryName().getResourcePath() + "_box";
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
		return "Boxes are decoration blocks created in the Block Boxer machine.";
	}

	//

	//

	// VISUALS

	public ModelResourceLocation getModelRes(String variant) {
		return new ModelResourceLocation(block.getRegistryName(), variant);
	}

	public IStateMapper getMapper() {
		return new CustomStateMapper(block.getRegistryName(), hasAxis, isCore(block));
		// example minecraft:diamond_block CONFIRMED
	}

	private boolean isCore(Block in) {
		return in == Main.core_stone || in == Main.core_heat || in == Main.core_green || in == Main.core_sentient;
	}

	static class CustomStateMapper extends StateMapperBase {

		private ResourceLocation res;
		private boolean hasAxis;
		private boolean isCore;

		public CustomStateMapper(ResourceLocation res, boolean hasAxis, boolean isCore) {
			this.res = res;
			this.hasAxis = hasAxis;
			this.isCore = isCore;
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
			if (isCore)
				return new ModelResourceLocation(res, "type=core_base");
			if (hasAxis)
				return new ModelResourceLocation(res, getPropertyString(state.getProperties()));
			return new ModelResourceLocation(res, "normal");
		}

	}

	//

	//

	// RECIPE RELATED

	@Override
	public String toString() {
		return "[ " + block.getUnlocalizedName() + " -> " + this.getUnlocalizedName() + " ]";
	}

}
