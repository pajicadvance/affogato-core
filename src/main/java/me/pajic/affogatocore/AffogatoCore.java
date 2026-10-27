package me.pajic.affogatocore;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.affogatocore.config.ModConfig;
import me.pajic.affogatocore.config.Preset;
import me.pajic.affogatocore.pack.ModDataPacks;
import me.pajic.affogatocore.integration.PenchantProgressEvents;
import me.pajic.affogatocore.item.ModItems;
import me.pajic.affogatocore.mixson.MixsonInitializer;
import me.pajic.affogatocore.util.ToolMaterialId;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AffogatoCore implements ModInitializer {

    public static final String MOD_ID = "affogatocore";
    public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);
    public static final Logger LOGGER = LoggerFactory.getLogger("Affogato Core");
    public static final boolean DEV = FabricLoader.getInstance().isDevelopmentEnvironment();

    @Override
    public void onInitialize() {
        MixsonInitializer.init();
        ModItems.init();
        ModDataPacks.init();
        if (CompatFlags.PENCHANT_LOADED) PenchantProgressEvents.init();
        if (AffogatoCore.CONFIG.debug.logMaterialIds.get()) {
            LOGGER.info("Material IDs:");
            ToolMaterialId.MATERIAL_IDS.forEach(LOGGER::info);
        }
        CONFIG.preset.listenToEntry(presetEntry -> Preset.apply(presetEntry.get()));
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
