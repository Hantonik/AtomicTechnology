package hantonik.atomictechnology.init;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.crafing.recipe.compressor.BasicCompressorRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.AdvancedSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.AtomicSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.BasicSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.EliteSmelterRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public final class AtomicRecipeSerializers {
    public static final IRecipeSerializer<BasicSmelterRecipe> BASIC_SMELTER = new BasicSmelterRecipe.Serializer();
    public static final IRecipeSerializer<EliteSmelterRecipe> ELITE_SMELTER = new EliteSmelterRecipe.Serializer();
    public static final IRecipeSerializer<AdvancedSmelterRecipe> ADVANCED_SMELTER = new AdvancedSmelterRecipe.Serializer();
    public static final IRecipeSerializer<AtomicSmelterRecipe> ATOMIC_SMELTER = new AtomicSmelterRecipe.Serializer();

    public static final IRecipeSerializer<BasicCompressorRecipe> BASIC_COMPRESSOR = new BasicCompressorRecipe.Serializer();

    @SubscribeEvent
    public void onRegisterSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();

        registry.register(BASIC_SMELTER.setRegistryName(new ResourceLocation(AtomicTechnology.MOD_ID, "basic_smelter")));
        registry.register(ELITE_SMELTER.setRegistryName(new ResourceLocation(AtomicTechnology.MOD_ID, "elite_smelter")));
        registry.register(ADVANCED_SMELTER.setRegistryName(new ResourceLocation(AtomicTechnology.MOD_ID, "advanced_smelter")));
        registry.register(ATOMIC_SMELTER.setRegistryName(new ResourceLocation(AtomicTechnology.MOD_ID, "atomic_smelter")));

        registry.register(BASIC_COMPRESSOR.setRegistryName(new ResourceLocation(AtomicTechnology.MOD_ID, "basic_compressor")));
    }
}
