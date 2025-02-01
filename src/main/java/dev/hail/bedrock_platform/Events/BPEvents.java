package dev.hail.bedrock_platform.Events;

import dev.hail.bedrock_platform.BlockExchangeRecipe.BERInput;
import dev.hail.bedrock_platform.BlockExchangeRecipe.BERecipe;
import dev.hail.bedrock_platform.Particle.BPParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
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
        RecipeManager recipes = level.getRecipeManager();
        Player player = event.getPlayer();
        ItemStack itemStack = null;
        if (player != null) {
            itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        }
        // Create an input and query the recipe.
        BERInput input = new BERInput(blockState, itemStack);
        Optional<RecipeHolder<BERecipe>> optional = recipes.getRecipeFor(
                // The recipe type.
                BLOCK_EXCHANGE.get(),
                input,
                level
        );
        BlockState resultState = optional
                .map(RecipeHolder::value)
                .map(e -> e.assembleBlock(level.registryAccess()))
                .orElse(null);
        // If there is a result, break the block and drop the result in the world.
        if (resultState != null) {
            if (player != null && !player.isCreative() && itemStack.getMaxStackSize() != 1)
                itemStack.consume(1,player);
            level.setBlock(pos, resultState,11);
            level.playLocalSound(pos, SoundEvents.NETHERITE_BLOCK_BREAK, SoundSource.BLOCKS,1,1,true);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, BPParticles.BLOCK_EXCHANGE.get(), UniformInt.of(1, 1));
            event.cancelWithResult(ItemInteractionResult.sidedSuccess(level.isClientSide));
        }
    }
}
