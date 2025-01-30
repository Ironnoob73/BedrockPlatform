package dev.hail.bedrock_platform.Items;

import dev.hail.bedrock_platform.Data.BPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ObsidianWrench extends Item {
    public ObsidianWrench(Properties pProperties) {
        super(pProperties);
    }

    public @NotNull InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        BlockState state = context.getLevel()
                .getBlockState(context.getClickedPos());

        if (player!=null){
            if (player.isShiftKeyDown() && state.is(BPTags.OBSIDIAN_WRENCH_CAN_REMOVE)){
                return onRemove(context);
            }
        }
        return super.useOn(context);
    }
    private InteractionResult onRemove(UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        if (!(world instanceof ServerLevel))
            return InteractionResult.SUCCESS;
        if (player != null && !player.isCreative())
            Block.getDrops(state, (ServerLevel) world, pos, world.getBlockEntity(pos), player, context.getItemInHand())
                    .forEach(itemStack -> player.getInventory().placeItemBackInInventory(itemStack));
        state.spawnAfterBreak((ServerLevel) world, pos, ItemStack.EMPTY, context.getItemInHand().getEnchantmentLevel(world.holderOrThrow(Enchantments.SILK_TOUCH))==0);
        world.destroyBlock(pos, false);
        return InteractionResult.SUCCESS;
    }
    public boolean isEnchantable(@NotNull ItemStack itemstack) {
        return true;
    }
    @Override
    public int getEnchantmentValue() {
        return 1;
    }
}
