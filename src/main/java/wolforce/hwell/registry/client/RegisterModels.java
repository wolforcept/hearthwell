package wolforce.hwell.registry.client;

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
import wolforce.hwell.Hwell;
import wolforce.hwell.Main;
import wolforce.hwell.blocks.BlockBox;
import wolforce.hwell.blocks.BlockCore;
import wolforce.hwell.blocks.tile.TileBranch;
import wolforce.hwell.blocks.tile.TileCharger;
import wolforce.hwell.blocks.tile.TileGraftingTray;
import wolforce.hwell.blocks.tile.TileGritVase;
import wolforce.hwell.blocks.tile.TileInertSeed;
import wolforce.hwell.blocks.tile.TileMutator;
import wolforce.hwell.blocks.tile.TilePickerHolder;
import wolforce.hwell.blocks.tile.TileSeparator;
import wolforce.hwell.blocks.tile.TileStatue;
import wolforce.hwell.blocks.tile.TileTray;
import wolforce.hwell.client.CustomBoxStateMapper;
import wolforce.hwell.client.CustomCoreStateMapper;
import wolforce.hwell.client.models.power.RenderPower;
import wolforce.hwell.client.tesr.TesrBranch;
import wolforce.hwell.client.tesr.TesrCharger;
import wolforce.hwell.client.tesr.TesrGraftingTray;
import wolforce.hwell.client.tesr.TesrGritVase;
import wolforce.hwell.client.tesr.TesrInertSeed;
import wolforce.hwell.client.tesr.TesrMutator;
import wolforce.hwell.client.tesr.TesrPickerHolder;
import wolforce.hwell.client.tesr.TesrSeparator;
import wolforce.hwell.client.tesr.TesrStatue;
import wolforce.hwell.client.tesr.TesrTray;
import wolforce.hwell.entities.EntityPower;
import wolforce.mechanics.Util;

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
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
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
			CustomBoxStateMapper mapper = new CustomBoxStateMapper(box.block.getRegistryName(), box.hasAxis);
			customRegisterRenders(box, box.block.getRegistryName(), mapper);
		}

		for (Entry<String, BlockCore> entry : Main.cores.entrySet()) {
			BlockCore core = entry.getValue();
			if (!core.isCustom()) {
				Item itemBlock = Item.getItemFromBlock(core);
				ModelLoader.setCustomModelResourceLocation(itemBlock, 0,
						new ModelResourceLocation(itemBlock.getRegistryName(), "inventory"));
				Item itemBlock2 = Item.getItemFromBlock(Main.custom_grafts.get(core));
				ModelLoader.setCustomModelResourceLocation(itemBlock2, 0,
						new ModelResourceLocation(itemBlock2.getRegistryName(), "inventory"));
				continue;
			}
			CustomCoreStateMapper mapper = new CustomCoreStateMapper(new ResourceLocation("hwell", "core_custom"));
			customRegisterRenders(core, new ResourceLocation("hwell", "core_custom"), mapper);

			CustomCoreStateMapper mapperGraft = new CustomCoreStateMapper(
					new ResourceLocation("hwell", "graft_custom"));
			Block graft = Main.custom_grafts.get(core);
			customRegisterRenders(graft, new ResourceLocation("hwell", "graft_custom"), mapperGraft);
		}

		// FLUIDS
		mapFluidState(Main.liquid_souls_block, Main.liquid_souls);

		// TESRS
		ClientRegistry.bindTileEntitySpecialRenderer(TileSeparator.class, new TesrSeparator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCharger.class, new TesrCharger());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePickerHolder.class, new TesrPickerHolder());
		ClientRegistry.bindTileEntitySpecialRenderer(TileStatue.class, new TesrStatue());
		ClientRegistry.bindTileEntitySpecialRenderer(TileTray.class, new TesrTray());
		ClientRegistry.bindTileEntitySpecialRenderer(TileGritVase.class, new TesrGritVase());
		ClientRegistry.bindTileEntitySpecialRenderer(TileInertSeed.class, new TesrInertSeed());
		ClientRegistry.bindTileEntitySpecialRenderer(TileGraftingTray.class, new TesrGraftingTray());
		ClientRegistry.bindTileEntitySpecialRenderer(TileBranch.class, new TesrBranch());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMutator.class, new TesrMutator());

	}

	// CUSTOM REGISTER RENDERS:

	private static void customRegisterRenders(Block block, ResourceLocation resourceLocation, IStateMapper mapper) {
		Item itemBlock = Item.getItemFromBlock(block);
		// register ITEM render
		ModelLoader.setCustomModelResourceLocation(itemBlock, 0,
				new ModelResourceLocation(resourceLocation, "inventory"));
		// register BLOCK render
		ModelLoader.setCustomStateMapper(block, mapper);
	}

	// MAP FLUIDS

	private static void mapFluidState(Block block, Fluid fluid) {
		Item item = Item.getItemFromBlock(block);
		wolforce.hwell.client.FluidStateMapper mapper = new wolforce.hwell.client.FluidStateMapper(fluid);
		if (item != Items.AIR) {
			net.minecraftforge.client.model.ModelLoader.registerItemVariants(item);
			net.minecraftforge.client.model.ModelLoader.setCustomMeshDefinition(item, mapper);
		}
		net.minecraftforge.client.model.ModelLoader.setCustomStateMapper(block, mapper);
	}
}
