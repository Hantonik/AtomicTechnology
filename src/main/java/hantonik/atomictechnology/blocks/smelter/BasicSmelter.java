package hantonik.atomictechnology.blocks.smelter;

import hantonik.atomiccore.block.BasicTileBlock;
import hantonik.atomictechnology.tiles.smelter.BasicSmelterTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class BasicSmelter extends BasicTileBlock {
    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    private static final VoxelShape NORTH = Stream.of(
            Block.makeCuboidShape(14, 1, 6, 15, 6, 10),
            Block.makeCuboidShape(14, 10, 6, 15, 15, 10),
            Block.makeCuboidShape(14, 1, 1, 15, 15, 6),
            Block.makeCuboidShape(0, 0, 1, 1, 1, 15),
            Block.makeCuboidShape(15, 0, 1, 16, 1, 15),
            Block.makeCuboidShape(0, 0, 15, 16, 1, 16),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 1),
            Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(14, 1, 10, 15, 15, 15),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(10, 14, 1, 14, 15, 10),
            Block.makeCuboidShape(6, 14, 1, 10, 15, 2),
            Block.makeCuboidShape(10, 14, 14, 14, 15, 15),
            Block.makeCuboidShape(2, 14, 14, 6, 15, 15),
            Block.makeCuboidShape(1, 14, 1, 2, 15, 15),
            Block.makeCuboidShape(2, 14, 1, 6, 15, 10),
            Block.makeCuboidShape(6, 14, 6, 10, 15, 15),
            Block.makeCuboidShape(1, 1, 5, 2, 5, 11),
            Block.makeCuboidShape(1, 11, 5, 2, 14, 11),
            Block.makeCuboidShape(1, 1, 11, 2, 14, 15),
            Block.makeCuboidShape(1, 1, 1, 2, 14, 5),
            Block.makeCuboidShape(5, 1, 14, 11, 5, 15),
            Block.makeCuboidShape(5, 1, 1, 11, 5, 2),
            Block.makeCuboidShape(5, 12, 14, 11, 14, 15),
            Block.makeCuboidShape(5, 12, 1, 11, 14, 2),
            Block.makeCuboidShape(2, 1, 14, 5, 14, 15),
            Block.makeCuboidShape(2, 1, 1, 5, 14, 2),
            Block.makeCuboidShape(11, 1, 14, 14, 14, 15),
            Block.makeCuboidShape(11, 1, 1, 14, 14, 2),
            Block.makeCuboidShape(2, 1, 2, 14, 14, 14),
            Block.makeCuboidShape(0, 15, 0, 1, 16, 16),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(6, 5, 0, 10, 6, 2),
            Block.makeCuboidShape(6, 7, 0, 10, 8, 2),
            Block.makeCuboidShape(6, 9, 0, 10, 10, 2),
            Block.makeCuboidShape(10, 5, 0, 11, 12, 2),
            Block.makeCuboidShape(6, 11, 0, 10, 12, 2),
            Block.makeCuboidShape(5, 5, 0, 6, 12, 2),
            Block.makeCuboidShape(6, 11, 14, 10, 12, 16),
            Block.makeCuboidShape(6, 9, 14, 10, 10, 16),
            Block.makeCuboidShape(6, 5, 14, 10, 6, 16),
            Block.makeCuboidShape(6, 7, 14, 10, 8, 16),
            Block.makeCuboidShape(5, 5, 14, 6, 12, 16),
            Block.makeCuboidShape(10, 5, 14, 11, 12, 16),
            Block.makeCuboidShape(13, 14, 11, 14, 16, 13),
            Block.makeCuboidShape(10, 14, 13, 14, 16, 14),
            Block.makeCuboidShape(10, 14, 11, 11, 16, 13),
            Block.makeCuboidShape(5, 14, 11, 6, 16, 13),
            Block.makeCuboidShape(2, 14, 13, 6, 16, 14),
            Block.makeCuboidShape(2, 14, 11, 3, 16, 13),
            Block.makeCuboidShape(2, 14, 10, 6, 16, 11),
            Block.makeCuboidShape(10, 14, 10, 14, 16, 11),
            Block.makeCuboidShape(9, 14, 3, 10, 16, 5),
            Block.makeCuboidShape(6, 14, 5, 10, 16, 6),
            Block.makeCuboidShape(6, 14, 3, 7, 16, 5),
            Block.makeCuboidShape(6, 14, 2, 10, 16, 3),
            Block.makeCuboidShape(15, 5, 6, 16, 6, 10),
            Block.makeCuboidShape(15, 5, 10, 16, 11, 11),
            Block.makeCuboidShape(15, 10, 6, 16, 11, 10),
            Block.makeCuboidShape(14, 9, 9, 15, 10, 10),
            Block.makeCuboidShape(14, 6, 9, 15, 7, 10),
            Block.makeCuboidShape(14, 6, 6, 15, 7, 7),
            Block.makeCuboidShape(14, 7, 7, 16, 9, 9),
            Block.makeCuboidShape(14, 9, 6, 15, 10, 7),
            Block.makeCuboidShape(15, 5, 5, 16, 11, 6),
            Block.makeCuboidShape(0, 5, 6, 2, 6, 10),
            Block.makeCuboidShape(0, 10, 6, 2, 11, 10),
            Block.makeCuboidShape(0, 5, 10, 2, 11, 11),
            Block.makeCuboidShape(0, 5, 5, 2, 11, 6)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    private static final VoxelShape SOUTH = Stream.of(
            Block.makeCuboidShape(1, 1, 6, 2, 6, 10),
            Block.makeCuboidShape(1, 10, 6, 2, 15, 10),
            Block.makeCuboidShape(1, 1, 10, 2, 15, 15),
            Block.makeCuboidShape(15, 0, 1, 16, 1, 15),
            Block.makeCuboidShape(0, 0, 1, 1, 1, 15),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 1),
            Block.makeCuboidShape(0, 0, 15, 16, 1, 16),
            Block.makeCuboidShape(0, 15, 0, 1, 16, 16),
            Block.makeCuboidShape(1, 15, 0, 15, 16, 1),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(1, 1, 1, 2, 15, 6),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(2, 14, 6, 6, 15, 15),
            Block.makeCuboidShape(6, 14, 14, 10, 15, 15),
            Block.makeCuboidShape(2, 14, 1, 6, 15, 2),
            Block.makeCuboidShape(10, 14, 1, 14, 15, 2),
            Block.makeCuboidShape(14, 14, 1, 15, 15, 15),
            Block.makeCuboidShape(10, 14, 6, 14, 15, 15),
            Block.makeCuboidShape(6, 14, 1, 10, 15, 10),
            Block.makeCuboidShape(14, 1, 5, 15, 5, 11),
            Block.makeCuboidShape(14, 11, 5, 15, 14, 11),
            Block.makeCuboidShape(14, 1, 1, 15, 14, 5),
            Block.makeCuboidShape(14, 1, 11, 15, 14, 15),
            Block.makeCuboidShape(5, 1, 1, 11, 5, 2),
            Block.makeCuboidShape(5, 1, 14, 11, 5, 15),
            Block.makeCuboidShape(5, 12, 1, 11, 14, 2),
            Block.makeCuboidShape(5, 12, 14, 11, 14, 15),
            Block.makeCuboidShape(11, 1, 1, 14, 14, 2),
            Block.makeCuboidShape(11, 1, 14, 14, 14, 15),
            Block.makeCuboidShape(2, 1, 1, 5, 14, 2),
            Block.makeCuboidShape(2, 1, 14, 5, 14, 15),
            Block.makeCuboidShape(2, 1, 2, 14, 14, 14),
            Block.makeCuboidShape(15, 15, 0, 16, 16, 16),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(1, 15, 15, 15, 16, 16),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(6, 5, 14, 10, 6, 16),
            Block.makeCuboidShape(6, 7, 14, 10, 8, 16),
            Block.makeCuboidShape(6, 9, 14, 10, 10, 16),
            Block.makeCuboidShape(5, 5, 14, 6, 12, 16),
            Block.makeCuboidShape(6, 11, 14, 10, 12, 16),
            Block.makeCuboidShape(10, 5, 14, 11, 12, 16),
            Block.makeCuboidShape(6, 11, 0, 10, 12, 2),
            Block.makeCuboidShape(6, 9, 0, 10, 10, 2),
            Block.makeCuboidShape(6, 5, 0, 10, 6, 2),
            Block.makeCuboidShape(6, 7, 0, 10, 8, 2),
            Block.makeCuboidShape(10, 5, 0, 11, 12, 2),
            Block.makeCuboidShape(5, 5, 0, 6, 12, 2),
            Block.makeCuboidShape(2, 14, 3, 3, 16, 5),
            Block.makeCuboidShape(2, 14, 2, 6, 16, 3),
            Block.makeCuboidShape(5, 14, 3, 6, 16, 5),
            Block.makeCuboidShape(10, 14, 3, 11, 16, 5),
            Block.makeCuboidShape(10, 14, 2, 14, 16, 3),
            Block.makeCuboidShape(13, 14, 3, 14, 16, 5),
            Block.makeCuboidShape(10, 14, 5, 14, 16, 6),
            Block.makeCuboidShape(2, 14, 5, 6, 16, 6),
            Block.makeCuboidShape(6, 14, 11, 7, 16, 13),
            Block.makeCuboidShape(6, 14, 10, 10, 16, 11),
            Block.makeCuboidShape(9, 14, 11, 10, 16, 13),
            Block.makeCuboidShape(6, 14, 13, 10, 16, 14),
            Block.makeCuboidShape(0, 5, 6, 1, 6, 10),
            Block.makeCuboidShape(0, 5, 5, 1, 11, 6),
            Block.makeCuboidShape(0, 10, 6, 1, 11, 10),
            Block.makeCuboidShape(1, 9, 6, 2, 10, 7),
            Block.makeCuboidShape(1, 6, 6, 2, 7, 7),
            Block.makeCuboidShape(1, 6, 9, 2, 7, 10),
            Block.makeCuboidShape(0, 7, 7, 2, 9, 9),
            Block.makeCuboidShape(1, 9, 9, 2, 10, 10),
            Block.makeCuboidShape(0, 5, 10, 1, 11, 11),
            Block.makeCuboidShape(14, 5, 6, 16, 6, 10),
            Block.makeCuboidShape(14, 10, 6, 16, 11, 10),
            Block.makeCuboidShape(14, 5, 5, 16, 11, 6),
            Block.makeCuboidShape(14, 5, 10, 16, 11, 11)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    private static final VoxelShape EAST = Stream.of(
            Block.makeCuboidShape(6, 1, 14, 10, 6, 15),
            Block.makeCuboidShape(6, 10, 14, 10, 15, 15),
            Block.makeCuboidShape(10, 1, 14, 15, 15, 15),
            Block.makeCuboidShape(1, 0, 0, 15, 1, 1),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16),
            Block.makeCuboidShape(0, 0, 0, 1, 1, 16),
            Block.makeCuboidShape(15, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(0, 15, 15, 16, 16, 16),
            Block.makeCuboidShape(0, 15, 1, 1, 16, 15),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(1, 1, 14, 6, 15, 15),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(6, 14, 10, 15, 15, 14),
            Block.makeCuboidShape(14, 14, 6, 15, 15, 10),
            Block.makeCuboidShape(1, 14, 10, 2, 15, 14),
            Block.makeCuboidShape(1, 14, 2, 2, 15, 6),
            Block.makeCuboidShape(1, 14, 1, 15, 15, 2),
            Block.makeCuboidShape(6, 14, 2, 15, 15, 6),
            Block.makeCuboidShape(1, 14, 6, 10, 15, 10),
            Block.makeCuboidShape(5, 1, 1, 11, 5, 2),
            Block.makeCuboidShape(5, 11, 1, 11, 14, 2),
            Block.makeCuboidShape(1, 1, 1, 5, 14, 2),
            Block.makeCuboidShape(11, 1, 1, 15, 14, 2),
            Block.makeCuboidShape(1, 1, 5, 2, 5, 11),
            Block.makeCuboidShape(14, 1, 5, 15, 5, 11),
            Block.makeCuboidShape(1, 12, 5, 2, 14, 11),
            Block.makeCuboidShape(14, 12, 5, 15, 14, 11),
            Block.makeCuboidShape(1, 1, 2, 2, 14, 5),
            Block.makeCuboidShape(14, 1, 2, 15, 14, 5),
            Block.makeCuboidShape(1, 1, 11, 2, 14, 14),
            Block.makeCuboidShape(14, 1, 11, 15, 14, 14),
            Block.makeCuboidShape(2, 1, 2, 14, 14, 14),
            Block.makeCuboidShape(0, 15, 0, 16, 16, 1),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(15, 15, 1, 16, 16, 15),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(14, 5, 6, 16, 6, 10),
            Block.makeCuboidShape(14, 7, 6, 16, 8, 10),
            Block.makeCuboidShape(14, 9, 6, 16, 10, 10),
            Block.makeCuboidShape(14, 5, 10, 16, 12, 11),
            Block.makeCuboidShape(14, 11, 6, 16, 12, 10),
            Block.makeCuboidShape(14, 5, 5, 16, 12, 6),
            Block.makeCuboidShape(0, 11, 6, 2, 12, 10),
            Block.makeCuboidShape(0, 9, 6, 2, 10, 10),
            Block.makeCuboidShape(0, 5, 6, 2, 6, 10),
            Block.makeCuboidShape(0, 7, 6, 2, 8, 10),
            Block.makeCuboidShape(0, 5, 5, 2, 12, 6),
            Block.makeCuboidShape(0, 5, 10, 2, 12, 11),
            Block.makeCuboidShape(3, 14, 13, 5, 16, 14),
            Block.makeCuboidShape(2, 14, 10, 3, 16, 14),
            Block.makeCuboidShape(3, 14, 10, 5, 16, 11),
            Block.makeCuboidShape(3, 14, 5, 5, 16, 6),
            Block.makeCuboidShape(2, 14, 2, 3, 16, 6),
            Block.makeCuboidShape(3, 14, 2, 5, 16, 3),
            Block.makeCuboidShape(5, 14, 2, 6, 16, 6),
            Block.makeCuboidShape(5, 14, 10, 6, 16, 14),
            Block.makeCuboidShape(11, 14, 9, 13, 16, 10),
            Block.makeCuboidShape(10, 14, 6, 11, 16, 10),
            Block.makeCuboidShape(11, 14, 6, 13, 16, 7),
            Block.makeCuboidShape(13, 14, 6, 14, 16, 10),
            Block.makeCuboidShape(6, 5, 15, 10, 6, 16),
            Block.makeCuboidShape(5, 5, 15, 6, 11, 16),
            Block.makeCuboidShape(6, 10, 15, 10, 11, 16),
            Block.makeCuboidShape(6, 9, 14, 7, 10, 15),
            Block.makeCuboidShape(6, 6, 14, 7, 7, 15),
            Block.makeCuboidShape(9, 6, 14, 10, 7, 15),
            Block.makeCuboidShape(7, 7, 14, 9, 9, 16),
            Block.makeCuboidShape(9, 9, 14, 10, 10, 15),
            Block.makeCuboidShape(10, 5, 15, 11, 11, 16),
            Block.makeCuboidShape(6, 5, 0, 10, 6, 2),
            Block.makeCuboidShape(6, 10, 0, 10, 11, 2),
            Block.makeCuboidShape(5, 5, 0, 6, 11, 2),
            Block.makeCuboidShape(10, 5, 0, 11, 11, 2)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    private static final VoxelShape WEST = Stream.of(
            Block.makeCuboidShape(6, 1, 1, 10, 6, 2),
            Block.makeCuboidShape(6, 10, 1, 10, 15, 2),
            Block.makeCuboidShape(1, 1, 1, 6, 15, 2),
            Block.makeCuboidShape(1, 0, 15, 15, 1, 16),
            Block.makeCuboidShape(1, 0, 0, 15, 1, 1),
            Block.makeCuboidShape(15, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(0, 0, 0, 1, 1, 16),
            Block.makeCuboidShape(0, 15, 0, 16, 16, 1),
            Block.makeCuboidShape(15, 15, 1, 16, 16, 15),
            Block.makeCuboidShape(15, 1, 0, 16, 15, 1),
            Block.makeCuboidShape(10, 1, 1, 15, 15, 2),
            Block.makeCuboidShape(15, 1, 15, 16, 15, 16),
            Block.makeCuboidShape(1, 14, 2, 10, 15, 6),
            Block.makeCuboidShape(1, 14, 6, 2, 15, 10),
            Block.makeCuboidShape(14, 14, 2, 15, 15, 6),
            Block.makeCuboidShape(14, 14, 10, 15, 15, 14),
            Block.makeCuboidShape(1, 14, 14, 15, 15, 15),
            Block.makeCuboidShape(1, 14, 10, 10, 15, 14),
            Block.makeCuboidShape(6, 14, 6, 15, 15, 10),
            Block.makeCuboidShape(5, 1, 14, 11, 5, 15),
            Block.makeCuboidShape(5, 11, 14, 11, 14, 15),
            Block.makeCuboidShape(11, 1, 14, 15, 14, 15),
            Block.makeCuboidShape(1, 1, 14, 5, 14, 15),
            Block.makeCuboidShape(14, 1, 5, 15, 5, 11),
            Block.makeCuboidShape(1, 1, 5, 2, 5, 11),
            Block.makeCuboidShape(14, 12, 5, 15, 14, 11),
            Block.makeCuboidShape(1, 12, 5, 2, 14, 11),
            Block.makeCuboidShape(14, 1, 11, 15, 14, 14),
            Block.makeCuboidShape(1, 1, 11, 2, 14, 14),
            Block.makeCuboidShape(14, 1, 2, 15, 14, 5),
            Block.makeCuboidShape(1, 1, 2, 2, 14, 5),
            Block.makeCuboidShape(2, 1, 2, 14, 14, 14),
            Block.makeCuboidShape(0, 15, 15, 16, 16, 16),
            Block.makeCuboidShape(0, 1, 0, 1, 15, 1),
            Block.makeCuboidShape(0, 15, 1, 1, 16, 15),
            Block.makeCuboidShape(0, 1, 15, 1, 15, 16),
            Block.makeCuboidShape(0, 5, 6, 2, 6, 10),
            Block.makeCuboidShape(0, 7, 6, 2, 8, 10),
            Block.makeCuboidShape(0, 9, 6, 2, 10, 10),
            Block.makeCuboidShape(0, 5, 5, 2, 12, 6),
            Block.makeCuboidShape(0, 11, 6, 2, 12, 10),
            Block.makeCuboidShape(0, 5, 10, 2, 12, 11),
            Block.makeCuboidShape(14, 11, 6, 16, 12, 10),
            Block.makeCuboidShape(14, 9, 6, 16, 10, 10),
            Block.makeCuboidShape(14, 5, 6, 16, 6, 10),
            Block.makeCuboidShape(14, 7, 6, 16, 8, 10),
            Block.makeCuboidShape(14, 5, 10, 16, 12, 11),
            Block.makeCuboidShape(14, 5, 5, 16, 12, 6),
            Block.makeCuboidShape(11, 14, 2, 13, 16, 3),
            Block.makeCuboidShape(13, 14, 2, 14, 16, 6),
            Block.makeCuboidShape(11, 14, 5, 13, 16, 6),
            Block.makeCuboidShape(11, 14, 10, 13, 16, 11),
            Block.makeCuboidShape(13, 14, 10, 14, 16, 14),
            Block.makeCuboidShape(11, 14, 13, 13, 16, 14),
            Block.makeCuboidShape(10, 14, 10, 11, 16, 14),
            Block.makeCuboidShape(10, 14, 2, 11, 16, 6),
            Block.makeCuboidShape(3, 14, 6, 5, 16, 7),
            Block.makeCuboidShape(5, 14, 6, 6, 16, 10),
            Block.makeCuboidShape(3, 14, 9, 5, 16, 10),
            Block.makeCuboidShape(2, 14, 6, 3, 16, 10),
            Block.makeCuboidShape(6, 5, 0, 10, 6, 1),
            Block.makeCuboidShape(10, 5, 0, 11, 11, 1),
            Block.makeCuboidShape(6, 10, 0, 10, 11, 1),
            Block.makeCuboidShape(9, 9, 1, 10, 10, 2),
            Block.makeCuboidShape(9, 6, 1, 10, 7, 2),
            Block.makeCuboidShape(6, 6, 1, 7, 7, 2),
            Block.makeCuboidShape(7, 7, 0, 9, 9, 2),
            Block.makeCuboidShape(6, 9, 1, 7, 10, 2),
            Block.makeCuboidShape(5, 5, 0, 6, 11, 1),
            Block.makeCuboidShape(6, 5, 14, 10, 6, 16),
            Block.makeCuboidShape(6, 10, 14, 10, 11, 16),
            Block.makeCuboidShape(10, 5, 14, 11, 11, 16),
            Block.makeCuboidShape(5, 5, 14, 6, 11, 16)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public BasicSmelter() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .setRequiresTool()
                .hardnessAndResistance(4.75F, 9.5F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
        switch (state.get(FACING)) {
            case SOUTH:
                return SOUTH;
            case EAST:
                return EAST;
            case WEST:
                return WEST;
            default:
                return NORTH;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case SOUTH:
                return SOUTH;
            case EAST:
                return EAST;
            case WEST:
                return WEST;
            default:
                return NORTH;
        }
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BasicSmelterTile();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);

            if (tile instanceof BasicSmelterTile) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tile, pos);
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tile = world.getTileEntity(pos);

            if (tile instanceof BasicSmelterTile) {
                BasicSmelterTile smelter = (BasicSmelterTile) tile;
                InventoryHelper.dropItems(world, pos, smelter.getInventory().getStacks());
            }
        }

        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
