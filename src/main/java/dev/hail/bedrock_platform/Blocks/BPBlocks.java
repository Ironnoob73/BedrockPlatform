package dev.hail.bedrock_platform.Blocks;

import dev.hail.bedrock_platform.Items.BPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static dev.hail.bedrock_platform.BedrockPlatform.MODID;

public class BPBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    static BlockBehaviour.StatePredicate block_never = (pState, pLevel, pPos) -> false;

    public static final DeferredBlock<Block> BEDROCK_PLATFORM = BLOCKS.registerSimpleBlock("bedrock_platform",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK));
    public static final DeferredItem<BlockItem> BEDROCK_PLATFORM_ITEM = BPItems.ITEMS.registerSimpleBlockItem("bedrock_platform", BEDROCK_PLATFORM, new Item.Properties().rarity(Rarity.UNCOMMON).fireResistant());

    public static final DeferredBlock<Block> LUMINOUS_BEDROCK_PLATFORM = BLOCKS.registerSimpleBlock("luminous_bedrock_platform",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.QUARTZ)
                    .lightLevel(l->15));
    public static final DeferredItem<BlockItem> LUMINOUS_BEDROCK_PLATFORM_ITEM = BPItems.ITEMS.registerSimpleBlockItem("luminous_bedrock_platform", LUMINOUS_BEDROCK_PLATFORM, new Item.Properties().rarity(Rarity.RARE).fireResistant());

    public static final DeferredBlock<Block> TWILL_BEDROCK_PLATFORM = BLOCKS.registerSimpleBlock("twill_bedrock_platform",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredItem<BlockItem> TWILL_BEDROCK_PLATFORM_ITEM = BPItems.ITEMS.registerSimpleBlockItem("twill_bedrock_platform", TWILL_BEDROCK_PLATFORM, new Item.Properties().rarity(Rarity.RARE).fireResistant());

    public static final DeferredBlock<Block> ORANGE_STRONG_INTERACTION_TILE = BLOCKS.registerSimpleBlock("orange_strong_interaction_tile",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_ORANGE));
    public static final DeferredItem<BlockItem> ORANGE_STRONG_INTERACTION_TILE_ITEM = BPItems.ITEMS.registerSimpleBlockItem("orange_strong_interaction_tile", ORANGE_STRONG_INTERACTION_TILE, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> CYAN_STRONG_INTERACTION_TILE = BLOCKS.registerSimpleBlock("cyan_strong_interaction_tile",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_CYAN));
    public static final DeferredItem<BlockItem> CYAN_STRONG_INTERACTION_TILE_ITEM = BPItems.ITEMS.registerSimpleBlockItem("cyan_strong_interaction_tile", CYAN_STRONG_INTERACTION_TILE, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> PURPLE_STRONG_INTERACTION_TILE = BLOCKS.registerSimpleBlock("purple_strong_interaction_tile",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_PURPLE));
    public static final DeferredItem<BlockItem> PURPLE_STRONG_INTERACTION_TILE_ITEM = BPItems.ITEMS.registerSimpleBlockItem("purple_strong_interaction_tile", PURPLE_STRONG_INTERACTION_TILE, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<SolidEndVoid> SOLID_END_VOID = BLOCKS.register("solid_end_void",()->new SolidEndVoid(
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.COLOR_BLACK)
                    .lightLevel(l->15)));
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

    public static final DeferredBlock<Block> GHAST_TEAR_GLASS = BLOCKS.register("ghast_tear_glass",()->new TransparentBlock(
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.GLASS)
                    .instrument(NoteBlockInstrument.HAT)
                    .mapColor(MapColor.COLOR_GRAY)
                    .noOcclusion()
                    .lightLevel(l->7)
                    .isValidSpawn(Blocks::never)
                    .isRedstoneConductor(block_never)
                    .isSuffocating(block_never)
                    .isViewBlocking(block_never)));
    public static final DeferredItem<BlockItem> GHAST_TEAR_GLASS_ITEM = BPItems.ITEMS.registerSimpleBlockItem("ghast_tear_glass", GHAST_TEAR_GLASS, new Item.Properties());

    public static final DeferredBlock<Block> ENCAPSULATED_END_PORTAL_FRAME = BLOCKS.register("encapsulated_end_portal_frame",()->new EncapsulatedEndPortalFrame(
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .sound(SoundType.GLASS)
                    .noOcclusion()));
    public static final DeferredItem<BlockItem> ENCAPSULATED_END_PORTAL_FRAME_ITEM = BPItems.ITEMS.registerSimpleBlockItem("encapsulated_end_portal_frame", ENCAPSULATED_END_PORTAL_FRAME, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> SCULK_RIB_BLOCK = BLOCKS.register("sculk_rib_block",()->new SculkRibBlock(
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .instrument(NoteBlockInstrument.XYLOPHONE)
                    .requiresCorrectToolForDrops()
                    .strength(55.0F, 1200.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.BONE_BLOCK)
                    .isValidSpawn(Blocks::never)));
    public static final DeferredItem<BlockItem> SCULK_RIB_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("sculk_rib_block", SCULK_RIB_BLOCK);

    public static final DeferredBlock<Block> RED_STRONG_INTERACTION_TILE = BLOCKS.registerSimpleBlock("red_strong_interaction_tile",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_RED));
    public static final DeferredItem<BlockItem> RED_STRONG_INTERACTION_TILE_ITEM = BPItems.ITEMS.registerSimpleBlockItem("red_strong_interaction_tile", RED_STRONG_INTERACTION_TILE, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> YELLOW_STRONG_INTERACTION_TILE = BLOCKS.registerSimpleBlock("yellow_strong_interaction_tile",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredItem<BlockItem> YELLOW_STRONG_INTERACTION_TILE_ITEM = BPItems.ITEMS.registerSimpleBlockItem("yellow_strong_interaction_tile", YELLOW_STRONG_INTERACTION_TILE, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> GREEN_STRONG_INTERACTION_TILE = BLOCKS.registerSimpleBlock("green_strong_interaction_tile",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_GREEN));
    public static final DeferredItem<BlockItem> GREEN_STRONG_INTERACTION_TILE_ITEM = BPItems.ITEMS.registerSimpleBlockItem("green_strong_interaction_tile", GREEN_STRONG_INTERACTION_TILE, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> BLUE_STRONG_INTERACTION_TILE = BLOCKS.registerSimpleBlock("blue_strong_interaction_tile",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_BLUE));
    public static final DeferredItem<BlockItem> BLUE_STRONG_INTERACTION_TILE_ITEM = BPItems.ITEMS.registerSimpleBlockItem("blue_strong_interaction_tile", BLUE_STRONG_INTERACTION_TILE, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> WHITE_STRONG_INTERACTION_TILE = BLOCKS.registerSimpleBlock("white_strong_interaction_tile",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_WHITE));
    public static final DeferredItem<BlockItem> WHITE_STRONG_INTERACTION_TILE_ITEM = BPItems.ITEMS.registerSimpleBlockItem("white_strong_interaction_tile", WHITE_STRONG_INTERACTION_TILE, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> GRAY_STRONG_INTERACTION_TILE = BLOCKS.registerSimpleBlock("gray_strong_interaction_tile",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_GRAY));
    public static final DeferredItem<BlockItem> GRAY_STRONG_INTERACTION_TILE_ITEM = BPItems.ITEMS.registerSimpleBlockItem("gray_strong_interaction_tile", GRAY_STRONG_INTERACTION_TILE, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> BLACK_STRONG_INTERACTION_TILE = BLOCKS.registerSimpleBlock("black_strong_interaction_tile",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_BLACK));
    public static final DeferredItem<BlockItem> BLACK_STRONG_INTERACTION_TILE_ITEM = BPItems.ITEMS.registerSimpleBlockItem("black_strong_interaction_tile", BLACK_STRONG_INTERACTION_TILE, new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredBlock<Block> RED_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("red_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_RED));
    public static final DeferredItem<BlockItem> RED_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("red_strong_interaction_block", RED_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> ORANGE_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("orange_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_ORANGE));
    public static final DeferredItem<BlockItem> ORANGE_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("orange_strong_interaction_block", ORANGE_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> YELLOW_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("yellow_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_YELLOW));
    public static final DeferredItem<BlockItem> YELLOW_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("yellow_strong_interaction_block", YELLOW_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> GREEN_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("green_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_GREEN));
    public static final DeferredItem<BlockItem> GREEN_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("green_strong_interaction_block", GREEN_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> CYAN_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("cyan_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_CYAN));
    public static final DeferredItem<BlockItem> CYAN_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("cyan_strong_interaction_block", CYAN_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> BLUE_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("blue_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_BLUE));
    public static final DeferredItem<BlockItem> BLUE_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("blue_strong_interaction_block", BLUE_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> PURPLE_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("purple_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_PURPLE));
    public static final DeferredItem<BlockItem> PURPLE_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("purple_strong_interaction_block", PURPLE_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> WHITE_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("white_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_WHITE));
    public static final DeferredItem<BlockItem> WHITE_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("white_strong_interaction_block", WHITE_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> GRAY_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("gray_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_GRAY));
    public static final DeferredItem<BlockItem> GRAY_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("gray_strong_interaction_block", GRAY_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> BLACK_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("black_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_BLACK));
    public static final DeferredItem<BlockItem> BLACK_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("black_strong_interaction_block", BLACK_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> RED_SLICK_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("red_slick_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_RED)
                    .friction(1F));
    public static final DeferredItem<BlockItem> RED_SLICK_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("red_slick_strong_interaction_block", RED_SLICK_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> ORANGE_SLICK_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("orange_slick_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_ORANGE)
                    .friction(1F));
    public static final DeferredItem<BlockItem> ORANGE_SLICK_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("orange_slick_strong_interaction_block", ORANGE_SLICK_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> YELLOW_SLICK_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("yellow_slick_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_YELLOW)
                    .friction(1F));
    public static final DeferredItem<BlockItem> YELLOW_SLICK_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("yellow_slick_strong_interaction_block", YELLOW_SLICK_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> GREEN_SLICK_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("green_slick_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_GREEN)
                    .friction(1F));
    public static final DeferredItem<BlockItem> GREEN_SLICK_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("green_slick_strong_interaction_block", GREEN_SLICK_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> CYAN_SLICK_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("cyan_slick_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_CYAN)
                    .friction(1F));
    public static final DeferredItem<BlockItem> CYAN_SLICK_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("cyan_slick_strong_interaction_block", CYAN_SLICK_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> BLUE_SLICK_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("blue_slick_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_BLUE)
                    .friction(1F));
    public static final DeferredItem<BlockItem> BLUE_SLICK_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("blue_slick_strong_interaction_block", BLUE_SLICK_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> PURPLE_SLICK_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("purple_slick_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_PURPLE)
                    .friction(1F));
    public static final DeferredItem<BlockItem> PURPLE_SLICK_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("purple_slick_strong_interaction_block", PURPLE_SLICK_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> WHITE_SLICK_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("white_slick_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .friction(1F));
    public static final DeferredItem<BlockItem> WHITE_SLICK_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("white_slick_strong_interaction_block", WHITE_SLICK_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> GRAY_SLICK_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("gray_slick_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_GRAY)
                    .friction(1F));
    public static final DeferredItem<BlockItem> GRAY_SLICK_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("gray_slick_strong_interaction_block", GRAY_SLICK_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> BLACK_SLICK_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("black_slick_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_BLACK)
                    .friction(1F));
    public static final DeferredItem<BlockItem> BLACK_SLICK_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("black_slick_strong_interaction_block", BLACK_SLICK_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> RED_GLOW_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("red_glow_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_RED)
                    .lightLevel(l->15));
    public static final DeferredItem<BlockItem> RED_GLOW_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("red_glow_strong_interaction_block", RED_GLOW_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> ORANGE_GLOW_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("orange_glow_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_ORANGE)
                    .lightLevel(l->15));
    public static final DeferredItem<BlockItem> ORANGE_GLOW_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("orange_glow_strong_interaction_block", ORANGE_GLOW_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> YELLOW_GLOW_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("yellow_glow_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_YELLOW)
                    .lightLevel(l->15));
    public static final DeferredItem<BlockItem> YELLOW_GLOW_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("yellow_glow_strong_interaction_block", YELLOW_GLOW_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> GREEN_GLOW_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("green_glow_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_GREEN)
                    .lightLevel(l->15));
    public static final DeferredItem<BlockItem> GREEN_GLOW_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("green_glow_strong_interaction_block", GREEN_GLOW_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> CYAN_GLOW_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("cyan_glow_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_CYAN)
                    .lightLevel(l->15));
    public static final DeferredItem<BlockItem> CYAN_GLOW_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("cyan_glow_strong_interaction_block", CYAN_GLOW_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> BLUE_GLOW_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("blue_glow_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_BLUE)
                    .lightLevel(l->15));
    public static final DeferredItem<BlockItem> BLUE_GLOW_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("blue_glow_strong_interaction_block", BLUE_GLOW_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> PURPLE_GLOW_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("purple_glow_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_PURPLE)
                    .lightLevel(l->15));
    public static final DeferredItem<BlockItem> PURPLE_GLOW_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("purple_glow_strong_interaction_block", PURPLE_GLOW_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> WHITE_GLOW_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("white_glow_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .lightLevel(l->15));
    public static final DeferredItem<BlockItem> WHITE_GLOW_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("white_glow_strong_interaction_block", WHITE_GLOW_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> GRAY_GLOW_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("gray_glow_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_GRAY)
                    .lightLevel(l->15));
    public static final DeferredItem<BlockItem> GRAY_GLOW_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("gray_glow_strong_interaction_block", GRAY_GLOW_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> BLACK_GLOW_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("black_glow_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_BLACK)
                    .lightLevel(l->15));
    public static final DeferredItem<BlockItem> BLACK_GLOW_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("black_glow_strong_interaction_block", BLACK_GLOW_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

    public static final DeferredBlock<Block> GRAY_TWILL_STRONG_INTERACTION_BLOCK = BLOCKS.registerSimpleBlock("gray_twill_strong_interaction_block",
            BlockBehaviour.Properties.ofLegacyCopy(Blocks.BEDROCK)
                    .mapColor(MapColor.TERRACOTTA_GRAY)
                    .friction(0.4F));
    public static final DeferredItem<BlockItem> GRAY_TWILL_STRONG_INTERACTION_BLOCK_ITEM = BPItems.ITEMS.registerSimpleBlockItem("gray_twill_strong_interaction_block", GRAY_TWILL_STRONG_INTERACTION_BLOCK, new Item.Properties().rarity(Rarity.UNCOMMON));

}
