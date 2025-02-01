package dev.hail.bedrock_platform.Particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockExchangeParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;
    public BlockExchangeParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.quadSize = 1.0F;
        this.spriteSet = spriteSet;
        this.lifetime = 30;
        this.gravity = 0;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public float getQuadSize(float pScaleFactor) {
        return this.quadSize * Mth.clamp(((float)this.age + pScaleFactor) / (float)this.lifetime * 0.75F, 0.0F, 1.0F) + 0.1f;
    }
    @Override
    public int getLightColor(float pPartialTick) {
        return 240;
    }
    @Override
    public void tick() {
        this.alpha -= 0.025F;
        this.setSpriteFromAge(spriteSet);
        super.tick();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
    @OnlyIn(Dist.CLIENT)
    public static class BlockExchangeParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public BlockExchangeParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            BlockExchangeParticle particle =  new BlockExchangeParticle(level, x, y, z, spriteSet);
            particle.setColor(0.0F, 1.0F, 0.5F);
            return particle;
        }
    }
    @OnlyIn(Dist.CLIENT)
    public static class BlockReductionParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public BlockReductionParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            BlockExchangeParticle particle =  new BlockExchangeParticle(level, x, y, z, spriteSet);
            particle.setColor(1.0F, 0.0F, 0.5F);
            return particle;
        }
    }
}
