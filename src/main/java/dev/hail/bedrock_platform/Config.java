package dev.hail.bedrock_platform;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = BedrockPlatform.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue SOLID_VOID_RENDER_END_PORTAL = BUILDER
            .comment("Whether the solid void should render the end portal.")
            .define("solidVoidRenderEndPortal", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean solidVoidRenderEndPortal;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        solidVoidRenderEndPortal = SOLID_VOID_RENDER_END_PORTAL.get();
    }

}
