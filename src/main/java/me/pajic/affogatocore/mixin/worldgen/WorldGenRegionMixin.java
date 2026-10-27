package me.pajic.affogatocore.mixin.worldgen;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.server.level.WorldGenRegion;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WorldGenRegion.class)
public class WorldGenRegionMixin {

    @WrapMethod(method = "warnIfReadOutsideWriteZone")
    private void suppressWarning(int chunkX, int chunkZ, Operation<Void> original) {
        if (!AffogatoCore.CONFIG.misc.suppressWorldgenReadWarning.get()) original.call(chunkX, chunkZ);
    }
}
