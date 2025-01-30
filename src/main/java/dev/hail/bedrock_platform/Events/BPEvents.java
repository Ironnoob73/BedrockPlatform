package dev.hail.bedrock_platform.Events;

import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Data.BPTags;
import dev.hail.bedrock_platform.Items.BPItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

public class BPEvents {
    @SubscribeEvent
    public void blockReinforceEvent(UseItemOnBlockEvent event){
        var player = event.getPlayer();
        var blockpos = event.getPos();
        var itemstack = event.getItemStack();
        var level = event.getLevel();
        if (itemstack.is(Items.NETHERITE_SCRAP) && level.getBlockState(blockpos).is(Blocks.BEDROCK)){
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
            }
            itemstack.consume(1,player);
            level.setBlock(blockpos, BPBlocks.BEDROCK_PLATFORM.get().defaultBlockState(),11);
            level.playLocalSound(blockpos, SoundEvents.NETHERITE_BLOCK_BREAK, SoundSource.BLOCKS,1,1,true);
            ParticleUtils.spawnParticlesOnBlockFaces(level, blockpos, ParticleTypes.SCRAPE, UniformInt.of(3, 5));
            event.cancelWithResult(ItemInteractionResult.SUCCESS);
        }
    }
}
