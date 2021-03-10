package hantonik.atomictechnology.init;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.blocks.AdvancedSmelter;
import hantonik.atomictechnology.blocks.AtomicSmelter;
import hantonik.atomictechnology.blocks.BasicSmelter;
import hantonik.atomictechnology.blocks.EliteSmelter;
import hantonik.atomictechnology.blocks.basics.BasicBlock;
import hantonik.atomictechnology.items.basic.BasicBlockItem;
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
public final class Blocks {
    public static final Map<RegistryObject<Block>, Supplier<Block>> BLOCKS = new LinkedHashMap<>();

    //MACHINES
    public static final RegistryObject<Block> ADVANCED_MACHINE_CASING = register("advanced_machine_casing", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 2,6F, 12.0F));
    public static final RegistryObject<Block> ELITE_MACHINE_CASING = register("elite_machine_casing", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 2,5.5F, 11.0F));
    public static final RegistryObject<Block> BASIC_MACHINE_CASING = register("basic_machine_casing", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 1, 4.75F, 9.5F));

    public static final RegistryObject<Block> ATOMIC_SMELTER = register("atomic_smelter", AtomicSmelter::new);
    public static final RegistryObject<Block> ADVANCED_SMELTER = register("advanced_smelter", AdvancedSmelter::new);
    public static final RegistryObject<Block> ELITE_SMELTER = register("elite_smelter", EliteSmelter::new);
    public static final RegistryObject<Block> BASIC_SMELTER = register("basic_smelter", BasicSmelter::new);

    //MATERIALS
    public static final RegistryObject<Block> TITANIUM_BLOCK = register("titanium_block", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 2, 7.25F, 8.25F));
    public static final RegistryObject<Block> ALUMINIUM_BLOCK = register("aluminium_block", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 1, 3.75F, 4.75F));
    public static final RegistryObject<Block> COPPER_BLOCK = register("copper_block", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 2, 4.2F, 5.2F));
    public static final RegistryObject<Block> SILVER_BLOCK = register("silver_block", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 2, 4.1F, 5.1F));
    public static final RegistryObject<Block> LEAD_BLOCK = register("lead_block", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 1, 2.6F, 3.6F));

    //SMELTER OUTS
    public static final RegistryObject<Block> STEEL_BLOCK = register("steel_block", () -> new BasicBlock(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 2, 8.5F, 9.5F));

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
        Items.BLOCK_ITEMS.add(() -> item.apply(reg).get().setRegistryName(loc));
        return reg;
    }
}
