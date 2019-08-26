package wolforce.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wolforce.HwellConfig;
import wolforce.base.BlockEnergyConsumer;
import wolforce.base.HasTE;
import wolforce.base.MyBlock;
import wolforce.blocks.tile.TileFormer;

public class BlockFormer extends MyBlock implements HasTE, BlockEnergyConsumer {

	public BlockFormer(String name) {
		super(name, Material.ROCK, true);
		setResistance(2f);
		setHardness(2f);
		setHarvestLevel("pickaxe", -1);
		setSoundType(SoundType.STONE);
	}

	// SQUARE :

	// int direction = 1, maxd = 1, d = 0;
	// int x = 0, y = 0, z = 0;
	// boolean onxx = true;
	// LinkedList<BlockPos> now = new LinkedList<>();
	// LinkedList<BlockPos> later = new LinkedList<>();
	// LinkedList<BlockPos> later2 = new LinkedList<>();
	// while (!world.isAirBlock(pos.add(x, y, z)) || !now.isEmpty()) {
	//
	// while (!now.isEmpty()) {
	// BlockPos nowpos = now.pop();
	// if (world.isAirBlock(nowpos))
	// return nowpos;
	// }
	//
	// if (Math.hypot(x, z) > (maxd - 1) / 2.0) {
	// later2.add(pos.add(x, y, z));
	// }
	// if (onxx) {
	// d++;
	// x += direction;
	// if (d == maxd) {
	// onxx = false;
	// d = 0;
	// }
	// } else {
	// d++;
	// z += direction;
	// if (d == maxd) {
	// onxx = true;
	// maxd++;
	// d = 0;
	// direction *= -1;
	// now = later;
	// later = later2;
	// later2 = new LinkedList<>();
	// }
	// }
	// }
	// return pos.add(x, y, z);

	@Override
	public String[] getDescription() {
		return new String[] { "Will slowly create a flat-top, circular, stone shape around it.",
				"Consumes " + getEnergyConsumption() + " energy per operation." };
	}

	@Override
	public int getEnergyConsumption() {
		return HwellConfig.machines.formerConsumption;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileFormer();
	}

}
