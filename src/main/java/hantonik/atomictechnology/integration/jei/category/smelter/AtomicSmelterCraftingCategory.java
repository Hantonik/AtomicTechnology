package hantonik.atomictechnology.integration.jei.category.smelter;

import hantonik.atomiccore.utils.Localizable;
import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.api.crafting.ISmelterRecipe;
import hantonik.atomictechnology.init.AtomicBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AtomicSmelterCraftingCategory implements IRecipeCategory<ISmelterRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(AtomicTechnology.MOD_ID, "smelter");
    private static final ResourceLocation TEXTURE = new ResourceLocation(AtomicTechnology.MOD_ID, "textures/gui/jei/smelter_crafting.png");

    private final IDrawable background;
    private final IDrawable icon;

    public AtomicSmelterCraftingCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 154, 78);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(AtomicBlocks.ATOMIC_SMELTER.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends ISmelterRecipe> getRecipeClass() {
        return ISmelterRecipe.class;
    }

    @Override
    public String getTitle() {
        return Localizable.of("jei.category." + AtomicTechnology.MOD_ID + ".smelter").buildString();
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(ISmelterRecipe recipe, IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());

        NonNullList<Ingredient> inputs = NonNullList.create();
        inputs.addAll(recipe.getInputs());

        if (inputs.size() == 2)
            inputs.add(Ingredient.EMPTY);
        else if (inputs.size() == 1) {
            inputs.add(Ingredient.EMPTY);
            inputs.add(Ingredient.EMPTY);
        }

        ingredients.setInputIngredients(inputs);
    }

    @Override
    public List<ITextComponent> getTooltipStrings(ISmelterRecipe recipe, double mouseX, double mouseY) {
        if (mouseX > 1 && mouseX < 14 && mouseY > 1 && mouseY < 78) {
            return Arrays.asList(
                    new StringTextComponent(recipe.getPowerCost() + " FE"),
                    new StringTextComponent(recipe.getPowerRate() + " FE/t")
            );
        }

        if (mouseX > 101 && mouseX < 121 && mouseY > 6 && mouseY < 14)
            return Collections.singletonList(Localizable.of("title." + AtomicTechnology.MOD_ID + ".atomic_smelter").build());

        return Collections.emptyList();
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, ISmelterRecipe recipe, IIngredients iIngredients) {
        IGuiItemStackGroup stacks = iRecipeLayout.getItemStacks();

        List<List<ItemStack>> inputs = iIngredients.getInputs(VanillaTypes.ITEM);
        List<ItemStack> outputs = iIngredients.getOutputs(VanillaTypes.ITEM).get(0);

        stacks.init(0, true, 25, 0);
        stacks.init(1, true, 25, 30);
        stacks.init(2, true, 25, 60);
        stacks.init(3, false, 132, 30);

        stacks.set(0, inputs.get(0));
        stacks.set(1, inputs.get(1));
        stacks.set(2, inputs.get(2));
        stacks.set(3, outputs);
    }
}
