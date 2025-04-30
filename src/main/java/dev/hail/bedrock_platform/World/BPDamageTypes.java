package dev.hail.bedrock_platform.World;

import dev.hail.bedrock_platform.BedrockPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class BPDamageTypes {
    public static final ResourceKey<DamageType> STRONG_INTERACTION_EXPLOSION =
            ResourceKey.create(Registries.DAMAGE_TYPE, BedrockPlatform.modResLocation("strong_interaction_explosion"));
}
