package hantonik.atomictechnology.recipes.smelter;

import hantonik.atomictechnology.utils.Localizable;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class Alloy {
    private final ResourceLocation id;
    private final String name;
    private final Ingredient[] inputs;
    private final Ingredient output;
    private final int outputCount;
    private final int powerRequired;

    public Alloy(ResourceLocation id, String name, Ingredient[] inputs, Ingredient output, int outputCount, int powerRequired) {
        this.id = id;
        this.name = name;
        this.inputs = inputs;
        this.output = output;
        this.outputCount = outputCount;
        this.powerRequired = powerRequired;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Ingredient[] getInputs() {
        if (this.inputs.length == 3)
            return this.inputs;
        else if (this.inputs.length == 2)
            return new Ingredient[] { this.inputs[0], this.getInputs()[1], Ingredient.EMPTY };
        else if (this.inputs.length == 1)
            return new Ingredient[] { this.inputs[0], Ingredient.EMPTY, Ingredient.EMPTY };
        else
            return new Ingredient[] { Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY };
    }

    public ITextComponent getDisplayName() {
        return Localizable.of(this.name).build();
    }

    public int getOutputCount() {
        return this.outputCount;
    }

    public Ingredient getOutput() {
        return this.output;
    }

    public int getPowerRequired() {
        return this.powerRequired;
    }
}
