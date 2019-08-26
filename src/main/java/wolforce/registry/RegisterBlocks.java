package wolforce.registry;

import java.util.List;
import java.util.Map.Entry;

import integration.jei.CT;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolforce.Hwell;
import wolforce.Main;
import wolforce.Util;
import wolforce.base.BlockWithDescription;
import wolforce.blocks.BlockBox;
import wolforce.blocks.BlockCore;
import wolforce.blocks.HasCustomItem;

@Mod.EventBusSubscriber(modid = Hwell.MODID)
public class RegisterBlocks {

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {

		if (Loader.isModLoaded("crafttweaker"))
			CT.registerBlocks(event);

		for (Block b : Main.blocks)
			event.getRegistry().register(b);

		for (BlockBox box : Main.boxes)
			event.getRegistry().register(box);

		for (Entry<String, BlockCore> entry : Main.custom_cores.entrySet()) {
			BlockCore core = entry.getValue();
			event.getRegistry().register(core);
			Block graft = Main.custom_grafts.get(core);
			event.getRegistry().register(graft);
		}

	}

	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
		for (Block block : Main.blocks) {
			ItemBlock item;
			if (block instanceof HasCustomItem) {
				item = ((HasCustomItem) block).getCustomItem();
			} else {
				if (Hwell.proxy.ee(block) != null)
					item = new ItemBlock(block) {
						public String getItemStackDisplayName(ItemStack stack) {
							return (String) Hwell.proxy.ee(block);
						};
					};
				else
					item = !(block instanceof BlockWithDescription) ? new ItemBlock(block) : new ItemBlock(block) {
						public void addInformation(ItemStack stack, net.minecraft.world.World worldIn,
								java.util.List<String> tooltip, net.minecraft.client.util.ITooltipFlag flagIn) {
							for (String string : ((BlockWithDescription) block).getDescription()) {
								tooltip.add(string);
							}
						};
					};
			}
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

			Block graft = Main.custom_grafts.get(core);
			Item itemGraft = new ItemBlock(graft) {
				@Override
				public String getItemStackDisplayName(ItemStack stack) {
					return graft.getLocalizedName();
				}
			};
			itemGraft.setRegistryName(graft.getRegistryName());
			event.getRegistry().register(itemGraft);
		}

		RegisterOreDict.registerBlocks();
	}
}
