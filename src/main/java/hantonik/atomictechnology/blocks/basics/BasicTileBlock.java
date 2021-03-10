package hantonik.atomictechnology.blocks.basics;

import net.minecraft.block.BlockState;

public abstract class BasicTileBlock extends BasicBlock {
    public BasicTileBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
