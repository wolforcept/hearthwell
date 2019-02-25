package wolforce.registry;

import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import wolforce.Hwell;
import wolforce.Main;
import wolforce.Util;
import wolforce.blocks.BlockBox;
import wolforce.blocks.BlockCore;
import wolforce.blocks.tile.TileCharger;
import wolforce.blocks.tile.TilePickerHolder;
import wolforce.blocks.tile.TileSeparator;
import wolforce.blocks.tile.TileStatue;
import wolforce.client.CustomBoxStateMapper;
import wolforce.client.CustomCoreStateMapper;
import wolforce.client.TesrCharger;
import wolforce.client.TesrPickerHolder;
import wolforce.client.TesrSeparator;
import wolforce.client.TesrStatue;
import wolforce.client.models.power.RenderPower;
import wolforce.entities.EntityPower;

@Mod.EventBusSubscriber(modid = Hwell.MODID, value = Side.CLIENT)
public class RegisterModels {

	public static void preInit() {
		IRenderFactory<EntityPower> fac = new IRenderFactory<EntityPower>() {
			@Override
			public Render<? super EntityPower> createRenderFor(RenderManager manager) {
				return new RenderPower(manager, Util.res("textures/entity/power.png"));
			}
		};
		RenderingRegistry.<EntityPower>registerEntityRenderingHandler(EntityPower.class, fac);
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {

		for (Item item : Main.items) {
			// if (item != Main.power_crystal)
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}

		// ModelBakery.registerItemVariants(Main.power_crystal, new
		// ModelResourceLocation(Hwell.MODID + ":power_crystal"),
		// new ModelResourceLocation(Hwell.MODID + ":power_crystal_base"));
		// ModelLoader.setCustomMeshDefinition(Main.power_crystal, new
		// ItemMeshDefinition() {
		// @Override
		// public ModelResourceLocation getModelLocation(ItemStack stack) {
		// return new ModelResourceLocation(Hwell.MODID + ":" + );
		// }
		// });

		// ModelLoader.setCustomMeshDefinition(Main.power_crystal, new
		// ItemMeshDefinition() {
		//
		// @Override
		// public ModelResourceLocation getModelLocation(ItemStack stack) {
		//
		// if (Util.isValid(stack))
		// return new ModelResourceLocation(Util.res("power_crystal"), "normal");
		//
		// NBTTagCompound nbt = stack.serializeNBT();
		// String prop1 = "power_nucleous=" + (int)
		// nbt.getFloat(ItemPowerCrystal.nucleous);
		// String prop2 = "power_relay=" + (int) nbt.getFloat(ItemPowerCrystal.relay);
		// String prop3 = "power_screen=" + (int) nbt.getFloat(ItemPowerCrystal.screen);
		//
		// ModelResourceLocation model = new
		// ModelResourceLocation(Util.res("power_crystal"),
		// String.join(",", prop1, prop2, prop3));
		// return model;
		// }
		// });

		for (Block block : Main.blocks) {
			Item itemBlock = Item.getItemFromBlock(block);
			ModelLoader.setCustomModelResourceLocation(itemBlock, 0,
					new ModelResourceLocation(itemBlock.getRegistryName(), "inventory"));
		}

		for (BlockBox box : Main.boxes) {
			CustomBoxStateMapper mapper = new CustomBoxStateMapper(box.block.getRegistryName(), box.hasAxis, box.isCore(box.block));
			customRegisterRenders(box, box.block.getRegistryName(), mapper);
		}

		for (Entry<String, BlockCore> entry : Main.custom_cores.entrySet()) {
			BlockCore core = entry.getValue();
			CustomCoreStateMapper mapper = new CustomCoreStateMapper(new ResourceLocation("hwell", "core_custom"));
			customRegisterRenders(core, new ResourceLocation("hwell", "core_custom"), mapper);
		}

		// FLUIDS
		mapFluidState(Main.liquid_souls_block, Main.liquid_souls);

		// TESRS
		ClientRegistry.bindTileEntitySpecialRenderer(TileSeparator.class, new TesrSeparator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCharger.class, new TesrCharger());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePickerHolder.class, new TesrPickerHolder());
		ClientRegistry.bindTileEntitySpecialRenderer(TileStatue.class, new TesrStatue());

	}

	// CUSTOM REGISTER RENDERS:

	private static void customRegisterRenders(Block block, ResourceLocation resourceLocation, IStateMapper mapper) {
		Item itemBlock = Item.getItemFromBlock(block);
		// register ITEM render
		ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(resourceLocation, "inventory"));
		// register BLOCK render
		ModelLoader.setCustomStateMapper(block, mapper);
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
