package hantonik.atomictechnology.init;

import hantonik.atomiccore.block.BasicBlock;
import hantonik.atomiccore.items.BasicBlockItem;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.blocks.GoldiumAlloyBlock;
import hantonik.atomictechnology.blocks.VosmeniteAlloyBlock;
import hantonik.atomictechnology.blocks.compressor.BasicCompressorBlock;
import hantonik.atomictechnology.blocks.smelter.AdvancedSmelter;
import hantonik.atomictechnology.blocks.smelter.AtomicSmelter;
import hantonik.atomictechnology.blocks.smelter.BasicSmelter;
import hantonik.atomictechnology.blocks.smelter.EliteSmelter;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("UnusedDeclaration")
public final class AtomicBlocks {
    public static final Map<RegistryObject<Block>, Supplier<Block>> BLOCKS = new LinkedHashMap<>();

    //MACHINE BASES
    public static final RegistryObject<Block> ADVANCED_MACHINE_CASING = register("advanced_machine_casing", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 2,6F, 12.0F));
    public static final RegistryObject<Block> ELITE_MACHINE_CASING = register("elite_machine_casing", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 2,5.5F, 11.0F));
    public static final RegistryObject<Block> BASIC_MACHINE_CASING = register("basic_machine_casing", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 1, 4.75F, 9.5F));

    // MACHINES
    public static final RegistryObject<Block> ATOMIC_SMELTER = register("atomic_smelter", AtomicSmelter::new);
    public static final RegistryObject<Block> ADVANCED_SMELTER = register("advanced_smelter", AdvancedSmelter::new);
    public static final RegistryObject<Block> ELITE_SMELTER = register("elite_smelter", EliteSmelter::new);
    public static final RegistryObject<Block> BASIC_SMELTER = register("basic_smelter", BasicSmelter::new);

    //public static final RegistryObject<Block> ATOMIC_ENERGY_CABLE = register("atomic_energy_cable", AtomicEnergyCable::new);
    //public static final RegistryObject<Block> ADVANCED_ENERGY_CABLE = register("advanced_energy_cable", AtomicEnergyCable::new);
    //public static final RegistryObject<Block> ELITE_ENERGY_CABLE = register("elite_energy_cable", AtomicEnergyCable::new);
    //public static final RegistryObject<Block> BASIC_ENERGY_CABLE = register("basic_energy_cable", AtomicEnergyCable::new);

    //public static final RegistryObject<Block> ATOMIC_COMPRESSOR = register("atomic_compressor", );
    //public static final RegistryObject<Block> ADVANCED_COMPRESSOR = register("advanced_compressor", );
    //public static final RegistryObject<Block> ELITE_COMPRESSOR = register("elite_compressor", );
    public static final RegistryObject<Block> BASIC_COMPRESSOR = register("basic_compressor", BasicCompressorBlock::new);

    //public static final RegistryObject<Block> ATOMIC_CRUSHER = register("atomic_crusher", );
    //public static final RegistryObject<Block> ADVANCED_CRUSHER = register("advanced_crusher", );
    //public static final RegistryObject<Block> ELITE_CRUSHER = register("elite_crusher", );
    //public static final RegistryObject<Block> BASIC_CRUSHER = register("basic_crusher", );

    //MATERIALS
    public static final RegistryObject<Block> TITANIUM_BLOCK = register("titanium_block", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 2, 7.25F, 8.25F));
    public static final RegistryObject<Block> ALUMINIUM_BLOCK = register("aluminium_block", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 1, 3.75F, 4.75F));
    public static final RegistryObject<Block> COPPER_BLOCK = register("copper_block", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 2, 4.2F, 5.2F));
    public static final RegistryObject<Block> SILVER_BLOCK = register("silver_block", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 2, 4.1F, 5.1F));
    public static final RegistryObject<Block> LEAD_BLOCK = register("lead_block", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 1, 2.6F, 3.6F));
    public static final RegistryObject<Block> STEEL_BLOCK = register("steel_block", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 2, 8.5F, 9.5F));

    //ALLOYS
    public static final RegistryObject<Block> VOSMENITE_ALLOY_BLOCK = register("vosmenite_alloy_block", VosmeniteAlloyBlock::new);
    public static final RegistryObject<Block> GOLDIUM_ALLOY_BLOCK = register("goldium_alloy_block", GoldiumAlloyBlock::new);

    @SubscribeEvent
    public void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        BLOCKS.forEach((reg, block) -> {
            registry.register(block.get());
            reg.updateReference(registry);
        });
    }

    private static RegistryObject<Block> register(String name, Supplier<Block> block) {
        return register(name, block, b -> () -> new BasicBlockItem(b.get(), p -> p.group(AtomicTechnology.GROUP)));
    }

    private static RegistryObject<Block> register(String name, Supplier<Block> block, Function<RegistryObject<Block>, Supplier<? extends BlockItem>> item) {
        ResourceLocation loc = new ResourceLocation(AtomicTechnology.MOD_ID, name);
        RegistryObject<Block> reg = RegistryObject.of(loc, ForgeRegistries.BLOCKS);
        BLOCKS.put(reg, () -> block.get().setRegistryName(loc));
        AtomicItems.BLOCK_ITEMS.add(() -> item.apply(reg).get().setRegistryName(loc));
        return reg;
    }
}
