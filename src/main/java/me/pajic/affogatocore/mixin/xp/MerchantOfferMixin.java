package me.pajic.affogatocore.mixin.xp;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MerchantOffer.class)
public class MerchantOfferMixin {

    @ModifyReturnValue(
            method = "shouldRewardExp",
            at = @At("RETURN")
    )
    private boolean preventTradeXp(boolean original) {
        return !AffogatoCore.CONFIG.experience.noXpFromTrading.get() && original;
    }
}
