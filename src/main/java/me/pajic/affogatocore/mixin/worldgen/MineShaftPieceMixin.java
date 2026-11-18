package me.pajic.affogatocore.mixin.worldgen;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.util.StructureGenUtil;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.level.levelgen.structure.structures.MineshaftPieces$MineShaftPiece")
public class MineShaftPieceMixin {

    @ModifyExpressionValue(
            method = "canBeReplaced",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftStructure$Type;getPlanksState()Lnet/minecraft/world/level/block/state/BlockState;"
            )
    )
    private BlockState useSprucePlanks(BlockState original) {
        return isDeepslate() ? StructureGenUtil.DEEPSLATE_MINESHAFT_TYPE.getPlanksState() : original;
    }

    @ModifyExpressionValue(
            method = "canBeReplaced",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftStructure$Type;getWoodState()Lnet/minecraft/world/level/block/state/BlockState;"
            )
    )
    private BlockState useSpruceLog(BlockState original) {
        return isDeepslate() ? StructureGenUtil.DEEPSLATE_MINESHAFT_TYPE.getWoodState() : original;
    }

    @ModifyExpressionValue(
            method = "canBeReplaced",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftStructure$Type;getFenceState()Lnet/minecraft/world/level/block/state/BlockState;"
            )
    )
    private BlockState useSpruceFence(BlockState original) {
        return isDeepslate() ? StructureGenUtil.DEEPSLATE_MINESHAFT_TYPE.getFenceState() : original;
    }

    @Unique
    private boolean isDeepslate() {
        return StructureGenUtil.isDeepslateMineshaftPiece(((StructurePiece) (Object) this).getBoundingBox());
    }
}
