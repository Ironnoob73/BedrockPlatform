package dev.hail.bedrock_platform.Blocks.SolidEnd;

import dev.hail.bedrock_platform.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SolidEndVoid extends Block implements EntityBlock {
    // Constructor deferring to super.
    public SolidEndVoid(BlockBehaviour.Properties properties) {
        super(properties);
    }

    // Return a new instance of our block entity here.
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new SolidEndVoidBE(pos, state);
    }
    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        if (Config.solidVoidRenderEndPortal)
            return RenderShape.INVISIBLE;
        else
            return RenderShape.MODEL;
    }
}
