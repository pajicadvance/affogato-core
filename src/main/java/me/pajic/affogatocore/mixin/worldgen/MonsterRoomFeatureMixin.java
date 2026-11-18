package me.pajic.affogatocore.mixin.worldgen;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.MonsterRoomFeature;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MonsterRoomFeature.class)
public class MonsterRoomFeatureMixin {

    @ModifyExpressionValue(
            method = "place",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/block/Blocks;MOSSY_COBBLESTONE:Lnet/minecraft/world/level/block/Block;",
                    opcode = Opcodes.GETSTATIC
            )
    )
    private Block useMossyCobbledDeepslate(Block original, @Local(argsOnly = true, name = "origin") BlockPos origin) {
        return useDeepslateVariant(origin) ? ModBlocks.MOSSY_COBBLED_DEEPSLATE : original;
    }

    @ModifyExpressionValue(
            method = "place",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/block/Blocks;COBBLESTONE:Lnet/minecraft/world/level/block/Block;",
                    opcode = Opcodes.GETSTATIC
            )
    )
    private Block useCobbledDeepslate(Block original, @Local(argsOnly = true, name = "origin") BlockPos origin) {
        return useDeepslateVariant(origin) ? Blocks.COBBLED_DEEPSLATE : original;
    }

    @Unique
    private static boolean useDeepslateVariant(BlockPos origin) {
        return AffogatoCore.CONFIG.packs.mossyDeepslateSet.get() &&
                AffogatoCore.CONFIG.worldgen.deepslateDungeonVariant.get() &&
                origin.getY() < 0;
    }
}
