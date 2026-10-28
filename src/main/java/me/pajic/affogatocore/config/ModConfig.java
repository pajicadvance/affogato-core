package me.pajic.affogatocore.config;

import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedMap;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedPair;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.util.LootEntryReplacement;
import me.pajic.affogatocore.util.ToolStatReplacement;
import me.pajic.affogatocore.util.WeaponStatReplacement;

@SuppressWarnings({"unchecked", "rawtypes"})
@Version(version = 1)
public class ModConfig extends Config {

    public ModConfig() {
        super(AffogatoCore.id("config"));
    }

    public ValidatedEnum<Preset> preset = new ValidatedEnum<>(Preset.DISABLE_ALL);
    public Packs packs = new Packs();
    public ExhaustionValues exhaustionValues = new ExhaustionValues();
    public Durabilities durabilities = new Durabilities();
    public Loot loot = new Loot();
    public Worldgen worldgen = new Worldgen();
    public Experience experience = new Experience();
    public Combat combat = new Combat();
    public ModIntegration modIntegration = new ModIntegration();
    public ValidatedList<ToolStatReplacement> toolStats = new ValidatedAny<>(new ToolStatReplacement()).toList();
    public ValidatedList<WeaponStatReplacement> weaponStats = new ValidatedAny<>(new WeaponStatReplacement()).toList();
    public Misc misc = new Misc();
    public Debug debug = new Debug();

    public static class Packs extends ConfigSection {
        public ValidatedBoolean earlyGameExtension = new ValidatedBoolean(false);
        public ValidatedBoolean recipeAdditions = new ValidatedBoolean(false);
        public ValidatedBoolean antiFarmPack = new ValidatedBoolean(false);
        public ValidatedBoolean customWanderingTraderTrades = new ValidatedBoolean(false);
    }

    public static class ExhaustionValues extends ConfigSection {
        public ValidatedFloat attack = new ValidatedFloat(0.1F);
        public ValidatedFloat blockAttack = new ValidatedFloat(0);
        public ValidatedFloat breakBlock = new ValidatedFloat(0.005F);
        public ValidatedFloat climb = new ValidatedFloat(0);
        public ValidatedFloat crawl = new ValidatedFloat(0);
        public ValidatedFloat drawRangedWeapon = new ValidatedFloat(0);
        public ValidatedFloat hungerEffectTick = new ValidatedFloat(0.005F);
        public ValidatedFloat jump = new ValidatedFloat(0.05F);
        public ValidatedFloat jumpSprint = new ValidatedFloat(0.2F);
        public ValidatedFloat naturalHealing = new ValidatedFloat(6.0F);
        public ValidatedFloat sneak = new ValidatedFloat(0);
        public ValidatedFloat sprint = new ValidatedFloat(0.1F);
        public ValidatedFloat swimFast = new ValidatedFloat(0.01F);
        public ValidatedFloat swimSlow = new ValidatedFloat(0.01F);
        public ValidatedFloat takeDamage = new ValidatedFloat(0.1F);
        public ValidatedFloat throwWeapon = new ValidatedFloat(0);
    }

    public static class Durabilities extends ConfigSection {
        public ValidatedInt helmetBase = new ValidatedInt(11);
        public ValidatedInt bodyBase = new ValidatedInt(16);
        public ValidatedInt leggingsBase = new ValidatedInt(15);
        public ValidatedInt bootsBase = new ValidatedInt(13);
        public ValidatedInt leatherArmorMult = new ValidatedInt(5);
        public ValidatedInt copperArmorMult = new ValidatedInt(11);
        public ValidatedInt ironArmorMult = new ValidatedInt(15);
        public ValidatedInt goldenArmorMult = new ValidatedInt(7);
        public ValidatedInt diamondArmorMult = new ValidatedInt(33);
        public ValidatedInt netheriteArmorMult = new ValidatedInt(37);
        public ValidatedInt turtleArmorMult = new ValidatedInt(25);
        public ValidatedInt wolfArmorMult = new ValidatedInt(4);
        public ValidatedMap<String, Integer> overrides = (new ValidatedMap.Builder())
                .keyHandler(new ValidatedString())
                .valueHandler(new ValidatedInt(1, Integer.MAX_VALUE, 1))
                .build();
    }

    public static class Loot extends ConfigSection {
        public ValidatedMap<String, LootEntryReplacement> lootEntryReplacements = (new ValidatedMap.Builder())
                .keyHandler(new ValidatedString())
                .valueHandler(new ValidatedAny<>(new LootEntryReplacement()))
                .build();
        public ValidatedMap<String, Double> totemMap = (new ValidatedMap.Builder())
                .keyHandler(new ValidatedString())
                .valueHandler(new ValidatedDouble(0.0, 1.0, 0.0))
                .build();
        public ValidatedBoolean emeraldBlockResetsVaults = new ValidatedBoolean(false);
        public ValidatedBoolean maceEnchantmentsInOminousVaults = new ValidatedBoolean(false);
        public ValidatedBoolean tridentEnchantmentsFromElderGuardians = new ValidatedBoolean(false);
        public ValidatedBoolean frostWalkerOnlyInIgloosAndIceBox = new ValidatedBoolean(false);
        public ValidatedBoolean cursesOnlyInDesertPyramids = new ValidatedBoolean(false);
        public ValidatedDouble curseEnchantedBookChance = new ValidatedDouble(0.5, 1, 0);
        public ValidatedDouble frostWalkerIglooChance = new ValidatedDouble(0.5, 1, 0);
        public ValidatedDouble frostWalkerIceBoxChance = new ValidatedDouble(1, 1, 0);
    }

    public static class Worldgen extends ConfigSection {
        public ValidatedFloat dungeonChanceMult = new ValidatedFloat(1);
        public ValidatedFloat mineshaftChanceMult = new ValidatedFloat(1);
        public ValidatedFloat oreSizeMult = new ValidatedFloat(1);
        public ValidatedBoolean allowAncientDebrisAirExposure = new ValidatedBoolean(false);
        public ValidatedInt ancientDebrisLargeSize = new ValidatedInt(3);
        public ValidatedInt ancientDebrisSmallSize = new ValidatedInt(2);
        public ValidatedInt ancientDebrisMinAmount = new ValidatedInt(0);
        public ValidatedBoolean removeAncientDebrisHeightLimit = new ValidatedBoolean(false);
        public ValidatedBoolean addRandomChestsToMansions = new ValidatedBoolean(false);
        public ValidatedFloat mansionRandomChestChance = new ValidatedFloat(0.0039F, 1.0F, 0F, ValidatedNumber.WidgetType.TEXTBOX);
        public ValidatedBoolean addRandomChestsToMonuments = new ValidatedBoolean(false);
        public ValidatedFloat monumentRandomChestChance = new ValidatedFloat(0.0025F, 1.0F, 0F, ValidatedNumber.WidgetType.TEXTBOX);
    }

    public static class Experience extends ConfigSection {
        public ValidatedMap<String, Double> recipeXpSwapByResult = (new ValidatedMap.Builder())
                .keyHandler(new ValidatedString())
                .valueHandler(new ValidatedDouble(0))
                .build();
        public ValidatedMap<String, Double> recipeXpSwapByInput = (new ValidatedMap.Builder())
                .keyHandler(new ValidatedString())
                .valueHandler(new ValidatedDouble(0))
                .build();
        public ValidatedPair<Integer, Integer> coal = new ValidatedInt(0).pairWith(new ValidatedInt(2));
        public ValidatedPair<Integer, Integer> netherGold = new ValidatedInt(0).pairWith(new ValidatedInt(1));
        public ValidatedPair<Integer, Integer> lapis = new ValidatedInt(2).pairWith(new ValidatedInt(5));
        public ValidatedPair<Integer, Integer> diamond = new ValidatedInt(3).pairWith(new ValidatedInt(7));
        public ValidatedPair<Integer, Integer> emerald = new ValidatedInt(3).pairWith(new ValidatedInt(7));
        public ValidatedPair<Integer, Integer> redstone = new ValidatedInt(1).pairWith(new ValidatedInt(5));
        public ValidatedPair<Integer, Integer> quartz = new ValidatedInt(2).pairWith(new ValidatedInt(5));
        public ValidatedBoolean noEnderDragonXpAfterFirst = new ValidatedBoolean(false);
        public ValidatedBoolean noFishingXp = new ValidatedBoolean(false);
        public ValidatedBoolean noMobXpDrops = new ValidatedBoolean(false);
        public ValidatedBoolean noXpFromTrading = new ValidatedBoolean(false);
    }

    public static class Combat extends ConfigSection {
        public ValidatedFloat eatTimeIncrease = new ValidatedFloat(0);
        public ValidatedBoolean eatCancel = new ValidatedBoolean(false);
        public ValidatedInt blazeFollowRange = new ValidatedInt(48);
        public ValidatedInt illusionerFollowRange = new ValidatedInt(32);
        public ValidatedInt pillagerFollowRange = new ValidatedInt(32);
        public ValidatedInt ravagerFollowRange = new ValidatedInt(32);
        public ValidatedFloat ravagerRoarKnockbackMult = new ValidatedFloat(0.1F);
        public ValidatedInt zombieFollowRange = new ValidatedInt(35);
        public ValidatedInt zombieAlertOtherRange = new ValidatedInt(35);
        public ValidatedInt drownedTridentThrowDelay = new ValidatedInt(40);
        public ValidatedInt endermanAttackDelay = new ValidatedInt(20);
        public ValidatedFloat shieldProjectileDamageDivisor = new ValidatedFloat(1);
    }

    public static class Misc extends ConfigSection {
        public ValidatedBoolean hardDifficultyDefault = new ValidatedBoolean(false);
        public ValidatedBoolean noRapidHealing = new ValidatedBoolean(false);
        public ValidatedBoolean noRiptideInRain = new ValidatedBoolean(false);
        public ValidatedBoolean nightVisionNuke = new ValidatedBoolean(false);
        public ValidatedInt torchLightLevel = new ValidatedInt(14);
        public ValidatedInt copperTorchLightLevel = new ValidatedInt(14);
        public ValidatedFloat iceFriction = new ValidatedFloat(0.98F);
        public ValidatedFloat blueIceFriction = new ValidatedFloat(0.989F);
        public ValidatedInt caveAmbientSoundFrequency = new ValidatedInt(6000, Integer.MAX_VALUE, 0);
        public ValidatedBoolean hoglinSpawnOnlyOnCrimsonNylium = new ValidatedBoolean(false);
        public ValidatedBoolean frostWalkerEarlyTrigger = new ValidatedBoolean(false);
        public ValidatedBoolean ironMinesObsidian = new ValidatedBoolean(false);
        public ValidatedBoolean moreStoneTypesInStoneCraftingRecipes = new ValidatedBoolean(false);
        public ValidatedBoolean fixOcelotSpawn = new ValidatedBoolean(false);
        public ValidatedBoolean noVillagerRerolling = new ValidatedBoolean(false);
        public ValidatedBoolean suppressWorldgenReadWarning = new ValidatedBoolean(false);
    }

    public static class ModIntegration extends ConfigSection {
        public ValidatedInt fdCakeSliceFoodLevel = new ValidatedInt(2);
        public ValidatedFloat fdCakeSliceSaturationLevel = new ValidatedFloat(0.1F);
        public ValidatedInt penchantXpCostMult = new ValidatedInt(1, 8, 1);
        public ValidatedBoolean penchantProgressOnHostileKill = new ValidatedBoolean(false);
        public ValidatedBoolean mastercutterAdvancementRemoval = new ValidatedBoolean(false);
    }

    public static class Debug extends ConfigSection {
        public ValidatedBoolean logMaterialIds = new ValidatedBoolean(false);
        public ValidatedBoolean exportJsonPatches = new ValidatedBoolean(false);
    }
}
