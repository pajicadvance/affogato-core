package me.pajic.affogatocore.mixson.events;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.mixson.MixsonHelper;

import java.util.List;

public class WorldgenDataEvents {

    private static final List<String> ORE = List.of("coal", "copper", "diamond", "emerald", "gold", "iron", "lapis", "nether_gold", "quartz", "redstone");

    public static void register() {
        MixsonHelper.registerMultiJson(
                "Increase max ancient debris vein size and allow air exposure",
                index -> index.id().toString().startsWith("minecraft:worldgen/feature/ore_ancient_debris"),
                context -> {
                    if (AffogatoCore.CONFIG.worldgen.allowAncientDebrisAirExposure.get())
                        context.getFile().getAsJsonObject().addProperty("discard_chance_on_air_exposure", 0);
                    context.getFile().getAsJsonObject()
                            .addProperty("size", context.getIndex().toString().endsWith("large") ?
                                    AffogatoCore.CONFIG.worldgen.ancientDebrisLargeSize.get() :
                                    AffogatoCore.CONFIG.worldgen.ancientDebrisSmallSize.get()
                            );
                }
        );
        MixsonHelper.registerMultiJson(
                "Remove height limit for large ancient debris spawns",
                index -> index.id().getPath().startsWith("worldgen/placed_feature/ore_ancient_debris_large"),
                context -> {
                    if (AffogatoCore.CONFIG.worldgen.removeAncientDebrisHeightLimit.get()) {
                        JsonArray placement = context.getFile().getAsJsonObject().getAsJsonArray("placement");
                        JsonArray updatedPlacement = new JsonArray();
                        placement.forEach(element -> {
                            if (!element.getAsJsonObject().getAsJsonPrimitive("type").getAsString().equals("minecraft:height_range")) {
                                updatedPlacement.add(element);
                            }
                        });
                        updatedPlacement.add(JsonParser.parseString("""
                                {
                                    "type": "minecraft:height_range",
                                    "height": {
                                        "type": "minecraft:uniform",
                                        "max_inclusive": {
                                          "below_top": 8
                                        },
                                        "min_inclusive": {
                                          "absolute": 8
                                        }
                                    }
                                }"""));
                        context.getFile().getAsJsonObject().add("placement", updatedPlacement);
                    }
                }
        );
        ORE.forEach(ore -> MixsonHelper.registerMultiJson(
                "Increase " + ore + " ore size",
                index -> index.id().toString().startsWith("minecraft:worldgen/feature/ore_" + ore),
                context -> context.getFile().getAsJsonObject().addProperty("size", Math.round(
                        context.getFile().getAsJsonObject().getAsJsonPrimitive("size").getAsInt() * AffogatoCore.CONFIG.worldgen.oreSizeMult.get()
                ))
        ));
        MixsonHelper.registerMultiJson(
                "Increase dungeon spawn rate",
                index -> index.id().toString().startsWith("minecraft:worldgen/placed_feature/monster_room"),
                context -> {
                    JsonArray placement = context.getFile().getAsJsonObject().getAsJsonArray("placement");
                    for (JsonElement element : placement) {
                        JsonObject object = element.getAsJsonObject();
                        String type = object.getAsJsonPrimitive("type").getAsString();
                        if (type.equals("minecraft:count")) {
                            object.addProperty("count", Math.round(
                                    object.getAsJsonPrimitive("count").getAsInt() * AffogatoCore.CONFIG.worldgen.dungeonChanceMult.get()
                            ));
                        }
                    }
                }
        );
        MixsonHelper.registerSingleJson(
                "Increase mineshaft spawn rate",
                "minecraft:worldgen/structure_set/mineshafts",
                context -> context.getFile().getAsJsonObject()
                        .getAsJsonObject("placement").addProperty("frequency",
                                context.getFile().getAsJsonObject().getAsJsonObject("placement")
                                        .getAsJsonPrimitive("frequency").getAsFloat() * AffogatoCore.CONFIG.worldgen.mineshaftChanceMult.get()
                        )
        );
        MixsonHelper.registerSingleJson(
                "Fix ocelots being counted as monsters during spawning",
                "minecraft:worldgen/biome/jungle",
                context -> {
                    if (AffogatoCore.CONFIG.misc.fixOcelotSpawn.get()) {
                        JsonObject spawns = context.getFile().getAsJsonObject()
                                .getAsJsonObject("attributes")
                                .getAsJsonObject("minecraft:gameplay/natural_mob_spawns")
                                .getAsJsonObject("argument")
                                .getAsJsonObject("spawns_by_category");
                        JsonArray monsters = spawns.getAsJsonArray("monster");
                        JsonArray creatures = spawns.getAsJsonArray("creature");
                        int idToRemove = -1;
                        for (int i = 0; i < monsters.size(); i++) {
                            JsonObject monster = monsters.get(i).getAsJsonObject();
                            if (monster.getAsJsonPrimitive("type").getAsString().equals("minecraft:ocelot")) {
                                idToRemove = i;
                                creatures.add(monster);
                                break;
                            }
                        }
                        if (idToRemove != -1) monsters.remove(idToRemove);
                    }
                }
        );
    }
}
