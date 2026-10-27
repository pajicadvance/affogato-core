package me.pajic.affogatocore.pack;

import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.CompatFlags;

public class ModDataPacks {

    public static void init() {
        if (AffogatoCore.CONFIG.packs.earlyGameExtension.get()) {
            PackUtil.registerPersistentPack("early_game_extension/vanilla", "Early game extension");
            if (CompatFlags.FD_LOADED) PackUtil.registerPersistentPack("early_game_extension/fd", "Early game extension - Farmer's Delight integration");
        }
        if (AffogatoCore.CONFIG.packs.recipeAdditions.get()) {
            PackUtil.registerPersistentPack("recipe_additions/vanilla", "Recipe additions");
            if (CompatFlags.FD_LOADED) PackUtil.registerPersistentPack("recipe_additions/fd", "Recipe additions - Farmer's Delight integration");
        }
        if (AffogatoCore.CONFIG.packs.customWanderingTraderTrades.get()) {
            PackUtil.registerPersistentPack("wandering_trades/vanilla", "Custom wandering trader trades");
            if (CompatFlags.FD_LOADED) PackUtil.registerPersistentPack("wandering_trades/fd", "Custom wandering trader trades - Farmer's Delight integration");
        }
        if (AffogatoCore.CONFIG.packs.antiFarmPack.get()) {
            PackUtil.registerPersistentPack("anti_farm/vanilla", "Anti-farm pack");
            if (CompatFlags.VARIANTS_AND_VENTURES_LOADED) PackUtil.registerPersistentPack("anti_farm/vav", "Anti-farm pack - Variants and Ventures integration");
        }
    }
}
