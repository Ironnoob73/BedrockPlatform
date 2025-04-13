package dev.hail.bedrock_platform.Items;

import com.google.common.collect.Lists;
import dev.hail.bedrock_platform.Blocks.PlatformBlock;
import dev.hail.bedrock_platform.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class PlatformItem extends BlockItem {
    public PlatformItem(Block pBlock, Item.Properties pProperties) {
        super(pBlock, pProperties);
    }
    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        if (pContext.isSecondaryUseActive()){
            return super.useOn(pContext);
        } else {
            return use(pContext.getLevel(), Objects.requireNonNull(pContext.getPlayer()), pContext.getHand()).getResult();
        }
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        BlockPlaceContext placeContext = setPlatformBlock(pLevel, pPlayer, pUsedHand);
        if (placeContext != null){
            place(placeContext);
            return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
        }
        return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
    }
    public BlockPlaceContext setPlatformBlock(@NotNull Level pLevel, Player pPlayer,  @NotNull InteractionHand pUsedHand){
        BlockPos currentPos = pPlayer.getOnPos();
        Direction direction = pPlayer.getDirection();
        UpOrDown upOrDown = UpOrDown.FORWARD;
        if (pPlayer.getXRot() >= 45){
            upOrDown = UpOrDown.DOWN;
        } else if (pPlayer.getXRot() <= -45){
            upOrDown = UpOrDown.UP;
        }
        if (!(pLevel.getBlockState(currentPos).getBlock() instanceof PlatformBlock)){
            if (pPlayer instanceof ServerPlayer){
                ((ServerPlayer)pPlayer).sendSystemMessage(Component.translatable("block.bedrock_platform.platform.cant_use").withStyle(ChatFormatting.RED), true);
            }
            return null;
        }
        int distance = 0;
        while (pLevel.getBlockState(currentPos).getBlock() instanceof PlatformBlock){
            if(distance > Config.platformExtensionDistance){
                if (pPlayer instanceof ServerPlayer){
                    ((ServerPlayer)pPlayer).sendSystemMessage(Component.translatable("block.bedrock_platform.platform.too_long", (Config.platformExtensionDistance)).withStyle(ChatFormatting.RED), true);
                }
                return null;
            }
            currentPos = currentPos.mutable().move(direction);
            if (pLevel.getBlockState(currentPos.above()).getBlock() instanceof PlatformBlock){
                currentPos = currentPos.above();
            } else if (pLevel.getBlockState(currentPos.below()).getBlock() instanceof PlatformBlock){
                currentPos = currentPos.below();
            }
            distance += 1;
        }
        BlockState previousBlock = pLevel.getBlockState(currentPos.mutable().move(direction.getOpposite()));
        boolean isPlatform = previousBlock.getBlock() instanceof PlatformBlock;
        if (pLevel.getBlockState(currentPos).canBeReplaced()) {
            if (upOrDown == UpOrDown.UP
                    && isPlatform
                    &&(previousBlock.getValue(PlatformBlock.HALF) == Half.TOP
                    || previousBlock.getValue(PlatformBlock.FACING) != direction.getOpposite())){
                currentPos = currentPos.above();
            } else if (isPlatform
                    &&((upOrDown == UpOrDown.DOWN
                    && (previousBlock.getValue(PlatformBlock.HALF) == Half.BOTTOM
                    && previousBlock.getValue(PlatformBlock.FACING) == direction.getOpposite()))
                    ||((upOrDown == UpOrDown.FORWARD)
                    && previousBlock.getValue(PlatformBlock.HALF) == Half.BOTTOM
                    && previousBlock.getValue(PlatformBlock.FACING) == direction.getOpposite()))){
                currentPos = currentPos.below();
            }
            int j = pLevel.getMaxBuildHeight();
            if (pPlayer instanceof ServerPlayer && currentPos.getY() >= j) {
                ((ServerPlayer)pPlayer).sendSystemMessage(Component.translatable("build.tooHigh", j - 1).withStyle(ChatFormatting.RED), true);
                return null;
            }
            return new BlockPlaceContext(pLevel,pPlayer,pUsedHand,pPlayer.getItemInHand(pUsedHand),
                    new BlockHitResult(new Vec3(currentPos.getX(),currentPos.getY() + (upOrDown == UpOrDown.FORWARD ? 0.7 : 0),currentPos.getZ()),
                            direction, currentPos, false));
        }
        return null;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, Item.@NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag){
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        List<Component> list = Lists.newArrayList();
        list.add(Component.translatable("block.bedrock_platform.platform.tooltip").withStyle(ChatFormatting.GRAY));
        list.add(Component.empty());
        list.add(Component.translatable("block.bedrock_platform.platform.tooltip_0").withStyle(ChatFormatting.GRAY));
        list.add(CommonComponents.space().append(Component.translatable("block.bedrock_platform.platform.tooltip_1").withStyle(ChatFormatting.BLUE)));
        list.add(CommonComponents.space().append(Component.translatable("block.bedrock_platform.platform.tooltip_2").withStyle(ChatFormatting.BLUE)));
        list.add(CommonComponents.space().append(Component.translatable("block.bedrock_platform.platform.tooltip_3").withStyle(ChatFormatting.BLUE)));
        list.add(Component.translatable("block.bedrock_platform.platform.tooltip_4").withStyle(ChatFormatting.GRAY));
        list.add(CommonComponents.space().append(Component.translatable("block.bedrock_platform.platform.tooltip_5").withStyle(ChatFormatting.BLUE)));
        pTooltipComponents.addAll(list);
    }

    public enum UpOrDown {
        UP(), FORWARD(), DOWN()
    }
}
