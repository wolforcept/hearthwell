package wolforce.hwell.blocks;

import java.util.List;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wolforce.hwell.recipes.RecipeCrushing;
import wolforce.mechanics.Util;

public class BlockCrushing extends BlockFalling {

	public BlockCrushing() {
		super();
		setSoundType(SoundType.STONE);
		setHardness(1f);
		setHarvestLevel("pickaxe", -1);
	}

	@Override
	public void onEndFalling(World world, BlockPos pos, IBlockState p_176502_3_, IBlockState p_176502_4_) {

		List<EntityItem> entities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos));
		// HashMap<ItemStack, Integer> results = new HashMap();
		for (EntityItem entityItem : entities) {
			boolean willdead = false;
			BlockPos posAir = getNearAirPos(world, pos);
			for (int i = 0; i < entityItem.getItem().getCount(); i++) {
				ItemStack result = RecipeCrushing.getSingleResult(entityItem.getItem());
				if (result != null) {
					Util.spawnItem(world, posAir, result.copy());
					willdead = true;
				}
				// worldIn.spawnEntity(new EntityItem(worldIn, posAir.getX(), posAir.getY(),
				// posAir.getZ(), itemStack));
			}
			if (willdead)
				entityItem.setDead();
		}
	}

	private BlockPos getNearAirPos(World world, BlockPos pos) {
		for (EnumFacing face : EnumFacing.HORIZONTALS)
			if (world.isAirBlock(pos.offset(face)))
				return pos.offset(face);
		return new BlockPos(pos);
	}

}
