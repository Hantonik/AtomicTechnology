package hantonik.atomictechnology.groups;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.init.AtomicBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class AtomicGroup extends ItemGroup {
    public AtomicGroup() {
        super(AtomicTechnology.MOD_ID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(AtomicBlocks.ATOMIC_SMELTER.get());
    }
}
