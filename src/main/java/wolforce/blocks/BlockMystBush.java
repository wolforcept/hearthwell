package wolforce.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wolforce.Main;

public class BlockMystBush extends BlockBush {

	protected static final AxisAlignedBB boundingBox = new AxisAlignedBB(.1, 0, .1, .9, .3, .9);
	protected static final AxisAlignedBB boundingBoxBig = new AxisAlignedBB(.1, 0, .1, .9, .9, .9);

	public BlockMystBush(String name) {
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(0.0F);
		setSoundType(SoundType.PLANT);
		setLightLevel(.1f);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Main.myst_dust;
	}

	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == Main.myst_grass;
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te,
			ItemStack toolused) {
		if (!worldIn.isRemote) {
			spawnAsEntity(worldIn, pos, new ItemStack(Main.myst_dust, getDropQuantity(state), 0));
		} else {
			super.harvestBlock(worldIn, player, pos, state, te, toolused);
		}
	}

	private int getDropQuantity(IBlockState state) {
		if (state.getBlock() == Main.myst_bush_big)
			return 1 + (int) (Math.random() * 3);
		return (int) (Math.random() * 2);
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		return true;
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getBlock() == Main.myst_bush_big ? boundingBoxBig : boundingBox;
	}

}
