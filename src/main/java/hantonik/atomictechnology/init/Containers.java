package hantonik.atomictechnology.init;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.inventory.containers.BasicSmelterContainer;
import hantonik.atomictechnology.inventory.screens.BasicSmelterScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class Containers {
    public static final List<Supplier<? extends ContainerType<?>>> CONTAINERS = new ArrayList<>();

    public static final RegistryObject<ContainerType<BasicSmelterContainer>> BASIC_SMELTER = register("basic_smelter", () -> new ContainerType<>((IContainerFactory<BasicSmelterContainer>) BasicSmelterContainer::create));

    @SubscribeEvent
    public void onRegisterContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
        IForgeRegistry<ContainerType<?>> registry = event.getRegistry();

        CONTAINERS.stream().map(Supplier::get).forEach(registry::register);
    }

    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup() {
        BASIC_SMELTER.ifPresent(container -> ScreenManager.registerFactory(container, BasicSmelterScreen::new));
    }

    private static <T extends ContainerType<?>> RegistryObject<T> register(String name, Supplier<? extends ContainerType<?>> container) {
        ResourceLocation loc = new ResourceLocation(AtomicTechnology.MOD_ID, name);
        CONTAINERS.add(() -> container.get().setRegistryName(loc));
        return RegistryObject.of(loc, ForgeRegistries.CONTAINERS);
    }
}
