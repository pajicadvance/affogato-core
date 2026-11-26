package me.pajic.affogatocore.mixson.events;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.CompatFlags;
import me.pajic.affogatocore.config.ModConfig;
import me.pajic.affogatocore.mixson.MixsonHelper;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class GuidanceAdvancementEvents {

    private static final List<String> COPPER_ARMOR = List.of(
            "minecraft:copper_helmet",
            "minecraft:copper_chestplate",
            "minecraft:copper_leggings",
            "minecraft:copper_boots"
    );

    private static boolean enabled() {
        return AffogatoCore.CONFIG.misc.guidanceAdvancements.get();
    }

    private static ModConfig config() {
        return AffogatoCore.CONFIG;
    }

    public static void register() {
        registerDescriptionEdits();
        registerNewAdvancements();
    }

    private static void registerDescriptionEdits() {
        MixsonHelper.registerSingleJson(
                "Guidance: copper armor for Suit Up",
                "minecraft:advancement/story/obtain_armor",
                context -> {
                    if (!enabled()) return;
                    JsonObject file = context.getFile().getAsJsonObject();
                    JsonObject criteria = new JsonObject();
                    JsonArray requirement = new JsonArray();
                    COPPER_ARMOR.forEach(id -> {
                        String name = id.substring(id.indexOf(':') + 1);
                        JsonObject item = new JsonObject();
                        item.addProperty("items", id);
                        JsonArray items = new JsonArray();
                        items.add(item);
                        JsonObject conditions = new JsonObject();
                        conditions.add("items", items);
                        JsonObject criterion = new JsonObject();
                        criterion.add("conditions", conditions);
                        criterion.addProperty("trigger", "minecraft:inventory_changed");
                        criteria.add(name, criterion);
                        requirement.add(name);
                    });
                    JsonArray requirements = new JsonArray();
                    requirements.add(requirement);
                    file.add("criteria", criteria);
                    file.add("requirements", requirements);
                    if (config().packs.earlyGameExtension.get()) file.addProperty("parent", "minecraft:story/copper_ingot");
                    JsonObject display = file.getAsJsonObject("display");
                    display.getAsJsonObject("icon").addProperty("id", "minecraft:copper_chestplate");
                    setDescription(display, "obtain_armor");
                }
        );
        editDescription(
                "minecraft:advancement/story/smelt_iron",
                () -> "smelt_iron",
                () -> config().experience.noMobXpDrops.get()
        );
        editDescription(
                "minecraft:advancement/story/enchant_item",
                () -> "enchant_item",
                () -> CompatFlags.PENCHANT_LOADED && config().modIntegration.penchantPaidLevelUps.get()
        );
        editDescription(
                "minecraft:advancement/nether/obtain_ancient_debris",
                () -> "obtain_ancient_debris",
                () -> config().worldgen.allowAncientDebrisAirExposure.get() && config().worldgen.removeAncientDebrisHeightLimit.get()
        );
        editDescription(
                "minecraft:advancement/end/elytra",
                () -> "elytra",
                () -> CompatFlags.AILERON_LOADED
        );
        editDescription(
                "minecraft:advancement/adventure/totem_of_undying",
                () -> "totem_of_undying",
                () -> !config().loot.totemMap.isEmpty()
        );
        editDescription(
                "minecraft:advancement/adventure/voluntary_exile",
                () -> "voluntary_exile",
                () -> CompatFlags.DEPILLAGE_LOADED
        );
        editDescription(
                "minecraft:advancement/nether/brew_potion",
                () -> CompatFlags.TOIL_AND_TROUBLE_LOADED && CompatFlags.GOLDEN_POTIONS_LOADED ? "brew_potion.both"
                        : CompatFlags.TOIL_AND_TROUBLE_LOADED ? "brew_potion.cauldron" : "brew_potion.golden",
                () -> CompatFlags.TOIL_AND_TROUBLE_LOADED || CompatFlags.GOLDEN_POTIONS_LOADED
        );
        editDescription(
                "minecraft:advancement/husbandry/fishy_business",
                () -> "fishy_business",
                () -> CompatFlags.SIMPLE_FISHING_OVERHAUL_LOADED
        );
        editDescription(
                "minecraft:advancement/adventure/revaulting",
                () -> "revaulting",
                () -> config().loot.maceEnchantmentsInOminousVaults.get()
        );
    }

    private static void registerNewAdvancements() {
        newAdvancement("story/liquid_knowledge", () -> config().experience.noMobXpDrops.get());
        newAdvancement("story/hearty_meal", () -> config().combat.eatCancel.get() && config().misc.noRapidHealing.get());
        newAdvancement("story/low_profile", () -> true);
        newAdvancement("story/hold_the_line", () -> CompatFlags.REARM_LOADED);
        newAdvancement("story/pack_mule", () -> CompatFlags.TIERED_BACKPACKS_LOADED);
        newAdvancement("story/tool_belt", () -> CompatFlags.TOOL_POUCH_LOADED);
        newAdvancement("adventure/off_the_grid", () -> CompatFlags.MAPSTITCH_LOADED);
        newAdvancement("adventure/plant_your_flag", () -> CompatFlags.BANNERPOINT_LOADED);
        newAdvancement("adventure/ancient_wisdom", () -> config().loot.tridentEnchantmentsFromElderGuardians.get());
        newAdvancement("adventure/second_helping", () -> config().loot.emeraldBlockResetsVaults.get());
        newAdvancement("adventure/cold_storage", () -> config().loot.frostWalkerOnlyInIgloosAndIceBox.get());
        newAdvancement("adventure/cursed_riches", () -> config().loot.cursesOnlyInDesertPyramids.get());
        newAdvancement("husbandry/seasonal_worker", () -> CompatFlags.SERENE_SEASONS_LOADED);
    }

    private static void editDescription(String target, Supplier<String> key, BooleanSupplier condition) {
        MixsonHelper.registerSingleJson(
                "Guidance: description for " + target,
                target,
                context -> {
                    if (enabled() && condition.getAsBoolean())
                        setDescription(context.getFile().getAsJsonObject().getAsJsonObject("display"), key.get());
                }
        );
    }

    private static void setDescription(JsonObject display, String key) {
        JsonObject description = new JsonObject();
        description.addProperty("translate", "advancements.affogatocore." + key + ".description");
        display.add("description", description);
    }

    private static void newAdvancement(String path, BooleanSupplier condition) {
        MixsonHelper.registerSingleJson(
                "Guidance: " + path,
                "affogatocore:advancement/" + path,
                context -> {
                    if (!enabled() || !condition.getAsBoolean()) context.markForDeletion(true);
                }
        );
    }
}
