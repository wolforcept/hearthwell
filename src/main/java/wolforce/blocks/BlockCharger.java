package wolforce.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.Util;
import wolforce.base.BlockWithDescription;
import wolforce.base.HasTE;
import wolforce.blocks.tile.TileCharger;

public class BlockCharger extends Block implements HasTE, BlockWithDescription {

	public BlockCharger(String name) {
		super(Material.ROCK);
		Util.setReg(this, name);
		setHardness(2);
		setHarvestLevel("pickaxe", -1);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		TileCharger tile = (TileCharger) world.getTileEntity(pos);
		ItemStack stack = tile.inventory.getStackInSlot(0);

		// IF THERE IS SOMETHING INSIDE
		if (Util.isValid(stack)) {
			if (!world.isRemote) {
				Util.spawnItem(world, pos.down(), tile.inventory.extractItem(0, 1, false));
				tile.markDirty();
			}
			return true;
		}

		// IF EMPTY LETS TRY INSERT
		ItemStack held = player.getHeldItem(hand);
		if (held.getItem() == Main.power_crystal) {

			if (!world.isRemote) {
				tile.inventory.setStackInSlot(0, held);
				tile.markDirty();
			}
			player.setHeldItem(hand, ItemStack.EMPTY);
			return true;
		}

		return false;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			TileCharger tile = (TileCharger) worldIn.getTileEntity(pos);
			Util.spawnItem(worldIn, pos, tile.inventory.extractItem(0, 1, false));
		}
		super.breakBlock(worldIn, pos, state);
	}

	//

	//

	// TILE ENTITIES

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCharger();
	}

	@Override
	public String[] getDescription() {
		return new String[] { "Charges Power Crystals.", "Requires a multiblock Structure." };
	}

}
