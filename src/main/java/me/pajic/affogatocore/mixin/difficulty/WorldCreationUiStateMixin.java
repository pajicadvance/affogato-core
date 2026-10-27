package me.pajic.affogatocore.mixin.difficulty;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.client.gui.screens.worldselection.WorldCreationUiState;
import net.minecraft.world.Difficulty;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldCreationUiState.class)
public class WorldCreationUiStateMixin {

    @ModifyExpressionValue(
            method = "<init>",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/Difficulty;NORMAL:Lnet/minecraft/world/Difficulty;",
                    opcode = Opcodes.GETSTATIC
            )
    )
    private Difficulty setDefaultDifficulty(Difficulty original) {
        return AffogatoCore.CONFIG.misc.hardDifficultyDefault.get() ? Difficulty.HARD : original;
    }
}
