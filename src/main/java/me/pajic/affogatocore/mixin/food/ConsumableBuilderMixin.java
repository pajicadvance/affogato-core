package me.pajic.affogatocore.mixin.food;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.item.component.Consumable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Consumable.Builder.class)
public class ConsumableBuilderMixin {

    @ModifyExpressionValue(
            method = "build",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/item/component/Consumable$Builder;consumeSeconds:F",
                    opcode = Opcodes.GETFIELD
            )
    )
    private float modifyEatSpeed(float original) {
        return original + AffogatoCore.CONFIG.combat.eatTimeIncrease.get();
    }
}
