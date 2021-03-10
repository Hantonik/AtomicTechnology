package hantonik.atomictechnology.tiles;

import hantonik.atomictechnology.api.crafting.Recipes;
import hantonik.atomictechnology.configs.Configs;
import hantonik.atomictechnology.crafting.recipe.BasicSmelterRecipe;
import hantonik.atomictechnology.init.Tiles;
import hantonik.atomictechnology.inventory.containers.BasicSmelterContainer;
import hantonik.atomictechnology.tiles.basics.BasicSmelterTileEntity;
import hantonik.atomictechnology.utils.Localizable;
import hantonik.atomictechnology.utils.helpers.StackHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BasicSmelterTile extends BasicSmelterTileEntity<BasicSmelterRecipe> {
    public BasicSmelterTile() {
        super(Tiles.BASIC_SMELTER.get(), Configs.BASIC_SMELTER_RF_CAPACITY.get());

        this.inventory.setSlotValidator(this::canInsertStack);
        this.inventory.setOutputSlots(0);
    }

    @Override
    public void tick() {
        World world = this.getWorld();

        if (world != null) {
            ItemStack output = this.inventory.getStackInSlot(0);
            ItemStack[] inputs = { this.inventory.getStackInSlot(1), this.inventory.getStackInSlot(2), this.inventory.getStackInSlot(3) };

            this.recipeInventory.setStackInSlot(0, this.inventory.getStackInSlot(1));
            this.recipeInventory.setStackInSlot(1, this.inventory.getStackInSlot(2));
            this.recipeInventory.setStackInSlot(2, this.inventory.getStackInSlot(3));

            if (this.recipe == null || !this.recipe.matches(this.recipeInventory))
                this.recipe = (BasicSmelterRecipe) world.getRecipeManager().getRecipe(Recipes.BASIC_SMELTER, this.recipeInventory.toIInventory(), world).orElse(null);

            if (!world.isRemote) {
                if (!inputs[0].isEmpty() && !inputs[1].isEmpty() && !inputs[2].isEmpty()) {
                    if (this.recipe != null) {
                        if (StackHelper.areStacksEqual(inputs[0], this.recipeInventory.getStackInSlot(0)) && StackHelper.areStacksEqual(inputs[1], this.recipeInventory.getStackInSlot(1)) && StackHelper.areStacksEqual(inputs[3], this.recipeInventory.getStackInSlot(3))) {
                            if (getProgress() == 0) {
                                inputs[0].shrink(1);
                                inputs[0].shrink(1);
                                inputs[0].shrink(1);
                            }
                        }
                    }
                }

                if (this.recipe != null) {
                    if (StackHelper.areStacksEqual(this.recipe.getRecipeOutput(), output)) {
                        this.inventory.setStackInSlot(0, new ItemStack(this.recipe.getRecipeOutput().getItem(), this.recipe.getOutputCount()));
                        this.recipe = null;
                    }
                }
            }
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return Localizable.of("container.atomictechnology.basic_smelter").build();
    }

    @Nullable
    @Override
    public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return BasicSmelterContainer.create(windowID, playerInventory, this::isUsableByPlayer, this.inventory, this.data, this.getPos());
    }
}
