package wolforce.blocks;

public class BlockDismantler /* extends BlockContainerBase */ {

	// // public static final PropertyInteger CHARGE =
	// PropertyInteger.create("charge",
	// // 0, 99);
	// // public static final PropertyString ITEM = Property String.create("item");
	//
	// public BlockDismantler(String name) {
	// super(name, Material.ROCK);
	// }
	//
	// @Override
	// public TileEntity createTileEntity() {
	// return new TileDismantler();
	// }
	//
	// @Override
	// public Class getTileEntityClass() {
	// return TileDismantler.class;
	// }
	//
	// // TODO @Override
	// // public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess
	// source,
	// // BlockPos pos) {
	// // return BlockSlabs.AABB_BOTTOM_HALF;
	// // }
	//
	// @Override
	// public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
	// EntityPlayer player, EnumHand hand,
	// EnumFacing par6, float par7, float par8, float par9) {
	// ItemStack heldItem = player.getHeldItem(hand);
	// if (!world.isRemote) {
	// TileDismantler stand = (TileDismantler) world.getTileEntity(pos);
	// if (stand != null) {
	// ItemStack display = stand.getItemStack();
	// if (isValid(heldItem)) {
	// if (!isValid(display)) {
	// ItemStack toPut = heldItem.copy();
	// toPut.setCount(1);
	// stand.setItemStack(toPut);
	// if (!player.capabilities.isCreativeMode)
	// heldItem.shrink(1);
	// return true;
	// } else if (canBeStacked(heldItem, display)) {
	// int maxTransfer = Math.min(display.getCount(),
	// heldItem.getMaxStackSize() - heldItem.getCount());
	// if (maxTransfer > 0) {
	// heldItem.grow(maxTransfer);
	// ItemStack newDisplay = display.copy();
	// newDisplay.shrink(maxTransfer);
	// stand.setItemStack(newDisplay);
	// return true;
	// }
	// }
	// } else {
	// if (isValid(display)) {
	// player.setHeldItem(hand, display.copy());
	// stand.setItemStack(ItemStack.EMPTY);
	// return true;
	// }
	// }
	// }
	// return false;
	// } else {
	// return true;
	// }
	// }
	//
	// @Override
	// public boolean isOpaqueCube(IBlockState state) {
	// return false;
	// }
	//
	// @Override
	// public boolean isFullCube(IBlockState state) {
	// return false;
	// }
	//
	// public EnumBlockRenderType getRenderType(IBlockState state) {
	// return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	// }
	//
	// // UTIL
	//
	// public static boolean isValid(ItemStack stack) {
	// return stack != null && !stack.isEmpty();
	// }
	//
	// public static boolean canBeStacked(ItemStack stack1, ItemStack stack2) {
	// return ItemStack.areItemsEqual(stack1, stack2) &&
	// ItemStack.areItemStackTagsEqual(stack1, stack2);
	// }
}
