package wolforce.hwell.items;

import wolforce.hwell.base.MyItem;

public class ItemCrystal extends MyItem {

	public ItemCrystal(String name) {
		super(name);
	}

//	@Override
//	public boolean hasCustomEntity(ItemStack stack) {
//		return true;
//	}
//
//	@Override
//	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
//
//		if (location instanceof EntityItem) {
//			EntityItem dc = (EntityItem) location;
//			EntityItem newCrystal = new EntityItem(dc.world, dc.posX, dc.posY, dc.posZ, dc.getItem()) {
//				{
//					motionX = dc.motionX;
//					motionY = dc.motionY;
//					motionZ = dc.motionZ;
//					setPickupDelay(40);
//				}
//
//				@Override
//				public Entity changeDimension(int dimensionIn, ITeleporter teleporter) {
//					if (!world.isRemote && dimensionIn == DimensionType.NETHER.getId()) {
//						ItemStack stack = getItem();
//						if (stack.getItem().equals(Main.crystal))
//							setItem(new ItemStack(Main.crystal_nether, stack.getCount(), stack.getMetadata()));
//						return this;
//					}
//					return super.changeDimension(dimensionIn, teleporter);
//				}
//			};
//			return newCrystal;
//		}
//		return super.createEntity(world, location, itemstack);
//	}
}
