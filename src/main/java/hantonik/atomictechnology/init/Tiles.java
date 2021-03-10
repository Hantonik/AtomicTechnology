package hantonik.atomictechnology.init;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.tiles.BasicSmelterTile;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class Tiles {
    public static final List<Supplier<? extends TileEntityType<?>>> TILES = new ArrayList<>();

    public static final RegistryObject<TileEntityType<BasicSmelterTile>> BASIC_SMELTER = register("basic_smelter", BasicSmelterTile::new, () -> new Block[] { Blocks.BASIC_SMELTER.get() });
    /*public static final RegistryObject<TileEntityType<EliteSmelterTile>> ELITE_SMELTER = register("elite_smelter", EliteSmelterTile::new, () -> new Block[] { Blocks.ELITE_SMELTER.get() });
    public static final RegistryObject<TileEntityType<AdvancedSmelterTile>> ADVANCED_SMELTER = register("advanced_smelter", AdvancedSmelterTile::new, () -> new Block[] { Blocks.ADVANCED_SMELTER.get() });
    public static final RegistryObject<TileEntityType<AtomicSmelterTile>> ATOMIC_SMELTER = register("atomic_smelter", AtomicSmelterTile::new, () -> new Block[] { Blocks.ATOMIC_SMELTER.get() });*/

    @SubscribeEvent
    public void onRegisterTypes(RegistryEvent.Register<TileEntityType<?>> event) {
        IForgeRegistry<TileEntityType<?>> registry = event.getRegistry();

        TILES.stream().map(Supplier::get).forEach(registry::register);
    }

    private static <T extends TileEntityType<?>> RegistryObject<T> register(String name, Supplier<TileEntity> tile, Supplier<Block[]> blocks) {
        ResourceLocation loc = new ResourceLocation(AtomicTechnology.MOD_ID, name);
        TILES.add(() -> TileEntityType.Builder.create(tile, blocks.get()).build(null).setRegistryName(loc));
        return RegistryObject.of(loc, ForgeRegistries.TILE_ENTITIES);
    }
}
