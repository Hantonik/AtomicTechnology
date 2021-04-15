package hantonik.atomictechnology.items;

import hantonik.atomiccore.items.BasicItem;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.lib.AtomicTooltips;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class GoldiumAlloyIngotItem extends BasicItem {
    public GoldiumAlloyIngotItem() {
        super(p -> p.group(AtomicTechnology.GROUP));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(AtomicTooltips.GOLDIUM_ALLOY.build());
    }
}
