package dev.hail.bedrock_platform.Datagen;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Items.BPItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = BedrockPlatform.MODID)
public class DatagenHandler {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new ModelProvider(output, helper));
    }
    public static class ModelProvider extends ItemModelProvider {
        ResourceLocation gItemModel = ResourceLocation.withDefaultNamespace("item/generated");
        public ModelProvider(PackOutput gen, ExistingFileHelper helper) {
            super(gen, BedrockPlatform.MODID, helper);
        }

        @Override
        protected void registerModels() {
            genDefault(BPItems.SCULK_RIB);
            genDefault(BPItems.ENCHANT_DUST);
            genDefault(BPItems.BLUE_ICE_CUBE);
        }

        private void genDefault(DeferredItem<Item> item){
            singleTexture(item.getId().getPath(), gItemModel, "layer0",
                    ResourceLocation.fromNamespaceAndPath(BedrockPlatform.MODID, "item/" + item.getId().getPath()));
        }
    }
}
