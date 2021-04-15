package hantonik.atomictechnology.api.item;

import net.minecraft.item.ItemStack;

public interface IElectricItemBase {
    float getEnergyStored(ItemStack itemStack);

    float getMaxEnergyStored(ItemStack itemStack);

    void setEnergy(ItemStack itemStack, float energy);
}

