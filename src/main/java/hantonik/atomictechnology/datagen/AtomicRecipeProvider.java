package hantonik.atomictechnology.datagen;

import hantonik.atomiccore.utils.Criteria;
import hantonik.atomiccore.utils.helpers.ItemHelper;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.configs.Configs;
import hantonik.atomictechnology.crafing.recipe.compressor.BasicCompressorRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.AdvancedSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.AtomicSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.BasicSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.EliteSmelterRecipe;
import hantonik.atomictechnology.init.AtomicBlocks;
import hantonik.atomictechnology.init.AtomicItems;
import hantonik.atomictechnology.init.AtomicTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class AtomicRecipeProvider extends RecipeProvider {
    public AtomicRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        if (!Configs.ENABLE_DEFAULT_RECIPES.get())
            return;

        this.registerSmelterRecipes(consumer);
        this.registerCompressorRecipes(consumer);
        this.registerShapelessRecipes(consumer);
    }

    private void registerSmelterRecipes(Consumer<IFinishedRecipe> consumer) {
        smelter(new ResourceLocation(AtomicTechnology.MOD_ID, "steel_ingot"),
                new ItemStack(AtomicItems.STEEL_INGOT.get(), 2),
                ItemHelper.toIngredientsList(Ingredient.fromTag(Tags.Items.INGOTS_IRON), Ingredient.fromItems(Items.COAL)),
                7500, consumer);

        smelter(new ResourceLocation(AtomicTechnology.MOD_ID, "goldium_alloy_ingot"),
                new ItemStack(AtomicItems.GOLDIUM_ALLOY_INGOT.get(), 2),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.INGOTS_COPPER), Ingredient.fromTag(Tags.Items.INGOTS_GOLD)),
                13_500, consumer);

        eliteSmelter(new ResourceLocation(AtomicTechnology.MOD_ID, "vosmenite_alloy_ingot"),
                new ItemStack(AtomicItems.VOSMENITE_ALLOY_INGOT.get(), 3),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.INGOTS_TITANIUM), Ingredient.fromTag(AtomicTags.Items.INGOTS_STEEL), Ingredient.fromTag(AtomicTags.Items.INGOTS_COPPER)),
                23_500, consumer);
        advancedSmelter(new ResourceLocation(AtomicTechnology.MOD_ID, "vosmenite_alloy_ingot"),
                new ItemStack(AtomicItems.VOSMENITE_ALLOY_INGOT.get(), 3),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.INGOTS_TITANIUM), Ingredient.fromTag(AtomicTags.Items.INGOTS_STEEL), Ingredient.fromTag(AtomicTags.Items.INGOTS_COPPER)),
                23_500, consumer);
        atomicSmelter(new ResourceLocation(AtomicTechnology.MOD_ID, "vosmenite_alloy_ingot"),
                new ItemStack(AtomicItems.VOSMENITE_ALLOY_INGOT.get(), 3),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.INGOTS_TITANIUM), Ingredient.fromTag(AtomicTags.Items.INGOTS_STEEL), Ingredient.fromTag(AtomicTags.Items.INGOTS_COPPER)),
                23_500, consumer);

        smelter(new ResourceLocation(AtomicTechnology.MOD_ID, "steel_block"),
                new ItemStack(AtomicBlocks.STEEL_BLOCK.get(), 2),
                ItemHelper.toIngredientsList(Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_IRON), Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_COAL)),
                37500, consumer);

        smelter(new ResourceLocation(AtomicTechnology.MOD_ID, "goldium_alloy_block"),
                new ItemStack(AtomicBlocks.GOLDIUM_ALLOY_BLOCK.get(), 2),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_COPPER), Ingredient.fromTag(Tags.Items.STORAGE_BLOCKS_GOLD)),
                67_500, consumer);

        eliteSmelter(new ResourceLocation(AtomicTechnology.MOD_ID, "vosmenite_alloy_block"),
                new ItemStack(AtomicBlocks.VOSMENITE_ALLOY_BLOCK.get(), 3),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_TITANIUM), Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_STEEL), Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_COPPER)),
                124_500, consumer);
        advancedSmelter(new ResourceLocation(AtomicTechnology.MOD_ID, "vosmenite_alloy_block"),
                new ItemStack(AtomicBlocks.VOSMENITE_ALLOY_BLOCK.get(), 3),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_TITANIUM), Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_STEEL), Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_COPPER)),
                124_500, consumer);
        atomicSmelter(new ResourceLocation(AtomicTechnology.MOD_ID, "vosmenite_alloy_block"),
                new ItemStack(AtomicBlocks.VOSMENITE_ALLOY_BLOCK.get(), 3),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_TITANIUM), Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_STEEL), Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_COPPER)),
                124_500, consumer);

    }

    private void registerCompressorRecipes(Consumer<IFinishedRecipe> consumer) {
        compressor(new ResourceLocation(AtomicTechnology.MOD_ID, "iron_plate"),
                new ItemStack(AtomicItems.IRON_PLATE.get()),
                ItemHelper.toIngredientsList(Ingredient.fromTag(Tags.Items.INGOTS_IRON), Ingredient.fromTag(Tags.Items.INGOTS_IRON)),
                Ingredient.fromItems(AtomicItems.PLATE_PATTERN.get()),
                2_000, consumer);

        compressor(new ResourceLocation(AtomicTechnology.MOD_ID, "steel_plate"),
                new ItemStack(AtomicItems.STEEL_PLATE.get()),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.INGOTS_STEEL), Ingredient.fromTag(AtomicTags.Items.INGOTS_STEEL)),
                Ingredient.fromItems(AtomicItems.PLATE_PATTERN.get()),
                2_500, consumer);
        compressor(new ResourceLocation(AtomicTechnology.MOD_ID, "aluminium_plate"),
                new ItemStack(AtomicItems.ALUMINIUM_PLATE.get()),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.INGOTS_ALUMINIUM), Ingredient.fromTag(AtomicTags.Items.INGOTS_ALUMINIUM)),
                Ingredient.fromItems(AtomicItems.PLATE_PATTERN.get()),
                2_000, consumer);
        compressor(new ResourceLocation(AtomicTechnology.MOD_ID, "copper_plate"),
                new ItemStack(AtomicItems.COPPER_PLATE.get()),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.INGOTS_COPPER), Ingredient.fromTag(AtomicTags.Items.INGOTS_COPPER)),
                Ingredient.fromItems(AtomicItems.PLATE_PATTERN.get()),
                2_000, consumer);
        compressor(new ResourceLocation(AtomicTechnology.MOD_ID, "lead_plate"),
                new ItemStack(AtomicItems.LEAD_PLATE.get()),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.INGOTS_LEAD), Ingredient.fromTag(AtomicTags.Items.INGOTS_LEAD)),
                Ingredient.fromItems(AtomicItems.PLATE_PATTERN.get()),
                2_000, consumer);
        compressor(new ResourceLocation(AtomicTechnology.MOD_ID, "silver_plate"),
                new ItemStack(AtomicItems.SILVER_PLATE.get()),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.INGOTS_SILVER), Ingredient.fromTag(AtomicTags.Items.INGOTS_SILVER)),
                Ingredient.fromItems(AtomicItems.PLATE_PATTERN.get()),
                2_000, consumer);
        compressor(new ResourceLocation(AtomicTechnology.MOD_ID, "titanium_plate"),
                new ItemStack(AtomicItems.TITANIUM_PLATE.get()),
                ItemHelper.toIngredientsList(Ingredient.fromTag(AtomicTags.Items.INGOTS_TITANIUM), Ingredient.fromTag(AtomicTags.Items.INGOTS_TITANIUM)),
                Ingredient.fromItems(AtomicItems.PLATE_PATTERN.get()),
                2_500, consumer);
        compressor(new ResourceLocation(AtomicTechnology.MOD_ID, "vosmenite_alloy_plate"),
                new ItemStack(AtomicItems.VOSMENITE_ALLOY_PLATE.get()),
                ItemHelper.toIngredientsList(Ingredient.fromItems(AtomicItems.VOSMENITE_ALLOY_INGOT.get()), Ingredient.fromItems(AtomicItems.VOSMENITE_ALLOY_INGOT.get())),
                Ingredient.fromItems(AtomicItems.PLATE_PATTERN.get()),
                4_500, consumer);
        compressor(new ResourceLocation(AtomicTechnology.MOD_ID, "goldium_alloy_plate"),
                new ItemStack(AtomicItems.GOLDIUM_ALLOY_PLATE.get()),
                ItemHelper.toIngredientsList(Ingredient.fromItems(AtomicItems.GOLDIUM_ALLOY_INGOT.get()), Ingredient.fromItems(AtomicItems.GOLDIUM_ALLOY_INGOT.get())),
                Ingredient.fromItems(AtomicItems.PLATE_PATTERN.get()),
                2_500, consumer);
    }

    private void registerShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapelessRecipe(AtomicItems.STEEL_INGOT.get(), 9).addIngredient(Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_STEEL), 1).addCriterion("has_steel_block", hasItem(AtomicTags.Items.STORAGE_BLOCKS_STEEL)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(AtomicItems.ALUMINIUM_INGOT.get(), 9).addIngredient(Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_ALUMINIUM), 1).addCriterion("has_aluminium_block", hasItem(AtomicTags.Items.STORAGE_BLOCKS_ALUMINIUM)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(AtomicItems.COPPER_INGOT.get(), 9).addIngredient(Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_COPPER), 1).addCriterion("has_copper_block", hasItem(AtomicTags.Items.STORAGE_BLOCKS_COPPER)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(AtomicItems.LEAD_INGOT.get(), 9).addIngredient(Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_LEAD), 1).addCriterion("has_lead_block", hasItem(AtomicTags.Items.STORAGE_BLOCKS_LEAD)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(AtomicItems.SILVER_INGOT.get(), 9).addIngredient(Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_SILVER), 1).addCriterion("has_silver_block", hasItem(AtomicTags.Items.STORAGE_BLOCKS_SILVER)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(AtomicItems.TITANIUM_INGOT.get(), 9).addIngredient(Ingredient.fromTag(AtomicTags.Items.STORAGE_BLOCKS_TITANIUM), 1).addCriterion("has_titanium_block", hasItem(AtomicTags.Items.STORAGE_BLOCKS_TITANIUM)).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(AtomicItems.VOSMENITE_ALLOY_INGOT.get(), 9).addIngredient(Ingredient.fromItems(AtomicBlocks.VOSMENITE_ALLOY_BLOCK.get()), 1).addCriterion("has_vosmenite_alloy_block", hasItem(AtomicBlocks.VOSMENITE_ALLOY_BLOCK.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(AtomicItems.GOLDIUM_ALLOY_INGOT.get(), 9).addIngredient(Ingredient.fromItems(AtomicBlocks.GOLDIUM_ALLOY_BLOCK.get()), 1).addCriterion("has_goldium_alloy_block", hasItem(AtomicBlocks.GOLDIUM_ALLOY_BLOCK.get())).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(Items.FLINT_AND_STEEL).addIngredient(Ingredient.fromItems(Items.FLINT)).addIngredient(Ingredient.fromTag(AtomicTags.Items.INGOTS_STEEL)).addCriterion("has_flint", hasItem(Items.FLINT)).addCriterion("has_steel_ingot", hasItem(AtomicTags.Items.INGOTS_STEEL)).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(AtomicBlocks.STEEL_BLOCK.get()).addIngredient(Ingredient.fromTag(AtomicTags.Items.INGOTS_STEEL), 9).addCriterion("has_steel_ingot", hasItem(AtomicTags.Items.INGOTS_STEEL)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(AtomicBlocks.ALUMINIUM_BLOCK.get()).addIngredient(Ingredient.fromTag(AtomicTags.Items.INGOTS_ALUMINIUM), 9).addCriterion("has_aluminium_ingot", hasItem(AtomicTags.Items.INGOTS_ALUMINIUM)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(AtomicBlocks.COPPER_BLOCK.get()).addIngredient(Ingredient.fromTag(AtomicTags.Items.INGOTS_COPPER), 9).addCriterion("has_copper_ingot", hasItem(AtomicTags.Items.INGOTS_COPPER)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(AtomicBlocks.LEAD_BLOCK.get()).addIngredient(Ingredient.fromTag(AtomicTags.Items.INGOTS_LEAD), 9).addCriterion("has_lead_ingot", hasItem(AtomicTags.Items.INGOTS_LEAD)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(AtomicBlocks.SILVER_BLOCK.get()).addIngredient(Ingredient.fromTag(AtomicTags.Items.INGOTS_SILVER), 9).addCriterion("has_silver_ingot", hasItem(AtomicTags.Items.INGOTS_SILVER)).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(AtomicBlocks.TITANIUM_BLOCK.get()).addIngredient(Ingredient.fromTag(AtomicTags.Items.INGOTS_TITANIUM), 9).addCriterion("has_titanium_ingot", hasItem(AtomicTags.Items.INGOTS_TITANIUM)).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(AtomicBlocks.VOSMENITE_ALLOY_BLOCK.get()).addIngredient(Ingredient.fromItems(AtomicItems.VOSMENITE_ALLOY_INGOT.get()), 9).addCriterion("has_vosmenite_alloy_ingot", hasItem(AtomicItems.VOSMENITE_ALLOY_INGOT.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(AtomicBlocks.GOLDIUM_ALLOY_BLOCK.get()).addIngredient(Ingredient.fromItems(AtomicItems.GOLDIUM_ALLOY_INGOT.get()), 9).addCriterion("has_goldium_alloy_ingot", hasItem(AtomicItems.GOLDIUM_ALLOY_INGOT.get())).build(consumer);
    }

    private void smelter(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, int powerCost, Consumer<IFinishedRecipe> consumer) {
        basicSmelter(id, output, inputs, powerCost, consumer);
        eliteSmelter(id, output, inputs, powerCost, consumer);
        advancedSmelter(id, output, inputs, powerCost, consumer);
        atomicSmelter(id, output, inputs, powerCost, consumer);
    }

    private void basicSmelter(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, int powerCost, Consumer<IFinishedRecipe> consumer) {
        new BasicSmelterRecipe(id, output, inputs, powerCost).addCriterion(Criteria.has(output.getItem())).build(consumer, new ResourceLocation(id.getNamespace(), "smelter/basic_smelter/" + id.getPath()));
    }

    private void eliteSmelter(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, int powerCost, Consumer<IFinishedRecipe> consumer) {
        new EliteSmelterRecipe(id, output, inputs, powerCost).addCriterion(Criteria.has(output.getItem())).build(consumer, new ResourceLocation(id.getNamespace(), "smelter/elite_smelter/" + id.getPath()));
    }

    private void advancedSmelter(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, int powerCost, Consumer<IFinishedRecipe> consumer) {
        new AdvancedSmelterRecipe(id, output, inputs, powerCost).addCriterion(Criteria.has(output.getItem())).build(consumer, new ResourceLocation(id.getNamespace(), "smelter/advanced_smelter/" + id.getPath()));
    }

    private void atomicSmelter(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, int powerCost, Consumer<IFinishedRecipe> consumer) {
        new AtomicSmelterRecipe(id, output, inputs, powerCost).addCriterion(Criteria.has(output.getItem())).build(consumer, new ResourceLocation(id.getNamespace(), "smelter/atomic_smelter/" + id.getPath()));
    }

    private void compressor(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, Ingredient pattern, int powerCost, Consumer<IFinishedRecipe> consumer) {
        basicCompressor(id, output, inputs, pattern, powerCost, consumer);
        eliteCompressor(id, output, inputs, pattern, powerCost, consumer);
        advancedCompressor(id, output, inputs, pattern, powerCost, consumer);
        atomicCompressor(id, output, inputs, pattern, powerCost, consumer);
    }

    private void basicCompressor(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, Ingredient pattern, int powerCost, Consumer<IFinishedRecipe> consumer) {
        new BasicCompressorRecipe(id, output, inputs, pattern, powerCost).addCriterion(Criteria.has(output.getItem())).build(consumer, new ResourceLocation(AtomicTechnology.MOD_ID, "compressor/basic_compressor/" + id.getPath()));
    }

    private void eliteCompressor(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, Ingredient pattern, int powerCost, Consumer<IFinishedRecipe> consumer) {

    }

    private void advancedCompressor(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, Ingredient pattern, int powerCost, Consumer<IFinishedRecipe> consumer) {

    }

    private void atomicCompressor(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, Ingredient pattern, int powerCost, Consumer<IFinishedRecipe> consumer) {

    }
}
