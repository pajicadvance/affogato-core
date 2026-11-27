package me.pajic.affogatocore.mixson.events;

import me.pajic.affogatocore.AffogatoCoreClient;
import me.pajic.affogatocore.mixson.MixsonHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssetEvents {

    public static void register() {
        MixsonHelper.registerMultiJson(
                "Disable BetterGrass layer feature",
                index -> index.id().getPath().startsWith("bettergrass/layer_types/"),
                context -> {
                    if (AffogatoCoreClient.CONFIG.disableLBGLayers.get()) context.markForDeletion(true);
                }
        );
        MixsonHelper.registerMultiText(
                "Cringe Modded Splash Text Obliterator",
                index -> !index.id().getNamespace().equals("minecraft") && index.id().getPath().contains("texts/splashes"),
                context -> {
                    if (AffogatoCoreClient.CONFIG.deleteModdedSplashTexts.get()) context.setFile("");
                }
        );
        MixsonHelper.registerMultiText(
                "Delight-map biome block light tint fix",
                MixsonHelper.FRAGMENT_SHADER,
                index -> index.id().toString().equals("minecraft:shaders/core/lightmap"),
                context -> {
                    if (!AffogatoCoreClient.CONFIG.delightMapBlockLightTintFix.get()) return;
                    String file = context.getFile();
                    if (!file.contains("eg_delight_map")) return;
                    Matcher matcher = DELIGHT_MAP_BLOCK_LIGHT_FUNCTION.matcher(file);
                    if (matcher.find()) context.setFile(matcher.replaceFirst(Matcher.quoteReplacement(DELIGHT_MAP_BLOCK_LIGHT_REPLACEMENT)));
                }
        );
        MixsonHelper.registerMultiText(
                "Delight-map color rounding fix",
                MixsonHelper.SHADER_INCLUDE,
                index -> index.id().toString().equals("eg_delight_map:shaders/include/util"),
                context -> {
                    if (!AffogatoCoreClient.CONFIG.delightMapBlockLightTintFix.get()) return;
                    context.setFile(context.getFile().replace("ivec3(col*255.)", "ivec3(round(col*255.))"));
                }
        );
    }

    private static final Pattern DELIGHT_MAP_BLOCK_LIGHT_FUNCTION = Pattern.compile(
            "vec3 getAdjustedBlockLightColour\\(float level\\) \\{.*?\\n}",
            Pattern.DOTALL
    );

    private static final String DELIGHT_MAP_BLOCK_LIGHT_REPLACEMENT = """
            vec3 getAdjustedBlockLightColour(float level) {
                vec3 base = OVERWORLD_BLOCK_LIGHT_COLOUR;
                if(isInEnd()) {
                    base = END_BLOCK_LIGHT_COLOUR;
                } else if(isInNether()) {
                    base = NETHER_BLOCK_LIGHT_COLOUR;
                }
                return clamp(base * BLOCK_LIGHT_TINT / tovec(0xffd88c), 0.0, 1.0);
            }""";
}
