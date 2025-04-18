package dev.hail.bedrock_platform.Entities;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static dev.hail.bedrock_platform.BedrockPlatform.MODID;

public class BPEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, MODID);
    public static final DeferredHolder<EntityType<?>, EntityType<NetherBoat>> NETHER_BOAT = ENTITY_TYPES.register(
            "nether_boat",()-> EntityType.Builder.<NetherBoat>of(NetherBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .build("nether_boat")
    );
    public static final DeferredHolder<EntityType<?>, EntityType<NetherBoat.NetherChestBoat>> NETHER_CHEST_BOAT = ENTITY_TYPES.register(
            "nether_chest_boat",()-> EntityType.Builder.<NetherBoat.NetherChestBoat>of(NetherBoat.NetherChestBoat::new, MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .eyeHeight(0.5625F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .build("nether_chest_boat")
    );
}
