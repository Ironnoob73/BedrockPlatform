package dev.hail.bedrock_platform.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WaterloggedTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BouncePadBlock extends WaterloggedTransparentBlock {
    public BouncePadBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
    }
    @Override
    public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            boolean isSteppingCarefully = livingEntity.isSteppingCarefully();
            if (isSteppingCarefully) {
                livingEntity.setDeltaMovement(0, 0, 0);
            } else {
                livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0, 1, 0));
            }
        }
        super.stepOn(level, pos, state, entity);
    }
    public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Entity entity, float fallDistance) {
        super.fallOn(level, state, pos, entity, 0);
    }
}
