package hantonik.atomictechnology.inventory.containers.bank;

import hantonik.atomictechnology.init.AtomicContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;

public class BasicEnergyBankContainer extends Container {
    private final IIntArray data;
    private final BlockPos pos;

    private BasicEnergyBankContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        this(type, id, playerInventory, new IntArray(3), buffer.readBlockPos());
    }

    private BasicEnergyBankContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, IIntArray data, BlockPos pos) {
        super(type, id);
        this.data = data;
        this.pos = pos;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 28 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 86));
        }

        this.trackIntArray(data);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slotNumber) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(slotNumber);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();

            if (slotNumber >= 27 && slotNumber <= 35) {
                if (!this.mergeItemStack(itemStack1, 0, 26, false))
                    return ItemStack.EMPTY;

            } else if (slotNumber >= 0 && slotNumber <= 26) {
                if (!this.mergeItemStack(itemStack1, 27, 35, false))
                    return ItemStack.EMPTY;
            }
            if (itemStack1.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();

            if (itemStack1.getCount() == itemStack.getCount())
                return ItemStack.EMPTY;

            slot.onTake(player, itemStack1);
        }

        return itemStack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public static BasicEnergyBankContainer create(int windowId, PlayerInventory playerInventory, PacketBuffer buffer) {
        return new BasicEnergyBankContainer(AtomicContainers.BASIC_ENERGY_BANK.get(), windowId, playerInventory, buffer);
    }

    public static BasicEnergyBankContainer create(int windowId, PlayerInventory playerInventory, IIntArray data, BlockPos pos) {
        return new BasicEnergyBankContainer(AtomicContainers.BASIC_ENERGY_BANK.get(), windowId, playerInventory, data, pos);
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public int getEnergyBarScaled(int pixels) {
        int i = this.getEnergyStored();
        int j = this.getMaxEnergyStored();
        return (int) (j != 0 && i != 0 ? (long) i * pixels / j : 0);
    }

    public int getEnergyStored() {
        return this.data.get(0);
    }

    public int getMaxEnergyStored() {
        return this.data.get(1);
    }
}
