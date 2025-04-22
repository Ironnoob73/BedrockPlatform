package dev.hail.bedrock_platform.Blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DecoVariantBlockSet {
    private final DecoVariantBlockSet.Builder builder;
    public static final List<DecoVariantBlockSet> BLOCK_SETS = new ArrayList<>();

    DecoVariantBlockSet(DecoVariantBlockSet.Builder builder) {
        this.builder = builder;
    }

    public static DecoVariantBlockSet.Builder builder(String id, MapColor color) {
        return new DecoVariantBlockSet.Builder(id, color);
    }
    public DeferredBlock<Block> getBaseBlock() {
        return builder.BASE;
    }
    public DeferredBlock<Block> getStairs() {
        return builder.STAIRS;
    }
    public DeferredBlock<Block> getSlab() {
        return builder.SLAB;
    }
    public DeferredBlock<Block> getWall() {
        return builder.WALL;
    }

    public static class Builder {
        private final String id;
        private final MapColor color;
        private final DeferredBlock<Block> BASE;
        private DeferredBlock<Block> STAIRS;
        private DeferredBlock<Block> SLAB;
        private DeferredBlock<Block> WALL;
        private Set<DeferredBlock<? extends Block>> blockCache = new HashSet<>();
        public Builder(String id, MapColor color) {
            this.id = id;
            this.color = color;
            this.BASE = BPBlocks.registerWithItem(id, () -> new Block(BPBlocks.geodeSeries().mapColor(color)));
        }
        public DecoVariantBlockSet.Builder defaultSet(){
            stairs().slab().wall();
            return this;
        }
        public DecoVariantBlockSet.Builder stairs() {
            this.STAIRS = BPBlocks.registerWithItem(id + "_stairs", () -> new StairBlock(BPBlocks.GEODE_MOSAIC_TILE.getBaseBlock().get().defaultBlockState(), BPBlocks.geodeSeries().mapColor(color)));
            blockCache.add(STAIRS);
            return this;
        }
        public DecoVariantBlockSet.Builder slab() {
            this.SLAB = BPBlocks.registerWithItem(id + "_slab", () -> new SlabBlock(BPBlocks.geodeSeries().mapColor(color)));
            blockCache.add(SLAB);
            return this;
        }
        public DecoVariantBlockSet.Builder wall() {
            this.WALL = BPBlocks.registerWithItem(id + "_wall", () -> new WallBlock(BPBlocks.geodeSeries().mapColor(color).forceSolidOn()));
            blockCache.add(WALL);
            return this;
        }
        public DecoVariantBlockSet build() {
            DecoVariantBlockSet blockSet = new DecoVariantBlockSet(this);
            BLOCK_SETS.add(blockSet);
            this.blockCache = null;
            return blockSet;
        }
    }
}
