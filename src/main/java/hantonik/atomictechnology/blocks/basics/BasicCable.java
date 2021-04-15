package hantonik.atomictechnology.blocks.basics;

import com.google.common.collect.Maps;
import hantonik.atomiccore.block.BasicTileBlock;
import hantonik.atomictechnology.blocks.cable.DirectionNullable;
import hantonik.atomictechnology.blocks.cable.EnumConnectType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Map;

public abstract class BasicCable extends BasicTileBlock {
    public static final EnumProperty<DirectionNullable> EXTRACT  = EnumProperty.create("extract", DirectionNullable.class);

    public static final EnumProperty<EnumConnectType> DOWN = EnumProperty.create("down", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> UP = EnumProperty.create("up", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> NORTH = EnumProperty.create("north", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> SOUTH = EnumProperty.create("south", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> WEST = EnumProperty.create("west", EnumConnectType.class);
    public static final EnumProperty<EnumConnectType> EAST = EnumProperty.create("east", EnumConnectType.class);

    public static final Map<Direction, EnumProperty<EnumConnectType>> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), p -> {
        p.put(Direction.NORTH, NORTH);
        p.put(Direction.EAST, EAST);
        p.put(Direction.SOUTH, SOUTH);
        p.put(Direction.WEST, WEST);
        p.put(Direction.UP, UP);
        p.put(Direction.DOWN, DOWN);
    });

    public BasicCable(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public abstract ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit);

    /*    static boolean shapeConnects(BlockState state, EnumProperty<EnumConnectType> dirctionProperty) {
        return state.get(dirctionProperty).equals(EnumConnectType.INVENTORY);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(handIn);

        if (stack.getItem() != AtomicItems.ATOMIC_WRENCH.get() || hit.getFace() == null)
            return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);

        final float hitLimit = 0.28F;
        Direction sideToToggle = hit.getFace();

        double hitX = hit.getHitVec().x - pos.getX();
        double hitY = hit.getHitVec().y - pos.getY();
        double hitZ = hit.getHitVec().z - pos.getZ();

        if (hitX < hitLimit)
            sideToToggle = Direction.WEST;
        else if (hitX > 1 - hitLimit)
    }*/

    public static boolean isCableBlocked(BlockState blockState, Direction side) {
        if (side == null)
            return false;

        EnumProperty<EnumConnectType> property = BasicCable.FACING_TO_PROPERTY_MAP.get(side);

        return !(blockState.getBlock() instanceof BasicCable) || !blockState.hasProperty(property) || blockState.get(property).isBlocked();
    }
}
