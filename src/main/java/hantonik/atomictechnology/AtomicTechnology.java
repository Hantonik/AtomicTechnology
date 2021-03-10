package hantonik.atomictechnology;

import hantonik.atomictechnology.configs.Configs;
import hantonik.atomictechnology.groups.MainGroup;
import hantonik.atomictechnology.init.*;
import hantonik.atomictechnology.recipes.smelter.AlloyRegistry;
import hantonik.atomictechnology.utils.managers.RecipeManager;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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

    public static final ItemGroup GROUP = new MainGroup();

    public AtomicTechnology() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.register(this);
        bus.register(new Blocks());
        bus.register(new Items());
        bus.register(new RecipeSerializers());
        bus.register(new Tiles());
        bus.register(new Containers());

        Configs.setup();
    }

    @SubscribeEvent
    public void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.debug(MOD_MARKER, "Starting common setup");

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new RecipeManager());

        event.enqueueWork(() -> AlloyRegistry.getInstance().loadAlloys());

        LOGGER.debug(MOD_MARKER, "Completed common setup");
    }

    @SubscribeEvent
    public void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.debug(MOD_MARKER, "Starting client setup");

        Containers.onClientSetup();

        LOGGER.debug(MOD_MARKER, "Completed client setup");
    }
}
