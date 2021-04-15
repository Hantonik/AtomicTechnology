package hantonik.atomictechnology.integration.jei;

import hantonik.atomictechnology.AtomicTechnology;
import hantonik.atomictechnology.api.AtomicRecipeTypes;
import hantonik.atomictechnology.init.AtomicBlocks;
import hantonik.atomictechnology.integration.jei.category.CompressorCategory;
import hantonik.atomictechnology.integration.jei.category.smelter.AdvancedSmelterCraftingCategory;
import hantonik.atomictechnology.integration.jei.category.smelter.AtomicSmelterCraftingCategory;
import hantonik.atomictechnology.integration.jei.category.smelter.BasicSmelterCraftingCategory;
import hantonik.atomictechnology.integration.jei.category.smelter.EliteSmelterCraftingCategory;
import hantonik.atomictechnology.inventory.screens.compressor.BasicCompressorScreen;
import hantonik.atomictechnology.inventory.screens.smelter.AdvancedSmelterScreen;
import hantonik.atomictechnology.inventory.screens.smelter.AtomicSmelterScreen;
import hantonik.atomictechnology.inventory.screens.smelter.BasicSmelterScreen;
import hantonik.atomictechnology.inventory.screens.smelter.EliteSmelterScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public final class JeiIntegration implements IModPlugin {
    public static final ResourceLocation UID = new ResourceLocation(AtomicTechnology.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new BasicSmelterCraftingCategory(guiHelper));
        registration.addRecipeCategories(new CompressorCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ClientWorld world = Minecraft.getInstance().world;

        if (world != null) {
            RecipeManager manager = world.getRecipeManager();

            registration.addRecipes(manager.getRecipes(AtomicRecipeTypes.BASIC_SMELTER).values(), BasicSmelterCraftingCategory.UID);
            registration.addRecipes(manager.getRecipes(AtomicRecipeTypes.ELITE_SMELTER).values(), EliteSmelterCraftingCategory.UID);
            registration.addRecipes(manager.getRecipes(AtomicRecipeTypes.ADVANCED_SMELTER).values(), AdvancedSmelterCraftingCategory.UID);
            registration.addRecipes(manager.getRecipes(AtomicRecipeTypes.ATOMIC_SMELTER).values(), AtomicSmelterCraftingCategory.UID);

            registration.addRecipes(manager.getRecipes(AtomicRecipeTypes.BASIC_COMPRESSOR).values(), CompressorCategory.UID);
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(AtomicBlocks.BASIC_SMELTER.get()), BasicSmelterCraftingCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(AtomicBlocks.ELITE_SMELTER.get()), EliteSmelterCraftingCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(AtomicBlocks.ADVANCED_SMELTER.get()), AdvancedSmelterCraftingCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(AtomicBlocks.ATOMIC_SMELTER.get()), AtomicSmelterCraftingCategory.UID);

        registration.addRecipeCatalyst(new ItemStack(AtomicBlocks.BASIC_COMPRESSOR.get()), CompressorCategory.UID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(BasicSmelterScreen.class, 53, 22, 77, 67, BasicSmelterCraftingCategory.UID);
        registration.addRecipeClickArea(EliteSmelterScreen.class, 53, 22, 77, 67, EliteSmelterCraftingCategory.UID);
        registration.addRecipeClickArea(AdvancedSmelterScreen.class, 53, 22, 77, 67, AdvancedSmelterCraftingCategory.UID);
        registration.addRecipeClickArea(AtomicSmelterScreen.class, 53, 22, 77, 67, AtomicSmelterCraftingCategory.UID);

        registration.addRecipeClickArea(BasicCompressorScreen.class, 88, 36, 25, 14, CompressorCategory.UID);
    }
}
