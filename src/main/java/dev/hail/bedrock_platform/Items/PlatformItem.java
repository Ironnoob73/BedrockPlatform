package dev.hail.bedrock_platform.Items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

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
        BlockState plat = Blocks.COBBLESTONE_STAIRS.defaultBlockState();
        BlockPos currentPos = pPlayer.getOnPos();
        Direction direction = pPlayer.getDirection();
        UpOrDown upOrDown = UpOrDown.FORWARD;
        if (pPlayer.getXRot() >= 45){
            upOrDown = UpOrDown.DOWN;
        } else if (pPlayer.getXRot() <= -45){
            upOrDown = UpOrDown.UP;
        }
        if (pLevel.getBlockState(currentPos).getBlock() != plat.getBlock()){
            return null;
        }
        while (pLevel.getBlockState(currentPos).getBlock() == plat.getBlock()){
            currentPos = currentPos.mutable().move(direction);
            if (pLevel.getBlockState(currentPos.above()).getBlock() == plat.getBlock()){
                currentPos = currentPos.above();
            } else if (pLevel.getBlockState(currentPos.below()).getBlock() == plat.getBlock()){
                currentPos = currentPos.below();
            }
        }
        if (pLevel.getBlockState(currentPos).canBeReplaced()) {
            if (upOrDown == UpOrDown.UP){
                currentPos = currentPos.above();
            } else if (upOrDown == UpOrDown.DOWN){
                currentPos = currentPos.below();
            }
            return new BlockPlaceContext(pLevel,pPlayer,pUsedHand,pPlayer.getItemInHand(pUsedHand),
                    new BlockHitResult(new Vec3(currentPos.getX() + 0.5,currentPos.getY() + 0.5,currentPos.getZ() + 0.5),
                            direction, currentPos, false));
        }
        return null;
    }

    public enum UpOrDown {
        UP(), FORWARD(), DOWN()
    }
}
