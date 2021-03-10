package hantonik.atomictechnology.api.crafting;

import hantonik.atomictechnology.AtomicTechnology;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Optional;

public class Recipes {
    public static final IRecipeType<ISmelterRecipe> BASIC_SMELTER = new IRecipeType<ISmelterRecipe>() {
        @Override
        public <C extends IInventory> Optional<ISmelterRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((ISmelterRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<ISmelterRecipe> ELITE_SMELTER = new IRecipeType<ISmelterRecipe>() {
        @Override
        public <C extends IInventory> Optional<ISmelterRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((ISmelterRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<ISmelterRecipe> ADVANCED_SMELTER = new IRecipeType<ISmelterRecipe>() {
        @Override
        public <C extends IInventory> Optional<ISmelterRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((ISmelterRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<ISmelterRecipe> ATOMIC_SMELTER = new IRecipeType<ISmelterRecipe>() {
        @Override
        public <C extends IInventory> Optional<ISmelterRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((ISmelterRecipe) recipe) : Optional.empty();
        }
    };

    static {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(AtomicTechnology.MOD_ID, "basic_smelter"), BASIC_SMELTER);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(AtomicTechnology.MOD_ID, "elite_smelter"), ELITE_SMELTER);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(AtomicTechnology.MOD_ID, "advanced_smelter"), ADVANCED_SMELTER);
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(AtomicTechnology.MOD_ID, "atomic_smelter"), ATOMIC_SMELTER);
    }
}
