package me.pajic.affogatocore.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class StructureGenUtil {

    private static final Set<Direction> DIRECTIONS = Set.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);

    public static void placeChestsAtRandomLocations(
            BoundingBox boundingBox,
            WorldGenLevel worldGenLevel,
            RandomSource randomSource,
            BiPredicate<BlockState, BlockPos> predicateOn,
            Predicate<BlockState> predicateAt,
            float chance,
            ResourceKey<LootTable> lootTable,
            @Nullable Integer minYOverride
    ) {
        int minY = minYOverride == null ? boundingBox.minY() : minYOverride;
        for (int x = boundingBox.minX(); x < boundingBox.maxX(); x++) {
            for (int y = minY; y < boundingBox.maxY(); y++) {
                for (int z = boundingBox.minZ(); z < boundingBox.maxZ(); z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = worldGenLevel.getBlockState(pos);
                    if (predicateOn.test(state, pos)) {
                        if (!worldGenLevel.canSeeSkyFromBelowWater(pos) && checkAbove(pos, worldGenLevel)) {
                            BlockPos pos1 = pos.above();
                            BlockState state1 = worldGenLevel.getBlockState(pos1);
                            if (predicateAt.test(state1)) {
                                Direction dir = checkSides(pos1, worldGenLevel, predicateAt);
                                if (dir != null && randomSource.nextFloat() < chance) {
                                    createChest(worldGenLevel, boundingBox, randomSource, pos1, dir, lootTable);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean checkAbove(BlockPos pos, WorldGenLevel level) {
        int x = pos.getX();
        int z = pos.getZ();
        for (int y = pos.getY(); y < level.getHeight(); y++) {
            BlockPos pos1 = new BlockPos(x, y, z);
            BlockState state = level.getBlockState(pos1);
            if (state.isFaceSturdy(level, pos1, Direction.DOWN)) return true;
        }
        return false;
    }

    @Nullable
    private static Direction checkSides(BlockPos pos, WorldGenLevel level, Predicate<BlockState> predicate) {
        Map<Direction, Boolean> states = new HashMap<>();
        for (Direction dir : DIRECTIONS) states.put(dir, predicate.test(level.getBlockState(pos.relative(dir))));
        if (states.containsValue(false)) {
            for (Direction dir : DIRECTIONS) if (states.get(dir) && !states.get(dir.getOpposite())) return dir;
            for (Direction dir : DIRECTIONS) if (states.get(dir)) return dir;
        }
        return null;
    }

    private static void createChest(
            ServerLevelAccessor serverLevelAccessor,
            BoundingBox boundingBox,
            RandomSource randomSource,
            BlockPos blockPos,
            Direction direction,
            ResourceKey<LootTable> resourceKey
    ) {
        if (boundingBox.isInside(blockPos) && !serverLevelAccessor.getBlockState(blockPos).is(Blocks.CHEST)) {
            serverLevelAccessor.setBlock(blockPos, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, direction), 2);
            BlockEntity blockEntity = serverLevelAccessor.getBlockEntity(blockPos);
            if (blockEntity instanceof ChestBlockEntity) {
                ((ChestBlockEntity) blockEntity).setLootTable(resourceKey, randomSource.nextLong());
            }
        }
    }
}
