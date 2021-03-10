package hantonik.atomictechnology.recipes.smelter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public final class AlloyUtils {
    public static Alloy loadFromJson(ResourceLocation id, JsonObject json) {
        String name = JSONUtils.getString(json, "name");
        JsonArray inputs = JSONUtils.getJsonArray(json, "inputs");
        Ingredient output = Ingredient.deserialize(json.get("output"));
        int outputCount = JSONUtils.getInt(json, "outputCount");
        int powerRequired = JSONUtils.getInt(json, "powerRequired");

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

        return new Alloy(id, name, new Ingredient[] { slot1, slot2, slot3 }, output, outputCount, powerRequired);
    }

    public static JsonObject writeToJson(Alloy alloy) {
        JsonObject json = new JsonObject();

        JsonArray inputs = new JsonArray();
        inputs.add(alloy.getInputs()[0].serialize());
        inputs.add(alloy.getInputs()[1].serialize());
        inputs.add(alloy.getInputs()[2].serialize());


        json.add("output", alloy.getOutput().serialize());
        json.addProperty("outputCount", alloy.getOutputCount());
        json.addProperty("powerRequired", alloy.getPowerRequired());
        json.addProperty("name", alloy.getName());
        json.add("inputs", inputs);

        return json;
    }
}
