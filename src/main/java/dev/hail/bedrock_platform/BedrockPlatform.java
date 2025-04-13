package dev.hail.bedrock_platform;

import com.mojang.logging.LogUtils;
import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Blocks.SolidEnd.SolidEndVoidRender;
import dev.hail.bedrock_platform.Events.BPEvents;
import dev.hail.bedrock_platform.Items.BPItems;
import dev.hail.bedrock_platform.Particle.BPParticles;
import dev.hail.bedrock_platform.Particle.BlockExchangeParticle;
import dev.hail.bedrock_platform.Particle.BrighterFlameParticle;
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
import net.neoforged.fml.ModLoadingContext;
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
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("bedrock_platform.name"))
            .icon(() -> BPBlocks.BEDROCK_PLATFORM.asItem().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(BPItems.OBSIDIAN_WRENCH.get());
                output.accept(BPBlocks.BEDROCK_PLATFORM.asItem());
                output.accept(BPBlocks.LUMINOUS_BEDROCK_PLATFORM.asItem());
                output.accept(BPBlocks.TWILL_BEDROCK_PLATFORM.asItem());
                output.accept(BPBlocks.ORANGE_SI_BLOCK_SET.getTile().asItem());
                output.accept(BPBlocks.CYAN_SI_BLOCK_SET.getTile().asItem());
                output.accept(BPBlocks.PURPLE_SI_BLOCK_SET.getTile().asItem());
                output.accept(BPBlocks.SOLID_END_VOID.asItem());
                output.accept(BPBlocks.GHAST_TEAR_GLASS.asItem());
                output.accept(BPBlocks.ENCAPSULATED_END_PORTAL_FRAME.asItem());
                output.accept(BPBlocks.BLACK_SI_BLOCK_SET.getBaseBlock().asItem());
                output.accept(BPItems.SCULK_RIB.get());
                output.accept(BPBlocks.SCULK_RIB_BLOCK.asItem());
                output.accept(BPItems.ENCHANT_DUST.get());
                output.accept(BPItems.BLUE_ICE_CUBE.get());
                output.accept(BPBlocks.GEODE_MOSAIC_TILE.asItem());
                output.accept(BPBlocks.KELP_BLOCK.asItem());
                output.accept(BPBlocks.PERMANENTLY_WETTED_FARMLAND.asItem());
                output.accept(BPBlocks.GLOW_PERMANENTLY_WETTED_FARMLAND.asItem());
                output.accept(BPBlocks.STONE_TORCH.getItem());
                output.accept(BPBlocks.DEEPSLATE_TORCH.getItem());
                output.accept(BPBlocks.AMETHYST_CANDLE.getItem());
                output.accept(BPBlocks.STONE_PLATFORM.asItem());
                output.accept(BPBlocks.TRANSPARENT_STONE_PLATFORM.asItem());
                output.accept(BPBlocks.AMETHYST_LANTERN.getUnwaxed().asItem());
                output.accept(BPBlocks.EXPOSED_AMETHYST_LANTERN.getUnwaxed().asItem());
                output.accept(BPBlocks.WEATHERED_AMETHYST_LANTERN.getUnwaxed().asItem());
                output.accept(BPBlocks.OXIDIZED_AMETHYST_LANTERN.getUnwaxed().asItem());
                output.accept(BPBlocks.AMETHYST_LANTERN.getWaxed().asItem());
                output.accept(BPBlocks.EXPOSED_AMETHYST_LANTERN.getWaxed().asItem());
                output.accept(BPBlocks.WEATHERED_AMETHYST_LANTERN.getWaxed().asItem());
                output.accept(BPBlocks.OXIDIZED_AMETHYST_LANTERN.getWaxed().asItem());

                output.accept(BPBlocks.RED_SI_BLOCK_SET.getTile().asItem());
                output.accept(BPBlocks.YELLOW_SI_BLOCK_SET.getTile().asItem());
                output.accept(BPBlocks.GREEN_SI_BLOCK_SET.getTile().asItem());
                output.accept(BPBlocks.BLUE_SI_BLOCK_SET.getTile().asItem());
                output.accept(BPBlocks.WHITE_SI_BLOCK_SET.getTile().asItem());
                output.accept(BPBlocks.GRAY_SI_BLOCK_SET.getTile().asItem());
                output.accept(BPBlocks.BLACK_SI_BLOCK_SET.getTile().asItem());
                output.accept(BPBlocks.RED_SI_BLOCK_SET.getBaseBlock().asItem());
                output.accept(BPBlocks.ORANGE_SI_BLOCK_SET.getBaseBlock().asItem());
                output.accept(BPBlocks.YELLOW_SI_BLOCK_SET.getBaseBlock().asItem());
                output.accept(BPBlocks.GREEN_SI_BLOCK_SET.getBaseBlock().asItem());
                output.accept(BPBlocks.CYAN_SI_BLOCK_SET.getBaseBlock().asItem());
                output.accept(BPBlocks.BLUE_SI_BLOCK_SET.getBaseBlock().asItem());
                output.accept(BPBlocks.PURPLE_SI_BLOCK_SET.getBaseBlock().asItem());
                output.accept(BPBlocks.WHITE_SI_BLOCK_SET.getBaseBlock().asItem());
                output.accept(BPBlocks.GRAY_SI_BLOCK_SET.getBaseBlock().asItem());
                output.accept(BPBlocks.RED_SI_BLOCK_SET.getSlick().asItem());
                output.accept(BPBlocks.ORANGE_SI_BLOCK_SET.getSlick().asItem());
                output.accept(BPBlocks.YELLOW_SI_BLOCK_SET.getSlick().asItem());
                output.accept(BPBlocks.GREEN_SI_BLOCK_SET.getSlick().asItem());
                output.accept(BPBlocks.CYAN_SI_BLOCK_SET.getSlick().asItem());
                output.accept(BPBlocks.BLUE_SI_BLOCK_SET.getSlick().asItem());
                output.accept(BPBlocks.PURPLE_SI_BLOCK_SET.getSlick().asItem());
                output.accept(BPBlocks.WHITE_SI_BLOCK_SET.getSlick().asItem());
                output.accept(BPBlocks.GRAY_SI_BLOCK_SET.getSlick().asItem());
                output.accept(BPBlocks.BLACK_SI_BLOCK_SET.getSlick().asItem());
                output.accept(BPBlocks.RED_SI_BLOCK_SET.getGlow().asItem());
                output.accept(BPBlocks.ORANGE_SI_BLOCK_SET.getGlow().asItem());
                output.accept(BPBlocks.YELLOW_SI_BLOCK_SET.getGlow().asItem());
                output.accept(BPBlocks.GREEN_SI_BLOCK_SET.getGlow().asItem());
                output.accept(BPBlocks.CYAN_SI_BLOCK_SET.getGlow().asItem());
                output.accept(BPBlocks.BLUE_SI_BLOCK_SET.getGlow().asItem());
                output.accept(BPBlocks.PURPLE_SI_BLOCK_SET.getGlow().asItem());
                output.accept(BPBlocks.WHITE_SI_BLOCK_SET.getGlow().asItem());
                output.accept(BPBlocks.GRAY_SI_BLOCK_SET.getGlow().asItem());
                output.accept(BPBlocks.BLACK_SI_BLOCK_SET.getGlow().asItem());
                output.accept(BPBlocks.RED_SI_BLOCK_SET.getTwill().asItem());
                output.accept(BPBlocks.ORANGE_SI_BLOCK_SET.getTwill().asItem());
                output.accept(BPBlocks.YELLOW_SI_BLOCK_SET.getTwill().asItem());
                output.accept(BPBlocks.GREEN_SI_BLOCK_SET.getTwill().asItem());
                output.accept(BPBlocks.CYAN_SI_BLOCK_SET.getTwill().asItem());
                output.accept(BPBlocks.BLUE_SI_BLOCK_SET.getTwill().asItem());
                output.accept(BPBlocks.PURPLE_SI_BLOCK_SET.getTwill().asItem());
                output.accept(BPBlocks.WHITE_SI_BLOCK_SET.getTwill().asItem());
                output.accept(BPBlocks.GRAY_SI_BLOCK_SET.getTwill().asItem());
                output.accept(BPBlocks.BLACK_SI_BLOCK_SET.getTwill().asItem());
                output.accept(BPBlocks.RED_SI_BLOCK_SET.getTransparent().asItem());
                output.accept(BPBlocks.ORANGE_SI_BLOCK_SET.getTransparent().asItem());
                output.accept(BPBlocks.YELLOW_SI_BLOCK_SET.getTransparent().asItem());
                output.accept(BPBlocks.GREEN_SI_BLOCK_SET.getTransparent().asItem());
                output.accept(BPBlocks.CYAN_SI_BLOCK_SET.getTransparent().asItem());
                output.accept(BPBlocks.BLUE_SI_BLOCK_SET.getTransparent().asItem());
                output.accept(BPBlocks.PURPLE_SI_BLOCK_SET.getTransparent().asItem());
                output.accept(BPBlocks.WHITE_SI_BLOCK_SET.getTransparent().asItem());
                output.accept(BPBlocks.GRAY_SI_BLOCK_SET.getTransparent().asItem());
                output.accept(BPBlocks.BLACK_SI_BLOCK_SET.getTransparent().asItem());
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
    }

    public void packSetup(AddPackFindersEvent event) {
        event.addPackFinders(
                BedrockPlatform.modResLocation("resourcepacks/bp_fusion"),
                PackType.CLIENT_RESOURCES,
                Component.translatable("pack.bp_fusion.name"),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP);
        event.addPackFinders(
                BedrockPlatform.modResLocation("resourcepacks/bp_mcpatcher"),
                PackType.CLIENT_RESOURCES,
                Component.translatable("pack.bp_mcpatcher.name"),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP);
        event.addPackFinders(
                BedrockPlatform.modResLocation("resourcepacks/bp_ctm"),
                PackType.CLIENT_RESOURCES,
                Component.translatable("pack.bp_ctm.name"),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP);

    }
    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.CLIENT, Config.SPEC_C);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.SERVER, Config.SPEC_S);
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
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> ConfigurationScreen::new);
        }
        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(BPParticles.BLOCK_EXCHANGE.get(), (p) -> new BlockExchangeParticle.Provider(p, false));
            event.registerSpriteSet(BPParticles.BLOCK_REDUCTION.get(), (p) -> new BlockExchangeParticle.Provider(p, true));

            event.registerSpriteSet(BPParticles.STONE_TORCH_FLAME.get(), (p) -> new BrighterFlameParticle.Provider(p,false));
            event.registerSpriteSet(BPParticles.DEEPSLATE_TORCH_FLAME.get(), (p) -> new BrighterFlameParticle.Provider(p,true));
        }
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BPBlocks.SOLID_END_VOID_BE.get(), context -> new SolidEndVoidRender());
        }
    }

    public static ResourceLocation modResLocation(String path){
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
