package hantonik.atomictechnology.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class AtomicTags {
    public static class Blocks extends AtomicTags {
        public static final ITag.INamedTag<Block> STORAGE_BLOCKS_TITANIUM = forgeTag("storage_blocks/titanium");
        public static final ITag.INamedTag<Block> STORAGE_BLOCKS_ALUMINIUM = forgeTag("storage_blocks/aluminium");
        public static final ITag.INamedTag<Block> STORAGE_BLOCKS_COPPER = forgeTag("storage_blocks/copper");
        public static final ITag.INamedTag<Block> STORAGE_BLOCKS_SILVER = forgeTag("storage_blocks/silver");
        public static final ITag.INamedTag<Block> STORAGE_BLOCKS_LEAD = forgeTag("storage_blocks/lead");
        public static final ITag.INamedTag<Block> STORAGE_BLOCKS_STEEL = forgeTag("storage_blocks/steel");

        static ITag.INamedTag<Block> tag(String modID, String name) {
            return BlockTags.makeWrapperTag(new ResourceLocation(modID, name).toString());
        }

        static ITag.INamedTag<Block> forgeTag(String name) {
            return tag("forge", name);
        }
    }

    public static class Items extends AtomicTags {
        public static final ITag.INamedTag<Item> PLATES = forgeTag("plates");

        public static final ITag.INamedTag<Item> PLATES_TITANIUM = forgeTag("plates/titanium");
        public static final ITag.INamedTag<Item> PLATES_ALUMINIUM = forgeTag("plates/aluminium");
        public static final ITag.INamedTag<Item> PLATES_COPPER = forgeTag("plates/copper");
        public static final ITag.INamedTag<Item> PLATES_SILVER = forgeTag("plates/silver");
        public static final ITag.INamedTag<Item> PLATES_LEAD = forgeTag("plates/lead");
        public static final ITag.INamedTag<Item> PLATES_STEEL = forgeTag("plates/steel");

        public static final ITag.INamedTag<Item> INGOTS_TITANIUM = forgeTag("ingots/titanium");
        public static final ITag.INamedTag<Item> INGOTS_ALUMINIUM = forgeTag("ingots/aluminium");
        public static final ITag.INamedTag<Item> INGOTS_COPPER = forgeTag("ingots/copper");
        public static final ITag.INamedTag<Item> INGOTS_SILVER = forgeTag("ingots/silver");
        public static final ITag.INamedTag<Item> INGOTS_LEAD = forgeTag("ingots/lead");
        public static final ITag.INamedTag<Item> INGOTS_STEEL = forgeTag("ingots/steel");

        public static final ITag.INamedTag<Item> STORAGE_BLOCKS_TITANIUM = forgeTag("storage_blocks/titanium");
        public static final ITag.INamedTag<Item> STORAGE_BLOCKS_ALUMINIUM = forgeTag("storage_blocks/aluminium");
        public static final ITag.INamedTag<Item> STORAGE_BLOCKS_COPPER = forgeTag("storage_blocks/copper");
        public static final ITag.INamedTag<Item> STORAGE_BLOCKS_SILVER = forgeTag("storage_blocks/silver");
        public static final ITag.INamedTag<Item> STORAGE_BLOCKS_LEAD = forgeTag("storage_blocks/lead");
        public static final ITag.INamedTag<Item> STORAGE_BLOCKS_STEEL = forgeTag("storage_blocks/steel");

        static ITag.INamedTag<Item> tag(String modID, String name) {
            return ItemTags.makeWrapperTag(new ResourceLocation(modID, name).toString());
        }

        static ITag.INamedTag<Item> forgeTag(String name) {
            return tag("forge", name);
        }
    }
}
