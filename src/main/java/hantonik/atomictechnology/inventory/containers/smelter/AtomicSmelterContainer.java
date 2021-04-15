package hantonik.atomictechnology.inventory.containers.smelter;

import hantonik.atomiccore.inventory.slot.BasicItemStackHandlerSlot;
import hantonik.atomiccore.inventory.slot.OutputSlot;
import hantonik.atomiccore.utils.handlers.BasicItemStackHandler;
import hantonik.atomictechnology.init.AtomicContainers;
import hantonik.atomictechnology.tiles.smelter.BasicSmelterTile;
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

import java.util.function.Function;

public class AtomicSmelterContainer extends Container {
    private final Function<PlayerEntity, Boolean> isUsableByPlayer;
    private final IIntArray data;
    private final BlockPos pos;

    private AtomicSmelterContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        this(type, id, playerInventory, p -> false, (new BasicSmelterTile()).getInventory(), new IntArray(5), buffer.readBlockPos());
    }

    private AtomicSmelterContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, Function<PlayerEntity, Boolean> isUsableByPlayer, BasicItemStackHandler inventory, IIntArray data, BlockPos pos) {
        super(type, id);
        this.isUsableByPlayer = isUsableByPlayer;
        this.data = data;
        this.pos = pos;

        this.addSlot(new OutputSlot(inventory, 0, 140, 48));
        this.addSlot(new BasicItemStackHandlerSlot(inventory, 1, 33, 18));
        this.addSlot(new BasicItemStackHandlerSlot(inventory, 2, 33, 48));
        this.addSlot(new BasicItemStackHandlerSlot(inventory, 3, 33, 78));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 112 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 170));
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

            if (slotNumber == 0) {
                if (!this.mergeItemStack(itemStack1, 4, 30, true))
                    return ItemStack.EMPTY;

                slot.onSlotChange(itemStack1, itemStack);
            } else if (slotNumber >= 4 && slotNumber <= 39) {
                if (!this.mergeItemStack(itemStack1, 1, 4, false))
                    return ItemStack.EMPTY;

            } else if (slotNumber >= 1 && slotNumber <= 3) {
                if (!this.mergeItemStack(itemStack1, 4, 31, true))
                    return ItemStack.EMPTY;

            } else if (!this.mergeItemStack(itemStack1, 4, 39, false))
                return ItemStack.EMPTY;

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
        return this.isUsableByPlayer.apply(playerIn);
    }

    public static AtomicSmelterContainer create(int windowId, PlayerInventory playerInventory, PacketBuffer buffer) {
        return new AtomicSmelterContainer(AtomicContainers.ADVANCED_SMELTER.get(), windowId, playerInventory, buffer);
    }

    public static AtomicSmelterContainer create(int windowId, PlayerInventory playerInventory, Function<PlayerEntity, Boolean> isUsableByPlayer, BasicItemStackHandler inventory, IIntArray data, BlockPos pos) {
        return new AtomicSmelterContainer(AtomicContainers.ADVANCED_SMELTER.get(), windowId, playerInventory, isUsableByPlayer, inventory, data, pos);
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public int getEnergyBarScaled(int pixels) {
        int i = this.getEnergyStored();
        int j = this.getMaxEnergyStored();
        return (int) (j != 0 && i != 0 ? (long) i * pixels / j : 0);
    }

    public int getProgressBarScaled(int pixels) {
        int i = this.getProgress();
        int j = this.getEnergyRequired();
        return (int) (j != 0 && i != 0 ? (long) i * pixels / j : 0);
    }

    public boolean hasRecipe() {
        return this.data.get(4) > 0;
    }

    public int getProgress() {
        return this.data.get(0);
    }

    public int getEnergyStored() {
        return this.data.get(1);
    }

    public int getMaxEnergyStored() {
        return this.data.get(2);
    }

    public int getEnergyRequired() {
        return this.data.get(3);
    }
}