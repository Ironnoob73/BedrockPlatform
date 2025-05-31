package dev.hail.bedrock_platform.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.Optional;

public class BouncePadParticle extends TextureSheetParticle {
    private float rot;
    private float rotO;
    private float pitch;
    private float pitchO;

    protected BouncePadParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.quadSize = 0.3F;
    }

    @Override
    public void render(@NotNull VertexConsumer pBuffer, @NotNull Camera pRenderInfo, float pPartialTicks) {
        float f = Mth.sin(((float)this.age + pPartialTicks - (float) (Math.PI * 2)) * 0.05F) * 2.0F;
        float f1 = Mth.lerp(pPartialTicks, this.rotO, this.rot);
        float f2 = Mth.lerp(pPartialTicks, this.pitchO, this.pitch) + (float) (Math.PI / 2);
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotationY(f1).rotateX(-f2).rotateY(f);
        this.renderRotatedQuad(pBuffer, pRenderInfo, quaternionf, pPartialTicks);
        quaternionf.rotationY((float) -Math.PI + f1).rotateX(f2).rotateY(f);
        this.renderRotatedQuad(pBuffer, pRenderInfo, quaternionf, pPartialTicks);
    }

    @Override
    public int getLightColor(float pPartialTick) {
        return 240;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            Optional<Vec3> optional = Optional.of(new Vec3(xo + this.xd, yo + this.yd, zo + this.zd));
            if (optional.isEmpty()) {
                this.remove();
            } else {
                int i = this.lifetime - this.age;
                double d0 = 1.0 / (double)i;
                Vec3 vec3 = optional.get();
                this.x = Mth.lerp(d0, this.x, vec3.x());
                this.y = Mth.lerp(d0, this.y, vec3.y());
                this.z = Mth.lerp(d0, this.z, vec3.z());
                this.setPos(this.x, this.y, this.z); // FORGE: Update the particle's bounding box
                double d1 = this.x - vec3.x();
                double d2 = this.y - vec3.y();
                double d3 = this.z - vec3.z();
                this.rotO = this.rot;
                this.rot = (float)Mth.atan2(d1, d3);
                this.pitchO = this.pitch;
                this.pitch = (float)Mth.atan2(d2, Math.sqrt(d1 * d1 + d3 * d3));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed)
        {
            BouncePadParticle bouncePadParticle = new BouncePadParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
            bouncePadParticle.pickSprite(this.sprite);
            bouncePadParticle.setAlpha(1.0F);
            return bouncePadParticle;
        }
    }
}
