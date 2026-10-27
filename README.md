# Affogato Core

This is a core mod used in my modpacks that provides features, tweaks, tools and utilities to modify game behavior which cannot be modified by regular means. It provides a config powered by Fzzy Config that allows toggling features and tweaks on and off and using the tools and utilities provided in the mod for your own needs. All options are off by default so you can easily install this in your modpack and set it up however you want.

Features:
- Adjust exhaustion values for all vanilla actions and a few additional actions that don't cause any exhaustion in vanilla.
- Adjust durability values for armor and unique items.
- Adjust attack damage, attack speed, durability and mining speed values for all tools in the game.
- Specify loot replacements, which replace one item with another item in every loot table.
- Adjust the chance for dungeons and mineshafts to generate.
- Apply a global multiplier to ore size, and modify generation rules for ancient debris.
- Remove all XP rewards from smelting and blasting recipes, while allowing and specifying the XP reward for recipes which match the specified input or output item.
- Adjust the amount of XP dropped from mining ore.
- Prevent XP drops from all mobs, fishing, trading, and Ender Dragon kills after the first.
- Adjust follow range for a bunch of mobs.
- Disable rapid healing. Works with AppleSkin.
- Adjust the time required to eat food items.
- Cancel eating on taking damage, excluding damage sources specified in the `affogatocore:no_eat_cancel` tag.
- Adjust the light level of torches.
- Adjust the friction of ice blocks.
- Allow resetting trial vaults by inserting an emerald block.
- Prevent Riptide from working in rain.
- Night Vision Nuke: Makes night vision unobtainable in survival by removing the recipe and preventing suspicious stew from getting the effect.
- Hard Difficulty Default: Difficulty on create world screen defaults to Hard and freshly created servers default to Hard in server.properties.
- ... and a bunch of other miscellaneous tweaks too specific to list here.

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
    - Removes mob drops which most resource farms rely on.
    - Increases mob loot drop chances and quantities across the board.
    - Reduces the amount of iron and gold required in recipes using them.
    - Increases the amount of quartz dropped from quartz ore.
    - Prevents using wool, blaze rods, and dried kelp blocks as fuel.
- Affogato Recipe Additions:
    - Allows crafting crying obsidian by combining obsidian and a ghast tear.
    - Allows crafting glowstone dust by combining redstone and blaze powder.
- Affogato Wandering Trader Trades: 
    - Replaces wandering trader trades with custom trades.
- Affogato Language Overrides:
    - Replaces certain item names and item descriptions.
