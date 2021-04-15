package hantonik.atomictechnology.api;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.crafing.recipe.compressor.BasicCompressorRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.AdvancedSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.AtomicSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.BasicSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.EliteSmelterRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Optional;

public class AtomicRecipeTypes {
    public static final IRecipeType<BasicSmelterRecipe> BASIC_SMELTER = new IRecipeType<BasicSmelterRecipe>() {
        @Nonnull
        @Override
        public <C extends IInventory> Optional<BasicSmelterRecipe> matches(IRecipe<C> recipe, @Nonnull World world, @Nonnull C inv) {
            return recipe.matches(inv, world) ? Optional.of((BasicSmelterRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<EliteSmelterRecipe> ELITE_SMELTER = new IRecipeType<EliteSmelterRecipe>() {
        @Nonnull
        @Override
        public <C extends IInventory> Optional<EliteSmelterRecipe> matches(IRecipe<C> recipe, @Nonnull World world, @Nonnull C inv) {
            return recipe.matches(inv, world) ? Optional.of((EliteSmelterRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<AdvancedSmelterRecipe> ADVANCED_SMELTER = new IRecipeType<AdvancedSmelterRecipe>() {
        @Nonnull
        @Override
        public <C extends IInventory> Optional<AdvancedSmelterRecipe> matches(IRecipe<C> recipe, @Nonnull World world, @Nonnull C inv) {
            return recipe.matches(inv, world) ? Optional.of((AdvancedSmelterRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<AtomicSmelterRecipe> ATOMIC_SMELTER = new IRecipeType<AtomicSmelterRecipe>() {
        @Nonnull
        @Override
        public <C extends IInventory> Optional<AtomicSmelterRecipe> matches(IRecipe<C> recipe, @Nonnull World world, @Nonnull C inv) {
            return recipe.matches(inv, world) ? Optional.of((AtomicSmelterRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<BasicCompressorRecipe> BASIC_COMPRESSOR = new IRecipeType<BasicCompressorRecipe>() {
        @Nonnull
        @Override
        public <C extends IInventory> Optional<BasicCompressorRecipe> matches(IRecipe<C> recipe, @Nonnull World world, @Nonnull C inv) {
            return recipe.matches(inv, world) ? Optional.of((BasicCompressorRecipe) recipe) : Optional.empty();
        }
    };

    static {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(AtomicTechnology.MOD_ID, "basic_smelter"), BASIC_SMELTER);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(AtomicTechnology.MOD_ID, "elite_smelter"), ELITE_SMELTER);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(AtomicTechnology.MOD_ID, "advanced_smelter"), ADVANCED_SMELTER);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(AtomicTechnology.MOD_ID, "atomic_smelter"), ATOMIC_SMELTER);

        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(AtomicTechnology.MOD_ID, "basic_compressor"), BASIC_COMPRESSOR);
    }
}
