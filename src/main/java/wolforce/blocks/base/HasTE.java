package wolforce.blocks.base;

import net.minecraft.block.ITileEntityProvider;

public interface HasTE extends ITileEntityProvider {

	default boolean isToRegister() {
		return true;
	}
}
