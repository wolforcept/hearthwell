package wolforce.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import wolforce.Main;
import wolforce.MyItem;
import wolforce.recipes.RecipeSeedOfLife;

public class ItemSeedOfLife extends MyItem {

	final int seedIndex;

	public ItemSeedOfLife(String name, String lore, int seedIndex) {
		super(name, lore + " Radius: " + radius[seedIndex]);
		setMaxStackSize(1);
		this.seedIndex = seedIndex;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 20;
	}

	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		RayTraceResult raytraceresult = this.rayTrace(world, player, true);

		if (raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
			return new ActionResult<>(EnumActionResult.FAIL, stack);

		BlockPos pos = raytraceresult.getBlockPos();
		Block block = world.getBlockState(pos).getBlock();

		if (world.isBlockModifiable(player, pos) && canTransform(world, pos)) {
			player.setActiveHand(hand);
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult<>(EnumActionResult.FAIL, stack);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {

		if (!(entityLiving instanceof EntityPlayer))
			return stack;

		EntityPlayer player = (EntityPlayer) entityLiving;
		RayTraceResult raytraceresult = this.rayTrace(world, player, true);

		if (raytraceresult == null || raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
			return stack;

		BlockPos pos = raytraceresult.getBlockPos();

		if (!world.isBlockModifiable(player, pos))
			return stack;

		// is possible to make tree or make grass
		if (!world.isAirBlock(pos.up()) || !canTransform(world, pos))
			return stack;

		// FROM THIS POINT FORWARD THE TREE WILL BE MADE AND THE ITEM IS LOST
		// world.setBlockState(pos, Main.fertile_soil.getDefaultState());
		// world.setBlockState(pos.up(), Blocks.SAPLING.getDefaultState());
		world.setBlockState(pos, center_block[seedIndex]);
		world.setBlockState(pos.up(), center_top_block[seedIndex]);
		world.playSound(null, entityLiving.getPosition(), SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1, 1);

		// makeTree(world, pos.up());

		// ON THE SERVER ONLY, CHANGE THE GROUND AROUND IT
		if (!world.isRemote) {
			int r = radius[seedIndex];
			for (int dx = -r; dx <= r; dx++) {
				for (int dz = -r; dz <= r; dz++) {
					float dist = 1f - (float) Math.hypot(dx, dz) / (r * 1.333f);
					BlockPos dpos = pos.add(dx, 0, dz);
					if (!world.isAirBlock(dpos)) {
						if (world.isAirBlock(dpos.up())) {
							transform(world, dpos, dist, dist);
						} else {
							if (world.isAirBlock(dpos.up().up())) {
								transform(world, dpos.up(), dist, dist);
							}
						}
					} else {
						if (!world.isAirBlock(dpos.down())) {
							transform(world, dpos.down(), dist, dist);
						} else {
							if (!world.isAirBlock(dpos.down().down()))
								transform(world, dpos.down().down(), dist, dist);
						}
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}

	/*
	 * GROUND
	 */

	private boolean canTransform(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		return RecipeSeedOfLife.getResult(state.getBlock(), state.getBlock().getMetaFromState(state));
	}

	private void transform(World world, BlockPos pos, float prob, float grassProbability) {
		if (Math.random() < prob && canTransform(world, pos)) {
			IBlockState other_block = less_probable_blocks[seedIndex][(int) (less_probable_blocks[seedIndex].length * Math.random())];
			world.setBlockState(pos, Math.random() < grassProbability ? probable_block[seedIndex] : other_block);
			if (Math.random() < .5) {
				IBlockState top_block = top_blocks[seedIndex][(int) (top_blocks[seedIndex].length * Math.random())];
				world.setBlockState(pos.up(), top_block);
			}
		}
	}

	public static final int radius[] = { 3, 10, 5 };
	static IBlockState[] center_block, center_top_block, probable_block;
	static IBlockState[][] less_probable_blocks;
	static IBlockState[][] top_blocks;

	public static void init() {

		center_block = new IBlockState[] { //
				Main.fertile_soil.getDefaultState(), //
				Main.crystal_nether_block.getDefaultState(), //
				Blocks.END_STONE.getDefaultState(), //
		};

		center_top_block = new IBlockState[] { //
				Blocks.SAPLING.getDefaultState(), //
				Blocks.AIR.getDefaultState(), //
				Blocks.CHORUS_FLOWER.getDefaultState(), //
		};

		probable_block = new IBlockState[] { //
				Blocks.GRASS.getDefaultState(), //
				Blocks.NETHERRACK.getDefaultState(), //
				Blocks.END_STONE.getDefaultState(), //
		};

		less_probable_blocks = new IBlockState[][] { //
				{ //
						Blocks.DIRT.getDefaultState() //
				}, { //
						Main.scorch_grit.getDefaultState(), //
						Blocks.GRAVEL.getDefaultState(),//
				}, { //
						Blocks.PURPUR_BLOCK.getDefaultState(), //
						Main.moonstone.getDefaultState(),//
				} //
		};

		top_blocks = new IBlockState[][] { //
				{ //
						Blocks.TALLGRASS.getDefaultState().withProperty(Blocks.TALLGRASS.TYPE, BlockTallGrass.EnumType.FERN), //
						Blocks.TALLGRASS.getDefaultState().withProperty(Blocks.TALLGRASS.TYPE, BlockTallGrass.EnumType.GRASS), //
				}, //
				{ //
						Blocks.GLOWSTONE.getDefaultState(), //
						Blocks.REDSTONE_WIRE.getDefaultState(), //
						Blocks.FIRE.getDefaultState(), //
				}, //
				{ //
						Blocks.AIR.getDefaultState(), //
				}, //
		};
	}

}
