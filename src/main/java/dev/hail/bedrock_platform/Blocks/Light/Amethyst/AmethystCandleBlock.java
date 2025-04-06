package dev.hail.bedrock_platform.Blocks.Light.Amethyst;

import dev.hail.bedrock_platform.Blocks.Light.UnderwaterTorchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class AmethystCandleBlock extends UnderwaterTorchBlock implements AmethystCandleLogic{
    public AmethystCandleBlock(SimpleParticleType particleType, Properties properties) {
        super(particleType, properties);
    }

    @Override
    public void animateTick(@NotNull BlockState pState, Level pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
        if (AmethystCandleLogic.canEmitParticle(pState)){
            super.animateTick(pState,pLevel,pPos,pRandom);
        }
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        int light = AmethystCandleLogic.getLightFromEnvironment(pContext);
        if (super.getStateForPlacement(pContext) != null){
            return Objects.requireNonNull(super.getStateForPlacement(pContext)).setValue(LIGHT, light);
        }
        return defaultBlockState().setValue(LIGHT, light);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AmethystCandleLogic.WATERLOGGED,LIGHT);
    }


    public static class AmethystWallCandleBlock extends UnderwaterTorchBlock.WallUnderwaterTorchBlock implements AmethystCandleLogic{
        public AmethystWallCandleBlock(SimpleParticleType particleType, Properties properties) {
            super(particleType, properties);
        }
        @Override
        public void animateTick(@NotNull BlockState pState, Level pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
            if (AmethystCandleLogic.canEmitParticle(pState)){
                super.animateTick(pState,pLevel,pPos,pRandom);
            }
        }
        @Nullable
        @Override
        public BlockState getStateForPlacement(BlockPlaceContext pContext) {
            int light = AmethystCandleLogic.getLightFromEnvironment(pContext);
            if (super.getStateForPlacement(pContext) != null){
                return Objects.requireNonNull(super.getStateForPlacement(pContext)).setValue(LIGHT, light);
            }
            return defaultBlockState().setValue(LIGHT, light);
        }
        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
            pBuilder.add(AmethystCandleLogic.WATERLOGGED,FACING,LIGHT);
        }
    }
}
