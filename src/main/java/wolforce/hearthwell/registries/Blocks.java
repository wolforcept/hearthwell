package wolforce.hearthwell.registries;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import wolforce.hearthwell.HearthWell;

import java.util.function.Supplier;

public class Blocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HearthWell.MODID);

    public static RegistryObject<Block> register(String id, Supplier<Block> block) {
        return BLOCKS.register(id, block);
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
