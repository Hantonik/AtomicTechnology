package hantonik.atomictechnology.inventory.slot;

import hantonik.atomiccore.utils.handlers.BasicItemStackHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class PatternSlot  extends SlotItemHandler {
    private final BasicItemStackHandler inventory;
    private final int index;

    public PatternSlot(BasicItemStackHandler inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);

        this.inventory = inventory;
        this.index = index;
    }

    @Override
    public boolean canTakeStack(PlayerEntity player) {
        return !this.inventory.extractItemSuper(this.index, 1, true).isEmpty();
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        return this.inventory.extractItemSuper(this.index, amount, false);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if (stack.getTag() != null) {
            if (stack.getTag().contains("isPattern")) {
                return stack.getTag().getBoolean("isPattern");
            }
        }

        return false;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
