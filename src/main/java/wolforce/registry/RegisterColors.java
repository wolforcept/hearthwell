package wolforce.registry;

import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolforce.Hwell;
import wolforce.Main;
import wolforce.blocks.BlockCore;

@Mod.EventBusSubscriber(modid = Hwell.MODID, value = Side.CLIENT)
public class RegisterColors {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerColors(ColorHandlerEvent.Block event) {
		net.minecraft.client.renderer.color.IBlockColor grassBlockColor = new net.minecraft.client.renderer.color.IBlockColor() {
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
				return worldIn != null && pos != null ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos)
						: ColorizerGrass.getGrassColor(0.5D, 1.0D);
			}
		};
		event.getBlockColors().registerBlockColorHandler(grassBlockColor, Main.fullgrass_block);

		for (Entry<String, BlockCore> entry : Main.custom_cores.entrySet()) {
			BlockCore core = entry.getValue();
			event.getBlockColors().registerBlockColorHandler(core.coreColor, core);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerColors(ColorHandlerEvent.Item event) {
		for (Entry<String, BlockCore> entry : Main.custom_cores.entrySet()) {
			BlockCore core = entry.getValue();
			event.getItemColors().registerItemColorHandler(core.coreItemColor, core);
		}
	}
}
