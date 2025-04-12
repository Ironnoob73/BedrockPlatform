package dev.hail.bedrock_platform.Datagen;

import dev.hail.bedrock_platform.Blocks.BPBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class BPLootTableProvider extends LootTableProvider {
    public BPLootTableProvider(PackOutput gen, CompletableFuture<HolderLookup.Provider> lookup) {
        super(gen, Set.of(), List.of(new SubProviderEntry(CustomBlockLoot::new, LootContextParamSets.BLOCK)), lookup);
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
                                    String.format(Locale.ROOT, "Missing lootTable '%s' for '%s'", resourcekey.location(), BuiltInRegistries.BLOCK.getKey(block))
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