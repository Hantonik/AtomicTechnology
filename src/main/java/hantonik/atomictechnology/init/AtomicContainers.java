package hantonik.atomictechnology.init;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.inventory.containers.bank.BasicEnergyBankContainer;
import hantonik.atomictechnology.inventory.containers.compressor.BasicCompressorContainer;
import hantonik.atomictechnology.inventory.containers.smelter.AdvancedSmelterContainer;
import hantonik.atomictechnology.inventory.containers.smelter.AtomicSmelterContainer;
import hantonik.atomictechnology.inventory.containers.smelter.BasicSmelterContainer;
import hantonik.atomictechnology.inventory.containers.smelter.EliteSmelterContainer;
import hantonik.atomictechnology.inventory.screens.bank.BasicEnergyBankScreen;
import hantonik.atomictechnology.inventory.screens.compressor.BasicCompressorScreen;
import hantonik.atomictechnology.inventory.screens.smelter.AdvancedSmelterScreen;
import hantonik.atomictechnology.inventory.screens.smelter.AtomicSmelterScreen;
import hantonik.atomictechnology.inventory.screens.smelter.BasicSmelterScreen;
import hantonik.atomictechnology.inventory.screens.smelter.EliteSmelterScreen;
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

public final class AtomicContainers {
    public static final List<Supplier<? extends ContainerType<?>>> CONTAINERS = new ArrayList<>();

    public static final RegistryObject<ContainerType<BasicSmelterContainer>> BASIC_SMELTER = register("basic_smelter", () -> new ContainerType<>((IContainerFactory<BasicSmelterContainer>) BasicSmelterContainer::create));
    public static final RegistryObject<ContainerType<EliteSmelterContainer>> ELITE_SMELTER = register("elite_smelter", () -> new ContainerType<>((IContainerFactory<EliteSmelterContainer>) EliteSmelterContainer::create));
    public static final RegistryObject<ContainerType<AdvancedSmelterContainer>> ADVANCED_SMELTER = register("advanced_smelter", () -> new ContainerType<>((IContainerFactory<AdvancedSmelterContainer>) AdvancedSmelterContainer::create));
    public static final RegistryObject<ContainerType<AtomicSmelterContainer>> ATOMIC_SMELTER = register("atomic_smelter", () -> new ContainerType<>((IContainerFactory<AtomicSmelterContainer>) AtomicSmelterContainer::create));

    public static final RegistryObject<ContainerType<BasicEnergyBankContainer>> BASIC_ENERGY_BANK = register("basic_energy_bank", () -> new ContainerType<>((IContainerFactory<BasicEnergyBankContainer>) BasicEnergyBankContainer::create));

    public static final RegistryObject<ContainerType<BasicCompressorContainer>> BASIC_COMPRESSOR = register("basic_compressor", () -> new ContainerType<>((IContainerFactory<BasicCompressorContainer>) BasicCompressorContainer::create));

    @SubscribeEvent
    public void onRegisterContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
        IForgeRegistry<ContainerType<?>> registry = event.getRegistry();

        CONTAINERS.stream().map(Supplier::get).forEach(registry::register);
    }

    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup() {
        BASIC_SMELTER.ifPresent(container -> ScreenManager.registerFactory(container, BasicSmelterScreen::new));
        ELITE_SMELTER.ifPresent(container -> ScreenManager.registerFactory(container, EliteSmelterScreen::new));
        ADVANCED_SMELTER.ifPresent(container -> ScreenManager.registerFactory(container, AdvancedSmelterScreen::new));
        ATOMIC_SMELTER.ifPresent(container -> ScreenManager.registerFactory(container, AtomicSmelterScreen::new));

        BASIC_ENERGY_BANK.ifPresent(container -> ScreenManager.registerFactory(container, BasicEnergyBankScreen::new));

        BASIC_COMPRESSOR.ifPresent(container -> ScreenManager.registerFactory(container, BasicCompressorScreen::new));
    }

    private static <T extends ContainerType<?>> RegistryObject<T> register(String name, Supplier<? extends ContainerType<?>> container) {
        ResourceLocation loc = new ResourceLocation(AtomicTechnology.MOD_ID, name);
        CONTAINERS.add(() -> container.get().setRegistryName(loc));
        return RegistryObject.of(loc, ForgeRegistries.CONTAINERS);
    }
}
