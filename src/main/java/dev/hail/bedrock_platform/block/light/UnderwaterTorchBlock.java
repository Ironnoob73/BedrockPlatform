package dev.hail.bedrock_platform.block.light;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class UnderwaterTorchBlock extends TorchBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public UnderwaterTorchBlock(SimpleParticleType particleType, Properties properties) {
        super(particleType, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        boolean waterFlag = levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER;
        return this.defaultBlockState()
                .setValue(WATERLOGGED, waterFlag);
    }

    @Override
    public void animateTick(@NotNull BlockState pState, Level pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
        double d0 = (double)pPos.getX() + 0.5;
        double d1 = (double)pPos.getY() + 0.7;
        double d2 = (double)pPos.getZ() + 0.5;
        pLevel.addParticle(this.flameParticle, d0, d1, d2, 0.0, 0.0, 0.0);
    }

    @Override
    protected @NotNull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED);
    }

    public static class WallUnderwaterTorchBlock extends WallTorchBlock implements SimpleWaterloggedBlock{
        public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
        public WallUnderwaterTorchBlock(SimpleParticleType particleType, Properties properties) {
            super(particleType, properties);
            this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.FALSE));
        }
        @Nullable
        @Override
        public BlockState getStateForPlacement(BlockPlaceContext pContext) {
            LevelAccessor levelaccessor = pContext.getLevel();
            BlockPos blockpos = pContext.getClickedPos();
            boolean waterFlag = levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER;
            if (super.getStateForPlacement(pContext) != null){
                return Objects.requireNonNull(super.getStateForPlacement(pContext))
                        .setValue(WATERLOGGED, waterFlag);
            }
            return null;
        }

        @Override
        public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
            Direction direction = pState.getValue(FACING);
            double d0 = (double)pPos.getX() + 0.5;
            double d1 = (double)pPos.getY() + 0.7;
            double d2 = (double)pPos.getZ() + 0.5;
            Direction direction1 = direction.getOpposite();
            pLevel.addParticle(
                    this.flameParticle, d0 + 0.27 * (double)direction1.getStepX(), d1 + 0.22, d2 + 0.27 * (double)direction1.getStepZ(), 0.0, 0.0, 0.0
            );
        }

        @Override
        protected @NotNull FluidState getFluidState(BlockState pState) {
            return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
        }
        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
            pBuilder.add(WATERLOGGED,FACING);
        }
    }
}
