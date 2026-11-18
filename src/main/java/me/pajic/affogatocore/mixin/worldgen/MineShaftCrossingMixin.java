package me.pajic.affogatocore.mixin.worldgen;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.util.StructureGenUtil;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MineshaftPieces.MineShaftCrossing.class)
public class MineShaftCrossingMixin {

    @ModifyExpressionValue(
            method = {"postProcess", "placeSupportPillar"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftStructure$Type;getPlanksState()Lnet/minecraft/world/level/block/state/BlockState;"
            )
    )
    private BlockState useSprucePlanks(BlockState original) {
        return StructureGenUtil.isDeepslateMineshaftPiece(((StructurePiece) (Object) this).getBoundingBox()) ?
                StructureGenUtil.DEEPSLATE_MINESHAFT_TYPE.getPlanksState() : original;
    }
}
