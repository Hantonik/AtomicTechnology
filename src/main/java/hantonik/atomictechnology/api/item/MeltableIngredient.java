package hantonik.atomictechnology.api.item;

import hantonik.atomictechnology.init.AtomicTags;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.Tags;

import java.util.HashMap;
import java.util.Map;

public class MeltableIngredient {
    private static final Map<Ingredient, Integer> MELTABLE_INGREDIENTS = new HashMap<>();

    static {
        //DEFAULTS
        register(Items.COAL, 1);
        register(Items.COAL_BLOCK, 1);
        register(Tags.Items.INGOTS_IRON, 800);
        register(Tags.Items.STORAGE_BLOCKS_IRON, 800, true);

        register(AtomicTags.Items.INGOTS_STEEL, 1_210);
        register(AtomicTags.Items.INGOTS_ALUMINIUM, 425);
        register(AtomicTags.Items.INGOTS_COPPER, 500);
        register(AtomicTags.Items.INGOTS_LEAD, 330);
        register(AtomicTags.Items.INGOTS_SILVER, 790);
        register(AtomicTags.Items.INGOTS_TITANIUM, 1_668);

        register(AtomicTags.Items.STORAGE_BLOCKS_STEEL, 1_210, true);
        register(AtomicTags.Items.STORAGE_BLOCKS_ALUMINIUM, 425, true);
        register(AtomicTags.Items.STORAGE_BLOCKS_COPPER, 500, true);
        register(AtomicTags.Items.STORAGE_BLOCKS_LEAD, 330, true);
        register(AtomicTags.Items.STORAGE_BLOCKS_SILVER, 790, true);
        register(AtomicTags.Items.STORAGE_BLOCKS_TITANIUM, 1_668, true);
    }

    public static void register(Ingredient ingredient, int temperature) {
        MELTABLE_INGREDIENTS.putIfAbsent(ingredient, temperature);
    }

    public static void register(ITag<Item> tag, int temperature) {
        tag.getAllElements().forEach(item -> register(Ingredient.fromItems(item), temperature));
    }

    public static void register(Item item, int temperature) {
        register(Ingredient.fromItems(item), temperature);
    }

    public static void register(Ingredient ingredient, int temperature, boolean isBlock) {
        if (isBlock)
            register(ingredient, temperature * 9);
        else
            register(ingredient, temperature);
    }

    public static void register(ITag<Item> tag, int temperature, boolean isBlock) {
        tag.getAllElements().forEach(item -> register(Ingredient.fromItems(item), temperature, isBlock));
    }

    public static void register(Item item, int temperature, boolean isBlock) {
        if (isBlock)
            register(item, temperature * 9);
        else
            register(item, temperature);
    }

    public static boolean isMeltable(Ingredient ingredient) {
        return MELTABLE_INGREDIENTS.containsKey(ingredient);
    }

    public static boolean isMeltable(ITag<Item> tag) {
        return tag.getAllElements().stream().anyMatch(item -> isMeltable(Ingredient.fromItems(item)));
    }

    public static int getTemperature(Ingredient ingredient) {
        return MELTABLE_INGREDIENTS.getOrDefault(ingredient, -1);
    }

    public static int getTemperature(ITag<Item> tag) {
        for (Item item : tag.getAllElements()) {
            if (getTemperature(Ingredient.fromItems(item)) != -1)
                return getTemperature(Ingredient.fromItems(item));
        }

        return -1;
    }

    public static Map<Ingredient, Integer> getAll() {
        return MELTABLE_INGREDIENTS;
    }
}
