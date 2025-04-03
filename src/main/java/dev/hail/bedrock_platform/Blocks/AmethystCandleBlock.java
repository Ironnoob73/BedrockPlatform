package dev.hail.bedrock_platform.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

import static net.minecraft.util.Mth.floor;

public class AmethystCandleBlock extends UnderwaterTorchBlock{
    public static final IntegerProperty LIGHT = IntegerProperty.create("light",0,15);
    public AmethystCandleBlock(SimpleParticleType particleType, Properties properties) {
        super(particleType, properties);
    }

    public static int getLightFromHeight(int Y){
        int result = 0;
        if (Y <= 0){
            result = 15;
        }else if (Y <= 64){
            result = 15 - floor((float) Y / 4);
        }
        return result < 0 ? 0 : Math.min(result, 15);
    }
    public static int getLight (BlockState blockState){
        if(blockState.getValue(WATERLOGGED)){
            return 15;
        }
        return blockState.getValue(LIGHT);
    }

    @Override
    public void animateTick(@NotNull BlockState pState, Level pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
        if (pState.getValue(LIGHT) > 4 || pState.getValue(WATERLOGGED)){
            super.animateTick(pState,pLevel,pPos,pRandom);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        int light = 0;
        if(!levelaccessor.dimensionType().ultraWarm() && !(levelaccessor.dimensionType().ambientLight() > 0)){
            light = getLightFromHeight(blockpos.getY());
        }
        if (super.getStateForPlacement(pContext) != null){
            return Objects.requireNonNull(super.getStateForPlacement(pContext)).setValue(LIGHT, light);
        }
        return defaultBlockState().setValue(LIGHT, light);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED,LIGHT);
    }


    public static class AmethystWallCandleBlock extends UnderwaterTorchBlock.WallUnderwaterTorchBlock{
        public AmethystWallCandleBlock(SimpleParticleType particleType, Properties properties) {
            super(particleType, properties);
        }
        @Override
        public void animateTick(@NotNull BlockState pState, Level pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
            if (pState.getValue(LIGHT) > 4 || pState.getValue(WATERLOGGED)){
                super.animateTick(pState,pLevel,pPos,pRandom);
            }
        }
        @Nullable
        @Override
        public BlockState getStateForPlacement(BlockPlaceContext pContext) {
            LevelAccessor levelaccessor = pContext.getLevel();
            BlockPos blockpos = pContext.getClickedPos();
            int light = 0;
            if(!levelaccessor.dimensionType().ultraWarm() && !(levelaccessor.dimensionType().ambientLight() > 0)){
                light = getLightFromHeight(blockpos.getY());
            }
            if (super.getStateForPlacement(pContext) != null){
                return Objects.requireNonNull(super.getStateForPlacement(pContext)).setValue(LIGHT, light);
            }
            return defaultBlockState().setValue(LIGHT, light);
        }
        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
            pBuilder.add(WATERLOGGED,FACING,LIGHT);
        }
    }
}
