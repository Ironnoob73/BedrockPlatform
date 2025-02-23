package dev.hail.bedrock_platform.Blocks;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SculkRibBlock extends Block {
    private static final VoxelShape OUTER_SHAPE = Shapes.block();
    private static final VoxelShape SHAPES = Shapes.join(OUTER_SHAPE, Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0), BooleanOp.ONLY_FIRST);

    public SculkRibBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES;
    }
    @Override
    protected VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES;
    }
}
