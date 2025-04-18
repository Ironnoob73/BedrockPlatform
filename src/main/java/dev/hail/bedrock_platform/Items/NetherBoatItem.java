package dev.hail.bedrock_platform.Items;

import dev.hail.bedrock_platform.Entities.NetherBoat;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
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
    public @NotNull Boat getBoat(@NotNull Level pLevel, HitResult pHitResult, @NotNull ItemStack pStack, @NotNull Player pPlayer) {
        Vec3 vec3 = pHitResult.getLocation();
        Boat boat = this.hasChest ? new NetherBoat.NetherChestBoat(pLevel, vec3.x, vec3.y, vec3.z) : new NetherBoat(pLevel, vec3.x, vec3.y, vec3.z);
        if (pLevel instanceof ServerLevel serverlevel) {
            EntityType.<Boat>createDefaultStackConfig(serverlevel, pStack, pPlayer).accept(boat);
        }
        return boat;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, Item.@NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag){
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.translatable("item.bedrock_platform.nether_boat.tooltip").withStyle(ChatFormatting.GRAY));
    }
}
