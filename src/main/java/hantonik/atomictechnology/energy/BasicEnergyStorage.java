package hantonik.atomictechnology.energy;

import net.minecraftforge.energy.EnergyStorage;

public class BasicEnergyStorage extends EnergyStorage {
    public BasicEnergyStorage(int capacity) {
        super(capacity);
    }

    public void setEnergy(int amount) {
        this.energy = amount;
    }
}
