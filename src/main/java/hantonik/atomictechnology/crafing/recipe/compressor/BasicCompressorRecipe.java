package hantonik.atomictechnology.crafing.recipe.compressor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import hantonik.atomiccore.utils.helpers.ItemHelper;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.api.AtomicRecipeTypes;
import hantonik.atomictechnology.api.crafting.ICompressorRecipe;
import hantonik.atomictechnology.configs.Configs;
import hantonik.atomictechnology.crafing.recipe.RecipeBuilder;
import hantonik.atomictechnology.init.AtomicRecipeSerializers;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class BasicCompressorRecipe extends RecipeBuilder<BasicCompressorRecipe> implements ICompressorRecipe {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputs;
    private final Ingredient pattern;
    private final int powerCost;
    private final int powerRate;

    public BasicCompressorRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, Ingredient pattern, int powerCost) {
        this(id, output, inputs, pattern, powerCost, Configs.BASIC_COMPRESSOR_RF_RATE.get());
    }

    public BasicCompressorRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputs, Ingredient pattern, int powerCost, int powerRate) {
        super(new ResourceLocation(AtomicTechnology.MOD_ID, "basic_compressor"));

        this.id = id;
        this.output = output;
        this.inputs = inputs;
        this.pattern = pattern;
        this.powerCost = powerCost;
        this.powerRate = powerRate;
    }

    @Override
    public String getSerializerName() {
        return this.serializerName.getPath();
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        ItemStack[] inputs = { inv.getStackInSlot(2), inv.getStackInSlot(3) };
        ItemStack pattern = inv.getStackInSlot(1);

        if (this.pattern.test(pattern)) {
            for (int i = 0; i < this.inputs.size(); i++) {
                if (!this.inputs.get(i).test(inputs[i]))
                    return false;
            }
        }

        return false;
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
    public Ingredient getPattern() {
        return this.pattern;
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
    public ResourceLocation getRecipeLocation() {
        return this.serializerName;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return AtomicRecipeSerializers.BASIC_COMPRESSOR;
    }

    @Override
    public IRecipeType<?> getType() {
        return AtomicRecipeTypes.BASIC_COMPRESSOR;
    }

    @Override
    protected RecipeResult getResult(ResourceLocation id) {
        return new CompressorRecipeResult(id);
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<BasicCompressorRecipe> {
        @Override
        public BasicCompressorRecipe read(ResourceLocation recipeId, JsonObject json) {
            JsonArray inputsArray = JSONUtils.getJsonArray(json, "inputs");
            NonNullList<Ingredient> inputs = NonNullList.create();

            for (int i = 0; i < inputsArray.size(); i++)
                inputs.add(Ingredient.deserialize(inputsArray.get(i)));

            Ingredient pattern = Ingredient.deserialize(JSONUtils.getJsonObject(json, "pattern"));
            ItemStack output = ItemHelper.deserializeStack(JSONUtils.getJsonObject(json, "output"));
            int powerCost = JSONUtils.getInt(json, "powerCost");
            int powerRate = JSONUtils.getInt(json, "powerRate");

            return new BasicCompressorRecipe(recipeId, output, inputs, pattern, powerCost, powerRate);
        }

        @Override
        public BasicCompressorRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            NonNullList<Ingredient> inputs = NonNullList.create();

            inputs.add(Ingredient.read(buffer));
            inputs.add(Ingredient.read(buffer));
            inputs.add(Ingredient.read(buffer));

            Ingredient pattern = Ingredient.read(buffer);
            ItemStack output = buffer.readItemStack();
            int powerCost = buffer.readVarInt();
            int powerRate = buffer.readVarInt();

            return new BasicCompressorRecipe(recipeId, output, inputs, pattern, powerCost, powerRate);
        }

        @Override
        public void write(PacketBuffer buffer, BasicCompressorRecipe recipe) {
            for (Ingredient input : recipe.inputs)
                input.write(buffer);

            recipe.pattern.write(buffer);

            buffer.writeItemStack(recipe.output);
            buffer.writeVarInt(recipe.powerCost);
            buffer.writeVarInt(recipe.powerRate);
        }
    }

    public class CompressorRecipeResult extends RecipeResult {
        CompressorRecipeResult(ResourceLocation id) {
            super(id);
        }

        @Override
        public void serialize(JsonObject json) {
            JsonArray inputsArray = new JsonArray();

            for (Ingredient input : inputs)
                inputsArray.add(input.serialize());

            json.add("inputs", inputsArray);
            json.add("pattern", pattern.serialize());
            json.add("output", ItemHelper.serialize(output));
            json.addProperty("powerCost", powerCost);
            json.addProperty("powerRate", powerRate);
        }
    }
}
