package dev.hail.bedrock_platform.Datagen;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Blocks.Light.Amethyst.AmethystLanternBlock;
import dev.hail.bedrock_platform.Blocks.StrongInteractionBlockSet;
import dev.hail.bedrock_platform.Items.BPItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = BedrockPlatform.MODID)
public class DatagenHandler {
    protected static List<DeferredBlock<Block>> cubeAllBlockList = new ArrayList<>();
    protected static List<StrongInteractionBlockSet> colorSIList = new ArrayList<>();
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        // INIT
        cubeAllBlockList.add(BPBlocks.BEDROCK_PLATFORM);
        cubeAllBlockList.add(BPBlocks.LUMINOUS_BEDROCK_PLATFORM);
        cubeAllBlockList.add(BPBlocks.TWILL_BEDROCK_PLATFORM);
        cubeAllBlockList.add(BPBlocks.SOLID_END_VOID);
        cubeAllBlockList.add(BPBlocks.GHAST_TEAR_GLASS);
        cubeAllBlockList.add(BPBlocks.GEODE_MOSAIC_TILE);
        colorSIList.add(BPBlocks.RED_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.ORANGE_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.YELLOW_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.GREEN_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.CYAN_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.BLUE_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.PURPLE_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.WHITE_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.GRAY_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.BLACK_SI_BLOCK_SET);

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new ModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new StateProvider(output, helper));
        generator.addProvider(event.includeServer(), new LootProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new MyRecipeProvider(output, lookupProvider));
    }

    public static class ModelProvider extends ItemModelProvider {
        ResourceLocation gItemModel = ResourceLocation.withDefaultNamespace("item/generated");
        public ModelProvider(PackOutput gen, ExistingFileHelper helper) {
            super(gen, BedrockPlatform.MODID, helper);
        }
        @Override
        protected void registerModels() {
            genDefault(BPItems.SCULK_RIB);
            genDefault(BPItems.ENCHANT_DUST);
            genDefault(BPItems.BLUE_ICE_CUBE);
            genBlockItem(BPBlocks.STONE_TORCH.getItem());
            genBlockItem(BPBlocks.DEEPSLATE_TORCH.getItem());
            genBlockItem(BPBlocks.AMETHYST_CANDLE.getItem());
        }
        private void genDefault(DeferredItem<Item> item){
            singleTexture(item.getId().getPath(), gItemModel, "layer0",
                    BedrockPlatform.modResLocation("item/" + item.getId().getPath()));
        }
        private void genBlockItem(DeferredItem<Item> item){
            singleTexture(item.getId().getPath(), gItemModel, "layer0",
                    BedrockPlatform.modResLocation("block/" + item.getId().getPath()));
        }
    }

    public static class StateProvider extends BlockStateProvider {
        public StateProvider(PackOutput gen, ExistingFileHelper helper) {
            super(gen, BedrockPlatform.MODID, helper);
        }
        @Override
        protected void registerStatesAndModels() {
            for (DeferredBlock<Block> block : cubeAllBlockList) {
                genCubeAllBlockWithItem(block);
            }
            for (StrongInteractionBlockSet color : colorSIList) {
                genSISet(color);
            }
            genBlockItemWithSpecialModel(BPBlocks.ENCAPSULATED_END_PORTAL_FRAME);
            genBlockItemWithSpecialModel(BPBlocks.SCULK_RIB_BLOCK);
            genKelpBlockWithItem();
            genBlockItemWithSpecialModel(BPBlocks.PERMANENTLY_WETTED_FARMLAND);
            genBlockItemWithSpecialModel(BPBlocks.GLOW_PERMANENTLY_WETTED_FARMLAND);
            genAmethystLanternBlockItem(BPBlocks.AMETHYST_LANTERN.getUnwaxed(),"copper_grate");
            genAmethystLanternBlockItem(BPBlocks.EXPOSED_AMETHYST_LANTERN.getUnwaxed(),"exposed_copper_grate");
            genAmethystLanternBlockItem(BPBlocks.WEATHERED_AMETHYST_LANTERN.getUnwaxed(),"weathered_copper_grate");
            genAmethystLanternBlockItem(BPBlocks.OXIDIZED_AMETHYST_LANTERN.getUnwaxed(),"oxidized_copper_grate");
        }
        protected void genCubeAllBlockWithItem(DeferredBlock<Block> block){
            simpleBlockWithItem(block.get(), cubeAll(block.get()));
        }
        protected void genTransparentBlockWithItem(DeferredBlock<Block> block){
            simpleBlockWithItem(block.get(), models().cubeAll(getBlockId(block), blockTexture(block.get())).renderType("translucent"));
        }
        protected void genKelpBlockWithItem(){
            simpleBlockWithItem(BPBlocks.KELP_BLOCK.get(), models().cubeColumn(BPBlocks.KELP_BLOCK.getId().getPath(),
                    getBlockTexture(getBlockId(BPBlocks.KELP_BLOCK) + "_side"), getBlockTexture(getBlockId(BPBlocks.KELP_BLOCK) + "_end")));
        }
        protected void genAmethystLanternBlockItem(DeferredBlock<Block> block, String outer){
            simpleBlockItem(block.get(), models().withExistingParent(block.getId().getPath(),
                    BedrockPlatform.modResLocation("block/template_amethyst_lantern"))
                    .texture("outer", ResourceLocation.withDefaultNamespace("block/" + outer))
                    .texture("torch", BedrockPlatform.modResLocation("block/amethyst_candle")).renderType("translucent"));
        }
        protected void genSISet(StrongInteractionBlockSet color){
            genCubeAllBlockWithItem(color.getBaseBlock());
            genCubeAllBlockWithItem(color.getTile());
            genCubeAllBlockWithItem(color.getSlick());
            genCubeAllBlockWithItem(color.getGlow());
            genCubeAllBlockWithItem(color.getTwill());
            genTransparentBlockWithItem(color.getTransparent());
        }
        protected void genBlockItemWithSpecialModel(DeferredBlock<Block> block){
            simpleBlockItem(block.get(), getBlockModel(block));
        }
        protected ModelFile getBlockModel(DeferredBlock<Block> block){
            return new ModelFile(blockTexture(block.get())) {
                @Override protected boolean exists() {return true;}
            };
        }
        protected String getBlockId(DeferredBlock<Block> block){
            return block.getId().getPath();
        }
        protected ResourceLocation getBlockTexture(String blockId){
            return BedrockPlatform.modResLocation("block/" + blockId);
        }
    }

    public static class LootProvider extends LootTableProvider {
        public LootProvider(PackOutput gen, CompletableFuture<HolderLookup.Provider> lookup) {
            super(gen, Set.of(), List.of(new SubProviderEntry(CustomBlockLoot::new, LootContextParamSets.BLOCK)), lookup);
        }
    }
    public static class CustomBlockLoot extends BlockLootSubProvider {
        List<Block> blockList = BPBlocks.BLOCKS.getEntries()
                .stream()
                .map(e -> (Block) e.value())
                .toList();
        protected CustomBlockLoot(HolderLookup.Provider lookupProvider) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
        }
        @Override
        public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pOutput) {
            this.generate();
            Set<ResourceKey<LootTable>> set = new HashSet<>();
            for(Block block : getKnownBlocks()) {
                if (block.isEnabled(this.enabledFeatures)) {
                    ResourceKey<LootTable> resourcekey = block.getLootTable();
                    if (resourcekey != BuiltInLootTables.EMPTY && set.add(resourcekey)) {
                        LootTable.Builder loottable$builder = this.map.remove(resourcekey);
                        if(loottable$builder != null) {
                            pOutput.accept(resourcekey, loottable$builder);
                        }else {
                            throw new IllegalStateException(
                                    String.format(Locale.ROOT, "Missing loottable '%s' for '%s'", resourcekey.location(), BuiltInRegistries.BLOCK.getKey(block))
                            );
                        }
                    }
                }
            }
        }
        @Override
        protected void generate() {
            for (Block block : blockList) {
                dropSelf(block);
            }
        }
        @NotNull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return BPBlocks.BLOCKS.getEntries()
                    .stream()
                    .map(DeferredHolder::value)
                    .collect(Collectors.toList());
        }
    }

    public static class MyRecipeProvider extends RecipeProvider {
        public MyRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
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
                    Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON,true), Items.ECHO_SHARD,
                    Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON,false), output);
            genBothRecipeWithModPath("sculk_shrieker_waterlogged_reactive","sculk_shrieker_waterlogged_disable",
                    Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON,true)
                            .setValue(SculkShriekerBlock.WATERLOGGED,true), Items.ECHO_SHARD,
                    Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON,false)
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
            for (StrongInteractionBlockSet color : colorSIList) {
                genSISet(color,output);
            }
            genBothRecipeWithState(Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE,7),
                    Items.SLIME_BALL, BPBlocks.PERMANENTLY_WETTED_FARMLAND.get().defaultBlockState(), output);
            genBothRecipe(BPBlocks.PERMANENTLY_WETTED_FARMLAND.get(), Items.GLOW_LICHEN,
                    BPBlocks.GLOW_PERMANENTLY_WETTED_FARMLAND.get(), output);
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
                            .save(output, "amethyst_lantern" + (w!=0 ? "_waterlogged_" : "") + i + "_reduction");
                    genBRRecipe(BPBlocks.EXPOSED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState()
                                    .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                            BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                            Blocks.EXPOSED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                            .save(output, "exposed_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "") + i + "_reduction");
                    genBRRecipe(BPBlocks.WEATHERED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState()
                                    .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                            BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                            Blocks.WEATHERED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                            .save(output, "weathered_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "") + i + "_reduction");
                    genBRRecipe(BPBlocks.OXIDIZED_AMETHYST_LANTERN.getUnwaxed().get().defaultBlockState()
                                    .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                            BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                            Blocks.OXIDIZED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                            .save(output, "oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "") + i + "_reduction");
                    genBRRecipe(BPBlocks.AMETHYST_LANTERN.getWaxed().get().defaultBlockState()
                                    .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                            BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                            Blocks.WAXED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                            .save(output, "waxed_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "") + i + "_reduction");
                    genBRRecipe(BPBlocks.EXPOSED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState()
                                    .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                            BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                            Blocks.WAXED_EXPOSED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                            .save(output, "waxed_exposed_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "") + i + "_reduction");
                    genBRRecipe(BPBlocks.WEATHERED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState()
                                    .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                            BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                            Blocks.WAXED_WEATHERED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                            .save(output, "waxed_weathered_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "") + i + "_reduction");
                    genBRRecipe(BPBlocks.OXIDIZED_AMETHYST_LANTERN.getWaxed().get().defaultBlockState()
                                    .setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0).setValue(AmethystLanternBlock.LIGHT,i),
                            BPBlocks.AMETHYST_CANDLE.getItem().get().getDefaultInstance(),
                            Blocks.WAXED_OXIDIZED_COPPER_GRATE.defaultBlockState().setValue(WaterloggedTransparentBlock.WATERLOGGED,w!=0))
                            .save(output, "waxed_oxidized_amethyst_lantern" + (w!=0 ? "_waterlogged_" : "") + i + "_reduction");
                }
            }
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
        protected MyRecipeBuilder.BERBuilder genBERecipe(BlockState inputBlock, Item ingredient, BlockState outputBlock){
            return new MyRecipeBuilder.BERBuilder(
                    new ItemStack(outputBlock.getBlock()),
                    inputBlock,
                    Ingredient.of(ingredient),
                    outputBlock);
        }
        protected MyRecipeBuilder.BRRBuilder genBRRecipe(BlockState inputBlock, ItemStack resultItem, BlockState outputBlock){
            return new MyRecipeBuilder.BRRBuilder(
                    new ItemStack(outputBlock.getBlock()),
                    inputBlock,
                    Ingredient.of(BPItems.OBSIDIAN_WRENCH),
                    resultItem,
                    outputBlock);
        }
    }
}
