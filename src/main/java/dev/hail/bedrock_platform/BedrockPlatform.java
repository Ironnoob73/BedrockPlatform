package dev.hail.bedrock_platform;

import com.mojang.logging.LogUtils;
import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Blocks.SolidEndVoidRender;
import dev.hail.bedrock_platform.Events.BPEvents;
import dev.hail.bedrock_platform.Items.BPItems;
import dev.hail.bedrock_platform.Particle.BPParticles;
import dev.hail.bedrock_platform.Particle.BlockExchangeParticle;
import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERSerializer;
import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERecipe;
import dev.hail.bedrock_platform.Recipe.BlockReductionRecipe.BRRSerializer;
import dev.hail.bedrock_platform.Recipe.BlockReductionRecipe.BRRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.CreativeModeTab;
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
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
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
                output.accept(BPBlocks.SCULK_RIB_BLOCK_ITEM.get());
                output.accept(BPItems.SCULK_RIB.get());
                output.accept(BPBlocks.RED_STRONG_INTERACTION_TILE_ITEM.get());
                output.accept(BPBlocks.YELLOW_STRONG_INTERACTION_TILE_ITEM.get());
                output.accept(BPBlocks.GREEN_STRONG_INTERACTION_TILE_ITEM.get());
                output.accept(BPBlocks.BLUE_STRONG_INTERACTION_TILE_ITEM.get());
                output.accept(BPBlocks.WHITE_STRONG_INTERACTION_TILE_ITEM.get());
                output.accept(BPBlocks.GRAY_STRONG_INTERACTION_TILE_ITEM.get());
                output.accept(BPBlocks.BLACK_STRONG_INTERACTION_TILE_ITEM.get());
            }).build());

    public BedrockPlatform(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::packSetup);

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
        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.SPEC_C);
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SPEC_S);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    public void packSetup(AddPackFindersEvent event) {
        event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(MODID, "resourcepacks/bp_fusion"),
                PackType.CLIENT_RESOURCES,
                Component.translatable("pack.bp_fusion.name"),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP);
        event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(MODID, "resourcepacks/bp_mcpatcher"),
                PackType.CLIENT_RESOURCES,
                Component.translatable("pack.bp_mcpatcher.name"),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP);
        event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(MODID, "resourcepacks/bp_ctm"),
                PackType.CLIENT_RESOURCES,
                Component.translatable("pack.bp_ctm.name"),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP);

    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {
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
