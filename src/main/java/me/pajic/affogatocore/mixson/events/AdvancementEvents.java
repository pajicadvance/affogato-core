package me.pajic.affogatocore.mixson.events;

import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.CompatFlags;
import me.pajic.affogatocore.mixson.MixsonHelper;

import java.util.List;
import java.util.Set;

public class AdvancementEvents {

    private static final List<String> EARLY_GAME_DISABLED_TOOLS = List.of(
            "minecraft:advancement/recipes/tools/stone_pickaxe",
            "minecraft:advancement/recipes/tools/stone_shovel",
            "minecraft:advancement/recipes/combat/stone_sword",
            "minecraft:advancement/recipes/tools/stone_axe",
            "minecraft:advancement/recipes/tools/stone_hoe",
            "minecraft:advancement/recipes/combat/stone_spear",
            "minecraft:advancement/recipes/tools/wooden_pickaxe",
            "minecraft:advancement/recipes/tools/wooden_shovel",
            "minecraft:advancement/recipes/combat/wooden_sword",
            "minecraft:advancement/recipes/tools/wooden_axe",
            "minecraft:advancement/recipes/tools/wooden_hoe",
            "minecraft:advancement/recipes/combat/wooden_spear"
    );

    public static void register() {
        MixsonHelper.registerMultiJson(
                "Remove Night Vision requirement for advancements",
                Set.of("minecraft:advancement/nether/all_effects", "minecraft:advancement/nether/all_potions"),
                context ->  {
                    if (AffogatoCore.CONFIG.misc.nightVisionNuke.get()) context.getFile().getAsJsonObject()
                            .getAsJsonObject("criteria")
                            .getAsJsonObject("all_effects")
                            .getAsJsonObject("conditions")
                            .getAsJsonObject("effects")
                            .remove("minecraft:night_vision");
                }
        );
        EARLY_GAME_DISABLED_TOOLS.forEach(s -> MixsonHelper.registerSingleJson(
                "Remove recipe unlocks",
                s,
                context -> {
                    if (AffogatoCore.CONFIG.packs.earlyGameExtension.get())
                        context.markForDeletion(true);
                }
        ));
        MixsonHelper.registerSingleJson(
                "Remove FD cake recipe unlock",
                "farmersdelight:advancement/recipes/food/cake_from_milk_bottle",
                context -> {
                    if (AffogatoCore.CONFIG.packs.recipeAdditions.get() && CompatFlags.FD_LOADED)
                        context.markForDeletion(true);
                }
        );
        MixsonHelper.registerSingleJson(
                "Modify cake recipe unlock",
                "minecraft:advancement/recipes/food/cake",
                context -> {
                    if (AffogatoCore.CONFIG.packs.recipeAdditions.get() && CompatFlags.FD_LOADED)
                        context.getFile().getAsJsonObject()
                                .getAsJsonObject("criteria")
                                .getAsJsonObject("has_the_recipe")
                                .getAsJsonObject("conditions")
                                .addProperty("recipes", "affogatocore:cake");
                }
        );
        MixsonHelper.registerMultiJson(
                "Remove Mastercutter advancements",
                index -> index.id().toString().startsWith("global:advancement/"),
                context -> {
                    if (AffogatoCore.CONFIG.modIntegration.mastercutterAdvancementRemoval.get()) {
                        context.markForDeletion(true);
                    }
                }
        );
    }
}
