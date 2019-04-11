package wolforce.blocks.tile;

import java.util.HashSet;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import wolforce.HwellConfig;
import wolforce.Main;
import wolforce.Util;
import wolforce.base.BlockEnergyConsumer;
import wolforce.blocks.BlockSetter;
import wolforce.blocks.BlockTray;

public class TileSetter extends TileEntity implements ITickable {

	@Override
	public void update() {
		if (world.isRemote || world.isBlockPowered(pos))
			return;

		EnumFacing facing = world.getBlockState(pos).getValue(BlockSetter.FACING);
		int extraRange = BlockSetter.getExtraRange(world, pos, facing);
		int start = BlockSetter.getStart(world, pos, facing);
		BlockPos[] poss = new BlockPos[HwellConfig.machines.setterBaseRange + extraRange];

		for (int i = 0; i < poss.length; i++) {
			poss[i] = pos.offset(facing, start + i);
		}

		HashSet<Item> filter = new HashSet<Item>();
		boolean isBlackList = BlockTray.getFilter(world, pos, filter);

		for (BlockPos pos2 : poss)
			setOneEntityItemInPos(pos, pos2, filter, isBlackList);

	}

	private void setOneEntityItemInPos(BlockPos setterPos, BlockPos pos, HashSet<Item> filter, boolean isBlackList) {
		List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos));
		for (EntityItem entityItem : entities) {
			if (!Util.isValid(entityItem.getItem()) || !world.isAirBlock(pos))
				continue;
			if (BlockTray.isItemAble(filter, entityItem.getItem().getItem(), isBlackList)) {
				Block block = Block.getBlockFromItem(entityItem.getItem().getItem());
				if (block != null && block != Blocks.AIR
						&& BlockEnergyConsumer.tryConsume(world, setterPos, Main.setter.getEnergyConsumption())) {
					world.setBlockState(pos, block.getStateFromMeta(entityItem.getItem().getMetadata()));
					entityItem.getItem().grow(-1);
					return;
				}
			}
		}
	}

	// private void setAnEntityItem2(World world, BlockPos pos) {
	// List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, new
	// AxisAlignedBB(pos));
	// for (EntityItem entityItem : entities) {
	// if (!Util.isValid(entityItem.getItem()) || !world.isAirBlock(pos))
	// continue;
	// // Block block = Block.getBlockFromItem(entityItem.getItem().getItem());
	// // if (block != null && block != Blocks.AIR) {
	// // world.setBlockState(pos,
	// // block.getStateFromMeta(entityItem.getItem().getMetadata()));
	// // entityItem.getItem().grow(-1);
	// // return;
	// // }
	// // tryPlace(world, pos, entityItem.getItem());
	// ItemStack stack = entityItem.getItem();
	// if (stack.canPlaceOn(world.getBlockState(pos.down()).getBlock())) {
	// if (stack.getItem() instanceof ItemBlock) {
	// ItemBlock itemBlock = (ItemBlock) stack.getItem();
	// int meta = itemBlock.getMetadata(stack.getMetadata());
	// IBlockState state = itemBlock.getBlock().getStateForPlacement(world, pos,
	// EnumFacing.NORTH, 0, 0, 0,
	// stack.getMetadata(), null);
	// world.setBlockState(pos, state);
	// entityItem.getItem().grow(-1);
	// return;
	// }
	// }
	// EntityPlayer a =
	// FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().get(0);
	// System.out.println(a);
	// stack.onItemUse(FakePlayerFactory.getMinecraft((WorldServer) world), world,
	// pos.down(), EnumHand.MAIN_HAND,
	// EnumFacing.NORTH, 0, 0, 0);
	// }
	// }

	//

	// private void tryPlace(World worldIn, BlockPos pos, ItemStack itemstack) {
	//
	// if (!(itemstack.getItem() instanceof ItemBlock))
	// return;
	// System.out.println("TileSetter.tryPlace(1)");
	// EnumFacing facing = EnumFacing.NORTH;
	// IBlockState iblockstate = worldIn.getBlockState(pos);
	// Block block = iblockstate.getBlock();
	// ItemBlock itemBlock = (ItemBlock) itemstack.getItem();
	// EntityPlayer player = FakePlayerFactory.getMinecraft((WorldServer) worldIn);
	//
	// if (!block.isReplaceable(worldIn, pos)) {
	// pos = pos.offset(facing);
	// }
	//
	// System.out.println("TileSetter.tryPlace(2)");
	//
	// System.out.println(canBePlaced(itemstack, pos));
	// System.out.println(worldIn.mayPlace(itemBlock.getBlock(), pos, false, facing,
	// (Entity) null));
	//
	// if (!Util.isValid(itemstack)
	// && (canBePlaced(itemstack, pos) && worldIn.mayPlace(itemBlock.getBlock(),
	// pos, false, facing, (Entity) null))) {
	// int i = itemBlock.getMetadata(itemstack.getMetadata());
	// IBlockState iblockstate1 = itemBlock.getBlock().getStateForPlacement(worldIn,
	// pos, facing, 0, 0, 0, i, player);
	//
	// System.out.println("TileSetter.tryPlace(3)");
	//
	// if (itemBlock.placeBlockAt(itemstack, player, worldIn, pos, facing, 0, 0, 0,
	// iblockstate1)) {
	//
	// System.out.println("TileSetter.tryPlace(4)");
	//
	// iblockstate1 = worldIn.getBlockState(pos);
	// SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1,
	// worldIn, pos, player);
	// worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(),
	// soundtype.getPlaceSound(), SoundCategory.BLOCKS,
	// (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, true);
	// itemstack.shrink(1);
	// }
	//
	// }
	// }
	//
	// private boolean canBePlaced(ItemStack stack, BlockPos blockpos) {
	// Block block = this.world.getBlockState(blockpos).getBlock();
	// return stack.canPlaceOn(block) || stack.canEditBlocks();
	// }
}
