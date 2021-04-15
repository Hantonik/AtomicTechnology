package hantonik.atomictechnology.datagen;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.init.AtomicBlocks;
import hantonik.atomictechnology.init.AtomicTags;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class AtomicBlockTagsProvider extends BlockTagsProvider {
    public AtomicBlockTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AtomicTechnology.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        createAndAppend(Tags.Blocks.STORAGE_BLOCKS, AtomicTags.Blocks.STORAGE_BLOCKS_STEEL, AtomicBlocks.STEEL_BLOCK);
        createAndAppend(Tags.Blocks.STORAGE_BLOCKS, AtomicTags.Blocks.STORAGE_BLOCKS_ALUMINIUM, AtomicBlocks.ALUMINIUM_BLOCK);
        createAndAppend(Tags.Blocks.STORAGE_BLOCKS, AtomicTags.Blocks.STORAGE_BLOCKS_COPPER, AtomicBlocks.COPPER_BLOCK);
        createAndAppend(Tags.Blocks.STORAGE_BLOCKS, AtomicTags.Blocks.STORAGE_BLOCKS_LEAD, AtomicBlocks.LEAD_BLOCK);
        createAndAppend(Tags.Blocks.STORAGE_BLOCKS, AtomicTags.Blocks.STORAGE_BLOCKS_SILVER, AtomicBlocks.SILVER_BLOCK);
        createAndAppend(Tags.Blocks.STORAGE_BLOCKS, AtomicTags.Blocks.STORAGE_BLOCKS_TITANIUM, AtomicBlocks.TITANIUM_BLOCK);
    }

    @SafeVarargs
    private final <T> T[] resolveAll(IntFunction<T[]> creator, Supplier<? extends T>... suppliers) {
        return Arrays.stream(suppliers).map(Supplier::get).toArray(creator);
    }

    @SafeVarargs
    private final void createTag(ITag.INamedTag<Block> tag, Supplier<? extends Block>... blocks) {
        getOrCreateBuilder(tag).add(resolveAll(Block[]::new, blocks));
    }

    @SafeVarargs
    private final void appendToTag(ITag.INamedTag<Block> tag, ITag.INamedTag<Block>... toAppend) {
        getOrCreateBuilder(tag).addTags(toAppend);
    }

    @SafeVarargs
    private final void createAndAppend(ITag.INamedTag<Block> to, ITag.INamedTag<Block> tag, Supplier<? extends Block>... blocks) {
        createTag(tag, blocks);
        appendToTag(to, tag);
    }

    @Override
    public String getName() {
        return "AtomicTechnology Block Tags";
    }
}
