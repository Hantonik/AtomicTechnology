package hantonik.atomictechnology.items.basic;

import net.minecraft.item.Item;

import java.util.function.Function;

public class BasicItem extends Item {
    public BasicItem(Function<Properties, Properties> properties) {
        super(properties.apply(new Properties()));
    }
}
