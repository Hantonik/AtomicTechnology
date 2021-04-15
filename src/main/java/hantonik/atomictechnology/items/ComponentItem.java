package hantonik.atomictechnology.items;

import hantonik.atomiccore.items.BasicItem;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.lib.AtomicTooltips;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ComponentItem extends BasicItem {
    private final String[] information;

    public ComponentItem(String...information) {
        super(p -> p.group(AtomicTechnology.GROUP));

        this.information = information;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(AtomicTooltips.CRAFTING_COMPONENT.build());

        for (String information : this.information)
            tooltip.add(new StringTextComponent(information));
    }
}
