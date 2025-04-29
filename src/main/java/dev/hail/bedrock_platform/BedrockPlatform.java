package dev.hail.bedrock_platform;

import com.mojang.logging.LogUtils;
import dev.hail.bedrock_platform.Blocks.BPBlocks;
import dev.hail.bedrock_platform.Blocks.SolidEnd.SolidEndVoidRender;
import dev.hail.bedrock_platform.Entities.BPEntities;
import dev.hail.bedrock_platform.Events.BPEvents;
import dev.hail.bedrock_platform.Items.BPItems;
import dev.hail.bedrock_platform.Particle.BPParticles;
import dev.hail.bedrock_platform.Particle.BlockExchangeParticle;
import dev.hail.bedrock_platform.Particle.BouncePadParticle;
import dev.hail.bedrock_platform.Particle.BrighterFlameParticle;
import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERSerializer;
import dev.hail.bedrock_platform.Recipe.BlockExchangeRecipe.BERecipe;
import dev.hail.bedrock_platform.Recipe.BlockReductionRecipe.BRRSerializer;
import dev.hail.bedrock_platform.Recipe.BlockReductionRecipe.BRRecipe;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
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
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
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
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("bedrock_platform_tab", () -> CreativeModeTab.builder()
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
                output.accept(BPBlocks.FILLED_SCULK_RIB_BLOCK.asItem());
                output.accept(BPItems.ENCHANT_DUST.get());
                output.accept(BPItems.BLUE_ICE_CUBE.get());
                output.accept(BPBlocks.GEODE_MOSAIC_TILE.getBaseBlock());
                output.accept(BPBlocks.KELP_BLOCK.asItem());
                output.accept(BPBlocks.PERMANENTLY_WETTED_FARMLAND.asItem());
                output.accept(BPBlocks.GLOW_PERMANENTLY_WETTED_FARMLAND.asItem());
                output.accept(BPBlocks.STONE_TORCH.getItem());
                output.accept(BPBlocks.DEEPSLATE_TORCH.getItem());
                output.accept(BPBlocks.AMETHYST_CANDLE.getItem());

                output.accept(BPBlocks.OAK_PLATFORM.asItem());
                output.accept(BPBlocks.TRANSPARENT_OAK_PLATFORM.asItem());
                output.accept(BPBlocks.BIRCH_PLATFORM.asItem());
                output.accept(BPBlocks.TRANSPARENT_BIRCH_PLATFORM.asItem());
                output.accept(BPBlocks.SPRUCE_PLATFORM.asItem());
                output.accept(BPBlocks.TRANSPARENT_SPRUCE_PLATFORM.asItem());
                output.accept(BPBlocks.JUNGLE_PLATFORM.asItem());
                output.accept(BPBlocks.TRANSPARENT_JUNGLE_PLATFORM.asItem());
                output.accept(BPBlocks.DARK_OAK_PLATFORM.asItem());
                output.accept(BPBlocks.TRANSPARENT_DARK_OAK_PLATFORM.asItem());
                output.accept(BPBlocks.ACACIA_PLATFORM.asItem());
                output.accept(BPBlocks.TRANSPARENT_ACACIA_PLATFORM.asItem());
                output.accept(BPBlocks.MANGROVE_PLATFORM.asItem());
                output.accept(BPBlocks.TRANSPARENT_MANGROVE_PLATFORM.asItem());
                output.accept(BPBlocks.CHERRY_PLATFORM.asItem());
                output.accept(BPBlocks.TRANSPARENT_CHERRY_PLATFORM.asItem());
                output.accept(BPBlocks.STONE_PLATFORM.asItem());
                output.accept(BPBlocks.TRANSPARENT_STONE_PLATFORM.asItem());
                output.accept(BPBlocks.CRIMSON_PLATFORM.asItem());
                output.accept(BPBlocks.TRANSPARENT_CRIMSON_PLATFORM.asItem());
                output.accept(BPBlocks.WARPED_PLATFORM.asItem());
                output.accept(BPBlocks.TRANSPARENT_WARPED_PLATFORM.asItem());
                output.accept(BPBlocks.GLASS_PLATFORM.asItem());

                output.accept(BPBlocks.PRECISE_NETHER_PORTAL_ITEM.get());
                output.accept(BPItems.CRIMSON_BOAT.get());
                output.accept(BPItems.CRIMSON_CHEST_BOAT.get());
                output.accept(BPItems.WARPED_BOAT.get());
                output.accept(BPItems.WARPED_CHEST_BOAT.get());
                output.accept(BPBlocks.GEODE_WHITE_CRATE);
                output.accept(BPBlocks.GEODE_BLACK_CRATE);
                output.accept(BPBlocks.GEODE_GRAY_CRATE);
                output.accept(BPBlocks.GEODE_BLUE_CRATE);
                output.accept(BPBlocks.AMETHYST_LANTERN.getUnwaxed().asItem());
                output.accept(BPBlocks.EXPOSED_AMETHYST_LANTERN.getUnwaxed().asItem());
                output.accept(BPBlocks.WEATHERED_AMETHYST_LANTERN.getUnwaxed().asItem());
                output.accept(BPBlocks.OXIDIZED_AMETHYST_LANTERN.getUnwaxed().asItem());
                output.accept(BPBlocks.AMETHYST_LANTERN.getWaxed().asItem());
                output.accept(BPBlocks.EXPOSED_AMETHYST_LANTERN.getWaxed().asItem());
                output.accept(BPBlocks.WEATHERED_AMETHYST_LANTERN.getWaxed().asItem());
                output.accept(BPBlocks.OXIDIZED_AMETHYST_LANTERN.getWaxed().asItem());

                output.accept(BPBlocks.GEODE_MOSAIC_TILE.getStairs());
                output.accept(BPBlocks.GEODE_MOSAIC_TILE.getSlab());
                output.accept(BPBlocks.GEODE_MOSAIC_TILE.getWall());
                output.accept(BPBlocks.GEODE_WHITE_TILES.getBaseBlock());
                output.accept(BPBlocks.GEODE_WHITE_TILES.getStairs());
                output.accept(BPBlocks.GEODE_WHITE_TILES.getSlab());
                output.accept(BPBlocks.GEODE_WHITE_TILES.getWall());
                output.accept(BPBlocks.GEODE_WHITE_SMOOTH_TILE.getBaseBlock());
                output.accept(BPBlocks.GEODE_WHITE_SMOOTH_TILE.getStairs());
                output.accept(BPBlocks.GEODE_WHITE_SMOOTH_TILE.getSlab());
                output.accept(BPBlocks.GEODE_WHITE_SMOOTH_TILE.getWall());
                output.accept(BPBlocks.GEODE_WHITE_BRICKS.getBaseBlock());
                output.accept(BPBlocks.GEODE_WHITE_BRICKS.getStairs());
                output.accept(BPBlocks.GEODE_WHITE_BRICKS.getSlab());
                output.accept(BPBlocks.GEODE_WHITE_BRICKS.getWall());
                output.accept(BPBlocks.GEODE_WHITE_PILLAR);
                output.accept(BPBlocks.GEODE_BLACK_TILES.getBaseBlock());
                output.accept(BPBlocks.GEODE_BLACK_TILES.getStairs());
                output.accept(BPBlocks.GEODE_BLACK_TILES.getSlab());
                output.accept(BPBlocks.GEODE_BLACK_TILES.getWall());
                output.accept(BPBlocks.GEODE_BLACK_SMOOTH_TILE.getBaseBlock());
                output.accept(BPBlocks.GEODE_BLACK_SMOOTH_TILE.getStairs());
                output.accept(BPBlocks.GEODE_BLACK_SMOOTH_TILE.getSlab());
                output.accept(BPBlocks.GEODE_BLACK_SMOOTH_TILE.getWall());
                output.accept(BPBlocks.GEODE_BLACK_BRICKS.getBaseBlock());
                output.accept(BPBlocks.GEODE_BLACK_BRICKS.getStairs());
                output.accept(BPBlocks.GEODE_BLACK_BRICKS.getSlab());
                output.accept(BPBlocks.GEODE_BLACK_BRICKS.getWall());
                output.accept(BPBlocks.GEODE_BLACK_PILLAR);
                output.accept(BPBlocks.GEODE_GRAY_TILES.getBaseBlock());
                output.accept(BPBlocks.GEODE_GRAY_TILES.getStairs());
                output.accept(BPBlocks.GEODE_GRAY_TILES.getSlab());
                output.accept(BPBlocks.GEODE_GRAY_TILES.getWall());
                output.accept(BPBlocks.GEODE_GRAY_SMOOTH_TILE.getBaseBlock());
                output.accept(BPBlocks.GEODE_GRAY_SMOOTH_TILE.getStairs());
                output.accept(BPBlocks.GEODE_GRAY_SMOOTH_TILE.getSlab());
                output.accept(BPBlocks.GEODE_GRAY_SMOOTH_TILE.getWall());
                output.accept(BPBlocks.GEODE_GRAY_BRICKS.getBaseBlock());
                output.accept(BPBlocks.GEODE_GRAY_BRICKS.getStairs());
                output.accept(BPBlocks.GEODE_GRAY_BRICKS.getSlab());
                output.accept(BPBlocks.GEODE_GRAY_BRICKS.getWall());
                output.accept(BPBlocks.GEODE_GRAY_PILLAR);
                output.accept(BPBlocks.GEODE_BLUE_TILES.getBaseBlock());
                output.accept(BPBlocks.GEODE_BLUE_TILES.getStairs());
                output.accept(BPBlocks.GEODE_BLUE_TILES.getSlab());
                output.accept(BPBlocks.GEODE_BLUE_TILES.getWall());
                output.accept(BPBlocks.GEODE_BLUE_SMOOTH_TILE.getBaseBlock());
                output.accept(BPBlocks.GEODE_BLUE_SMOOTH_TILE.getStairs());
                output.accept(BPBlocks.GEODE_BLUE_SMOOTH_TILE.getSlab());
                output.accept(BPBlocks.GEODE_BLUE_SMOOTH_TILE.getWall());
                output.accept(BPBlocks.GEODE_BLUE_BRICKS.getBaseBlock());
                output.accept(BPBlocks.GEODE_BLUE_BRICKS.getStairs());
                output.accept(BPBlocks.GEODE_BLUE_BRICKS.getSlab());
                output.accept(BPBlocks.GEODE_BLUE_BRICKS.getWall());
                output.accept(BPBlocks.GEODE_BLUE_PILLAR);
                output.accept(BPBlocks.GEODE_GRAY_WHITE_TILES.getBaseBlock());
                output.accept(BPBlocks.GEODE_GRAY_WHITE_TILES.getStairs());
                output.accept(BPBlocks.GEODE_GRAY_WHITE_TILES.getSlab());
                output.accept(BPBlocks.GEODE_GRAY_WHITE_TILES.getWall());
                output.accept(BPBlocks.GEODE_BLUE_WHITE_TILES.getBaseBlock());
                output.accept(BPBlocks.GEODE_BLUE_WHITE_TILES.getStairs());
                output.accept(BPBlocks.GEODE_BLUE_WHITE_TILES.getSlab());
                output.accept(BPBlocks.GEODE_BLUE_WHITE_TILES.getWall());

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
        BPEntities.ENTITY_TYPES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        BPParticles.PARTICLE_TYPES.register(modEventBus);

        BERecipe.RECIPE_TYPES.register(modEventBus);
        BERSerializer.RECIPE_SERIALIZERS.register(modEventBus);
        BRRecipe.RECIPE_TYPES.register(modEventBus);
        BRRSerializer.RECIPE_SERIALIZERS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new BPEvents());

        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::registerFlammable);

        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.SPEC_C);
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

            event.registerSpriteSet(BPParticles.BOUNCE_PAD.get(), BouncePadParticle.Provider::new);
        }
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BPBlocks.SOLID_END_VOID_BE.get(), context -> new SolidEndVoidRender());
            event.registerEntityRenderer(BPEntities.NETHER_BOAT.get(),context -> new BoatRenderer(context,false));
            event.registerEntityRenderer(BPEntities.NETHER_CHEST_BOAT.get(),context -> new BoatRenderer(context,true));
        }
    }
    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BPBlocks.GEODE_CRATE_BE.get(),
                (BlockEntity, side) -> BlockEntity.inventory
        );
    }
    private void registerFlammable(FMLCommonSetupEvent event){
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        fireBlock.setFlammable(BPBlocks.OAK_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.TRANSPARENT_OAK_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.BIRCH_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.TRANSPARENT_BIRCH_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.SPRUCE_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.TRANSPARENT_SPRUCE_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.JUNGLE_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.TRANSPARENT_JUNGLE_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.DARK_OAK_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.TRANSPARENT_DARK_OAK_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.ACACIA_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.TRANSPARENT_ACACIA_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.MANGROVE_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.TRANSPARENT_MANGROVE_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.CHERRY_PLATFORM.get(),20,20);
        fireBlock.setFlammable(BPBlocks.TRANSPARENT_CHERRY_PLATFORM.get(),20,20);
    }
    public static ResourceLocation modResLocation(String path){
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
