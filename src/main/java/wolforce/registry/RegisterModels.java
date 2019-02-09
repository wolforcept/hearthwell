package wolforce.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wolforce.Hwell;
import wolforce.Main;
import wolforce.blocks.BlockBox;
import wolforce.blocks.tile.TilePickerHolder;
import wolforce.blocks.tile.TileSeparator;
import wolforce.client.CustomStateMapper;
import wolforce.client.TesrPickerHolder;
import wolforce.client.TesrSeparator;

@Mod.EventBusSubscriber(modid = Hwell.MODID, value = Side.CLIENT)
public class RegisterModels {

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {

		for (Item item : Main.items) {
			regItemResourceLocation(item);
		}

		for (Block block : Main.blocks) {
			regItemBlockResourceLocation(block);
		}

		for (BlockBox box : Main.boxes) {
			regBoxResourceLocation(box);
		}

		// FLUIDS
		mapFluidState(Main.liquid_souls_block, Main.liquid_souls);

		// TESRS
		ClientRegistry.bindTileEntitySpecialRenderer(TileSeparator.class, new TesrSeparator());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePickerHolder.class, new TesrPickerHolder());
	}

	private static void regItemResourceLocation(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	private static void regItemBlockResourceLocation(Block block) {
		Item itemBlock = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(itemBlock.getRegistryName(), "inventory"));
	}

	// CUSTOM BOX REGISTER RENDERS:

	private static void regBoxResourceLocation(BlockBox box) {
		Item item = Item.getItemFromBlock(box);
		ModelLoader.setCustomModelResourceLocation(item, 0, getModelRes(box, "inventory"));
		mapBoxBlock(box);
	}

	private static void mapBoxBlock(BlockBox box) {
		ModelLoader.setCustomStateMapper(box, new CustomStateMapper(box.block.getRegistryName(), box.hasAxis, box.isCore(box.block)));
	}

	private static ModelResourceLocation getModelRes(BlockBox box, String variant) {
		return new ModelResourceLocation(box.block.getRegistryName(), variant);
	}

	// MAP FLUIDS

	private static void mapFluidState(Block block, Fluid fluid) {
		Item item = Item.getItemFromBlock(block);
		wolforce.client.FluidStateMapper mapper = new wolforce.client.FluidStateMapper(fluid);
		if (item != Items.AIR) {
			net.minecraftforge.client.model.ModelLoader.registerItemVariants(item);
			net.minecraftforge.client.model.ModelLoader.setCustomMeshDefinition(item, mapper);
		}
		net.minecraftforge.client.model.ModelLoader.setCustomStateMapper(block, mapper);
	}
}
