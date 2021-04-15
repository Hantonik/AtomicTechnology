package hantonik.atomictechnology.crafing.recipe.smelter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hantonik.atomiccore.utils.helpers.ItemHelper;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.api.AtomicRecipeTypes;
import hantonik.atomictechnology.api.crafting.ISmelterRecipe;
import hantonik.atomictechnology.configs.Configs;
import hantonik.atomictechnology.crafing.recipe.RecipeBuilder;
import hantonik.atomictechnology.init.AtomicRecipeSerializers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Arrays;

public class AtomicSmelterRecipe extends RecipeBuilder<AtomicSmelterRecipe> implements ISmelterRecipe {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputs;
    private final int powerCost;
    private final int powerRate;

    public AtomicSmelterRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, int powerCost) {
        this(id, output, inputs, powerCost, Configs.ATOMIC_SMELTER_RF_RATE.get());
    }

    public AtomicSmelterRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, int powerCost, int powerRate) {
        super(new ResourceLocation(AtomicTechnology.MOD_ID, "atomic_smelter"));

        this.id = id;
        this.output = output;
        this.inputs = inputs;
        this.powerCost = powerCost;
        this.powerRate = powerRate;
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
    public NonNullList<Ingredient> getInputs() {
        return this.inputs;
    }

    @Override
    public String getSerializerName() {
        return this.serializerName.getPath();
    }

    @Override
    public ItemStack getCraftingResult(IItemHandler inventory) {
        return this.output.copy();
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
        return this.id;
    }

    @Override
    public boolean matches(IItemHandler inventory) {
        return Arrays.stream(new ItemStack[]{ inventory.getStackInSlot(0), inventory.getStackInSlot(1), inventory.getStackInSlot(2) }).allMatch(stack -> {
            for (Ingredient ingredient : this.inputs)
                if (ingredient.test(stack))
                    return true;

            return false;
        });
    }

    @Override
    public ResourceLocation getRecipeLocation() {
        return new ResourceLocation(AtomicTechnology.MOD_ID, "atomic_smelter");
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return AtomicRecipeSerializers.ATOMIC_SMELTER;
    }

    @Override
    public IRecipeType<?> getType() {
        return AtomicRecipeTypes.ATOMIC_SMELTER;
    }

    @Override
    protected RecipeResult getResult(ResourceLocation id) {
        return new SmelterRecipeResult(id);
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AtomicSmelterRecipe> {
        @Override
        public AtomicSmelterRecipe read(ResourceLocation recipeId, JsonObject json) {
            JsonArray inputsArray = JSONUtils.getJsonArray(json, "inputs");
            NonNullList<Ingredient> inputs = NonNullList.create();

            for (int i = 0; i < inputsArray.size(); i++)
                inputs.add(Ingredient.deserialize(inputsArray.get(i)));

            ItemStack output = ItemHelper.deserializeStack(JSONUtils.getJsonObject(json, "output"));
            int powerCost = JSONUtils.getInt(json, "powerCost");
            int powerRate = JSONUtils.getInt(json, "powerRate");

            return new AtomicSmelterRecipe(recipeId, output, inputs, powerCost, powerRate);
        }

        @Override
        public AtomicSmelterRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            NonNullList<Ingredient> inputs = NonNullList.create();

            inputs.add(Ingredient.read(buffer));
            inputs.add(Ingredient.read(buffer));
            inputs.add(Ingredient.read(buffer));

            ItemStack output = buffer.readItemStack();
            int powerCost = buffer.readVarInt();
            int powerRate = buffer.readVarInt();

            return new AtomicSmelterRecipe(recipeId, output, inputs, powerCost, powerRate);
        }

        @Override
        public void write(PacketBuffer buffer, AtomicSmelterRecipe recipe) {
            for (Ingredient input : recipe.inputs)
                input.write(buffer);

            buffer.writeItemStack(recipe.output);
            buffer.writeVarInt(recipe.powerCost);
            buffer.writeVarInt(recipe.powerRate);
        }
    }

    public class SmelterRecipeResult extends RecipeResult {
        SmelterRecipeResult(ResourceLocation id) {
            super(id);
        }

        @Override
        public void serialize(JsonObject json) {
            JsonArray inputsArray = new JsonArray();

            for (Ingredient input : inputs)
                inputsArray.add(input.serialize());

            json.add("inputs", inputsArray);
            json.add("output", ItemHelper.serialize(output));
            json.addProperty("powerCost", powerCost);
            json.addProperty("powerRate", powerRate);
        }
    }
}
