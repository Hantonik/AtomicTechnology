package hantonik.atomictechnology.blocks;

import hantonik.atomictechnology.blocks.basics.BasicTileBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraftforge.common.ToolType;

public class AdvancedSmelter extends BasicTileBlock {
    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public AdvancedSmelter() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .setRequiresTool()
                .hardnessAndResistance(6.0F, 12.0F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(2));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
