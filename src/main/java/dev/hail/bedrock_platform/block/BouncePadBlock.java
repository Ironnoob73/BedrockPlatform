package dev.hail.bedrock_platform.block;

import dev.hail.bedrock_platform.BedrockPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class BouncePadBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public BouncePadBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(WATERLOGGED, false));
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        switch (pState.getValue(FACING)){
            case DOWN ->{
                return Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
            }
            case NORTH ->{
                return Block.box(0.0, 0.0, 12.0, 16.0, 16.0, 16.0);
            }
            case SOUTH ->{
                return Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 4.0);
            }
            case WEST ->{
                return Block.box(12.0, 0.0, 0.0, 16.0, 16.0, 16.0);
            }
            case EAST ->{
                return Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0);
            }
        }
        return Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
    }
    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Entity pEntity) {
        BedrockPlatform.LOGGER.debug("INSIDE");
        if (!(pEntity instanceof LivingEntity livingEntity && livingEntity.isSteppingCarefully())) {
            pEntity.setDeltaMovement(pEntity.getDeltaMovement().add(getFaceDir(pState.getValue(FACING))));
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }
    public Vec3 getFaceDir(Direction dir){
        switch (dir){
            case DOWN -> {
                return new Vec3(0,-1,0);
            }
            case NORTH -> {
                return new Vec3(0,0,-1);
            }
            case SOUTH -> {
                return new Vec3(0,0,1);
            }
            case WEST -> {
                return new Vec3(-1,0,0);
            }
            case EAST -> {
                return new Vec3(1,0,0);
            }
        }
        return new Vec3(0,1,0);
    }
    @Override
    public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Entity entity, float fallDistance) {
        super.fallOn(level, state, pos, entity, 0);
    }
    @Override
    protected @NotNull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING,WATERLOGGED);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        return this.defaultBlockState()
                .setValue(WATERLOGGED, levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER)
                .setValue(FACING, pContext.getNearestLookingDirection().getOpposite());
    }
    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        Vec3 dir = getFaceDir(pState.getValue(FACING));
        double d0 = (double)pPos.getX() + 0.5 - dir.x * 0.5;
        double d1 = (double)pPos.getY() + 0.5 - dir.y * 0.5;
        double d2 = (double)pPos.getZ() + 0.5 - dir.z * 0.5;
        DustParticleOptions force = new DustParticleOptions(new Vector3f(1,1,0), 1);
        pLevel.addParticle(force, d0, d1, d2, dir.x * 100, dir.y * 100, dir.z * 100);
    }
}
