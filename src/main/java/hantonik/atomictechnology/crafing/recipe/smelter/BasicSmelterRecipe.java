package hantonik.atomictechnology.crafing.recipe.smelter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hantonik.atomiccore.utils.helpers.ItemHelper;
import hantonik.atomiccore.utils.helpers.StackHelper;
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

public class BasicSmelterRecipe extends RecipeBuilder<BasicSmelterRecipe> implements ISmelterRecipe {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputs;
    private final int powerCost;
    private final int powerRate;

    public BasicSmelterRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, int powerCost) {
        this(id, output, inputs, powerCost, Configs.BASIC_SMELTER_RF_RATE.get());
    }

    public BasicSmelterRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, int powerCost, int powerRate) {
        super(new ResourceLocation(AtomicTechnology.MOD_ID, "basic_smelter"));

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
        NonNullList<ItemStack> stacks = NonNullList.create();

        for (int i = 0; i < inventory.getSlots(); i++) {
            if (StackHelper.areStacksEqual(inventory.getStackInSlot(i), ItemStack.EMPTY))
                continue;

            stacks.add(i, inventory.getStackInSlot(i));
        }

        if (this.inputs.size() == 3 && stacks.size() == 3) {
            if (this.inputs.get(0).test(stacks.get(0)) && this.inputs.get(1).test(stacks.get(1)) && this.inputs.get(2).test(stacks.get(2)))
                return true;
            else if (this.inputs.get(1).test(stacks.get(0)) && this.inputs.get(2).test(stacks.get(1)) && this.inputs.get(0).test(stacks.get(2)))
                return true;
            else
                return this.inputs.get(2).test(stacks.get(0)) && this.inputs.get(0).test(stacks.get(1)) && this.inputs.get(1).test(stacks.get(2));
        } else if (this.inputs.size() == 2 && stacks.size() == 2) {
            if (this.inputs.get(0).test(stacks.get(0)) && this.inputs.get(1).test(stacks.get(1)))
                return true;
            else if (this.inputs.get(0).test(stacks.get(0)) && this.inputs.get(1).test(stacks.get(2)))
                return true;
            else if (this.inputs.get(1).test(stacks.get(0)) && this.inputs.get(0).test(stacks.get(1)))
                return true;
            else if (this.inputs.get(1).test(stacks.get(0)) && this.inputs.get(0).test(stacks.get(2)))
                return true;
            else if (this.inputs.get(0).test(stacks.get(1)) && this.inputs.get(1).test(stacks.get(2)))
                return true;
            else if (this.inputs.get(0).test(stacks.get(1)) && this.inputs.get(1).test(stacks.get(0)))
                return true;
            else
                return this.inputs.get(1).test(stacks.get(1)) && this.inputs.get(0).test(stacks.get(2));
        } else if (this.inputs.size() == 1 && stacks.size() == 1) {
            if (this.inputs.get(0).test(stacks.get(0)))
                return true;
            else if (this.inputs.get(0).test(stacks.get(1)))
                return true;
            else
                return this.inputs.get(0).test(stacks.get(2));
        }

        return false;
    }

    @Override
    public ResourceLocation getRecipeLocation() {
        return new ResourceLocation(AtomicTechnology.MOD_ID, "basic_smelter");
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return AtomicRecipeSerializers.BASIC_SMELTER;
    }

    @Override
    public IRecipeType<?> getType() {
        return AtomicRecipeTypes.BASIC_SMELTER;
    }

    @Override
    protected RecipeResult getResult(ResourceLocation id) {
        return new SmelterRecipeResult(id);
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<BasicSmelterRecipe> {
        @Override
        public BasicSmelterRecipe read(ResourceLocation recipeId, JsonObject json) {
            JsonArray inputsArray = JSONUtils.getJsonArray(json, "inputs");
            NonNullList<Ingredient> inputs = NonNullList.create();

            for (int i = 0; i < inputsArray.size(); i++)
                inputs.add(Ingredient.deserialize(inputsArray.get(i)));

            ItemStack output = ItemHelper.deserializeStack(JSONUtils.getJsonObject(json, "output"));
            int powerCost = JSONUtils.getInt(json, "powerCost");
            int powerRate = JSONUtils.getInt(json, "powerRate");

            return new BasicSmelterRecipe(recipeId, output, inputs, powerCost, powerRate);
        }

        @Override
        public BasicSmelterRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            NonNullList<Ingredient> inputs = NonNullList.create();

            inputs.add(Ingredient.read(buffer));
            inputs.add(Ingredient.read(buffer));
            inputs.add(Ingredient.read(buffer));

            ItemStack output = buffer.readItemStack();
            int powerCost = buffer.readVarInt();
            int powerRate = buffer.readVarInt();

            return new BasicSmelterRecipe(recipeId, output, inputs, powerCost, powerRate);
        }

        @Override
        public void write(PacketBuffer buffer, BasicSmelterRecipe recipe) {
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
