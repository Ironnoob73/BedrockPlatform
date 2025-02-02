package dev.hail.bedrock_platform;

import com.mojang.logging.LogUtils;
import dev.hail.bedrock_platform.Blocks.SolidEndVoidBE;
import dev.hail.bedrock_platform.Blocks.SolidEndVoidRender;
import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERSerializer;
import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERecipe;
import dev.hail.bedrock_platform.Recipe.BlockReductionRecipe.BRRSerializer;
import dev.hail.bedrock_platform.Recipe.BlockReductionRecipe.BRRecipe;
import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Events.BPEvents;
import dev.hail.bedrock_platform.Items.BPItems;
import dev.hail.bedrock_platform.Particle.BPParticles;
import dev.hail.bedrock_platform.Particle.BlockExchangeParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndGatewayRenderer;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
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
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

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
                output.accept(BPBlocks.ORANGE_STRONG_INTERACTION_TILE_ITEM.get());
                output.accept(BPBlocks.CYAN_STRONG_INTERACTION_TILE_ITEM.get());
                output.accept(BPBlocks.PURPLE_STRONG_INTERACTION_TILE_ITEM.get());
                output.accept(BPBlocks.SOLID_END_VOID_ITEM.get());
                output.accept(BPBlocks.GHAST_TEAR_GLASS_ITEM.get());
                output.accept(BPBlocks.ENCAPSULATED_END_PORTAL_FRAME_ITEM.get());
                output.accept(BPItems.SCULK_RIB.get());
            }).build());

    public BedrockPlatform(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);

        BPBlocks.BLOCKS.register(modEventBus);
        BPBlocks.BLOCK_ENTITY_TYPES.register(modEventBus);
        BPItems.ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        BPParticles.PARTICLE_TYPES.register(modEventBus);

        BERecipe.RECIPE_TYPES.register(modEventBus);
        BERSerializer.RECIPE_SERIALIZERS.register(modEventBus);
        BRRecipe.RECIPE_TYPES.register(modEventBus);
        BRRSerializer.RECIPE_SERIALIZERS.register(modEventBus);

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
    }
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }
        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(BPParticles.BLOCK_EXCHANGE.get(), BlockExchangeParticle.BlockExchangeParticleProvider::new);
            event.registerSpriteSet(BPParticles.BLOCK_REDUCTION.get(), BlockExchangeParticle.BlockReductionParticleProvider::new);
        }
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(
                    // The block entity type to register the renderer for.
                    BPBlocks.SOLID_END_VOID_BE.get(),
                    // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer.
                    context -> new SolidEndVoidRender()
            );
        }
    }
}
