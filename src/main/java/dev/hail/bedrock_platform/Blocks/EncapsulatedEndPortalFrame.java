package dev.hail.bedrock_platform.Blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class EncapsulatedEndPortalFrame extends HorizontalDirectionalBlock {
    public static final MapCodec<EncapsulatedEndPortalFrame> CODEC = simpleCodec(EncapsulatedEndPortalFrame::new);

    @Override
    public MapCodec<EncapsulatedEndPortalFrame> codec() {
        return CODEC;
    }

    public EncapsulatedEndPortalFrame(BlockBehaviour.Properties p_53677_) {
        super(p_53677_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
}
