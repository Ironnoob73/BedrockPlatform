package dev.hail.bedrock_platform.Items;

import dev.hail.bedrock_platform.BlockExchangeRecipe.BERInput;
import dev.hail.bedrock_platform.BlockExchangeRecipe.BERecipe;
import dev.hail.bedrock_platform.Data.BPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Optional;

import static dev.hail.bedrock_platform.BlockExchangeRecipe.BERecipe.BLOCK_EXCHANGE;

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
            }/*else if (!player.isShiftKeyDown()){
                return onReExchange(context);
            }*/
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
    /*private  InteractionResult onReExchange(UseOnContext context){
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        RecipeManager recipes = level.getRecipeManager();
        BERInput input = new BERInput(state, itemStack);
        Optional<RecipeHolder<BERecipe>> optional = recipes.getRecipeFor(
                // The recipe type.
                BLOCK_EXCHANGE.get(),
                input,
                level
        );
        BlockState resultState = optional
                .map(RecipeHolder::value)
                .map(e -> e.assembleBlock(input, level.registryAccess()))
                .orElse(null);
        // If there is a result, break the block and drop the result in the world.
        if (resultState != null) {
            if (player != null && !player.isCreative() && itemStack.getMaxStackSize() != 1)
                itemStack.consume(1,player);
            level.setBlock(pos, resultState,11);
            level.playLocalSound(pos, SoundEvents.NETHERITE_BLOCK_BREAK, SoundSource.BLOCKS,1,1,true);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SCRAPE, UniformInt.of(3, 5));
        }
        return InteractionResult.SUCCESS;
    }*/

    public boolean isEnchantable(@NotNull ItemStack itemstack) {
        return true;
    }
    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public void onDestroyed(ItemEntity pItemEntity) {
        var DestoryResult = new java.util.HashSet<>(Collections.singleton(new ItemStack(Items.CRYING_OBSIDIAN)));
        DestoryResult.add(new ItemStack(Items.END_CRYSTAL));
        DestoryResult.add(new ItemStack(Items.SCULK_SHRIEKER));
        ItemUtils.onContainerDestroyed(pItemEntity, DestoryResult);
    }
}
