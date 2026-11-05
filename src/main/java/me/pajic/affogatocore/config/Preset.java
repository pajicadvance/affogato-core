package me.pajic.affogatocore.config;

import me.fzzyhmstrs.fzzy_config.api.ConfigApi;
import me.fzzyhmstrs.fzzy_config.util.ValidationResult;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedPair;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.util.LootEntryReplacement;
import me.pajic.affogatocore.util.ItemStatModification;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum Preset {
    AFFOGATO, DISABLE_ALL, CUSTOM;

    private static boolean detecting = false;

    public static void apply(Preset preset) {
        if (!detecting) apply(preset, AffogatoCore.CONFIG);
    }

    public static boolean detect(ModConfig config) {
        Preset detected = CUSTOM;
        for (Preset preset : values()) {
            if (preset != CUSTOM && matches(preset, config)) {
                detected = preset;
                break;
            }
        }
        if (config.preset.get() == detected) return false;
        detecting = true;
        try {
            config.preset.validateAndSet(detected);
        } finally {
            detecting = false;
        }
        return true;
    }

    private static boolean matches(Preset preset, ModConfig config) {
        ModConfig expected = new ModConfig();
        apply(preset, expected);
        expected.preset.validateAndSet(config.preset.get());
        expected.debug = config.debug;
        return serialize(expected).equals(serialize(config));
    }

    private static String serialize(ModConfig config) {
        return ConfigApi.serializeConfig(config, ValidationResult.Companion.createMutable("Preset detection")).get();
    }

    private static void apply(Preset preset, ModConfig config) {
        switch (preset) {
            case CUSTOM -> {}
            case AFFOGATO -> {
                // Packs
                config.packs.earlyGameExtension.validateAndSet(true);
                config.packs.recipeAdditions.validateAndSet(true);
                config.packs.antiFarmPack.validateAndSet(true);
                config.packs.customWanderingTraderTrades.validateAndSet(true);

                // Exhaustion values
                config.exhaustionValues.attack.validateAndSet(0.5F);
                config.exhaustionValues.blockAttack.validateAndSet(0.1F);
                config.exhaustionValues.breakBlock.validateAndSet(0.05F);
                config.exhaustionValues.climb.validateAndSet(0.01F);
                config.exhaustionValues.crawl.validateAndSet(0.04F);
                config.exhaustionValues.drawRangedWeapon.validateAndSet(0.25F);
                config.exhaustionValues.hungerEffectTick.validateAndSet(0.05F);
                config.exhaustionValues.jump.validateAndSet(0.025F);
                config.exhaustionValues.jumpSprint.validateAndSet(0.8F);
                config.exhaustionValues.naturalHealing.validateAndSet(8.0F);
                config.exhaustionValues.sneak.validateAndSet(0.02F);
                config.exhaustionValues.sprint.validateAndSet(0.02F);
                config.exhaustionValues.swimFast.validateAndSet(0.02F);
                config.exhaustionValues.swimSlow.validateAndSet(0.01F);
                config.exhaustionValues.takeDamage.validateAndSet(2.0F);
                config.exhaustionValues.throwWeapon.validateAndSet(0.25F);

                // Loot
                config.loot.lootEntryReplacements.validateAndSet(orderedMap(
                        Map.entry("minecraft:diamond_helmet", new LootEntryReplacement("minecraft:diamond", 5)),
                        Map.entry("minecraft:diamond_chestplate", new LootEntryReplacement("minecraft:diamond", 8)),
                        Map.entry("minecraft:diamond_leggings", new LootEntryReplacement("minecraft:diamond", 7)),
                        Map.entry("minecraft:diamond_boots", new LootEntryReplacement("minecraft:diamond", 4)),
                        Map.entry("minecraft:diamond_pickaxe", new LootEntryReplacement("minecraft:diamond", 3)),
                        Map.entry("minecraft:diamond_axe", new LootEntryReplacement("minecraft:diamond", 3)),
                        Map.entry("minecraft:diamond_shovel", new LootEntryReplacement("minecraft:diamond", 1)),
                        Map.entry("minecraft:diamond_sword", new LootEntryReplacement("minecraft:diamond", 2)),
                        Map.entry("minecraft:diamond_hoe", new LootEntryReplacement("minecraft:diamond", 2)),
                        Map.entry("minecraft:diamond_spear", new LootEntryReplacement("minecraft:diamond", 1)),
                        Map.entry("minecraft:iron_helmet", new LootEntryReplacement("minecraft:iron_ingot", 5)),
                        Map.entry("minecraft:iron_chestplate", new LootEntryReplacement("minecraft:iron_ingot", 8)),
                        Map.entry("minecraft:iron_leggings", new LootEntryReplacement("minecraft:iron_ingot", 7)),
                        Map.entry("minecraft:iron_boots", new LootEntryReplacement("minecraft:iron_ingot", 4)),
                        Map.entry("minecraft:iron_pickaxe", new LootEntryReplacement("minecraft:iron_ingot", 3)),
                        Map.entry("minecraft:iron_axe", new LootEntryReplacement("minecraft:iron_ingot", 3)),
                        Map.entry("minecraft:iron_shovel", new LootEntryReplacement("minecraft:iron_ingot", 1)),
                        Map.entry("minecraft:iron_sword", new LootEntryReplacement("minecraft:iron_ingot", 2)),
                        Map.entry("minecraft:iron_hoe", new LootEntryReplacement("minecraft:iron_ingot", 2)),
                        Map.entry("minecraft:iron_spear", new LootEntryReplacement("minecraft:iron_ingot", 1)),
                        Map.entry("farmersdelight:diamond_knife", new LootEntryReplacement("minecraft:diamond", 1)),
                        Map.entry("farmersdelight:copper_knife", new LootEntryReplacement("minecraft:copper_ingot", 1)),
                        Map.entry("farmersdelight:iron_knife", new LootEntryReplacement("minecraft:iron_ingot", 1)),
                        Map.entry("minecraft:stone_pickaxe", new LootEntryReplacement("minecraft:cobblestone", 3)),
                        Map.entry("minecraft:stone_shovel", new LootEntryReplacement("minecraft:cobblestone", 1)),
                        Map.entry("minecraft:stone_sword", new LootEntryReplacement("minecraft:cobblestone", 2)),
                        Map.entry("minecraft:stone_axe", new LootEntryReplacement("minecraft:cobblestone", 3)),
                        Map.entry("minecraft:stone_hoe", new LootEntryReplacement("minecraft:cobblestone", 2)),
                        Map.entry("minecraft:stone_spear", new LootEntryReplacement("minecraft:cobblestone", 1)),
                        Map.entry("minecraft:copper_pickaxe", new LootEntryReplacement("minecraft:copper_ingot", 3)),
                        Map.entry("minecraft:copper_shovel", new LootEntryReplacement("minecraft:copper_ingot", 1)),
                        Map.entry("minecraft:copper_sword", new LootEntryReplacement("minecraft:copper_ingot", 2)),
                        Map.entry("minecraft:copper_axe", new LootEntryReplacement("minecraft:copper_ingot", 3)),
                        Map.entry("minecraft:copper_hoe", new LootEntryReplacement("minecraft:copper_ingot", 2)),
                        Map.entry("minecraft:copper_spear", new LootEntryReplacement("minecraft:copper_ingot", 1))
                ));
                config.loot.totemMap.validateAndSet(orderedMap(
                        Map.entry("minecraft:jungle_temple", 0.5),
                        Map.entry("minecraft:pillager_outpost", 0.75),
                        Map.entry("minecraft:woodland_mansion", 1.0)
                ));
                config.loot.emeraldBlockResetsVaults.validateAndSet(true);
                config.loot.maceEnchantmentsInOminousVaults.validateAndSet(true);
                config.loot.tridentEnchantmentsFromElderGuardians.validateAndSet(true);
                config.loot.frostWalkerOnlyInIgloosAndIceBox.validateAndSet(true);
                config.loot.cursesOnlyInDesertPyramids.validateAndSet(true);
                config.loot.curseEnchantedBookChance.validateAndSet(0.5);
                config.loot.frostWalkerIglooChance.validateAndSet(0.5);
                config.loot.frostWalkerIceBoxChance.validateAndSet(1.0);

                // Worldgen
                config.worldgen.dungeonChanceMult.validateAndSet(1.3F);
                config.worldgen.mineshaftChanceMult.validateAndSet(1.2F);
                config.worldgen.oreSizeMinMult.validateAndSet(0.8F);
                config.worldgen.oreSizeMaxMult.validateAndSet(1.2F);
                config.worldgen.oreSizeMultMaxY.validateAndSet(192);
                config.worldgen.oreSizeMultMidY.validateAndSet(96);
                config.worldgen.oreSizeMultMinY.validateAndSet(0);
                config.worldgen.allowAncientDebrisAirExposure.validateAndSet(true);
                config.worldgen.ancientDebrisLargeSize.validateAndSet(6);
                config.worldgen.ancientDebrisSmallSize.validateAndSet(6);
                config.worldgen.ancientDebrisMinAmount.validateAndSet(3);
                config.worldgen.removeAncientDebrisHeightLimit.validateAndSet(true);
                config.worldgen.addRandomChestsToMansions.validateAndSet(true);
                config.worldgen.mansionRandomChestChance.validateAndSet(0.0039F);
                config.worldgen.addRandomChestsToMonuments.validateAndSet(true);
                config.worldgen.monumentRandomChestChance.validateAndSet(0.0025F);

                // Experience
                config.experience.recipeXpSwapByResult.validateAndSet(orderedMap(
                        Map.entry("minecraft:iron_ingot", 1.5),
                        Map.entry("minecraft:copper_ingot", 1.0),
                        Map.entry("minecraft:netherite_scrap", 10.0),
                        Map.entry("minecraft:iron_nugget", 1.0),
                        Map.entry("minecraft:gold_nugget", 1.0),
                        Map.entry("minecraft:copper_nugget", 1.0)
                ));
                config.experience.recipeXpSwapByInput.validateAndSet(orderedMap(
                        Map.entry("minecraft:raw_gold", 3.0),
                        Map.entry("minecraft:gold_ore", 3.0),
                        Map.entry("minecraft:deepslate_gold_ore", 3.0),
                        Map.entry("minecraft:nether_gold_ore", 0.75)
                ));
                config.experience.coal.validateAndSet(new ValidatedPair.Tuple<>(0, 3));
                config.experience.netherGold.validateAndSet(new ValidatedPair.Tuple<>(0, 3));
                config.experience.lapis.validateAndSet(new ValidatedPair.Tuple<>(3, 7));
                config.experience.diamond.validateAndSet(new ValidatedPair.Tuple<>(7, 10));
                config.experience.emerald.validateAndSet(new ValidatedPair.Tuple<>(12, 15));
                config.experience.redstone.validateAndSet(new ValidatedPair.Tuple<>(3, 7));
                config.experience.quartz.validateAndSet(new ValidatedPair.Tuple<>(3, 7));
                config.experience.noEnderDragonXpAfterFirst.validateAndSet(true);
                config.experience.noFishingXp.validateAndSet(true);
                config.experience.noMobXpDrops.validateAndSet(true);
                config.experience.noXpFromTrading.validateAndSet(true);

                // Combat
                config.combat.eatTimeIncrease.validateAndSet(1.6F);
                config.combat.eatCancel.validateAndSet(true);
                config.combat.blazeFollowRange.validateAndSet(32);
                config.combat.illusionerFollowRange.validateAndSet(16);
                config.combat.pillagerFollowRange.validateAndSet(24);
                config.combat.ravagerFollowRange.validateAndSet(16);
                config.combat.ravagerRoarKnockbackMult.validateAndSet(0.1F);
                config.combat.zombieFollowRange.validateAndSet(16);
                config.combat.zombieAlertOtherRange.validateAndSet(35);
                config.combat.drownedTridentThrowDelay.validateAndSet(80);
                config.combat.endermanAttackDelay.validateAndSet(40);
                config.combat.shieldProjectileDamageDivisor.validateAndSet(3F);

                // Mod integration
                config.modIntegration.fdCakeSliceFoodLevel.validateAndSet(3);
                config.modIntegration.fdCakeSliceSaturationLevel.validateAndSet(0.3F);
                config.modIntegration.penchantXpCostMult.validateAndSet(3);
                config.modIntegration.penchantProgressOnHostileKill.validateAndSet(true);
                config.modIntegration.mastercutterAdvancementRemoval.validateAndSet(true);

                // Item stats
                config.itemStats.validateAndSet(affogatoItemStats());

                // Misc
                config.misc.hardDifficultyDefault.validateAndSet(true);
                config.misc.noRapidHealing.validateAndSet(true);
                config.misc.noRiptideInRain.validateAndSet(true);
                config.misc.nightVisionNuke.validateAndSet(true);
                config.misc.torchLightLevel.validateAndSet(12);
                config.misc.copperTorchLightLevel.validateAndSet(12);
                config.misc.iceFriction.validateAndSet(0.965F);
                config.misc.blueIceFriction.validateAndSet(0.97F);
                config.misc.caveAmbientSoundFrequency.validateAndSet(800);
                config.misc.hoglinSpawnOnlyOnCrimsonNylium.validateAndSet(true);
                config.misc.frostWalkerEarlyTrigger.validateAndSet(true);
                config.misc.ironMinesObsidian.validateAndSet(true);
                config.misc.moreStoneTypesInStoneCraftingRecipes.validateAndSet(true);
                config.misc.fixOcelotSpawn.validateAndSet(true);
                config.misc.noVillagerRerolling.validateAndSet(true);
                config.misc.potentSulfurEruptionFrequencyMult.validateAndSet(0.5F);
                config.misc.potentSulfurGeyserSoundFrequencyMult.validateAndSet(0.5F);
                config.misc.potentSulfurGeyserSoundRange.validateAndSet(32);
                config.misc.trialSpawnerAmbientSoundFrequencyMult.validateAndSet(0.5F);
                config.misc.trialSpawnerAmbientSoundRange.validateAndSet(16);
                config.misc.suppressWorldgenReadWarning.validateAndSet(true);
            }
            case DISABLE_ALL -> {
                // Packs
                config.packs.earlyGameExtension.validateAndSet(false);
                config.packs.recipeAdditions.validateAndSet(false);
                config.packs.antiFarmPack.validateAndSet(false);
                config.packs.customWanderingTraderTrades.validateAndSet(false);

                // Exhaustion values
                config.exhaustionValues.attack.validateAndSet(0.1F);
                config.exhaustionValues.blockAttack.validateAndSet(0F);
                config.exhaustionValues.breakBlock.validateAndSet(0.005F);
                config.exhaustionValues.climb.validateAndSet(0F);
                config.exhaustionValues.crawl.validateAndSet(0F);
                config.exhaustionValues.drawRangedWeapon.validateAndSet(0F);
                config.exhaustionValues.hungerEffectTick.validateAndSet(0.005F);
                config.exhaustionValues.jump.validateAndSet(0.05F);
                config.exhaustionValues.jumpSprint.validateAndSet(0.2F);
                config.exhaustionValues.naturalHealing.validateAndSet(6.0F);
                config.exhaustionValues.sneak.validateAndSet(0F);
                config.exhaustionValues.sprint.validateAndSet(0.1F);
                config.exhaustionValues.swimFast.validateAndSet(0.01F);
                config.exhaustionValues.swimSlow.validateAndSet(0.01F);
                config.exhaustionValues.takeDamage.validateAndSet(0.1F);
                config.exhaustionValues.throwWeapon.validateAndSet(0F);

                // Loot
                config.loot.lootEntryReplacements.validateAndSet(Map.of());
                config.loot.totemMap.validateAndSet(Map.of());
                config.loot.emeraldBlockResetsVaults.validateAndSet(false);
                config.loot.maceEnchantmentsInOminousVaults.validateAndSet(false);
                config.loot.tridentEnchantmentsFromElderGuardians.validateAndSet(false);
                config.loot.frostWalkerOnlyInIgloosAndIceBox.validateAndSet(false);
                config.loot.cursesOnlyInDesertPyramids.validateAndSet(false);
                config.loot.curseEnchantedBookChance.validateAndSet(0.5);
                config.loot.frostWalkerIglooChance.validateAndSet(0.5);
                config.loot.frostWalkerIceBoxChance.validateAndSet(1.0);

                // Worldgen
                config.worldgen.dungeonChanceMult.validateAndSet(1F);
                config.worldgen.mineshaftChanceMult.validateAndSet(1F);
                config.worldgen.oreSizeMinMult.validateAndSet(1F);
                config.worldgen.oreSizeMaxMult.validateAndSet(1F);
                config.worldgen.oreSizeMultMaxY.validateAndSet(192);
                config.worldgen.oreSizeMultMidY.validateAndSet(96);
                config.worldgen.oreSizeMultMinY.validateAndSet(0);
                config.worldgen.allowAncientDebrisAirExposure.validateAndSet(false);
                config.worldgen.ancientDebrisLargeSize.validateAndSet(3);
                config.worldgen.ancientDebrisSmallSize.validateAndSet(2);
                config.worldgen.ancientDebrisMinAmount.validateAndSet(0);
                config.worldgen.removeAncientDebrisHeightLimit.validateAndSet(false);
                config.worldgen.addRandomChestsToMansions.validateAndSet(false);
                config.worldgen.mansionRandomChestChance.validateAndSet(0.0039F);
                config.worldgen.addRandomChestsToMonuments.validateAndSet(false);
                config.worldgen.monumentRandomChestChance.validateAndSet(0.0025F);

                // Experience
                config.experience.recipeXpSwapByResult.validateAndSet(Map.of());
                config.experience.recipeXpSwapByInput.validateAndSet(Map.of());
                config.experience.coal.validateAndSet(new ValidatedPair.Tuple<>(0, 2));
                config.experience.netherGold.validateAndSet(new ValidatedPair.Tuple<>(0, 1));
                config.experience.lapis.validateAndSet(new ValidatedPair.Tuple<>(2, 5));
                config.experience.diamond.validateAndSet(new ValidatedPair.Tuple<>(3, 7));
                config.experience.emerald.validateAndSet(new ValidatedPair.Tuple<>(3, 7));
                config.experience.redstone.validateAndSet(new ValidatedPair.Tuple<>(1, 5));
                config.experience.quartz.validateAndSet(new ValidatedPair.Tuple<>(2, 5));
                config.experience.noEnderDragonXpAfterFirst.validateAndSet(false);
                config.experience.noFishingXp.validateAndSet(false);
                config.experience.noMobXpDrops.validateAndSet(false);
                config.experience.noXpFromTrading.validateAndSet(false);

                // Combat
                config.combat.eatTimeIncrease.validateAndSet(0F);
                config.combat.eatCancel.validateAndSet(false);
                config.combat.blazeFollowRange.validateAndSet(48);
                config.combat.illusionerFollowRange.validateAndSet(32);
                config.combat.pillagerFollowRange.validateAndSet(32);
                config.combat.ravagerFollowRange.validateAndSet(32);
                config.combat.ravagerRoarKnockbackMult.validateAndSet(1.0F);
                config.combat.zombieFollowRange.validateAndSet(35);
                config.combat.zombieAlertOtherRange.validateAndSet(35);
                config.combat.drownedTridentThrowDelay.validateAndSet(40);
                config.combat.endermanAttackDelay.validateAndSet(20);
                config.combat.shieldProjectileDamageDivisor.validateAndSet(1F);

                // Mod integration
                config.modIntegration.fdCakeSliceFoodLevel.validateAndSet(2);
                config.modIntegration.fdCakeSliceSaturationLevel.validateAndSet(0.1F);
                config.modIntegration.penchantXpCostMult.validateAndSet(1);
                config.modIntegration.penchantProgressOnHostileKill.validateAndSet(false);
                config.modIntegration.mastercutterAdvancementRemoval.validateAndSet(false);

                // Item stats
                config.itemStats.validateAndSet(List.of());

                // Misc
                config.misc.hardDifficultyDefault.validateAndSet(false);
                config.misc.noRapidHealing.validateAndSet(false);
                config.misc.noRiptideInRain.validateAndSet(false);
                config.misc.nightVisionNuke.validateAndSet(false);
                config.misc.torchLightLevel.validateAndSet(14);
                config.misc.copperTorchLightLevel.validateAndSet(14);
                config.misc.iceFriction.validateAndSet(0.98F);
                config.misc.blueIceFriction.validateAndSet(0.989F);
                config.misc.caveAmbientSoundFrequency.validateAndSet(6000);
                config.misc.hoglinSpawnOnlyOnCrimsonNylium.validateAndSet(false);
                config.misc.frostWalkerEarlyTrigger.validateAndSet(false);
                config.misc.ironMinesObsidian.validateAndSet(false);
                config.misc.moreStoneTypesInStoneCraftingRecipes.validateAndSet(false);
                config.misc.fixOcelotSpawn.validateAndSet(false);
                config.misc.noVillagerRerolling.validateAndSet(false);
                config.misc.potentSulfurEruptionFrequencyMult.validateAndSet(1F);
                config.misc.potentSulfurGeyserSoundFrequencyMult.validateAndSet(1F);
                config.misc.potentSulfurGeyserSoundRange.validateAndSet(0);
                config.misc.trialSpawnerAmbientSoundFrequencyMult.validateAndSet(1F);
                config.misc.trialSpawnerAmbientSoundRange.validateAndSet(0);
                config.misc.suppressWorldgenReadWarning.validateAndSet(false);
            }
        }
    }

    private static List<ItemStatModification> affogatoItemStats() {
        List<ItemStatModification> stats = new ArrayList<>(List.of(
                ItemStatModification.durability(256, "minecraft:bow"),
                ItemStatModification.durability(640, "minecraft:elytra"),
                ItemStatModification.durability(256, "minecraft:shears"),
                ItemStatModification.durability(384, "minecraft:shield"),
                ItemStatModification.durability(320, "minecraft:crossbow"),
                ItemStatModification.durability(384, "minecraft:trident"),
                ItemStatModification.durability(240, "reliable_gliders:glider"),
                ItemStatModification.durability(384, "rearm:netherite_bow"),
                ItemStatModification.durability(480, "rearm:netherite_crossbow")
        ));

        addToolStats(stats, AffogatoCore.MOD_ID, "flint", 80, 3F);
        addToolStats(stats, "minecraft", "wooden", 80, 3F);
        addToolStats(stats, "minecraft", "stone", 120, 4F);
        addToolStats(stats, "minecraft", "copper", 160, 5F);
        addToolStats(stats, "minecraft", "iron", 320, 7F);
        addToolStats(stats, "minecraft", "golden", 80, 20F);
        addToolStats(stats, "minecraft", "diamond", 1280, 9F);
        addToolStats(stats, "minecraft", "netherite", 2560, 11F);

        stats.add(ItemStatModification.attackSpeed(-3.4F,
                AffogatoCore.MOD_ID + ":flint_axe", "minecraft:wooden_axe", "minecraft:stone_axe", "minecraft:copper_axe",
                "minecraft:iron_axe", "minecraft:diamond_axe", "minecraft:netherite_axe"
        ));
        stats.add(ItemStatModification.attackSpeed(-2F,
                AffogatoCore.MOD_ID + ":flint_hoe", "minecraft:wooden_hoe", "minecraft:stone_hoe", "minecraft:copper_hoe",
                "minecraft:iron_hoe", "minecraft:diamond_hoe", "minecraft:netherite_hoe"
        ));
        stats.add(ItemStatModification.attackSpeed(-1.6F, "minecraft:golden_sword"));
        stats.add(ItemStatModification.attackSpeed(-2.2F, "minecraft:golden_pickaxe"));
        stats.add(ItemStatModification.attackSpeed(-2.5F, "minecraft:golden_shovel"));
        stats.add(ItemStatModification.attackSpeed(0F, "minecraft:golden_hoe"));

        addArmorStats(stats, "leather", 8);
        addArmorStats(stats, "copper", 10);
        addArmorStats(stats, "chainmail", 12);
        addArmorStats(stats, "iron", 12);
        addArmorStats(stats, "golden", 6);
        addArmorStats(stats, "diamond", 20);
        addArmorStats(stats, "netherite", 30);
        stats.add(ItemStatModification.durability(13 * 16, "minecraft:turtle_helmet"));
        stats.add(ItemStatModification.durability(16 * 8, "minecraft:wolf_armor"));
        return stats;
    }

    private static void addToolStats(List<ItemStatModification> stats, String namespace, String material, int durability, float miningSpeed) {
        stats.add(ItemStatModification.durabilityAndMiningSpeed(durability, miningSpeed,
                namespace + ":" + material + "_pickaxe", namespace + ":" + material + "_axe",
                namespace + ":" + material + "_shovel", namespace + ":" + material + "_hoe"
        ));
        stats.add(ItemStatModification.durability(durability, namespace + ":" + material + "_sword"));
    }

    private static void addArmorStats(List<ItemStatModification> stats, String material, int multiplier) {
        stats.add(ItemStatModification.durability(13 * multiplier, "minecraft:" + material + "_helmet"));
        stats.add(ItemStatModification.durability(16 * multiplier, "minecraft:" + material + "_chestplate"));
        stats.add(ItemStatModification.durability(14 * multiplier, "minecraft:" + material + "_leggings"));
        stats.add(ItemStatModification.durability(12 * multiplier, "minecraft:" + material + "_boots"));
    }

    @SafeVarargs
    private static <K, V> Map<K, V> orderedMap(Map.Entry<K, V>... entries) {
        Map<K, V> map = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : entries) map.put(entry.getKey(), entry.getValue());
        return map;
    }
}
