package dev.hail.bedrock_platform.Datagen;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Blocks.Light.Amethyst.AmethystLanternBlock;
import dev.hail.bedrock_platform.Blocks.PlatformBlock;
import dev.hail.bedrock_platform.Blocks.StrongInteractionBlockSet;
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
    public static final TagKey<Item> STONE_PLATFORM_MATERIAL_2_TAG = TagKey.create(Registries.ITEM, BedrockPlatform.modResLocation("stone_platform_materials_2"));
    public static final TagKey<Item> STONE_PLATFORM_MATERIAL_TAG = TagKey.create(Registries.ITEM, BedrockPlatform.modResLocation("stone_platform_materials"));

    public BPRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }
    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        genBothRecipe(Blocks.BEDROCK, Items.NETHERITE_SCRAP, BPBlocks.BEDROCK_PLATFORM.get(), output);
        genBothRecipe(BPBlocks.BEDROCK_PLATFORM.get(), Items.GOLD_INGOT, BPBlocks.TWILL_BEDROCK_PLATFORM.get(), output);
        genBothRecipe(BPBlocks.BEDROCK_PLATFORM.get(), Items.NETHER_STAR, BPBlocks.LUMINOUS_BEDROCK_PLATFORM.get(), output);
        genBothRecipe(Blocks.GLASS, Items.GHAST_TEAR, BPBlocks.GHAST_TEAR_GLASS.get(), output);
        genBothRecipeWithModPath("reinforced_deepslate","reinforced_deepslate_reduction",
                Blocks.DEEPSLATE.defaultBlockState(), BPItems.SCULK_RIB.get(), Blocks.REINFORCED_DEEPSLATE.defaultBlockState(), output);
        genBothRecipeWithModPath("crying_obsidian","crying_obsidian_reduction",
                Blocks.OBSIDIAN.defaultBlockState(), BPItems.ENCHANT_DUST.get(), Blocks.CRYING_OBSIDIAN.defaultBlockState(), output);
        genBothRecipeWithModPath("sculk_shrieker_reactive","sculk_shrieker_disable",
                Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON,false), Items.ECHO_SHARD,
                Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON,true), output);
        genBothRecipeWithModPath("sculk_shrieker_waterlogged_reactive","sculk_shrieker_waterlogged_disable",
                Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON,false)
                        .setValue(SculkShriekerBlock.WATERLOGGED,true), Items.ECHO_SHARD,
                Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON,true)
                        .setValue(SculkShriekerBlock.WATERLOGGED,true), output);
        for (int i = 0; i < 4; i++){
            genBRRecipe(
                    Blocks.END_PORTAL_FRAME.defaultBlockState().rotate(Rotation.values()[i]).setValue(EndPortalFrameBlock.HAS_EYE,true),
                    Items.ENDER_EYE.getDefaultInstance(),
                    Blocks.END_PORTAL_FRAME.defaultBlockState().rotate(Rotation.values()[i]).setValue(EndPortalFrameBlock.HAS_EYE,false))
                    .save(output, BedrockPlatform.modResLocation("end_portal_frame_" + Rotation.values()[i].getSerializedName() + "_reduction"));
            genBothRecipeWithModPath(
                    "encapsulated_end_portal_frame_" + Rotation.values()[i].getSerializedName(),
                    "encapsulated_end_portal_frame_" + Rotation.values()[i].getSerializedName() + "_reduction",
                    Blocks.END_PORTAL_FRAME.defaultBlockState().rotate(Rotation.values()[i]).setValue(EndPortalFrameBlock.HAS_EYE,true),
                    BPBlocks.GHAST_TEAR_GLASS.asItem(),
                    BPBlocks.ENCAPSULATED_END_PORTAL_FRAME.get().defaultBlockState().rotate(Rotation.values()[i]), output);
        }
        genBothRecipeWithModPath("sculk_catalyst_from_rib","sculk_rib_block_from_reduction",
                BPBlocks.SCULK_RIB_BLOCK.get().defaultBlockState(), Items.ECHO_SHARD, Blocks.SCULK_CATALYST.defaultBlockState(), output);
        for (StrongInteractionBlockSet color : DatagenHandler.colorSIList) {
            genSISet(color,output);
        }
        genCompressAndDecompressFour(BPItems.BLUE_ICE_CUBE, Blocks.BLUE_ICE, output);
        genCompressAndDecompressNine(Items.KELP, BPBlocks.KELP_BLOCK.get(), output);
        genBothRecipeWithState(Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE,7),
                Items.SLIME_BALL, BPBlocks.PERMANENTLY_WETTED_FARMLAND.get().defaultBlockState(), output);
        genBothRecipe(BPBlocks.PERMANENTLY_WETTED_FARMLAND.get(), Items.GLOW_LICHEN,
                BPBlocks.GLOW_PERMANENTLY_WETTED_FARMLAND.get(), output);
        genTorch(COAL_TAG,COBBLESTONE_TAG,BPBlocks.STONE_TORCH.getItem()).save(output);
        genTorch(COAL_TAG,COBBLED_DEEPSLATE_TAG,BPBlocks.DEEPSLATE_TORCH.getItem()).save(output);
        for (int w = 0; w < 2; w++){
            genBothRecipeWithModPath(
                    "amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    BPBlocks.AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "exposed_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "exposed_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.EXPOSED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    BPBlocks.EXPOSED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "weathered_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "weathered_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.WEATHERED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    BPBlocks.WEATHERED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.OXIDIZED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    BPBlocks.OXIDIZED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "waxed_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "waxed_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.WAXED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    BPBlocks.AMETHYST_LANTERN.getWaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "waxed_exposed_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "waxed_exposed_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.WAXED_EXPOSED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    BPBlocks.EXPOSED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "waxed_weathered_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "waxed_weathered_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.WAXED_WEATHERED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    BPBlocks.WEATHERED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            genBothRecipeWithModPath(
                    "waxed_oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged" : ""),
                    "waxed_oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged" : "") + "_reduction",
                    Blocks.WAXED_OXIDIZED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0),
                    BPBlocks.AMETHYST_CANDLE.getItem().get(),
                    BPBlocks.OXIDIZED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
            for (int i = 0; i < 16; i++){
                genBRRecipe(BPBlocks.AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Blocks.COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.EXPOSED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Blocks.EXPOSED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("exposed_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.WEATHERED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Blocks.WEATHERED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("weathered_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.OXIDIZED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Blocks.OXIDIZED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.AMETHYST_LANTERN.getWaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Blocks.WAXED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("waxed_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.EXPOSED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Blocks.WAXED_EXPOSED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("waxed_exposed_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.WEATHERED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Blocks.WAXED_WEATHERED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("waxed_weathered_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
                genBRRecipe(BPBlocks.OXIDIZED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState()
                                .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                        BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                        Blocks.WAXED_OXIDIZED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                        .save(output, BedrockPlatform.modResLocation("waxed_oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "_") + i + "_reduction"));
            }
        }
        genStonePlat(STONE_PLATFORM_MATERIAL_2_TAG,STONE_PLATFORM_MATERIAL_TAG,BPBlocks.STONE_PLATFORM,Blocks.STONE_BRICK_SLAB,output);
        genTransparentStonePlat(BPBlocks.STONE_PLATFORM, BPBlocks.TRANSPARENT_STONE_PLATFORM, output);
    }
    protected void genSISet(StrongInteractionBlockSet color, RecipeOutput output){
        genBothRecipe(color.getBaseBlock().get(), BPItems.BLUE_ICE_CUBE.get(), color.getSlick().get(), output);
        genBothRecipe(color.getBaseBlock().get(), Items.GLOWSTONE_DUST, color.getGlow().get(), output);
        genBothRecipe(color.getBaseBlock().get(), Items.WIND_CHARGE, color.getTwill().get(), output);
        genBothRecipe(color.getBaseBlock().get(), Items.BLAZE_POWDER, color.getTransparent().get(), output);
        if (color != BPBlocks.BLACK_SI_BLOCK_SET){
            genBERecipe(BPBlocks.BLACK_SI_BLOCK_SET.getBaseBlock().get().defaultBlockState(),
                    StrongInteractionBlockSet.returnColorMaterial(color),
                    color.getBaseBlock().get().defaultBlockState())
                    .save(output, BuiltInRegistries.ITEM.getKey(color.getBaseBlock().asItem()) + "_dye");
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

    protected void genBothRecipe(Block inputBlock, Item ingredient, Block outputBlock, RecipeOutput output){
        genBothRecipeWithState(inputBlock.defaultBlockState(), ingredient, outputBlock.defaultBlockState(), output);
    }
    protected void genBothRecipeWithState(BlockState inputBlock, Item ingredient, BlockState outputBlock, RecipeOutput output){
        genBERecipe(inputBlock, ingredient, outputBlock).save(output);
        genBRRecipe(outputBlock, ingredient.getDefaultInstance(), inputBlock).save(output, BuiltInRegistries.ITEM.getKey(outputBlock.getBlock().asItem()) + "_reduction");
    }
    protected void genBothRecipeWithModPath(String path0, String path1, BlockState inputBlock, Item ingredient, BlockState outputBlock, RecipeOutput output){
        genBERecipe(inputBlock, ingredient, outputBlock).save(output, BedrockPlatform.modResLocation(path0));
        genBRRecipe(outputBlock, ingredient.getDefaultInstance(), inputBlock).save(output, BedrockPlatform.modResLocation(path1));
    }
    protected BPRecipeBuilder.BERBuilder genBERecipe(BlockState inputBlock, Item ingredient, BlockState outputBlock){
        return new BPRecipeBuilder.BERBuilder(
                new ItemStack(outputBlock.getBlock()),
                inputBlock,
                Ingredient.of(ingredient),
                outputBlock);
    }
    protected BPRecipeBuilder.BRRBuilder genBRRecipe(BlockState inputBlock, ItemStack resultItem, BlockState outputBlock){
        return new BPRecipeBuilder.BRRBuilder(
                new ItemStack(outputBlock.getBlock()),
                inputBlock,
                Ingredient.of(BPItems.ENCHANT_DUST),
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

    protected ShapedRecipeBuilder genTorch(TagKey<Item> input0, TagKey<Item> input1, ItemLike result){
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .define('^', input0).define('|', input1)
                .pattern("^").pattern("|")
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(input0)));
    }

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
    protected void genTransparentStonePlat(DeferredBlock<Block> inputBlock, DeferredBlock<Block> outputBlock, @NotNull RecipeOutput output){
        for (int i = 0; i < 4; i++){
            for (Half half : Half.values()){
                for (int w = 0; w < 2; w++){
                    genBothRecipeWithModPath(
                        BuiltInRegistries.ITEM.getKey(outputBlock.get().asItem()).getPath() + (w!=0 ? "_waterlogged_" : "_") + half.getSerializedName() + "_" + Rotation.values()[i].getSerializedName(),
                        BuiltInRegistries.ITEM.getKey(outputBlock.get().asItem()).getPath() + (w!=0 ? "_waterlogged_" : "_") + half.getSerializedName() + "_" + Rotation.values()[i].getSerializedName() + "_reduction",
                        inputBlock.get().defaultBlockState().rotate(Rotation.values()[i]).setValue(PlatformBlock.HALF, half).setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), Items.GLASS_PANE,
                        outputBlock.get().defaultBlockState().rotate(Rotation.values()[i]).setValue(PlatformBlock.HALF, half).setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0), output);
                }
            }
        }
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, outputBlock)
                .requires(inputBlock).requires(Items.GLASS_PANE)
                .unlockedBy("hasitem", inventoryTrigger(net.minecraft.advancements.critereon.ItemPredicate.Builder.item().of(inputBlock)))
                .save(output, BuiltInRegistries.ITEM.getKey(outputBlock.get().asItem()) + "_from_crafting");
    }
}