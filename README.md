# Affogato Core

This is a core mod used in my modpacks that provides features, tweaks, tools and utilities to modify game behavior which cannot be modified by regular means. It provides a config powered by Fzzy Config that allows toggling features and tweaks on and off and using the tools and utilities provided in the mod for your own needs. All options are off by default so you can easily install this in your modpack and set it up however you want.

The config comes with two presets: Affogato, which applies the settings used in my modpacks, and Disable All, which resets everything to vanilla defaults. When the current settings don't match either preset, the preset option shows Custom.

Features:
- Adjust durability, mining speed, attack damage and attack speed of any item. Each modification targets a list of item IDs and item tag IDs.
- Adjust exhaustion values for all vanilla actions and a few additional actions that don't cause any exhaustion in vanilla.
- Specify loot replacements, which replace one item with another item in every loot table.
- Add Totems of Undying to chest loot tables with a configurable chance per loot table.
- Make Density and Breach enchanted books obtainable only from ominous trial vaults.
- Make Loyalty, Riptide, Impaling and Channeling enchanted books obtainable only from elder guardians.
- Make Frost Walker enchanted books obtainable only from igloo and ancient city ice box chests, with configurable chances.
- Make curse enchanted books obtainable only from desert pyramid chests, with a configurable chance.
- Allow resetting trial vaults by inserting an emerald block.
- Adjust the chance for dungeons and mineshafts to generate.
- Apply depth-based ore size multipliers in the Overworld, with configurable multipliers and heights.
- Modify generation rules for ancient debris: vein sizes, minimum amount, air exposure and height limit.
- Place extra loot chests at random enclosed locations inside woodland mansions and ocean monuments.
- Remove all XP rewards from smelting and blasting recipes, while allowing and specifying the XP reward for recipes which match the specified input or output item.
- Adjust the amount of XP dropped from mining ore. Works with Universal Ores.
- Prevent XP drops from all mobs, fishing, trading, and Ender Dragon kills after the first.
- Adjust follow range for blazes, illusioners, pillagers, ravagers and zombies, and the range in which zombies alert other zombies.
- Adjust the delay between drowned trident throws and between enderman attacks.
- Reduce the knockback of the ravager roar with a multiplier.
- Reduce the durability damage shields take from projectiles.
- Disable rapid healing. Works with AppleSkin.
- Adjust the time required to eat food items.
- Cancel eating on taking damage, excluding damage sources specified in the `affogatocore:no_eat_cancel` tag.
- Adjust the light level of torches and copper torches.
- Adjust the friction of ice and blue ice.
- Adjust how often cave ambient sounds play.
- Prevent Riptide from working in rain.
- Make hoglins spawn only on crimson nylium.
- Make Frost Walker freeze water ahead of the player, so players moving at high speeds can't outrun the effect.
- Allow mining obsidian, crying obsidian and respawn anchors with iron tools.
- Allow andesite, granite, diorite and tuff in recipes that use cobblestone variants for stone tools and crafting.
- Fix ocelots being counted as monsters when spawning in jungles.
- Prevent rerolling villager professions by breaking their workstation.
- Adjust how often potent sulfur geysers erupt with a multiplier.
- Adjust how often potent sulfur geyser eruption sounds and trial spawner ambient sounds play, and the distance from the player within which they play, to stop them from filling up the sound limit.
- Suppress the log warning about world generation reading outside its write zone.
- Night Vision Nuke: Makes night vision unobtainable in survival by removing its brewing recipes, replacing it with invisibility in loot and suspicious stew, and removing it from advancement requirements.
- Hard Difficulty Default: Difficulty on create world screen defaults to Hard and freshly created servers default to Hard in server.properties.

Mod integration:
- Farmer's Delight: Adjust the nutrition and saturation of cake slices. Also applies to eating vanilla cake.
- Penchant: Multiply enchantment XP costs, and progress enchantments on armor and weapons by killing hostile mobs (and on Farmer's Delight knives by killing animals).
- Mastercutter: Remove its advancements.

Client features:
- Shader presets: If Iris is installed and shader preset files are provided, creates shader presets that can be switched from the Sodium video settings.
- Disable LambdaBetterGrass layers.
- Remove splash texts added by mods.

Packs (toggleable via config):
- Affogato Early Game Changes:
    - Hides wooden and stone tools and removes their recipes.
    - Adds flint tools (replaces wooden tools).
    - Flint axe has a recipe path that can be crafted in a 2x2 grid.
    - Requires using an axe for getting drops from logs.
    - Increases flint drop chance from gravel blocks.
    - Increases stick drop chance from leaves.
    - Increases apple drop chance from leaves which drop them.
    - If Farmer's Delight is present, makes shoveling gravel on the cutting table always drop flint.
    - Allows copper ore to be mined with flint tools.
    - Adapts affected vanilla advancements and adds a few extra ones.
    - Reworks the vanilla in-game tutorial to match the changes above.
    - Gives wither skeletons golden swords instead of stone swords.
    - Increases jungle sapling drop chance from jungle leaves.
- Affogato Anti-Farm Package:
    - Removes mob drops which most resource farms rely on. Also covers Variants & Ventures mobs.
    - Increases mob loot drop chances and quantities across the board.
    - Reduces the amount of iron and gold required in recipes using them.
    - Increases the amount of quartz dropped from quartz ore.
    - Prevents using wool, blaze rods, and dried kelp blocks as fuel.
- Affogato Recipe Additions:
    - Allows crafting crying obsidian by combining obsidian and a ghast tear.
    - Allows crafting glowstone dust by combining redstone and blaze powder.
    - If Farmer's Delight is present, replaces the cake recipe with one using milk bottles, sugar, sweet berries, an egg, pie crust and apple cider.
- Affogato Wandering Trader Trades: 
    - Replaces wandering trader trades with custom trades.
- Affogato Language Overrides (client config):
    - Replaces certain item names and item descriptions.
