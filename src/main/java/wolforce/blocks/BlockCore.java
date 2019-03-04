package wolforce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.base.HasTE;
import wolforce.blocks.tile.TileCore;
import wolforce.recipes.RecipeCoring;

public class BlockCore extends Block implements HasTE {

	public static PropertyEnum TYPE = PropertyEnum.create("type", CoreType.class);
	private boolean isToRegister;
	public int color1, color2;

	public BlockCore(String name, boolean isToRegister) {
		this(name, isToRegister, null, null);
	}

	public BlockCore(String name, boolean isToRegister, String colorString1, String colorString2) {
		super(Material.CLAY);
		this.isToRegister = isToRegister;
		Util.setReg(this, name);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, CoreType.core_base));

		if (colorString1 != null && colorString2 != null) {
			color1 = Integer.parseInt(colorString1, 16);
			color2 = Integer.parseInt(colorString2, 16);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand enumhand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {

		if (state.getProperties().get(TYPE) != CoreType.core_base)
			return false;

		Item hand = playerIn.getHeldItem(enumhand).getItem();

		if (RecipeCoring.getResult(this, hand) == null)
			return false;

		for (CoreType ctype : CoreType.values()) {
			if (hand.equals(ctype.getShard()))
				set(world, pos, ctype);
		}

		if (!playerIn.isCreative())
			playerIn.getHeldItem(enumhand).shrink(1);
		return true;
	}

	private void set(World world, BlockPos pos, CoreType coretype) {
		world.setBlockState(pos, getDefaultState().withProperty(TYPE, coretype));
	}

	public static enum CoreType implements IStringSerializable {
		core_base, core_c, core_ca, core_fe, core_o, core_au, core_h, core_p, core_n;

		@Override
		public String getName() {
			return name();
		}

		public Item getShard() {
			switch (this) {
			case core_au:
				return Main.shard_au;
			case core_c:
				return Main.shard_c;
			case core_ca:
				return Main.shard_ca;
			case core_fe:
				return Main.shard_fe;
			case core_h:
				return Main.shard_h;
			case core_n:
				return Main.shard_n;
			case core_o:
				return Main.shard_o;
			case core_p:
				return Main.shard_p;
			default:
			}
			return null;
		}

	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.TRANSLUCENT;
	}

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCore();
	}

	@Override
	public boolean isToRegister() {
		return isToRegister;
	}

	// BLOCK STATES

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, CoreType.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((Enum<CoreType>) state.getValue(TYPE)).ordinal();
	}

}
