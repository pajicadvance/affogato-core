Reworked enchanted book loot distribution to build on top of Penchant's loot rework:
- Removed `loot.maceEnchantmentsInOminousVaults`.
- Added `loot.randomLootExcludedEnchantments`, which removes the listed enchantments from random loot, including when Penchant's loot rework is enabled. The Affogato preset excludes Frost Walker, Density, Breach, Loyalty, Impaling, Riptide, Channeling and Stun Burst.
- Replaced `loot.tridentEnchantmentsFromElderGuardians` with `loot.channelingAndRiptideFromElderGuardians`, which adds Channeling and Riptide books to elder guardian drops.
- Replaced `loot.frostWalkerOnlyInIgloosAndIceBox` and `loot.frostWalkerIglooChance` with `loot.frostWalkerInIceBox`, which adds Frost Walker books to ancient city ice box chests.
- `loot.cursesOnlyInDesertPyramids` now also removes curses from random loot when Penchant's loot rework is enabled.
- The desert pyramid curse pool now only contains Curse of Binding and Curse of Vanishing, so curses added to `#minecraft:curse` by other mods are no longer included.
- Added `modIntegration.enderscapeStunBurstInEndCities` and `modIntegration.enderscapeStunBurstChance` (enabled with a 5% chance in the Affogato preset), which add Stun Burst books to end city chests.
- Added `modIntegration.penchantReplaceDisabledLootBooks` (enabled in the Affogato preset), which replaces enchanted books added by Penchant's loot rework with random enchanted books when their enchantment is disabled for loot by Enchantment Disabler.
