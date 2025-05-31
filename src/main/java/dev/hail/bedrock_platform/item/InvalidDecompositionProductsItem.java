package dev.hail.bedrock_platform.item;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InvalidDecompositionProductsItem extends Item {
    public InvalidDecompositionProductsItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, Item.@NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag){
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        List<Component> list = Lists.newArrayList();
        list.add(Component.translatable("item.bedrock_platform.ineffective_decomposition_products.tooltip").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        if(pTooltipFlag.hasShiftDown()){
            list.add(Component.empty());
            list.add(Component.translatable("item.bedrock_platform.ineffective_decomposition_products.tooltip_0").withStyle(ChatFormatting.GRAY));
            list.add(CommonComponents.space().append(Component.translatable("item.bedrock_platform.ineffective_decomposition_products.tooltip_1").withStyle(ChatFormatting.RED)));
            list.add(CommonComponents.space().append(Component.translatable("item.bedrock_platform.ineffective_decomposition_products.tooltip_2").withStyle(ChatFormatting.RED)));
            list.add(CommonComponents.space().append(Component.translatable("item.bedrock_platform.ineffective_decomposition_products.tooltip_3").withStyle(ChatFormatting.RED)));
            list.add(CommonComponents.space().append(Component.translatable("item.bedrock_platform.ineffective_decomposition_products.tooltip_4").withStyle(ChatFormatting.RED)));
            list.add(CommonComponents.space().append(Component.translatable("item.bedrock_platform.ineffective_decomposition_products.tooltip_5").withStyle(ChatFormatting.RED)));
            list.add(CommonComponents.space().append(Component.translatable("item.bedrock_platform.ineffective_decomposition_products.tooltip_6").withStyle(ChatFormatting.RED)));
            list.add(Component.translatable("item.bedrock_platform.ineffective_decomposition_products.tooltip_7").withStyle(ChatFormatting.DARK_GRAY));
        } else {
            list.add(Component.translatable("tooltip.bedrock_platform.shift").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        }
        pTooltipComponents.addAll(list);
    }
}
