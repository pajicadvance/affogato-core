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

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class StructureGenUtil {

    public static void placeChestsAtRandomLocations(
            BoundingBox chunkBoundingBox,
            BoundingBox structureBoundingBox,
            WorldGenLevel worldGenLevel,
            RandomSource randomSource,
            BiPredicate<BlockState, BlockPos> predicateOn,
            Predicate<BlockState> predicateAt,
            float chance,
            ResourceKey<LootTable> lootTable
    ) {
        if (!chunkBoundingBox.intersects(structureBoundingBox)) return;
        int minX = Math.max(chunkBoundingBox.minX(), structureBoundingBox.minX());
        int maxX = Math.min(chunkBoundingBox.maxX(), structureBoundingBox.maxX());
        int minY = Math.max(chunkBoundingBox.minY(), structureBoundingBox.minY());
        int maxY = Math.min(chunkBoundingBox.maxY(), structureBoundingBox.maxY());
        int minZ = Math.max(chunkBoundingBox.minZ(), structureBoundingBox.minZ());
        int maxZ = Math.min(chunkBoundingBox.maxZ(), structureBoundingBox.maxZ());
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    pos.set(x, y, z);
                    if (predicateOn.test(worldGenLevel.getBlockState(pos), pos)) {
                        BlockPos pos1 = pos.above();
                        if (predicateAt.test(worldGenLevel.getBlockState(pos1)) && isEnclosed(pos1, worldGenLevel, structureBoundingBox, predicateAt)) {
                            Direction dir = checkSides(pos1, worldGenLevel, predicateAt);
                            if (dir != null && randomSource.nextFloat() < chance) {
                                createChest(worldGenLevel, chunkBoundingBox, randomSource, pos1, dir, lootTable);
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean isEnclosed(BlockPos pos, WorldGenLevel level, BoundingBox structureBoundingBox, Predicate<BlockState> predicateAt) {
        BlockPos.MutableBlockPos pos1 = pos.mutable().move(Direction.UP);
        while (structureBoundingBox.isInside(pos1)) {
            BlockState state = level.getBlockState(pos1);
            if (!predicateAt.test(state) && state.isFaceSturdy(level, pos1, Direction.DOWN)) return true;
            pos1.move(Direction.UP);
        }
        return false;
    }

    @Nullable
    private static Direction checkSides(BlockPos pos, WorldGenLevel level, Predicate<BlockState> predicate) {
        Map<Direction, Boolean> states = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.Plane.HORIZONTAL) states.put(dir, predicate.test(level.getBlockState(pos.relative(dir))));
        if (states.containsValue(false)) {
            for (Direction dir : Direction.Plane.HORIZONTAL) if (states.get(dir) && !states.get(dir.getOpposite())) return dir;
            for (Direction dir : Direction.Plane.HORIZONTAL) if (states.get(dir)) return dir;
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
