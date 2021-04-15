package hantonik.atomictechnology.registry.meltable;

import com.google.gson.JsonObject;
import hantonik.atomiccore.utils.helpers.ItemHelper;
import net.minecraft.item.Item;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class MeltableItemUtils {
    public static MeltableItem loadFromJson(ResourceLocation id, JsonObject json) {
        Item item = ItemHelper.deserializeItem(JSONUtils.getJsonObject(json, "item"));
        int meltingTemperature = JSONUtils.getInt(json, "meltingTemperature");

        return new MeltableItem(id, item, meltingTemperature);
    }

    public static JsonObject writeToJson(MeltableItem meltableItem) {
        JsonObject json = new JsonObject();

        json.add("item", ItemHelper.serialize(meltableItem.getItem()));
        json.addProperty("meltingTemperature", meltableItem.getMeltingTemperature());

        return json;
    }
}
