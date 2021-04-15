package hantonik.atomictechnology.tiles.compressor;

import hantonik.atomiccore.utils.Localizable;
import hantonik.atomiccore.utils.helpers.StackHelper;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.api.AtomicRecipeTypes;
import hantonik.atomictechnology.configs.Configs;
import hantonik.atomictechnology.crafing.recipe.compressor.BasicCompressorRecipe;
import hantonik.atomictechnology.init.AtomicTiles;
import hantonik.atomictechnology.inventory.containers.compressor.BasicCompressorContainer;
import hantonik.atomictechnology.tiles.basics.AbstractCompressorTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BasicCompressorTile extends AbstractCompressorTile<BasicCompressorRecipe> {
    public BasicCompressorTile() {
        super(AtomicTiles.BASIC_COMPRESSOR.get(), Configs.BASIC_COMPRESSOR_RF_CAPACITY.get());
    }

    @Override
    public void tick() {
        World world = this.world;

        if (world != null) {
            this.updateRecipeInventory();
            IInventory recipeInventory = this.recipeInventory.toIInventory();

            if (this.recipe == null || !this.recipe.matches(recipeInventory, world))
                this.recipe = world.getRecipeManager().getRecipe(AtomicRecipeTypes.BASIC_COMPRESSOR, recipeInventory, world).orElse(null);

            if (!world.isRemote) {
                if (this.recipe != null) {
                    AtomicTechnology.LOGGER.error(1);

                    ItemStack result = this.recipe.getCraftingResult(recipeInventory);

                    if (StackHelper.canCombineStacks(result, this.inventory.getStackInSlot(0))) {
                        if (!this.mark) {
                            ItemStack input;

                            for (int i = 0; i < this.inventory.getSlots() - 2; i++) {
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
        return Localizable.of("containers." + AtomicTechnology.MOD_ID + ".basic_compressor").build();
    }

    @Nullable
    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return BasicCompressorContainer.create(windowId, playerInventory, this::isUsableByPlayer, this.inventory, this.data, this.getPos());
    }
}
