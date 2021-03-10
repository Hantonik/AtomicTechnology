package hantonik.atomictechnology.utils.managers;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.crafting.recipe.BasicSmelterRecipe;
/*import hantonik.atomictechnology.crafting.recipe.EliteSmelterRecipe;
import hantonik.atomictechnology.crafting.recipe.AdvancedSmelterRecipe;
import hantonik.atomictechnology.crafting.recipe.AtomicSmelterRecipe;*/
import hantonik.atomictechnology.recipes.smelter.Alloy;
import hantonik.atomictechnology.recipes.smelter.AlloyRegistry;
import hantonik.atomictechnology.utils.helpers.RecipeHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class RecipeManager implements IResourceManagerReloadListener {
    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        AlloyRegistry.getInstance().getAlloys().forEach(alloy -> {
            BasicSmelterRecipe basicSmelterRecipe = createBasicSmelterRecipe(alloy);
/*            EliteSmelterRecipe eliteSmelterRecipe = createEliteSmelterRecipe(alloy);
            AdvancedSmelterRecipe advancedSmelterRecipe = createAdvancedSmelterRecipe(alloy);
            AtomicSmelterRecipe atomicSmelterRecipe = createAtomicSmelterRecipe(alloy);*/

            if (basicSmelterRecipe != null)
                RecipeHelper.addRecipe(basicSmelterRecipe);

/*            if (eliteSmelterRecipe != null)
                RecipeHelper.addRecipe(eliteSmelterRecipe);

            if (advancedSmelterRecipe != null)
                RecipeHelper.addRecipe(advancedSmelterRecipe);

            if (atomicSmelterRecipe != null)
                RecipeHelper.addRecipe(atomicSmelterRecipe);*/
        });
    }

    @SubscribeEvent
    public void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(this);
    }

    private static BasicSmelterRecipe createBasicSmelterRecipe(Alloy alloy) {
        Ingredient slot1 = alloy.getInputs()[0];
        Ingredient slot2 = alloy.getInputs()[1];
        Ingredient slot3 = alloy.getInputs()[2];

        ItemStack output = new ItemStack((IItemProvider) alloy.getOutput());
        ResourceLocation id = alloy.getId();
        ResourceLocation recipeId = new ResourceLocation(AtomicTechnology.MOD_ID, id.getPath());

        int outputCount = alloy.getOutputCount();
        int powerRequired = alloy.getPowerRequired();

        if (slot1 == Ingredient.EMPTY && slot2 == Ingredient.EMPTY && slot3 == Ingredient.EMPTY)
            return null;

        return new BasicSmelterRecipe(recipeId, alloy.getInputs(), output, outputCount, powerRequired);
    }

    /*private static EliteSmelterRecipe createEliteSmelterRecipe(Alloy alloy) {
        Ingredient slot1 = alloy.getInputs()[0];
        Ingredient slot2 = alloy.getInputs()[1];
        Ingredient slot3 = alloy.getInputs()[2];

        ItemStack output = alloy.getOutput();
        ResourceLocation id = alloy.getId();
        ResourceLocation recipeId = new ResourceLocation(AtomicTechnology.MOD_ID, id.getPath());

        int outputCount = alloy.getOutputCount();
        int powerRequired = alloy.getPowerRequired();

        if (slot1 == Ingredient.EMPTY && slot2 == Ingredient.EMPTY && slot3 == Ingredient.EMPTY)
            return null;

        return new EliteSmelterRecipe(recipeId, alloy.getInputs(), output, outputCount, powerRequired);
    }

    private static AdvancedSmelterRecipe createAdvancedSmelterRecipe(Alloy alloy) {
        Ingredient slot1 = alloy.getInputs()[0];
        Ingredient slot2 = alloy.getInputs()[1];
        Ingredient slot3 = alloy.getInputs()[2];

        ItemStack output = alloy.getOutput();
        ResourceLocation id = alloy.getId();
        ResourceLocation recipeId = new ResourceLocation(AtomicTechnology.MOD_ID, id.getPath());

        int outputCount = alloy.getOutputCount();
        int powerRequired = alloy.getPowerRequired();

        if (slot1 == Ingredient.EMPTY && slot2 == Ingredient.EMPTY && slot3 == Ingredient.EMPTY)
            return null;

        return new AdvancedSmelterRecipe(recipeId, alloy.getInputs(), output, outputCount, powerRequired);
    }

    private static AtomicSmelterRecipe createAtomicSmelterRecipe(Alloy alloy) {
        Ingredient slot1 = alloy.getInputs()[0];
        Ingredient slot2 = alloy.getInputs()[1];
        Ingredient slot3 = alloy.getInputs()[2];

        ItemStack output = alloy.getOutput();
        ResourceLocation id = alloy.getId();
        ResourceLocation recipeId = new ResourceLocation(AtomicTechnology.MOD_ID, id.getPath());

        int outputCount = alloy.getOutputCount();
        int powerRequired = alloy.getPowerRequired();

        if (slot1 == Ingredient.EMPTY && slot2 == Ingredient.EMPTY && slot3 == Ingredient.EMPTY)
            return null;

        return new AtomicSmelterRecipe(recipeId, alloy.getInputs(), output, outputCount, powerRequired);
    }*/
}
