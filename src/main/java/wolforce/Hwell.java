package wolforce;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolforce.blocks.BlockBox;
import wolforce.blocks.base.BlockWithDescription;

@Mod(modid = Hwell.MODID, name = Hwell.NAME, version = Hwell.VERSION)
@Mod.EventBusSubscriber(modid = Hwell.MODID)
public class Hwell {

	static {
		FluidRegistry.enableUniversalBucket();
	}

	// @SidedProxy(clientSide = "wolforce.ClientProxy", serverSide =
	// "wolforce.Main")
	// public static Main proxy = new Main();

	public static final String MODID = "hwell";
	public static final String NAME = "Hearth Well";
	public static final String VERSION = "1.0";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Main.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		Main.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Main.postInit(event);
	}

	// REGS

	// ----------------------
	// FOR ITEMS

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		for (Item item : Main.items) {
			event.getRegistry().registerAll(item);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerRendersItem(ModelRegistryEvent event) {
		for (Item item : Main.items) {
			registerRenderItem(item);
		}
	}

	private static void registerRenderItem(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0,
				new net.minecraft.client.renderer.block.model.ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	// ----------------------
	// FOR BLOCKS

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for (Block b : Main.blocks)
			event.getRegistry().register(b);
		for (BlockBox box : Main.boxes) {
			event.getRegistry().register(box);
			ModelLoader.setCustomStateMapper(box, box.getMapper());
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
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerRendersBlockItem(ModelRegistryEvent event) {
		for (Block block : Main.blocks) {
			Item item = Item.getItemFromBlock(block);
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new net.minecraft.client.renderer.block.model.ModelResourceLocation(item.getRegistryName(), "inventory"));
		}

		for (BlockBox box : Main.boxes) {
			Item item = Item.getItemFromBlock(box);
			ModelLoader.setCustomModelResourceLocation(item, 0, box.getModelRes("inventory"));
		}
	}

	//

	//

	//

	public static class HwellCreativeTab extends CreativeTabs {

		public HwellCreativeTab() {
			super("hwell");
		}

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Main.myst_dust);
		}
	}

}
