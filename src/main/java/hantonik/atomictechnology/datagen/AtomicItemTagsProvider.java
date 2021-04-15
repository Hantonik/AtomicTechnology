package hantonik.atomictechnology.datagen;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.init.AtomicBlocks;
import hantonik.atomictechnology.init.AtomicItems;
import hantonik.atomictechnology.init.AtomicTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.function.Supplier;

public class AtomicItemTagsProvider extends ItemTagsProvider {
    public AtomicItemTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, new AtomicBlockTagsProvider(generatorIn, existingFileHelper), AtomicTechnology.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        createBuilderIfAbsent(AtomicTags.Items.PLATES);

        copy(AtomicTags.Blocks.STORAGE_BLOCKS_STEEL, AtomicTags.Items.STORAGE_BLOCKS_STEEL);
        copy(AtomicTags.Blocks.STORAGE_BLOCKS_ALUMINIUM, AtomicTags.Items.STORAGE_BLOCKS_ALUMINIUM);
        copy(AtomicTags.Blocks.STORAGE_BLOCKS_COPPER, AtomicTags.Items.STORAGE_BLOCKS_COPPER);
        copy(AtomicTags.Blocks.STORAGE_BLOCKS_LEAD, AtomicTags.Items.STORAGE_BLOCKS_LEAD);
        copy(AtomicTags.Blocks.STORAGE_BLOCKS_SILVER, AtomicTags.Items.STORAGE_BLOCKS_SILVER);
        copy(AtomicTags.Blocks.STORAGE_BLOCKS_TITANIUM, AtomicTags.Items.STORAGE_BLOCKS_TITANIUM);

        addItemsToTag(AtomicTags.Items.PLATES_STEEL, AtomicItems.STEEL_PLATE);
        addItemsToTag(AtomicTags.Items.PLATES_ALUMINIUM, AtomicItems.ALUMINIUM_PLATE);
        addItemsToTag(AtomicTags.Items.PLATES_COPPER, AtomicItems.COPPER_PLATE);
        addItemsToTag(AtomicTags.Items.PLATES_LEAD, AtomicItems.LEAD_PLATE);
        addItemsToTag(AtomicTags.Items.PLATES_SILVER, AtomicItems.SILVER_PLATE);
        addItemsToTag(AtomicTags.Items.PLATES_TITANIUM, AtomicItems.TITANIUM_PLATE);

        addItemsToTag(AtomicTags.Items.INGOTS_STEEL, AtomicItems.STEEL_INGOT);
        addItemsToTag(AtomicTags.Items.INGOTS_ALUMINIUM, AtomicItems.ALUMINIUM_INGOT);
        addItemsToTag(AtomicTags.Items.INGOTS_COPPER, AtomicItems.COPPER_INGOT);
        addItemsToTag(AtomicTags.Items.INGOTS_LEAD, AtomicItems.LEAD_INGOT);
        addItemsToTag(AtomicTags.Items.INGOTS_SILVER, AtomicItems.SILVER_INGOT);
        addItemsToTag(AtomicTags.Items.INGOTS_TITANIUM, AtomicItems.TITANIUM_INGOT);

        addItemsToTag(AtomicTags.Items.STORAGE_BLOCKS_STEEL, AtomicBlocks.STEEL_BLOCK);
        addItemsToTag(AtomicTags.Items.STORAGE_BLOCKS_ALUMINIUM, AtomicBlocks.ALUMINIUM_BLOCK);
        addItemsToTag(AtomicTags.Items.STORAGE_BLOCKS_COPPER, AtomicBlocks.COPPER_BLOCK);
        addItemsToTag(AtomicTags.Items.STORAGE_BLOCKS_LEAD, AtomicBlocks.LEAD_BLOCK);
        addItemsToTag(AtomicTags.Items.STORAGE_BLOCKS_SILVER, AtomicBlocks.SILVER_BLOCK);
        addItemsToTag(AtomicTags.Items.STORAGE_BLOCKS_TITANIUM, AtomicBlocks.TITANIUM_BLOCK);

        appendToTag(Tags.Items.STORAGE_BLOCKS, AtomicTags.Items.STORAGE_BLOCKS_STEEL);
        appendToTag(Tags.Items.STORAGE_BLOCKS, AtomicTags.Items.STORAGE_BLOCKS_ALUMINIUM);
        appendToTag(Tags.Items.STORAGE_BLOCKS, AtomicTags.Items.STORAGE_BLOCKS_COPPER);
        appendToTag(Tags.Items.STORAGE_BLOCKS, AtomicTags.Items.STORAGE_BLOCKS_LEAD);
        appendToTag(Tags.Items.STORAGE_BLOCKS, AtomicTags.Items.STORAGE_BLOCKS_SILVER);
        appendToTag(Tags.Items.STORAGE_BLOCKS, AtomicTags.Items.STORAGE_BLOCKS_TITANIUM);

        appendToTag(Tags.Items.INGOTS, AtomicTags.Items.INGOTS_STEEL);
        appendToTag(Tags.Items.INGOTS, AtomicTags.Items.INGOTS_ALUMINIUM);
        appendToTag(Tags.Items.INGOTS, AtomicTags.Items.INGOTS_COPPER);
        appendToTag(Tags.Items.INGOTS, AtomicTags.Items.INGOTS_LEAD);
        appendToTag(Tags.Items.INGOTS, AtomicTags.Items.INGOTS_SILVER);
        appendToTag(Tags.Items.INGOTS, AtomicTags.Items.INGOTS_TITANIUM);
    }

    @SafeVarargs
    private final void addItemsToTag(ITag.INamedTag<Item> tag, Supplier<? extends IItemProvider>... items) {
        getOrCreateBuilder(tag).add(Arrays.stream(items).map(Supplier::get).map(IItemProvider::asItem).toArray(Item[]::new));
    }

    @SafeVarargs
    private final void appendToTag(ITag.INamedTag<Item> tag, ITag.INamedTag<Item>... toAppend) {
        getOrCreateBuilder(tag).addTags(toAppend);
    }

    @Override
    public String getName() {
        return "AtomicTechnology Item Tags";
    }
}
