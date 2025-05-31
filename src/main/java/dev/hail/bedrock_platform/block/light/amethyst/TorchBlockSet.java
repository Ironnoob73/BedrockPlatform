package dev.hail.bedrock_platform.block.light.amethyst;

import dev.hail.bedrock_platform.block.BPBlocks;
import dev.hail.bedrock_platform.item.BPItems;
import dev.hail.bedrock_platform.item.TooltipTorchItem;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Supplier;

public class TorchBlockSet {
    private final TorchBlockSet.Builder builder;

    TorchBlockSet(TorchBlockSet.Builder builder) {
        this.builder = builder;
    }
    public static TorchBlockSet.Builder builder(String id, boolean candle, Supplier<TorchBlock> standTorch, Supplier<WallTorchBlock> wallTorch) {
        return new TorchBlockSet.Builder(id, candle, standTorch, wallTorch);
    }
    public DeferredBlock<Block> getStand() {
        return builder.STAND;
    }
    public DeferredBlock<Block> getWall() {
        return builder.WALL;
    }
    public DeferredItem<Item> getItem() {
        return builder.ITEM;
    }

    public static class Builder {
        private final DeferredBlock<Block> STAND;
        private final DeferredBlock<Block> WALL;
        private final DeferredItem<Item> ITEM;
        public Builder(String id, boolean candle, Supplier<TorchBlock> standTorch, Supplier<WallTorchBlock> wallTorch) {
            String candleVar = !candle ? id + "_torch" : id + "_candle";
            this.STAND = BPBlocks.BLOCKS.register(candleVar, standTorch);
            this.WALL = BPBlocks.BLOCKS.register(!candle ? id + "_wall_torch" : id + "_wall_candle", wallTorch);
            this.ITEM = BPItems.ITEMS.register(candleVar, ()->new TooltipTorchItem(STAND.get(), WALL.get(), new Item.Properties(), Direction.DOWN));
        }
        public TorchBlockSet build() {
            return new TorchBlockSet(this);
        }
    }
}
