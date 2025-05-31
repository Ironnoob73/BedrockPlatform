package dev.hail.bedrock_platform.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CrateBlock extends BaseEntityBlock {
    public static final MapCodec<CrateBlock> CODEC = simpleCodec(CrateBlock::new);

    @Override
    public MapCodec<CrateBlock> codec() {
        return CODEC;
    }

    protected CrateBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof CrateBlockEntity) {
                pPlayer.openMenu((CrateBlockEntity) blockentity);
                pPlayer.awardStat(Stats.OPEN_BARREL);
                PiglinAi.angerNearbyPiglins(pPlayer, true);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        Containers.dropContentsOnDestroy(pState, pNewState, pLevel, pPos);
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @javax.annotation.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CrateBlockEntity(pPos, pState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(pLevel.getBlockEntity(pPos));
    }


    public static class CrateBlockEntity extends RandomizableContainerBlockEntity {
        private NonNullList<ItemStack> items = NonNullList.withSize(54, ItemStack.EMPTY);
        public ItemStackHandler inventory;

        public CrateBlockEntity(BlockPos pPos, BlockState pBlockState) {
            super(BPBlocks.GEODE_CRATE_BE.get(), pPos, pBlockState);
            this.initCap();
        }

        private void initCap() {
            this.inventory = new ItemStackHandler(items) {
                @Override
                protected void onContentsChanged(int slot) {
                    super.onContentsChanged(slot);
                    CrateBlockEntity.this.setChanged();
                }
            };
        }

        @Override
        protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
            super.saveAdditional(pTag, pRegistries);
            if (!this.trySaveLootTable(pTag)) {
                ContainerHelper.saveAllItems(pTag, this.items, pRegistries);
            }
        }

        @Override
        protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
            super.loadAdditional(pTag, pRegistries);
            this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
            if (!this.tryLoadLootTable(pTag)) {
                ContainerHelper.loadAllItems(pTag, this.items, pRegistries);
            }
            this.initCap();
        }

        @Override
        public int getContainerSize() {
            return 54;
        }

        @Override
        protected NonNullList<ItemStack> getItems() {
            return this.items;
        }

        @Override
        protected void setItems(NonNullList<ItemStack> pItems) {
            this.items = pItems;
            this.initCap();
        }

        @Override
        protected Component getDefaultName() {
            return Component.translatable("container.bedrock_platform.crate");
        }

        @Override
        protected AbstractContainerMenu createMenu(int pId, Inventory pPlayer) {
            return ChestMenu.sixRows(pId, pPlayer, this);
        }
    }
}
