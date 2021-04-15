package hantonik.atomictechnology.integration.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import hantonik.atomiccore.utils.helpers.RecipeHelper;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.api.AtomicRecipeTypes;
import hantonik.atomictechnology.crafing.recipe.smelter.AdvancedSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.AtomicSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.BasicSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.EliteSmelterRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ZenCodeType.Name("mods." + AtomicTechnology.MOD_ID + ".Smelter")
@ZenRegister
public final class Smelter {
    @ZenCodeType.Method
    public static void addRecipe(String id, String tier, IItemStack output, IIngredient[] inputs, int powerCost) {
        if (!matches(tier, "basic") && !matches(tier, "elite") && !matches(tier, "advanced") && !matches(tier, "atomic"))
            CraftTweakerAPI.logError("Unable to assign a tier to the Smelter Recipe for stack " + output.getCommandString() + ". Tier cannot be other than basic, elite, advanced and atomic.");

        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                if (matches(tier, "basic"))
                    RecipeHelper.addRecipe(new BasicSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost));

                else if (matches(tier, "elite"))
                    RecipeHelper.addRecipe(new EliteSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost));

                else if (matches(tier, "advanced"))
                    RecipeHelper.addRecipe(new AdvancedSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost));

                else if (matches(tier, "atomic"))
                    RecipeHelper.addRecipe(new AtomicSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost));
            }

            @Override
            public String describe() {
                return "Adding " + String.valueOf(tier.charAt(0)).toUpperCase(Locale.ROOT) + tier.substring(1).toLowerCase(Locale.ROOT) + " smelter recipe for " + output.getCommandString();
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack output, IIngredient[] inputs, int powerCost) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new BasicSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost));
                RecipeHelper.addRecipe(new EliteSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost));
                RecipeHelper.addRecipe(new AdvancedSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost));
                RecipeHelper.addRecipe(new AtomicSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost));
            }

            @Override
            public String describe() {
                return "Adding Smelter recipe for " + output.getCommandString();
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, String tier, IItemStack output, IIngredient[] inputs, int powerCost, int powerRate) {
        if (!matches(tier, "basic") && !matches(tier, "elite") && !matches(tier, "advanced") && !matches(tier, "atomic"))
            CraftTweakerAPI.logError("Unable to assign a tier to the Smelter Recipe for stack " + output.getCommandString() + ". Tier cannot be other than basic, elite, advanced and atomic.");

        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                if (matches(tier, "basic"))
                    RecipeHelper.addRecipe(new BasicSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost, powerRate));

                else if (matches(tier, "elite"))
                    RecipeHelper.addRecipe(new EliteSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost, powerRate));

                else if (matches(tier, "advanced"))
                    RecipeHelper.addRecipe(new AdvancedSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost, powerRate));

                else if (matches(tier, "atomic"))
                    RecipeHelper.addRecipe(new AtomicSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost, powerRate));
            }

            @Override
            public String describe() {
                return "Adding " + String.valueOf(tier.charAt(0)).toUpperCase(Locale.ROOT) + tier.substring(1).toLowerCase(Locale.ROOT) + " smelter recipe for " + output.getCommandString();
            }
        });
    }

    @ZenCodeType.Method
    public static void addRecipe(String id, IItemStack output, IIngredient[] inputs, int powerCost, int powerRate) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                RecipeHelper.addRecipe(new BasicSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost, powerRate));
                RecipeHelper.addRecipe(new EliteSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost, powerRate));
                RecipeHelper.addRecipe(new AdvancedSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost, powerRate));
                RecipeHelper.addRecipe(new AtomicSmelterRecipe(new ResourceLocation(AtomicTechnology.MOD_ID, id), output.getInternal(), toIngredientsList(inputs), powerCost, powerRate));
            }

            @Override
            public String describe() {
                return "Adding Smelter recipe for " + output.getCommandString();
            }
        });
    }

    @ZenCodeType.Method
    public static void remove(IItemStack stack) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                List<ResourceLocation> basic_smelter = RecipeHelper.getRecipes()
                        .getOrDefault(AtomicRecipeTypes.BASIC_SMELTER, new HashMap<>())
                        .values().stream()
                        .filter(r -> r.getRecipeOutput().isItemEqual(stack.getInternal()))
                        .map(IRecipe::getId)
                        .collect(Collectors.toList());

                List<ResourceLocation> elite_smelter = RecipeHelper.getRecipes()
                        .getOrDefault(AtomicRecipeTypes.ELITE_SMELTER, new HashMap<>())
                        .values().stream()
                        .filter(r -> r.getRecipeOutput().isItemEqual(stack.getInternal()))
                        .map(IRecipe::getId)
                        .collect(Collectors.toList());

                List<ResourceLocation> advanced_smelter = RecipeHelper.getRecipes()
                        .getOrDefault(AtomicRecipeTypes.ADVANCED_SMELTER, new HashMap<>())
                        .values().stream()
                        .filter(r -> r.getRecipeOutput().isItemEqual(stack.getInternal()))
                        .map(IRecipe::getId)
                        .collect(Collectors.toList());

                List<ResourceLocation> atomic_smelter = RecipeHelper.getRecipes()
                        .getOrDefault(AtomicRecipeTypes.ATOMIC_SMELTER, new HashMap<>())
                        .values().stream()
                        .filter(r -> r.getRecipeOutput().isItemEqual(stack.getInternal()))
                        .map(IRecipe::getId)
                        .collect(Collectors.toList());

                basic_smelter.forEach(r -> RecipeHelper.getRecipes().get(AtomicRecipeTypes.BASIC_SMELTER).remove(r));
                elite_smelter.forEach(r -> RecipeHelper.getRecipes().get(AtomicRecipeTypes.ELITE_SMELTER).remove(r));
                advanced_smelter.forEach(r -> RecipeHelper.getRecipes().get(AtomicRecipeTypes.ADVANCED_SMELTER).remove(r));
                atomic_smelter.forEach(r -> RecipeHelper.getRecipes().get(AtomicRecipeTypes.ATOMIC_SMELTER).remove(r));
            }

            @Override
            public String describe() {
                return "Removing Smelter recipes for " + stack.getCommandString();
            }
        });
    }

    @ZenCodeType.Method
    public static void remove(String tier, IItemStack stack) {
        CraftTweakerAPI.apply(new IRuntimeAction() {
            @Override
            public void apply() {
                if (matches(tier, "basic")) {
                    List<ResourceLocation> basic_smelter = RecipeHelper.getRecipes()
                            .getOrDefault(AtomicRecipeTypes.BASIC_SMELTER, new HashMap<>())
                            .values().stream()
                            .filter(r -> r.getRecipeOutput().isItemEqual(stack.getInternal()))
                            .map(IRecipe::getId)
                            .collect(Collectors.toList());

                    basic_smelter.forEach(r -> RecipeHelper.getRecipes().get(AtomicRecipeTypes.BASIC_SMELTER).remove(r));
                } else if (matches(tier, "elite")) {
                    List<ResourceLocation> elite_smelter = RecipeHelper.getRecipes()
                            .getOrDefault(AtomicRecipeTypes.ELITE_SMELTER, new HashMap<>())
                            .values().stream()
                            .filter(r -> r.getRecipeOutput().isItemEqual(stack.getInternal()))
                            .map(IRecipe::getId)
                            .collect(Collectors.toList());

                    elite_smelter.forEach(r -> RecipeHelper.getRecipes().get(AtomicRecipeTypes.ELITE_SMELTER).remove(r));
                } else if (matches(tier, "advanced")) {
                    List<ResourceLocation> advanced_smelter = RecipeHelper.getRecipes()
                            .getOrDefault(AtomicRecipeTypes.ADVANCED_SMELTER, new HashMap<>())
                            .values().stream()
                            .filter(r -> r.getRecipeOutput().isItemEqual(stack.getInternal()))
                            .map(IRecipe::getId)
                            .collect(Collectors.toList());

                    advanced_smelter.forEach(r -> RecipeHelper.getRecipes().get(AtomicRecipeTypes.ADVANCED_SMELTER).remove(r));
                } else if (matches(tier, "atomic")) {
                    List<ResourceLocation> atomic_smelter = RecipeHelper.getRecipes()
                            .getOrDefault(AtomicRecipeTypes.ATOMIC_SMELTER, new HashMap<>())
                            .values().stream()
                            .filter(r -> r.getRecipeOutput().isItemEqual(stack.getInternal()))
                            .map(IRecipe::getId)
                            .collect(Collectors.toList());

                    atomic_smelter.forEach(r -> RecipeHelper.getRecipes().get(AtomicRecipeTypes.ATOMIC_SMELTER).remove(r));
                }
            }

            @Override
            public String describe() {
                return "Removing " + String.valueOf(tier.charAt(0)).toUpperCase(Locale.ROOT) + tier.substring(1).toLowerCase(Locale.ROOT) + " Smelter recipe for " + stack.getCommandString();
            }
        });
    }

    private static NonNullList<Ingredient> toIngredientsList(IIngredient... ingredients) {
        return Arrays.stream(ingredients)
                .map(IIngredient::asVanillaIngredient)
                .collect(Collectors.toCollection(NonNullList::create));
    }

    private static boolean matches(String test, String tier) {
        if (test.equals(tier))
            return true;

        else if (test.toLowerCase(Locale.ROOT).equals(tier))
            return true;

        else
            return test.toUpperCase(Locale.ROOT).equals(tier);
    }
}
