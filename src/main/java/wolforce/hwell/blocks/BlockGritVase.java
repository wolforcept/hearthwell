package wolforce.hwell.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.hwell.Main;
import wolforce.hwell.base.BlockWithDescription;
import wolforce.hwell.base.HasTE;
import wolforce.hwell.blocks.tile.TileGritVase;
import wolforce.hwell.items.ItemBranch;

public class BlockGritVase extends Block implements HasTE, BlockWithDescription {

	public static final PropertyInteger SIZE = PropertyInteger.create("size", 0, 5);

	public BlockGritVase(String name) {
		super(Material.ROCK);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
		setDefaultState(this.blockState.getBaseState().withProperty(SIZE, 0));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {

		if (player.getHeldItem(hand).getItem() == Main.myst_fertilizer && state.getValue(SIZE) < 5) {
			if (!player.isCreative())
				player.getHeldItem(hand).shrink(1);
			world.setBlockState(pos, state.cycleProperty(SIZE));
			return true;
		}
		return false;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state,
			int fortune) {
		int s = state.getValue(SIZE);
		drops.add(ItemBranch.createNewStack((int) (Math.random() * s) + s));
		super.getDrops(drops, world, pos, state, fortune);
	}

	//

	//

	// VISUALS

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	//

	//

	//

	// BLOCK STATES

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SIZE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(SIZE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(SIZE);
	}

	//

	//

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileGritVase();
	}

	@Override
	public String[] getDescription() {
		return new String[] { "When supplied." };
	}

}
