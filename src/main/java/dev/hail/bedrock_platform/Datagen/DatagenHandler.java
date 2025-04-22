package dev.hail.bedrock_platform.Datagen;

import dev.hail.bedrock_platform.BedrockPlatform;
import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Blocks.StrongInteractionBlockSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = BedrockPlatform.MODID)
public class DatagenHandler {
    protected static List<DeferredBlock<Block>> cubeAllBlockList = new ArrayList<>();
    protected static List<StrongInteractionBlockSet> colorSIList = new ArrayList<>();

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        // INIT
        cubeAllBlockList.add(BPBlocks.BEDROCK_PLATFORM);
        cubeAllBlockList.add(BPBlocks.LUMINOUS_BEDROCK_PLATFORM);
        cubeAllBlockList.add(BPBlocks.TWILL_BEDROCK_PLATFORM);
        cubeAllBlockList.add(BPBlocks.SOLID_END_VOID);
        cubeAllBlockList.add(BPBlocks.GHAST_TEAR_GLASS);
        colorSIList.add(BPBlocks.RED_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.ORANGE_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.YELLOW_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.GREEN_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.CYAN_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.BLUE_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.PURPLE_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.WHITE_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.GRAY_SI_BLOCK_SET);
        colorSIList.add(BPBlocks.BLACK_SI_BLOCK_SET);

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new BPItemModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new BPBlockModelProvider(output, helper));
        generator.addProvider(event.includeServer(), new BPLootTableProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new BPRecipeProvider(output, lookupProvider));
    }
}
