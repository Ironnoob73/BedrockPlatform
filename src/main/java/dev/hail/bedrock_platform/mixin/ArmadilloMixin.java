package dev.hail.bedrock_platform.mixin;

import dev.hail.bedrock_platform.item.BPItems;
import dev.hail.bedrock_platform.mixed.SelfGetter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.entity.LivingEntity.getSlotForHand;

@Mixin(Armadillo.class)
public class ArmadilloMixin implements SelfGetter<Armadillo> {
    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void brushWithSawBladeBrush(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> ci) {
        Armadillo self = bedrockPlatform$self();
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.is(BPItems.SAW_BLADE_COMB) && self.brushOffScute()) {
            itemstack.hurtAndBreak(1, pPlayer, getSlotForHand(pHand));
            ci.setReturnValue(InteractionResult.sidedSuccess(self.level().isClientSide));
        }
    }
}
