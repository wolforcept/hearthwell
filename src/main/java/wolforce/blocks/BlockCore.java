package wolforce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.blocks.tile.TileCore;
import wolforce.recipes.RecipeCoring;

public class BlockCore extends Block implements ITileEntityProvider {

	public static PropertyEnum TYPE = PropertyEnum.create("type", CoreType.class);
	private boolean isToRegister;

	public BlockCore(String name, boolean isToRegister) {
		super(Material.CLAY);
		this.isToRegister = isToRegister;
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, CoreType.core_base));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand enumhand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {

		Comparable prev = state.getProperties().get(TYPE);
		Item hand = playerIn.getHeldItem(enumhand).getItem();

		if (RecipeCoring.getResult(this, hand) == null)
			return false;

		/**/if (prev == CoreType.core_base && hand == Main.shard_ca)
			set(world, pos, CoreType.core_ca);

		else if (prev == CoreType.core_base && hand == Main.shard_c)
			set(world, pos, CoreType.core_c);

		else if (prev == CoreType.core_base && hand == Main.shard_au)
			set(world, pos, CoreType.core_au);

		else if (prev == CoreType.core_base && hand == Main.shard_fe)
			set(world, pos, CoreType.core_fe);

		else if (prev == CoreType.core_base && hand == Main.shard_o)
			set(world, pos, CoreType.core_o);

		else if (prev == CoreType.core_base && hand == Main.shard_h)
			set(world, pos, CoreType.core_h);

		else if (prev == CoreType.core_base && hand == Main.shard_p)
			set(world, pos, CoreType.core_p);

		else if (prev == CoreType.core_base && hand == Main.shard_n)
			set(world, pos, CoreType.core_n);

		// else if (prev == iron && hand == Main.soul_dust)
		// set(world, pos, CoreType.soulsteel);

		else
			return false;

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

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCore();
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
