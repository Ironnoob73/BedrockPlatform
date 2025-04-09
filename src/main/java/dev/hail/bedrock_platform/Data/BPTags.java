package dev.hail.bedrock_platform.Data;

import dev.hail.bedrock_platform.BedrockPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BPTags {
    public static final TagKey<Block> OBSIDIAN_WRENCH_CAN_REMOVE = TagKey.create(
            Registries.BLOCK,
            BedrockPlatform.modResLocation("obsidian_wrench_can_remove")
    );
}
