package hantonik.atomictechnology.items.basic;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

import java.util.function.Function;

public class BasicBlockItem extends BlockItem {
    public BasicBlockItem(Block block, Function<Properties, Properties> properties) {
        super(block, properties.apply(new Properties()));
    }
}
