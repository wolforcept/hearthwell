package wolforce.base;

import net.minecraft.block.ITileEntityProvider;

public interface HasTE extends ITileEntityProvider {

	default boolean isToRegisterTileEntity() {
		return true;
	}
}
