package dev.hail.bedrock_platform.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SolidEndVoid extends Block implements EntityBlock {
    // Constructor deferring to super.
    public SolidEndVoid(BlockBehaviour.Properties properties) {
        super(properties);
    }

    // Return a new instance of our block entity here.
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SolidEndVoidBE(pos, state);
    }
    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }
}
