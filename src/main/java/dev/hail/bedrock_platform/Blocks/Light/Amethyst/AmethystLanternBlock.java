package dev.hail.bedrock_platform.Blocks.Light.Amethyst;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopperGrateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class AmethystLanternBlock extends WeatheringCopperGrateBlock implements AmethystCandleLogic{
    protected final SimpleParticleType flameParticle;
    public AmethystLanternBlock(SimpleParticleType particleType, WeatherState weatherState, Properties properties) {
        super(weatherState, properties);
        this.flameParticle = particleType;
    }
    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (AmethystCandleLogic.canEmitParticle(pState)){
            double d0 = (double)pPos.getX() + 0.5;
            double d1 = (double)pPos.getY() + 0.5;
            double d2 = (double)pPos.getZ() + 0.5;
            pLevel.addParticle(this.flameParticle, d0, d1, d2, 0.0, 0.0, 0.0);
        }
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
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
}
