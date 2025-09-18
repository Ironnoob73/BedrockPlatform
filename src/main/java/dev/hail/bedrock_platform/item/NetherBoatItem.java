package dev.hail.bedrock_platform.item;

import dev.hail.bedrock_platform.entity.NetherBoat;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NetherBoatItem extends BoatItem {
    private final boolean hasChest;
    public NetherBoatItem(boolean pHasChest, Boat.Type pType, Properties pProperties) {
        super(pHasChest, pType, pProperties);
        this.hasChest = pHasChest;
    }
    @Override
    public @NotNull Boat getBoat(@NotNull Level pLevel, HitResult pHitResult) {
        Vec3 vec3 = pHitResult.getLocation();
        return this.hasChest ? new NetherBoat.NetherChestBoat(pLevel, vec3.x, vec3.y, vec3.z) : new NetherBoat(pLevel, vec3.x, vec3.y, vec3.z);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, Level pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag){
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.translatable("item.bedrock_platform.nether_boat.tooltip").withStyle(ChatFormatting.GRAY));
    }
}
