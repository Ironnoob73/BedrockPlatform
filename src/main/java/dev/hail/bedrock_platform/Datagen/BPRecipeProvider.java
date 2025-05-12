package dev.hail.bedrock_platform.Datagen;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Blocks.*;
import dev.hail.bedrock_platform.Blocks.Light.Amethyst.AmethystLanternBlock;
import dev.hail.bedrock_platform.Items.BPItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class BPRecipeProvider extends RecipeProvider {
    // TAGS
    public static final TagKey<Item> COAL_TAG = TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("coals"));
    public static final TagKey<Item> COBBLED_DEEPSLATE_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","cobblestones/deepslate"));
    public static final TagKey<Item> COBBLESTONE_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","cobblestones/normal"));
    public static final TagKey<Item> COLORLESS_GLASS_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","glass_blocks/colorless"));
    public static final TagKey<Item> AMETHYST_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","gems/amethyst"));
    public static final TagKey<Item> STONE_PLATFORM_MATERIAL_2_TAG = TagKey.create(Registries.ITEM, BedrockPlatform.modResLocation("stone_platform_materials_2"));
    public static final TagKey<Item> STONE_PLATFORM_MATERIAL_TAG = TagKey.create(Registries.ITEM, BedrockPlatform.modResLocation("stone_platform_materials"));
    public static final TagKey<Item> WOODEN_CHEST_TAG = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c","chests/wooden"));
    public static final TagKey<Item> GEODE_BRICKS_TAG = TagKey.create(Registries.ITEM, BedrockPlatform.modResLocation("geode_bricks"));
    public static final TagKey<Item> GEODE_WHITE_BRICKS_TAG = TagKey.create(Registries.ITEM, BedrockPlatform.modResLocation("geode_bricks/white_bricks"));
    public static final TagKey<Item> GEODE_WHITE_BRICK_SLABS_TAG = TagKey.create(Registries.ITEM, BedrockPlatform.modResLocation("geode_bricks/white_brick_slabs"));
    public static final TagKey<Item> GEODE_BLACK_BRICKS_TAG = TagKey.create(Registries.ITEM, BedrockPlatform.modResLocation("geode_bricks/black_bricks"));
    public static final TagKey<Item> GEODE_BLACK_BRICK_SLABS_TAG = TagKey.create(Registries.ITEM, BedrockPlatform.modResLocation("geode_bricks/black_brick_slabs"));
    public static final TagKey<Item> GEODE_GRAY_BRICKS_TAG = TagKey.create(Registries.ITEM, BedrockPlatform.modResLocation("geode_bricks/gray_bricks"));
    public static final TagKey<Item> GEODE_GRAY_BRICK_SLABS_TAG = TagKey.create(Registries.ITEM, BedrockPlatform.modResLocation("geode_bricks/gray_brick_slabs"));
    public static final TagKey<Item> GEODE_BLUE_BRICKS_TAG = TagKey.create(Registries.ITEM, BedrockPlatform.modResLocation("geode_bricks/blue_bricks"));
    public static final TagKey<Item> GEODE_BLUE_BRICK_SLABS_TAG = TagKey.create(Registries.ITEM, BedrockPlatform.modResLocation("geode_bricks/blue_brick_slabs"));

    public BPRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }
    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        genBothRecipe(Blocks.BEDROCK, Items.NETHERITE_SCRAP, Items.NETHERITE_SCRAP, BPBlocks.BEDROCK_PLATFORM.get(), output);
        genBothRecipe(BPBlocks.BEDROCK_PLATFORM.get(), Items.GOLD_INGOT, Items.GOLD_INGOT, BPBlocks.TWILL_BEDROCK_PLATFORM.get(), output);
        genBothRecipe(BPBlocks.BEDROCK_PLATFORM.get(), Items.NETHER_STAR, Items.NETHER_STAR, BPBlocks.LUMINOUS_BEDROCK_PLATFORM.get(), output);
        genBothRecipe(Blocks.END_PORTAL, BPItems.ENCHANT_DUST.get(), BPItems.ENCHANT_DUST, BPBlocks.SOLID_END_VOID.get(), output);
        genBothRecipe(Blocks.GLASS, Items.GHAST_TEAR, Items.GHAST_TEAR, BPBlocks.GHAST_TEAR_GLASS.get(), output);
        genBothRecipeWithModPath("reinforced_deepslate","reinforced_deepslate_reduction",
                Blocks.DEEPSLATE.defaultBlockState(), BPItems.SCULK_RIB.get(), BPItems.SCULK_RIB.get(), Blocks.REINFORCED_DEEPSLATE.defaultBlockState(), output);
        genBothRecipe(BPBlocks.SCULK_RIB_BLOCK.get(), Items.AMETHYST_SHARD, Items.AMETHYST_SHARD, BPBlocks.FILLED_SCULK_RIB_BLOCK.get(), output);
        genBERecipe(BPBlocks.SCULK_RIB_BLOCK.get().defaultBlockState().setValue(SculkRibBlock.WATERLOGGED, true),
                Items.AMETHYST_SHARD, BPBlocks.FILLED_SCULK_RIB_BLOCK.get().defaultBlockState())
                .save(output, BedrockPlatform.modResLocation("filled_sculk_rib_block_from_waterlogged"));
        genBothRecipeWithModPath("crying_obsidian","crying_obsidian_reduction",
                Blocks.OBSIDIAN.defaultBlockState(), BPItems.ENCHANT_DUST.get(), BPItems.ENCHANT_DUST.get(), Blocks.CRYING_OBSIDIAN.defaultBlockState(), output);
        genBothRecipeWithModPath("sculk_shrieker_reactive","sculk_shrieker_disable",
                Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON,false), Items.ECHO_SHARD, Items.ECHO_SHARD,
                Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON,true), output);
        genBothRecipeWithModPath("sculk_shrieker_waterlogged_reactive","sculk_shrieker_waterlogged_disable",
                Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON,false)
                        .setValue(SculkShriekerBlock.WATERLOGGED,true), Items.ECHO_SHARD, Items.ECHO_SHARD,
                Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON,true)
                        .setValue(SculkShriekerBlock.WATERLOGGED,true), output);
        for (int i = 0; i < 4; i++){
            genBRRecipe(
                    Blocks.END_PORTAL_FRAME.defaultBlockState().rotate(Rotation.values()[i]).setValue(EndPortalFrameBlock.HAS_EYE,true),
                    Items.ENDER_EYE.getDefaultInstance(),
                    Items.ENDER_PEARL,
                    Blocks.END_PORTAL_FRAME.defaultBlockState().rotate(Rotation.values()[i]).setValue(EndPortalFrameBlock.HAS_EYE,false))
                    .save(output, BedrockPlatform.modResLocation("end_portal_frame_" + Rotation.values()[i].getSerializedName() + "_reduction"));
            genBothRecipeWithModPath(
                    "encapsulated_end_portal_frame_" + Rotation.values()[i].getSerializedName(),
                    "encapsulated_end_portal_frame_" + Rotation.values()[i].getSerializedName() + "_reduction",
                    Blocks.END_PORTAL_FRAME.defaultBlockState().rotate(Rotation.values()[i]).setValue(EndPortalFrameBlock.HAS_EYE,true),
                    BPBlocks.GHAST_TEAR_GLASS.asItem(),
                    Items.GLASS,
                    BPBlocks.ENCAPSULATED_END_PORTAL_FRAME.get().defaultBlockState().rotate(Rotation.values()[i]), output);
        }
        genBothRecipeWithModPath("sculk_catalyst_from_rib","sculk_rib_block_from_reduction",
                BPBlocks.SCULK_RIB_BLOCK.get().defaultBlockState(), Items.ECHO_SHARD, Items.ECHO_SHARD, Blocks.SCULK_CATALYST.defaultBlockState(), output);
        genCompressAndDecompressEight(BPItems.SCULK_RIB,BPBlocks.SCULK_RIB_BLOCK,output);
        genHuiShaped(BPItems.SCULK_RIB,Items.ECHO_SHARD,Items.SCULK_CATALYST,1).save(output,BedrockPlatform.modResLocation("sculk_catalyst_from_crafting"));
        genHuiShaped(BPItems.SCULK_RIB,AMETHYST_TAG,BPBlocks.FILLED_SCULK_RIB_BLOCK,1).save(output,BedrockPlatform.modResLocation("filled_sculk_rib_block_from_crafting"));
        for (StrongInteractionBlockSet color : DatagenHandler.colorSIList) {
            genSISet(color,output);
        }
        genCompressAndDecompressFour(BPItems.BLUE_ICE_CUBE, Blocks.BLUE_ICE, output);
        genCompressAndDecompressNine(Items.KELP, BPBlocks.KELP_BLOCK.get(), output);
        genBothRecipeWithState(Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE,7),
                Items.SLIME_BALL, Items.SLIME_BALL, BPBlocks.PERMANENTLY_WETTED_FARMLAND.get().defaultBlockState(), output);
        genBothRecipe(BPBlocks.PERMANENTLY_WETTED_FARMLAND.get(), Items.GLOW_LICHEN, Items.GLOW_LICHEN,
                BPBlocks.GLOW_PERMANENTLY_WETTED_FARMLAND.get(), output);
        genTorch(COAL_TAG,COBBLESTONE_TAG,BPBlocks.STONE_TORCH.getItem()).save(output);
        genTorch(COAL_TAG,COBBLED_DEEPSLATE_TAG,BPBlocks.DEEPSLATE_TORCH.getItem()).save(output);
        genHuiShaped(AMETHYST_TAG,Items.GLOW_LICHEN,BPBlocks.AMETHYST_CANDLE.getItem(),8).save(output);
        genHuiShaped(AMETHYST_TAG,Items.GLOW_INK_SAC,BPBlocks.AMETHYST_CANDLE.getItem(),8).save(output,BedrockPlatform.modResLocation("amethyst_candle_from_ink"));
        for (int w = 0; w < 2; w++){
            genBothRecipeWithModPath(
                    "amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    Items.AMETHYST_SHARD,
                    BPBlocks.AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "exposed_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "exposed_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.EXPOSED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    Items.AMETHYST_SHARD,
                    BPBlocks.EXPOSED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "weathered_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "weathered_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.WEATHERED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    Items.AMETHYST_SHARD,
                    BPBlocks.WEATHERED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.OXIDIZED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    Items.AMETHYST_SHARD,
                    BPBlocks.OXIDIZED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "waxed_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "waxed_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.WAXED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    Items.AMETHYST_SHARD,
                    BPBlocks.AMETHYST_LANTERN.getWaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "waxed_exposed_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "waxed_exposed_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.WAXED_EXPOSED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    Items.AMETHYST_SHARD,
                    BPBlocks.EXPOSED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "waxed_weathered_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "waxed_weathered_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.WAXED_WEATHERED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    Items.AMETHYST_SHARD,
                    BPBlocks.WEATHERED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "waxed_oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "waxed_oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.WAXED_OXIDIZED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    Items.AMETHYST_SHARD,
                    BPBlocks.OXIDIZED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            for (int i = 0; i < 16; i++){
                genBRRecipe(BPBlocks.AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Items.AMETHYST_SHARD,
                        Blocks.COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.EXPOSED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Items.AMETHYST_SHARD,
                        Blocks.EXPOSED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("exposed_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.WEATHERED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Items.AMETHYST_SHARD,
                        Blocks.WEATHERED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("weathered_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.OXIDIZED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Items.AMETHYST_SHARD,
                        Blocks.OXIDIZED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.AMETHYST_LANTERN.getWaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Items.AMETHYST_SHARD,
                        Blocks.WAXED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("waxed_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.EXPOSED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Items.AMETHYST_SHARD,
                        Blocks.WAXED_EXPOSED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("waxed_exposed_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.WEATHERED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Items.AMETHYST_SHARD,
                        Blocks.WAXED_WEATHERED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("waxed_weathered_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.OXIDIZED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Items.AMETHYST_SHARD,
                        Blocks.WAXED_OXIDIZED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("waxed_oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
            }
        }
        genWoodenPlat(Items.OAK_PLANKS,Items.OAK_STAIRS,Items.OAK_SLAB,BPBlocks.OAK_PLATFORM,output);
        genTransparentPlat(BPBlocks.OAK_PLATFORM, BPBlocks.TRANSPARENT_OAK_PLATFORM, output);
        genWoodenPlat(Items.BIRCH_PLANKS,Items.BIRCH_STAIRS,Items.BIRCH_SLAB,BPBlocks.BIRCH_PLATFORM,output);
        genTransparentPlat(BPBlocks.BIRCH_PLATFORM, BPBlocks.TRANSPARENT_BIRCH_PLATFORM, output);
        genWoodenPlat(Items.SPRUCE_PLANKS,Items.SPRUCE_STAIRS,Items.SPRUCE_SLAB,BPBlocks.SPRUCE_PLATFORM,output);
        genTransparentPlat(BPBlocks.SPRUCE_PLATFORM, BPBlocks.TRANSPARENT_SPRUCE_PLATFORM, output);
        genWoodenPlat(Items.JUNGLE_PLANKS,Items.JUNGLE_STAIRS,Items.JUNGLE_SLAB,BPBlocks.JUNGLE_PLATFORM,output);
        genTransparentPlat(BPBlocks.JUNGLE_PLATFORM, BPBlocks.TRANSPARENT_JUNGLE_PLATFORM, output);
        genWoodenPlat(Items.DARK_OAK_PLANKS,Items.DARK_OAK_STAIRS,Items.DARK_OAK_SLAB,BPBlocks.DARK_OAK_PLATFORM,output);
        genTransparentPlat(BPBlocks.DARK_OAK_PLATFORM, BPBlocks.TRANSPARENT_DARK_OAK_PLATFORM, output);
        genWoodenPlat(Items.ACACIA_PLANKS,Items.ACACIA_STAIRS,Items.ACACIA_SLAB,BPBlocks.ACACIA_PLATFORM,output);
        genTransparentPlat(BPBlocks.ACACIA_PLATFORM, BPBlocks.TRANSPARENT_ACACIA_PLATFORM, output);
        genWoodenPlat(Items.MANGROVE_PLANKS,Items.MANGROVE_STAIRS,Items.MANGROVE_SLAB,BPBlocks.MANGROVE_PLATFORM,output);
        genTransparentPlat(BPBlocks.MANGROVE_PLATFORM, BPBlocks.TRANSPARENT_MANGROVE_PLATFORM, output);
        genWoodenPlat(Items.CHERRY_PLANKS,Items.CHERRY_STAIRS,Items.CHERRY_SLAB,BPBlocks.CHERRY_PLATFORM,output);
        genTransparentPlat(BPBlocks.CHERRY_PLATFORM, BPBlocks.TRANSPARENT_CHERRY_PLATFORM, output);
        genStonePlat(STONE_PLATFORM_MATERIAL_2_TAG,STONE_PLATFORM_MATERIAL_TAG,BPBlocks.STONE_PLATFORM,Blocks.STONE_BRICK_SLAB,output);
        genTransparentPlat(BPBlocks.STONE_PLATFORM, BPBlocks.TRANSPARENT_STONE_PLATFORM, output);
        genWoodenPlat(Items.CRIMSON_PLANKS,Items.CRIMSON_STAIRS,Items.CRIMSON_SLAB,BPBlocks.CRIMSON_PLATFORM,output);
        genTransparentPlat(BPBlocks.CRIMSON_PLATFORM, BPBlocks.TRANSPARENT_CRIMSON_PLATFORM, output);
        genWoodenPlat(Items.WARPED_PLANKS,Items.WARPED_STAIRS,Items.WARPED_SLAB,BPBlocks.WARPED_PLATFORM,output);
        genTransparentPlat(BPBlocks.WARPED_PLATFORM, BPBlocks.TRANSPARENT_WARPED_PLATFORM, output);
        genStonePlatCraftingTable(COLORLESS_GLASS_TAG,BPBlocks.GLASS_PLATFORM).save(output);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(COLORLESS_GLASS_TAG), RecipeCategory.BUILDING_BLOCKS, BPBlocks.GLASS_PLATFORM,2)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(COLORLESS_GLASS_TAG)))
                .save(output, BuiltInRegistries.ITEM.getKey(BPBlocks.GLASS_PLATFORM.asItem()) + "_from_stonecutting");
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, Blocks.GLASS)
                .requires(BPBlocks.GLASS_PLATFORM).requires(BPBlocks.GLASS_PLATFORM)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(BPBlocks.GLASS_PLATFORM)))
                .save(output, BuiltInRegistries.ITEM.getKey(BPBlocks.GLASS_PLATFORM.asItem()) + "_reverse");

        genBoatWithChest(Items.CRIMSON_PLANKS,BPItems.CRIMSON_BOAT,BPItems.CRIMSON_CHEST_BOAT,output);
        genBoatWithChest(Items.WARPED_PLANKS,BPItems.WARPED_BOAT,BPItems.WARPED_CHEST_BOAT,output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, BPBlocks.PRECISE_NETHER_PORTAL_ITEM, 2)
                .define('#', Items.OBSIDIAN).define('@', Items.CRYING_OBSIDIAN).define('*', Items.BLAZE_POWDER)
                .pattern("@#@").pattern("#*#").pattern("@#@")
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(Items.BLAZE_POWDER)))
                .save(output);
        genGeodeSet(BPBlocks.GEODE_MOSAIC_TILE, output);
        genGeodeSet(BPBlocks.GEODE_WHITE_TILES, output);
        genGeodeSet(BPBlocks.GEODE_WHITE_SMOOTH_TILE, output);
        genGeodeSet(BPBlocks.GEODE_WHITE_BRICKS, output);
        genPillarSet(GEODE_WHITE_BRICKS_TAG,GEODE_WHITE_BRICK_SLABS_TAG,BPBlocks.GEODE_WHITE_PILLAR,output);
        genHuiShaped(GEODE_WHITE_BRICKS_TAG,WOODEN_CHEST_TAG,BPBlocks.GEODE_WHITE_CRATE,1).save(output);
        genGeodeSet(BPBlocks.GEODE_BLACK_TILES, output);
        genGeodeSet(BPBlocks.GEODE_BLACK_SMOOTH_TILE, output);
        genGeodeSet(BPBlocks.GEODE_BLACK_BRICKS, output);
        genPillarSet(GEODE_BLACK_BRICKS_TAG,GEODE_BLACK_BRICK_SLABS_TAG,BPBlocks.GEODE_BLACK_PILLAR,output);
        genHuiShaped(GEODE_BLACK_BRICKS_TAG,WOODEN_CHEST_TAG,BPBlocks.GEODE_BLACK_CRATE,1).save(output);
        genGeodeSet(BPBlocks.GEODE_GRAY_TILES, output);
        genGeodeSet(BPBlocks.GEODE_GRAY_SMOOTH_TILE, output);
        genGeodeSet(BPBlocks.GEODE_GRAY_BRICKS, output);
        genPillarSet(GEODE_GRAY_BRICKS_TAG,GEODE_GRAY_BRICK_SLABS_TAG,BPBlocks.GEODE_GRAY_PILLAR,output);
        genHuiShaped(GEODE_GRAY_BRICKS_TAG,WOODEN_CHEST_TAG,BPBlocks.GEODE_GRAY_CRATE,1).save(output);
        genGeodeSet(BPBlocks.GEODE_BLUE_TILES, output);
        genGeodeSet(BPBlocks.GEODE_BLUE_SMOOTH_TILE, output);
        genGeodeSet(BPBlocks.GEODE_BLUE_BRICKS, output);
        genPillarSet(GEODE_BLUE_BRICKS_TAG,GEODE_BLUE_BRICK_SLABS_TAG,BPBlocks.GEODE_BLUE_PILLAR,output);
        genHuiShaped(GEODE_BLUE_BRICKS_TAG,WOODEN_CHEST_TAG,BPBlocks.GEODE_BLUE_CRATE,1).save(output);
        genGeodeSet(BPBlocks.GEODE_GRAY_WHITE_TILES, output);
        genGeodeSet(BPBlocks.GEODE_BLUE_WHITE_TILES, output);
        genFourPlusOne(Items.TINTED_GLASS, GEODE_WHITE_BRICKS_TAG, BPBlocks.GEODE_TINTED_WHITE_GLASS).save(output);
        genFourPlusOne(Items.TINTED_GLASS, GEODE_BLACK_BRICKS_TAG, BPBlocks.GEODE_TINTED_BLACK_GLASS).save(output);
        genFourPlusOne(Items.TINTED_GLASS, GEODE_GRAY_BRICKS_TAG, BPBlocks.GEODE_TINTED_GRAY_GLASS).save(output);
        genFourPlusOne(Items.TINTED_GLASS, GEODE_BLUE_BRICKS_TAG, BPBlocks.GEODE_TINTED_BLUE_GLASS).save(output);

        genBERecipe(Blocks.CHEST.defaultBlockState(), Items.OAK_DOOR, Blocks.BARREL.defaultBlockState()).save(output, BedrockPlatform.modResLocation("chest_test"));
    }
    protected void genSISet(StrongInteractionBlockSet color, RecipeOutput output){
        genBothRecipe(color.getBaseBlock().get(), BPItems.BLUE_ICE_CUBE.get(), BPItems.BLUE_ICE_CUBE.get(), color.getSlick().get(), output);
        genBothRecipe(color.getBaseBlock().get(), Items.GLOWSTONE_DUST, Items.GLOWSTONE_DUST, color.getGlow().get(), output);
        genBothRecipe(color.getBaseBlock().get(), Items.WIND_CHARGE, Items.WIND_CHARGE, color.getTwill().get(), output);
        genBothRecipe(color.getBaseBlock().get(), Items.BLAZE_POWDER, Items.BLAZE_POWDER, color.getTransparent().get(), output);
        if (color != BPBlocks.BLACK_SI_BLOCK_SET){
            genBERecipe(BPBlocks.BLACK_SI_BLOCK_SET.getBaseBlock().get().defaultBlockState(),
                    StrongInteractionBlockSet.returnColorMaterial(color),
                    color.getBaseBlock().get().defaultBlockState())
                    .save(output, BuiltInRegistries.ITEM.getKey(color.getBaseBlock().asItem()) + "_dye");
            genBERecipe(BPBlocks.BLACK_SI_BLOCK_SET.getSlick().get().defaultBlockState(),
                    StrongInteractionBlockSet.returnColorMaterial(color),
                    color.getSlick().get().defaultBlockState())
                    .save(output, BuiltInRegistries.ITEM.getKey(color.getSlick().asItem()) + "_dye");
            genBERecipe(BPBlocks.BLACK_SI_BLOCK_SET.getGlow().get().defaultBlockState(),
                    StrongInteractionBlockSet.returnColorMaterial(color),
                    color.getGlow().get().defaultBlockState())
                    .save(output, BuiltInRegistries.ITEM.getKey(color.getGlow().asItem()) + "_dye");
            genBERecipe(BPBlocks.BLACK_SI_BLOCK_SET.getTwill().get().defaultBlockState(),
                    StrongInteractionBlockSet.returnColorMaterial(color),
                    color.getTwill().get().defaultBlockState())
                    .save(output, BuiltInRegistries.ITEM.getKey(color.getTwill().asItem()) + "_dye");
            genBERecipe(BPBlocks.BLACK_SI_BLOCK_SET.getTransparent().get().defaultBlockState(),
                    StrongInteractionBlockSet.returnColorMaterial(color),
                    color.getTransparent().get().defaultBlockState())
                    .save(output, BuiltInRegistries.ITEM.getKey(color.getTransparent().asItem()) + "_dye");
        }
        genCompressAndDecompressFour(color.getBaseBlock().get(),color.getTile().get(),output);
    }
    protected void genGeodeSet(DecoVariantBlockSet block, RecipeOutput output){
        genGeodeBrickStonecutting(block.getBaseBlock(), output);
        genGeodeBrickStonecutting(block.getStairs(), output);
        genGeodeBrickSlabStonecutting(block.getSlab(), output);
        genGeodeBrickStonecutting(block.getWall(), output);
    }
    protected void genPillarSet(TagKey<Item> inputTag, TagKey<Item> inputSlabTag, DeferredBlock<Block> outputBlock, RecipeOutput output){
        genPillarStonecutting(inputTag,outputBlock,output);
        genPillarFromReverse(inputSlabTag,outputBlock).save(output);
    }

    protected void genBothRecipe(Block inputBlock, Item ingredient, ItemLike decompositionProducts, Block outputBlock, RecipeOutput output){
        genBothRecipeWithState(inputBlock.defaultBlockState(), ingredient, decompositionProducts, outputBlock.defaultBlockState(), output);
    }
    protected void genBothRecipeWithState(BlockState inputBlock, Item ingredient, ItemLike decompositionProducts, BlockState outputBlock, RecipeOutput output){
        genBERecipe(inputBlock, ingredient, outputBlock).save(output);
        genBRRecipe(outputBlock, ingredient.getDefaultInstance(), decompositionProducts, inputBlock).save(output, BuiltInRegistries.ITEM.getKey(outputBlock.getBlock().asItem()) + "_reduction");
    }
    protected void genBothRecipeWithModPath(String path0, String path1, BlockState inputBlock, Item ingredient, ItemLike decompositionProducts, BlockState outputBlock, RecipeOutput output){
        genBERecipe(inputBlock, ingredient, outputBlock).save(output, BedrockPlatform.modResLocation(path0));
        genBRRecipe(outputBlock, ingredient.getDefaultInstance(), decompositionProducts, inputBlock).save(output, BedrockPlatform.modResLocation(path1));
    }
    protected BPRecipeBuilder.BERBuilder genBERecipe(BlockState inputBlock, Item ingredient, BlockState outputBlock){
        return new BPRecipeBuilder.BERBuilder(
                new ItemStack(outputBlock.getBlock()),
                inputBlock,
                Ingredient.of(ingredient),
                outputBlock);
    }
    protected BPRecipeBuilder.BRRBuilder genBRRecipe(BlockState inputBlock, ItemStack resultItem, ItemLike decompositionProducts, BlockState outputBlock){
        return new BPRecipeBuilder.BRRBuilder(
                new ItemStack(outputBlock.getBlock()),
                inputBlock,
                Ingredient.of(decompositionProducts),
                resultItem,
                outputBlock);
    }

    // 九原料合成一块的合成及分解配方
    protected void genCompressAndDecompressNine(ItemLike input, ItemLike result, @NotNull RecipeOutput output){
        genCompressNine(input, result).save(output, BedrockPlatform.modResLocation(BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()));
        genDecompressNine(result, input).save(output,  BedrockPlatform.modResLocation(BuiltInRegistries.ITEM.getKey(input.asItem()).getPath() + "_from_block"));
    }
    protected ShapedRecipeBuilder genCompressNine(ItemLike input, ItemLike result){
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('A', input)
                .pattern("AAA").pattern("AAA").pattern("AAA")
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
    protected ShapelessRecipeBuilder genDecompressNine(ItemLike input, ItemLike result){
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result, 9)
                .requires(input)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
    // 八原料合成一块的合成及分解配方
    protected void genCompressAndDecompressEight(ItemLike input, ItemLike result, @NotNull RecipeOutput output){
        genCompressEight(input, result).save(output, BedrockPlatform.modResLocation(BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()));
        genDecompressEight(result, input).save(output,  BedrockPlatform.modResLocation(BuiltInRegistries.ITEM.getKey(input.asItem()).getPath() + "_from_block"));
    }
    protected ShapelessRecipeBuilder genCompressEight(ItemLike input, ItemLike result){
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result)
                .requires(input).requires(input).requires(input).requires(input).requires(input).requires(input).requires(input).requires(input)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
    protected ShapelessRecipeBuilder genDecompressEight(ItemLike input, ItemLike result){
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result, 8)
                .requires(input)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
    // 四原料合成一块的合成及分解配方
    protected void genCompressAndDecompressFour(ItemLike input, ItemLike result, @NotNull RecipeOutput output){
        genCompressFour(input, result).save(output, BedrockPlatform.modResLocation(BuiltInRegistries.ITEM.getKey(input.asItem()).getPath()));
        genDecompressFour(result, input).save(output, BedrockPlatform.modResLocation(BuiltInRegistries.ITEM.getKey(input.asItem()).getPath() + "_from_block"));
    }
    protected ShapedRecipeBuilder genCompressFour(ItemLike input, ItemLike result){
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('A', input)
                .pattern("AA").pattern("AA")
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
    protected ShapelessRecipeBuilder genDecompressFour(ItemLike input, ItemLike result){
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .requires(input)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
    // 回字形配方
    protected ShapedRecipeBuilder genHuiShaped(TagKey<Item> input, TagKey<Item> input0 , ItemLike result, int count){
        return genHuiShaped(Ingredient.of(input),Ingredient.of(input0),result, count)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input).of(input0)));
    }
    protected ShapedRecipeBuilder genHuiShaped(ItemLike input, ItemLike input0 , ItemLike result, int count){
        return genHuiShaped(Ingredient.of(input),Ingredient.of(input0),result, count)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input).of(input0)));
    }
    protected ShapedRecipeBuilder genHuiShaped(ItemLike input, TagKey<Item> input0 , ItemLike result, int count){
        return genHuiShaped(Ingredient.of(input),Ingredient.of(input0),result, count)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input).of(input0)));
    }
    protected ShapedRecipeBuilder genHuiShaped(TagKey<Item> input, ItemLike input0 , ItemLike result, int count){
        return genHuiShaped(Ingredient.of(input),Ingredient.of(input0),result, count)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input).of(input0)));
    }
    protected ShapedRecipeBuilder genHuiShaped(Ingredient input, Ingredient input0 , ItemLike result, int count){
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, count)
                .define('#', input).define('@', input0)
                .pattern("###").pattern("#@#").pattern("###");
    }
    // 4+1配方
    protected ShapelessRecipeBuilder genFourPlusOne(ItemLike input, TagKey<Item> input4, ItemLike result){
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .requires(input).requires(input4).requires(input4).requires(input4).requires(input4)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
    // 火把
    protected ShapedRecipeBuilder genTorch(TagKey<Item> input0, TagKey<Item> input1, ItemLike result){
        return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 4)
                .define('^', input0).define('|', input1)
                .pattern("^").pattern("|")
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input0)));
    }
    // 木平台
    protected void genWoodenPlat(ItemLike blockInput, ItemLike stairInput, ItemLike slabInput, ItemLike result, @NotNull RecipeOutput output){
        genWoodenPlatCraftingTable(blockInput,result).save(output);
        genWoodenPlatReverse(result, slabInput).save(output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_recovery");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(blockInput), RecipeCategory.BUILDING_BLOCKS, result,2)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(blockInput)))
                .save(output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(stairInput), RecipeCategory.BUILDING_BLOCKS, result,2)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(blockInput)))
                .save(output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(slabInput), RecipeCategory.BUILDING_BLOCKS, result)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(slabInput)))
                .save(output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_stonecutting_single");
    }
    protected ShapedRecipeBuilder genWoodenPlatCraftingTable(ItemLike input, ItemLike result){
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 8)
                .define('A', input).define('/', Items.STICK)
                .pattern("AAA").pattern("/ /")
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
    protected ShapelessRecipeBuilder genWoodenPlatReverse(ItemLike input, ItemLike result){
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result)
                .requires(input)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
    // 石平台
    protected void genStonePlat(TagKey<Item> blockInput, TagKey<Item> slabInput, ItemLike result, ItemLike recovery, @NotNull RecipeOutput output){
        genStonePlatCraftingTable(blockInput,result).save(output);
        genStonePlatReverse(result, recovery).save(output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_recovery");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(blockInput), RecipeCategory.BUILDING_BLOCKS, result,2)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(blockInput)))
                .save(output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(slabInput), RecipeCategory.BUILDING_BLOCKS, result)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(slabInput)))
                .save(output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_stonecutting_single");
    }
    protected ShapedRecipeBuilder genStonePlatCraftingTable(TagKey<Item> input, ItemLike result){
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 10)
                .define('A', input)
                .pattern("AAA").pattern("A A")
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
    protected ShapelessRecipeBuilder genStonePlatReverse(ItemLike input, ItemLike result){
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, result)
                .requires(input)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
    protected void genTransparentPlat(DeferredBlock<Block> inputBlock, DeferredBlock<Block> outputBlock, @NotNull RecipeOutput output){
        for (int i = 0; i < 4; i++){
            for (Half half : Half.values()){
                for (int w = 0; w < 2; w++){
                    genBothRecipeWithModPath(
                            BuiltInRegistries.ITEM.getKey(outputBlock.get().asItem()).getPath() + (w!=0 ? "_waterlogged_" : "_") + half.getSerializedName() + "_" + Rotation.values()[i].getSerializedName(),
                            BuiltInRegistries.ITEM.getKey(outputBlock.get().asItem()).getPath() + (w!=0 ? "_waterlogged_" : "_") + half.getSerializedName() + "_" + Rotation.values()[i].getSerializedName() + "_reduction",
                            inputBlock.get().defaultBlockState().rotate(Rotation.values()[i]).setValue(PlatformBlock.HALF, half).setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), Items.GLASS_PANE, Items.GLASS_PANE,
                            outputBlock.get().defaultBlockState().rotate(Rotation.values()[i]).setValue(PlatformBlock.HALF, half).setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
                }
            }
        }
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, outputBlock)
                .requires(inputBlock).requires(Items.GLASS_PANE)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(inputBlock)))
                .save(output, BuiltInRegistries.ITEM.getKey(outputBlock.get().asItem()) + "_from_crafting");
    }
    // 船
    protected void genBoatWithChest(ItemLike input, ItemLike result, ItemLike chestResult, @NotNull RecipeOutput output){
        genBoat(input,result).save(output);
        genChestBoat(input,chestResult).save(output);
    }
    protected ShapedRecipeBuilder genBoat(ItemLike input, ItemLike result){
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .define('A', input)
                .pattern("A A").pattern("AAA")
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
    protected ShapelessRecipeBuilder genChestBoat(ItemLike input, ItemLike result){
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
                .requires(WOODEN_CHEST_TAG).requires(input)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
    // 晶洞砖
    protected void genGeodeBrickStonecutting(ItemLike result, @NotNull RecipeOutput output){
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(GEODE_BRICKS_TAG), RecipeCategory.BUILDING_BLOCKS, result)
            .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(GEODE_BRICKS_TAG)))
            .save(output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_stonecutting");
    }
    protected void genGeodeBrickSlabStonecutting(ItemLike result, @NotNull RecipeOutput output){
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(GEODE_BRICKS_TAG), RecipeCategory.BUILDING_BLOCKS, result, 2)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(GEODE_BRICKS_TAG)))
                .save(output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_stonecutting");
    }
    // 柱子
    protected void genPillarStonecutting(TagKey<Item> input, ItemLike result, @NotNull RecipeOutput output){
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, result)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)))
                .save(output, BuiltInRegistries.ITEM.getKey(result.asItem()) + "_from_stonecutting");
    }
    protected ShapedRecipeBuilder genPillarFromReverse(TagKey<Item> input, ItemLike result){
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .define('A', input)
                .pattern("A").pattern("A")
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input)));
    }
}