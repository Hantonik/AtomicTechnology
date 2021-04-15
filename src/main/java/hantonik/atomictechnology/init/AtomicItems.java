package hantonik.atomictechnology.init;

import hantonik.atomiccore.items.BasicItem;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.items.ComponentItem;
import hantonik.atomictechnology.items.GoldiumAlloyIngotItem;
import hantonik.atomictechnology.items.PatternItem;
import hantonik.atomictechnology.items.VosmeniteAlloyIngotItem;
import hantonik.atomictechnology.lib.AtomicTooltips;
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
public final class AtomicItems {
    public static final List<Supplier<Item>> BLOCK_ITEMS = new ArrayList<>();
    public static final Map<RegistryObject<Item>, Supplier<Item>> ITEMS = new LinkedHashMap<>();

    //PROCESSORS
    public static final RegistryObject<Item> ATOMIC_PROCESSOR = register("atomic_processor");
    public static final RegistryObject<Item> ADVANCED_PROCESSOR = register("advanced_processor");
    public static final RegistryObject<Item> ELITE_PROCESSOR = register("elite_processor");
    public static final RegistryObject<Item> BASIC_PROCESSOR = register("basic_processor");

    //UTILS
    //public static final RegistryObject<Item> ATOMIC_BATTERY = register("atomic_battery", () -> new BatteryItem(500_000_000));
    //public static final RegistryObject<Item> ADVANCED_BATTERY = register("advanced_battery", () -> new BatteryItem(100_000_000));
    //public static final RegistryObject<Item> ELITE_BATTERY = register("elite_battery", () -> new BatteryItem(500_000));
    //public static final RegistryObject<Item> BASIC_BATTERY = register("basic_battery", () -> new BatteryItem(100_000));

    //MATERIALS
    public static final RegistryObject<Item> TITANIUM_INGOT = register("titanium_ingot");
    public static final RegistryObject<Item> ALUMINIUM_INGOT = register("aluminium_ingot");
    public static final RegistryObject<Item> COPPER_INGOT = register("copper_ingot");
    public static final RegistryObject<Item> SILVER_INGOT = register("silver_ingot");
    public static final RegistryObject<Item> LEAD_INGOT = register("lead_ingot");
    public static final RegistryObject<Item> STEEL_INGOT = register("steel_ingot");
    public static final RegistryObject<Item> SILICON = register("silicon");

    //ALLOYS
    public static final RegistryObject<Item> VOSMENITE_ALLOY_INGOT = register("vosmenite_alloy_ingot", VosmeniteAlloyIngotItem::new);
    public static final RegistryObject<Item> GOLDIUM_ALLOY_INGOT = register("goldium_alloy_ingot", GoldiumAlloyIngotItem::new);

    //COMPONENTS
    public static final RegistryObject<Item> IRON_PLATE = registerComponent("iron_plate");
    public static final RegistryObject<Item> STEEL_PLATE = registerComponent("steel_plate");
    public static final RegistryObject<Item> COPPER_PLATE = registerComponent("copper_plate");
    public static final RegistryObject<Item> ALUMINIUM_PLATE = registerComponent("aluminium_plate");
    public static final RegistryObject<Item> TITANIUM_PLATE = registerComponent("titanium_plate");
    public static final RegistryObject<Item> SILVER_PLATE = registerComponent("silver_plate");
    public static final RegistryObject<Item> LEAD_PLATE = registerComponent("lead_plate");
    public static final RegistryObject<Item> VOSMENITE_ALLOY_PLATE = registerComponent("vosmenite_alloy_plate", AtomicTooltips.VOSMENITE_ALLOY.buildString());
    public static final RegistryObject<Item> GOLDIUM_ALLOY_PLATE = registerComponent("goldium_alloy_plate", AtomicTooltips.GOLDIUM_ALLOY.buildString());

    public static final RegistryObject<Item> PLATE_PATTERN = registerPattern("plate_pattern");

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

    private static RegistryObject<Item> registerComponent(String name) {
        return register(name, ComponentItem::new);
    }

    private static RegistryObject<Item> registerComponent(String name, String...information) {
        return register(name, () -> new ComponentItem(information));
    }

    private static RegistryObject<Item> registerPattern(String name) {
        return register(name, PatternItem::new);
    }
}
