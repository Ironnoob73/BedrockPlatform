package dev.hail.bedrock_platform.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
@ParametersAreNonnullByDefault
public class SimpleTooltipBlockItem extends BlockItem {
    public String tooltip;
    public SimpleTooltipBlockItem(Block pBlock, Properties pProperties, String tooltip) {
        super(pBlock, pProperties);
        this.tooltip = tooltip;
    }
    @Override
    public void appendHoverText( ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag){
        pTooltipComponents.add(Component.translatable(tooltip).withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}
