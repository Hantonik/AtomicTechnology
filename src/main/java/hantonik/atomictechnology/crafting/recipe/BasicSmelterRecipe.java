package hantonik.atomictechnology.crafting.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hantonik.atomictechnology.api.crafting.ISmelterRecipe;
import hantonik.atomictechnology.api.crafting.Recipes;
import hantonik.atomictechnology.configs.Configs;
import hantonik.atomictechnology.crafting.ISpecialRecipe;
import hantonik.atomictechnology.init.RecipeSerializers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class BasicSmelterRecipe implements ISpecialRecipe, ISmelterRecipe {
    private final ResourceLocation recipeId;
    private final Ingredient[] inputs;
    private final ItemStack output;
    private final int outputCount;
    private final int powerCost;
    private final int powerRate;

    public BasicSmelterRecipe(ResourceLocation recipeId, Ingredient[] inputs, ItemStack output, int outputCount, int powerCost) {
        this(recipeId, inputs, output, outputCount, powerCost, Configs.BASIC_SMELTER_RF_RATE.get());
    }

    public BasicSmelterRecipe(ResourceLocation recipeId, Ingredient[] inputs, ItemStack output, int outputCount, int powerCost, int powerRate) {
        this.recipeId = recipeId;
        this.inputs = inputs;
        this.output = output;
        this.outputCount = outputCount;
        this.powerCost = powerCost;
        this.powerRate = powerRate;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.recipeId;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializers.BASIC_SMELTER;
    }

    @Override
    public IRecipeType<?> getType() {
        return Recipes.BASIC_SMELTER;
    }

    @Override
    public int getPowerCost() {
        return this.powerCost;
    }

    @Override
    public int getPowerRate() {
        return this.powerRate;
    }

    @Override
    public int getOutputCount() {
        return this.outputCount;
    }

    @Override
    public Ingredient[] getInputs() {
        return this.inputs;
    }

    @Override
    public ItemStack getCraftingResult(IItemHandler inventory) {
        return this.output.copy();
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<BasicSmelterRecipe> {
        @Override
        public BasicSmelterRecipe read(ResourceLocation recipeId, JsonObject json) {
            JsonArray inputs = JSONUtils.getJsonArray(json, "inputs");
            ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "output"));
            int outputCount = JSONUtils.getInt(json, "outputCount");
            int powerRequired = JSONUtils.getInt(json, "powerRequired");
            int powerRate = JSONUtils.getInt(json, "powerRate");

            Ingredient slot1;
            Ingredient slot2;
            Ingredient slot3;

            if (inputs.size() == 3) {
                slot1 = Ingredient.deserialize(inputs.get(0));
                slot2 = Ingredient.deserialize(inputs.get(1));
                slot3 = Ingredient.deserialize(inputs.get(2));
            } else if (inputs.size() == 2) {
                slot1 = Ingredient.deserialize(inputs.get(0));
                slot2 = Ingredient.deserialize(inputs.get(1));
                slot3 = Ingredient.EMPTY;
            } else if (inputs.size() == 1) {
                slot1 = Ingredient.deserialize(inputs.get(0));
                slot2 = Ingredient.EMPTY;
                slot3 = Ingredient.EMPTY;
            } else {
                slot1 = Ingredient.EMPTY;
                slot2 = Ingredient.EMPTY;
                slot3 = Ingredient.EMPTY;
            }

            return new BasicSmelterRecipe(recipeId, new Ingredient[]{ slot1, slot2, slot3 }, output, outputCount, powerRequired, powerRate);
        }

        @Override
        public BasicSmelterRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient[] inputs = { Ingredient.read(buffer), Ingredient.read(buffer), Ingredient.read(buffer) };
            ItemStack output = buffer.readItemStack();
            int outputCount = buffer.readVarInt();
            int powerRequired = buffer.readVarInt();
            int powerRate = buffer.readVarInt();

            return new BasicSmelterRecipe(recipeId, inputs, output, outputCount, powerRequired, powerRate);
        }

        @Override
        public void write(PacketBuffer buffer, BasicSmelterRecipe recipe) {
            for (Ingredient input : recipe.inputs)
                input.write(buffer);

            buffer.writeItemStack(recipe.output);
            buffer.writeVarInt(recipe.outputCount);
            buffer.writeVarInt(recipe.powerCost);
            buffer.writeVarInt(recipe.powerRate);
        }
    }
}
