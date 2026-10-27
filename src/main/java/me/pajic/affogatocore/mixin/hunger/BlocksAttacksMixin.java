package me.pajic.affogatocore.mixin.hunger;

import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlocksAttacks.class)
public class BlocksAttacksMixin {

    @Inject(
            method = "hurtBlockingItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;awardStat(Lnet/minecraft/stats/Stat;)V"
            )
    )
    private void blockAttackExhaustion(
            Level level,
            ItemStack item,
            LivingEntity user,
            InteractionHand hand,
            float damage,
            CallbackInfo ci,
            @Local(name = "player") Player player
    ) {
        player.causeFoodExhaustion(AffogatoCore.CONFIG.exhaustionValues.blockAttack.get());
    }
}
