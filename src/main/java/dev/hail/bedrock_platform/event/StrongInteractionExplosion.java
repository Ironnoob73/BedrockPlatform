package dev.hail.bedrock_platform.event;

import com.google.common.collect.Sets;
import dev.hail.bedrock_platform.block.BPBlocks;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StrongInteractionExplosion extends Explosion {

    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new ExplosionDamageCalculator();
    private final boolean fire;
    private final RandomSource random = RandomSource.create();
    private final Level level;
    private final double x;
    private final double y;
    private final double z;
    @Nullable
    private final Entity source;
    private final float radius;
    private final ExplosionDamageCalculator damageCalculator;
    private final ParticleOptions smallExplosionParticles;
    private final ParticleOptions largeExplosionParticles;
    private final ObjectArrayList<BlockPos> toBlow = new ObjectArrayList<>();

    public StrongInteractionExplosion(
            Level pLevel,
            @Nullable Entity pSource,
            DamageSource pDamageSource,
            ExplosionDamageCalculator pDamageCalculator,
            double pX, double pY, double pZ, float pRadius, boolean pFire,
            StrongInteractionExplosion.BlockInteraction pBlockInteraction,
            ParticleOptions pSmallExplosionParticles,
            ParticleOptions pLargeExplosionParticles,
            Holder<SoundEvent> pExplosionSound)
    {
        super(pLevel,pSource,pDamageSource,pDamageCalculator,pX,pY,pZ,pRadius,pFire,pBlockInteraction,pSmallExplosionParticles,pLargeExplosionParticles,pExplosionSound);
        this.level = pLevel;
        this.source = pSource;
        this.radius = pRadius;
        this.x = pX;
        this.y = pY;
        this.z = pZ;
        this.fire = pFire;
        this.damageCalculator = pDamageCalculator == null ? this.makeDamageCalculator(pSource) : pDamageCalculator;
        this.smallExplosionParticles = pSmallExplosionParticles;
        this.largeExplosionParticles = pLargeExplosionParticles;
    }

    private ExplosionDamageCalculator makeDamageCalculator(@Nullable Entity pEntity) {
        return pEntity == null ? EXPLOSION_DAMAGE_CALCULATOR : new EntityBasedExplosionDamageCalculator(pEntity);
    }
    @Override
    public void explode() {
        Set<BlockPos> set = Sets.newHashSet();

        for (int j = 0; j < 16; j++) {
            for (int k = 0; k < 16; k++) {
                for (int l = 0; l < 16; l++) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        double d0 = (float)j / 15.0F * 2.0F - 1.0F;
                        double d1 = (float)k / 15.0F * 2.0F - 1.0F;
                        double d2 = (float)l / 15.0F * 2.0F - 1.0F;
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f = this.radius * random.nextFloat() * random.nextFloat() * 2;
                        double d4 = this.x;
                        double d6 = this.y;
                        double d8 = this.z;

                        for (; f > 0.0F; f -= 0.22500001F) {
                            BlockPos blockpos = BlockPos.containing(d4, d6, d8);
                            BlockState blockstate = this.level.getBlockState(blockpos);
                            FluidState fluidstate = this.level.getFluidState(blockpos);
                            if (!this.level.isInWorldBounds(blockpos)) {
                                break;
                            }

                            Optional<Float> optional = this.damageCalculator.getBlockExplosionResistance(this, this.level, blockpos, blockstate, fluidstate);
                            if (optional.isPresent()) {
                                f -= (optional.get() + 0.3F) * 0.3F;
                            }

                            if (f > 0.0F && this.damageCalculator.shouldBlockExplode(this, this.level, blockpos, blockstate, f)) {
                                set.add(blockpos);
                            }

                            d4 += d0 * 0.3F;
                            d6 += d1 * 0.3F;
                            d8 += d2 * 0.3F;
                        }
                    }
                }
            }
        }

        this.toBlow.addAll(set);
        float f2 = this.radius * 2.0F;
        int k1 = Mth.floor(this.x - (double)f2 - 1.0);
        int l1 = Mth.floor(this.x + (double)f2 + 1.0);
        int i2 = Mth.floor(this.y - (double)f2 - 1.0);
        int i1 = Mth.floor(this.y + (double)f2 + 1.0);
        int j2 = Mth.floor(this.z - (double)f2 - 1.0);
        int j1 = Mth.floor(this.z + (double)f2 + 1.0);
        List<Entity> list = this.level.getEntities(this.source, new AABB(k1, i2, j2, l1, i1, j1));
        net.neoforged.neoforge.event.EventHooks.onExplosionDetonate(this.level, this, list, f2);
    }
    @Override
    public void finalizeExplosion(boolean pSpawnParticles) {
        boolean flag = this.interactsWithBlocks();
        if (pSpawnParticles) {
            ParticleOptions particleoptions;
            if (!(this.radius < 2.0F) && flag) {
                particleoptions = this.largeExplosionParticles;
            } else {
                particleoptions = this.smallExplosionParticles;
            }

            this.level.addParticle(particleoptions, this.x, this.y, this.z, 1.0, 0.0, 0.0);
        }

        if (flag) {
            this.level.getProfiler().push("explosion_blocks");
            Util.shuffle(this.toBlow, this.level.random);

            for (BlockPos blockpos : this.toBlow) {
                onExplosionHit(blockpos, this.level.getBlockState(blockpos));
            }

            this.level.getProfiler().pop();
        }

        if (this.fire) {
            for (BlockPos blockpos1 : this.toBlow) {
                if (this.random.nextInt(3) == 0
                        && this.level.getBlockState(blockpos1).isAir()
                        && this.level.getBlockState(blockpos1.below()).isSolidRender(this.level, blockpos1.below())) {
                    this.level.setBlockAndUpdate(blockpos1, BaseFireBlock.getState(this.level, blockpos1));
                }
            }
        }
    }

    public void onExplosionHit(BlockPos pPos,BlockState blockState) {
        onExplosionHit(blockState, pPos);
    }
    protected void onExplosionHit(BlockState pState, BlockPos pPos) {
        if (pState.isAir()) {
            level.setBlock(pPos, BPBlocks.BLACK_SI_BLOCK_SET.getBaseBlock().get().defaultBlockState(), 3);
        }
    }
}
