package dev.hail.bedrock_platform;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = BedrockPlatform.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder BUILDER_C = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder BUILDER_S = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue SOLID_VOID_RENDER_END_PORTAL = BUILDER_C
            .comment("Whether the solid void should render the end portal.")
            .define("solid_void_render_end_portal", true);
    private static final ForgeConfigSpec.DoubleValue STRONG_INTERACTION_EXPLODES_RADIUS = BUILDER_S
            .defineInRange("strong_interaction_explodes_radius", 10D, 0D, Double.MAX_VALUE);
    private static final ForgeConfigSpec.DoubleValue STRONG_INTERACTION_PRODUCE_RADIUS = BUILDER_S
            .defineInRange("strong_interaction_produce_radius", 5D, 0D, Double.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue PLATFORM_EXTENSION_DISTANCE = BUILDER_S
            .defineInRange("platform_extension_distance", 16, 0, Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();
    static final ForgeConfigSpec SPEC_C = BUILDER_C.build();
    static final ForgeConfigSpec SPEC_S = BUILDER_S.build();

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
