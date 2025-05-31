package dev.hail.bedrock_platform.datagen;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.block.BPBlocks;
import dev.hail.bedrock_platform.item.BPItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class BPItemModelProvider extends ItemModelProvider {
    ResourceLocation gItemModel = ResourceLocation.withDefaultNamespace("item/generated");
    public BPItemModelProvider(PackOutput gen, ExistingFileHelper helper) {
        super(gen, BedrockPlatform.MODID, helper);
    }
    @Override
    protected void registerModels() {
        genDefault(BPItems.SCULK_RIB);
        genDefault(BPItems.ENCHANT_DUST);
        genDefault(BPItems.BLUE_ICE_CUBE);
        genBlockItem(BPBlocks.STONE_TORCH.getItem());
        genBlockItem(BPBlocks.DEEPSLATE_TORCH.getItem());
        genBlockItem(BPBlocks.AMETHYST_CANDLE.getItem());
        genDefault(BPItems.CRIMSON_BOAT);
        genDefault(BPItems.CRIMSON_CHEST_BOAT);
        genDefault(BPItems.WARPED_BOAT);
        genDefault(BPItems.WARPED_CHEST_BOAT);
    }
    private void genDefault(DeferredItem<Item> item){
        singleTexture(item.getId().getPath(), gItemModel, "layer0",
                BedrockPlatform.modResLocation("item/" + item.getId().getPath()));
    }
    private void genBlockItem(DeferredItem<Item> item){
        singleTexture(item.getId().getPath(), gItemModel, "layer0",
                BedrockPlatform.modResLocation("block/" + item.getId().getPath()));
    }
    private void genBlockItemWithModel(DeferredItem<Item> item){
        withExistingParent(item.getId().getPath(), BedrockPlatform.modResLocation("block/" + item.getId().getPath()));
    }
}