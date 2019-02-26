// package wolforce.blocks;

//
// import net.minecraft.block.BlockStone;
// import net.minecraft.block.ITileEntityProvider;
// import net.minecraft.block.properties.PropertyInteger;
// import net.minecraft.block.state.BlockStateContainer;
// import net.minecraft.block.state.IBlockState;
// import net.minecraft.init.Blocks;
// import net.minecraft.tileentity.TileEntity;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.world.World;
// import wolforce.blocks.base.BlockEnergyProvider;
// import wolforce.blocks.base.BlockMachineBase;
// import wolforce.blocks.tile.TileGeneratorHeat;
//
// public class BlockGeneratorHeat extends BlockMachineBase implements
// BlockEnergyProvider, ITileEntityProvider {
//
// public static final int E = 15; // energy per temp value
// public static final PropertyInteger TEMP = PropertyInteger.create("temp", 0,
// 9);
//
// public BlockGeneratorHeat(String name) {
// super(name);
// setDefaultState(getDefaultState().withProperty(TEMP, 0));
// }
//
// @Override
// public boolean hasEnergy(World world, BlockPos pos, int energy) {
// if (world.getBlockState(pos).getBlock() instanceof BlockGeneratorHeat)
// return world.getBlockState(pos).getValue(TEMP) * E >= energy;
// return false;
// }
//
// @Override
// public boolean tryConsume(World world, BlockPos pos, int energyDecrease) {
// int energy = world.getBlockState(pos).getValue(TEMP);
// int newEnergy = energy - energyDecrease / E - 1;
// if (newEnergy >= 0) {
// world.setBlockState(pos, getDefaultState().withProperty(TEMP, newEnergy));
// if (Math.random() < .01) {
// if
// (world.getBlockState(pos.down()).getBlock().getMetaFromState(world.getBlockState(pos.down()))
// == 0)
// world.setBlockState(pos.down(), Blocks.OBSIDIAN.getDefaultState());
// else
// world.setBlockState(pos.down(),
// Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT,
// BlockStone.EnumType.STONE));
// }
// return true;
// }
// return false;
// }
// // BLOCK STATES
//
// @Override
// protected BlockStateContainer createBlockState() {
// return new BlockStateContainer(this, TEMP);
// }
//
// @Override
// public IBlockState getStateFromMeta(int meta) {
// return this.getDefaultState().withProperty(TEMP, meta);
// }
//
// @Override
// public int getMetaFromState(IBlockState state) {
// return state.getValue(TEMP);
// }
//
// @Override
// public TileEntity createNewTileEntity(World worldIn, int meta) {
// return new TileGeneratorHeat();
// }
// }
