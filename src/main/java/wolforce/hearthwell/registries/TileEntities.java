package wolforce.hearthwell.registries;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import wolforce.hearthwell.HearthWell;
import wolforce.hearthwell.blocks.tiles.TeBurstSeed;
import wolforce.hearthwell.blocks.tiles.TeFertileSoil;

import java.util.Set;
import java.util.function.Supplier;

public class TileEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,
			HearthWell.MODID);

	private static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerType(BlockEntityType.BlockEntitySupplier<T> create, Supplier<Block> valid) {
		return () -> new BlockEntityType<>(create, Set.of(valid.get()), null);
	}

	// TILE REGISTRY

	public static final RegistryObject<BlockEntityType<TeBurstSeed>> burst_seed = REGISTRY.register("burst_seed_tile_entity",
			registerType(TeBurstSeed::new, () -> HearthWell.burst_seed.get()));

	public static final RegistryObject<BlockEntityType<TeFertileSoil>> fertile_soil = REGISTRY.register("fertile_soil_tile_entity",
			registerType(TeFertileSoil::new, () -> HearthWell.fertile_soil.get()));

}
