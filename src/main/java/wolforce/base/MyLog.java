package wolforce.base;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import wolforce.Util;

public class MyLog extends BlockLog {

	public MyLog(String name) {
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));

		Util.setReg(this, name);
		setHardness(1.8f);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();

		switch (meta & 0b1100) {
		case 0b0000:
			state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
			break;

		case 0b0100:
			state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
			break;

		case 0b1000:
			state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
			break;

		case 0b1100:
			state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
			break;
		}

		return state;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		switch ((BlockLog.EnumAxis) state.getValue(LOG_AXIS)) {
		case X:
			return 0b0100;
		case Y:
			return 0b0000;
		case Z:
			return 0b1000;
		default:
			return 0b1100;
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { LOG_AXIS });
	}

}
