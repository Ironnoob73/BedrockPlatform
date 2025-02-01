package dev.hail.bedrock_platform.Blocks;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Items.BPItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static dev.hail.bedrock_platform.BedrockPlatform.MODID;

public class BPBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);

    public static final DeferredBlock<Block> BEDROCK_PLATFORM = BLOCKS.registerSimpleBlock("bedrock_platform",
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .isValidSpawn(Blocks::never));
    public static final DeferredItem<BlockItem> BEDROCK_PLATFORM_ITEM = BPItems.ITEMS.registerSimpleBlockItem("bedrock_platform", BEDROCK_PLATFORM, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> LUMINOUS_BEDROCK_PLATFORM = BLOCKS.registerSimpleBlock("luminous_bedrock_platform",
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .strength(-1.0F, 3600000.0F)
                    .lightLevel(l->15)
                    .isValidSpawn(Blocks::never));
    public static final DeferredItem<BlockItem> LUMINOUS_BEDROCK_PLATFORM_ITEM = BPItems.ITEMS.registerSimpleBlockItem("luminous_bedrock_platform", LUMINOUS_BEDROCK_PLATFORM, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> TWILL_BEDROCK_PLATFORM = BLOCKS.registerSimpleBlock("twill_bedrock_platform",
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_YELLOW)
                    .strength(-1.0F, 3600000.0F)
                    .isValidSpawn(Blocks::never));
    public static final DeferredItem<BlockItem> TWILL_BEDROCK_PLATFORM_ITEM = BPItems.ITEMS.registerSimpleBlockItem("twill_bedrock_platform", TWILL_BEDROCK_PLATFORM, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<SolidEndVoid> SOLID_END_VOID = BLOCKS.register("solid_end_void",()->new SolidEndVoid(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(-1.0F, 3600000.0F)
                    .isValidSpawn(Blocks::never))
            );
    public static final DeferredItem<BlockItem> SOLID_END_VOID_ITEM = BPItems.ITEMS.registerSimpleBlockItem("solid_end_void", SOLID_END_VOID, new Item.Properties().rarity(Rarity.EPIC));
    public static final Supplier<BlockEntityType<SolidEndVoidBE>> SOLID_END_VOID_BE = BLOCK_ENTITY_TYPES.register(
            "my_block_entity",
            // The block entity type, created using a builder.
            () -> BlockEntityType.Builder.of(
                            // The supplier to use for constructing the block entity instances.
                            SolidEndVoidBE::new,
                            // A vararg of blocks that can have this block entity.
                            // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                            SOLID_END_VOID.get()
                    )
                    // Build using null; vanilla does some datafixer shenanigans with the parameter that we don't need.
                    .build(null)
    );
}
