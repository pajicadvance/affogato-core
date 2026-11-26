package me.pajic.affogatocore.mixin.integration.penchant;

import archives.tater.penchant.util.PenchantmentHelper;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.integration.PenchantRequirements;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;

@IfModLoaded("penchant")
@Mixin(PenchantmentHelper.class)
public class PenchantmentHelperMixin {

    @WrapMethod(method = "getXpLevelCost")
    private static int applyTierXpCost(Holder<Enchantment> enchantment, Operation<Integer> original) {
        return PenchantRequirements.getTierValue(
                enchantment,
                AffogatoCore.CONFIG.modIntegration.penchantTierXpCosts.get(),
                original.call(enchantment)
        );
    }

    @WrapMethod(method = "getBookRequirement")
    private static int applyTierBookRequirement(Holder<Enchantment> enchantment, Operation<Integer> original) {
        return PenchantRequirements.getTierValue(
                enchantment,
                AffogatoCore.CONFIG.modIntegration.penchantTierBookRequirements.get(),
                original.call(enchantment)
        );
    }
}
