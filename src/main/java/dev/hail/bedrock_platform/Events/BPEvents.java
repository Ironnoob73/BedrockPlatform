package dev.hail.bedrock_platform.Events;

import dev.hail.bedrock_platform.BlockExchangeRecipe.BERInput;
import dev.hail.bedrock_platform.BlockExchangeRecipe.BERecipe;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

import java.util.Optional;

import static dev.hail.bedrock_platform.BlockExchangeRecipe.BERecipe.BLOCK_EXCHANGE;

public class BPEvents {
    @SubscribeEvent
    public void blockReinforceEvent(UseItemOnBlockEvent event){
        if (event.getUsePhase() != UseItemOnBlockEvent.UsePhase.BLOCK) return;
        UseOnContext context = event.getUseOnContext();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        ItemStack itemStack = context.getItemInHand();
        RecipeManager recipes = level.getRecipeManager();
        //if (itemStack.is(Items.NETHERITE_SCRAP) && level.getBlockState(pos).is(Blocks.BEDROCK)){
        //    blockExchange(BPBlocks.BEDROCK_PLATFORM.get().defaultBlockState(),player,blockpos,itemstack,level,event);
        //}
        // Create an input and query the recipe.
        BERInput input = new BERInput(blockState, itemStack);
        Optional<RecipeHolder<BERecipe>> optional = recipes.getRecipeFor(
                // The recipe type.
                BLOCK_EXCHANGE.get(),
                input,
                level
        );
        ItemStack result = optional
                .map(RecipeHolder::value)
                .map(e -> e.assemble(input, level.registryAccess()))
                .orElse(ItemStack.EMPTY);
        // If there is a result, break the block and drop the result in the world.
        if (!result.isEmpty()) {
            level.removeBlock(pos, false);
            // If the level is not a server level, don't spawn the entity.
            if (!level.isClientSide()) {
                ItemEntity entity = new ItemEntity(level,
                        // Center of pos.
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        result);
                level.addFreshEntity(entity);
            }
            // Cancel the event to stop the interaction pipeline.
            event.cancelWithResult(ItemInteractionResult.sidedSuccess(level.isClientSide));
        }
    }

    public void blockExchange (BlockState result, Player player, BlockPos blockpos, ItemStack itemstack, Level level, UseItemOnBlockEvent event){
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
        }
        itemstack.consume(1,player);
        level.setBlock(blockpos, result,11);
        level.playLocalSound(blockpos, SoundEvents.NETHERITE_BLOCK_BREAK, SoundSource.BLOCKS,1,1,true);
        ParticleUtils.spawnParticlesOnBlockFaces(level, blockpos, ParticleTypes.SCRAPE, UniformInt.of(3, 5));
        event.cancelWithResult(ItemInteractionResult.SUCCESS);
    }
}
