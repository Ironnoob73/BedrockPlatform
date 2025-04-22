package dev.hail.bedrock_platform.Blocks.Light;

import dev.hail.bedrock_platform.Particle.BPParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SolidTorchBlock extends UnderwaterTorchBlock {
    private final boolean deepSlate;
    public SolidTorchBlock(Properties properties, boolean deepSlate) {
        super(ParticleTypes.FLAME, properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(WATERLOGGED, Boolean.FALSE)
        );
        this.deepSlate = deepSlate;
    }
    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (!pState.getValue(WATERLOGGED)){
            double d0 = (double)pPos.getX() + 0.5;
            double d1 = (double)pPos.getY() + 0.65;
            double d2 = (double)pPos.getZ() + 0.5;
            SimpleParticleType flame = BPParticles.STONE_TORCH_FLAME.get();
            if (deepSlate){
                flame = BPParticles.DEEPSLATE_TORCH_FLAME.get();
            }
            pLevel.addParticle(flame, d0, d1, d2, 0.0, 0.005, 0.0);
        }
    }

    public static class SolidWallTorchBlock extends WallUnderwaterTorchBlock {
        private final boolean deepSlate;
        public SolidWallTorchBlock(Properties properties, boolean deepSlate) {
            super(ParticleTypes.FLAME, properties);
            this.registerDefaultState(
                    this.stateDefinition
                            .any()
                            .setValue(WATERLOGGED, Boolean.FALSE)
                            .setValue(FACING, Direction.SOUTH)
            );
            this.deepSlate = deepSlate;
        }
        @Override
        public void animateTick(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
            if (!pState.getValue(WATERLOGGED)){
                Direction direction = pState.getValue(FACING);
                double d0 = (double)pPos.getX() + 0.5;
                double d1 = (double)pPos.getY() + 0.65;
                double d2 = (double)pPos.getZ() + 0.5;
                Direction direction1 = direction.getOpposite();
                SimpleParticleType flame = BPParticles.STONE_TORCH_FLAME.get();
                if (deepSlate){
                    flame = BPParticles.DEEPSLATE_TORCH_FLAME.get();
                }
                pLevel.addParticle(
                        flame, d0 + 0.27 * (double)direction1.getStepX(), d1 + 0.22, d2 + 0.27 * (double)direction1.getStepZ(), 0.0, 0.005, 0.0
                );
            }
        }
    }
}
