package hantonik.atomictechnology.items.basic;

import net.minecraft.item.ItemStack;

import java.util.function.Function;

public class BasicShinyItem extends BasicItem {
    public BasicShinyItem(Function<Properties, Properties> properties) {
        super(properties);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
