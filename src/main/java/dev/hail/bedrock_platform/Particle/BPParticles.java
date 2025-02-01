package dev.hail.bedrock_platform.Particle;

import dev.hail.bedrock_platform.BedrockPlatform;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BPParticles{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, BedrockPlatform.MODID);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLOCK_EXCHANGE = PARTICLE_TYPES.register(
            "block_exchange",() -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLOCK_REDUCTION = PARTICLE_TYPES.register(
            "block_reduction",() -> new SimpleParticleType(false));
}
