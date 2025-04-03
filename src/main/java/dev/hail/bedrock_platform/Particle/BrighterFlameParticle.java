package dev.hail.bedrock_platform.Particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BrighterFlameParticle extends FlameParticle {
    protected BrighterFlameParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;
        private final boolean deepSlate;

        public Provider(SpriteSet pSprites, boolean deepSlate) {
            this.sprite = pSprites;
            this.deepSlate = deepSlate;
        }

        public Particle createParticle(
                @NotNull SimpleParticleType pType,
                @NotNull ClientLevel pLevel,
                double pX,
                double pY,
                double pZ,
                double pXSpeed,
                double pYSpeed,
                double pZSpeed
        ) {
            FlameParticle flameparticle = new BrighterFlameParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
            flameparticle.pickSprite(this.sprite);
            if (!deepSlate){
                flameparticle.setColor(1.0F, 0.9F, 0.7F);
            }
            return flameparticle;
        }
    }
}
