package hantonik.atomictechnology.blocks.cable;

import hantonik.atomictechnology.blocks.basics.BasicCable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AtomicEnergyCable extends BasicCable {
    public AtomicEnergyCable() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .hardnessAndResistance(0.75F, 1.5F));
    }

    @Nonnull
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return null;
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);
            IEnergyStorage capability = tile.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);

            if (capability != null) {
                int energyStored = capability.getEnergyStored();

                if (energyStored > 0)
                    player.sendStatusMessage(new TranslationTextComponent(energyStored + ""), true);
            }
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);

        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        for (Direction direction : Direction.values()) {
            TileEntity tile = worldIn.getTileEntity(pos.offset(direction));
            IEnergyStorage energyStorage = tile == null ? null : tile.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).orElse(null);

            if (energyStorage != null) {
                state = state.with(FACING_TO_PROPERTY_MAP.get(direction), EnumConnectType.INVENTORY);
                worldIn.setBlockState(pos, state);
            }
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        EnumProperty<EnumConnectType> property = FACING_TO_PROPERTY_MAP.get(facing);

        if (stateIn.get(property).isBlocked())
            return stateIn;

        if (isEnergy(facing, worldIn, facingPos))
            return  stateIn.with(property, EnumConnectType.INVENTORY);
        else
            return stateIn.with(property, EnumConnectType.NONE);
    }
}
