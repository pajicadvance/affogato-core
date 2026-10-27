package me.pajic.affogatocore.mixson;

import me.pajic.affogatocore.mixson.events.AssetEvents;

public class MixsonClientInitializer {

    public static void init() {
        AssetEvents.register();
    }
}
