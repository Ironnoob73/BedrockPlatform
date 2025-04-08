package dev.hail.bedrock_platform.Blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StrongInteractionBlockSet {
    private final Builder builder;
    public static final List<StrongInteractionBlockSet> BLOCK_SETS = new ArrayList<>();

    StrongInteractionBlockSet(Builder builder) {
        this.builder = builder;
    }

    public static Builder builder(String id, MapColor color) {
        return new Builder(id, color);
    }

    public DeferredBlock<Block> getBaseBlock() {
        return builder.BASE;
    }
    public DeferredBlock<Block> getTile() {
        return builder.TILE;
    }
    public DeferredBlock<Block> getSlick() {
        return builder.SLICK;
    }
    public DeferredBlock<Block> getGlow() {
        return builder.GLOW;
    }
    public DeferredBlock<Block> getTwill() {
        return builder.TWILL;
    }
    public DeferredBlock<Block> getTransparent() {
        return builder.TRANSPARENT;
    }

    public static class Builder {
        private final String id;
        private final MapColor color;
        private final DeferredBlock<Block> BASE;
        private DeferredBlock<Block> TILE;
        private DeferredBlock<Block> SLICK;
        private DeferredBlock<Block> GLOW;
        private DeferredBlock<Block> TWILL;
        private DeferredBlock<Block> TRANSPARENT;
        private Set<DeferredBlock<? extends Block>> blockCache = new HashSet<>();
        public Builder(String id, MapColor color) {
            this.id = id;
            this.color = color;
            this.BASE = BPBlocks.registerWithUncommonItem(id + "_strong_interaction_block", () -> new Block(BPBlocks.bedrockLike().mapColor(color)));
        }
        public Builder defaultSet(){
            tile().slick().glow().twill().transparent();
            return this;
        }

        public Builder tile() {
            this.TILE = BPBlocks.registerWithRareItem(id + "_strong_interaction_tile", () -> new Block(BPBlocks.bedrockLike().mapColor(color)));
            blockCache.add(TILE);
            return this;
        }
        public Builder slick() {
            this.SLICK = BPBlocks.registerWithUncommonItem(id + "_slick_strong_interaction_block", () -> new Block(BPBlocks.bedrockLike().mapColor(color).friction(1F)));
            blockCache.add(SLICK);
            return this;
        }
        public Builder glow() {
            this.GLOW = BPBlocks.registerWithUncommonItem(id + "_glow_strong_interaction_block", () -> new Block(BPBlocks.bedrockLike().mapColor(color).lightLevel(l->15)));
            blockCache.add(GLOW);
            return this;
        }
        public Builder twill() {
            this.TWILL = BPBlocks.registerWithUncommonItem(id + "_twill_strong_interaction_block", () -> new Block(BPBlocks.bedrockLike().mapColor(color).friction(0.4F)));
            blockCache.add(TWILL);
            return this;
        }
        public Builder transparent() {
            this.TRANSPARENT = BPBlocks.registerWithUncommonItem(id + "_transparent_strong_interaction_block", () -> new TransparentBlock(BPBlocks.bedrockLike().mapColor(color).noOcclusion()));
            blockCache.add(TRANSPARENT);
            return this;
        }

        public StrongInteractionBlockSet build() {
            StrongInteractionBlockSet blockSet = new StrongInteractionBlockSet(this);
            BLOCK_SETS.add(blockSet);
            this.blockCache = null;
            return blockSet;
        }
    }
}
