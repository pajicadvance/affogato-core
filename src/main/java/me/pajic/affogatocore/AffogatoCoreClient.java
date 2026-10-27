package me.pajic.affogatocore;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.pajic.affogatocore.config.ModClientConfig;
import me.pajic.affogatocore.iris.ShaderPresetSetup;
import me.pajic.affogatocore.mixson.MixsonClientInitializer;
import me.pajic.affogatocore.pack.ModAssetPacks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AffogatoCoreClient implements ClientModInitializer {

    public static ModClientConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModClientConfig::new, RegisterType.CLIENT);
    public static final Logger LOGGER = LoggerFactory.getLogger("Affogato Core Client");

    @Override
    public void onInitializeClient() {
        MixsonClientInitializer.init();
        ModAssetPacks.init();
        ClientLifecycleEvents.CLIENT_STARTED.register(_ -> {
            if (CompatFlags.IRIS_LOADED) ShaderPresetSetup.init();
        });
    }
}
