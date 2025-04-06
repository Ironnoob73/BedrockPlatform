package dev.hail.bedrock_platform.Blocks.Light.Amethyst;

import dev.hail.bedrock_platform.Blocks.BPBlocks;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

public class WaxedAmethystLanternBlockSet {
    private final WaxedAmethystLanternBlockSet.Builder builder;

    WaxedAmethystLanternBlockSet(WaxedAmethystLanternBlockSet.Builder builder) {
        this.builder = builder;
    }
    public static WaxedAmethystLanternBlockSet.Builder builder(String id, WeatheringCopper.WeatherState weatherState, Block block) {
        return new Builder(id, weatherState, block);
    }

    public DeferredBlock<Block> getUnwaxed() {
        return builder.UNWAXED;
    }
    public DeferredBlock<Block> getWaxed() {
        return builder.WAXED;
    }

    public static class Builder {
        private final DeferredBlock<Block> UNWAXED;
        private final DeferredBlock<Block> WAXED;
        public Builder(String id, WeatheringCopper.WeatherState weatherState, Block block) {
            this.UNWAXED = BPBlocks.registerWithItem(id + "amethyst_lantern", ()->new AmethystLanternBlock(
                    ParticleTypes.GLOW, weatherState, BlockBehaviour.Properties.ofFullCopy(block).lightLevel(AmethystCandleLogic::getLight)
            ));
            this.WAXED = BPBlocks.registerWithItem("waxed_" + id + "amethyst_lantern", ()->new WaxedAmethystLanternBlock(
                    ParticleTypes.GLOW, BlockBehaviour.Properties.ofFullCopy(block).lightLevel(AmethystCandleLogic::getLight)
            ));
        }
        public WaxedAmethystLanternBlockSet build() {
            return new WaxedAmethystLanternBlockSet(this);
        }
    }
}
