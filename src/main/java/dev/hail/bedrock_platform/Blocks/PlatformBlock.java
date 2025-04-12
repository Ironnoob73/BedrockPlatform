package dev.hail.bedrock_platform.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlatformBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape TOP_SHAPE = Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape HALF_SHAPE_X = Block.box(0.0, 6.0, 0.0, 16.0, 8.0, 8.0);
    protected static final VoxelShape HALF_SHAPE_Z = Block.box(0.0, 6.0, 0.0, 8.0, 8.0, 16.0);
    public PlatformBlock(Properties properties) {
        super(properties);
        this.stateDefinition
                .any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, Half.TOP)
                .setValue(WATERLOGGED, Boolean.FALSE);
    }
    @Override
    protected boolean useShapeForLightOcclusion(@NotNull BlockState pState) {
        return true;
    }
    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        if (pState.getValue(HALF) != Half.TOP){
            switch (pState.getValue(FACING)){
                case NORTH -> {
                    return Shapes.or(HALF_SHAPE_X.move(0,0,0.5),HALF_SHAPE_X.move(0,0.5,0));
                }
                case SOUTH -> {
                    return Shapes.or(HALF_SHAPE_X,HALF_SHAPE_X.move(0,0.5,0.5));
                }
                case WEST -> {
                    return Shapes.or(HALF_SHAPE_Z.move(0.5,0,0),HALF_SHAPE_Z.move(0,0.5,0));
                }
                case EAST -> {
                    return Shapes.or(HALF_SHAPE_Z,HALF_SHAPE_Z.move(0.5,0.5,0));
                }
            }
        }
        return TOP_SHAPE;
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getClickedFace();
        BlockPos blockpos = pContext.getClickedPos();
        FluidState fluidstate = pContext.getLevel().getFluidState(blockpos);
        Direction horizontailDirection = pContext.getHorizontalDirection();
        if (pContext.getPlayer() != null && pContext.getPlayer().getXRot() >= 45){
            horizontailDirection = horizontailDirection.getOpposite();
        }
        return this.defaultBlockState()
                .setValue(FACING, horizontailDirection)
                .setValue(
                        HALF,
                        direction != Direction.DOWN && (direction == Direction.UP || !(pContext.getClickLocation().y - (double)blockpos.getY() > 0.5))
                                ? Half.BOTTOM
                                : Half.TOP
                )
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }
    @Override
    protected @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }


    @Override
    protected @NotNull BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }
    @Override
    protected @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        Direction direction = pState.getValue(FACING);
        switch (pMirror) {
            case LEFT_RIGHT -> {
                if (direction.getAxis() == Direction.Axis.Z) {
                    return pState.rotate(Rotation.CLOCKWISE_180);
                }
            }
            case FRONT_BACK -> {
                if (direction.getAxis() == Direction.Axis.X) {
                    return pState.rotate(Rotation.CLOCKWISE_180);
                }
            }
        }
        return super.mirror(pState, pMirror);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HALF, WATERLOGGED);
    }
    @Override
    protected @NotNull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
    @Override
    protected boolean isPathfindable(@NotNull BlockState pState, @NotNull PathComputationType pPathComputationType) {
        return false;
    }
}
