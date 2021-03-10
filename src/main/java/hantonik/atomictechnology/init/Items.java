package hantonik.atomictechnology.init;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.items.basic.BasicItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("UnusedDeclaration")
public final class Items {
    public static final List<Supplier<Item>> BLOCK_ITEMS = new ArrayList<>();
    public static final Map<RegistryObject<Item>, Supplier<Item>> ITEMS = new LinkedHashMap<>();

    //PROCESSORS
    public static final RegistryObject<Item> ATOMIC_PROCESSOR = register("atomic_processor");
    public static final RegistryObject<Item> ADVANCED_PROCESSOR = register("advanced_processor");
    public static final RegistryObject<Item> ELITE_PROCESSOR = register("elite_processor");
    public static final RegistryObject<Item> BASIC_PROCESSOR = register("basic_processor");

    //MATERIALS
    public static final RegistryObject<Item> TITANIUM_INGOT = register("titanium_ingot");
    public static final RegistryObject<Item> ALUMINIUM_INGOT = register("aluminium_ingot");
    public static final RegistryObject<Item> COPPER_INGOT = register("copper_ingot");
    public static final RegistryObject<Item> SILVER_INGOT = register("silver_ingot");
    public static final RegistryObject<Item> LEAD_INGOT = register("lead_ingot");

    //SMELTER OUTS
    public static final RegistryObject<Item> STEEL_INGOT = register("steel_ingot");

    @SubscribeEvent
    public void onRegisterItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        BLOCK_ITEMS.stream().map(Supplier::get).forEach(registry::register);
        ITEMS.forEach((reg, item) -> {
            registry.register(item.get());
            reg.updateReference(registry);
        });
    }

    private static RegistryObject<Item> register(String name) {
        return register(name, () -> new BasicItem(p -> p.group(AtomicTechnology.GROUP)));
    }

    private static RegistryObject<Item> register(String name, Supplier<Item> item) {
        ResourceLocation loc = new ResourceLocation(AtomicTechnology.MOD_ID, name);
        RegistryObject<Item> reg = RegistryObject.of(loc, ForgeRegistries.ITEMS);
        ITEMS.put(reg, () -> item.get().setRegistryName(loc));
        return reg;
    }
}
