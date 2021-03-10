package hantonik.atomictechnology.groups;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.init.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class MainGroup extends ItemGroup {
    public MainGroup() {
        super(AtomicTechnology.MOD_ID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Blocks.ATOMIC_SMELTER.get());
    }
}
