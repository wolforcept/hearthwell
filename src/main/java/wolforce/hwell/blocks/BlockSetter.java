package wolforce.hwell.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.hwell.HwellConfig;
import wolforce.hwell.Main;
import wolforce.hwell.base.BlockEnergyConsumer;
import wolforce.hwell.base.HasTE;
import wolforce.hwell.blocks.tile.TileSetter;

public class BlockSetter extends Block implements HasTE, BlockEnergyConsumer {

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);

	public BlockSetter() {
		super(Material.IRON);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) {
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
		for (int i = start; i < start + HwellConfig.machines.setterBaseRange + extraRange; i++) {
			BlockPos pos = _pos.offset(facing, i);
			worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + Math.random(),
					pos.getY() + (Math.random()), pos.getZ() + Math.random(), 0, -.02 - Math.random() * .2, 0);
			worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, pos.getX() + Math.random(),
					pos.getY() + (Math.random()), pos.getZ() + Math.random(), 0, -.02 - Math.random() * .2, 0);
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
		// BlockPos pos = _pos.offset(facing);
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
		return Math.max(range, -HwellConfig.machines.setterBaseRange + 1);
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
		return HwellConfig.machines.setterConsumption;
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Consumes 5 Energy per Operation." };
	}

	// TILE ENTITY

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileSetter();
	}
}
