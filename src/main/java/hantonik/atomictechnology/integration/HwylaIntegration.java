package hantonik.atomictechnology.integration;

import hantonik.atomictechnology.blocks.compressor.BasicCompressorBlock;
import hantonik.atomictechnology.blocks.smelter.AdvancedSmelter;
import hantonik.atomictechnology.blocks.smelter.AtomicSmelter;
import hantonik.atomictechnology.blocks.smelter.BasicSmelter;
import hantonik.atomictechnology.blocks.smelter.EliteSmelter;
import hantonik.atomictechnology.crafing.recipe.compressor.BasicCompressorRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.AdvancedSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.AtomicSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.BasicSmelterRecipe;
import hantonik.atomictechnology.crafing.recipe.smelter.EliteSmelterRecipe;
import hantonik.atomictechnology.lib.AtomicTooltips;
import hantonik.atomictechnology.tiles.compressor.BasicCompressorTile;
import hantonik.atomictechnology.tiles.smelter.AdvancedSmelterTile;
import hantonik.atomictechnology.tiles.smelter.AtomicSmelterTile;
import hantonik.atomictechnology.tiles.smelter.BasicSmelterTile;
import hantonik.atomictechnology.tiles.smelter.EliteSmelterTile;
import mcp.mobius.waila.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

@WailaPlugin
public class HwylaIntegration implements IWailaPlugin {
    @Override
    public void register(IRegistrar registrar) {
        registrar.registerComponentProvider(new IComponentProvider() {
            @Override
            public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
                BasicSmelterTile basicSmelter = (BasicSmelterTile) accessor.getTileEntity();
                BasicSmelterRecipe recipe = basicSmelter.getActiveRecipe();

                if (recipe != null) {
                    ItemStack output = recipe.getRecipeOutput();
                    tooltip.add(AtomicTooltips.CRAFTING.args(output.getCount(), output.getDisplayName()).build());
                }
            }
        }, TooltipPosition.BODY, BasicSmelter.class);

        registrar.registerComponentProvider(new IComponentProvider() {
            @Override
            public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
                EliteSmelterTile eliteSmelter = (EliteSmelterTile) accessor.getTileEntity();
                EliteSmelterRecipe recipe = eliteSmelter.getActiveRecipe();

                if (recipe != null) {
                    ItemStack output = recipe.getRecipeOutput();
                    tooltip.add(AtomicTooltips.CRAFTING.args(output.getCount(), output.getDisplayName()).build());
                }
            }
        }, TooltipPosition.BODY, EliteSmelter.class);

        registrar.registerComponentProvider(new IComponentProvider() {
            @Override
            public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
                AdvancedSmelterTile advancedSmelter = (AdvancedSmelterTile) accessor.getTileEntity();
                AdvancedSmelterRecipe recipe = advancedSmelter.getActiveRecipe();

                if (recipe != null) {
                    ItemStack output = recipe.getRecipeOutput();
                    tooltip.add(AtomicTooltips.CRAFTING.args(output.getCount(), output.getDisplayName()).build());
                }
            }
        }, TooltipPosition.BODY, AdvancedSmelter.class);

        registrar.registerComponentProvider(new IComponentProvider() {
            @Override
            public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
                AtomicSmelterTile atomicSmelter = (AtomicSmelterTile) accessor.getTileEntity();
                AtomicSmelterRecipe recipe = atomicSmelter.getActiveRecipe();

                if (recipe != null) {
                    ItemStack output = recipe.getRecipeOutput();
                    tooltip.add(AtomicTooltips.CRAFTING.args(output.getCount(), output.getDisplayName()).build());
                }
            }
        }, TooltipPosition.BODY, AtomicSmelter.class);

        registrar.registerComponentProvider(new IComponentProvider() {
            @Override
            public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
                BasicCompressorTile basicCompressor = (BasicCompressorTile) accessor.getTileEntity();
                BasicCompressorRecipe recipe = basicCompressor.getActiveRecipe();

                if (recipe != null) {
                    ItemStack output = recipe.getRecipeOutput();
                    tooltip.add(AtomicTooltips.CRAFTING.args(output.getCount(), output.getDisplayName()).build());
                }
            }
        }, TooltipPosition.BODY, BasicCompressorBlock.class);
    }
}
