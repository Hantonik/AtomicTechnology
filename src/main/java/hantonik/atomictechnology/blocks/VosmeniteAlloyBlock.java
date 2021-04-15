package hantonik.atomictechnology.blocks;

import hantonik.atomiccore.block.BasicBlock;
import hantonik.atomictechnology.lib.AtomicTooltips;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;

public class VosmeniteAlloyBlock extends BasicBlock {
    public VosmeniteAlloyBlock() {
        super(Material.IRON, SoundType.METAL, ToolType.PICKAXE, 3, 20.5F, 55.5F);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(AtomicTooltips.VOSMENITE_ALLOY.build());
    }
}
