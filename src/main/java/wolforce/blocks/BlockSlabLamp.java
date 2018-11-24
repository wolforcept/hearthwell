package wolforce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.MyBlock;
import wolforce.blocks.simplevariants.MySlab;

public class BlockSlabLamp extends MySlab {

	public BlockSlabLamp(String name) {
		super(name, Material.ROCK);
		setHardness(1);
		setResistance(1);
		setLightLevel(0.9375F);
		setHarvestLevel("pickaxe", -1);
	}

}
