package dev.hail.bedrock_platform.Items;

import com.google.common.collect.Lists;
import dev.hail.bedrock_platform.Blocks.BPBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TooltipTorchItem extends StandingAndWallBlockItem {
    public TooltipTorchItem(Block pBlock, Block pWallBlock, Properties pProperties, Direction pAttachmentDirection) {
        super(pBlock, pWallBlock, pProperties, pAttachmentDirection);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, Item.@NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag){
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        if (pStack.is(BPBlocks.STONE_TORCH_ITEM) || pStack.is(BPBlocks.DEEPSLATE_TORCH_ITEM)){
            pTooltipComponents.add(Component.translatable("block.bedrock_platform.stone_torch.tooltip").withStyle(ChatFormatting.GRAY));
        }else if(pStack.is(BPBlocks.AMETHYST_CANDLE_ITEM)){
            List<Component> list = Lists.newArrayList();
            list.add(Component.translatable("block.bedrock_platform.amethyst_candle.tooltip").withStyle(ChatFormatting.GRAY));
            list.add(Component.empty());
            list.add(Component.translatable("block.bedrock_platform.amethyst_candle.tooltip_0").withStyle(ChatFormatting.GRAY));
            list.add(CommonComponents.space().append(Component.translatable("block.bedrock_platform.amethyst_candle.tooltip_1").withStyle(ChatFormatting.BLUE)));
            list.add(Component.translatable("block.bedrock_platform.amethyst_candle.tooltip_2").withStyle(ChatFormatting.GRAY));
            list.add(CommonComponents.space().append(Component.translatable("block.bedrock_platform.amethyst_candle.tooltip_3").withStyle(ChatFormatting.GRAY)));
            list.add(CommonComponents.space().append(CommonComponents.space()).append(Component.translatable("block.bedrock_platform.amethyst_candle.tooltip_1").withStyle(ChatFormatting.BLUE)));
            list.add(CommonComponents.space().append(Component.translatable("block.bedrock_platform.amethyst_candle.tooltip_4").withStyle(ChatFormatting.GRAY)));
            list.add(CommonComponents.space().append(CommonComponents.space()).append(Component.translatable("block.bedrock_platform.amethyst_candle.tooltip_5").withStyle(ChatFormatting.BLUE)));
            list.add(Component.translatable("block.bedrock_platform.amethyst_candle.tooltip_6").withStyle(ChatFormatting.GRAY));
            list.add(CommonComponents.space().append(Component.translatable("block.bedrock_platform.amethyst_candle.tooltip_7").withStyle(ChatFormatting.BLUE)));
            pTooltipComponents.addAll(list);
        }
    }
}
