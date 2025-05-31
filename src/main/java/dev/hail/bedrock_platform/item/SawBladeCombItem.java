package dev.hail.bedrock_platform.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SawBladeCombItem extends Item {
    public SawBladeCombItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (!pLevel.isClientSide && !pState.is(BlockTags.FIRE)) {
            pStack.hurtAndBreak(1, pEntityLiving, EquipmentSlot.MAINHAND);
        }
        return pState.is(BlockTags.LEAVES)
                || pState.is(Blocks.COBWEB)
                || pState.is(Blocks.SHORT_GRASS)
                || pState.is(Blocks.FERN)
                || pState.is(Blocks.DEAD_BUSH)
                || pState.is(Blocks.HANGING_ROOTS)
                || pState.is(Blocks.VINE)
                || pState.is(Blocks.TRIPWIRE)
                || pState.is(BlockTags.WOOL);
    }
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, net.minecraft.world.InteractionHand hand) {
        if (entity instanceof net.neoforged.neoforge.common.IShearable target) {
            BlockPos pos = entity.blockPosition();
            boolean isClient = entity.level().isClientSide();
            if (target.isShearable(player, stack, entity.level(), pos)) {
                List<ItemStack> drops = target.onSheared(player, stack, entity.level(), pos);
                if (!isClient) {
                    for(ItemStack drop : drops) {
                        target.spawnShearedDrop(entity.level(), pos, drop);
                    }
                }
                entity.gameEvent(GameEvent.SHEAR, player);
                if (!isClient) {
                    stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                }
                return InteractionResult.sidedSuccess(isClient);
            }
        }
        return InteractionResult.PASS;
    }
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Direction hitDir = pContext.getClickedFace();
        ItemStack itemstack = pContext.getItemInHand();
        EquipmentSlot equipmentSlot = LivingEntity.getSlotForHand(pContext.getHand());
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState blockstate = level.getBlockState(blockpos);
        BlockState blockstate1 = blockstate.getToolModifiedState(pContext, net.neoforged.neoforge.common.ItemAbilities.SHEARS_TRIM, false);
        BlockState blockstate2 = blockstate.getToolModifiedState(pContext, net.neoforged.neoforge.common.ItemAbilities.FIRESTARTER_LIGHT, false);
        if (blockstate1 != null) {
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
            }
            level.setBlockAndUpdate(blockpos, blockstate1);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(pContext.getPlayer(), blockstate1));
            if (player != null) {
                itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(pContext.getHand()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (pContext.isSecondaryUseActive()){
            if (blockstate2 == null) {
                BlockPos blockpos1 = blockpos.relative(hitDir);
                if (BaseFireBlock.canBePlacedAt(level, blockpos1, pContext.getHorizontalDirection())) {
                    level.playSound(player, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                    BlockState blockstateF = BaseFireBlock.getState(level, blockpos1);
                    level.setBlock(blockpos1, blockstateF, 11);
                    level.gameEvent(player, GameEvent.BLOCK_PLACE, blockpos);
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockpos1, itemstack);
                        itemstack.hurtAndBreak(1, player, equipmentSlot);
                    }

                    return InteractionResult.SUCCESS;
                }
            } else {
                level.playSound(player, blockpos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                level.setBlock(blockpos, blockstate2, 11);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockpos);
                if (player != null) {
                    itemstack.hurtAndBreak(1, player, equipmentSlot);
                }

                return InteractionResult.SUCCESS;
            }
        } else if (player != null) {
            if (blockstate.getBlock() == Blocks.TNT){
                blockstate.onCaughtFire(level, blockpos, hitDir, player);
                level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 11);
                itemstack.hurtAndBreak(1, player, equipmentSlot);
                player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
                return InteractionResult.SUCCESS;
            } else if (!level.isClientSide() && level.getBlockEntity(blockpos) instanceof BrushableBlockEntity brushableblockentity) {
                brushableblockentity.hitDirection = hitDir;
                brushableblockentity.brushingCompleted(player);
                itemstack.hurtAndBreak(1, player, equipmentSlot);
            }
        }
        return super.useOn(pContext);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return Stream.of(ItemAbilities.SHEARS_DIG,
                        ItemAbilities.SHEARS_HARVEST,
                        ItemAbilities.SHEARS_REMOVE_ARMOR,
                        ItemAbilities.SHEARS_CARVE,
                        ItemAbilities.SHEARS_DISARM,
                        ItemAbilities.SHEARS_TRIM,
                        ItemAbilities.FIRESTARTER_LIGHT,
                        ItemAbilities.BRUSH_BRUSH)
                .collect(Collectors.toCollection(Sets::newIdentityHashSet))
                .contains(itemAbility);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, Item.@NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag){
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        List<Component> list = Lists.newArrayList();
        list.add(Component.translatable("item.bedrock_platform.saw_blade_comb.tooltip").withStyle(ChatFormatting.GRAY));
        if(pTooltipFlag.hasShiftDown()){
            list.add(Component.empty());
            list.add(Component.translatable("tooltip.bedrock_platform.sneak_and_use").withStyle(ChatFormatting.GRAY));
            list.add(CommonComponents.space().append(Component.translatable("item.bedrock_platform.saw_blade_comb.tooltip_1").withStyle(ChatFormatting.BLUE)));
            list.add(Component.translatable("item.bedrock_platform.saw_blade_comb.tooltip_2").withStyle(ChatFormatting.GRAY));
            list.add(CommonComponents.space().append(Component.translatable("item.bedrock_platform.saw_blade_comb.tooltip_3").withStyle(ChatFormatting.BLUE)));
            list.add(Component.translatable("item.bedrock_platform.saw_blade_comb.tooltip_4").withStyle(ChatFormatting.GRAY));
            list.add(CommonComponents.space().append(Component.translatable("item.bedrock_platform.saw_blade_comb.tooltip_5").withStyle(ChatFormatting.BLUE)));
            list.add(Component.translatable("item.bedrock_platform.saw_blade_comb.tooltip_6").withStyle(ChatFormatting.GRAY));
        } else {
            list.add(Component.translatable("tooltip.bedrock_platform.shift").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        }
        pTooltipComponents.addAll(list);
    }
}
