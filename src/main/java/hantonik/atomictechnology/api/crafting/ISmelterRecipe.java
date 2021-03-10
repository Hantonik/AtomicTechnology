package hantonik.atomictechnology.api.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;

public interface ISmelterRecipe extends IRecipe<IInventory> {
    int getPowerCost();
    int getPowerRate();
    int getOutputCount();
    Ingredient[] getInputs();
}
