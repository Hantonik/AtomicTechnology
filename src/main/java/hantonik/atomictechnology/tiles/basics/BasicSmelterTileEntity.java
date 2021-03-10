package hantonik.atomictechnology.tiles.basics;

import hantonik.atomictechnology.api.crafting.ISmelterRecipe;
import hantonik.atomictechnology.energy.BasicEnergyStorage;
import hantonik.atomictechnology.utils.handlers.BasicItemStackHandler;
import hantonik.atomictechnology.utils.helpers.StackHelper;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class BasicSmelterTileEntity<R extends ISmelterRecipe> extends BasicInventoryTileEntity implements ITickableTileEntity, INamedContainerProvider {
    protected final BasicItemStackHandler inventory = new BasicItemStackHandler(4);
    protected final BasicEnergyStorage energy;
    protected final BasicItemStackHandler recipeInventory = new BasicItemStackHandler(3);
    protected final LazyOptional<IEnergyStorage> capability = LazyOptional.of(this::getEnergy);
    protected int progress;
    protected int oldEnergy;
    protected R recipe;

    protected IIntArray data = new IIntArray() {
        @Override
        public int get(int i) {
            switch (i) {
                case 0: return BasicSmelterTileEntity.this.getProgress();
                case 1: return BasicSmelterTileEntity.this.getEnergy().getEnergyStored();
                case 2: return BasicSmelterTileEntity.this.getEnergy().getMaxEnergyStored();
                case 3: return BasicSmelterTileEntity.this.getEnergyRequired();
                case 4: return BasicSmelterTileEntity.this.hasRecipe() ? 1 : 0;
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

    public BasicSmelterTileEntity(TileEntityType<?> type, int maxEnergy) {
        super(type);

        this.inventory.setSlotValidator(this::canInsertStack);
        this.inventory.setOutputSlots(0);
        this.energy = new BasicEnergyStorage(maxEnergy);
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
        tag.putInt("Progress", this.progress);
        tag.putInt("Energy", this.energy.getEnergyStored());

        return tag;
    }

    @Override
    public <E> LazyOptional<E> getCapability(Capability<E> cap, Direction side) {
        if (!this.isRemoved() && cap == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.orEmpty(cap, this.capability);
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
        int extract = recipe.getPowerRate();
        int difference = recipe.getPowerCost() - this.progress;
        if (difference < extract)
            extract = difference;

        int extracted = this.energy.extractEnergy(extract, false);
        this.progress += extracted;
    }

    protected void updateResult(ItemStack stack) {
        ItemStack result = this.inventory.getStackInSlot(0);
        if (result.isEmpty()) {
            this.inventory.setStackInSlot(0, stack);
        } else {
            this.inventory.setStackInSlot(0, StackHelper.grow(result, stack.getCount()));
        }
    }

    protected boolean canInsertStack(int slot, ItemStack stack) {
        return slot > 0 && slot < 4;
    }
}
