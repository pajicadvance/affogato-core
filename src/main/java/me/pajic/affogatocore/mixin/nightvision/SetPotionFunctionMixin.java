package me.pajic.affogatocore.mixin.nightvision;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.core.Holder;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SetPotionFunction.class)
public class SetPotionFunctionMixin {

    @ModifyExpressionValue(
            method = "run",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/storage/loot/functions/SetPotionFunction;potion:Lnet/minecraft/core/Holder;",
                    opcode = Opcodes.GETFIELD
            )
    )
    private Holder<Potion> replaceNightVision(Holder<Potion> original) {
        return AffogatoCore.CONFIG.misc.nightVisionNuke.get() && original == Potions.NIGHT_VISION ? Potions.INVISIBILITY : original;
    }
}
