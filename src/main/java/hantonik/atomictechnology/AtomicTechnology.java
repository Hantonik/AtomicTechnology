package hantonik.atomictechnology;

import hantonik.atomictechnology.configs.Configs;
import hantonik.atomictechnology.datagen.AtomicBlockTagsProvider;
import hantonik.atomictechnology.datagen.AtomicItemTagsProvider;
import hantonik.atomictechnology.datagen.AtomicRecipeProvider;
import hantonik.atomictechnology.groups.AtomicGroup;
import hantonik.atomictechnology.init.*;
import hantonik.atomictechnology.registry.meltable.MeltableItemsRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@Mod(AtomicTechnology.MOD_ID)
public final class AtomicTechnology {
    public static final String MOD_ID = "atomictechnology";
    public static final String NAME = "AtomicTechnology";

    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static final Marker MOD_MARKER = new MarkerManager.Log4jMarker("MOD");

    public static final ItemGroup GROUP = new AtomicGroup();

    public AtomicTechnology() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.register(this);
        bus.register(new AtomicBlocks());
        bus.register(new AtomicItems());
        bus.register(new AtomicRecipeSerializers());
        bus.register(new AtomicTiles());
        bus.register(new AtomicContainers());

        Configs.setup();
    }

    @SubscribeEvent
    public void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.debug(MOD_MARKER, "Starting common setup");

        MinecraftForge.EVENT_BUS.register(this);

        event.enqueueWork(() -> MeltableItemsRegistry.getInstance().loadItems());

        LOGGER.debug(MOD_MARKER, "Completed common setup");
    }

    @SubscribeEvent
    public void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.debug(MOD_MARKER, "Starting client setup");

        AtomicContainers.onClientSetup();

        LOGGER.debug(MOD_MARKER, "Completed client setup");
    }

    @EventBusSubscriber(modid = MOD_ID, bus = Bus.MOD)
    public static class DataGenerators {
        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            if (event.includeServer()) {
                DataGenerator generator = event.getGenerator();

                generator.addProvider(new AtomicBlockTagsProvider(generator, event.getExistingFileHelper()));
                generator.addProvider(new AtomicItemTagsProvider(generator, event.getExistingFileHelper()));
                generator.addProvider(new AtomicRecipeProvider(generator));
            }
        }
    }
}
