package dev.hail.bedrock_platform.item;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PreciseNetherPortalItem extends BlockItem {
    public PreciseNetherPortalItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, Item.@NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag){
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        List<Component> list = Lists.newArrayList();
        list.add(Component.translatable("block.bedrock_platform.precise_nether_portal.tooltip").withStyle(ChatFormatting.GRAY));
        if(pTooltipFlag.hasShiftDown()){
            list.add(Component.empty());
            list.add(Component.translatable("block.bedrock_platform.precise_nether_portal.tooltip_0").withStyle(ChatFormatting.GRAY));
            list.add(Component.translatable("block.bedrock_platform.precise_nether_portal.tooltip_1").withStyle(ChatFormatting.GRAY));
            list.add(Component.translatable("block.bedrock_platform.precise_nether_portal.tooltip_2").withStyle(ChatFormatting.BLUE));
            list.add(CommonComponents.space().append(Component.translatable("block.bedrock_platform.precise_nether_portal.tooltip_3").withStyle(ChatFormatting.GRAY)));
            list.add(CommonComponents.space().append(Component.translatable("block.bedrock_platform.precise_nether_portal.tooltip_4").withStyle(ChatFormatting.GRAY)));
            list.add(CommonComponents.space().append(Component.translatable("block.bedrock_platform.precise_nether_portal.tooltip_5").withStyle(ChatFormatting.GRAY)));
        } else {
            list.add(Component.translatable("tooltip.bedrock_platform.shift").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        }
        pTooltipComponents.addAll(list);
    }
}
