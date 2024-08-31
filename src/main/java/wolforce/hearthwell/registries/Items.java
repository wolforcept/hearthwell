package wolforce.hearthwell.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import wolforce.hearthwell.HearthWell;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Items {

    public static List<RegistryObject<Item>> ITEM_LIST = new ArrayList<>();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HearthWell.MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HearthWell.MODID);

    public static RegistryObject<Item> register(String id, Supplier<Item> item) {
        var obj = ITEMS.register(id, item);
        ITEM_LIST.add(obj);
        return obj;
    }

    public static void register(IEventBus bus) {
        TABS.register("hearthwell", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.hearthwell"))
                .icon(() -> new ItemStack(HearthWell.myst_dust.get()))
                .displayItems((p, o) -> o.acceptAll(ITEM_LIST.stream().map(e -> new ItemStack(e.get())).collect(Collectors.toList())))
                .build());

        TABS.register(bus);
        ITEMS.register(bus);
    }
}
