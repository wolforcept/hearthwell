package wolforce.blocks.base;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.IStateMapper;

public interface BlockWithCustomStateMapper {

	IStateMapper getMapper();
}
