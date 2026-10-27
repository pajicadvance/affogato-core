package me.pajic.affogatocore.mixin.trade;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.ResetProfession;
import net.minecraft.world.entity.npc.villager.Villager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ResetProfession.class)
public class ResetProfessionMixin {

    @WrapMethod(method = "lambda$create$2")
    private static boolean preventProfessionReset(ServerLevel level, Villager body, long timestamp, Operation<Boolean> original) {
        return !AffogatoCore.CONFIG.misc.noVillagerRerolling.get() && original.call(level, body, timestamp);
    }
}
