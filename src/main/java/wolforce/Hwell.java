package wolforce;

import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import wolforce.blocks.BlockBox;
import wolforce.blocks.BlockCore;
import wolforce.blocks.base.BlockWithDescription;

@Mod(modid = Hwell.MODID, name = Hwell.NAME, version = Hwell.VERSION)
@Mod.EventBusSubscriber(modid = Hwell.MODID)
public class Hwell {

	static {
		FluidRegistry.enableUniversalBucket();
	}

	public static final String MODID = "hwell";
	public static final String NAME = "Hearth Well";
	public static final String VERSION = "0.1.1";
	public static final Logger logger = LogManager.getLogger(NAME);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Main.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		Main.init(event);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		for (Item item : Main.items) {
			event.getRegistry().register(item);
		}
	}

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
			item.setRegistryName(block.getRegistryName());
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
