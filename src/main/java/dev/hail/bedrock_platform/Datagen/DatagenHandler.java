package dev.hail.bedrock_platform.Datagen;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Blocks.StrongInteractionBlockSet;
import dev.hail.bedrock_platform.Items.BPItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
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
        }
        private void genDefault(DeferredItem<Item> item){
            singleTexture(item.getId().getPath(), gItemModel, "layer0",
                    ResourceLocation.fromNamespaceAndPath(BedrockPlatform.MODID, "item/" + item.getId().getPath()));
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
            genColumnBlockWithItem(BPBlocks.KELP_BLOCK);
            genBlockItemWithSpecialModel(BPBlocks.PERMANENTLY_WETTED_FARMLAND);
            genBlockItemWithSpecialModel(BPBlocks.GLOW_PERMANENTLY_WETTED_FARMLAND);
        }
        protected void genCubeAllBlockWithItem(DeferredBlock<Block> block){
            simpleBlockWithItem(block.get(), cubeAll(block.get()));
        }
        protected void genTransparentBlockWithItem(DeferredBlock<Block> block){
            simpleBlockWithItem(block.get(), models().cubeAll(getBlockId(block), blockTexture(block.get())).renderType("translucent"));
        }
        protected void genColumnBlockWithItem(DeferredBlock<Block> block){
            simpleBlockWithItem(block.get(), models().cubeColumn(block.getId().getPath(),
                    getBlockTexture(getBlockId(block) + "_side"), getBlockTexture(getBlockId(block) + "_end")));
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
            return ResourceLocation.fromNamespaceAndPath(BedrockPlatform.MODID, "block/" + blockId);
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
}
