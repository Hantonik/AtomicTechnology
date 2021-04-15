package hantonik.atomictechnology.registry.meltable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class MeltableItem {
    private final ResourceLocation id;
    private final Item item;
    private final int meltingTemperature;

    public MeltableItem(Block block, int meltingTemperature) {
        this(block.getRegistryName(), block, meltingTemperature);
    }

    MeltableItem(ResourceLocation id, Block block, int meltingTemperature) {
        this(id, block.asItem(), meltingTemperature);
    }

    public MeltableItem(Item item, int meltingTemperature) {
        this(item.getRegistryName(), item, meltingTemperature);
    }

    MeltableItem(ResourceLocation id, Item item, int meltingTemperature) {
        this.id = id;

        this.item = item;
        this.meltingTemperature = meltingTemperature;
    }

    public Item getItem() {
        return this.item;
    }

    public int getMeltingTemperature() {
        return this.meltingTemperature;
    }

    public ResourceLocation getId() {
        return this.id;
    }
}
