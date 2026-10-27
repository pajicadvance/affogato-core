package me.pajic.affogatocore.pack;

import me.pajic.affogatocore.AffogatoCoreClient;

public class ModAssetPacks {

    public static void init() {
        if (AffogatoCoreClient.CONFIG.langOverrides.get()) {
            PackUtil.registerPersistentPack("lang_overrides", "Language overrides");
        }
    }
}
