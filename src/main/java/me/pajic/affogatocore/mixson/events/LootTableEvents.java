package me.pajic.affogatocore.mixson.events;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.CompatFlags;
import me.pajic.affogatocore.integration.EnchantmentDisablerCompat;
import me.pajic.affogatocore.mixson.MixsonHelper;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LootTableEvents {
    public static void register() {
        AffogatoCore.CONFIG.loot.totemMap.get().forEach((key, value) -> {
            String[] split = key.split(":");
            if (split.length == 2) {
                MixsonHelper.registerSingleJson(
                        "Distribute Totem of Undying to loot chests",
                        split[0] + ":loot_table/chests/" + split[1],
                        context -> {
                            JsonElement pool = JsonParser.parseString("""
                                    {
                                      "rolls": 1.0,
                                      "entries": [
                                        {
                                          "type": "minecraft:item",
                                          "name": "minecraft:totem_of_undying"
                                        },
                                        {
                                          "type": "minecraft:empty"
                                        }
                                      ]
                                    }
                                    """).deepCopy();
                            double c = value * 100;
                            pool.getAsJsonObject()
                                    .getAsJsonArray("entries").get(0).getAsJsonObject()
                                    .addProperty("weight", c);
                            pool.getAsJsonObject()
                                    .getAsJsonArray("entries").get(1).getAsJsonObject()
                                    .addProperty("weight", 100 - c);
                            context.getFile().getAsJsonObject().getAsJsonArray("pools").add(pool);
                        }
                );
            }
        });
        MixsonHelper.registerSingleJson(
                "Distribute curse enchantments to desert pyramid chests",
                "minecraft:loot_table/chests/desert_pyramid",
                context -> {
                    if (AffogatoCore.CONFIG.loot.cursesOnlyInDesertPyramids.get()) {
                        JsonElement pool = JsonParser.parseString("""
                        {
                          "rolls": 1,
                          "entries": [
                            {
                              "type": "minecraft:item",
                              "name": "minecraft:book",
                              "modifier": [
                                {
                                  "type": "minecraft:enchant_randomly",
                                  "options": [
                                    "minecraft:binding_curse",
                                    "minecraft:vanishing_curse"
                                  ]
                                }
                              ]
                            },
                            {
                              "type": "minecraft:empty"
                            }
                          ]
                        }
                        """).deepCopy();
                        double c = AffogatoCore.CONFIG.loot.curseEnchantedBookChance.get() * 100;
                        pool.getAsJsonObject()
                                .getAsJsonArray("entries").get(0).getAsJsonObject()
                                .addProperty("weight", c);
                        pool.getAsJsonObject()
                                .getAsJsonArray("entries").get(1).getAsJsonObject()
                                .addProperty("weight", 100 - c);
                        context.getFile().getAsJsonObject().getAsJsonArray("pools").add(pool);
                    }
                }
        );
        MixsonHelper.registerSingleJson(
                "Distribute frost walker enchantment to ice box chests",
                "minecraft:loot_table/chests/ancient_city_ice_box",
                context -> {
                    if (AffogatoCore.CONFIG.loot.frostWalkerInIceBox.get()) {
                        JsonElement pool = JsonParser.parseString("""
                        {
                          "rolls": 1,
                          "entries": [
                            {
                              "type": "minecraft:item",
                              "name": "minecraft:book",
                              "modifier": [
                                {
                                  "type": "minecraft:enchant_randomly",
                                  "options": "minecraft:frost_walker"
                                }
                              ]
                            },
                            {
                              "type": "minecraft:empty"
                            }
                          ]
                        }
                        """).deepCopy();
                        double c = AffogatoCore.CONFIG.loot.frostWalkerIceBoxChance.get() * 100;
                        pool.getAsJsonObject().getAsJsonArray("entries").get(0).getAsJsonObject()
                                .addProperty("weight", c);
                        pool.getAsJsonObject().getAsJsonArray("entries").get(1).getAsJsonObject()
                                .addProperty("weight", 100 - c);
                        context.getFile().getAsJsonObject().getAsJsonArray("pools").add(pool);
                    }
                }
        );
        MixsonHelper.registerSingleJson(
                "Distribute channeling and riptide enchantments to elder guardian drops",
                "minecraft:loot_table/entities/elder_guardian",
                context -> {
                    if (AffogatoCore.CONFIG.loot.channelingAndRiptideFromElderGuardians.get()) {
                        JsonElement pool = JsonParser.parseString("""
                                {
                                  "rolls": 1,
                                  "entries": [
                                    {
                                      "type": "minecraft:item",
                                      "name": "minecraft:book",
                                      "modifier": [
                                        {
                                          "type": "minecraft:enchant_randomly",
                                          "options": [
                                             "minecraft:channeling",
                                             "minecraft:riptide"
                                          ]
                                        }
                                      ]
                                    }
                                  ]
                                }
                                """).deepCopy();
                        context.getFile().getAsJsonObject().getAsJsonArray("pools").add(pool);
                    }
                }
        );
        MixsonHelper.registerSingleJson(
                "Distribute stun burst enchantment to end city chests",
                "minecraft:loot_table/chests/end_city_treasure",
                context -> {
                    if (CompatFlags.ENDERSCAPE_LOADED && AffogatoCore.CONFIG.modIntegration.enderscapeStunBurstInEndCities.get()) {
                        JsonElement pool = JsonParser.parseString("""
                        {
                          "rolls": 1,
                          "entries": [
                            {
                              "type": "minecraft:item",
                              "name": "minecraft:book",
                              "modifier": [
                                {
                                  "type": "minecraft:enchant_randomly",
                                  "options": "enderscape:stun_burst"
                                }
                              ]
                            },
                            {
                              "type": "minecraft:empty"
                            }
                          ]
                        }
                        """).deepCopy();
                        double c = AffogatoCore.CONFIG.modIntegration.enderscapeStunBurstChance.get() * 100;
                        pool.getAsJsonObject().getAsJsonArray("entries").get(0).getAsJsonObject()
                                .addProperty("weight", c);
                        pool.getAsJsonObject().getAsJsonArray("entries").get(1).getAsJsonObject()
                                .addProperty("weight", 100 - c);
                        context.getFile().getAsJsonObject().getAsJsonArray("pools").add(pool);
                    }
                }
        );
        MixsonHelper.registerSingleJson(
                "Remove enchantments from random loot tag",
                "minecraft:tags/enchantment/on_random_loot",
                context -> {
                    List<String> removed = new ArrayList<>(AffogatoCore.CONFIG.loot.randomLootExcludedEnchantments.get());
                    if (AffogatoCore.CONFIG.loot.cursesOnlyInDesertPyramids.get()) {
                        removed.add("minecraft:binding_curse");
                        removed.add("minecraft:vanishing_curse");
                    }
                    removeFromTag(context.getFile(), removed);
                }
        );
        MixsonHelper.registerSingleJson(
                "Remove enchantments from non treasure tag",
                "minecraft:tags/enchantment/non_treasure",
                context -> removeFromTag(context.getFile(), AffogatoCore.CONFIG.loot.randomLootExcludedEnchantments.get())
        );
        MixsonHelper.registerMultiJson(
                "Replace disabled enchanted books in Penchant loot modifications with random enchanted books",
                index -> index.id().getNamespace().equals("penchant") && index.id().getPath().startsWith("penchant/loot_modification/"),
                context -> {
                    if (
                            !CompatFlags.PENCHANT_LOADED ||
                            !CompatFlags.ENCHANTMENT_DISABLER_LOADED ||
                            !AffogatoCore.CONFIG.modIntegration.penchantReplaceDisabledLootBooks.get()
                    ) return;
                    JsonArray pools = context.getFile().getAsJsonObject().getAsJsonArray("pools");
                    if (pools == null) return;
                    for (JsonElement pool : pools) {
                        JsonArray entries = pool.getAsJsonObject().getAsJsonArray("entries");
                        if (entries == null) continue;
                        for (JsonElement element : entries) {
                            JsonObject entry = element.getAsJsonObject();
                            if (!entry.has("name") || !entry.get("name").getAsString().equals("minecraft:book")) continue;
                            if (!hasOnlyDisabledEnchantments(entry.get("modifier"))) continue;
                            JsonObject modifier = new JsonObject();
                            modifier.addProperty("type", "minecraft:enchant_randomly");
                            modifier.addProperty("options", "#minecraft:on_random_loot");
                            entry.add("modifier", modifier);
                        }
                    }
                }
        );
    }

    private static void removeFromTag(JsonElement file, List<? extends String> removed) {
        if (removed.isEmpty()) return;
        JsonObject obj = file.getAsJsonObject();
        JsonArray values = obj.getAsJsonArray("values");
        if (!obj.has("fabric:remove")) obj.add("fabric:remove", new JsonArray());
        JsonArray remove = obj.getAsJsonArray("fabric:remove");
        for (String id : removed) {
            if (values != null) values.remove(new JsonPrimitive(id));
            JsonObject entry = new JsonObject();
            entry.addProperty("id", id);
            entry.addProperty("required", false);
            if (!remove.contains(entry)) remove.add(entry);
        }
    }

    private static boolean hasOnlyDisabledEnchantments(@Nullable JsonElement modifier) {
        if (modifier == null) return false;
        List<JsonObject> functions = new ArrayList<>();
        if (modifier.isJsonArray()) modifier.getAsJsonArray().forEach(e -> functions.add(e.getAsJsonObject()));
        else functions.add(modifier.getAsJsonObject());
        boolean found = false;
        for (JsonObject function : functions) {
            if (!function.has("type") || !function.get("type").getAsString().equals("minecraft:set_enchantments")) continue;
            JsonObject enchantments = function.getAsJsonObject("enchantments");
            if (enchantments == null) continue;
            for (String id : enchantments.keySet()) {
                Identifier identifier = Identifier.tryParse(id);
                if (identifier == null || !EnchantmentDisablerCompat.isDisabledInLoot(identifier)) return false;
                found = true;
            }
        }
        return found;
    }
}
