package hantonik.atomictechnology.tiles.basics;

import hantonik.atomiccore.energy.BasicEnergyStorage;
import hantonik.atomiccore.tiles.BasicInventoryTileEntity;
import hantonik.atomiccore.utils.handlers.BasicItemStackHandler;
import hantonik.atomiccore.utils.helpers.StackHelper;
import hantonik.atomictechnology.api.crafting.ISmelterRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;

public abstract class AbstractSmelterTile<R extends ISmelterRecipe> extends BasicInventoryTileEntity implements ITickableTileEntity, INamedContainerProvider, ISidedInventory {
    protected final BasicItemStackHandler inventory = new BasicItemStackHandler(4);
    protected final BasicEnergyStorage energy;
    protected final BasicItemStackHandler recipeInventory = new BasicItemStackHandler(3);
    protected final LazyOptional<IEnergyStorage> capability = LazyOptional.of(this::getEnergy);
    protected boolean mark = false;
    protected int progress = 0;
    protected int oldEnergy;
    protected R recipe;

    LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    protected IIntArray data = new IIntArray() {
        @Override
        public int get(int i) {
            switch (i) {
                case 0: return AbstractSmelterTile.this.getProgress();
                case 1: return AbstractSmelterTile.this.getEnergy().getEnergyStored();
                case 2: return AbstractSmelterTile.this.getEnergy().getMaxEnergyStored();
                case 3: return AbstractSmelterTile.this.getEnergyRequired();
                case 4: return AbstractSmelterTile.this.hasRecipe() ? 1 : 0;

                default: return 0;
            }
        }

        @Override
        public void set(int i, int value) { }

        @Override
        public int size() {
            return 5;
        }
    };

    public AbstractSmelterTile(TileEntityType<?> type, int maxEnergy) {
        super(type);

        this.inventory.setSlotValidator(this::canInsertStack);
        this.inventory.setOutputSlots(0);
        this.energy = new BasicEnergyStorage(maxEnergy).setEnergy((maxEnergy / 4) * 3);
    }

    @Override
    public BasicItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);

        this.progress = tag.getInt("Progress");
        this.energy.setEnergy(tag.getInt("Energy"));
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag = super.write(tag);

        tag.putDouble("Progress", this.progress);
        tag.putInt("Energy", this.energy.getEnergyStored());

        return tag;
    }

    @Override
    public <E> LazyOptional<E> getCapability(Capability<E> cap, Direction side) {
        if (!this.isRemoved() && side != null) {
            if (cap == CapabilityEnergy.ENERGY && side.equals(Direction.EAST))
                return CapabilityEnergy.ENERGY.orEmpty(cap, this.capability);

            if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && !side.equals(Direction.EAST)) {
                switch (side) {
                    case UP:
                        return handlers[0].cast();
                    case DOWN:
                        return handlers[1].cast();
                    default:
                        return handlers[2].cast();
                }
            }
        }

        return super.getCapability(cap, side);
    }

    public BasicEnergyStorage getEnergy() {
        return this.energy;
    }

    public int getProgress() {
        return this.progress;
    }

    public boolean hasRecipe() {
        return this.recipe != null;
    }

    public R getActiveRecipe() {
        return this.recipe;
    }

    public int getEnergyRequired() {
        if (this.hasRecipe())
            return this.recipe.getPowerCost();

        return 0;
    }

    protected void process(R recipe) {
        int powerCost = this.getEnergyRequired();
        int powerRate = recipe.getPowerRate();

        powerRate = Math.min(powerRate, powerCost);

        if (this.getEnergy().getEnergyStored() >= powerRate) {
            if ((powerCost - this.progress) < powerRate)
                this.progress = powerCost;
            else {
                this.energy.decreaseEnergy(powerRate);
                this.progress = Math.min(this.progress + powerRate, powerCost);
            }
        }
    }

    protected void updateRecipeInventory() {
        for (int i = 0; i < 3; i++) {
            ItemStack stack = this.inventory.toIInventory().getStackInSlot(i + 1);

            if (!stack.isEmpty() && !stack.equals(ItemStack.EMPTY) && !stack.getItem().equals(Items.AIR) && stack.getCount() > 0)
                this.recipeInventory.setStackInSlot(i, stack);
        }
    }

    protected void updateResult(ItemStack stack) {
        ItemStack result = this.inventory.getStackInSlot(0);

        if (result.isEmpty())
            this.inventory.setStackInSlot(0, stack);
        else
            this.inventory.setStackInSlot(0, StackHelper.grow(result, stack.getCount()));
    }

    protected boolean canInsertStack(int slot, ItemStack stack) {
        return slot > 0 && slot < 4;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        switch (side) {
            case DOWN:
                return new int[] { 0 };

            case UP:
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
            default:
                return new int[] { 1, 2, 3 };
        }
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return direction.equals(Direction.DOWN) && index == 0;
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.getStacks().size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.inventory.getStacks()) {
            if (!itemstack.isEmpty())
                return false;
        }

        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.inventory.getStacks(), index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.inventory.getStacks(), index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.inventory.setStackInSlot(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
            stack.setCount(this.getInventoryStackLimit());
    }

    @Override
    public void clear() {
        this.inventory.getStacks().clear();
    }
}
