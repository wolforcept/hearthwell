// package wolforce.blocks.tile;

//
// import net.minecraft.block.material.Material;
// import net.minecraft.tileentity.TileEntity;
// import net.minecraft.util.ITickable;
// import wolforce.Main;
// import wolforce.blocks.BlockGeneratorHeat;
//
// public class TileGeneratorHeat extends TileEntity implements ITickable {
//
// @Override
// public void update() {
//
// if (world.isRemote || world.getBlockState(pos.down()).getMaterial() !=
// Material.LAVA || Math.random() > .1)
// return;
//
// int next = world.getBlockState(pos).getValue(BlockGeneratorHeat.TEMP) + 1;
// if (next != 10)
// world.setBlockState(pos,
// Main.generator_heat.getDefaultState().withProperty(BlockGeneratorHeat.TEMP,
// next));
//
// }
//
// }
