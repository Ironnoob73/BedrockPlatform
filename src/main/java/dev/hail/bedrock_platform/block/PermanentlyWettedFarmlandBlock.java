package dev.hail.bedrock_platform.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PermanentlyWettedFarmlandBlock extends FarmBlock {
    public PermanentlyWettedFarmlandBlock(Properties p_53247_) {
        super(p_53247_);
    }

    @Override
    protected boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, @NotNull BlockPos pPos) {
        return true;
    }
    @Override
    protected void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {

    }
    @Override
    public void fallOn(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockPos pPos, Entity pEntity, float pFallDistance) {
        pEntity.causeFallDamage(pFallDistance, 1.0F, pEntity.damageSources().fall());
    }
}
