package me.pajic.affogatocore.mixin.misc;

import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.effects.ReplaceDisk;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ReplaceDisk.class)
public class ReplaceDiskMixin {

    @ModifyVariable(
            method = "apply",
            at = @At("HEAD"),
            argsOnly = true,
            name = "position"
    )
    private Vec3 triggerFrostWalkerEarly(Vec3 position, @Local(argsOnly = true, name = "entity") Entity entity) {
        if (!AffogatoCore.CONFIG.misc.frostWalkerEarlyTrigger.get()) return position;
        Vec3 movement = entity.getKnownMovement();
        return position.add(movement.x, 0, movement.z);
    }
}
