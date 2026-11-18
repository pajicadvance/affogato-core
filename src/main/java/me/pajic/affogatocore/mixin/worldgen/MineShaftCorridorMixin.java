package me.pajic.affogatocore.mixin.worldgen;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.CompatFlags;
import me.pajic.affogatocore.util.StructureGenUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.vehicle.minecart.MinecartTNT;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(MineshaftPieces.MineShaftCorridor.class)
public abstract class MineShaftCorridorMixin extends StructurePiece {

    @Unique private static final Identifier WALL_LANTERN = Identifier.fromNamespaceAndPath("walllanterns", "wall_lantern");

    protected MineShaftCorridorMixin(StructurePieceType type, int genDepth, BoundingBox boundingBox) {
        super(type, genDepth, boundingBox);
    }

    @ModifyExpressionValue(
            method = {"postProcess", "placeDoubleLowerOrUpperSupport", "placeSupport"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftStructure$Type;getPlanksState()Lnet/minecraft/world/level/block/state/BlockState;"
            )
    )
    private BlockState useSprucePlanks(BlockState original) {
        return StructureGenUtil.isDeepslateMineshaftPiece(boundingBox) ? StructureGenUtil.DEEPSLATE_MINESHAFT_TYPE.getPlanksState() : original;
    }

    @ModifyExpressionValue(
            method = "placeDoubleLowerOrUpperSupport",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftStructure$Type;getWoodState()Lnet/minecraft/world/level/block/state/BlockState;"
            )
    )
    private BlockState useSpruceLog(BlockState original) {
        return StructureGenUtil.isDeepslateMineshaftPiece(boundingBox) ? StructureGenUtil.DEEPSLATE_MINESHAFT_TYPE.getWoodState() : original;
    }

    @ModifyExpressionValue(
            method = {"fillPillarDownOrChainUp", "placeSupport"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftStructure$Type;getFenceState()Lnet/minecraft/world/level/block/state/BlockState;"
            )
    )
    private BlockState useSpruceFence(BlockState original) {
        return StructureGenUtil.isDeepslateMineshaftPiece(boundingBox) ? StructureGenUtil.DEEPSLATE_MINESHAFT_TYPE.getFenceState() : original;
    }

    @ModifyArg(
            method = "placeSupport",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftCorridor;maybeGenerateBlock(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/util/RandomSource;FIIILnet/minecraft/world/level/block/state/BlockState;)V"
            ),
            index = 7
    )
    private BlockState useLanterns(BlockState torch) {
        if (!StructureGenUtil.isDeepslateMineshaftPiece(boundingBox)) return torch;
        if (CompatFlags.WALL_LANTERNS_LOADED) {
            Mirror mirror = getMirror() == null ? Mirror.NONE : getMirror();
            Rotation rotation = getRotation() == null ? Rotation.NONE : getRotation();
            Direction facing = torch.mirror(mirror).rotate(rotation).getValue(WallTorchBlock.FACING);
            return BuiltInRegistries.BLOCK.getValue(WALL_LANTERN).defaultBlockState()
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
        }
        return Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, true);
    }

    @ModifyExpressionValue(
            method = "<init>(ILnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftStructure$Type;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I",
                    ordinal = 0
            )
    )
    private int increaseRailChance(int original, @Local(argsOnly = true, name = "boundingBox") BoundingBox corridorBox) {
        return StructureGenUtil.isDeepslateMineshaftPiece(corridorBox) && original == 1 ? 0 : original;
    }

    @ModifyExpressionValue(
            method = "postProcess",
            at = {
                    @At(value = "CONSTANT", args = "floatValue=0.7"),
                    @At(value = "CONSTANT", args = "floatValue=0.9")
            }
    )
    private float reduceRailGaps(float original) {
        return StructureGenUtil.isDeepslateMineshaftPiece(boundingBox) ? original + (1 - original) * 0.8F : original;
    }

    @ModifyExpressionValue(
            method = "postProcess",
            at = @At(
                    value = "CONSTANT",
                    args = "intValue=100"
            )
    )
    private int increaseChestMinecartChance(int original) {
        return StructureGenUtil.isDeepslateMineshaftPiece(boundingBox) ?
                Math.max(1, Math.round(original / AffogatoCore.CONFIG.worldgen.deepslateMineshaftChestMinecartMult.get())) :
                original;
    }

    @WrapOperation(
            method = "postProcess",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftCorridor;maybeGenerateBlock(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/util/RandomSource;FIIILnet/minecraft/world/level/block/state/BlockState;)V"
            )
    )
    private void placeDeepslateRails(
            MineshaftPieces.MineShaftCorridor instance,
            WorldGenLevel level,
            BoundingBox chunkBB,
            RandomSource random,
            float chance,
            int x,
            int y,
            int z,
            BlockState rail,
            Operation<Void> original
    ) {
        if (!StructureGenUtil.isDeepslateMineshaftPiece(boundingBox)) {
            original.call(instance, level, chunkBB, random, chance, x, y, z, rail);
            return;
        }
        if (random.nextFloat() < AffogatoCore.CONFIG.worldgen.deepslateMineshaftActivatorRailChance.get()) {
            BlockState activatorRail = Blocks.ACTIVATOR_RAIL.defaultBlockState().setValue(PoweredRailBlock.SHAPE, rail.getValue(RailBlock.SHAPE));
            original.call(instance, level, chunkBB, random, chance, x, y, z, activatorRail);
            if (getBlock(level, x, y, z, chunkBB).is(Blocks.ACTIVATOR_RAIL) && placeRedstoneTorch(level, chunkBB, random, x, y, z)) {
                placeBlock(level, activatorRail.setValue(PoweredRailBlock.POWERED, true), x, y, z, chunkBB);
            }
            return;
        }
        original.call(instance, level, chunkBB, random, chance, x, y, z, rail);
        if (getBlock(level, x, y, z, chunkBB).is(Blocks.RAIL) && random.nextFloat() < AffogatoCore.CONFIG.worldgen.deepslateMineshaftTntMinecartChance.get()) {
            MinecartTNT minecart = EntityTypes.TNT_MINECART.create(level.getLevel(), EntitySpawnReason.CHUNK_GENERATION);
            if (minecart != null) {
                BlockPos pos = getWorldPos(x, y, z);
                minecart.setInitialPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                level.addFreshEntity(minecart);
            }
        }
    }

    @Unique
    private boolean placeRedstoneTorch(WorldGenLevel level, BoundingBox chunkBB, RandomSource random, int x, int y, int z) {
        int first = random.nextBoolean() ? -1 : 1;
        for (int side : new int[]{first, -first}) {
            BlockPos pos = getWorldPos(x + side, y, z);
            BlockState torch = Blocks.REDSTONE_TORCH.defaultBlockState();
            if (chunkBB.isInside(pos) && level.getBlockState(pos).isAir() && torch.canSurvive(level, pos)) {
                placeBlock(level, torch, x + side, y, z, chunkBB);
                return true;
            }
        }
        return false;
    }
}
