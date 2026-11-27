package me.pajic.affogatocore.mixson.events;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.CompatFlags;
import me.pajic.affogatocore.mixson.MixsonHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.HashSet;
import java.util.Set;

public class WorldgenDataEvents {

    public static void register() {
        MixsonHelper.registerMultiNbt(
                "Remove leftover jigsaw blocks from CliffTree shipwrecks",
                index -> index.id().getNamespace().equals("clifftree") && index.id().getPath().startsWith("structure/shipwreck/"),
                context -> {
                    if (!CompatFlags.CLIFFTREE_LOADED || !AffogatoCore.CONFIG.modIntegration.clifftreeShipwreckJigsawFix.get()) return;
                    CompoundTag file = context.getFile();
                    ListTag palette = file.getListOrEmpty("palette");
                    Set<Integer> jigsawStates = new HashSet<>();
                    for (int i = 0; i < palette.size(); i++) {
                        if (palette.getCompoundOrEmpty(i).getStringOr("Name", "").equals("minecraft:jigsaw")) jigsawStates.add(i);
                    }
                    if (jigsawStates.isEmpty()) return;
                    file.getListOrEmpty("blocks").removeIf(tag -> {
                        if (!(tag instanceof CompoundTag block) || !jigsawStates.contains(block.getIntOr("state", -1))) return false;
                        String pool = block.getCompoundOrEmpty("nbt").getStringOr("pool", "");
                        return pool.isEmpty() || pool.equals("minecraft:");
                    });
                }
        );
        MixsonHelper.registerMultiJson(
                "Increase max ancient debris vein size and allow air exposure",
                index -> index.id().toString().startsWith("minecraft:worldgen/feature/ore_ancient_debris"),
                context -> {
                    if (AffogatoCore.CONFIG.worldgen.allowAncientDebrisAirExposure.get())
                        context.getFile().getAsJsonObject().addProperty("discard_chance_on_air_exposure", 0);
                    context.getFile().getAsJsonObject()
                            .addProperty("size", context.getIndex().id().getPath().endsWith("large") ?
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
    }
}
