package dev.hail.bedrock_platform.Events;

import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Blocks.Light.Amethyst.AmethystCandleBlock;
import dev.hail.bedrock_platform.Blocks.Light.Amethyst.AmethystCandleLogic;
import dev.hail.bedrock_platform.Blocks.Light.Amethyst.AmethystLanternBlock;
import dev.hail.bedrock_platform.Blocks.Light.Amethyst.WaxedAmethystLanternBlock;
import dev.hail.bedrock_platform.Config;
import dev.hail.bedrock_platform.Particle.BPParticles;
import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERInput;
import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERecipe;
import dev.hail.bedrock_platform.World.BPDamageTypes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

import javax.annotation.Nullable;
import java.util.Optional;

import static dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERecipe.BLOCK_EXCHANGE;
import static net.minecraft.world.level.block.Block.UPDATE_ALL;

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
        BERInput input = new BERInput(blockState, itemStack);
        Optional<RecipeHolder<BERecipe>> optional = recipes.getRecipeFor(
                BLOCK_EXCHANGE.get(),
                input,
                level
        );
        BlockState resultState = optional
                .map(RecipeHolder::value)
                .map(e -> e.assembleBlock(level.registryAccess()))
                .orElse(null);
        if (resultState != null) {
            if (player != null && !player.isCreative() && itemStack.getMaxStackSize() != 1)
                itemStack.consume(1,player);
            if (resultState.getBlock() instanceof AmethystLanternBlock || resultState.getBlock() instanceof WaxedAmethystLanternBlock){
                resultState = resultState.setValue(AmethystCandleBlock.LIGHT, AmethystCandleLogic.getLightFromEnvironment(level, pos));
            }
            level.setBlock(pos, resultState, UPDATE_ALL);
            level.getBlockState(pos).onPlace(level,pos,resultState,false);
            level.playLocalSound(pos, SoundEvents.NETHERITE_BLOCK_BREAK, SoundSource.BLOCKS,1,1,true);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, BPParticles.BLOCK_EXCHANGE.get(), UniformInt.of(1, 1));
            event.cancelWithResult(ItemInteractionResult.sidedSuccess(level.isClientSide));
        }
    }

    @SubscribeEvent
    public void strongInteractionExplode(UseItemOnBlockEvent event){
        if (event.getUsePhase() != UseItemOnBlockEvent.UsePhase.BLOCK) return;
        UseOnContext context = event.getUseOnContext();
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack itemStack = null;
        if (player != null) {
            itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        }
        if (itemStack != null && itemStack.is(Items.END_CRYSTAL)
                && level.getBlockState(pos).is(Blocks.BEDROCK)
                && level.getBlockState(pos.east()).is(BPBlocks.ENCAPSULATED_END_PORTAL_FRAME)
                && level.getBlockState(pos.west()).is(BPBlocks.ENCAPSULATED_END_PORTAL_FRAME)
                && level.getBlockState(pos.north()).is(BPBlocks.ENCAPSULATED_END_PORTAL_FRAME)
                && level.getBlockState(pos.south()).is(BPBlocks.ENCAPSULATED_END_PORTAL_FRAME)) {
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, itemStack);
            }
            if (!player.isCreative())
                itemStack.consume(1, player);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            level.setBlock(pos.east(), Blocks.AIR.defaultBlockState(), 11);
            level.setBlock(pos.west(), Blocks.AIR.defaultBlockState(), 11);
            level.setBlock(pos.north(), Blocks.AIR.defaultBlockState(), 11);
            level.setBlock(pos.south(), Blocks.AIR.defaultBlockState(), 11);
            level.explode(null, new DamageSource(
                    level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(BPDamageTypes.STRONG_INTERACTION_EXPLOSION),
                            null, null, null), null,
                    pos.getX(), pos.getY(), pos.getZ(), (float)Config.strongInteractionExplodesRadius, false, Level.ExplosionInteraction.TNT);
            strongInteractionExplode(level,null, new DamageSource(
                    level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(BPDamageTypes.STRONG_INTERACTION_EXPLOSION),
                            null, null, null), null,
                    pos.getX(), pos.getY(), pos.getZ(), (float)Config.strongInteractionProduceRadius, false, Level.ExplosionInteraction.TNT,
                    null,null,null);
            event.cancelWithResult(ItemInteractionResult.SUCCESS);
        }
    }

    public StrongInteractionExplosion strongInteractionExplode(
            Level level,
            @Nullable Entity pSource,
            @Nullable DamageSource pDamageSource,
            @Nullable ExplosionDamageCalculator pDamageCalculator,
            double pX,
            double pY,
            double pZ,
            float pRadius,
            boolean pFire,
            Level.ExplosionInteraction pExplosionInteraction,
            ParticleOptions pSmallExplosionParticles,
            ParticleOptions pLargeExplosionParticles,
            Holder<SoundEvent> pExplosionSound
    ) {
        StrongInteractionExplosion.BlockInteraction explosion$blockinteraction = switch (pExplosionInteraction) {
            case NONE -> Explosion.BlockInteraction.KEEP;
            case BLOCK -> this.getDestroyType(GameRules.RULE_BLOCK_EXPLOSION_DROP_DECAY,level);
            case MOB -> net.neoforged.neoforge.event.EventHooks.canEntityGrief(level, pSource)
                    ? this.getDestroyType(GameRules.RULE_MOB_EXPLOSION_DROP_DECAY,level)
                    : Explosion.BlockInteraction.KEEP;
            case TNT -> this.getDestroyType(GameRules.RULE_TNT_EXPLOSION_DROP_DECAY,level);
            case TRIGGER -> Explosion.BlockInteraction.TRIGGER_BLOCK;
        };
        StrongInteractionExplosion explosion = new StrongInteractionExplosion(
                level,
                pSource,
                pDamageSource,
                pDamageCalculator,
                pX,
                pY,
                pZ,
                pRadius,
                pFire,
                explosion$blockinteraction,
                pSmallExplosionParticles,
                pLargeExplosionParticles,
                pExplosionSound
        );
        if (net.neoforged.neoforge.event.EventHooks.onExplosionStart(level, explosion)) return explosion;
        explosion.explode();
        explosion.finalizeExplosion(false);
        return explosion;
    }

    private Explosion.BlockInteraction getDestroyType(GameRules.Key<GameRules.BooleanValue> pGameRule,Level level) {
        return this.getGameRules(level).getBoolean(pGameRule) ? Explosion.BlockInteraction.DESTROY_WITH_DECAY : Explosion.BlockInteraction.DESTROY;
    }
    public GameRules getGameRules(Level level) {
        return level.getLevelData().getGameRules();
    }

}
