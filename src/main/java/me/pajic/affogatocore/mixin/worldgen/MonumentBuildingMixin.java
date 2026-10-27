package me.pajic.affogatocore.mixin.worldgen;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.util.StructureGenUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentPieces;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OceanMonumentPieces.MonumentBuilding.class)
public class MonumentBuildingMixin {

    @WrapMethod(method = "postProcess")
    private void addChestsToRandomLocations(
            WorldGenLevel level,
            StructureManager structureManager,
            ChunkGenerator generator,
            RandomSource random,
            BoundingBox chunkBB,
            ChunkPos chunkPos,
            BlockPos referencePos,
            Operation<Void> original
    ) {
        original.call(level, structureManager, generator, random, chunkBB, chunkPos, referencePos);
        if (AffogatoCore.CONFIG.worldgen.addRandomChestsToMonuments.get()) {
            StructureGenUtil.placeChestsAtRandomLocations(
                    chunkBB,
                    ((StructurePiece) (Object) this).getBoundingBox(),
                    level,
                    random,
                    (state, _) -> state.is(Blocks.PRISMARINE) || state.is(Blocks.PRISMARINE_BRICKS),
                    state -> state.is(BlockTags.AIR) || state.is(Blocks.WATER),
                    AffogatoCore.CONFIG.worldgen.monumentRandomChestChance.get(),
                    ResourceKey.create(Registries.LOOT_TABLE, AffogatoCore.id("chests/ocean_monument"))
            );
        }
    }
}
