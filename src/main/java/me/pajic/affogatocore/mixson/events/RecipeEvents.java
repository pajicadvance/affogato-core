package me.pajic.affogatocore.mixson.events;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.CompatFlags;
import me.pajic.affogatocore.mixson.MixsonHelper;

import java.util.List;
import java.util.Set;

public class RecipeEvents {

    private static final List<String> EARLY_GAME_DISABLED_TOOLS = List.of(
            "minecraft:recipe/stone_pickaxe",
            "minecraft:recipe/stone_shovel",
            "minecraft:recipe/stone_sword",
            "minecraft:recipe/stone_axe",
            "minecraft:recipe/stone_hoe",
            "minecraft:recipe/stone_spear",
            "minecraft:recipe/wooden_pickaxe",
            "minecraft:recipe/wooden_shovel",
            "minecraft:recipe/wooden_sword",
            "minecraft:recipe/wooden_axe",
            "minecraft:recipe/wooden_hoe",
            "minecraft:recipe/wooden_spear"
    );

    public static void register() {
        MixsonHelper.registerMultiJson(
                "Modify smelting and blasting recipe XP rewards",
                index -> index.id().getPath().startsWith("recipe/") && !index.id().getNamespace().equals("emi"),
                context -> {
                    String type = context.getFile().getAsJsonObject().getAsJsonPrimitive("type").getAsString();
                    boolean xpSwapEnabled = !AffogatoCore.CONFIG.experience.recipeXpSwapByResult.get().isEmpty() ||
                            !AffogatoCore.CONFIG.experience.recipeXpSwapByInput.get().isEmpty();
                    if (xpSwapEnabled && context.getFile().getAsJsonObject().has("experience")) {
                        if (type.equals("minecraft:campfire_cooking") || type.equals("minecraft:smoking") || type.equals("simplekiln:kiln_smelting"))
                            context.getFile().getAsJsonObject().remove("experience");
                        if (type.equals("minecraft:smelting") || type.equals("minecraft:blasting")) {
                            String resultItem = context.getFile().getAsJsonObject().getAsJsonObject("result").getAsJsonPrimitive("id").getAsString();
                            if (AffogatoCore.CONFIG.experience.recipeXpSwapByResult.get().containsKey(resultItem))
                                context.getFile().getAsJsonObject().addProperty("experience", AffogatoCore.CONFIG.experience.recipeXpSwapByResult.get().get(resultItem));
                            else {
                                JsonElement inputItems = context.getFile().getAsJsonObject().get("ingredient");
                                if (!inputItems.isJsonArray()) {
                                    String inputItem = inputItems.getAsString();
                                    if (AffogatoCore.CONFIG.experience.recipeXpSwapByInput.get().containsKey(inputItem))
                                        context.getFile().getAsJsonObject().addProperty("experience", AffogatoCore.CONFIG.experience.recipeXpSwapByInput.get().get(inputItem));
                                    else context.getFile().getAsJsonObject().remove("experience");
                                }
                                else context.getFile().getAsJsonObject().remove("experience");
                            }
                        }
                    }
                }
        );
        EARLY_GAME_DISABLED_TOOLS.forEach(s -> MixsonHelper.registerSingleJson(
                "Remove recipes",
                s,
                context -> {
                    if (AffogatoCore.CONFIG.packs.earlyGameExtension.get())
                        context.markForDeletion(true);
                }
        ));
        if (CompatFlags.FD_LOADED) {
            MixsonHelper.registerSingleJson(
                    "Modify cooking pot recipe",
                    "farmersdelight:recipe/cooking_pot",
                    context -> {
                        if (AffogatoCore.CONFIG.packs.earlyGameExtension.get())
                            context.getFile().getAsJsonObject()
                                    .getAsJsonObject("key")
                                    .addProperty("S", "#minecraft:shovels");
                    }
            );
            MixsonHelper.registerMultiJson(
                    "Remove recipes",
                    Set.of("farmersdelight:recipe/cutting/gravel"),
                    context -> {
                        if (AffogatoCore.CONFIG.packs.earlyGameExtension.get())
                            context.markForDeletion(true);
                    }
            );
        }
        MixsonHelper.registerMultiJson(
                "Remove recipes",
                Set.of(
                        "minecraft:recipe/cake",
                        "farmersdelight:recipe/cake_from_milk_bottle"
                ),
                context -> {
                    if (AffogatoCore.CONFIG.packs.recipeAdditions.get() && CompatFlags.FD_LOADED)
                        context.markForDeletion(true);
                }
        );
        MixsonHelper.registerMultiJson(
                "Modify night vision brewing recipes",
                index -> index.id().getPath().startsWith("recipe/brewing/"),
                context -> {
                    if (AffogatoCore.CONFIG.misc.nightVisionNuke.get()) {
                        JsonObject file = context.getFile().getAsJsonObject();
                        if (file.getAsJsonPrimitive("type").getAsString().equals("minecraft:brewing")) {
                            JsonObject input = file.getAsJsonObject("input");
                            if (input.has("potion_contents")) {
                                JsonElement potions = input.getAsJsonObject("potion_contents").get("potions");
                                if (potions.isJsonPrimitive()) {
                                    if (potions.getAsString().equals("minecraft:night_vision")) {
                                        context.markForDeletion(true);
                                        return;
                                    }
                                } else {
                                    potions.getAsJsonArray().remove(new JsonPrimitive("minecraft:night_vision"));
                                    if (potions.getAsJsonArray().isEmpty()) {
                                        context.markForDeletion(true);
                                        return;
                                    }
                                }
                            }
                            JsonElement output = file.get("output");
                            if (output.isJsonObject()) {
                                JsonObject outputObj = output.getAsJsonObject();
                                if (outputObj.has("components")) {
                                    JsonObject components = outputObj.getAsJsonObject("components");
                                    if (components.has("minecraft:potion_contents")) {
                                        JsonObject potionContents = components.getAsJsonObject("minecraft:potion_contents");
                                        if (potionContents.getAsJsonPrimitive("potion").getAsString().equals("minecraft:night_vision")) {
                                            potionContents.addProperty("potion", "minecraft:invisibility");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        );
        if (CompatFlags.TOIL_AND_TROUBLE_LOADED) {
            MixsonHelper.registerMultiJson(
                    "Remove night vision recipes",
                    Set.of(
                            "toil_and_trouble:recipe/brewing/night_vision",
                            "toil_and_trouble:recipe/brewing/long_night_vision"
                    ),
                    context -> {
                        if (AffogatoCore.CONFIG.misc.nightVisionNuke.get())
                            context.markForDeletion(true);
                    }
            );
            MixsonHelper.registerSingleJson(
                    "Modify invisibility recipe",
                    "toil_and_trouble:recipe/brewing/invisibility",
                    context -> {
                        if (AffogatoCore.CONFIG.misc.nightVisionNuke.get()) {
                            JsonObject file = context.getFile().getAsJsonObject();
                            file.addProperty("reagent", "minecraft:golden_carrot");
                            file.addProperty("potion", "minecraft:awkward");
                        }
                    }
            );
        }
    }
}
