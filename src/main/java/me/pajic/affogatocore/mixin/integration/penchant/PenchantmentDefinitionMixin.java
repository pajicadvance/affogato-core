package me.pajic.affogatocore.mixin.integration.penchant;

import archives.tater.penchant.PenchantmentDefinition;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.affogatocore.AffogatoCore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("penchant")
@Mixin(PenchantmentDefinition.class)
public class PenchantmentDefinitionMixin {

    @ModifyExpressionValue(
            method = "createFallback",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/Enchantment;getAnvilCost()I"
            )
    )
    private static int multXpCost(int original) {
        return original * AffogatoCore.CONFIG.modIntegration.penchantXpCostMult.get();
    }
}
