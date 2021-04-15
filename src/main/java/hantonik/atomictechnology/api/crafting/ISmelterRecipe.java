package hantonik.atomictechnology.api.crafting;

import hantonik.atomiccore.crafting.ISpecialRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

public interface ISmelterRecipe extends IRecipe<IInventory>, ISpecialRecipe {
    int getPowerCost();
    int getPowerRate();
    NonNullList<Ingredient> getInputs();
}
