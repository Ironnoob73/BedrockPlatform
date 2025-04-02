package dev.hail.bedrock_platform.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class KelpBlock extends Block {
    public KelpBlock(Properties properties) {
        super(properties);
    }
    @Override
    protected void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pLevel.dimensionType().ultraWarm()) {
            pLevel.setBlock(pPos, Blocks.DRIED_KELP_BLOCK.defaultBlockState(), 3);
            pLevel.levelEvent(2009, pPos, 0);
            pLevel.playSound(null, pPos, SoundEvents.WET_SPONGE_DRIES, SoundSource.BLOCKS, 1.0F, (1.0F + pLevel.getRandom().nextFloat() * 0.2F) * 0.7F);
        }
    }
}
