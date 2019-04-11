package wolforce.registry;

import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import wolforce.Hwell;
import wolforce.Main;
import wolforce.Util;
import wolforce.base.BlockWithDescription;
import wolforce.blocks.BlockBox;
import wolforce.blocks.BlockCore;
import wolforce.entities.EntityPower;

@Mod.EventBusSubscriber(modid = Hwell.MODID)
public class RegisterBlocks {

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for (Block b : Main.blocks)
			event.getRegistry().register(b);
		for (BlockBox box : Main.boxes) {
			event.getRegistry().register(box);
		}
		for (Entry<String, BlockCore> entry : Main.custom_cores.entrySet()) {
			BlockCore core = entry.getValue();
			event.getRegistry().register(core);
		}
	}

	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
		for (Block block : Main.blocks) {
			Item item = !(block instanceof BlockWithDescription) ? new ItemBlock(block) : //
					new ItemBlock(block) {
						public void addInformation(ItemStack stack, net.minecraft.world.World worldIn, java.util.List<String> tooltip,
								net.minecraft.client.util.ITooltipFlag flagIn) {
							for (String string : ((BlockWithDescription) block).getDescription()) {
								tooltip.add(string);
							}
						};
					};
			item.setUnlocalizedName(Util.res(block.getRegistryName().getResourcePath()).toString());
			item.setRegistryName(Util.res(block.getRegistryName().getResourcePath()));
			event.getRegistry().register(item);
		}

		for (BlockBox box : Main.boxes) {
			Item item = new ItemBlock(box) {

				@Override
				public String getItemStackDisplayName(ItemStack stack) {
					return Item.getItemFromBlock(box.block).getItemStackDisplayName(stack) + " Box";
				}

				@Override
				public void addInformation(ItemStack stack, World worldIn, List<String> tooltip,
						net.minecraft.client.util.ITooltipFlag flagIn) {
					tooltip.add(box.getDescription());
					super.addInformation(stack, worldIn, tooltip, flagIn);
				}

			};
			item.setRegistryName(box.getRegistryName());
			event.getRegistry().register(item);
		}

		for (

		Entry<String, BlockCore> entry : Main.custom_cores.entrySet()) {
			BlockCore core = entry.getValue();
			Item item = new ItemBlock(core) {
				@Override
				public String getItemStackDisplayName(ItemStack stack) {
					return core.getLocalizedName();
				}
			};
			item.setRegistryName(core.getRegistryName());
			event.getRegistry().register(item);
		}
	}
}
