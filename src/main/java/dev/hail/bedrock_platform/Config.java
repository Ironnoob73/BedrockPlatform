package dev.hail.bedrock_platform;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = BedrockPlatform.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    private static final ModConfigSpec.Builder BUILDER_C = new ModConfigSpec.Builder();
    private static final ModConfigSpec.Builder BUILDER_S = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue SOLID_VOID_RENDER_END_PORTAL = BUILDER_C
            .comment("Whether the solid void should render the end portal.")
            .define("solid_void_render_end_portal", true);
    private static final ModConfigSpec.DoubleValue STRONG_INTERACTION_EXPLODES_RADIUS = BUILDER_S
            .defineInRange("strong_interaction_explodes_radius", 10D, 0D, Double.MAX_VALUE);
    private static final ModConfigSpec.DoubleValue STRONG_INTERACTION_PRODUCE_RADIUS = BUILDER_S
            .defineInRange("strong_interaction_produce_radius", 5D, 0D, Double.MAX_VALUE);
    private static final ModConfigSpec.IntValue PLATFORM_EXTENSION_DISTANCE = BUILDER_S
            .defineInRange("platform_extension_distance", 16, 0, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();
    static final ModConfigSpec SPEC_C = BUILDER_C.build();
    static final ModConfigSpec SPEC_S = BUILDER_S.build();

    public static boolean solidVoidRenderEndPortal;
    public static double strongInteractionExplodesRadius;
    public static double strongInteractionProduceRadius;
    public static double platformExtensionDistance;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event)
    {
        if (event.getConfig().getSpec() == SPEC_C) {
            solidVoidRenderEndPortal = SOLID_VOID_RENDER_END_PORTAL.get();
        }
        else if (event.getConfig().getSpec() == SPEC_S) {
            strongInteractionExplodesRadius = STRONG_INTERACTION_EXPLODES_RADIUS.get();
            strongInteractionProduceRadius = STRONG_INTERACTION_PRODUCE_RADIUS.get();
            platformExtensionDistance = PLATFORM_EXTENSION_DISTANCE.get();
        }
    }
}
