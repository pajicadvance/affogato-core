package me.pajic.affogatocore.mixson.events;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.mixson.MixsonHelper;

import java.util.Set;

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
                                  "options": "#minecraft:curse"
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
        MixsonHelper.registerMultiJson(
                "Distribute frost walker enchantment to igloo and ice box chests",
                Set.of("minecraft:loot_table/chests/igloo_chest", "minecraft:loot_table/chests/ancient_city_ice_box"),
                context -> {
                    if (AffogatoCore.CONFIG.loot.frostWalkerOnlyInIgloosAndIceBox.get()) {
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
                        double c = (context.getIndex().id().getPath().contains("igloo_chest") ?
                                AffogatoCore.CONFIG.loot.frostWalkerIglooChance.get() :
                                AffogatoCore.CONFIG.loot.frostWalkerIceBoxChance.get()) * 100;
                        pool.getAsJsonObject().getAsJsonArray("entries").get(0).getAsJsonObject()
                                .addProperty("weight", c);
                        pool.getAsJsonObject().getAsJsonArray("entries").get(1).getAsJsonObject()
                                .addProperty("weight", 100 - c);
                        context.getFile().getAsJsonObject().getAsJsonArray("pools").add(pool);
                    }
                }
        );
        MixsonHelper.registerSingleJson(
                "Distribute trident enchantments to elder guardian drops",
                "minecraft:loot_table/entities/elder_guardian",
                context -> {
                    if (AffogatoCore.CONFIG.loot.tridentEnchantmentsFromElderGuardians.get()) {
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
                                             "minecraft:loyalty",
                                             "minecraft:riptide",
                                             "minecraft:impaling",
                                             "minecraft:channeling"
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
                "Distribute mace enchantments to trial chamber rewards",
                "minecraft:loot_table/chests/trial_chambers/reward_ominous",
                context -> {
                    if (AffogatoCore.CONFIG.loot.maceEnchantmentsInOminousVaults.get()) {
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
                                             "minecraft:density",
                                             "minecraft:breach"
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
                "Remove enchantments from non treasure tag",
                "minecraft:tags/enchantment/non_treasure",
                context -> {
                    JsonArray values = context.getFile().getAsJsonObject().getAsJsonArray("values");
                    if (AffogatoCore.CONFIG.loot.maceEnchantmentsInOminousVaults.get()) {
                        values.remove(new JsonPrimitive("minecraft:density"));
                        values.remove(new JsonPrimitive("minecraft:breach"));
                    }
                    if (AffogatoCore.CONFIG.loot.tridentEnchantmentsFromElderGuardians.get()) {
                        values.remove(new JsonPrimitive("minecraft:loyalty"));
                        values.remove(new JsonPrimitive("minecraft:impaling"));
                        values.remove(new JsonPrimitive("minecraft:riptide"));
                        values.remove(new JsonPrimitive("minecraft:channeling"));
                    }
                }
        );
        MixsonHelper.registerSingleJson(
                "Remove enchantments from random loot tag",
                "minecraft:tags/enchantment/on_random_loot",
                context -> {
                    JsonArray values = context.getFile().getAsJsonObject().getAsJsonArray("values");
                    if (AffogatoCore.CONFIG.loot.frostWalkerOnlyInIgloosAndIceBox.get()) {
                        values.remove(new JsonPrimitive("minecraft:frost_walker"));
                    }
                    if (AffogatoCore.CONFIG.loot.cursesOnlyInDesertPyramids.get()) {
                        values.remove(new JsonPrimitive("minecraft:binding_curse"));
                        values.remove(new JsonPrimitive("minecraft:vanishing_curse"));
                    }
                }
        );
    }
}
