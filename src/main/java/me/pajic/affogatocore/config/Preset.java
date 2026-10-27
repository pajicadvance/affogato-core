package me.pajic.affogatocore.config;

import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedPair;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.util.LootEntryReplacement;
import me.pajic.affogatocore.util.ToolMaterialId;
import me.pajic.affogatocore.util.ToolStatReplacement;
import me.pajic.affogatocore.util.WeaponStatReplacement;
import me.pajic.affogatocore.util.WeaponType;
import net.minecraft.world.item.ToolMaterial;

import java.util.List;
import java.util.Map;

public enum Preset {
    AFFOGATO, DISABLE_ALL;

    public static void apply(Preset preset) {
        ModConfig config = AffogatoCore.CONFIG;

        switch (preset) {
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

                // Durabilities
                config.durabilities.helmetBase.validateAndSet(13);
                config.durabilities.bodyBase.validateAndSet(16);
                config.durabilities.leggingsBase.validateAndSet(14);
                config.durabilities.bootsBase.validateAndSet(12);
                config.durabilities.leatherArmorMult.validateAndSet(8);
                config.durabilities.copperArmorMult.validateAndSet(10);
                config.durabilities.ironArmorMult.validateAndSet(12);
                config.durabilities.goldenArmorMult.validateAndSet(6);
                config.durabilities.diamondArmorMult.validateAndSet(20);
                config.durabilities.netheriteArmorMult.validateAndSet(30);
                config.durabilities.turtleArmorMult.validateAndSet(16);
                config.durabilities.wolfArmorMult.validateAndSet(8);
                config.durabilities.overrides.validateAndSet(Map.ofEntries(
                        Map.entry("minecraft:bow", 256),
                        Map.entry("minecraft:elytra", 640),
                        Map.entry("minecraft:shears", 256),
                        Map.entry("minecraft:shield", 384),
                        Map.entry("minecraft:crossbow", 320),
                        Map.entry("minecraft:trident", 384),
                        Map.entry("reliable_gliders:glider", 240),
                        Map.entry("rearm:netherite_bow", 384),
                        Map.entry("rearm:netherite_crossbow", 480)
                ));

                // Loot
                config.loot.lootEntryReplacements.validateAndSet(Map.ofEntries(
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
                config.loot.totemMap.validateAndSet(Map.of(
                        "minecraft:jungle_temple", 0.5,
                        "minecraft:pillager_outpost", 0.75,
                        "minecraft:woodland_mansion", 1.0
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
                config.worldgen.oreSizeMult.validateAndSet(1.2F);
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
                config.experience.recipeXpSwapByResult.validateAndSet(Map.of(
                        "minecraft:iron_ingot", 1.5,
                        "minecraft:copper_ingot", 1.0,
                        "minecraft:netherite_scrap", 10.0,
                        "minecraft:iron_nugget", 1.0,
                        "minecraft:gold_nugget", 1.0,
                        "minecraft:copper_nugget", 1.0
                ));
                config.experience.recipeXpSwapByInput.validateAndSet(Map.of(
                        "minecraft:raw_gold", 3.0,
                        "minecraft:gold_ore", 3.0,
                        "minecraft:deepslate_gold_ore", 3.0,
                        "minecraft:nether_gold_ore", 0.75
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
                config.combat.blazeFollowRange.validateAndSet(32);
                config.combat.illusionerFollowRange.validateAndSet(16);
                config.combat.pillagerFollowRange.validateAndSet(24);
                config.combat.ravagerFollowRange.validateAndSet(16);
                config.combat.zombieFollowRange.validateAndSet(16);
                config.combat.zombieAlertOtherRange.validateAndSet(35);
                config.combat.drownedTridentThrowDelay.validateAndSet(80);
                config.combat.endermanAttackDelay.validateAndSet(40);

                // Mod integration
                config.modIntegration.fdCakeSliceFoodLevel.validateAndSet(3);
                config.modIntegration.fdCakeSliceSaturationLevel.validateAndSet(0.3F);
                config.modIntegration.penchantXpCostMult.validateAndSet(3);
                config.modIntegration.penchantProgressOnHostileKill.validateAndSet(true);

                // Tool stats
                config.toolStats.validateAndSet(List.of(
                        new ToolStatReplacement(ToolMaterialId.of(ToolMaterial.WOOD), 80, 3.0F),
                        new ToolStatReplacement(ToolMaterialId.of(ToolMaterial.STONE), 96, 4.0F),
                        new ToolStatReplacement(ToolMaterialId.of(ToolMaterial.COPPER), 160, 5.0F),
                        new ToolStatReplacement(ToolMaterialId.of(ToolMaterial.IRON), 320, 7.0F),
                        new ToolStatReplacement(ToolMaterialId.of(ToolMaterial.GOLD), 80, 20.0F),
                        new ToolStatReplacement(ToolMaterialId.of(ToolMaterial.DIAMOND), 1280, 9.0F),
                        new ToolStatReplacement(ToolMaterialId.of(ToolMaterial.NETHERITE), 2560, 11.0F)
                ));

                // Weapon stats
                config.weaponStats.validateAndSet(List.of(
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.WOOD), WeaponType.AXE, -3.4F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.STONE), WeaponType.AXE, -3.4F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.IRON), WeaponType.AXE, -3.4F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.COPPER), WeaponType.AXE, -3.4F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.DIAMOND), WeaponType.AXE, -3.4F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.NETHERITE), WeaponType.AXE, -3.4F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.WOOD), WeaponType.HOE, -2.0F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.STONE), WeaponType.HOE, -2.0F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.IRON), WeaponType.HOE, -2.0F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.COPPER), WeaponType.HOE, -2.0F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.DIAMOND), WeaponType.HOE, -2.0F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.NETHERITE), WeaponType.HOE, -2.0F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.GOLD), WeaponType.SWORD, -1.6F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.GOLD), WeaponType.PICKAXE, -2.2F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.GOLD), WeaponType.SHOVEL, -2.5F),
                        new WeaponStatReplacement(ToolMaterialId.of(ToolMaterial.GOLD), WeaponType.HOE, 0.0F)
                ));

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
                config.exhaustionValues.blockAttack.validateAndSet(0.0F);
                config.exhaustionValues.breakBlock.validateAndSet(0.005F);
                config.exhaustionValues.climb.validateAndSet(0.0F);
                config.exhaustionValues.crawl.validateAndSet(0.0F);
                config.exhaustionValues.drawRangedWeapon.validateAndSet(0.0F);
                config.exhaustionValues.hungerEffectTick.validateAndSet(0.005F);
                config.exhaustionValues.jump.validateAndSet(0.05F);
                config.exhaustionValues.jumpSprint.validateAndSet(0.2F);
                config.exhaustionValues.naturalHealing.validateAndSet(6.0F);
                config.exhaustionValues.sneak.validateAndSet(0.0F);
                config.exhaustionValues.sprint.validateAndSet(0.1F);
                config.exhaustionValues.swimFast.validateAndSet(0.01F);
                config.exhaustionValues.swimSlow.validateAndSet(0.01F);
                config.exhaustionValues.takeDamage.validateAndSet(0.1F);
                config.exhaustionValues.throwWeapon.validateAndSet(0.0F);

                // Durabilities
                config.durabilities.helmetBase.validateAndSet(11);
                config.durabilities.bodyBase.validateAndSet(16);
                config.durabilities.leggingsBase.validateAndSet(15);
                config.durabilities.bootsBase.validateAndSet(13);
                config.durabilities.leatherArmorMult.validateAndSet(5);
                config.durabilities.copperArmorMult.validateAndSet(11);
                config.durabilities.ironArmorMult.validateAndSet(15);
                config.durabilities.goldenArmorMult.validateAndSet(7);
                config.durabilities.diamondArmorMult.validateAndSet(33);
                config.durabilities.netheriteArmorMult.validateAndSet(37);
                config.durabilities.turtleArmorMult.validateAndSet(25);
                config.durabilities.wolfArmorMult.validateAndSet(4);
                config.durabilities.overrides.validateAndSet(Map.of());

                // Loot
                config.loot.lootEntryReplacements.validateAndSet(Map.of());
                config.loot.totemMap.validateAndSet(Map.of());
                config.loot.emeraldBlockResetsVaults.validateAndSet(false);
                config.loot.maceEnchantmentsInOminousVaults.validateAndSet(false);
                config.loot.tridentEnchantmentsFromElderGuardians.validateAndSet(false);
                config.loot.frostWalkerOnlyInIgloosAndIceBox.validateAndSet(false);
                config.loot.cursesOnlyInDesertPyramids.validateAndSet(false);

                // Worldgen
                config.worldgen.dungeonChanceMult.validateAndSet(1.0F);
                config.worldgen.mineshaftChanceMult.validateAndSet(1.0F);
                config.worldgen.oreSizeMult.validateAndSet(1.0F);
                config.worldgen.allowAncientDebrisAirExposure.validateAndSet(false);
                config.worldgen.ancientDebrisLargeSize.validateAndSet(3);
                config.worldgen.ancientDebrisSmallSize.validateAndSet(2);
                config.worldgen.ancientDebrisMinAmount.validateAndSet(0);
                config.worldgen.removeAncientDebrisHeightLimit.validateAndSet(false);
                config.worldgen.addRandomChestsToMansions.validateAndSet(false);
                config.worldgen.addRandomChestsToMonuments.validateAndSet(false);

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
                config.combat.eatTimeIncrease.validateAndSet(0.0F);
                config.combat.blazeFollowRange.validateAndSet(48);
                config.combat.illusionerFollowRange.validateAndSet(32);
                config.combat.pillagerFollowRange.validateAndSet(32);
                config.combat.ravagerFollowRange.validateAndSet(32);
                config.combat.zombieFollowRange.validateAndSet(35);
                config.combat.zombieAlertOtherRange.validateAndSet(35);
                config.combat.drownedTridentThrowDelay.validateAndSet(40);
                config.combat.endermanAttackDelay.validateAndSet(20);

                // Mod integration
                config.modIntegration.fdCakeSliceFoodLevel.validateAndSet(2);
                config.modIntegration.fdCakeSliceSaturationLevel.validateAndSet(0.1F);
                config.modIntegration.penchantXpCostMult.validateAndSet(1);
                config.modIntegration.penchantProgressOnHostileKill.validateAndSet(false);

                // Tool & weapon stats
                config.toolStats.validateAndSet(List.of());
                config.weaponStats.validateAndSet(List.of());

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
                config.misc.suppressWorldgenReadWarning.validateAndSet(false);
            }
        }
    }
}
