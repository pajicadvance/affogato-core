package me.pajic.affogatocore.mixin.misc;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.level.block.entity.PotentSulfurBlockEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotentSulfurBlockEntity.class)
public class PotentSulfurBlockEntityMixin {

    @WrapOperation(
            method = "lambda$static$4",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/block/entity/PotentSulfurBlockEntity;waitingCountdown:I",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 0
            )
    )
    private static void modifyEruptionFrequency(PotentSulfurBlockEntity instance, int value, Operation<Void> original) {
        original.call(instance, Math.max(1, Math.round(value / AffogatoCore.CONFIG.misc.potentSulfurEruptionFrequencyMult.get())));
    }
}
