package wolforce.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.HWellConfig;
import wolforce.Util;
import wolforce.blocks.base.BlockEnergyConsumer;
import wolforce.blocks.base.BlockMachineBase;
import wolforce.blocks.base.BlockWithDescription;
import wolforce.blocks.tile.TilePrecisionGrinder;
import wolforce.items.ItemGrindingWheel;

public class BlockSpawnerBase extends BlockMachineBase implements ITileEntityProvider, BlockEnergyConsumer {

	public BlockSpawnerBase(String name) {
		super(name);
	}

	//

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePrecisionGrinder();
	}

	@Override
	public int getEnergyConsumption() {
		return HWellConfig.energyConsumptionGrinder;
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Consumes " + getEnergyConsumption() + " energy per operation." };
	}
}
