package wolforce.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.base.BlockEnergyConsumer;
import wolforce.blocks.base.BlockWithDescription;
import wolforce.blocks.tile.TileSetter;
import wolforce.items.ItemGrindingWheel;
import wolforce.recipes.RecipeTube;

public class BlockSetter extends Block implements ITileEntityProvider, BlockEnergyConsumer {

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);

	public BlockSetter(String name) {
		super(Material.IRON);
		Util.setReg(this, name);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {
		if (placer.rotationPitch < -45)
			return this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
		if (placer.rotationPitch > 45)
			return this.getDefaultState().withProperty(FACING, EnumFacing.UP);
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos _pos, Random rand) {
		if (worldIn.isBlockPowered(_pos))
			return;
		EnumFacing facing = stateIn.getValue(FACING);
		int extraRange = getExtraRange(worldIn, _pos, facing);
		int start = getStart(worldIn, _pos, facing);
		for (int i = start; i < start + HwellConfig.setterBaseRange + extraRange; i++) {
			BlockPos pos = _pos.offset(facing, i);
			worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + Math.random(), pos.getY() + (Math.random()),
					pos.getZ() + Math.random(), 0, -.02 - Math.random() * .2, 0);
			worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + Math.random(), pos.getY() + (Math.random()),
					pos.getZ() + Math.random(), 0, -.02 - Math.random() * .2, 0);
		}

	}

	public static int getStart(World worldIn, BlockPos _pos, EnumFacing facing) {
		int start = 1;
		for (EnumFacing f : EnumFacing.values()) {
			if (f == facing)
				continue;
			if (worldIn.getBlockState(_pos.offset(f)).getBlock().equals(Main.crystal_block))
				start++;
		}
		return start;
	}

	public static int getExtraRange(World worldIn, BlockPos _pos, EnumFacing facing) {
		BlockPos pos = _pos.offset(facing);
		int range = 0;
		for (EnumFacing f : EnumFacing.values()) {
			if (f == facing)
				continue;
			Block block = worldIn.getBlockState(_pos.offset(f)).getBlock();
			if (block.equals(Main.moonstone))
				range++;
			if (block.equals(Main.azurite) || block.equals(Main.smooth_azurite))
				range--;
		}
		return Math.max(range, -HwellConfig.setterBaseRange + 1);
	}

	private BlockPos[] getBlockPoss(EnumFacing facing, int start, int range) {
		return null;
	}

	//

	//

	// BLOCK STATES

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).ordinal();
	}

	@Override
	public int getEnergyConsumption() {
		return HwellConfig.setterConsumption;
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Consumes 5 Energy per Operation.", "Requires a multiblock Structure." };
	}

	// TILE ENTITY

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileSetter();
	}
}
