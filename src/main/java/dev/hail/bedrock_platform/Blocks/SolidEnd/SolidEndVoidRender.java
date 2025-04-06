package dev.hail.bedrock_platform.Blocks.SolidEnd;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.hail.bedrock_platform.Config;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class SolidEndVoidRender implements BlockEntityRenderer<SolidEndVoidBE> {
    @Override
    public void render(@NotNull SolidEndVoidBE blockEntity, float partialTick, PoseStack stack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Matrix4f matrix4f = stack.last().pose();
        if (Config.solidVoidRenderEndPortal)
            this.renderCube(blockEntity, matrix4f, bufferSource.getBuffer(this.renderType()));
    }

    private void renderCube(SolidEndVoidBE pBlockEntity, Matrix4f pPose, VertexConsumer pConsumer) {
        float f = this.getOffsetDown();
        float f1 = this.getOffsetUp();
        this.renderFace(pBlockEntity, pPose, pConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
        this.renderFace(pBlockEntity, pPose, pConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
        this.renderFace(pBlockEntity, pPose, pConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
        this.renderFace(pBlockEntity, pPose, pConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
        this.renderFace(pBlockEntity, pPose, pConsumer, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
        this.renderFace(pBlockEntity, pPose, pConsumer, 0.0F, 1.0F, f1, f1, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
    }
    private void renderFace(
            SolidEndVoidBE pBlockEntity,
            Matrix4f pPose,
            VertexConsumer pConsumer,
            float pX0,
            float pX1,
            float pY0,
            float pY1,
            float pZ0,
            float pZ1,
            float pZ2,
            float pZ3,
            Direction pDirection
    ) {
        if (pBlockEntity.shouldRenderFace(pDirection)) {
            pConsumer.addVertex(pPose, pX0, pY0, pZ0);
            pConsumer.addVertex(pPose, pX1, pY0, pZ1);
            pConsumer.addVertex(pPose, pX1, pY1, pZ2);
            pConsumer.addVertex(pPose, pX0, pY1, pZ3);
        }
    }
    protected float getOffsetUp() {
        return 1.0F;
    }
    protected float getOffsetDown() {
        return 0.0F;
    }
    protected RenderType renderType() {
        return RenderType.endPortal();
    }
}
