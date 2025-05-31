package dev.hail.bedrock_platform.block.solid_end;

import dev.hail.bedrock_platform.block.BPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SolidEndVoidBE extends BlockEntity {
    public SolidEndVoidBE(BlockPos pos, BlockState state) {
        super(BPBlocks.SOLID_END_VOID_BE.get(), pos, state);
    }

    public boolean shouldRenderFace(Direction pFace) {
        return Block.shouldRenderFace(this.getBlockState(), this.level, this.getBlockPos(), pFace, this.getBlockPos().relative(pFace));
    }
}
