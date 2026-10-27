package me.pajic.affogatocore.mixson;

import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.mixson.events.AdvancementEvents;
import me.pajic.affogatocore.mixson.events.LootTableEvents;
import me.pajic.affogatocore.mixson.events.RecipeEvents;
import me.pajic.affogatocore.mixson.events.TagEvents;
import me.pajic.affogatocore.mixson.events.WorldgenDataEvents;
import net.ramixin.mixson.Mixson;
import net.ramixin.mixson.enums.DebugOption;

public class MixsonInitializer {

    public static void init() {
        if (AffogatoCore.DEV || AffogatoCore.CONFIG.debug.exportJsonPatches.get()) {
            Mixson.enableDebugOption(DebugOption.BASIC_LOGGING);
            Mixson.enableDebugOption(DebugOption.EXTRA_LOGGING);
            Mixson.enableDebugOption(DebugOption.EXPORT_PATCHED_FILE);
        }

        AdvancementEvents.register();
        LootTableEvents.register();
        RecipeEvents.register();
        TagEvents.register();
        WorldgenDataEvents.register();
    }
}
