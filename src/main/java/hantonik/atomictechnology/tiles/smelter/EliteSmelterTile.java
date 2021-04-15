package hantonik.atomictechnology.tiles.smelter;

import hantonik.atomiccore.utils.Localizable;
import hantonik.atomiccore.utils.helpers.StackHelper;
import hantonik.atomictechnology.api.AtomicRecipeTypes;
import hantonik.atomictechnology.configs.Configs;
import hantonik.atomictechnology.crafing.recipe.smelter.EliteSmelterRecipe;
import hantonik.atomictechnology.init.AtomicTiles;
import hantonik.atomictechnology.inventory.containers.smelter.EliteSmelterContainer;
import hantonik.atomictechnology.tiles.basics.AbstractSmelterTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EliteSmelterTile extends AbstractSmelterTile<EliteSmelterRecipe> {
    public EliteSmelterTile() {
        super(AtomicTiles.ELITE_SMELTER.get(), Configs.ELITE_SMELTER_RF_CAPACITY.get());
    }

    @Override
    public void tick() {
        World world = this.world;

        if (world != null) {
            this.updateRecipeInventory();
            IInventory recipeInventory = this.recipeInventory.toIInventory();

            if (this.recipe == null || !this.recipe.matches(recipeInventory, world) && this.progress == 0)
                this.recipe = world.getRecipeManager().getRecipe(AtomicRecipeTypes.ELITE_SMELTER, recipeInventory, world).orElse(null);

            if (!world.isRemote) {
                if (this.recipe != null) {
                    ItemStack result = this.recipe.getCraftingResult(recipeInventory);
                    ItemStack output = this.inventory.getStackInSlot(0);

                    if (StackHelper.canCombineStacks(result, output)) {
                        if (!this.mark) {
                            ItemStack input;

                            for (int i = 0; i < this.inventory.getSlots() - 1; i++) {
                                input = this.inventory.getStackInSlot(i + 1);
                                input.shrink(1);

                                this.inventory.setStackInSlot(i + 1, input);
                            }

                            this.mark = true;
                        }

                        if (this.progress >= this.getEnergyRequired()) {
                            this.updateResult(result);

                            this.progress = 0;
                            this.mark = false;
                        }

                        this.process(this.recipe);
                    }
                }
            }
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return Localizable.of("containers.atomictechnology.elite_smelter").build();
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return EliteSmelterContainer.create(windowId, playerInventory, this::isUsableByPlayer, this.inventory, this.data, this.getPos());
    }
}
