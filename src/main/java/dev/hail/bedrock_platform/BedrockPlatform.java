package dev.hail.bedrock_platform;

import dev.hail.bedrock_platform.BlockExchangeRecipe.BERSerializer;
import dev.hail.bedrock_platform.BlockExchangeRecipe.BERecipe;
import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Events.BPEvents;
import dev.hail.bedrock_platform.Items.BPItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(BedrockPlatform.MODID)
public class BedrockPlatform
{
    public static final String MODID = "bedrock_platform";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("bedrock_platform.name"))
            .icon(() -> BPBlocks.BEDROCK_PLATFORM_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(BPItems.OBSIDIAN_WRENCH.get());
                output.accept(BPBlocks.BEDROCK_PLATFORM_ITEM.get());
                output.accept(BPBlocks.LUMINOUS_BEDROCK_PLATFORM_ITEM.get());
                output.accept(BPBlocks.TWILL_BEDROCK_PLATFORM_ITEM.get());
                output.accept(BPItems.SCULK_RIB.get());
            }).build());

    public BedrockPlatform(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);

        BPBlocks.BLOCKS.register(modEventBus);
        BPItems.ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        BERecipe.RECIPE_TYPES.register(modEventBus);
        BERSerializer.RECIPE_SERIALIZERS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new BPEvents());

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }
    }
}
