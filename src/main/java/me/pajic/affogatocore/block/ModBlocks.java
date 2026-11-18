package me.pajic.affogatocore.block;

import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.item.ModBlockItem;
import me.pajic.affogatocore.mixin.block.StairBlockAccessor;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {

    public static final Block MOSSY_COBBLED_DEEPSLATE = registerMossyDeepslateBlock(
            "mossy_cobbled_deepslate", Block::new, Blocks.COBBLED_DEEPSLATE
    );
    public static final Block MOSSY_COBBLED_DEEPSLATE_STAIRS = registerMossyDeepslateBlock(
            "mossy_cobbled_deepslate_stairs",
            properties -> StairBlockAccessor.ac$create(MOSSY_COBBLED_DEEPSLATE.defaultBlockState(), properties),
            Blocks.COBBLED_DEEPSLATE_STAIRS
    );
    public static final Block MOSSY_COBBLED_DEEPSLATE_SLAB = registerMossyDeepslateBlock(
            "mossy_cobbled_deepslate_slab", SlabBlock::new, Blocks.COBBLED_DEEPSLATE_SLAB
    );
    public static final Block MOSSY_COBBLED_DEEPSLATE_WALL = registerMossyDeepslateBlock(
            "mossy_cobbled_deepslate_wall", WallBlock::new, Blocks.COBBLED_DEEPSLATE_WALL
    );
    public static final Block MOSSY_DEEPSLATE_BRICKS = registerMossyDeepslateBlock(
            "mossy_deepslate_bricks", Block::new, Blocks.DEEPSLATE_BRICKS
    );
    public static final Block MOSSY_DEEPSLATE_BRICK_STAIRS = registerMossyDeepslateBlock(
            "mossy_deepslate_brick_stairs",
            properties -> StairBlockAccessor.ac$create(MOSSY_DEEPSLATE_BRICKS.defaultBlockState(), properties),
            Blocks.DEEPSLATE_BRICK_STAIRS
    );
    public static final Block MOSSY_DEEPSLATE_BRICK_SLAB = registerMossyDeepslateBlock(
            "mossy_deepslate_brick_slab", SlabBlock::new, Blocks.DEEPSLATE_BRICK_SLAB
    );
    public static final Block MOSSY_DEEPSLATE_BRICK_WALL = registerMossyDeepslateBlock(
            "mossy_deepslate_brick_wall", WallBlock::new, Blocks.DEEPSLATE_BRICK_WALL
    );
    public static final Block INFESTED_MOSSY_DEEPSLATE_BRICKS = registerMossyDeepslateBlock(
            "infested_mossy_deepslate_bricks",
            properties -> new InfestedBlock(MOSSY_DEEPSLATE_BRICKS, properties),
            Blocks.INFESTED_DEEPSLATE
    );

    public static void init() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            entries.insertAfter(
                    Items.COBBLED_DEEPSLATE_WALL, MOSSY_COBBLED_DEEPSLATE, MOSSY_COBBLED_DEEPSLATE_STAIRS,
                    MOSSY_COBBLED_DEEPSLATE_SLAB, MOSSY_COBBLED_DEEPSLATE_WALL
            );
            entries.insertAfter(
                    Items.DEEPSLATE_BRICK_WALL, MOSSY_DEEPSLATE_BRICKS, MOSSY_DEEPSLATE_BRICK_STAIRS,
                    MOSSY_DEEPSLATE_BRICK_SLAB, MOSSY_DEEPSLATE_BRICK_WALL
            );
        });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries ->
                entries.insertAfter(Items.INFESTED_DEEPSLATE, INFESTED_MOSSY_DEEPSLATE_BRICKS)
        );
    }

    private static Block registerMossyDeepslateBlock(String name, Function<BlockBehaviour.Properties, Block> factory, Block copyFrom) {
        return registerModBlock(name, factory, copyFrom, AffogatoCore.CONFIG.packs.mossyDeepslateSet.get());
    }

    private static Block registerModBlock(String name, Function<BlockBehaviour.Properties, Block> factory, Block copyFrom, boolean enabled) {
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, AffogatoCore.id(name));
        Block block = Registry.register(
                BuiltInRegistries.BLOCK, blockKey,
                factory.apply(BlockBehaviour.Properties.ofLegacyCopy(copyFrom).setId(blockKey))
        );
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, AffogatoCore.id(name));
        Registry.register(
                BuiltInRegistries.ITEM, itemKey,
                new ModBlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix(), enabled)
        );
        return block;
    }
}
