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

public class PatternItem extends BasicItem {
    public PatternItem() {
        super(p -> p.group(AtomicTechnology.GROUP));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(AtomicTooltips.PATTERN.build());
    }

 /*   @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        if (this instanceof PatternItem) {
            if (stack.getTag() == null)
                stack.setTag(new CompoundNBT());

            stack.getTag().putBoolean("isPattern", true);
        }

        return super.initCapabilities(stack, nbt);
    }*/
}
