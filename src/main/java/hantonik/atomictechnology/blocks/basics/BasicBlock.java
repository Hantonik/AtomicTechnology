package hantonik.atomictechnology.blocks.basics;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BasicBlock extends Block {
    public BasicBlock(Properties properties) {
        super(properties);
    }

    public BasicBlock(Material material, SoundType soundType, ToolType toolType, int harvestLevel, float hardness, float resistance) {
        super(Properties.create(material)
                .sound(soundType)
                .harvestTool(toolType)
                .harvestLevel(harvestLevel)
                .hardnessAndResistance(hardness, resistance)
                .setRequiresTool());
    }
}
