package me.pajic.affogatocore.mixson.events;

import me.pajic.affogatocore.AffogatoCoreClient;
import me.pajic.affogatocore.mixson.MixsonHelper;

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
    }
}
