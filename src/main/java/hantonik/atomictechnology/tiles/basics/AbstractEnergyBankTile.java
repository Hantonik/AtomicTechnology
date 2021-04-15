package hantonik.atomictechnology.tiles.basics;

import hantonik.atomiccore.energy.BasicEnergyStorage;
import hantonik.atomiccore.tiles.BasicTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class AbstractEnergyBankTile extends BasicTileEntity implements INamedContainerProvider {
    protected final BasicEnergyStorage energy;
    protected final LazyOptional<IEnergyStorage> capability = LazyOptional.of(this::getEnergy);

    protected IIntArray data = new IIntArray() {
        @Override
        public int get(int i) {
            switch (i) {
                case 0: return AbstractEnergyBankTile.this.getEnergy().getEnergyStored();
                case 1: return AbstractEnergyBankTile.this.getEnergy().getMaxEnergyStored();

                default: return 0;
            }
        }

        @Deprecated
        @Override
        public void set(int i, int value) { }

        @Override
        public int size() {
            return 3;
        }
    };

    public AbstractEnergyBankTile(TileEntityType<?> type, int capacity) {
        super(type);

        this.energy = new BasicEnergyStorage(capacity);
    }


    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);

        this.energy.setEnergy(tag.getInt("Energy"));
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag = super.write(tag);

        tag.putInt("Energy", this.energy.getEnergyStored());

        return tag;
    }

    public BasicEnergyStorage getEnergy() {
        return this.energy;
    }
}
