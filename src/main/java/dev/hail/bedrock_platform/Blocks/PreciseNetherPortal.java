package dev.hail.bedrock_platform.Blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PreciseNetherPortal extends Block implements SimpleWaterloggedBlock, Portal {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE_X = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
    protected static final VoxelShape SHAPE_Z = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
    public PreciseNetherPortal(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AXIS, Direction.Axis.X)
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(WATERLOGGED, Boolean.FALSE));
    }
    // Base
    @Override
    protected @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        if (pState.getValue(AXIS) == Direction.Axis.Z) {
            return SHAPE_Z;
        }
        return SHAPE_X;
    }
    @Override
    protected @NotNull BlockState updateShape(BlockState pState, Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (pFacing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (pFacing == Direction.UP)) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos)
                    ? Blocks.AIR.defaultBlockState()
                    : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        } else {
            return pFacingState.getBlock() instanceof PreciseNetherPortal && pFacingState.getValue(HALF) != doubleblockhalf
                    ? pFacingState.setValue(HALF, doubleblockhalf)
                    : Blocks.AIR.defaultBlockState();
        }
    }
    @Override
    public @NotNull BlockState playerWillDestroy(Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull Player pPlayer) {
        if (!pLevel.isClientSide && (pPlayer.isCreative() || !pPlayer.hasCorrectToolForDrops(pState, pLevel, pPos))) {
            DoublePlantBlock.preventDropFromBottomPart(pLevel, pPos, pState, pPlayer);
        }
        return super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }
    @Override
    protected boolean isPathfindable(@NotNull BlockState pState, @NotNull PathComputationType pPathComputationType) {
        return false;
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        LevelAccessor levelaccessor = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(pContext)) {
            return this.defaultBlockState()
                    .setValue(AXIS, pContext.getHorizontalDirection().getClockWise().getAxis())
                    .setValue(HALF, DoubleBlockHalf.LOWER)
                    .setValue(WATERLOGGED, levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER);
        } else {
            return null;
        }
    }
    @Override
    protected @NotNull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, @NotNull ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }
    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return pState.getValue(HALF) == DoubleBlockHalf.LOWER || blockstate.is(this);
    }
    @Override
    protected @NotNull BlockState rotate(@NotNull BlockState pState, Rotation pRot) {
        switch (pRot) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> {
                return switch (pState.getValue(AXIS)) {
                    case Z -> pState.setValue(AXIS, Direction.Axis.X);
                    case X -> pState.setValue(AXIS, Direction.Axis.Z);
                    default -> pState;
                };
            }
            default -> {
                return pState;
            }
        }
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF, AXIS, WATERLOGGED);
    }
    // Particle & Sound
    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(100) == 0) {
            pLevel.playLocalSound(
                    (double)pPos.getX() + 0.5,
                    (double)pPos.getY() + 0.5,
                    (double)pPos.getZ() + 0.5,
                    SoundEvents.PORTAL_AMBIENT,
                    SoundSource.BLOCKS,
                    0.5F,
                    pRandom.nextFloat() * 0.4F + 0.8F,
                    false
            );
        }
        for (int i = 0; i < 4; i++) {
            double d0 = (double)pPos.getX() + pRandom.nextDouble();
            double d1 = (double)pPos.getY() + pRandom.nextDouble();
            double d2 = (double)pPos.getZ() + pRandom.nextDouble();
            double d3 = ((double)pRandom.nextFloat() - 0.5) * 0.5;
            double d4 = ((double)pRandom.nextFloat() - 0.5) * 0.5;
            double d5 = ((double)pRandom.nextFloat() - 0.5) * 0.5;
            int j = pRandom.nextInt(2) * 2 - 1;
            if (!pLevel.getBlockState(pPos.west()).is(this) && !pLevel.getBlockState(pPos.east()).is(this)) {
                d0 = (double)pPos.getX() + 0.5 + 0.25 * (double)j;
                d3 = pRandom.nextFloat() * 2.0F * (float)j;
            } else {
                d2 = (double)pPos.getZ() + 0.5 + 0.25 * (double)j;
                d5 = pRandom.nextFloat() * 2.0F * (float)j;
            }

            pLevel.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }
    }
    // Logic
    @Override
    public int getPortalTransitionTime(@NotNull ServerLevel pLevel, @NotNull Entity pEntity) {
        return pEntity instanceof Player player
                ? Math.max(
                1,
                pLevel.getGameRules()
                        .getInt(
                                player.getAbilities().invulnerable
                                        ? GameRules.RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY
                                        : GameRules.RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY
                        )
        )
                : 0;
    }
    @Override
    protected void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Entity pEntity) {
        if (pEntity.canUsePortal(false)) {
            if (pEntity instanceof ServerPlayer player && pEntity.portalProcess != null && !pEntity.isOnPortalCooldown()){
                player.sendSystemMessage(
                        Component.translatable("block.bedrock_platform.precise_nether_portal.process",
                                        pLevel.getGameRules().getInt(player.getAbilities().invulnerable ?
                                                        GameRules.RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY :
                                                        GameRules.RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY
                                        ) - pEntity.portalProcess.getPortalTime())
                                .withStyle(ChatFormatting.LIGHT_PURPLE), true);
            }
            pEntity.setAsInsidePortal(this, pPos);
        }
    }
    @org.jetbrains.annotations.Nullable
    @Override
    public DimensionTransition getPortalDestination(@NotNull ServerLevel pLevel, @NotNull Entity pEntity, @NotNull BlockPos pPos) {
        ResourceKey<Level> resourcekey = pLevel.dimension() == Level.NETHER ? Level.OVERWORLD : pLevel.dimension() == Level.OVERWORLD ? Level.NETHER : null;
        if (resourcekey == null){
            if (pEntity instanceof ServerPlayer){
                ((ServerPlayer)pEntity).sendSystemMessage(Component.translatable("block.bedrock_platform.precise_nether_portal.invalid").withStyle(ChatFormatting.RED), true);
            }
            return null;
        }
        ServerLevel serverlevel = pLevel.getServer().getLevel(resourcekey);
        if (serverlevel == null) {
            if (pEntity instanceof ServerPlayer){
                ((ServerPlayer)pEntity).sendSystemMessage(Component.translatable("block.bedrock_platform.precise_nether_portal.invalid").withStyle(ChatFormatting.RED), true);
            }
            return null;
        } else {
            boolean flag = serverlevel.dimension() == Level.NETHER;
            WorldBorder worldborder = serverlevel.getWorldBorder();
            double d0 = DimensionType.getTeleportationScale(pLevel.dimensionType(), serverlevel.dimensionType());
            BlockPos blockpos = worldborder.clampToBounds(pPos.getX() * d0, pPos.getY(), pPos.getZ() * d0);
            return this.getExitPortal(serverlevel, pEntity, pPos, blockpos, flag, worldborder);
        }
    }
    @Nullable
    private DimensionTransition getExitPortal(ServerLevel pLevel, Entity pEntity, BlockPos pPos, BlockPos pExitPos, boolean pIsNether, WorldBorder pWorldBorder) {
        DimensionTransition.PostDimensionTransition dimensiontransition$postdimensiontransition;
        ServerLevel targetLevel = pLevel.getServer().getLevel(pIsNether ? Level.OVERWORLD : Level.NETHER);
        if (targetLevel == null) {
            if (pEntity instanceof ServerPlayer){
                ((ServerPlayer)pEntity).sendSystemMessage(Component.translatable("block.bedrock_platform.precise_nether_portal.invalid_target").withStyle(ChatFormatting.RED), true);
            }
            return null;
        } else if(!pWorldBorder.isWithinBounds(pExitPos)){
            if (pEntity instanceof ServerPlayer){
                ((ServerPlayer)pEntity).sendSystemMessage(Component.translatable("block.bedrock_platform.precise_nether_portal.out_border").withStyle(ChatFormatting.RED), true);
            }
            return null;
        } else {
            if (targetLevel.getBlockState(pPos) == pLevel.getBlockState(pExitPos) || (pEntity instanceof Player && ((Player) pEntity).isCreative())){
                dimensiontransition$postdimensiontransition = DimensionTransition.PLAY_PORTAL_SOUND.then(DimensionTransition.PLACE_PORTAL_TICKET);
            } else {
                if (pEntity instanceof ServerPlayer){
                    ((ServerPlayer)pEntity).sendSystemMessage(Component.translatable("block.bedrock_platform.precise_nether_portal.cant_use").withStyle(ChatFormatting.RED), true);
                }
                return null;
            }
        }
        return new DimensionTransition(pLevel, pExitPos.getBottomCenter(), pEntity.getDeltaMovement(), pEntity.getYRot(), pEntity.getXRot(), dimensiontransition$postdimensiontransition);
    }
}
